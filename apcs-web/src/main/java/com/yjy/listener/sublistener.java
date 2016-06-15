package com.yjy.listener;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.jedisPubSub.JedisPubSubImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/6/15.
 */
public class Sublistener implements ApplicationListener {

    private static final Logger logger= LoggerFactory.getLogger(Sublistener.class);

    @Resource
    private JedisTemplate jedisTemplate;

    @Resource
    private  JedisPubSubImpl jedisPubSubImpl;


    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

        logger.info("开始监听.......");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                logger.info("开始订阅");
//                jedisTemplate.subscribe(RedisKey.REPORT_OF_DAY, jedisPubSubImpl);
//            }
//        },"李文科订阅线程").start();
    }
}
