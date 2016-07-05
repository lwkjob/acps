package com.yjy.apcs.rpc.server;

import com.yjy.common.constant.FundConstant;
import org.apache.thrift.server.TServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2016/6/15.
 */
public class AcpsRpcStartupMain {

    private static final Logger logger = LoggerFactory.getLogger(AcpsRpcStartupMain.class);

    public static void main(String[] args) {
        System.setProperty(FundConstant.IS_MASTER,"0");
        ApplicationContext xmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:/spring-rpc-config.xml");

        AcpsRpcServer acpsRpcServer=(AcpsRpcServer)    xmlApplicationContext.getBean("acpsRpcServer");
        TServer tServer = acpsRpcServer.startServer();
        logger.info("开始监听");
        tServer.serve();
    }
}
