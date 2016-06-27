package com.yjy.service.subandpub;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.zk.ZkTemplate;
import com.yjy.service.distributed.ScheduleServiceDayNew;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;

/**
 * Created by liwenke on 16/6/27.
 */
public class StartUpLissltener implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(StartUpLissltener.class);




    @Resource
    private JedisTemplate jedisTemplate;

    @Resource
    private ZkTemplate zkTemplate;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        logger.info(o.getClass().getName() + "ss=" + logger);
        String    isMaster=     System.getProperty(FundConstant.IS_MASTER);

        if (o instanceof ScheduleServiceDayNew) {
            CuratorFramework  curatorClient= zkTemplate.getCuratorClient();
            curatorClient.start();
//            InterProcessMutex lock=new InterProcessMutex(curatorClient,"/com/yjy/acps/lock/master");
            try {
//                 if(!isMaster.equals("0")&&lock.acquire(10, TimeUnit.SECONDS)){
                 if(!isMaster.equals("0")){
                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             //
                             logger.info("开始监听子任务完成状态");
                             jedisTemplate.subscribe(RedisKey.REPORT_OF_DAY_PUB_LISSTENER, new PubLisstener());

                         }
                     },"接收任务监听线程").start();
                };
            }catch (Exception e){
                logger.error("",e);
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("开始监听接收任务");
                    jedisTemplate.subscribe(RedisKey.REPORT_OF_DAY_SUB_LISSTENER, new SubLisstener());

                }
            },"接收任务监听线程").start();
        }
        return o;
    }
}
