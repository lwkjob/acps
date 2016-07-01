package com.yjy.test.funbook;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/6/30.
 */
public class DateTest {
    Logger logger= LoggerFactory.getLogger(DateTest.class);
    @Test
    public void nimei(){

        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(1451579235*1000l);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info(simpleDateFormat.format(calendar.getTime()));
    }

}


