package com.yjy.service.fundbook.onlyMonth;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.dao.Pagination;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.common.entity.fundbook.Fundbook;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.Fundbookmonth;
import com.yjy.common.entity.fundbook.UserBasicInfo;
import com.yjy.repository.mapper.fundbook.FundbookExtMapper;
import com.yjy.repository.mapper.fundbook.FundbookMonthExtMapper;
import com.yjy.repository.mapper.fundbook.UserBasicExtMapper;
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
public class OnlyMonthService {

    private static Logger logger = LoggerFactory.getLogger(OnlyMonthService.class);

    @Resource
    private UserBasicExtMapper userBasicExtMapper;

    @Resource
    private FundbookMonthExtMapper fundbookMonthExtMapper;

    @Resource
    private FundbookExtMapper fundbookExtMapper;

    @Resource
    private JedisTemplate jedisTemplate;


    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");




    //从月结表中,分页更新上月最后一天的余额到redis
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
        logger.info("...........................");
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
                            String mapKey=list2MapKey(userBasicInfo.getUserid(),bookcode.getBookcode());
                            Fundbookmonth currentFundbookMonth=fundbookmonthMap.get(mapKey);

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
                                fundbookmonth.setHappencredit(currentFundbookMonth.getHappencredit());
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

    //按月刷余额
    public void oneByOneUpdateBalance(Date startDate, final Date endDate, final List<Fundbookcode> bookcodes,  List<Integer> userids) {

        //轮训每一个月

        while (!(endDate.compareTo(startDate) == -1)) {
            //前一个月最后一天
            final String preMonthLastDay = DateTools.getPreMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);
            final String startStr_yyyyMM = simpleDateFormat_yyyyMM.format(startDate);
            final String tableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + startStr_yyyyMM;
            List<Integer> selectUserids = null;
            if (userids != null&&userids.size()!=0) {
                selectUserids = userids;
            } else {
                //发生数据的用户
                selectUserids = fundbookExtMapper.selectUserids(tableName);
            }
            final int dataSize = selectUserids.size(); //01,23,4;
            final int pageSize = dataSize/20;
            final int cacheThreadCount = (dataSize / pageSize) + 1;
            logger.info("总用户:" + dataSize + " 刷余额" + startStr_yyyyMM + " " + cacheThreadCount + "页");
            final CountDownLatch countDownLatch = new CountDownLatch(cacheThreadCount);
            ExecutorService executorService = Executors.newFixedThreadPool(cacheThreadCount);
            for (int j = 1; j <= cacheThreadCount; j++) {
                final int jm = j;
                final List<Integer> selectUseridss = selectUserids;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Fundbook> insertFunbooks = new ArrayList<>();
                        for (int i = (jm - 1) * pageSize; !(i > (jm * pageSize - 1) || i > (dataSize - 1)); i++) {
                            int userid = selectUseridss.get(i);
                            //当期有发生数据的账本
                            List<String> bookcodeList = fundbookExtMapper.selectBookcodes(tableName, userid);

                            for (String bookcode : bookcodeList) {
                                if (bookcodes != null && bookcodes.size() > 0 && !isContains(bookcodes, bookcode))//如果不要求刷的 就不用刷了
                                    continue;

//                                Fundbookday fundbookday = new Fundbookday(); //查询上一个月的(查询条件)
//                                fundbookday.setBookcode(bookcode);
//                                fundbookday.setUserid(userid);
                                //前一个月 这个用户 这个账本 最后一条数据
                                String prebalanceStr = null;
                                if (!startStr_yyyyMM.equals("201309")) {//201309之前没有数据
                                    String prebalancekey = RedisKey.MONTH_CACHE+String.format("%s|-%s|-%s", StringUtils.substring(preMonthLastDay,0,6), bookcode, userid);
                                    prebalanceStr = jedisTemplate.get(prebalancekey);
                                }
                                BigDecimal firstPreBalance = new BigDecimal("0");
                                Fundbook preFundbook = new Fundbook();
                                if (prebalanceStr != null) {
                                    firstPreBalance = new BigDecimal(prebalanceStr);
                                }
                                preFundbook.setBalance(firstPreBalance);

                                //取当前用户账本中的数据
                                Fundbook fundbook = new Fundbook(); // (查询条件)
                                fundbook.setBookcode(bookcode);
                                fundbook.setUserid(userid);
                                List<Fundbook> fundbooks = fundbookExtMapper.selectByExample(fundbook, tableName, 0, 0, false);

                                //轮训账本表中每一条数据
                                if (fundbooks != null && fundbooks.size() > 0) {
                                    for (int m = 0; m < fundbooks.size(); m++) {
                                        Fundbook iFundbook = fundbooks.get(m);
                                        BigDecimal preBalance = preFundbook.getBalance();
                                        if (Integer.parseInt(StringUtils.substring(bookcode, 0, 4)) == FundConstant.FUND_TYPE_DEBT) {
                                            //负债类公式: 上期贷余 + 本期发生贷 - 本期发生借 = 本期贷余
                                            BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                                            BigDecimal debit = iFundbook.getDebit();  //当期发生借
                                            BigDecimal iBalance = preBalance.add(credit).subtract(debit);
                                            iFundbook.setBalance(iBalance);
                                        } else {
                                            BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                                            BigDecimal debit = iFundbook.getDebit();  //当期发生借
                                            //资产类公式和损益类: 上期借余 + 本期发生借 - 本期发生贷 = 本期借余
                                            BigDecimal iBalance = preBalance.add(debit).subtract(credit);
                                            iFundbook.setBalance(iBalance);
                                        }
                                        insertFunbooks.add(iFundbook);
                                        if (insertFunbooks.size() % 10000 == 0) {
                                            long startupdate=System.currentTimeMillis();
                                            fundbookExtMapper.batchUpdateByPrimaryKeySelective(insertFunbooks, tableName);
                                            long endupdate=System.currentTimeMillis();
                                            logger.info(jm+" "+(double)(endupdate-startupdate)/1000 +  " 更新完成一次");
                                            insertFunbooks = new ArrayList<>();

                                        }
                                        //轮训下一个条
                                        preFundbook = iFundbook;
                                    }
                                }

                            }

                        }
                        if(insertFunbooks.size()>0){
                            logger.info(jm + " 本线程最后一次更新余额" + insertFunbooks.size());
                            fundbookExtMapper.batchUpdateByPrimaryKeySelective(insertFunbooks, tableName);
                        }
                        insertFunbooks = null;
                        countDownLatch.countDown();
                    }
                });
            }
            try {

                countDownLatch.await();
                executorService.shutdown();
            } catch (Exception e) {
                logger.error("刷新余额", e);
            }
            logger.info(tableName + "本月余额刷完了");

            startDate = DateTools.getNextMonthsDate(startDate);

        }
    }


    //按天刷余额
    public void oneByOneUpdateBalanceByDay(String jobDatesrt,List<Integer> userids) {

        final Date jobStartDate= DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, jobDatesrt + "00:00:00", logger);
        final Date jobEndDate= DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, jobDatesrt + "23:59:59", logger);
        Date jobPreDayDate = DateTools.getPreDayDate(jobStartDate);
        final String jobPreDateStr=DateTools.formate_yyyyMMdd(jobPreDayDate);
        //前一个月最后一天

            final String fundbookTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + StringUtils.substring(jobDatesrt,0,6);
            List<Integer> selectUserids = null;
            if (userids != null&&userids.size()!=0) {
                selectUserids = userids;
            } else {
                //发生数据的用户
                selectUserids = fundbookExtMapper.selectUserids(fundbookTableName);
            }
            final int dataSize = selectUserids.size(); //01,23,4;
            final int pageSize = dataSize/20;
            final int cacheThreadCount = (dataSize / pageSize) + 1;
            logger.info("总用户:" + dataSize + " 刷余额" + jobDatesrt + " " + cacheThreadCount + "页");
            final CountDownLatch countDownLatch = new CountDownLatch(cacheThreadCount);
            ExecutorService executorService = Executors.newFixedThreadPool(cacheThreadCount);
            for (int j = 1; j <= cacheThreadCount; j++) {
                final int jm = j;
                final List<Integer> selectUseridss = selectUserids;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Fundbook> insertFunbooks = new ArrayList<>();
                        for (int i = (jm - 1) * pageSize; !(i > (jm * pageSize - 1) || i > (dataSize - 1)); i++) {
                            int userid = selectUseridss.get(i);
                            //当期有发生数据的账本
                            List<String> bookcodeList = fundbookExtMapper.selectBookcodes(fundbookTableName, userid);

                            for (String bookcode : bookcodeList) {

//                                Fundbookday fundbookday = new Fundbookday(); //查询上一个月的(查询条件)
//                                fundbookday.setBookcode(bookcode);
//                                fundbookday.setUserid(userid);
                                //前一个月 这个用户 这个账本 最后一条数据
                                String prebalanceStr = null;
                                String prebalancekey = String.format("%s|-%s|-%s", jobPreDateStr, bookcode, userid);

                                prebalanceStr = jedisTemplate.get(prebalancekey);
                                BigDecimal firstPreBalance = new BigDecimal("0");
                                Fundbook preFundbook = new Fundbook();
                                if (prebalanceStr != null) {
                                    firstPreBalance = new BigDecimal(prebalanceStr);
                                }
                                preFundbook.setBalance(firstPreBalance);

                                //取当前用户账本中的数据
                                Fundbook fundbook = new Fundbook(); // (查询条件)
                                fundbook.setBookcode(bookcode);
                                fundbook.setUserid(userid);
                                List<Fundbook> fundbooks = fundbookExtMapper.selectByExample(fundbook, fundbookTableName, jobStartDate.getTime()/1000l, jobEndDate.getTime()/1000l, false);

                                //轮训账本表中每一条数据
                                if (fundbooks != null && fundbooks.size() > 0) {
                                    for (int m = 0; m < fundbooks.size(); m++) {
                                        Fundbook iFundbook = fundbooks.get(m);
                                        BigDecimal preBalance = preFundbook.getBalance();
                                        if (Integer.parseInt(StringUtils.substring(bookcode, 0, 4)) == FundConstant.FUND_TYPE_DEBT) {
                                            //负债类公式: 上期贷余 + 本期发生贷 - 本期发生借 = 本期贷余
                                            BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                                            BigDecimal debit = iFundbook.getDebit();  //当期发生借
                                            BigDecimal iBalance = preBalance.add(credit).subtract(debit);
                                            iFundbook.setBalance(iBalance);
                                        } else {
                                            BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                                            BigDecimal debit = iFundbook.getDebit();  //当期发生借
                                            //资产类公式和损益类: 上期借余 + 本期发生借 - 本期发生贷 = 本期借余
                                            BigDecimal iBalance = preBalance.add(debit).subtract(credit);
                                            iFundbook.setBalance(iBalance);
                                        }
                                        insertFunbooks.add(iFundbook);
                                        if (insertFunbooks.size() % 10000 == 0) {
                                            long startupdate=System.currentTimeMillis();
                                            fundbookExtMapper.batchUpdateByPrimaryKeySelective(insertFunbooks, fundbookTableName);
                                            long endupdate=System.currentTimeMillis();
                                            logger.info(jm+" "+(double)(endupdate-startupdate)/1000 +  " 更新完成一次");
                                            insertFunbooks = new ArrayList<>();

                                        }
                                        //轮训下一个条
                                        preFundbook = iFundbook;
                                    }
                                }

                            }

                        }
                        if(insertFunbooks.size()>0){
                            logger.info(jm + " 本线程最后一次更新余额" + insertFunbooks.size());
                            fundbookExtMapper.batchUpdateByPrimaryKeySelective(insertFunbooks, fundbookTableName);
                        }
                        insertFunbooks = null;
                        countDownLatch.countDown();
                    }
                });
            }
            try {

                countDownLatch.await();
                executorService.shutdown();
            } catch (Exception e) {
                logger.error("刷新余额", e);
            }
            logger.info(fundbookTableName + "本月余额刷完了");
    }

    private boolean isContains(List<Fundbookcode> bookcodes, String bookCode) {
        for (Fundbookcode s : bookcodes) {
            if (s.getBookcode().equals(bookCode))
                return true;
        }
        return false;
    }

    //创建月结临时表通过fundbook表
    public void createFundmonthtemp(String fundmonthtemptableName,String fundbooktableName){
        fundbookMonthExtMapper.createFundmonthtemp(fundmonthtemptableName,fundbooktableName);
    }


}


