package com.yjy.service.subandpub;

import com.yjy.service.distributed.ScheduleServiceDayNew;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.Resource;

/**
 * Created by liwenke on 16/6/27.
 */
public class StartUpLissltener   implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(StartUpLissltener.class);




    @Resource
    private ScheduleServiceDayNew scheduleServiceDayNew;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info(  "ss=================" + event.getApplicationContext().getParent());
        if(event.getApplicationContext().getParent() == null){
            // scheduleServiceDayNew.lisstenerStart(scheduleServiceDayNew);
        }


    }
}