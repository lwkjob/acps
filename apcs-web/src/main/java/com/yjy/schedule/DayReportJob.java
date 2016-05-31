package com.yjy.schedule;

import com.yjy.entity.FundbookExample;
import com.yjy.repository.mapper.FundbookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by liwenke on 16/5/28.
 */

public class DayReportJob {

    private Logger logger= LoggerFactory.getLogger(DayReportJob.class);

    public void doJob(){

        logger.info("定时任务开始");
    }
}
