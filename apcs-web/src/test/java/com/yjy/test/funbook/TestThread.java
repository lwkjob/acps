package com.yjy.test.funbook;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/6/13.
 */
public class TestThread {

    Logger logger= LoggerFactory.getLogger(TestThread.class);

    @Test
    public void test() throws Exception{
        final int dataSize=15; //01,23,4;
        final int pageSize=10;
        final int cacheThreadCount=(dataSize/pageSize)+1;
        logger.info(cacheThreadCount + "页");
        final CountDownLatch countDownLatch=new CountDownLatch(cacheThreadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(cacheThreadCount);
        for (int j=1;j<=cacheThreadCount;j++){
            final int jm=j;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = (jm - 1) * pageSize; !(i > (jm * pageSize - 1) || i > (dataSize - 1)); i++) {
                        if (jm == 1) {
                            seleeps();
                        }
                        String test = "abcdefg" + i;
                        if (jm == 2) {
                            test = "nimeiaaa";
                        }

                        logger.info(Thread.currentThread().getName() + "  " + i + ""+test);
                    }


                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        executorService.shutdown();
    }

    private void seleeps() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (Exception e){
            logger.error("12",e);
        }
    }
}
