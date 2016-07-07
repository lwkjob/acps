package com.yjy.service.fundbook;


import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.utils.DateTools;
import com.yjy.common.constant.FundConstant;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.Fundbookday;
import com.yjy.common.entity.fundbook.Fundbookmonth;
import com.yjy.common.entity.fundbook.UserBasicInfo;
import com.yjy.repository.dto.SumMonthByBookcode;
import com.yjy.repository.mapper.fundbook.FundbookMonthExtMapper;
import com.yjy.repository.mapper.fundbook.FundbookdayExtMapper;
import com.yjy.repository.mapper.fundbook.UserBasicExtMapper;
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
@Service("fundbookMonthService")
public class FundbookMonthService {

    @Resource
    private FundbookMonthExtMapper fundbookMonthExtMapper;

    @Resource
    private FundbookdayExtMapper fundbookdayExtMapper;

    @Resource
    private UserBasicExtMapper userBasicExtMapper;


    @Resource
    JedisTemplate jedisTemplate;


    private static Logger logger = LoggerFactory.getLogger(FundbookMonthService.class);

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
            if (users == null || users.size() <= 0) {
                fundbookMonthExtMapper.deleteAll(monthTableName);
            } else {
                fundbookMonthExtMapper.deleteFundbookMonth(null, users, monthTableName);
            }
            String dayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + simpleDateFormat_yyyyMM.format(startDate);

