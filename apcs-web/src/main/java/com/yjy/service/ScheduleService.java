package com.yjy.service;

import com.yjy.common.utils.DateTools;
import com.yjy.common.utils.JsonUtils;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.UserBasicInfo;
import com.yjy.jedisPubSub.SubPubMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/15.
 */
@Service("scheduleService")
public class ScheduleService {
    @Resource
    private FundbookService fundbookService;

    @Resource
    private FundbookDayService fundbookDayService;

    @Resource
    private FundbookMonthService fundbookMonthService;

    @Resource
    private FundbookcodeService fundbookcodeService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    public void dayreport(String start ,String end,List<Fundbookcode> fundbookcodes,List<UserBasicInfo> users){

        Map<Integer, List<Fundbookcode>> bookcodemap = fundbookcodeService.cacheFndbookcode();

        try {
            long startRunTime=System.currentTimeMillis();
            Date startDate= DateTools.parseDateFromString_yyyyMMdd(start, logger);

            Date endDate= DateTools.parseDateFromString_yyyyMMdd(end,logger);

            fundbookService.oneByOneUpdateBalance(startDate, endDate, fundbookcodes, users);

            fundbookDayService.insertFundBookDay(startDate, endDate, bookcodemap, users);

            fundbookMonthService.insertFundBookMonth(startDate, endDate, bookcodemap);
            long endRunTime=System.currentTimeMillis();
            logger.info((double)(endRunTime-startRunTime)/1000+"秒任务全部跑完了");
        }catch (Exception e){
            logger.error("报错了异常被吃了继续监听",e);
        }
    }
}
