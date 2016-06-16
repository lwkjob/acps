package com.yjy.jedisPubSub;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.utils.DateTools;
import com.yjy.common.utils.JsonUtils;
import com.yjy.entity.DelTableName;
import com.yjy.entity.Fundbookcode;
import com.yjy.service.FundbookDayService;
import com.yjy.service.FundbookMonthService;
import com.yjy.service.FundbookService;
import com.yjy.service.FundbookcodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 任务发布者 2016/6/15.
 */
@Component("jedisPub")
public class JedisPub extends JedisPubSub {

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");
    @Resource
    private FundbookService fundbookService;

    @Resource
    private FundbookDayService fundbookDayService;

    @Resource
    private FundbookMonthService fundbookMonthService;

    @Resource
    private FundbookcodeService fundbookcodeService;


    private static final Logger logger = LoggerFactory.getLogger(JedisPub.class);



        @Override
        public void onMessage(String channel, String message) {

            logger.info("onMessage:" + message);
            try {
                Map<Integer, List<Fundbookcode>> bookcodemap = fundbookcodeService.cacheFundbookcode();
                long startRunTime=System.currentTimeMillis();
                SubPubMessage subPubMessage= JsonUtils.readToT(message, SubPubMessage.class);
                Date startDate= DateTools.parseDateFromString_yyyyMMdd(subPubMessage.getStartDate(), logger);
                Date endDate= DateTools.parseDateFromString_yyyyMMdd(subPubMessage.getEndDate(), logger);
                Map<String, DelTableName> deleteTableNameMap = DateTools.getDeleteTableName(startDate, endDate, logger);
                //LinkedHashMap有顺序的
                for (String key : deleteTableNameMap.keySet()) {
                    long startMonthTime = System.currentTimeMillis();
                    //每个月
                    DelTableName delTableName = deleteTableNameMap.get(key);

                    String fundbookDayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + delTableName.getTableNameSuffix();
                    String fundbookMonthTableName = FundConstant.FUNDBOOKMONTH_TABLE_NAME_PRE + delTableName.getTableNameSuffix();

                    //1.2删除需要统计的数据
                    fundbookDayService.deleteAll(fundbookDayTableName);
                    fundbookMonthService.deleteAll(fundbookMonthTableName);

                    //3 每个表，每个用户，每天，每个账本一条数据
                    //3.2每个用户
                    Date startDateOfMonth = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr(),logger);
                    Date endDateOfMonth = DateTools.parseDateFromStr(simpleDateFormat_yyyyMMddhhmmss, delTableName.getEndStr() + "23:59:59", logger);
                    fundbookService.oneByOneUpdateBalance(startDateOfMonth, endDateOfMonth, subPubMessage.getBookcodes(), subPubMessage.getUsers());
                    fundbookDayService.insertFundBookDay(startDateOfMonth, endDateOfMonth, bookcodemap, subPubMessage.getUsers());
                    fundbookMonthService.insertFundBookMonth(startDateOfMonth, endDateOfMonth, bookcodemap, subPubMessage.getUsers());

                    long endRunTime=System.currentTimeMillis();

                    logger.info(delTableName.getTableNameSuffix()+"月,"+(double)(endRunTime-startMonthTime)/1000+"秒任务全部跑完了");
                }
                long endRunTime=System.currentTimeMillis();
                logger.info((double)(endRunTime-startRunTime)/1000+"秒任务全部跑完了");
            }catch (Exception e){
                logger.error("报错了异常被吃了继续监听",e);
            }
        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            logger.info("onPMessage:" + message);
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            logger.info("onSubscribe");
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            logger.info("onUnsubscribe");
        }

        @Override
        public void onPUnsubscribe(String pattern, int subscribedChannels) {
            logger.info("onPUnsubscribe");
        }

        @Override
        public void onPSubscribe(String pattern, int subscribedChannels) {
            logger.info("onPSubscribe");
        }
}
