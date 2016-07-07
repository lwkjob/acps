package com.yjy.common.zk;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/6/24.
 */
public class ZkTemplate {

    private String host;
    private CuratorFramework curatorFramework;

    private static Log logger = LogFactory.getLog(ZkTemplate.class);

    @Value("${startLisstener}")
    private int startLisstener;

    public ZkTemplate(String host){
        this.host=host;
        if(startLisstener==1){
            ExponentialBackoffRetry retry=new ExponentialBackoffRetry(1000,3);
            this. curatorFramework = CuratorFrameworkFactory.newClient(this.host, retry);
            curatorFramework.start();
        }
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
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path,data.getBytes());
        }catch (Exception e){
            logger.error("创建临时节点报错",e);
        }
        return null;
    }

    //创建节点
    public String createPersistentNode(String path,String data){
        CuratorFramework client= getCuratorClient();
        try {
            if(data!=null){
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,data.getBytes());
            }else {
                client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
            }
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
            if( checkExists(path)){
                client.delete().deletingChildrenIfNeeded().forPath(path);
            }
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
        ZkTemplate zkTemplate= new ZkTemplate("192.168.2.12:2181");

        List<String> strings = zkTemplate.getCuratorClient().getChildren().forPath(FundConstant.REPORT_OF_DAY_SUB_LISSTENER);
        for (String s:strings){
            logger.info(s);
        }

        List<String> strings2 = zkTemplate.getCuratorClient().getChildren().forPath("/lwk");
        for (String s:strings2){
            logger.info(s);
        }

    }
}
