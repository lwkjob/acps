package com.yjy.service.fundbook;

import com.yjy.common.utils.DateTools;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.UserBasicInfo;
import com.yjy.common.utils.DelTableName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");

    private static final Logger logger = LoggerFactory.getLogger(ScheduleService.class);

    public void dayreport(String start ,String end,List<Fundbookcode> fundbookcodes,List<UserBasicInfo> users,List<Integer> userids){

        Map<Integer, List<Fundbookcode>> bookcodemap = fundbookcodeService.cacheFndbookcode();

        try {
            long startRunTime=System.currentTimeMillis();
            //1.1根据时间区间算出所有需要删数据的表名
            Date startDate= DateTools.parseDateFromString_yyyyMMdd(start, logger);

            Date endDate= DateTools.parseDateFromString_yyyyMMdd(end, logger);

            Map<String, DelTableName> deleteTableNameMap = DateTools.getDeleteTableName(startDate, endDate, logger);

            for (String key : deleteTableNameMap.keySet()) {

                long startRunTimes=System.currentTimeMillis();
                //每个月
                DelTableName delTableName = deleteTableNameMap.get(key);
                Date startDateByTable =  DateTools.parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr(),logger);
                Date endDateByTable =  DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddHHmmss, delTableName.getEndStr() + "23:59:59",logger);

                fundbookService.oneByOneUpdateBalance(startDateByTable, endDateByTable, fundbookcodes, userids);

                fundbookDayService.insertFundBookDay(startDateByTable, endDateByTable, bookcodemap, users);

                fundbookMonthService.insertFundBookMonth(startDateByTable, endDateByTable, bookcodemap,users);

                long endRunTime=System.currentTimeMillis();
                logger.info((double) (endRunTime - startRunTimes) / 1000 + "秒任务全部跑完了,"+delTableName.getTableNameSuffix());
            }

            long endRunTime=System.currentTimeMillis();
            logger.info((double)(endRunTime-startRunTime)/1000+"秒任务全部跑完了");
        }catch (Exception e){
            logger.error("报错了异常被吃了继续监听",e);
        }
    }
}
