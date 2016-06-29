package com.yjy.service.onlyMonth;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.dao.Pagination;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.Fundbookmonth;
import com.yjy.entity.UserBasicInfo;
import com.yjy.repository.mapper.FundbookMonthExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/6/29.
 */
@Service
public class OnlyMonth {
    private static Logger logger = LoggerFactory.getLogger(OnlyMonth.class);

    @Resource
    private JedisTemplate jedisTemplate;

    @Resource
    private UserBasicExtMapper userBasicExtMapper;

    @Resource
    private FundbookMonthExtMapper fundbookMonthExtMapper;

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");




    //分页更新上月最后一天的余额到redis,从日结表中查
    public void getByBookdete(final Fundbookmonth fundbookmonth,final List<Integer> userids) {
        final   Integer bookdate = fundbookmonth.getBookdate();
        final String tablename = FundConstant.FUNDBOOKMONTH_TABLE_NAME_PRE + StringUtils.substring(bookdate + "", 0, 6);
        final int pagesize = 8000;//一次取
        Pagination pagination = new Pagination(1, pagesize);
        List<Fundbookmonth> list = fundbookMonthExtMapper.loadMonthRrport(tablename, pagination, userids);
        logger.info(bookdate+" 总页数" + pagination.getPageCount());
        if (list != null && list.size() > 0) {
            ExecutorService executorService = Executors.newFixedThreadPool(30);
            final CountDownLatch countDownLatch = new CountDownLatch(pagination.getPageCount() - 1);
            for (int i = 2; i <= pagination.getPageCount(); i++) {
                final int j = i;

                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        Pagination pagination2 = new Pagination(j, pagesize);

                        List<Fundbookmonth> list = fundbookMonthExtMapper.loadMonthRrport(tablename, pagination2, userids);
                        cachePreMonthBalace(list);
                        countDownLatch.countDown();
                    }
                });
            }
            cachePreMonthBalace(list);
            try {
                countDownLatch.await();
                executorService.shutdown();
            } catch (Exception e) {
                logger.error("被打断",e);
            }

        }
        logger.info("缓存"+bookdate+"的余额执行完了");

    }


    private void cachePreMonthBalace(List<Fundbookmonth> list) {
        long start=System.currentTimeMillis();
        for (Fundbookmonth fundbookmonth : list) {
            String rediskey = RedisKey.MONTH_CACHE + fundbookmonth.getBookdate() + "|-" + fundbookmonth.getBookcode() + "|-" + fundbookmonth.getUserid();
            jedisTemplate.set(rediskey, fundbookmonth.getBalance().doubleValue() + "");
        }
        long end=System.currentTimeMillis();
        logger.info("数据量"+list.size()+" 时间" +(double)(end-start)/1000);
        list=null;
    }

    public int insertFundBookMonth(Date startDate, Date endDate, Map<Integer, List<Fundbookcode>> bookcodemap, List<UserBasicInfo> users) {

        long startRunTime = System.currentTimeMillis();

        while (endDate.compareTo(startDate) != -1) {
            String bookDateStr=DateTools.formate_yyyyMM(startDate);
            //本月最后一天
            String currentMonthLastDay = DateTools.getCurrentMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);
            //前一个月最后一天
            String preMonthLastDay = DateTools.getPreMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);

            List<Fundbookmonth> insertfundbookmonthList = new ArrayList<>();
            String monthTableName = FundConstant.FUNDBOOKMONTH_TABLE_NAME_PRE +bookDateStr;
            String fundmonthtemp = FundConstant.FUNDMONTHTEMP_TABLE_NAME_PRE +bookDateStr;
            if (users == null || users.size() <= 0) {
                fundbookMonthExtMapper.deleteAll(monthTableName);
            } else {
                fundbookMonthExtMapper.deleteFundbookMonth(null, users, monthTableName);
            }

            if (users != null && users.size() > 0) {

//                long startF=System.currentTimeMillis();
//                for (UserBasicInfo userBasicInfo : users) {
//                    List<Fundbookcode> bookcodes = bookcodemap.get(userBasicInfo.getTypeId());
//                    Map<String, SumMonthByBookcode> monthByBookcodeMap = new HashedMap();
//                    getSumMonthGroupBookcodeWhereUserid(dayTableName, userBasicInfo, monthByBookcodeMap);
//                    for (int i = 0; i <= (bookcodes.size() - 1); i++) {
//                        Fundbookcode bookcode = bookcodes.get(i);
//                        //日清表的sum数据
//                        SumMonthByBookcode sumMonthByBookcode = monthByBookcodeMap.get(bookcode.getBookcode());
//                        //3.3每个账本
//                        String balancekey = String.format("%s|-%s|-%s", currentMonthLastDay, bookcode.getBookcode(), userBasicInfo.getUserid());
//                        String balanceStr = jedisTemplate.get(balancekey);
//                        String prebalanceStr = null;
//                        if (!bookDateStr.equals("201309")) {//201309之前没有数据
//                            String prebalancekey = String.format("%s|-%s|-%s", preMonthLastDay, bookcode.getBookcode(), userBasicInfo.getUserid());
//                            prebalanceStr = jedisTemplate.get(prebalancekey);
//                            if (prebalanceStr != null) {
//                                jedisTemplate.del(prebalancekey);
//                            }
//                        }
//                        BigDecimal preBalance = null;
//                        BigDecimal balance = null;
//
//                        if (balanceStr != null) {
//                            balance = new BigDecimal(balanceStr);
//                        } else {
//                            balance = new BigDecimal(0);
//                        }
//
//                        if (prebalanceStr != null) {
//                            preBalance = new BigDecimal(prebalanceStr);
//                        } else {
//                            preBalance = new BigDecimal(0);
//                        }
//                        Fundbookmonth fundbookmonth = new Fundbookmonth();
//                        fundbookmonth.setUserid(userBasicInfo.getUserid());
//                        fundbookmonth.setBookdate(Integer.parseInt(bookDateStr));
//                        fundbookmonth.setBookcode(bookcode.getBookcode());
//                        fundbookmonth.setAreacode(0);
//                        fundbookmonth.setPrevbalance(preBalance);
//                        fundbookmonth.setBalance(balance);
//                        if (sumMonthByBookcode == null) {
//                            fundbookmonth.setHappendebit(new BigDecimal(0));
//                            fundbookmonth.setHappencredit(new BigDecimal(0));
//                        } else {
//
//                            fundbookmonth.setHappendebit(sumMonthByBookcode.getDebit());
//                            fundbookmonth.setHappencredit(sumMonthByBookcode.getCredit());
//                        }
//                        insertfundbookmonthList.add(fundbookmonth);
//
//                        if (insertfundbookmonthList.size() % 2000 == 0) {
//                            long memeoryRunTime = System.currentTimeMillis();
//                            logger.info("内存计算:"+(double)(memeoryRunTime-startF)/1000);
//                            startF= System.currentTimeMillis();
//                            fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
//                            long insertRunTime = System.currentTimeMillis();
//                            logger.info(" 账本总数" + bookcodes.size() + " 账本顺序" + i + " 插入完成账本用时" + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr + " " + bookcode.getBookcode());
//                            insertfundbookmonthList = new ArrayList<>();
//                        }
//                    }
//                }
//
//                if (insertfundbookmonthList.size() > 0) {
//
//                    long memeoryRunTime = System.currentTimeMillis();
//                    fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
//                    long insertRunTime = System.currentTimeMillis();
//                    logger.info("本月最后一天数据:" + insertfundbookmonthList.size() + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr);
//                }

            } else {
                //在临时表取数据
                List<Fundbookmonth> monthTempList = fundbookMonthExtMapper.findMonthTemp(null, fundmonthtemp);
                Map<String, Fundbookmonth> fundbookmonthMap = list2Map(monthTempList);
                for (Integer bookcodetype : bookcodemap.keySet()) {
                    List<Fundbookcode> bookcodes = bookcodemap.get(bookcodetype);//账本分类
                    long userCreateEndTime = 0;
                    Date endDateByTable = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, currentMonthLastDay + "23:59:59",logger);
                    //用户注册时间
                    userCreateEndTime = endDateByTable.getTime() / 1000l;
                    List<UserBasicInfo> users2 = userBasicExtMapper.getUsers(0, 0, bookcodetype, 0, userCreateEndTime);
                    for (int i = 0; i <= (bookcodes.size() - 1); i++) {
                        Fundbookcode bookcode = bookcodes.get(i);
                        //日清表的sum数据

                        for (int j = 0; j <= (users2.size() - 1); j++) {

                            UserBasicInfo userBasicInfo = users2.get(j);

                            //3.3每个账本

                            String prebalanceStr = null;
                            if (!bookDateStr.equals("201309")) {//201309之前没有数据
                                String prebalancekey = RedisKey.MONTH_CACHE+String.format("%s|-%s|-%s", StringUtils.substring(preMonthLastDay,0,6), bookcode.getBookcode(), userBasicInfo.getUserid());
                                prebalanceStr = jedisTemplate.get(prebalancekey);
//                                if (prebalanceStr != null) {
//                                    jedisTemplate.del(prebalancekey);
//                                }
                            }
                            BigDecimal preBalance = null;
                            BigDecimal balance = null;
                            Fundbookmonth currentFundbookMonth=fundbookmonthMap.get(list2MapKey(userBasicInfo.getUserid(),bookcode.getBookcode()));

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

                            if (currentFundbookMonth == null) {
                                fundbookmonth.setHappendebit(new BigDecimal(0));
                                fundbookmonth.setHappencredit(new BigDecimal(0));
                                balance=preBalance;
                            } else {
                                fundbookmonth.setHappendebit(currentFundbookMonth.getHappendebit());
                                fundbookmonth.setHappencredit(currentFundbookMonth.getHappendebit());
                                balance=currentFundbookMonth.getBalance();
                            }
                            fundbookmonth.setBalance(balance);
//                            jedisTemplate.set(RedisKey.MONTH_CACHE+String.format("%s|-%s|-%s", StringUtils.substring(bookDateStr,0,6), bookcode.getBookcode(), userBasicInfo.getUserid()),balance.doubleValue()+"");
                            insertfundbookmonthList.add(fundbookmonth);
                            if (insertfundbookmonthList.size() % 5000 == 0) {
                                long memeoryRunTime = System.currentTimeMillis();
                                fundbookMonthExtMapper.batchInsert(insertfundbookmonthList, monthTableName);
                                long insertRunTime = System.currentTimeMillis();
                                logger.info(" 账本总数" + bookcodes.size() + " 账本顺序" + i +  " 用户顺序 " + j + " 插入完成账本用时" + (float) (insertRunTime - memeoryRunTime) / 1000 + " " + bookDateStr);
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
            startDate = DateTools.getNextMonthsDate(startDate);//轮训到下一个月
        }
        long end = System.currentTimeMillis();
        logger.info("月结全部计算完了" + DateTools.formate_yyyyMMdd(endDate) + " 执行用时:" + (float) (end - startRunTime) / 1000 + "秒");
        //批量插入
        return 1;
    }

    private Map<String,Fundbookmonth> list2Map(List<Fundbookmonth> list){
        Map<String,Fundbookmonth> map=new HashedMap();
        for(Fundbookmonth fundbookmonth:list){
            map.put(list2MapKey(fundbookmonth.getUserid(),fundbookmonth.getBookcode()),fundbookmonth);
        }
        return map;
    }

    private String list2MapKey(int userid,String bookcode ){
        return userid+"-"+bookcode;

    }

}


