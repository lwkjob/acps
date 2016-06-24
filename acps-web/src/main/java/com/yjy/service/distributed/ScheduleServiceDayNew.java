package com.yjy.service.distributed;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.common.utils.JsonUtils;
import com.yjy.common.zk.DayNodeCacheListener;
import com.yjy.common.zk.ZkTemplate;
import com.yjy.entity.*;
import com.yjy.repository.mapper.FundbookExtMapper;
import com.yjy.repository.mapper.FundbookdayExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import com.yjy.service.*;
import com.yjy.web.vo.JedisVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/6/15.
 */
@Service("scheduleServiceDayNew")
public class ScheduleServiceDayNew {
    @Resource
    private FundbookService fundbookService;

    @Resource
    private FundbookDayService fundbookDayService;

    @Resource
    private FundbookMonthService fundbookMonthService;

    @Resource
    private FundbookcodeService fundbookcodeService;


    @Resource
    private UserBasicExtMapper userBasicExtMapper;

    @Resource
    private FundbookExtMapper fundbookExtMapper;

    @Resource
    private FundbookdayExtMapper fundbookdayExtMapper;

    @Resource
    private JedisTemplate jedisTemplate;

    @Resource
    private ZkTemplate zkTemplate;

    private static Calendar calendar = Calendar.getInstance();

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceDayNew.class);


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


    //分布式日清
    public void dayReportSchedule(String start, String end, final Map<Integer, List<Fundbookcode>> bookcodemap) {
        try {
            long startRunTime = System.currentTimeMillis();
            //1.1根据时间区间算出所有需要删数据的表名
            Date startDate = DateTools.parseDateFromString_yyyyMMdd(start, logger);

            Date endDate = DateTools.parseDateFromString_yyyyMMdd(end, logger);

            Map<String, DelTableName> deleteTableNameMap = DateTools.getDeleteTableName(startDate, endDate, logger);

            for (String key : deleteTableNameMap.keySet()) {
                long startRunTimes = System.currentTimeMillis();
                //每个月
                DelTableName delTableName = deleteTableNameMap.get(key);
                String fundbookDayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + delTableName.getTableNameSuffix();
                Date startDateByTable = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr(), logger);
                Date endDateByTable = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, delTableName.getEndStr() + "23:59:59", logger);
                while (endDateByTable.compareTo(startDateByTable) != -1) {
                    //每天
                    String bookDateStr = DateTools.formate_yyyyMMdd(startDateByTable);
                    //今天的活跃用户
                    String currentDayStr = DateTools.formate_yyyyMMdd(startDateByTable);
                    Date createEndTime = DateTools.parseDateFormat_yyyyMMddHHmmss(currentDayStr + "23:59:59", logger);
                    //  今天的活跃用户数
                    final List<UserBasicInfo> userListObject = userBasicExtMapper.getUsers(0, 0, 0, 0, createEndTime.getTime() / 1000l);
                    final int dataSize = userListObject.size(); //01,23,4;
                    final int pageSize = 2000;
                    final int cacheThreadCount = (dataSize / pageSize) + 1;
                    final CountDownLatch countDownLatch = new CountDownLatch(cacheThreadCount);
                    ExecutorService executorService = Executors.newFixedThreadPool(3);
                    logger.info(cacheThreadCount + "页");
                     Date preDate = DateTools.getPreDayDate(startDateByTable);
                    String preDateStr = simpleDateFormat_yyyyMMdd.format(preDate);
                    for (int j = 1; j <= cacheThreadCount; j++) {

                        List<UserBasicInfo> tempUserList = new ArrayList<>();
                        for (int i = (j - 1) * pageSize; !(i > (j * pageSize - 1) || i > (dataSize - 1)); i++) {
                            tempUserList.add(userListObject.get(i));
                        }
                        final Map<String, Fundbookday> fundbookdayMap = getStringFundbookdayMapUsers(delTableName.getTableNameSuffix(), startDateByTable, createEndTime,tempUserList);
                        //多线程刷当天的余额到redis
                        cacheBalance(tempUserList, bookcodemap, preDateStr, bookDateStr, fundbookdayMap);
                        FundbookdayRunner fundbookdayRunner = new FundbookdayRunner();
                        fundbookdayRunner.setCountDownLatch(countDownLatch);
                        fundbookdayRunner.setUsers(tempUserList);
                        fundbookdayRunner.setBookcodemap(bookcodemap);
                        fundbookdayRunner.setBookDate(startDateByTable);
                        fundbookdayRunner.setFundbookdayExtMapper(fundbookdayExtMapper);
                        fundbookdayRunner.setFundbookdayMap(fundbookdayMap);
                        fundbookdayRunner.setBookDateStr(bookDateStr);
                        fundbookdayRunner.setPreDateStr(preDateStr);
                        fundbookdayRunner.setFundbookDayTableName(fundbookDayTableName);
                        fundbookdayRunner.setJedisTemplate(jedisTemplate);
                        executorService.execute(fundbookdayRunner);

                        logger.info(j + "跑完一次4000");

                    }
                    countDownLatch.await();
                    executorService.shutdown();
                    startDateByTable = DateTools.getNextDayDate(startDateByTable);
                }
                long endRunTime = System.currentTimeMillis();
                logger.info((double) (endRunTime - startRunTimes) / 1000 + "秒任务全部跑完了," + delTableName.getTableNameSuffix());
            }

            long endRunTime = System.currentTimeMillis();
            logger.info((double) (endRunTime - startRunTime) / 1000 + "秒任务全部跑完了");
        } catch (Exception e) {
            logger.error("报错了异常被吃了继续监听", e);
        }

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
        logger.info(bookDateStr + "Cache刷完了" + dataSize + "数据量，" + (double) (cacheEnd - cacheStart) / 1000 + " " + preDateStr);
    }

    //创建任务
    public void scheduleCreate(String start, String end) {
        try {
            long startRunTime = System.currentTimeMillis();
            //1.1根据时间区间算出所有需要删数据的表名
            Date startDate = DateTools.parseDateFromString_yyyyMMdd(start, logger);

            Date endDate = DateTools.parseDateFromString_yyyyMMdd(end, logger);

            Map<String, DelTableName> deleteTableNameMap = DateTools.getDeleteTableName(startDate, endDate, logger);

            for (String key : deleteTableNameMap.keySet()) {
                long startRunTimes = System.currentTimeMillis();
                //每个月
                DelTableName delTableName = deleteTableNameMap.get(key);
                Date startDateByTable = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr(), logger);
                Date endDateByTable = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, delTableName.getEndStr() + "23:59:59", logger);
                while (endDateByTable.compareTo(startDateByTable) != -1) {
                    //每天
                    String bookDateStr = DateTools.formate_yyyyMMdd(startDateByTable);
                    //今天的活跃用户
                    String currentDayStr = DateTools.formate_yyyyMMdd(startDateByTable);
                    Date createEndTime = DateTools.parseDateFormat_yyyyMMddHHmmss(currentDayStr + "23:59:59", logger);
                    //  今天的活跃用户数
                    final List<UserBasicInfo> userListObject = userBasicExtMapper.getUsers(0, 0, 0, 0, createEndTime.getTime() / 1000l);
                    final int dataSize = userListObject.size(); //01,23,4;
                    final int pageSize = 2000;
                    final int cacheThreadCount = (dataSize / pageSize) + 1;

                    logger.info(cacheThreadCount + "页");
                    for (int j = 1; j <= cacheThreadCount; j++) {

                        List<UserBasicInfo> tempUserList = new ArrayList<>();
                        for (int i = (j - 1) * pageSize; !(i > (j * pageSize - 1) || i > (dataSize - 1)); i++) {
                            tempUserList.add(userListObject.get(i));
                        }
                        jedisTemplate.rpush(RedisKey.REPORT_OF_DAY_QUEUE+bookDateStr, JsonUtils.toJson(tempUserList));
                    }
                    startDateByTable = DateTools.getNextDayDate(startDateByTable);
                }
                long endRunTime = System.currentTimeMillis();
                logger.info((double) (endRunTime - startRunTimes) / 1000 + "秒任务全部跑完了," + delTableName.getTableNameSuffix());
            }

            long endRunTime = System.currentTimeMillis();
            logger.info((double) (endRunTime - startRunTime) / 1000 + "秒任务全部跑完了");
        } catch (Exception e) {
            logger.error("报错了异常被吃了继续监听", e);
        }
    }

    //分配任务
    public void scheduleDispatcher(String bookdateStr){
        String userJson=  jedisTemplate.lpop(RedisKey.REPORT_OF_DAY_QUEUE + bookdateStr);
        List<UserBasicInfo> tempUserList=JsonUtils.readToList(userJson,UserBasicInfo.class);
         //多线程刷当天的余额到redis

    }
}
