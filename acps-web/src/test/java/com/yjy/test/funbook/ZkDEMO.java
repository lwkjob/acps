package com.yjy.test.funbook;

import com.yjy.common.zk.ZkTemplate;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by liwenke on 16/6/27.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:test-config.xml") // 加载配置
public class ZkDEMO {
        Logger logger= LoggerFactory.getLogger(ZkDEMO.class);

    @Resource
    private ZkTemplate zkTemplate;

    @Test
    public void testWatch() throws  Exception{
        if(zkTemplate.checkExists("/com/lwk2")){
            zkTemplate.deletingChildrenIfNeeded("/com/lwk2");
        }
        zkTemplate.createPersistentNode("/com/lwk2","0");
//        client.setData().forPath("/com/lwk","0".getBytes());
//        client.setData().forPath("/com/lwk2","0".getBytes());
//        final  NodeCache nodeCache=new NodeCache(client,"/com/lwk");
//        nodeCache.start();
//        nodeCache.getListenable().addListener(new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//              String currentData= new String( nodeCache.getCurrentData().getData());
//                logger.info("nodeCache==="+currentData);
//            }
//        });
        CuratorFramework client= zkTemplate.getCuratorClient();
        final PathChildrenCache  pathCache2=new PathChildrenCache(client,"/com/lwk2",false);
        pathCache2.start();
        pathCache2.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                switch (pathChildrenCacheEvent.getType()){
                    case  CHILD_ADDED:
                        logger.info("子节点增加 "+pathChildrenCacheEvent.getData()+"  "+pathChildrenCacheEvent.getInitialData());
                        break;
                    case CHILD_REMOVED:
                        logger.info("子节点被删除 "+pathChildrenCacheEvent.getData()+"  "+pathChildrenCacheEvent.getInitialData());
                        break;
                    case CHILD_UPDATED:
                        logger.info("子节点更新 "+pathChildrenCacheEvent.getData()+" "+pathChildrenCacheEvent.getInitialData());
                        break;
                    case CONNECTION_LOST:
                        logger.info("链接被断开 ");
                        break;
                    default:
                        logger.info("其他事件"+pathChildrenCacheEvent.getType()+" ");

                }
            }
        });

//        client.setData().forPath("/com/lwk","1".getBytes());
//        client.setData().forPath("/com/lwk2","2".getBytes());
//        TimeUnit.SECONDS.sleep(5);
//        logger.info("稍等");
//        client.setData().forPath("/com/lwk","3".getBytes());
//        client.setData().forPath("/com/lwk2","4".getBytes());
        zkTemplate.createPersistentNode("/com/lwk2/l1","1");
        zkTemplate.createPersistentNode("/com/lwk2/l2","2");
        zkTemplate.createPersistentNode("/com/lwk2/l3","3");
        zkTemplate.deletingChildrenIfNeeded("/com/lwk2");
        zkTemplate.createPersistentNode("/com/lwk2","0");
        zkTemplate.createPersistentNode("/com/lwk2/l1","4");
        zkTemplate.createPersistentNode("/com/lwk2/l2","5");
        TimeUnit.SECONDS.sleep(5000);

    }
}
