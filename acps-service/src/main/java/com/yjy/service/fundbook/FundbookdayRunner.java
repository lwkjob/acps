package com.yjy.service.fundbook;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.utils.DateTools;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.Fundbookday;
import com.yjy.common.entity.fundbook.UserBasicInfo;
import com.yjy.repository.mapper.fundbook.FundbookdayExtMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/6/6.
 */
public class FundbookdayRunner implements Runnable {
    private Logger logger = LoggerFactory.getLogger(FundbookdayRunner.class);

    private CountDownLatch countDownLatch;
    private Map<Integer, List<Fundbookcode>> bookcodemap;

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    private List<UserBasicInfo> users;

    private Date bookDate;

    private FundbookdayExtMapper fundbookdayExtMapper;

    private Map<String, Fundbookday> fundbookdayMap;

    private String bookDateStr;

    private String preDateStr;

    private String fundbookDayTableName;

    private JedisTemplate jedisTemplate;

    @Override
    public void run() {

        //每天

        long start = System.currentTimeMillis();
        final String currentMonthLastDay = DateTools.getCurrentMonthLastDay(DateTools.parseDateFromString_yyyyMMdd(preDateStr, logger), simpleDateFormat_yyyyMMdd);
        final int dataSize = users.size();
        final int pageSize = 2000;
        final int cacheThreadCount = (dataSize / pageSize) + 1;
        logger.info(bookDateStr +"日清 "+cacheThreadCount + "页");
        final CountDownLatch subcountDownLatch = new CountDownLatch(cacheThreadCount);
        ExecutorService subexecutorService = Executors.newFixedThreadPool(cacheThreadCount);
        for (int j = 1; j <= cacheThreadCount; j++) {
            final int jm = j;
            subexecutorService.execute(new Runnable() {

                @Override
                public void run() {
                    List<Fundbookday> insertFundbookdays = new ArrayList<>();
                    long start = System.currentTimeMillis();
                    for (int i = (jm - 1) * pageSize; !(i > (jm * pageSize - 1) || i > (dataSize - 1)); i++) {

                        //当期活跃用户
                        UserBasicInfo userBasicInfo = users.get(i);
                        //这个用户类型需要写的账本数据
                        List<Fundbookcode> fundbookcodes = bookcodemap.get(userBasicInfo.getTypeId());

                        for (int m = 0; m <= (fundbookcodes.size() - 1); m++) { //2.每个账本

                            Fundbookcode bookcode = fundbookcodes.get(m);

                            //3.4每个账本
                            Fundbookday fundbookday = new Fundbookday();
                            fundbookday.setUserid(userBasicInfo.getUserid());
                            fundbookday.setBookdate(Integer.parseInt(bookDateStr));
                            fundbookday.setBookcode(bookcode.getBookcode());

                            String mapKey = String.format("%s|-%s|-%s", fundbookday.getBookdate(), fundbookday.getBookcode(), fundbookday.getUserid());
                            String mapPreKey = String.format("%s|-%s|-%s", preDateStr, fundbookday.getBookcode(), fundbookday.getUserid());

                            String preBalanceStr = null;
                            if (!preDateStr.equals("20130831")) {//201309之前没有数据
                                preBalanceStr = jedisTemplate.get(mapPreKey); //查询前一天的余
                            }

                            if (!currentMonthLastDay.equals(preDateStr)) {
                                jedisTemplate.del(mapPreKey);  //用了就删了他,留下每月的最后一条数据
                            }
                            BigDecimal preBalance = null;
                            if (StringUtils.isNotBlank(preBalanceStr)) {
                                preBalance = new BigDecimal(preBalanceStr);
                            } else {
                                preBalance = new BigDecimal("0");
                            }

                            Fundbookday fundbookdayActive = fundbookdayMap.get(mapKey);
                            if (fundbookdayActive != null) {
                                fundbookday.setPrevbalance(preBalance);
                                fundbookday.setBalance(fundbookdayActive.getBalance());
                                fundbookday.setAreacode(fundbookdayActive.getAreacode());
                                fundbookday.setHappencredit(fundbookdayActive.getHappencredit());
                                fundbookday.setHappendebit(fundbookdayActive.getHappendebit());
                            } else {
                                BigDecimal bigDecimal0 = new BigDecimal(0);
                                fundbookday.setAreacode(0);
                                fundbookday.setBalance(preBalance);
                                fundbookday.setPrevbalance(preBalance);
                                fundbookday.setHappencredit(bigDecimal0);
                                fundbookday.setHappendebit(bigDecimal0);
                            }
//                jedisTemplate.set(mapKey, fundbookday.getBalance().doubleValue() + "");
                            insertFundbookdays.add(fundbookday);
                            if (insertFundbookdays.size() % 10000 == 0) {
                                //每3万条插入一次
                                long memeryRunTime = System.currentTimeMillis();
                                logger.info("内存计算完" + (float) (memeryRunTime - start) / 1000 + " " + bookDateStr + ",当前数据量:" + insertFundbookdays.size());
                                fundbookdayExtMapper.batchInsert(insertFundbookdays, fundbookDayTableName);
                                long insertRunTime = System.currentTimeMillis();

                                logger.info("插入完成账本" + (float) (insertRunTime - memeryRunTime) / 1000 + " " + bookDateStr + " " + bookcode.getBookcode());
                                insertFundbookdays = new ArrayList<>();
                                start=System.currentTimeMillis();
                            }
                        }

                    }
                    if (insertFundbookdays.size() > 0) {
                        //每3万条插入一次
                        long memeryRunTime = System.currentTimeMillis();
                        logger.info("最后一次内存计算 " + (float) (memeryRunTime - start) / 1000 + " " + bookDateStr + " 总数" + insertFundbookdays.size());
                        fundbookdayExtMapper.batchInsert(insertFundbookdays, fundbookDayTableName);
                        long insertRunTime = System.currentTimeMillis();
                        logger.info("最后一次插入完成 " + (float) (insertRunTime - memeryRunTime) / 1000);
                    }
                    subcountDownLatch.countDown();
                }
            });
        }

        try {
            subcountDownLatch.await();
            subexecutorService.shutdown();

        } catch (Exception e) {
            logger.error("日清分用户报错", e);
        }
        long insertRunTime = System.currentTimeMillis();
        logger.info(bookDateStr +" 今天日清刷完 " + (float) (insertRunTime - start) / 1000);
        countDownLatch.countDown();

//        for (int j = 0; j <= (users.size() - 1); j++) { //每个用户
//
//            //当期活跃用户
//            UserBasicInfo userBasicInfo = users.get(j);
//            //这个用户类型需要写的账本数据
//            List<Fundbookcode> fundbookcodes = bookcodemap.get(userBasicInfo.getTypeId());
//
//            for (int i = 0; i <= (fundbookcodes.size() - 1); i++) {//2.每个账本
//
//                Fundbookcode bookcode = fundbookcodes.get(i);
//
//                //3.4每个账本
//                Fundbookday fundbookday = new Fundbookday();
//                fundbookday.setUserid(userBasicInfo.getUserid());
//                fundbookday.setBookdate(Integer.parseInt(bookDateStr));
//                fundbookday.setBookcode(bookcode.getBookcode());
//
//                String mapKey = String.format("%s|-%s|-%s", fundbookday.getBookdate(), fundbookday.getBookcode(), fundbookday.getUserid());
//                String mapPreKey = String.format("%s|-%s|-%s", preDateStr, fundbookday.getBookcode(), fundbookday.getUserid());
//
//                String preBalanceStr = null;
//                if(!preDateStr.equals("20130831")){//201309之前没有数据
//                    preBalanceStr= jedisTemplate.get(mapPreKey); //查询前一天的余
//                }
//
//                if(!currentMonthLastDay.equals(preDateStr)){
//                        jedisTemplate.del(mapPreKey);  //用了就删了他,留下每月的最后一条数据
//                }
//                BigDecimal preBalance = null;
//                if (StringUtilsAcps.isNotBlank(preBalanceStr)) {
//                    preBalance = new BigDecimal(preBalanceStr);
//                } else {
//                    preBalance = new BigDecimal("0");
//                }
//
//                Fundbookday fundbookdayActive = fundbookdayMap.get(mapKey);
//                if (fundbookdayActive != null) {
//                    fundbookday.setPrevbalance(preBalance);
//                    fundbookday.setBalance(fundbookdayActive.getBalance());
//                    fundbookday.setAreacode(fundbookdayActive.getAreacode());
//                    fundbookday.setHappencredit(fundbookdayActive.getHappencredit());
//                    fundbookday.setHappendebit(fundbookdayActive.getHappendebit());
//                } else {
//                    BigDecimal bigDecimal0 = new BigDecimal(0);
//                    fundbookday.setAreacode(0);
//                    fundbookday.setBalance(preBalance);
//                    fundbookday.setPrevbalance(preBalance);
//                    fundbookday.setHappencredit(bigDecimal0);
//                    fundbookday.setHappendebit(bigDecimal0);
//                }
////                jedisTemplate.set(mapKey, fundbookday.getBalance().doubleValue() + "");
//                insertFundbookdays.add(fundbookday);
//                if (insertFundbookdays.size() % 10000 == 0 ) {
//                    //每3万条插入一次
//                    long memeryRunTime = System.currentTimeMillis();
//                    logger.info("内存计算完" + (float) (memeryRunTime - start) / 1000 + " " + bookDateStr + " 总用户数" + users.size() + " 剩余用户数" + (users.size() - (j + 1)) + ",当前数据量:" + insertFundbookdays.size());
//                    fundbookdayExtMapper.batchInsert(insertFundbookdays, fundbookDayTableName);
//                    long insertRunTime = System.currentTimeMillis();
//
//                    logger.info("插入完成账本" + (float) (insertRunTime - memeryRunTime) / 1000 + " " + bookDateStr + " " + bookcode.getBookcode());
//                    insertFundbookdays = new ArrayList<>();
//                    start = System.currentTimeMillis();
//                }
//            }
//        }
//
//        if (insertFundbookdays.size()>0) {
//            //每3万条插入一次
//            long memeryRunTime = System.currentTimeMillis();
//            logger.info("最后一次内存计算 " + (float) (memeryRunTime - start) / 1000 + " " + bookDateStr + " 总数" + insertFundbookdays.size());
//            fundbookdayExtMapper.batchInsert(insertFundbookdays, fundbookDayTableName);
//            long insertRunTime = System.currentTimeMillis();
//            logger.info("最后一次插入完成 " + (float) (insertRunTime - memeryRunTime) / 1000);
//        }
//        countDownLatch.countDown();

    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public FundbookdayExtMapper getFundbookdayExtMapper() {
        return fundbookdayExtMapper;
    }

    public void setFundbookdayExtMapper(FundbookdayExtMapper fundbookdayExtMapper) {
        this.fundbookdayExtMapper = fundbookdayExtMapper;
    }

    public Map<String, Fundbookday> getFundbookdayMap() {
        return fundbookdayMap;
    }

    public void setFundbookdayMap(Map<String, Fundbookday> fundbookdayMap) {
        this.fundbookdayMap = fundbookdayMap;
    }

    public String getBookDateStr() {
        return bookDateStr;
    }

    public void setBookDateStr(String bookDateStr) {
        this.bookDateStr = bookDateStr;
    }

    public String getPreDateStr() {
        return preDateStr;
    }

    public void setPreDateStr(String preDateStr) {
        this.preDateStr = preDateStr;
    }

    public JedisTemplate getJedisTemplate() {
        return jedisTemplate;
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    public String getFundbookDayTableName() {
        return fundbookDayTableName;
    }

    public void setFundbookDayTableName(String fundbookDayTableName) {
        this.fundbookDayTableName = fundbookDayTableName;
    }

    public Map<Integer, List<Fundbookcode>> getBookcodemap() {
        return bookcodemap;
    }

    public void setBookcodemap(Map<Integer, List<Fundbookcode>> bookcodemap) {
        this.bookcodemap = bookcodemap;
    }


    public List<UserBasicInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserBasicInfo> users) {
        this.users = users;
    }
}
