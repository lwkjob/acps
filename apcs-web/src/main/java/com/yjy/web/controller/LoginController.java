package com.yjy.web.controller;

import com.yjy.common.dao.Pagination;
import com.yjy.common.utils.DateTools;
import com.yjy.constant.FundConstant;
import com.yjy.entity.Fundbook;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.FundbookcodeExample;
import com.yjy.entity.UserBasicInfo;
import com.yjy.schedule.DayReportJob;
import com.yjy.service.*;
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
public class LoginController {


    @Resource
    private FundbookService fundbookService;

    @Resource
    private FundbookcodeService fundbookcodeService;

    @Resource
    private FundbookDayService fundbookDayService;

    @Resource
    private FundbookMonthService fundbookMonthService;



    private static Logger logger = LoggerFactory.getLogger(LoginController.class);



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
            String[] accbookArray = StringUtils.split(updateBalanceVo.getAccBook(), "-");
            Fundbookcode bookcode = new Fundbookcode();
            bookcode.setFundtype(Integer.parseInt(accbookArray[0]));
            bookcode.setBookcode(updateBalanceVo.getAccBook());
            bookcodes.add(bookcode);
        }

        //判断用户
        List<UserBasicInfo> users = null;
        int typeid=0;
        if (updateBalanceVo.getUserid() == null || updateBalanceVo.getUserid() <= 0) {
            //查询所有的用户,(注册时间小于当前时间)
              typeid=updateBalanceVo.getTypeid()==null?0:updateBalanceVo.getTypeid();
        } else {
            users=new ArrayList<>();
            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setUserid(updateBalanceVo.getUserid());
            users.add(userBasicInfo);
        }
        Map<Integer,List<Fundbookcode>>  bookcodemap=  cacheFndbookcode();


        long startTime = System.currentTimeMillis();

         switch (monthFund){
             case 1:
                 startDate = DateTools.parseDateFromString_yyyyMMdd(updateBalanceVo.getStartDate(), logger);
                 endDate = DateTools.parseDateFromString_yyyyMMdd(updateBalanceVo.getEndDate(), logger);
                 fundbookDayService.insertFundBookDay(startDate, endDate, bookcodemap, users);
                 break;
             case 2:
                 fundbookMonthService.insertFundBookMonth(startDate, endDate, bookcodemap);
                 break;
             default:
                 fundbookService.oneByOneUpdateBalance(startDate, endDate, bookcodes, typeid,users);
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
        mv.addObject("pagination",pagination);
        mv.setViewName("fundbooklist");
        return  mv;
    }



}
