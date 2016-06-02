package com.yjy.common.utils;


import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/2.
 */
public class DateTools {

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    public static Date parseDateFromString_yyyyMM(String dateStr, Logger logger) {
        try {
            return simpleDateFormat_yyyyMM.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }

    public static Date parseDateFromString_yyyyMMdd(String dateStr,Logger logger) {
        try {
            return simpleDateFormat_yyyyMMdd.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }
}
