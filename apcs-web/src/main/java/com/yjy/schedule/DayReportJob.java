package com.yjy.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liwenke on 16/5/28.
 */

public class DayReportJob {

    private Logger logger= LoggerFactory.getLogger(DayReportJob.class);

    public void doJob(){

        logger.info("定时任务开始");
    }
}
