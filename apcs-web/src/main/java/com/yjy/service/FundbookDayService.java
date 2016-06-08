package com.yjy.service;


import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.utils.JsonUtils;
import com.yjy.constant.FundConstant;
import com.yjy.entity.*;
import com.yjy.repository.mapper.FundbookExtMapper;
import com.yjy.repository.mapper.FundbookdayExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 日清逻辑
 * Created by Administrator on 2016/5/31.
 */
@Service("fundbookDayService")
public class FundbookDayService {

    @Resource
    private FundbookdayExtMapper fundbookdayExtMapper;

    @Resource
    private FundbookExtMapper fundbookExtMapper;

    @Resource
    private UserBasicExtMapper userBasicExtMapper;

    @Resource
    private JedisTemplate jedisTemplate;

    private static Logger logger = LoggerFactory.getLogger(FundbookDayService.class);

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat simpleDateFormat_yyyyMMddhhmmss = new SimpleDateFormat("yyyyMMddHH:mm:ss");

    private static ExecutorService executorService = Executors.newFixedThreadPool(30);

    private static Calendar calendar = Calendar.getInstance();

    //取前一天
    public Date getPreDayDate(Date startDate) {
        Date preDate = DateUtils.addDays(startDate, -1);
        return preDate;
    }

    //取后一天
    public Date getNextDayDate(Date startDate) {
        Date preDate = DateUtils.addDays(startDate, 1);
        return preDate;
    }


    //插入日清数据
    public int insertFundBookDay(Date startDate, Date endDate, Map<Integer, List<Fundbookcode>> bookcodemap, List<UserBasicInfo> users) {


//        if(bookcodes==null || bookcodes.size()==0){
//            bookcodes=fundbookcodeMapper.selectByExample(null);
//        }
        //delete日清表指定时间之前的数据

        long start = System.currentTimeMillis();

        //1.1根据时间区间算出所有需要删数据的表名
        Map<String, DelTableName> deleteTableNameMap = getDeleteTableName(startDate, endDate);

        for (String key : deleteTableNameMap.keySet()) {
            //每个月
            DelTableName delTableName = deleteTableNameMap.get(key);
            //1.2删除需要统计的数据
            String fundbookDayTableName = FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE + delTableName.getTableNameSuffix();


            //3 每个表，每个用户，每天，每个账本一条数据
            //3.2每个用户
            Date startDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getStartStr());
            Date endDateByTable = parseDateFromStr(simpleDateFormat_yyyyMMddhhmmss, delTableName.getEndStr() + "23:59:59");

            //2 统计每天每个用户每个账本数据
            Fundbook fundbookExample = new Fundbook(); //查询条件
            String fundbookTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + delTableName.getTableNameSuffix();

            List<Fundbook> fundbooks = getFundbooks(
                    fundbookExample,
                    fundbookTableName,
                    startDateByTable.getTime() / 1000l,
                    endDateByTable.getTime() / 1000l);

            List<UserBasicInfo> usersssss = userBasicExtMapper.getUsers(0, 0, 0, 0, endDateByTable.getTime()/1000l);

            //当期月每个用户每个账本每天的业务发生
            Map<String, Fundbookday> fundbookdayMap = getFundbookDay(fundbooks);

            while (endDateByTable.compareTo(startDateByTable) != -1) {//1.每天
                String bookDateStr = simpleDateFormat_yyyyMMdd.format(startDateByTable);
                Date createEndTime = parseDateFromStr(simpleDateFormat_yyyyMMddhhmmss, bookDateStr + "23:59:59");

                Date preDate = getPreDayDate(startDateByTable);
                String preDateStr = simpleDateFormat_yyyyMMdd.format(preDate);

                logger.info("开始刷cache "+preDateStr);
                 long cacheStart=System.currentTimeMillis();
                //刷每天的余额到redis
                for (UserBasicInfo userBasicInfo : usersssss) {
                    List<Fundbookcode> bookcodesssss = bookcodemap.get(userBasicInfo.getTypeId());
                    for (Fundbookcode fundbookcode : bookcodesssss) {
                        String jedskey = String.format("%s|-%s|-%s", bookDateStr, fundbookcode.getBookcode(), userBasicInfo.getUserid());
                        String jedsPrekey = String.format("%s|-%s|-%s", preDateStr, fundbookcode.getBookcode(), userBasicInfo.getUserid());
                        Fundbookday fundbookdaysss = fundbookdayMap.get(jedskey);
                        if (fundbookdaysss != null) {
                            jedisTemplate.set(jedskey, fundbookdaysss.getBalance().doubleValue() + "");
                        } else {
                            String preBalanceStr = jedisTemplate.get(jedsPrekey);
                            BigDecimal preBalance = null;
                            if (preBalanceStr == null) {
                                preBalance = new BigDecimal(0);
                            } else {
                                preBalance = new BigDecimal(preBalanceStr);
                            }
                            jedisTemplate.set(jedskey, preBalance.doubleValue() + "");
                        }
                    }
                }

                long cacheEnd=System.currentTimeMillis();
                logger.info("Cache刷完了"+(float)(cacheEnd-cacheStart)/1000+" "+preDateStr);
                FundbookdayRunner fundbookdayRunner = new FundbookdayRunner();
                fundbookdayRunner.setBookcodemap(bookcodemap);
                fundbookdayRunner.setUserCreateEndTime(createEndTime.getTime() / 1000l);
                fundbookdayRunner.setBookDate(startDateByTable);
                fundbookdayRunner.setUserBasicExtMapper(userBasicExtMapper);
                fundbookdayRunner.setFundbookdayExtMapper(fundbookdayExtMapper);
                fundbookdayRunner.setFundbookdayMap(fundbookdayMap);
                fundbookdayRunner.setBookDateStr(bookDateStr);
                fundbookdayRunner.setPreDateStr(preDateStr);
                fundbookdayRunner.setFundbookDayTableName(fundbookDayTableName);
                fundbookdayRunner.setJedisTemplate(jedisTemplate);
                executorService.execute(fundbookdayRunner);
                startDateByTable = getNextDayDate(startDateByTable);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("计算完了" + (float) (end - start) / 1000 + "秒");
        return 1;
    }


    private Date parseDateFromStr(SimpleDateFormat simpleDateFormat, String dateStr) {

        Date date = null;
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            logger.error("日期转换报错", e);
        }
        return date;
    }


