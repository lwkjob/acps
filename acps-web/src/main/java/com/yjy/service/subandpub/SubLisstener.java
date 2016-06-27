package com.yjy.service.subandpub;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.JsonUtils;
import com.yjy.entity.UserBasicInfo;
import com.yjy.service.distributed.ScheduleServiceDayNew;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liwenke on 16/6/26.
 */
@Component
public class SubLisstener extends JedisPubSub {
    private static final Logger logger= LoggerFactory.getLogger(SubLisstener.class);


    private ScheduleServiceDayNew scheduleServiceDayNew;

    @Override
    public void onMessage(String key, String bookdate) {
        logger.info("子服务收到任务"+key +"|   |"+bookdate);
        scheduleServiceDayNew.getSchedule();
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

    public ScheduleServiceDayNew getScheduleServiceDayNew() {
        return scheduleServiceDayNew;
    }

    public void setScheduleServiceDayNew(ScheduleServiceDayNew scheduleServiceDayNew) {
        this.scheduleServiceDayNew = scheduleServiceDayNew;
    }
}
