package com.yjy.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/6/15.
 */
public class Startup {

    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    public static void main(String[] args) {
        ApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:/spring-config.xml");
        try {
            synchronized (xmlApplicationContext){
                xmlApplicationContext.wait();
            }
        } catch (Exception e) {
            logger.error("被干死了", e);
        }
    }
}
