package com.yjy.web.controller;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.utils.DateTools;
import com.yjy.entity.*;
import com.yjy.service.*;
import com.yjy.service.onlyMonth.OnlyMonthService;
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

}
