package com.yjy.common.utils;


import com.yjy.entity.DelTableName;
import org.apache.commons.lang3.*;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 线程不安全
 * Created by Administrator on 2016/6/2.
 */
public class DateTools {

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");

    public synchronized static Date parseDateFromString_yyyyMM(String dateStr, Logger logger) {
        try {
            return simpleDateFormat_yyyyMM.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }

    public synchronized static Date parseDateFormat_yyyyMMddHHmmss(String dateStr, Logger logger) {
        try {
            return simpleDateFormat_yyyyMMddHHmmss.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误|"+dateStr+"|", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }

    public synchronized static Date parseDateFromString_yyyyMMdd(String dateStr,Logger logger) {
        try {
            return simpleDateFormat_yyyyMMdd.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误|"+dateStr+"|", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }

    public synchronized static Date parseDateFromStr(SimpleDateFormat simpleDateFormat, String dateStr,Logger logger) {

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            logger.error("日期转换报错", e);
        }
        return date;
    }

    //取前一天
    public synchronized  static Date getPreDayDate(Date startDate) {
        Date preDate = DateUtils.addDays(startDate, -1);
        return preDate;
    }

    public synchronized  static  String formate_yyyyMM( Date date) {

        return simpleDateFormat_yyyyMM.format(date);
    }

    public synchronized static  String formate_yyyyMMdd( Date date) {

        return simpleDateFormat_yyyyMMdd.format(date);
    }

    public synchronized static  String formate_yyyyMMddHHmmss( Date date) {

        return simpleDateFormat_yyyyMMddHHmmss.format(date);
    }

    //取后一天
    public synchronized static Date getNextDayDate(Date startDate) {
        Date preDate = DateUtils.addDays(startDate, 1);
        return preDate;
    }

    //获得当月最后一天
    public synchronized static  String getCurrentMonthLastDay(Date date,SimpleDateFormat simpleDateFormat){
        date=DateUtils.addMonths(date,1);
        date=DateUtils.setDays(date,1);
        date=DateUtils.addDays(date,-1);
        return simpleDateFormat.format(date);
    }




    //获得上个月最后一天
    public synchronized static String getPreMonthLastDay(Date date,SimpleDateFormat simpleDateFormat){
        date=DateUtils.setDays(date,1);
        date=DateUtils.addDays(date,-1);
        return simpleDateFormat.format(date);
    }


    //取后一月
    public synchronized static Date getNextMonthsDate(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, 1);
        return preDate;
    }

    /**
     * 计算需要执行统计的月和月内的天
     *
     * @return
     */
    public synchronized static Map<String, DelTableName> getDeleteTableName(Date startDate, Date endDate,Logger logger) {
        Map<String, DelTableName> map = new LinkedHashMap<>();//一定要有顺序
        while (endDate.compareTo(startDate) != -1) {
            String startStr = simpleDateFormat_yyyyMMdd.format(startDate);
            String tableNameSuffix = StringUtils.substring(startStr, 0, 6);//数据库中yyyyMM作为表名的后缀
            DelTableName delTableName = new DelTableName();
            if (map.get(tableNameSuffix) != null) {
                String startStrTemp = map.get(tableNameSuffix).getStartStr();
                delTableName.setTableNameSuffix(tableNameSuffix);
                delTableName.setStartStr(startStrTemp);
                delTableName.setEndStr(startStr);
            } else {
                delTableName.setTableNameSuffix(tableNameSuffix);
                delTableName.setStartStr(startStr);
                delTableName.setEndStr(startStr);
            }
            map.put(tableNameSuffix, delTableName);
            startDate = getNextDayDate(startDate);
        }
        logger.info("计算需要执行统计的月和月内的天:" + JsonUtils.toJson(map));
        return map;
    }

}
