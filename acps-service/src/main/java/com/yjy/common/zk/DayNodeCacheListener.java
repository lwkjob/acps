package com.yjy.common.zk;

import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * Created by Administrator on 2016/6/24.
 */
public class DayNodeCacheListener implements NodeCacheListener {

    private NodeCache nodeCache;
    private int cacheThreadCount ;



    @Override
    public void nodeChanged() throws Exception {
        ChildData currentData = nodeCache.getCurrentData();
        String curr=new String(currentData.getData());
        if (curr.equals(cacheThreadCount)){
//            继续任务
        }
    }

    public int getCacheThreadCount() {
        return cacheThreadCount;
    }

    public void setCacheThreadCount(int cacheThreadCount) {
        this.cacheThreadCount = cacheThreadCount;
    }

    public NodeCache getNodeCache() {
        return nodeCache;
    }

    public void setNodeCache(NodeCache nodeCache) {
        this.nodeCache = nodeCache;
    }
}
