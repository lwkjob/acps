package com.yjy.web.controller;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.dao.Pagination;
import com.yjy.common.utils.DateTools;
import com.yjy.entity.*;
import com.yjy.service.*;
import com.yjy.service.onlyMonth.FundbookDayServiceNew;
import com.yjy.service.onlyMonth.FundbookMonthServiceNew;
import com.yjy.service.onlyMonth.ScheduleServiceNew;
import com.yjy.web.vo.UpdateBalanceVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/new")
public class LoginControllerNew {


    @Resource
    private FundbookService fundbookService;


    @Resource
    private FundbookcodeService fundbookcodeService;

    @Resource
    private FundbookDayService fundbookDayService;
    @Resource
    private FundbookDayServiceNew fundbookDayServiceNew;

    @Resource
    private FundbookMonthService fundbookMonthService;

    @Resource
    private FundbookMonthServiceNew fundbookMonthServiceNew;


    @Resource
    private UserService userService;

    @Resource
    private ScheduleService scheduleService;

    @Resource
    private ScheduleServiceNew scheduleServiceNew;


    private static Logger logger = LoggerFactory.getLogger(LoginControllerNew.class);



    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fundbook");
        List<Fundbookcode> bookcodes = fundbookcodeService.getFundbookcodes();
        modelAndView.addObject("bookcodes", bookcodes);
        return modelAndView;
    }

    @RequestMapping("/updateBalance")
    public String updateBalance(UpdateBalanceVo updateBalanceVo,
                                @RequestParam(value = "monthFund",required = false ,defaultValue = "0")int monthFund) {
        String returnUrl = "redirect:/index.shtml";

        List<Fundbookcode>  bookcodes = new ArrayList<>();

        Date startDate = null;
        Date endDate = null;
        startDate = DateTools.parseDateFromString_yyyyMM(updateBalanceVo.getStartDate(),logger);
        endDate = DateTools.parseDateFromString_yyyyMM(updateBalanceVo.getEndDate(),logger);

        if (startDate.compareTo(endDate) == 1) {//startDate > endDate
            logger.info("日期填写错误,开始日期" + updateBalanceVo.getStartDate() + ">结束日期" + updateBalanceVo.getEndDate() + "");
            return returnUrl;
        }


        if (!StringUtils.isBlank(updateBalanceVo.getAccBook())) {
            String[] accbookArray = StringUtils.split(updateBalanceVo.getAccBook(), "&&");
            Fundbookcode bookcode = new Fundbookcode();
            bookcode.setFundtype(Integer.parseInt(accbookArray[0]));
            bookcode.setBookcode(accbookArray[1]);
            bookcodes.add(bookcode);
        }


        List<UserBasicInfo> users = null;
        List<Integer> userids =null;
        if (updateBalanceVo.getUserids()!=null) {
             userids = getUserids(updateBalanceVo.getUserids());
            users =userService.getUsersByUserids(userids);
        }
        Map<Integer,List<Fundbookcode>>  bookcodemap=  cacheFndbookcode();


        long startTime = System.currentTimeMillis();

         switch (monthFund){
             case 1:
                 //日清
                 startDate = DateTools.parseDateFromString_yyyyMMdd(updateBalanceVo.getStartDate(), logger);
                 endDate = DateTools.parseDateFromString_yyyyMMdd(updateBalanceVo.getEndDate(), logger);
                 fundbookDayService.insertFundBookDay(startDate, endDate, bookcodemap, users);
                 break;
             case 2:
                 //月结
                 fundbookMonthService.insertFundBookMonth(startDate, endDate, bookcodemap,users);
                 break;
             default:
                 //更新余额
                 fundbookService.oneByOneUpdateBalance(startDate, endDate, bookcodes, userids);
         }

        long endTime = System.currentTimeMillis();
        logger.info("总的执行时间:" + (float) (endTime - startTime) / 1000 + "秒");

        return returnUrl;
    }
    private List<Integer> getUserids(String useridsStr){
        List<Integer> useridList=new ArrayList<>();
        String[] useridsArray = StringUtils.split(useridsStr, ",");
        for (String userid:useridsArray){
            useridList.add(Integer.parseInt(StringUtils.trimToEmpty(userid)));
        }
        return useridList;
    }

    //不写日清
    @RequestMapping("/updateBalance2")
    public String updateBalance2(UpdateBalanceVo updateBalanceVo,
                                @RequestParam(value = "monthFund",required = false ,defaultValue = "0")int monthFund) {
        String returnUrl = "redirect:/index.shtml";

        List<Fundbookcode>  bookcodes = new ArrayList<>();

        Date startDate = null;
        Date endDate = null;
        startDate = DateTools.parseDateFromString_yyyyMM(updateBalanceVo.getStartDate(),logger);
        endDate = DateTools.parseDateFromString_yyyyMM(updateBalanceVo.getEndDate(),logger);

        if (startDate.compareTo(endDate) == 1) {//startDate > endDate
            logger.info("日期填写错误,开始日期" + updateBalanceVo.getStartDate() + ">结束日期" + updateBalanceVo.getEndDate() + "");
            return returnUrl;
        }


        if (!StringUtils.isBlank(updateBalanceVo.getAccBook())) {
            String[] accbookArray = StringUtils.split(updateBalanceVo.getAccBook(), "&&");
            Fundbookcode bookcode = new Fundbookcode();
            bookcode.setFundtype(Integer.parseInt(accbookArray[0]));
            bookcode.setBookcode(accbookArray[1]);
            bookcodes.add(bookcode);
        }


        List<UserBasicInfo> users = null;
        if (updateBalanceVo.getUserids()!=null) {
            List<Integer> userids = getUserids(updateBalanceVo.getUserids());
            users =userService.getUsersByUserids(userids);
        }
        Map<Integer,List<Fundbookcode>>  bookcodemap=  cacheFndbookcode();


        long startTime = System.currentTimeMillis();

        switch (monthFund){
            case 1:
                startDate = DateTools.parseDateFromString_yyyyMMdd(updateBalanceVo.getStartDate(), logger);
                endDate = DateTools.parseDateFromString_yyyyMMdd(updateBalanceVo.getEndDate(), logger);
                fundbookDayServiceNew.insertFundBookDay(startDate, endDate, bookcodemap, users);
                break;
            case 2:
                fundbookMonthServiceNew.insertFundBookMonth(startDate, endDate, bookcodemap,users);
                break;
            default:
//                fundbookService.oneByOneUpdateBalance(startDate, endDate, bookcodes, users);
        }

        long endTime = System.currentTimeMillis();
        logger.info("总的执行时间:" + (float) (endTime - startTime) / 1000 + "秒");

        return returnUrl;
    }

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

    @RequestMapping("/fundbooklist")
    public ModelAndView getFundbookList(@RequestParam(value = "currentPage",defaultValue = "1")int currentPage){
        ModelAndView mv=new ModelAndView();
        int pageSize=5;
        Pagination<Fundbook> pagination=new Pagination(currentPage,pageSize);
        Fundbook example=new Fundbook();
        long startTime=0;
        long endTime=0;
        String tablename="fundbook201309";
        List<Fundbook> fundbooks =  fundbookService.selectPageListByExample(example,tablename,startTime,endTime,pagination);
        pagination.setData(fundbooks);
        mv.addObject("pagination", pagination);
        mv.setViewName("fundbooklist");
        return  mv;
    }

    @RequestMapping("/cacheUser")
    public ModelAndView cacheUser(String start,String end){
        ModelAndView mv=new ModelAndView();

        userService.cacheUsers(start, end);

        mv.setViewName("redirect:/index.shtml");
        return  mv;
    }


    //分页更新上月最后一天的余额到redis,从日结表中查
    @RequestMapping("/loadPreMonthBalance")
    public ModelAndView loadPreMonthBalance(int start,@RequestParam(value = "userids",required = false)String useridsStr){
        List<Integer> userids=null;
        if(useridsStr!=null){
            userids=getUserids(useridsStr);
        }
        ModelAndView mv=new ModelAndView();
        Fundbookday fundbookday=new Fundbookday();
        fundbookday.setBookdate(start);
        fundbookDayService.getByBookdete(fundbookday,userids);
        mv.setViewName("redirect:/index.shtml");
        return  mv;
    }


    //
    @RequestMapping("/dayreport")
    public ModelAndView dayreport(String start,String end,@RequestParam(value = "userids",required = false)String useridsStr){
        List<Integer> useridList=null;
        if(useridsStr!=null){
            useridList=getUserids(useridsStr);
        }
        List<UserBasicInfo> users = userService.getUsersByUserids(useridList);
        ModelAndView mv=new ModelAndView();
        scheduleService.dayreport(start, end, null, users);
        mv.setViewName("redirect:/index.shtml");
        return  mv;
    }

    //
    @RequestMapping("/dayreport2")
    public ModelAndView dayreport2(String start,String end){
        ModelAndView mv=new ModelAndView();
        scheduleServiceNew.dayreport(start,end,null,null);
        mv.setViewName("redirect:/index.shtml");
        return  mv;
    }




}
