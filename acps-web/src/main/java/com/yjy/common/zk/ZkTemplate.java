package com.yjy.common.zk;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.utils.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

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
        curatorFramework.start();

    }

    public CuratorFramework getCuratorClient(){
        return this.curatorFramework;
    }


    public void setData(String path,String data){
        CuratorFramework client= getCuratorClient();
        try {
            //设置当前任务,子服务会监听到
            client.setData().forPath(path, data.getBytes());
        }catch (Exception e){
            logger.error("修改zk数据失败",e);
        }
    }
    public String getData(String path){
        CuratorFramework client= getCuratorClient();
        try {
            //设置当前任务,子服务会监听到
          byte[]  databyte= client.getData().forPath(path);
           return databyte==null?null:new String(databyte);
        }catch (Exception e){
            logger.error("getzk数据失败",e);
        }
        return null;
    }

    //创建临时节点
    public String createEphemeralNode(String path,String data){
        CuratorFramework client= getCuratorClient();
        try {
            //设置当前任务,子服务会监听到
            client.create().withMode(CreateMode.EPHEMERAL).forPath(path,data.getBytes());
        }catch (Exception e){
            logger.error("创建临时节点报错",e);
        }
        return null;
    }

    //创建节点
    public String createPersistentNode(String path,String data){
        CuratorFramework client= getCuratorClient();
        try {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path,data.getBytes());
        }catch (Exception e){
            logger.error("创建永久节点报错",e);
        }
        return null;
    }

    //递归删除节点和子节点
    public void deletingChildrenIfNeeded(String path){
        CuratorFramework client= getCuratorClient();
        try {
            //设置当前任务,子服务会监听到
            client.delete().deletingChildrenIfNeeded().forPath(path);
        }catch (Exception e){
            logger.error("删除节点报错",e);
        }
    }
    //判断节点是否存在
    public boolean checkExists(String path){
        CuratorFramework client= getCuratorClient();
        try {
            //设置当前任务,子服务会监听到
            Stat stat = client.checkExists().forPath(path);
            return stat==null?false:true;
        }catch (Exception e){
            logger.error("删除节点报错",e);
        }
        return false;
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
