package com.yjy;

import com.yjy.common.constant.FundConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/6/15.
 */
public class AcpsStartup {

    private static final Logger logger = LoggerFactory.getLogger(AcpsStartup.class);

    public static void main(String[] args) {
        System.setProperty(FundConstant.IS_MASTER,"0");
        ApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:/spring-config.xml");
        try {

            logger.info("启动完成");
            synchronized (xmlApplicationContext){
                xmlApplicationContext.wait();
            }
        } catch (Exception e) {
            logger.error("被干死了", e);
        }
    }
}
