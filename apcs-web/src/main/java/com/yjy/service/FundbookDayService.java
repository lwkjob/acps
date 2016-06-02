package com.yjy.service;


import com.yjy.common.utils.JsonUtils;
import com.yjy.constant.FundConstant;
import com.yjy.entity.*;
import com.yjy.repository.mapper.FundbookExtMapper;
import com.yjy.repository.mapper.FundbookdayExtMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日清逻辑
 * Created by Administrator on 2016/5/31.
 */
@Service("fundbookDayService")
public class FundbookDayService {

    @Resource
    private FundbookdayExtMapper fundbookdayExtMapper;

    @Resource
    private FundbookExtMapper fundbookExtMapper;

    private static Logger logger = LoggerFactory.getLogger(FundbookDayService.class);

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    private static Calendar calendar = Calendar.getInstance();

    //取前一天
    public Date getPreDayDate(Date startDate) {
        Date preDate = DateUtils.addDays(startDate, -1);
        return preDate;
    }

    //取后一天
    public Date getNextDayDate(Date startDate) {
        Date preDate = DateUtils.addDays(startDate, 1);
        return preDate;
    }

    //插入日清数据
    public int insertFundBookDay(Date startDate, Date endDate, List<Fundbookcode> bookcodes, List<UserBasicInfo> users) {
        //delete日清表指定时间之前的数据

        long start = System.currentTimeMillis();

        //1.1根据时间区间算出所有需要删数据的表名
        Map<String, DelTableName> deleteTableNameMap = getDeleteTableName(startDate, endDate);

        for (String key : deleteTableNameMap.keySet()) {
                    DelTableName delTableName = deleteTableNameMap.get(key);
                    //1.2删除需要统计的数据
                    String fundbookDayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + delTableName.getTableNameSuffix();



            //3 每个表，每个用户，每天，每个账本一条数据
            //3.2每个用户
            Date startDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr());
            Date endDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getEndStr());
            while (endDateByTable.compareTo(startDateByTable) != -1) {
                int i=0;
                for (Fundbookcode bookcode : bookcodes) {
                    //2 统计每天每个用户每个账本数据
                    Fundbook fundbookExample = new Fundbook(); //查询条件
                    fundbookExample.setBookcode(bookcode.getBookcode());
                    String fundbookTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + delTableName.getTableNameSuffix();
                    List<Fundbook> fundbooks = getFundbooks(
                            fundbookExample,
                            fundbookTableName,
                            Integer.parseInt(delTableName.getStartStr()),
                            Integer.parseInt(delTableName.getEndStr()));
                    //当期月每个用户每个账本每天的业务发生
                    Map<String, Fundbookday> fundbookdayMap = getFundbookDay(fundbooks);
                    i++;
                    //用户数据量很大目前接近10万
                    List<Fundbookday> fundbookdays = new ArrayList<>();
                    String bookDateStr = simpleDateFormat_yyyyMMdd.format(startDateByTable);
                    for (UserBasicInfo userBasicInfo : users) {
                        //3.4每个账本
                            Fundbookday fundbookday = new Fundbookday();
                            fundbookday.setUserid(userBasicInfo.getUserid());
                            fundbookday.setBookdate(Integer.parseInt(bookDateStr));
                            fundbookday.setBookcode(bookcode.getBookcode());
                        //遍历没个用户今天是否产生账本信息
                        String mapKey = String.format("%s|-%s|-%s", fundbookday.getBookdate(), fundbookday.getBookcode(),  fundbookday.getUserid());
                        Fundbookday fundbookdayActive = fundbookdayMap.get(mapKey);
                        if (fundbookdayActive!=null){
                            fundbookday.setPrevbalance(fundbookdayActive.getPrevbalance());
                            fundbookday.setBalance(fundbookdayActive.getBalance());
                            fundbookday.setAreacode(fundbookdayActive.getAreacode());
                            fundbookday.setHappencredit(fundbookdayActive.getHappencredit());
                            fundbookday.setHappendebit(fundbookdayActive.getHappendebit());
                        }else {
                            BigDecimal bigDecimal0 = new BigDecimal(0);
                            fundbookday.setAreacode(0);
                            fundbookday.setBalance(bigDecimal0);
                            fundbookday.setHappencredit(bigDecimal0);
                            fundbookday.setHappendebit(bigDecimal0);
                            fundbookday.setPrevbalance(bigDecimal0);
                        }
                        fundbookdays.add(fundbookday);
                     }
                    int startInt = Integer.parseInt(delTableName.getStartStr());
                    int entInt = Integer.parseInt(delTableName.getEndStr());
                    List<Fundbookcode> delBookcode=new ArrayList();
                    delBookcode.add(bookcode);//数据太多只能一个一个账本的删除
                    logger.info("删除重新统计数据"+JsonUtils.toJson(bookcode));
                    fundbookdayExtMapper.deleteFundbookDay(
                            delBookcode,
                            users,
                            fundbookDayTableName,
                            startInt,
                            entInt
                    );
                    logger.info("内存计算完"+i+"剩余"+(bookcodes.size()-i)+",当前数据量:"+fundbookdays.size());
                    fundbookdayExtMapper.batchInsert(fundbookdays,fundbookDayTableName);
                    logger.info("插入完成账本"+bookDateStr+" "+JsonUtils.toJson(bookcode));
                }
                startDateByTable = getNextDayDate(startDateByTable);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("计算完了" + (float) (end - start) / 1000 + "秒");
        return 1;
    }

