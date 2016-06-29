package com.yjy.service;


import com.yjy.common.dao.Pagination;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.common.constant.FundConstant;
import com.yjy.entity.*;
import com.yjy.repository.mapper.FundbookExtMapper;
import com.yjy.repository.mapper.FundbookdayExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import com.yjy.service.vo.JedisVo;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @Resource
    private UserBasicExtMapper userBasicExtMapper;

    @Resource
    private JedisTemplate jedisTemplate;

    private static Logger logger = LoggerFactory.getLogger(FundbookDayService.class);

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");


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
    public int insertFundBookDay(Date startDate, Date endDate, Map<Integer, List<Fundbookcode>> bookcodemap, List<UserBasicInfo> users) {


//        if(bookcodes==null || bookcodes.size()==0){
//            bookcodes=fundbookcodeMapper.selectByExample(null);
//        }
        //delete日清表指定时间之前的数据

        long start = System.currentTimeMillis();

        //1.1根据时间区间算出所有需要删数据的表名
        Map<String, DelTableName> deleteTableNameMap = DateTools.getDeleteTableName(startDate, endDate, logger);


        for (String key : deleteTableNameMap.keySet()) {
            //每个月
            DelTableName delTableName = deleteTableNameMap.get(key);

            String fundbookDayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + delTableName.getTableNameSuffix();

            if(users==null||users.size()<=0){
                //1.2删除需要统计的数据
                fundbookdayExtMapper.deleteAll(fundbookDayTableName);
            }else {
                fundbookdayExtMapper.deleteFundbookDay(null,users,fundbookDayTableName,0,0);
            }

            //3 每个表，每个用户，每天，每个账本一条数据
            //3.2每个用户
            Date startDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr());
            Date endDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMddhhmmss, delTableName.getEndStr() + "23:59:59");

            ExecutorService executorService = Executors.newFixedThreadPool(31);
            Integer d1=Integer.parseInt(delTableName.getStartStr());
            Integer d2=Integer.parseInt(delTableName.getEndStr());
            int threadCount=(d2-d1)+1;
            CountDownLatch countDownLatch=new CountDownLatch(threadCount);
            long startMonthTime = System.currentTimeMillis();
            while (endDateByTable.compareTo(startDateByTable) != -1) {//1.每天

                String bookDateStr = simpleDateFormat_yyyyMMdd.format(startDateByTable);
                Date createEndTime = parseDateFromStr(simpleDateFormat_yyyyMMddhhmmss, bookDateStr + "23:59:59");
                //今天活跃数据
                Map<String, Fundbookday> fundbookdayMap =null;
                //  今天的活跃用户数
                List<UserBasicInfo> userOfMonthList = null;
                if(users==null||users.size()==0){
                    fundbookdayMap = getStringFundbookdayMap(delTableName.getTableNameSuffix(), startDateByTable, createEndTime);

                    userOfMonthList = jedisTemplate.getListObject(RedisKey.USERS_OF_DAY + bookDateStr, UserBasicInfo.class);

                }else {
                    fundbookdayMap = getStringFundbookdayMapUsers(delTableName.getTableNameSuffix(), startDateByTable, createEndTime,users);

                    userOfMonthList=users;
                }


                Date preDate = getPreDayDate(startDateByTable);
                String preDateStr = simpleDateFormat_yyyyMMdd.format(preDate);

                //多线程刷当天的余额到redis
                cacheBalance(userOfMonthList, bookcodemap, preDateStr, bookDateStr, fundbookdayMap);

                FundbookdayRunner fundbookdayRunner = new FundbookdayRunner();
                fundbookdayRunner.setCountDownLatch(countDownLatch);
                fundbookdayRunner.setUsers(userOfMonthList);
                fundbookdayRunner.setBookcodemap(bookcodemap);
                fundbookdayRunner.setBookDate(startDateByTable);
                fundbookdayRunner.setFundbookdayExtMapper(fundbookdayExtMapper);
                fundbookdayRunner.setFundbookdayMap(fundbookdayMap);
                fundbookdayRunner.setBookDateStr(bookDateStr);
                fundbookdayRunner.setPreDateStr(preDateStr);
                fundbookdayRunner.setFundbookDayTableName(fundbookDayTableName);
                fundbookdayRunner.setJedisTemplate(jedisTemplate);
                executorService.execute(fundbookdayRunner);
                startDateByTable = getNextDayDate(startDateByTable);
            }
            try {
                countDownLatch.await();
                executorService.shutdown();
                long end = System.currentTimeMillis();
                logger.info(fundbookDayTableName+"日清跑完了"+ (float) (end - startMonthTime) / 1000 + "秒");
            }catch (Exception e){
                logger.error("日结报错了"+fundbookDayTableName,e);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("计算完了" + (float) (end - start) / 1000 + "秒");
        return 1;
    }


    //查询区间内全部账本数据
    public List<Fundbook> getFundbooksUsers(String tableName, long startTime, long endTime,List<UserBasicInfo> users) {
        return fundbookExtMapper.selectByExampleInUserids( tableName, startTime,endTime, users);
    }
    private Map<String, Fundbookday> getStringFundbookdayMapUsers(String table_yyyymm, Date startDateByTable, Date endDateByTable,List<UserBasicInfo> users) {
        Map<String, Fundbookday> fundbookdayMap;//2 统计每天每个用户每个账本数据
//        Fundbook fundbookExample = new Fundbook(); //查询条件
        String fundbookTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + table_yyyymm;

        List<Fundbook> fundbooks = getFundbooksUsers(
                fundbookTableName,
                startDateByTable.getTime() / 1000l,
                endDateByTable.getTime() / 1000l, users);

        //当期月每个用户每个账本每天的业务发生
        fundbookdayMap = getFundbookDay(fundbooks);
        return fundbookdayMap;
    }
    private Map<String, Fundbookday> getStringFundbookdayMap(String table_yyyymm, Date startDateByTable, Date endDateByTable) {
        Map<String, Fundbookday> fundbookdayMap;//2 统计每天每个用户每个账本数据
        Fundbook fundbookExample = new Fundbook(); //查询条件
        String fundbookTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + table_yyyymm;

        List<Fundbook> fundbooks = getFundbooks(
                fundbookExample,
                fundbookTableName,
                startDateByTable.getTime() / 1000l,
                endDateByTable.getTime() / 1000l);

        //当期月每个用户每个账本每天的业务发生
        fundbookdayMap = getFundbookDay(fundbooks);
        return fundbookdayMap;
    }


    //刷每天的余额到redis
    private void cacheBalance(final List<UserBasicInfo> userOfMonthList,
                              final Map<Integer, List<Fundbookcode>> bookcodemap,
                              final String preDateStr,
                              final String bookDateStr,
                              final Map<String, Fundbookday> fundbookdayMap) {

        long cacheStart = System.currentTimeMillis();

        //今天的所有用户分多线程刷余额到redis
        final int dataSize = userOfMonthList.size();
        final int pageSize = 1000;
        final int cacheThreadCount = (dataSize / pageSize) + 1;
        final CountDownLatch countDownLatch = new CountDownLatch(cacheThreadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(cacheThreadCount);
        for (int j = 1; j <= cacheThreadCount; j++) {
            final int jm = j;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    List<JedisVo> setJedisVos = new ArrayList<JedisVo>();
                    for (int i = (jm - 1) * pageSize; !(i > (jm * pageSize - 1) || i > (dataSize - 1)); i++) {
                        UserBasicInfo userBasicInfo = userOfMonthList.get(i);
                        //当前用户需要写的账本
                        List<Fundbookcode> bookcodesssss = bookcodemap.get(userBasicInfo.getTypeId());
                        for (Fundbookcode fundbookcode : bookcodesssss) {
                            String jedskey = String.format("%s|-%s|-%s", bookDateStr, fundbookcode.getBookcode(), userBasicInfo.getUserid());
                            String jedsPrekey = String.format("%s|-%s|-%s", preDateStr, fundbookcode.getBookcode(), userBasicInfo.getUserid());
                            String jedsValue = "0.0";
                            //今天的发生数据
                            Fundbookday fundbookdaysss = fundbookdayMap.get(jedskey);
                            if (fundbookdaysss != null) {
                                jedsValue = fundbookdaysss.getBalance().doubleValue() + "";
                            } else {
                                String preBalanceStr = jedisTemplate.get(jedsPrekey);
                                jedsValue = preBalanceStr==null?"0.0":preBalanceStr;
                            }
//                            jedisTemplate.set(jedskey,jedsValue);
                            JedisVo jedisVo = new JedisVo(jedskey, jedsValue);
                            setJedisVos.add(jedisVo);

                            if (setJedisVos.size() % 8000 == 0) {
                                long cacheStart = System.currentTimeMillis();
                                jedisTemplate.pipset(setJedisVos);

                                long cacheEnd = System.currentTimeMillis();
                                logger.info(bookDateStr + "批量set" +setJedisVos.size()+"数据量，"+ (double) (cacheEnd - cacheStart) / 1000 + " " + preDateStr);
                                setJedisVos=new ArrayList<JedisVo>();
                            }
                        }
                    }
                    if (setJedisVos.size()!= 0) {
                        jedisTemplate.pipset(setJedisVos);
                        logger.info(bookDateStr + "批量set最后一次" + setJedisVos.size() + "数据量" + preDateStr);
                    }
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (Exception e) {
            logger.error("线程被意外中断");
        }

        long cacheEnd = System.currentTimeMillis();
        logger.info(bookDateStr + "Cache刷完了" +dataSize+"数据量，"+ (double) (cacheEnd - cacheStart) / 1000 + " " + preDateStr);
    }


    //更新日清表每天的余
    public void updateDayOfBalance(final Map<Integer, List<Fundbookcode>> bookcodemap,
                                   final String startDateStr,
                                   final String endDateStr) {
        logger.info("开始刷余 " + startDateStr);
        Date startDate = DateTools.parseDateFromString_yyyyMMdd(startDateStr, logger);
        Date endDate = DateTools.parseDateFromString_yyyyMMdd(endDateStr, logger);
        Map<String, DelTableName> deleteTableNameMap = DateTools.getDeleteTableName(startDate, endDate, logger);



        for (String key : deleteTableNameMap.keySet()) {
            //每个月
            DelTableName delTableName = deleteTableNameMap.get(key);

            String fundbookDayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + delTableName.getTableNameSuffix();

            //3 每个表，每个用户，每天，每个账本一条数据
            //3.2每个用户
            Date startDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr());
            Date endDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMddhhmmss, delTableName.getEndStr() + "23:59:59");

            while (endDateByTable.compareTo(startDateByTable) != -1) {//1.每天
                String bookDateStr = simpleDateFormat_yyyyMMdd.format(startDateByTable);
                Date createEndTime = parseDateFromStr(simpleDateFormat_yyyyMMddhhmmss, bookDateStr + "23:59:59");

                Date preDate = getPreDayDate(startDateByTable);
                String preDateStr = simpleDateFormat_yyyyMMdd.format(preDate);

                //今天活跃数据
                Map<String, Fundbookday> fundbookdayMap = getStringFundbookdayMap(delTableName.getTableNameSuffix(), startDateByTable, createEndTime);
                //  今天的活跃用户数
                List<UserBasicInfo> userOfMonthList = null;
                userOfMonthList = jedisTemplate.getListObject(RedisKey.USERS_OF_DAY + bookDateStr, UserBasicInfo.class);
                if (userOfMonthList == null) {
                    userOfMonthList = userBasicExtMapper.getUsers(0, 0, 0, 0, createEndTime.getTime() / 1000l);
                }
                final int dataSize = userOfMonthList.size();
                final int pageSize = 800;
                final int cacheThreadCount = (dataSize / pageSize) + 1;
                for (int j = 1; j <= cacheThreadCount; j++) {
                    for (int i = (j - 1) * pageSize; !(i > (j * pageSize - 1) || i > (dataSize - 1)); i++) {
                        UserBasicInfo userBasicInfo = userOfMonthList.get(i);
                        List<Fundbookcode> bookcodesssss = bookcodemap.get(userBasicInfo.getTypeId());
                        for (Fundbookcode fundbookcode : bookcodesssss) {
                            String jedskey = String.format("%s|-%s|-%s", bookDateStr, fundbookcode.getBookcode(), userBasicInfo.getUserid());
                            String jedsPrekey = String.format("%s|-%s|-%s", preDateStr, fundbookcode.getBookcode(), userBasicInfo.getUserid());
                            Fundbookday fundbookdaysss = fundbookdayMap.get(jedskey);
                            if (fundbookdaysss != null) {
                                jedisTemplate.set(jedskey, fundbookdaysss.getBalance().doubleValue() + "");
                            } else {
                                String preBalanceStr = jedisTemplate.get(jedsPrekey);
                                BigDecimal preBalance = null;
                                if (preBalanceStr == null) {
                                    preBalance = new BigDecimal(0);
                                } else {
                                    preBalance = new BigDecimal(preBalanceStr);
                                }
                                jedisTemplate.set(jedskey, preBalance.doubleValue() + "");
                            }
                        }
                    }
                }
                startDateByTable = getNextDayDate(startDateByTable);
            }
        }


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



    //查询区间内全部账本数据
    public List<Fundbook> getFundbooks(Fundbook fundbook, String tableName, long startTime, long endTime) {
        return fundbookExtMapper.selectByExample(fundbook, tableName, startTime, endTime, false);
    }


    /**
     * 统计每天的发生数据
     * 计算范围时间内:每天-每个账本-每个用户 的sum(借),sum(贷),余
     * 取出来的数据必须是按照按照用户和发生时间排好序
     */
    public Map<String, Fundbookday> getFundbookDay(List<Fundbook> fundbooks) {
        Map<String, Fundbookday> map = new HashedMap();
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
            if (fundbookdays != null && fundbookdays.size() > 0) {
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

    //分页更新上月最后一天的余额到redis,从日结表中查
    public void getByBookdete(final Fundbookday fundbookday,final List<Integer> userids) {
        final   Integer bookdate = fundbookday.getBookdate();
        final String tablename = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + StringUtils.substring(bookdate + "", 0, 6);
        final int pagesize = 8000;//一次取
        Pagination pagination = new Pagination(1, pagesize);
        List<Fundbookday> list = fundbookdayExtMapper.selectByExample(fundbookday, tablename, pagination, userids);
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

                        List<Fundbookday> list = fundbookdayExtMapper.selectByExample(fundbookday, tablename, pagination2,userids );
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




    private void cachePreMonthBalace(List<Fundbookday> list) {
        long start=System.currentTimeMillis();
        for (Fundbookday fundbookday1 : list) {
            String rediskey = fundbookday1.getBookdate() + "|-" + fundbookday1.getBookcode() + "|-" + fundbookday1.getUserid();
            jedisTemplate.set(rediskey, fundbookday1.getBalance().doubleValue() + "");
        }
        long end=System.currentTimeMillis();
        logger.info("数据量"+list.size()+" 时间" +(double)(end-start)/1000);
        list=null;
    }
}
