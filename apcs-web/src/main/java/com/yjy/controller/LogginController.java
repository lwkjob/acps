package com.yjy.controller;

import com.yjy.entity.Bookcode;
import com.yjy.entity.UserBasicInfo;
import com.yjy.schedule.DayReportJob;
import com.yjy.service.FundbookService;
import com.yjy.web.vo.UpdateBalanceVo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
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
public class LogginController {


    @Resource
    private FundbookService fundbookService;

    private static Logger logger = LoggerFactory.getLogger(DayReportJob.class);


    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("fundbook");
        List<Bookcode> bookcodes = fundbookService.getBookcodes();
        modelAndView.addObject("bookcodes", bookcodes);
        return modelAndView;
    }

    @RequestMapping("/updateBalance")
    public String updateBalance(UpdateBalanceVo updateBalanceVo) {
        String returnUrl = "redirect:/index.shtml";

        List<Bookcode> bookcodes = null;

        Date startDate = null;
        Date endDate = null;
        startDate = fundbookService.parseDateFromString(updateBalanceVo.getStartDate());
        endDate = fundbookService.parseDateFromString(updateBalanceVo.getEndDate());

        if (startDate.compareTo(endDate) == 1) {
            logger.info("日期填写错误,开始日期" + updateBalanceVo.getStartDate() + ">结束日期" + updateBalanceVo.getEndDate() + "");
            return returnUrl;
        }

        //没传账本 就更新所有账本
        if (StringUtils.isBlank(updateBalanceVo.getAccBook())) {
            bookcodes = fundbookService.getBookcodes();
        } else {
            bookcodes = new ArrayList<>();
            String[] accbookArray = StringUtils.split(updateBalanceVo.getAccBook(), "-");
            Bookcode bookcode = new Bookcode();
            bookcode.setBookcodeone(Integer.parseInt(accbookArray[0]));
            bookcode.setBookcodetwo(Integer.parseInt(accbookArray[1]));
            bookcode.setBookcodethree(Integer.parseInt(accbookArray[2]));
            bookcodes.add(bookcode);
        }

        //判断用户
        List<UserBasicInfo> users = new ArrayList<>();

        if (updateBalanceVo.getUserId() == null || updateBalanceVo.getUserId() <= 0) {
            //查询所有的用户
            Map<String, Object> map = new HashedMap();
            map.put("typeid", 2);
            int typeid=updateBalanceVo.getTypeid();
            users = fundbookService.getUsers(0,0,typeid,0,0);
        } else {

            UserBasicInfo userBasicInfo = new UserBasicInfo();
            userBasicInfo.setUserid(updateBalanceVo.getUserId());
            users.add(userBasicInfo);
        }

        long startTime = System.currentTimeMillis();

        fundbookService.oneByOneUpdateBalance(startDate, endDate, bookcodes,users);

        long endTime = System.currentTimeMillis();
        logger.info("总的执行时间:" + (float) (endTime - startTime) / 1000 + "秒");

        return returnUrl;
    }


}