    private Date parseDateFromStr(SimpleDateFormat simpleDateFormat, String dateStr) {

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            logger.error("日期转换报错", e);
        }
        return date;
    }


    /**
     * 计算需要删除的表明和日期区间
     *
     * @return
     */
    public Map<String, DelTableName> getDeleteTableName(Date startDate, Date endDate) {
        Map<String, DelTableName> map = new HashedMap();
        while (endDate.compareTo(startDate) != -1) {
            String startStr = simpleDateFormat_yyyyMMdd.format(startDate);
            String tableNameSuffix = StringUtils.substring(startStr, 0, 6);//数据库中yyyyMM作为表名的后缀
            DelTableName delTableName = new DelTableName();
            if (map.get(tableNameSuffix) != null) {
                String startStrTemp = map.get(tableNameSuffix).getStartStr();
                delTableName.setTableNameSuffix(tableNameSuffix);
                delTableName.setStartStr(startStrTemp);
                delTableName.setEndStr(startStr);
            } else {
                delTableName.setTableNameSuffix(tableNameSuffix);
                delTableName.setStartStr(startStr);
                delTableName.setEndStr(startStr);
            }
            map.put(tableNameSuffix, delTableName);
            startDate = getNextDayDate(startDate);
        }
        logger.info("需要删除的表名和日期区间:" + JsonUtils.toJson(map));
        return map;
    }

    //查询区间内全部账本数据
    public List<Fundbook> getFundbooks(Fundbook fundbook, String tableName, int startTime, int endTime) {
        return fundbookExtMapper.selectByExample(fundbook, tableName, startTime, endTime, true);
    }


    /**
     * 统计每天的发生数据
     * 计算范围时间内:每天-每个账本-每个用户 的sum(借),sum(贷),余
     * 取出来的数据必须是按照按照用户和发生时间排好序
     */
    public Map<String, Fundbookday> getFundbookDay(List<Fundbook> fundbooks) {
        Map<String, Fundbookday> map = new HashedMap(2000);
        for (Fundbook fundbook : fundbooks) {
            calendar.setTimeInMillis(fundbook.getHappentime() * 1000l);
            String dayStr = simpleDateFormat_yyyyMMdd.format(calendar.getTime());
            String key = String.format("%s|-%s|-%s", dayStr, fundbook.getBookcode(), fundbook.getUserid());
            Fundbookday fundbookDaySum = map.get(key);
            if (map.get(key) == null) {
                fundbookDaySum = new Fundbookday();//插入一条新的
                copyPropertis(fundbook, dayStr, fundbookDaySum);
            } else {
                //更新余额,汇总发生
                fundbookDaySum.setHappendebit(fundbook.getDebit().add(fundbookDaySum.getHappendebit()));
                fundbookDaySum.setHappencredit(fundbook.getCredit().add(fundbookDaySum.getHappencredit()));
                fundbookDaySum.setBalance(fundbook.getBalance());
            }
            fundbookDaySum.setPrevbalance(getPrevbalance(map, key));  //设置上期的余。。。。
            map.put(key, fundbookDaySum);
        }
        return map;
    }

    //获取前一天的日清余额
    private BigDecimal getPrevbalance(Map<String, Fundbookday> map, String key) {
        String currentDateStr = StringUtils.substring(key, 0, 8);
        Date currentDate = null;
        try {
            currentDate = simpleDateFormat_yyyyMMdd.parse(currentDateStr);
        } catch (ParseException e) {
            logger.error("日期转换出错", e);
        }
        Date preDayDate = getPreDayDate(currentDate);
        String preKey = simpleDateFormat_yyyyMMdd.format(preDayDate) + StringUtils.substring(key, 8);
        Fundbookday fundbookday = map.get(preKey);
        if (fundbookday != null) {
            return fundbookday.getBalance();
        } else {
            logger.info("没找到去数据库查询");
            String yyyyMM = StringUtils.substring(preKey, 0, 6);
            String tableName = "fundbookday" + yyyyMM;
            String[] preKeyArray = StringUtils.split(preKey, "|-");
            String bookdate = preKeyArray[0];
            String bookcode = preKeyArray[1];
            String userid = preKeyArray[2];
            Fundbookday fundbookday1 = new Fundbookday();
            fundbookday1.setUserid(Integer.parseInt(userid));
            fundbookday1.setBookdate(Integer.parseInt(bookdate));       //前一天
            fundbookday1.setBookcode(bookcode);

            //取前一天的这个用户的这个账本数据
            List<Fundbookday> fundbookdays = fundbookdayExtMapper.selectByExample(fundbookday1, tableName);
            if (fundbookdays != null) {
                return fundbookdays.get(0).getBalance();
            } else {
                logger.info("数据库都没找到preKey=" + preKey);
                return new BigDecimal(0);
            }
        }
    }

    //复制账本数据到日清数据
    private void copyPropertis(Fundbook fundbook, String dayStr, Fundbookday fundbookDaySum) {
        fundbookDaySum.setBookcode(fundbook.getBookcode());
        fundbookDaySum.setAreacode(fundbook.getAreacode());//这个区域同booid一样是多条数据 有可能不一样
        fundbookDaySum.setHappendebit(fundbook.getDebit());
        fundbookDaySum.setHappencredit(fundbook.getCredit());
        fundbookDaySum.setBalance(fundbook.getBalance());
        fundbookDaySum.setBookdate(Integer.parseInt(dayStr));
        fundbookDaySum.setBookid(fundbook.getBookid()); //这个bookid貌似没有意义
        fundbookDaySum.setUserid(fundbook.getUserid());
    }
}
