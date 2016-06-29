package com.yjy.test.funbook;

import com.yjy.common.dao.Pagination;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.utils.DateTools;
import com.yjy.entity.Fundbook;
import com.yjy.entity.Fundbookday;
import com.yjy.service.FundbookDayService;
import com.yjy.service.FundbookService;
import com.yjy.service.FundbookcodeService;
import com.yjy.service.UserService;
import com.yjy.service.distributed.ScheduleServiceDayNew;
import com.yjy.service.vo.JedisVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by liwenke on 16/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:test-config.xml") // 加载配置
public class FundbookTest {

    @Resource
    private FundbookService fundbookService;


     @Resource
    private FundbookDayService fundbookDayService;


    @Resource
    private FundbookcodeService fundbookcodeService;

    @Resource
    private UserService userService;

    @Resource
    private JedisTemplate jedisTemplate;


    @Resource
    private ScheduleServiceDayNew scheduleServiceDayNew;

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

    private static Logger logger= LoggerFactory.getLogger(Fundbook.class);

    //分页查询账本
    @Test
    public void testPaginnationFundbook(){
        Pagination<Fundbook> pagination=new Pagination(1,2);
        Fundbook example=new Fundbook();
        example.setBookid(8l);
        long startTime=1377964800l;
        long endTime=1377964803l;
        String tablename="fundbook201309";
        List<Fundbook> list =  fundbookService.selectPageListByExample(example,tablename,startTime,endTime,pagination);
        for (Fundbook fundbook:list){
            logger.info(""+fundbook.getBookid()+" "+fundbook.getHappentime());
        }
    }

    //分页更新上月最后一天的余额到redis,从日结表中查
    @Test
    public void getByBookdete(){
        Fundbookday fundbookday=new Fundbookday();
        fundbookday.setBookdate(20131031);
//        fundbookDayService.getByBookdete(fundbookday);
    }


    //更新余额
    @Test
    public void oneByOneUpdateBalance(){
        Date startDate= DateTools.parseDateFromString_yyyyMM("201511",logger);
        Date endDate= DateTools.parseDateFromString_yyyyMM("201511",logger);
        //fundbookService.oneByOneUpdateBalance(startDate, endDate, null, null);
    }


    @Test
    public void testRedis(){

        List<JedisVo> jedisVos=new ArrayList<>();
        JedisVo jedisVo1=    new JedisVo("key1","va1");
        JedisVo jedisVo2=    new JedisVo("key2","va2");
        JedisVo jedisVo3=    new JedisVo("key3","va3");
        jedisVos.add(jedisVo1);
        jedisVos.add(jedisVo2);
        jedisVos.add(jedisVo3);

        jedisTemplate.pipset(jedisVos);
        logger.info(jedisTemplate.get("key1"));
        logger.info(jedisTemplate.get("key2"));
        logger.info(jedisTemplate.get("key3"));

    }

    //缓存每天的活跃用户到redis
    @Test
    public void cacheUsers(){
        String start="20160301";
        String end="20160331";
        userService.cacheUsers(start, end);
    }


    @Test
    public void lisstenerStart() throws  Exception{

//                new Thread(new Runnable() {
//
//                    public void run() {
//                          jedisTemplate=new JedisTemplate(host,port,timeout,threadCount,password,database);
//                        PubLisstener pubLisstener = new PubLisstener();
//                        pubLisstener.setJedisTemplate(jedisTemplate);
//                        pubLisstener.setScheduleServiceDayNew(scheduleServiceDayNew);
//                        logger.info("开始监听子任务完成状态");
//                        jedisTemplate.subscribe(RedisKey.REPORT_OF_DAY_PUB_LISSTENER, pubLisstener);
//                    }
//                },"子任务完成状态监听线程").start();
//
//
//        new Thread(new Runnable() {
//
//            public void run() {
//                logger.info("开始监听接收任务");
//                 jedisTemplate=new JedisTemplate(host,port,timeout,threadCount,password,database);
//                SubLisstener subLisstener = new SubLisstener();
//                subLisstener.setScheduleServiceDayNew(scheduleServiceDayNew);
//                jedisTemplate.subscribe(RedisKey.REPORT_OF_DAY_SUB_LISSTENER,subLisstener);
//
//            }
//        },"接收任务监听线程").start();

        Thread.sleep(1000000);
    }
}
