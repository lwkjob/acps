package com.yjy.common.utils;


import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/2.
 */
public class DateTools {

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");

    public static Date parseDateFromString_yyyyMM(String dateStr, Logger logger) {
        try {
            return simpleDateFormat_yyyyMM.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }

    public static Date parseDateFormat_yyyyMMddHHmmss(String dateStr, Logger logger) {
        try {
            return simpleDateFormat_yyyyMMddHHmmss.parse(dateStr);
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

    public static Date parseDateFromStr(SimpleDateFormat simpleDateFormat, String dateStr,Logger logger) {

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            logger.error("日期转换报错", e);
        }
        return date;
    }


    public static  String formate_yyyyMM( Date date) {

        return simpleDateFormat_yyyyMM.format(date);
    }

    public static  String formate_yyyyMMdd( Date date) {

        return simpleDateFormat_yyyyMMdd.format(date);
    }

    public static  String formate_yyyyMMddHHmmss( Date date) {

        return simpleDateFormat_yyyyMMddHHmmss.format(date);
    }

    //取后一天
    public static Date getNextDayDate(Date startDate) {
        Date preDate = DateUtils.addDays(startDate, 1);
        return preDate;
    }
}
