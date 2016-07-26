package com.yjy.web.controller;

import com.yjy.service.fundbook.quartz.QuartzJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 接收贵哥基础数据统计完成的通知请求
 */
@Controller
public class JobController {


    @Resource
    private QuartzJob quartzJob;



    private static Logger logger = LoggerFactory.getLogger(JobController.class);




    @RequestMapping("/doWork")
    @ResponseBody
    public Object doWork(String jobDate) {
        quartzJob.doWork();
        return "";
    }


}
