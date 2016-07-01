package com.yjy.web.controller;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.zk.ZkTemplate;
import com.yjy.entity.UserBasicInfo;
import com.yjy.service.UserService;
import com.yjy.service.distributed.ScheduleServiceDayNew;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
@Controller
@RequestMapping("/distributed")
public class DistributedController {

    @Resource
    private ZkTemplate zkTemplate;


    @Resource
    private ScheduleServiceDayNew scheduleServiceDayNew;

    @Resource
    private UserService userService;



    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("distributed");
        return modelAndView;
    }

    //初始化zk节点
    @RequestMapping("/setZk")
    public ModelAndView setZk(){
        if(!zkTemplate.checkExists(FundConstant.REPORT_OF_DAY_SUB_LISSTENER)){
            zkTemplate.createPersistentNode(FundConstant.REPORT_OF_DAY_SUB_LISSTENER, "");
        }else {
            zkTemplate.setData(FundConstant.REPORT_OF_DAY_SUB_LISSTENER, "");
        }
        if(!zkTemplate.checkExists(FundConstant.REPORT_OF_DAY_PUB_LISSTENER)){
            zkTemplate.createPersistentNode(FundConstant.REPORT_OF_DAY_PUB_LISSTENER, null);
        }
        ModelAndView mv=new ModelAndView();
        mv.setViewName("distributed");
        return mv;
    }

    //分布式执行
    @RequestMapping("/scheduleServiceDayNew")
    public ModelAndView scheduleServiceDayNew(String start,String end,@RequestParam(value = "userids",required = false)String useridsStr){
        ModelAndView mv=new ModelAndView();
        List<UserBasicInfo> users=null;
        if(StringUtils.isNotBlank(useridsStr)){
            List<Integer>   useridList=getUserids(useridsStr);
            users=userService.getUsersByUserids(useridList);
        }
        scheduleServiceDayNew.scheduleCreate(start, end,users);
        mv.setViewName("distributed");
        return  mv;
    }

    private List<Integer> getUserids(String useridsStr){
        List<Integer> useridList=new ArrayList<>();
        String[] useridsArray = StringUtils.split(useridsStr, ",");
        for (String userid:useridsArray){
            useridList.add(Integer.parseInt(StringUtils.trimToEmpty(userid)));
        }
        return useridList;
    }

}
