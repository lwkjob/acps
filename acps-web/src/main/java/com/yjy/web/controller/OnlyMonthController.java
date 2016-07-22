package com.yjy.web.controller;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.utils.DateTools;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.FundbookcodeExample;
import com.yjy.common.entity.fundbook.Fundbookmonth;
import com.yjy.service.fundbook.FundbookcodeService;
import com.yjy.service.fundbook.onlyMonth.OnlyMonthService;
import com.yjy.web.vo.UpdateBalanceVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenke on 16/5/28.
 */

@Controller
@RequestMapping("/onlymonth")
public class OnlyMonthController {


    @Resource
    private FundbookcodeService fundbookcodeService;

    @Resource
    private OnlyMonthService onlyMonthService;




    private static Logger logger = LoggerFactory.getLogger(OnlyMonthController.class);


    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("onlymonth");
        return modelAndView;
    }

    @RequestMapping("/shceduleAll")
    public ModelAndView shceduleAll(String start,String end){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("onlymonth");
        Date date1 = DateTools.parseDateFromString_yyyyMM(start, logger);
        Date date2 = DateTools.parseDateFromString_yyyyMM(end, logger);

        while (date2.compareTo(date1)!=-1){
            Date preMonth = new DateTime(date1).plusMonths(-1).toDate();
            int preMonthInt=Integer.parseInt(DateTools.formate_yyyyMM(preMonth)) ;
            loadCache(preMonthInt);
           String  loadDate=DateTools.formate_yyyyMM(date1);
            UpdateBalanceVo updateBalanceVo=new UpdateBalanceVo();
            updateBalanceVo.setStartDate(loadDate);
            updateBalanceVo.setEndDate(loadDate);
            oneByOneUpdateBalance(updateBalanceVo);
            createFundmonthtemp(loadDate);
            insertFundBookMonth(loadDate, loadDate);
            date1=DateTools.getNextMonthsDate(date1);
        }

        return modelAndView;
    }


    //加载上个月的余额到redis
    @RequestMapping("/loadCache")
    public ModelAndView loadCache(int loadDate) {
        ModelAndView modelAndView = new ModelAndView();
        Fundbookmonth fundbookmonth = new Fundbookmonth();
        fundbookmonth.setBookdate(loadDate);
        onlyMonthService.getByBookdete(fundbookmonth, null);
        modelAndView.setViewName("onlymonth");
        return modelAndView;
    }



    //缓存账本到内存中
    private Map<Integer, List<Fundbookcode>> cacheFndbookcode() {
        Map<Integer, List<Fundbookcode>> map = new HashedMap();
        FundbookcodeExample example = new FundbookcodeExample();
        example.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_BUYER);
        map.put(FundConstant.TYPEID_BUYER, fundbookcodeService.getFundbookcodesByExample(example));


        FundbookcodeExample example2 = new FundbookcodeExample();
        example2.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_SALES);
        map.put(FundConstant.TYPEID_SALES, fundbookcodeService.getFundbookcodesByExample(example2));

        FundbookcodeExample example3 = new FundbookcodeExample();
        example3.createCriteria().andRolecodeEqualTo(FundConstant.ROLECODE_PLATFORM);
        map.put(FundConstant.TYPEID_PLATFORM, fundbookcodeService.getFundbookcodesByExample(example3));

        return map;
    }


    /**
     * 根据日期段一个月一个月的更新余额
     */
    @RequestMapping("/oneByOneUpdateBalance")
    public ModelAndView oneByOneUpdateBalance(UpdateBalanceVo updateBalanceVo) {
        ModelAndView modelAndView = new ModelAndView();
        Date startDate = null;
        Date endDate = null;
        startDate = DateTools.parseDateFromString_yyyyMM(updateBalanceVo.getStartDate(),logger);
        endDate = DateTools.parseDateFromString_yyyyMM(updateBalanceVo.getEndDate(),logger);
        List<Integer> userids =null;
        if (StringUtils.isNotBlank(updateBalanceVo.getUserids())) {
            userids = getUserids(updateBalanceVo.getUserids());
        }
        onlyMonthService.oneByOneUpdateBalance(startDate, endDate, null, userids);
        modelAndView.setViewName("onlymonth");
        return modelAndView;
    }



    private List<Integer> getUserids(String useridsStr){
        List<Integer> useridList=new ArrayList<>();
        String[] useridsArray = StringUtils.split(useridsStr, ",");
        for (String userid:useridsArray){
            useridList.add(Integer.parseInt(StringUtils.trimToEmpty(userid)));
        }
        return useridList;
    }

    //创建月结数据临时表
    @RequestMapping("/createFundmonthtemp")
    public ModelAndView createFundmonthtemp(String dateStr){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("onlymonth");
        if(StringUtils.isBlank(dateStr)){
            logger.info("参数有错误");
            return modelAndView;
        }
        onlyMonthService.createFundmonthtemp(FundConstant.FUNDMONTHTEMP_TABLE_NAME_PRE + dateStr, FundConstant.FUNDBOOK_TABLE_NAME_PRE + dateStr);
        logger.info("创建成功" + dateStr);
        return modelAndView;
    }

    //统计月结数据
    @RequestMapping("/insertFundBookMonth")
    public ModelAndView insertFundBookMonth(String start, String end) {
        ModelAndView modelAndView = new ModelAndView();
        Date startDate = DateTools.parseDateFromString_yyyyMM(start, logger);
        Date endDate = DateTools.parseDateFromString_yyyyMM(end, logger);
        onlyMonthService.insertFundBookMonth(startDate, endDate, cacheFndbookcode(), null);
        modelAndView.setViewName("onlymonth");
        return modelAndView;
    }

}