    /**
     * 计算需要删除的表明和日期区间
     *
     * @return
     */
    public Map<String, DelTableName> getDeleteTableName(Date startDate, Date endDate) {
        Map<String, DelTableName> map = new HashedMap();
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
        logger.info("需要删除的表名和日期区间:" + JsonUtils.toJson(map));
        return map;
    }

    //查询区间内全部账本数据
    public List<Fundbook> getFundbooks(Fundbook fundbook, String tableName, long startTime, long endTime) {
        return fundbookExtMapper.selectByExample(fundbook, tableName, startTime, endTime, false);
    }


    /**
     * 统计每天的发生数据
     * 计算范围时间内:每天-每个账本-每个用户 的sum(借),sum(贷),余
     * 取出来的数据必须是按照按照用户和发生时间排好序
     */
    public Map<String, Fundbookday> getFundbookDay(List<Fundbook> fundbooks) {
        Map<String, Fundbookday> map = new HashedMap();
        for (Fundbook fundbook : fundbooks) {
            calendar.setTimeInMillis(fundbook.getHappentime() * 1000l);
            String dayStr = simpleDateFormat_yyyyMMdd.format(calendar.getTime());

            String key = String.format("%s|-%s|-%s", dayStr, fundbook.getBookcode(), fundbook.getUserid());

            Fundbookday fundbookDaySum = map.get(key);
            if (map.get(key) == null) {
                fundbookDaySum = new Fundbookday();//插入一条新的
                copyPropertis(fundbook, dayStr, fundbookDaySum);
            } else {
                //更新余额,汇总发生
                fundbookDaySum.setHappendebit(fundbook.getDebit().add(fundbookDaySum.getHappendebit()));
                fundbookDaySum.setHappencredit(fundbook.getCredit().add(fundbookDaySum.getHappencredit()));
                fundbookDaySum.setBalance(fundbook.getBalance());
            }
            map.put(key, fundbookDaySum);
        }

        return map;
    }

    //获取前一天的日清余额
    private BigDecimal getPrevbalance(Map<String, Fundbookday> map, String key) {
        String currentDateStr = StringUtils.substring(key, 0, 8);
        Date currentDate = null;
        try {
            currentDate = simpleDateFormat_yyyyMMdd.parse(currentDateStr);
        } catch (ParseException e) {
            logger.error("日期转换出错", e);
        }
        Date preDayDate = getPreDayDate(currentDate);
        String preKey = simpleDateFormat_yyyyMMdd.format(preDayDate) + StringUtils.substring(key, 8);
        Fundbookday fundbookday = map.get(preKey);
        if (fundbookday != null) {
            return fundbookday.getBalance();
        } else {
            logger.info("没找到去数据库查询");
            String yyyyMM = StringUtils.substring(preKey, 0, 6);
            String tableName = "fundbookday" + yyyyMM;
            String[] preKeyArray = StringUtils.split(preKey, "|-");
            String bookdate = preKeyArray[0];
            String bookcode = preKeyArray[1];
            String userid = preKeyArray[2];
            Fundbookday fundbookday1 = new Fundbookday();
            fundbookday1.setUserid(Integer.parseInt(userid));
            fundbookday1.setBookdate(Integer.parseInt(bookdate));       //前一天
            fundbookday1.setBookcode(bookcode);

            //取前一天的这个用户的这个账本数据
            List<Fundbookday> fundbookdays = fundbookdayExtMapper.selectByExample(fundbookday1, tableName);
            if (fundbookdays != null && fundbookdays.size() > 0) {
                return fundbookdays.get(0).getBalance();
            } else {
                logger.info("数据库都没找到preKey=" + preKey);
                return new BigDecimal(0);
            }
        }
    }

    //复制账本数据到日清数据
    private void copyPropertis(Fundbook fundbook, String dayStr, Fundbookday fundbookDaySum) {
        fundbookDaySum.setBookcode(fundbook.getBookcode());
        fundbookDaySum.setAreacode(fundbook.getAreacode());//这个区域同booid一样是多条数据 有可能不一样
        fundbookDaySum.setHappendebit(fundbook.getDebit());
        fundbookDaySum.setHappencredit(fundbook.getCredit());
        fundbookDaySum.setBalance(fundbook.getBalance());
        fundbookDaySum.setBookdate(Integer.parseInt(dayStr));
        fundbookDaySum.setBookid(fundbook.getBookid()); //这个bookid貌似没有意义
        fundbookDaySum.setUserid(fundbook.getUserid());
    }

    public static void main(String[] args) throws Exception {

        Date parse = simpleDateFormat_yyyyMMddhhmmss.parse("2013092900:00:00");
        Date parse2 = simpleDateFormat_yyyyMMddhhmmss.parse("2013092923:59:59");
        logger.info(parse.getTime() / 1000l + "");
        logger.info(parse2.getTime() / 1000l + "");
    }
}
