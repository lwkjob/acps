package com.yjy.service.subandpub;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.service.distributed.ScheduleServiceDayNew;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;

/**
 * Created by liwenke on 16/6/26.
 */
@Component
public class PubLisstener extends JedisPubSub {
    private static final Logger logger = LoggerFactory.getLogger(PubLisstener.class);


    private JedisTemplate jedisTemplate;


    private ScheduleServiceDayNew scheduleServiceDayNew;

    @Override
    public void onMessage(String key, String bookdate) {
        logger.info("主服务收到任务" + key + "|   |" + bookdate);

       String currentTaskFlag= jedisTemplate.get(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TEMP);
        if(StringUtils.isNotBlank(currentTaskFlag)&&!currentTaskFlag.equals("0")){
            String totaltask =StringUtils.substring(currentTaskFlag,currentTaskFlag.lastIndexOf("|-")+1);
            //要确认所有任务都执行完了
            if (totaltask.equals(0)||totaltask.equals(jedisTemplate.get(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL + bookdate))) {
                //如果没任务发布下一个任务
                jedisTemplate.del(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL + bookdate);
                jedisTemplate.set(RedisKey.REPORT_OF_DAY_SUB_QUEUE_TEMP, "0");
                scheduleServiceDayNew.scheduleDispatcher();
                //todo 如果这里有一天任务卡死 ,所有后面任务都会不能执行
            }
        }
    }


    @Override
    public void onPMessage(String s, String s1, String s2) {
        logger.info("onPMessage"+s);
    }

    @Override
    public void onSubscribe(String s, int i) {
        logger.info("onSubscribe"+s);
    }

    @Override
    public void onUnsubscribe(String s, int i) {
        logger.info("onUnsubscribe"+s);
    }

    @Override
    public void onPUnsubscribe(String s, int i) {
        logger.info("onPUnsubscribe"+s);
    }

    @Override
    public void onPSubscribe(String s, int i) {
        logger.info("onPSubscribe"+s);
    }

    public JedisTemplate getJedisTemplate() {
        return jedisTemplate;
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    public ScheduleServiceDayNew getScheduleServiceDayNew() {
        return scheduleServiceDayNew;
    }

    public void setScheduleServiceDayNew(ScheduleServiceDayNew scheduleServiceDayNew) {
        this.scheduleServiceDayNew = scheduleServiceDayNew;
    }
}

