package com.yjy.service.distributed;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.common.utils.JsonUtils;
import com.yjy.common.zk.ZkTemplate;
import com.yjy.entity.*;
import com.yjy.repository.mapper.FundbookExtMapper;
import com.yjy.repository.mapper.FundbookdayExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import com.yjy.service.FundbookcodeService;
import com.yjy.service.vo.JedisVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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


    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.timeout}")
    private int timeout;
    @Value("${redis.threadCount}")
    private int threadCount;
    @Value("${redis.password}")
    private String password;
    @Value("${redis.database}")
    private int database;


    private static Calendar calendar = Calendar.getInstance();

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceDayNew.class);


    //查询区间内全部账本数据
    public List<Fundbook> getFundbooksUsers(String tableName, long startTime, long endTime, List<UserBasicInfo> users) {
        return fundbookExtMapper.selectByExampleInUserids(tableName, startTime, endTime, users);
    }

    private Map<String, Fundbookday> getStringFundbookdayMapUsers(String table_yyyymm, Date startDateByTable, Date endDateByTable, List<UserBasicInfo> users) {
        Map<String, Fundbookday> fundbookdayMap;//2 统计每天每个用户每个账本数据
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

    //缓存账本到内存中
    private Map<Integer, List<Fundbookcode>> cacheFndbookcode() {
        Map<Integer, List<Fundbookcode>> map = new HashedMap();
        FundbookcodeExample example = new FundbookcodeExample();
        example.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_BUYER);
        map.put(FundConstant.TYPEID_BUYER, fundbookcodeService.getFundbookcodesByExample(example));


        FundbookcodeExample example2 = new FundbookcodeExample();
        example2.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_SALES);
        map.put(FundConstant.TYPEID_SALES, fundbookcodeService.getFundbookcodesByExample(example2));

        FundbookcodeExample example3 = new FundbookcodeExample();
        example3.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_PLATFORM);
        map.put(FundConstant.TYPEID_PLATFORM, fundbookcodeService.getFundbookcodesByExample(example3));

        return map;
    }

    //分布式执行日清任务
    public void dayReportSchedule(String start, List<UserBasicInfo> users) {
        logger.info("领到任务 开始搞" + start+"-"+users.get(0).getUserid()+"-"+users.get(users.size()-1).getUserid());
        long startRunTime = System.currentTimeMillis();
        Date startDate = DateTools.parseDateFromString_yyyyMMdd(start, logger);
        Date endDate = DateTools.parseDateFormat_yyyyMMddHHmmss(start + "23:59:59", logger);
        Date preDate = DateTools.getPreDayDate(startDate);
        String preDateStr = simpleDateFormat_yyyyMMdd.format(preDate);


        String bookcodemapJson = jedisTemplate.get(RedisKey.BOOKCODEMAP);
        final Map<Integer, List<Fundbookcode>> bookcodemap = JsonUtils.readToMapList(bookcodemapJson);
        String table_yyyymm = StringUtils.substring(start, 0, 6);
        String tableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + StringUtils.substring(start, 0, 6);
        fundbookdayExtMapper.deleteFundbookDay(null,users,tableName,Integer.parseInt(start),Integer.parseInt(start));
        final Map<String, Fundbookday> fundbookdayMap = getStringFundbookdayMapUsers(table_yyyymm, startDate, endDate, users);
        //多线程刷当天的余额到redis
        cacheBalance(users, bookcodemap, preDateStr, start, fundbookdayMap);
        FundbookdayRunnerNew fundbookdayRunner = new FundbookdayRunnerNew();
        fundbookdayRunner.setUsers(users);
        fundbookdayRunner.setBookcodemap(bookcodemap);
        fundbookdayRunner.setBookDate(startDate);
        fundbookdayRunner.setFundbookdayExtMapper(fundbookdayExtMapper);
        fundbookdayRunner.setFundbookdayMap(fundbookdayMap);
        fundbookdayRunner.setBookDateStr(start);
        fundbookdayRunner.setPreDateStr(preDateStr);
        fundbookdayRunner.setFundbookDayTableName(tableName);
        fundbookdayRunner.setJedisTemplate(jedisTemplate);
        fundbookdayRunner.runJob();
        long endRunTime = System.currentTimeMillis();
        logger.info((double) (endRunTime - startRunTime) / 1000 + "秒任务全部跑完了");
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
        final int pageSize = 300;
        final int cacheThreadCount = (dataSize / pageSize) + 1;
        final CountDownLatch countDownLatch = new CountDownLatch(cacheThreadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(cacheThreadCount);
        for (int j = 1; j <= cacheThreadCount; j++) {
            final int jm = j;
            executorService.execute(new Runnable() {
                public void run() {
                    List<JedisVo> setJedisVos = new ArrayList<JedisVo>();
                    long cacheStartsss = System.currentTimeMillis();
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
                                String preBalanceStr =null;
                                if (!StringUtils.substring(preDateStr,0,6).equals("201308")) {
                                    preBalanceStr = jedisTemplate.get(jedsPrekey);
                                }
                                jedsValue = preBalanceStr == null ? "0.0" : preBalanceStr;
                            }
                            JedisVo jedisVo = new JedisVo(jedskey, jedsValue);
                            setJedisVos.add(jedisVo);
                            if (setJedisVos.size() % 4000 == 0) {
                                long cacheStart = System.currentTimeMillis();
                                jedisTemplate.pipset(setJedisVos);

                                long cacheEnd = System.currentTimeMillis();
                                double memerTime = (double) (cacheEnd - cacheStartsss) / 1000;
                                logger.info(memerTime + "m " + bookDateStr + "批量set余额到redis" + setJedisVos.size() + "数据量，" + (double) (cacheEnd - cacheStart) / 1000 + " " + preDateStr);
                                setJedisVos = new ArrayList<JedisVo>();
                                cacheStartsss = System.currentTimeMillis();
                            }
                        }
                    }
                    if (setJedisVos.size() != 0) {
                        jedisTemplate.pipset(setJedisVos);
                        logger.info(bookDateStr + "批量set余额到redis最后一次" + setJedisVos.size() + "数据量" + preDateStr);
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

    //创建任务,todo 任务创建了 如果一直没有被消费会被产生很多垃圾数据
    public void scheduleCreate(String start, String end,  //  今天的活跃用户数
                               final List<UserBasicInfo> userListObjectCustomer) {
        Map<Integer, List<Fundbookcode>> bookcodemap = cacheFndbookcode();
        jedisTemplate.del(RedisKey.REPORT_OF_DAY_QUEUE);//删除之前的任务
        jedisTemplate.set(RedisKey.BOOKCODEMAP, JsonUtils.toJson(bookcodemap));
        //1.1根据时间区间算出所有需要删数据的表名
        Date startDate = DateTools.parseDateFromString_yyyyMMdd(start, logger);

        Date endDate = DateTools.parseDateFromString_yyyyMMdd(end, logger);

        Map<String, DelTableName> deleteTableNameMap = DateTools.getDeleteTableName(startDate, endDate, logger);

        for (String key : deleteTableNameMap.keySet()) {
            //每个月
            DelTableName delTableName = deleteTableNameMap.get(key);
            Date startDateByTable = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr(), logger);
            Date endDateByTable = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, delTableName.getEndStr() + "23:59:59", logger);
            while (endDateByTable.compareTo(startDateByTable) != -1) {
                //每天
                String bookDateStr = DateTools.formate_yyyyMMdd(startDateByTable);
                //删除之前的任务队列
                jedisTemplate.del(RedisKey.REPORT_OF_DAY_SUB_QUEUE + bookDateStr);
                //今天的活跃用户
                String currentDayStr = DateTools.formate_yyyyMMdd(startDateByTable);
                Date createEndTime = DateTools.parseDateFormat_yyyyMMddHHmmss(currentDayStr + "23:59:59", logger);
                //  今天的活跃用户数
                  List<UserBasicInfo> userListObject=null;
                if(userListObjectCustomer!=null&&userListObjectCustomer.size()>0){
                    userListObject=userListObjectCustomer;
                }else {
                    userListObject = userBasicExtMapper.getUsers(0, 0, 0, 0, createEndTime.getTime() / 1000l);
                }
                final int dataSize = userListObject.size(); //01,23,4;
                final int pageSize = 600;
                final int cacheThreadCount = (dataSize / pageSize) + 1;

                logger.info(bookDateStr + " " + cacheThreadCount + "页");

                for (int j = 1; j <= cacheThreadCount; j++) {
                    List<UserBasicInfo> tempUserList = new ArrayList<>();
                    for (int i = (j - 1) * pageSize; !(i > (j * pageSize - 1) || i > (dataSize - 1)); i++) {
                        tempUserList.add(userListObject.get(i));
                    }
                    //创建每天的子任务
                    jedisTemplate.rpush(RedisKey.REPORT_OF_DAY_SUB_QUEUE + bookDateStr, JsonUtils.toJson(tempUserList));
                }
                //创建每天日结任务
                jedisTemplate.set(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL+bookDateStr,"0");

                jedisTemplate.rpush(RedisKey.REPORT_OF_DAY_QUEUE, bookDateStr);
                startDateByTable = DateTools.getNextDayDate(startDateByTable);
            }
        }
        //任务创建完了开始分配任务
        scheduleDispatcher();
    }

    //主服务分配任务
    public void scheduleDispatcher() {
        //取一个任务,然后通知子服务观察者
        String bookDateStr = jedisTemplate.lpop(RedisKey.REPORT_OF_DAY_QUEUE);
        if (StringUtils.isNotBlank(bookDateStr)) {
            long totalTask = jedisTemplate.llen(RedisKey.REPORT_OF_DAY_SUB_QUEUE + bookDateStr);
            zkTemplate.deletingChildrenIfNeeded(FundConstant.REPORT_OF_DAY_PUB_CHILD);
            zkTemplate.createPersistentNode(FundConstant.REPORT_OF_DAY_PUB_CHILD,null);
            ScheduleVo scheduleVo = new ScheduleVo(bookDateStr, totalTask);
            //放一个任务到任务桶中
            String scheduleVoJson = JsonUtils.toJson(scheduleVo);
            zkTemplate.setData(FundConstant.REPORT_OF_DAY_SUB_LISSTENER, scheduleVoJson);
        }
    }

    //子服务领任务
    public void getSchedule() {
        boolean taskIsNull = false;
        ScheduleVo scheduleVo = null;
        while (!taskIsNull) {
            String userJsons = null;
            String scheduleVoJson = zkTemplate.getData(FundConstant.REPORT_OF_DAY_SUB_LISSTENER);
            if (StringUtils.isNotBlank(scheduleVoJson)) {
                scheduleVo = JsonUtils.readToT(scheduleVoJson, ScheduleVo.class);
                userJsons = jedisTemplate.lpop(RedisKey.REPORT_OF_DAY_SUB_QUEUE + scheduleVo.getBookdate());
                if (StringUtils.isNotBlank(userJsons)) {
                    List<UserBasicInfo> users = JsonUtils.readToList(userJsons, UserBasicInfo.class);
                    dayReportSchedule(scheduleVo.getBookdate(), users);
                    //执行完了 完成数+1,并通知主线程
                    jedisTemplate.incr(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL + scheduleVo.getBookdate());
                    //通知任务中心,我干完了
                    String data = scheduleVo.getBookdate()+"-"+users.get(0).getUserid() + "-" + users.get(users.size() - 1).getUserid();
                    zkTemplate.createEphemeralNode(FundConstant.REPORT_OF_DAY_PUB_CHILD + "/"+scheduleVo.getBookdate()+ "-" + users.get(0).getUserid(), data);
                } else {
                    taskIsNull = true;
                    logger.info("任务被取完了,等着");
                }
            } else {
                taskIsNull = true;
                logger.info("没任务,等着");
            }
        }
    }


    public void lisstenerStart(final ScheduleServiceDayNew scheduleServiceDayNew) {

        String isMaster = null;
        try {
            isMaster = System.getProperty(FundConstant.IS_MASTER);
        } catch (Exception e) {
            logger.error("线程报错了", e);
            throw new RuntimeException(e);
        }
        CuratorFramework curatorClient = zkTemplate.getCuratorClient();
        if (!"0".equals(isMaster)) {
            //todo 必须先启动主节点
            zkTemplate.setData(FundConstant.REPORT_OF_DAY_SUB_LISSTENER, "");
//           主服务监听
            final TreeCache treeCache = new TreeCache(curatorClient, FundConstant.REPORT_OF_DAY_PUB_LISSTENER);
            try {
                treeCache.start();
            } catch (Exception e) {
                logger.error("主服务监听报错", e);
            }

            treeCache.getListenable().addListener(new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent event) throws Exception {
                    switch (event.getType()) {
                        case NODE_ADDED:
                            logger.info("子节点增加 " + event.getData().getPath() + " " + new String(event.getData().getData()));
                            String scheduleVoJson = zkTemplate.getData(FundConstant.REPORT_OF_DAY_SUB_LISSTENER);
                            if (StringUtils.isNotBlank(scheduleVoJson)) {
                                ScheduleVo scheduleVo = JsonUtils.readToT(scheduleVoJson, ScheduleVo.class);
                                //要确认所有任务都执行完了
                                String dtotal=jedisTemplate.get(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL + scheduleVo.getBookdate());
                                logger.info("目前完成"+dtotal+"总任务数量"+scheduleVo.getTotalTask());
                                long redisTotal = dtotal==null?0:Long.valueOf(dtotal);
                                if (scheduleVo.getTotalTask() == redisTotal) {
                                    logger.info("恭喜你 完成一天了===============================");
                                    //清空上一个任务任务总数
                                    jedisTemplate.del(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL + scheduleVo.getBookdate());
                                    scheduleServiceDayNew.scheduleDispatcher();
                                    //todo 如果这里有一天任务卡死 ,所有后面任务都会不能执行
                                }
                            }
                            break;
                        case NODE_REMOVED:
                            logger.info("子节点被删除 " + curatorFramework.getData());
                            break;
                        case NODE_UPDATED:
                            logger.info("子节点更新 " + curatorFramework.getData());
                            break;
                        case CONNECTION_LOST:
                            logger.info("链接被断开 ");
                            break;
                        default:
                            logger.info("其他事件" + event.getType() + " ");

                    }
                }
            });
            logger.info("主服务开始监听");
        }
        NodeCache nodeCache = new NodeCache(curatorClient, FundConstant.REPORT_OF_DAY_SUB_LISSTENER);
        try {
            nodeCache.start();
        } catch (Exception e) {
            logger.error("子节点监控失败", e);
        }
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                logger.info("子任务收到通知:" + zkTemplate.getData(FundConstant.REPORT_OF_DAY_SUB_LISSTENER));
                //收到通知 就去取一次任务
                getSchedule();
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        logger.info("子====服务开始监听，并尝试获取任务");
        getSchedule();
    }
}