            String bookDateStr = simpleDateFormat_yyyyMM.format(startDate);
            //本月最后一天
            String currentMonthLastDay = getCurrentMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);
            //前一个月最后一天
            String preMonthLastDay = getPreMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);

            if (users != null && users.size() > 0) {

                long startF=System.currentTimeMillis();
                for (UserBasicInfo userBasicInfo : users) {
                    List<Fundbookcode> bookcodes = bookcodemap.get(userBasicInfo.getTypeId());
                    Map<String, SumMonthByBookcode> monthByBookcodeMap = new HashedMap();
                    getSumMonthGroupBookcodeWhereUserid(dayTableName, userBasicInfo, monthByBookcodeMap);
                    for (int i = 0; i <= (bookcodes.size() - 1); i++) {
                        Fundbookcode bookcode = bookcodes.get(i);
                        //日清表的sum数据
                        SumMonthByBookcode sumMonthByBookcode = monthByBookcodeMap.get(bookcode.getBookcode());
                        //3.3每个账本
                        String balancekey = String.format("%s|-%s|-%s", currentMonthLastDay, bookcode.getBookcode(), userBasicInfo.getUserid());
                        String balanceStr = jedisTemplate.get(balancekey);
                        String prebalanceStr = null;
                        if (!bookDateStr.equals("201309")) {//201309之前没有数据
                            String prebalancekey = String.format("%s|-%s|-%s", preMonthLastDay, bookcode.getBookcode(), userBasicInfo.getUserid());
                            prebalanceStr = jedisTemplate.get(prebalancekey);
                            if (prebalanceStr != null) {
                                jedisTemplate.del(prebalancekey);
                            }
                        }
                        BigDecimal preBalance = null;
                        BigDecimal balance = null;

                        if (balanceStr != null) {
                            balance = new BigDecimal(balanceStr);
                        } else {
                            balance = new BigDecimal(0);
                        }

                        if (prebalanceStr != null) {
                            preBalance = new BigDecimal(prebalanceStr);
                        } else {
                            preBalance = new BigDecimal(0);
                        }
                        Fundbookmonth fundbookmonth = new Fundbookmonth();
                        fundbookmonth.setUserid(userBasicInfo.getUserid());
                        fundbookmonth.setBookdate(Integer.parseInt(bookDateStr));
                        fundbookmonth.setBookcode(bookcode.getBookcode());
                        fundbookmonth.setAreacode(0);
                        fundbookmonth.setPrevbalance(preBalance);
                        fundbookmonth.setBalance(balance);
                        if (sumMonthByBookcode == null) {
                            fundbookmonth.setHappendebit(new BigDecimal(0));
                            fundbookmonth.setHappencredit(new BigDecimal(0));
                        } else {

                            fundbookmonth.setHappendebit(sumMonthByBookcode.getDebit());
                            fundbookmonth.setHappencredit(sumMonthByBookcode.getCredit());
                        }
                        insertfundbookmonthList.add(fundbookmonth);

                        if (insertfundbookmonthList.size() % 2000 == 0) {
                            long memeoryRunTime = System.currentTimeMillis();
                            logger.info("内存计算:"+(double)(memeoryRunTime-startF)/1000);
                            startF= System.currentTimeMillis();
                            fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
                            long insertRunTime = System.currentTimeMillis();
                            logger.info(" 账本总数" + bookcodes.size() + " 账本顺序" + i + " 插入完成账本用时" + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr + " " + bookcode.getBookcode());
                            insertfundbookmonthList = new ArrayList<>();
                        }
                    }
                }

                if (insertfundbookmonthList.size() > 0) {

                    long memeoryRunTime = System.currentTimeMillis();
                    fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
                    long insertRunTime = System.currentTimeMillis();
                    logger.info("本月最后一天数据:" + insertfundbookmonthList.size() + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr);
                }

            } else {
                for (Integer bookcodetype : bookcodemap.keySet()) {

                    List<Fundbookcode> bookcodes = bookcodemap.get(bookcodetype);//账本分类

                    long userCreateEndTime = 0;
                    Date endDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, currentMonthLastDay + "23:59:59");
                    //用户注册时间
                    userCreateEndTime = endDateByTable.getTime() / 1000l;
                    List<UserBasicInfo> users2 = userBasicExtMapper.getUsers(0, 0, bookcodetype, 0, userCreateEndTime);

                    for (int i = 0; i <= (bookcodes.size() - 1); i++) {
                        Fundbookcode bookcode = bookcodes.get(i);

                        //日清表的sum数据
                        Map<Integer, SumMonthByBookcode> monthByBookcodeMap = new HashedMap();
                        getSumDayRepoot(dayTableName, bookcode, monthByBookcodeMap);
                        for (int j = 0; j <= (users2.size() - 1); j++) {

                            UserBasicInfo userBasicInfo = users2.get(j);

                            SumMonthByBookcode sumMonthByBookcode = monthByBookcodeMap.get(userBasicInfo.getUserid());
                            //3.3每个账本

                            String balancekey = String.format("%s|-%s|-%s", currentMonthLastDay, bookcode.getBookcode(), userBasicInfo.getUserid());
                            String balanceStr = jedisTemplate.get(balancekey);
                            String prebalanceStr = null;
                            if (!bookDateStr.equals("201309")) {//201309之前没有数据
                                String prebalancekey = String.format("%s|-%s|-%s", preMonthLastDay, bookcode.getBookcode(), userBasicInfo.getUserid());
                                prebalanceStr = jedisTemplate.get(prebalancekey);
                                if (prebalanceStr != null) {
                                    jedisTemplate.del(prebalancekey);
                                }
                            }
                            BigDecimal preBalance = null;
                            BigDecimal balance = null;

                            if (balanceStr != null) {
                                balance = new BigDecimal(balanceStr);
                            } else {
                                balance = new BigDecimal(0);
                            }

                            if (prebalanceStr != null) {
                                preBalance = new BigDecimal(prebalanceStr);
                            } else {
                                preBalance = new BigDecimal(0);
                            }
                            Fundbookmonth fundbookmonth = new Fundbookmonth();
                            fundbookmonth.setUserid(userBasicInfo.getUserid());
                            fundbookmonth.setBookdate(Integer.parseInt(bookDateStr));
                            fundbookmonth.setBookcode(bookcode.getBookcode());
                            fundbookmonth.setAreacode(0);
                            fundbookmonth.setPrevbalance(preBalance);
                            fundbookmonth.setBalance(balance);
                            if (sumMonthByBookcode == null) {
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
                                logger.info(" 账本总数" + bookcodes.size() + " 账本顺序" + i +  " 用户顺序 " + j + " 插入完成账本用时" + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr + " " + bookcode.getBookcode());
                                insertfundbookmonthList = new ArrayList<>();
                            }
                        }
                    }
                }
                if (insertfundbookmonthList.size() > 0) {

                    long memeoryRunTime = System.currentTimeMillis();
                    fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
                    long insertRunTime = System.currentTimeMillis();
                    logger.info("本月最后一天数据:" + insertfundbookmonthList.size() + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr);
                }

            }


            //3 每个表，每个用户，每月，每个账本一条数据
            startDate = getNextMonthsDate(startDate);//轮训到下一个月
        }
        long end = System.currentTimeMillis();
        logger.info("月结全部计算完了" + DateTools.formate_yyyyMMdd(endDate) + " 执行用时:" + (float) (end - startRunTime) / 1000 + "秒");
        //批量插入
        return 1;
    }

    private void getSumDayRepoot(String dayTableName, Fundbookcode bookcode, Map<Integer, SumMonthByBookcode> monthByBookcodeMap) {
        List<SumMonthByBookcode> list = fundbookdayExtMapper.sumMonthByBookcode(dayTableName, bookcode.getBookcode());
        for (SumMonthByBookcode sumMonthByBookcode : list) {
            monthByBookcodeMap.put(sumMonthByBookcode.getUserid(), sumMonthByBookcode);
        }
        list = null;
    }

    private void getSumMonthGroupBookcodeWhereUserid(String dayTableName, UserBasicInfo userBasicInfo, Map<String, SumMonthByBookcode> monthByBookcodeMap) {
//        long start=System.currentTimeMillis();
        List<SumMonthByBookcode> list = fundbookdayExtMapper.getSumMonthGroupBookcodeWhereUserid(dayTableName, userBasicInfo.getUserid());
//        long end=System.currentTimeMillis();
//        logger.info((double)(end-start)/1000+" 数据库时间？？？？");
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
}
