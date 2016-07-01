package com.yjy.test.funbook;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.utils.DateTools;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.FundbookcodeExample;
import com.yjy.entity.Fundbookmonth;
import com.yjy.service.FundbookcodeService;
import com.yjy.service.onlyMonth.OnlyMonthService;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:test-config.xml") // 加载配置
public class OnlyMonthTest {

    private  static Logger logger= LoggerFactory.getLogger(OnlyMonthTest.class);

    @Resource
    private FundbookcodeService fundbookcodeService;

    @Resource
    private OnlyMonthService onlyMonthService;

    @Test
    public void loadCache(){
        Fundbookmonth fundbookmonth=new Fundbookmonth();
        fundbookmonth.setBookdate(201512);
       // onlyMonthService.getByBookdete(fundbookmonth, null);
    }

    @Test
    public void insertFundBookMonth(){
        Date startDate= DateTools.parseDateFromString_yyyyMM("201601",logger);
        Date endDate= DateTools.parseDateFromString_yyyyMM("201601",logger);
       // onlyMonthService.insertFundBookMonth(startDate, endDate, cacheFndbookcode(), null);
    }

    @Test
    public void oneByOneUpdateBalance(){
        Date startDate= DateTools.parseDateFromString_yyyyMM("201601",logger);
        Date endDate= DateTools.parseDateFromString_yyyyMM("201601",logger);
        //onlyMonthService.oneByOneUpdateBalance(startDate, endDate, null, null);
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


    @Test
    public  void createFundmonthtemp(){
        //onlyMonthService.createFundmonthtemp("fundmonthtemp201603","fundbook201603");
    }
}
