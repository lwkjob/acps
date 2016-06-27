package com.yjy.common.zk;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/6/24.
 */
public class ZkTemplate {

    private String host;
    private CuratorFramework curatorFramework;

    private static Log logger = LogFactory.getLog(ZkTemplate.class);

    public ZkTemplate(String host){
        this.host=host;
        ExponentialBackoffRetry retry=new ExponentialBackoffRetry(1000,3);
        this. curatorFramework = CuratorFrameworkFactory.newClient(this.host, retry);

    }

    public CuratorFramework getCuratorClient(){
        return this.curatorFramework;
    }

    public static void main(String[] args) throws Exception{
        ZkTemplate zkTemplate= new ZkTemplate("localhost:2181");
        CuratorFramework curatorFramework= zkTemplate.getCuratorClient();
        curatorFramework.start();
        InterProcessMutex mutex=new InterProcessMutex(curatorFramework,"/com/yjy/acps/lock/master");
        mutex.acquire(10, TimeUnit.SECONDS);
        Thread.sleep(1000000);
    }
}
