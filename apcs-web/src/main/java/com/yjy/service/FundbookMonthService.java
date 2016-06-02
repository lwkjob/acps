package com.yjy.service;


import com.yjy.common.utils.JsonUtils;
import com.yjy.constant.FundConstant;
import com.yjy.entity.*;
import com.yjy.repository.mapper.FundbookMonthExtMapper;
import com.yjy.repository.mapper.FundbookdayExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 月结逻辑
 * Created by Administrator on 2016/5/31.
 */
@Service("fundbookMonthService")
public class FundbookMonthService {

    @Resource
    private FundbookMonthExtMapper fundbookMonthExtMapper;

    @Resource
    private FundbookdayExtMapper fundbookdayExtMapper;

    @Resource
    private UserBasicExtMapper userBasicExtMapper;

    private static Logger logger = LoggerFactory.getLogger(FundbookMonthService.class);

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");

    //取前一月
    public Date getPreMonthsDate(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, -1);
        return preDate;
    }

    //取后一月
    public Date getNextMonthsDate(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, 1);
        return preDate;
    }

    //插入日清数据
    public int insertFundBookMonth(Date startDate, Date endDate, List<Fundbookcode> bookcodes,int typeid,List<UserBasicInfo> users) {

        long start = System.currentTimeMillis();//记录运行时间
        while (endDate.compareTo(startDate) != -1) {
            String monthTableName = FundConstant.FUNDBOOKMONTH_TABLE_NAME_PRE + simpleDateFormat_yyyyMM.format(startDate);
            String dayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + simpleDateFormat_yyyyMM.format(startDate);




            //3 每个表，每个用户，每月，每个账本一条数据
            for (Fundbookcode bookcode : bookcodes) {
                //2 统计当前月每个用户每个账本产生的数据
                Fundbookday fundbookdayExample = new Fundbookday(); //查询条件
                fundbookdayExample.setBookcode(bookcode.getBookcode());
                List<Fundbookday> fundbookdays = getFundbookDays(fundbookdayExample, dayTableName);
                //当月发发生数据
                Map<String, Fundbookmonth> fundbookmonthMap = getFundbookMonth(fundbookdays);
                List<Fundbookmonth> FundbookmonthList = new ArrayList<>();
                int i=0;
                String bookDateStr = simpleDateFormat_yyyyMM.format(startDate);
                // 当期活跃用户
                if (users==null){
                      users = userBasicExtMapper.getUsers(0, 0, typeid, 0, startDate.getTime() / 1000);
                }
                for (UserBasicInfo userBasicInfo : users) {
                    i++;
                    //3.3每个账本
                    Fundbookmonth fundbookmonth = new Fundbookmonth();
                    fundbookmonth.setUserid(userBasicInfo.getUserid());
                    fundbookmonth.setBookdate(Integer.parseInt(bookDateStr));
                    fundbookmonth.setBookcode(bookcode.getBookcode());
                    String mapkey = String.format("%s|-%s|-%s",
                            bookDateStr,
                            bookcode.getBookcode(),
                            userBasicInfo.getUserid());
                    //如果当月有发生数据

                    Fundbookmonth fundbookmonthActive = fundbookmonthMap.get(mapkey);
                    if (fundbookmonthActive != null) {
                        fundbookmonth.setAreacode(fundbookmonthActive.getAreacode());
                        fundbookmonth.setPrevbalance(fundbookmonthActive.getPrevbalance());
                        fundbookmonth.setBalance(fundbookmonthActive.getBalance());
                        fundbookmonth.setHappendebit(fundbookmonthActive.getHappendebit());
                        fundbookmonth.setHappencredit(fundbookmonthActive.getHappencredit());
                    }else {
                        BigDecimal bigDecimal0=new BigDecimal(0);
                        fundbookmonth.setAreacode(0);
                        fundbookmonth.setPrevbalance(bigDecimal0);
                        fundbookmonth.setBalance(bigDecimal0);
                        fundbookmonth.setHappendebit(bigDecimal0);
                        fundbookmonth.setHappencredit(bigDecimal0);
                    }
                    FundbookmonthList.add(fundbookmonth);
                }

                List<Fundbookcode> delBookCode=new ArrayList<>();
                delBookCode.add(bookcode);
                logger.info("删除重新统计"+JsonUtils.toJson(bookcode));
                //1删除需要重新统计的数据
                fundbookMonthExtMapper.deleteFundbookMonth(
                        delBookCode,
                        users,
                        monthTableName);
                logger.info("内存计算完"+i+"剩余"+(bookcodes.size()-i)+",当前数据量:"+FundbookmonthList.size());
                fundbookMonthExtMapper.batchInsert(FundbookmonthList, monthTableName);
                logger.info("插入完成账本"+bookDateStr+" "+ JsonUtils.toJson(bookcode));
            }
            startDate = getNextMonthsDate(startDate);//轮训到下一个月
        }
        long end = System.currentTimeMillis();
        logger.info("全部计算完了" + (float) (end - start) / 1000 + "秒");
        //批量插入
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


    //查询区间内全部账本日清数据
    public List<Fundbookday> getFundbookDays(Fundbookday fundbook, String tableName) {
        return fundbookdayExtMapper.selectByExample(fundbook, tableName);
    }


    /**
     * 统计每月发生数据
     * 计算范围时间内:每月-每个用户-每个账本 的sum(借),sum(贷),余
     * 取出来的数据必须是按照按照用户和发生时间排好序,因为本期余就取最后一条的余记录
     */
    public Map<String, Fundbookmonth> getFundbookMonth(List<Fundbookday> fundbookdays) {
        Map<String, Fundbookmonth> map = new HashedMap(2000);//一个用户每个月至少账本数量条,用户量很大的情况月统计也很大
        for (Fundbookday fundbookday : fundbookdays) {
            String dayStr = StringUtils.substring(fundbookday.getBookdate() + "", 0, 6);
            String key = String.format("%s|-%s|-%s", dayStr, fundbookday.getBookcode(), fundbookday.getUserid());
            Fundbookmonth fundbookmonth = map.get(key);
            if (map.get(key) == null) {
                fundbookmonth = new Fundbookmonth();
                copyPropertis(fundbookday, dayStr, fundbookmonth);
            } else {
                fundbookmonth.setHappencredit(fundbookday.getHappencredit().add(fundbookmonth.getHappencredit()));
                fundbookmonth.setHappendebit(fundbookday.getHappendebit().add(fundbookmonth.getHappendebit()));
                fundbookmonth.setBalance(fundbookday.getBalance());   //当期余额永远是取最后一条数据的余
            }
            fundbookmonth.setPrevbalance(getPrevbalance(key));  //设置上期的余。。。。
            map.put(key, fundbookmonth);
        }
        return map;
    }


    //获取前一月的月结余额
    private BigDecimal getPrevbalance(String key) {
        String currentDateStr = StringUtils.substring(key, 0, 6);
        Date currentDate = parseDateFromStr(simpleDateFormat_yyyyMM, currentDateStr);
        Date preDayDate = getPreMonthsDate(currentDate);
        String preDateStr = simpleDateFormat_yyyyMM.format(preDayDate);

        String preKey = preDateStr + StringUtils.substring(key, 6);
        String tableName = FundConstant.FUNDBOOKMONTH_TABLE_NAME_PRE + preDateStr;
        String[] preKeyArray = StringUtils.split(preKey, "|-");
        String bookdate = preDateStr;
        String bookcode = preKeyArray[1];
        String userid = preKeyArray[2];
        Fundbookmonth fundbookmonthExample = new Fundbookmonth();//查询条件
        fundbookmonthExample.setUserid(Integer.parseInt(userid));
        fundbookmonthExample.setBookdate(Integer.parseInt(bookdate)); //前一个月
        fundbookmonthExample.setBookcode(bookcode);

        List<Fundbookmonth> fundbookmonths = fundbookMonthExtMapper.selectByExample(fundbookmonthExample, tableName);
        if (fundbookmonths != null) {
            return fundbookmonths.get(0).getBalance();
        } else {
            logger.info("数据库都没找到preKey=" + preKey);
            return new BigDecimal(0);
        }
    }


    //复制账本数据到日清数据
    private void copyPropertis(Fundbookday fundbookday, String dayStr, Fundbookmonth fundbookmonth) {
        fundbookmonth.setBookcode(fundbookday.getBookcode());
        fundbookmonth.setAreacode(fundbookday.getAreacode());
        fundbookmonth.setHappencredit(fundbookday.getHappencredit());
        fundbookmonth.setHappendebit(fundbookday.getHappendebit());
        fundbookmonth.setBalance(fundbookday.getBalance());
        fundbookmonth.setBookdate(Integer.parseInt(dayStr));
        fundbookmonth.setBookid(fundbookday.getBookid());
        fundbookmonth.setUserid(fundbookday.getUserid());
    }
}
