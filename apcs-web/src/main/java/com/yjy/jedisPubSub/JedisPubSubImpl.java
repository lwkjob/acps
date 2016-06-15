package com.yjy.jedisPubSub;

import com.yjy.common.utils.DateTools;
import com.yjy.common.utils.JsonUtils;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/15.
 */
@Component("jedisPubSubImpl")
public class JedisPubSubImpl extends JedisPubSub {


    @Resource
    private FundbookService fundbookService;

    @Resource
    private FundbookDayService fundbookDayService;

    @Resource
    private FundbookMonthService fundbookMonthService;

    @Resource
    private FundbookcodeService fundbookcodeService;

    private static final Logger logger = LoggerFactory.getLogger(JedisPubSubImpl.class);



        @Override
        public void onMessage(String channel, String message) {
            Map<Integer, List<Fundbookcode>> bookcodemap = fundbookcodeService.cacheFndbookcode();
            logger.info("onMessage:" + message);
            try {
                long startRunTime=System.currentTimeMillis();
                SubPubMessage subPubMessage= JsonUtils.readToT(message,SubPubMessage.class);
                Date startDate= DateTools.parseDateFromString_yyyyMMdd(subPubMessage.getStartDate(), logger);

                Date endDate= DateTools.parseDateFromString_yyyyMMdd(subPubMessage.getEndDate(),logger);

                fundbookService.oneByOneUpdateBalance(startDate, endDate, subPubMessage.getBookcodes(), subPubMessage.getUsers());

                fundbookDayService.insertFundBookDay(startDate, endDate, bookcodemap, subPubMessage.getUsers());

                fundbookMonthService.insertFundBookMonth(startDate, endDate, bookcodemap);
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
