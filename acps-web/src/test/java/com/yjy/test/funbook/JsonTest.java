package com.yjy.test.funbook;

import com.yjy.common.utils.JsonUtils;
import com.yjy.service.distributed.ScheduleVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Administrator on 2016/6/28.
 */
public class JsonTest {

    Logger logger= LoggerFactory.getLogger(JsonTest.class);

    @Test
    public void testn(){
        String j="{\n" +
                "  \"bookdate\" : \"20160628\",\n" +
                "  \"totalTask\" : 36\n" +
                "}";
        ScheduleVo scheduleVo1=new ScheduleVo("20160628",36);

        String s2 = JsonUtils.toJson(scheduleVo1);


        ScheduleVo scheduleVo=  JsonUtils.readToT(s2, ScheduleVo.class);
        logger.info(scheduleVo.getBookdate());
    }
}
