package com.yjy.listener;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.jedisPubSub.JedisSub;
import com.yjy.service.ScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/6/15.
 */
public class Sublistener implements BeanPostProcessor {

    private static final Logger logger = LoggerFactory.getLogger(Sublistener.class);

    @Resource
    private JedisTemplate jedisTemplate;

    @Resource
    private JedisSub jedisSub;


    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {

        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o instanceof ScheduleService) {
            logger.info("开始监听.......");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    logger.info("开始订阅");
                    jedisTemplate.subscribe(RedisKey.REPORT_OF_DAY_PUB_KEY, jedisSub);
                }
            }, "李文科订阅线程").start();
        }


        return o;
    }
}
