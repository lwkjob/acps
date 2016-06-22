//package com.yjy.web.service;
//
//import com.yjy.common.utils.DateTools;
//import com.yjy.common.utils.JsonUtils;
//import com.yjy.common.constant.FundConstant;
//import com.yjy.entity.*;
//import com.yjy.service.FundbookdayRunner;
//import org.apache.commons.collections.map.HashedMap;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * 创建任务
// * <p/>
// * Created by Administrator on 2016/6/12.
// */
//public class CreateSchedule {
//
//    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
//    private static SimpleDateFormat simpleDateFormat_yyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");
//    private static Logger logger= LoggerFactory.getLogger(CreateSchedule.class);
//
//    public int schedule(Date startDate, Date endDate, Map<Integer, List<Fundbookcode>> bookcodemap) {
//
//        long start = System.currentTimeMillis();
//
//        //1.1根据时间区间算出所有需要删数据的表名
//        Map<String, DelTableName> deleteTableNameMap = getDeleteTableName(startDate, endDate);
//
//        for (String key : deleteTableNameMap.keySet()) {
//            //每个月
//            DelTableName delTableName = deleteTableNameMap.get(key);
//
//            String fundbookDayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + delTableName.getTableNameSuffix();
//
//            //3 每个表，每个用户,每天,每个账本一条数据
//            //3.2每个用户
//            Date startDateByTable = DateTools.parseDateFromString_yyyyMMdd(delTableName.getStartStr(), logger);
//            Date endDateByTable = DateTools.parseDateFormat_yyyyMMddHHmmss(delTableName.getEndStr() + "23:59:59", logger);
//
//            //2 统计每天每个用户每个账本数据
//            Fundbook fundbookExample = new Fundbook(); //查询条件
//            String fundbookTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + delTableName.getTableNameSuffix();
//
//            List<Fundbook> fundbooks = getFundbooks(
//                    fundbookExample,
//                    fundbookTableName,
//                    startDateByTable.getTime() / 1000l,
//                    endDateByTable.getTime() / 1000l);
//
//            //本月的活跃用户数
//            List<UserBasicInfo> userOfMonthList = userBasicExtMapper.getUsers(0, 0, 0, 0, endDateByTable.getTime()/1000l);
//
//            //当期月业务发生的每个用户每个账本
//            Map<String, Fundbookday> fundbookdayMap = getFundbookDay(fundbooks);
//
//            while (endDateByTable.compareTo(startDateByTable) != -1) {//1.每天
//                String bookDateStr = DateTools.formate_yyyyMMdd(startDateByTable);
//                Date createEndTime = DateTools.parseDateFormat_yyyyMMddHHmmss(bookDateStr + "23:59:59", logger);
//
//                Date preDate = getPreDayDate(startDateByTable);
//                String preDateStr = simpleDateFormat_yyyyMMdd.format(preDate);
//                //刷当天的余额到redis
//                cacheBalance(userOfMonthList,bookcodemap,preDateStr,bookDateStr,fundbookdayMap);
//
//                FundbookdayRunner fundbookdayRunner = new FundbookdayRunner();
//                fundbookdayRunner.setBookcodemap(bookcodemap);
//                fundbookdayRunner.setUserCreateEndTime(createEndTime.getTime() / 1000l);
//                fundbookdayRunner.setBookDate(startDateByTable);
//                fundbookdayRunner.setUserBasicExtMapper(userBasicExtMapper);
//                fundbookdayRunner.setFundbookdayExtMapper(fundbookdayExtMapper);
//                fundbookdayRunner.setFundbookdayMap(fundbookdayMap);
//                fundbookdayRunner.setBookDateStr(bookDateStr);
//                fundbookdayRunner.setPreDateStr(preDateStr);
//                fundbookdayRunner.setFundbookDayTableName(fundbookDayTableName);
//                fundbookdayRunner.setJedisTemplate(jedisTemplate);
//                executorService.execute(fundbookdayRunner);
//                startDateByTable = getNextDayDate(startDateByTable);
//            }
//        }
//        long end = System.currentTimeMillis();
//        logger.info("计算完了" + (float) (end - start) / 1000 + "秒");
//        return 1;
//
//    }
//
//    /**
//     * 计算需要删除的表名和日期区间
//     *
//     * @return
//     */
//    public Map<String, DelTableName> getDeleteTableName(Date startDate, Date endDate) {
//        Map<String, DelTableName> map = new HashedMap();
//        while (endDate.compareTo(startDate) != -1) {
//            String startStr = simpleDateFormat_yyyyMMdd.format(startDate);
//            String tableNameSuffix = StringUtils.substring(startStr, 0, 6);//数据库中yyyyMM作为表名的后缀
//            DelTableName delTableName = new DelTableName();
//            if (map.get(tableNameSuffix) != null) {
//                String startStrTemp = map.get(tableNameSuffix).getStartStr();
//                delTableName.setTableNameSuffix(tableNameSuffix);
//                delTableName.setStartStr(startStrTemp);
//                delTableName.setEndStr(startStr);
//            } else {
//                delTableName.setTableNameSuffix(tableNameSuffix);
//                delTableName.setStartStr(startStr);
//                delTableName.setEndStr(startStr);
//            }
//            map.put(tableNameSuffix, delTableName);
//            startDate = getNextDayDate(startDate);
//        }
//        logger.info("需要删除的表名和日期区间:" + JsonUtils.toJson(map));
//        return map;
//    }
//}
