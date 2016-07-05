package com.yjy.service.fundbook.subandpub;

import com.yjy.service.fundbook.distributed.ScheduleServiceDayNew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * Created by liwenke on 16/6/27.
 */
public class StartUpLissltener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StartUpLissltener.class);

    @Value("${startLisstener}")
    private int startLisstener;


    @Resource
    private ScheduleServiceDayNew scheduleServiceDayNew;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            if (startLisstener == 1) {
                logger.info("分布式模式");
                 scheduleServiceDayNew.lisstenerStart();
            }else {
                logger.info("单机模式");
            }
        }
    }
}