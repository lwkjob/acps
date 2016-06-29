package com.yjy.web.controller;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.zk.ZkTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/6/29.
 */
@Controller
@RequestMapping("/admin")
public class DataInitController {

    @Resource
    private ZkTemplate zkTemplate;


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
        mv.setViewName("/admin");
        return mv;
    }
}
