package com.yjy.service.fundbook.quartz;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.FundbookcodeExample;
import com.yjy.common.entity.fundbook.Fundbookmonth;
import com.yjy.common.utils.DateTools;
import com.yjy.service.fundbook.FundbookDayService;
import com.yjy.service.fundbook.FundbookMonthService;
import com.yjy.service.fundbook.FundbookcodeService;
import com.yjy.service.fundbook.onlyMonth.OnlyMonthService;
import org.apache.commons.collections.map.HashedMap;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/1.
 */
public class QuartzTest {
    private static final Logger logger = LoggerFactory.getLogger(QuartzTest.class);


    @Resource
    private OnlyMonthService  onlyMonthService;

    @Resource
    private FundbookcodeService fundbookcodeService;

    @Resource
    private FundbookDayService fundbookDayService;

    @Resource
    private FundbookMonthService fundbookMonthService;



    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");



    public void doWorkMd(){
        Date lastDay = DateTime.now().plusDays(-1).toDate();
        String jobDateStr=DateTools.formate_yyyyMMdd(lastDay) ;//"20131031";

        logger.info("开始刷余额"+jobDateStr);
        Map<Integer,List<Fundbookcode>> bookcodemap=  cacheFndbookcode();
        //刷余额
        onlyMonthService.oneByOneUpdateBalanceByDay(jobDateStr,null);
        Date startDate = DateTools.parseDateFromString_yyyyMMdd(jobDateStr, logger);
        Date endDate = DateTools.parseDateFromString_yyyyMMdd(jobDateStr, logger);
        logger.info("开始跑日清"+jobDateStr);
        fundbookDayService.insertFundBookDay(startDate, endDate, bookcodemap, null, true);
        String currentMonthLastDay = DateTools.getCurrentMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);
        if(currentMonthLastDay.equals(jobDateStr)){
            logger.info("最后一天跑月结"+jobDateStr);
            fundbookMonthService.insertFundBookMonth(startDate,startDate,bookcodemap,null);
        }
    }



    //缓存账本到内存中
    private Map<Integer,List<Fundbookcode>> cacheFndbookcode(){
        Map<Integer,List<Fundbookcode>> map=new HashedMap();
        FundbookcodeExample example=new FundbookcodeExample();
        example.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_BUYER);
        map.put(FundConstant.TYPEID_BUYER, fundbookcodeService.getFundbookcodesByExample(example));


        FundbookcodeExample example2=new FundbookcodeExample();
        example2.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_SALES);
        map.put(FundConstant.TYPEID_SALES, fundbookcodeService.getFundbookcodesByExample(example2));

        FundbookcodeExample example3=new FundbookcodeExample();
        example3.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_PLATFORM);
        map.put(FundConstant.TYPEID_PLATFORM, fundbookcodeService.getFundbookcodesByExample(example3));

        return map;
    }


    public static void main(String[] args) {

    }
}
