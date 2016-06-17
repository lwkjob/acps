package com.yjy.service;


import com.yjy.common.constant.FundConstant;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.entity.*;
import com.yjy.repository.dto.SumMonthByBookcode;
import com.yjy.repository.mapper.FundbookExtMapper;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 月结逻辑
 * Created by Administrator on 2016/5/31.
 */
@Service("fundbookMonthServiceNew")
public class FundbookMonthServiceNew {

    @Resource
    private FundbookMonthExtMapper fundbookMonthExtMapper;

    @Resource
    private FundbookdayExtMapper fundbookdayExtMapper;

    @Resource
    private FundbookExtMapper fundbookExtMapper;


    @Resource
    JedisTemplate jedisTemplate;


    private static Logger logger = LoggerFactory.getLogger(FundbookMonthServiceNew.class);

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");

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


    public int insertFundBookMonth(Date startDate, Date endDate, Map<Integer, List<Fundbookcode>> bookcodemap, List<UserBasicInfo> users) {

        long startRunTime = System.currentTimeMillis();

        while (endDate.compareTo(startDate) != -1) {

            List<Fundbookmonth> insertfundbookmonthList = new ArrayList<>();

            String monthTableName = FundConstant.FUNDBOOKMONTH_TABLE_NAME_PRE + simpleDateFormat_yyyyMM.format(startDate);

            String fundbookTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + simpleDateFormat_yyyyMM.format(startDate);

            fundbookMonthExtMapper.deleteAll(monthTableName);

            String preMonthtablename =FundConstant.FUNDBOOKMONTH_TABLE_NAME_PRE +  simpleDateFormat_yyyyMM.format(getPreMonthsDate(startDate));

            String bookDateStr = simpleDateFormat_yyyyMM.format(startDate);

            String currentMonthLastDay = getCurrentMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);


            if (users == null || users.size() <= 0) {
                users = jedisTemplate.getListObject(RedisKey.USERS_OF_DAY + currentMonthLastDay, UserBasicInfo.class);
            }

            for (int j = 0; j <= (users.size() - 1); j++) {

                UserBasicInfo userBasicInfo = users.get(j);

                List<Fundbookcode> bookcodes = bookcodemap.get(userBasicInfo.getTypeId());//账本分类
                //账本表的sum数据
                Map<String, SumMonthByBookcode> monthByBookcodeMap = new HashedMap();
                getSumDayRepoot(fundbookTableName, userBasicInfo, monthByBookcodeMap);

                for (Fundbookcode fundbookcode : bookcodes) {

                    Fundbook fundbookExample=new Fundbook();
                    fundbookExample.setBookcode(fundbookcode.getBookcode());
                    fundbookExample.setUserid(userBasicInfo.getUserid());
                    //本月发生数据得到最后一条
                    List<Fundbook> fundbooks = fundbookExtMapper.selectByExample(fundbookExample, fundbookTableName, 0, 0, true);

                    Fundbookmonth fundbookmonthExample=new Fundbookmonth();
                    fundbookmonthExample.setBookcode(fundbookcode.getBookcode());
                    fundbookmonthExample.setUserid(userBasicInfo.getUserid());
                    List<Fundbookmonth> preFundbookmonth = fundbookMonthExtMapper.selectByExample(fundbookmonthExample, preMonthtablename);

                    SumMonthByBookcode sumMonthByBookcode = monthByBookcodeMap.get(fundbookcode.getBookcode());
                    //3.3每个账本
                    BigDecimal preBalance = preFundbookmonth.get(0).getBalance();

                    BigDecimal balance =null;
                    if(fundbooks==null||fundbooks.size()==0){//本月没有发生数据,本月的余等于上月的余
                        balance=preBalance;
                    }else {
                        balance= fundbooks.get(0).getBalance();
                    }
                    Fundbookmonth fundbookmonth = new Fundbookmonth();
                    fundbookmonth.setUserid(userBasicInfo.getUserid());
                    fundbookmonth.setBookdate(Integer.parseInt(bookDateStr));
                    fundbookmonth.setBookcode(fundbookcode.getBookcode());
                    fundbookmonth.setAreacode(0);
                    fundbookmonth.setPrevbalance(preBalance);
                    fundbookmonth.setBalance(balance);
                    if (sumMonthByBookcode == null) {//发生数据
                        fundbookmonth.setHappendebit(new BigDecimal(0));
                        fundbookmonth.setHappencredit(new BigDecimal(0));
                    } else {
                        fundbookmonth.setHappendebit(sumMonthByBookcode.getDebit());
                        fundbookmonth.setHappencredit(sumMonthByBookcode.getCredit());
                    }
                    insertfundbookmonthList.add(fundbookmonth);
                    if (insertfundbookmonthList.size() % 10000 == 0) {
                        long memeoryRunTime = System.currentTimeMillis();
                        fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
                        long insertRunTime = System.currentTimeMillis();
                        logger.info("用户总数 " + users.size() + " 用户顺序 " + j + " 插入完成账本用时" + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr);
                        insertfundbookmonthList = new ArrayList<>();
                    }
                }
            }
            long memeoryRunTime = System.currentTimeMillis();
            fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
            long insertRunTime = System.currentTimeMillis();
            logger.info("本月最后一天数据:" + insertfundbookmonthList.size() + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr);
            insertfundbookmonthList = null;
            //3 每个表，每个用户，每月，每个账本一条数据
            startDate = getNextMonthsDate(startDate);//轮训到下一个月
            long end = System.currentTimeMillis();
            logger.info("月结全部计算完了" + DateTools.formate_yyyyMMdd(endDate) + " 执行用时:" + (float) (end - startRunTime) / 1000 + "秒");
        }
        //批量插入
        return 1;
    }


    private void getSumDayRepoot(String dayTableName, UserBasicInfo userBasicInfo, Map<String, SumMonthByBookcode> monthByBookcodeMap) {
        List<SumMonthByBookcode> list = fundbookExtMapper.sumMonthByUserid(dayTableName, userBasicInfo.getUserid());
        for (SumMonthByBookcode sumMonthByBookcode : list) {
            monthByBookcodeMap.put(sumMonthByBookcode.getBookcode(), sumMonthByBookcode);
        }
        list = null;
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
        Map<String, Fundbookmonth> map = new HashedMap();//一个用户每个月至少账本数量条,用户量很大的情况月统计也很大
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
//            fundbookmonth.setPrevbalance(getPrevbalance(key));  //设置上期的余。。。。
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

    public static void main(String[] args) throws Exception {

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyyMMdd");
        Date date = simpleDateFormat2.parse("201604");


        date = DateUtils.setDays(date, 1);
        date = DateUtils.addDays(date, -1);
        String s2 = simpleDateFormat2.format(date);
        logger.info(s2);
    }

    //获得当月最后一天
    private String getCurrentMonthLastDay(Date date, SimpleDateFormat simpleDateFormat) {
        date = DateUtils.addMonths(date, 1);
        date = DateUtils.setDays(date, 1);
        date = DateUtils.addDays(date, -1);
        return simpleDateFormat.format(date);
    }

    //获得上个月最后一天
    private String getPreMonthLastDay(Date date, SimpleDateFormat simpleDateFormat) {
        date = DateUtils.setDays(date, 1);
        date = DateUtils.addDays(date, -1);
        return simpleDateFormat.format(date);
    }


    //清空表
    public void deleteAll(String tableName){
        fundbookdayExtMapper.deleteAll(tableName);
    }
}