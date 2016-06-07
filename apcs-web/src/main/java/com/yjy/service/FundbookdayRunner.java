package com.yjy.service;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.JsonUtils;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.Fundbookday;
import com.yjy.entity.UserBasicInfo;
import com.yjy.repository.mapper.FundbookdayExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/6.
 */
public class FundbookdayRunner implements Runnable {
    private Logger logger = LoggerFactory.getLogger(FundbookdayRunner.class);


    private Map<Integer, List<Fundbookcode>> bookcodemap;


    private long userCreateEndTime;

    private Date bookDate;

    private UserBasicExtMapper userBasicExtMapper;

    private FundbookdayExtMapper fundbookdayExtMapper;

    private Map<String, Fundbookday> fundbookdayMap;

    private String bookDateStr;

    private String preDateStr;

    private String fundbookDayTableName;

    private JedisTemplate jedisTemplate;

    @Override
    public void run() {
        if (!jedisTemplate.setnx(RedisKey.FUNDBOOK_DAY_REPOOT + bookDateStr, "1")) {
            return;
        }
        long start = System.currentTimeMillis();
        // 当期活跃用户
        List<Fundbookday> fundbookdays = new ArrayList<>();

        List<UserBasicInfo> users = userBasicExtMapper.getUsers(0, 0, 0, 0, userCreateEndTime);

        for (int j = 0; j <= (users.size() - 1); j++) {

            UserBasicInfo userBasicInfo = users.get(j);
            //这个用户类型需要些的正本数据
            List<Fundbookcode> fundbookcodes = bookcodemap.get(userBasicInfo.getTypeId());

            for (int i = 0; i <= (fundbookcodes.size() - 1); i++) {//2.每个账本

                Fundbookcode bookcode = fundbookcodes.get(i);

                //3.4每个账本
                Fundbookday fundbookday = new Fundbookday();
                fundbookday.setUserid(userBasicInfo.getUserid());
                fundbookday.setBookdate(Integer.parseInt(bookDateStr));
                fundbookday.setBookcode(bookcode.getBookcode());

                String mapKey = String.format("%s|-%s|-%s", fundbookday.getBookdate(), fundbookday.getBookcode(), fundbookday.getUserid());
                String mapPreKey = String.format("%s|-%s|-%s", preDateStr, fundbookday.getBookcode(), fundbookday.getUserid());

                String preBalanceStr = jedisTemplate.get(mapPreKey); //查询前一天的余
                BigDecimal preBalance = null;
                if (StringUtils.isNotBlank(preBalanceStr)) {
                    preBalance = new BigDecimal(preBalanceStr);
//                    jedisTemplate.del(mapPreKey);
                } else {
                    preBalance = new BigDecimal("0");
                }

                Fundbookday fundbookdayActive = fundbookdayMap.get(mapKey);
                if (fundbookdayActive != null) {
                    fundbookday.setPrevbalance(preBalance);
                    fundbookday.setBalance(fundbookdayActive.getBalance());
                    fundbookday.setAreacode(fundbookdayActive.getAreacode());
                    fundbookday.setHappencredit(fundbookdayActive.getHappencredit());
                    fundbookday.setHappendebit(fundbookdayActive.getHappendebit());
                } else {
                    BigDecimal bigDecimal0 = new BigDecimal(0);
                    fundbookday.setAreacode(0);
                    fundbookday.setBalance(preBalance);
                    fundbookday.setPrevbalance(preBalance);
                    fundbookday.setHappencredit(bigDecimal0);
                    fundbookday.setHappendebit(bigDecimal0);
                }
                jedisTemplate.set(mapKey, fundbookday.getBalance().floatValue() + "");
                fundbookdays.add(fundbookday);
                if (fundbookdays.size() % 30000 == 0 || (j == (users.size() - 1) && i == (fundbookcodes.size() - 1))) {
                    //每3万条插入一次
                    long memeryRunTime = System.currentTimeMillis();
                    logger.info("内存计算完" + (float) (memeryRunTime - start) / 1000 + " " + bookDateStr + " 总用户数" + users.size() + " 剩余用户数" + (users.size() - (j + 1)) + ",当前数据量:" + fundbookdays.size());
                    fundbookdayExtMapper.batchInsert(fundbookdays, fundbookDayTableName);
                    long insertRunTime = System.currentTimeMillis();

                    logger.info("插入完成账本" + (float) (insertRunTime - memeryRunTime) / 1000 + " " + bookDateStr + " " + JsonUtils.toJson(bookcode));
                    fundbookdays = new ArrayList<>();
                    start = System.currentTimeMillis();
                }
            }
        }

    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public UserBasicExtMapper getUserBasicExtMapper() {
        return userBasicExtMapper;
    }

    public void setUserBasicExtMapper(UserBasicExtMapper userBasicExtMapper) {
        this.userBasicExtMapper = userBasicExtMapper;
    }

    public FundbookdayExtMapper getFundbookdayExtMapper() {
        return fundbookdayExtMapper;
    }

    public void setFundbookdayExtMapper(FundbookdayExtMapper fundbookdayExtMapper) {
        this.fundbookdayExtMapper = fundbookdayExtMapper;
    }

    public Map<String, Fundbookday> getFundbookdayMap() {
        return fundbookdayMap;
    }

    public void setFundbookdayMap(Map<String, Fundbookday> fundbookdayMap) {
        this.fundbookdayMap = fundbookdayMap;
    }

    public String getBookDateStr() {
        return bookDateStr;
    }

    public void setBookDateStr(String bookDateStr) {
        this.bookDateStr = bookDateStr;
    }

    public String getPreDateStr() {
        return preDateStr;
    }

    public void setPreDateStr(String preDateStr) {
        this.preDateStr = preDateStr;
    }

    public JedisTemplate getJedisTemplate() {
        return jedisTemplate;
    }

    public void setJedisTemplate(JedisTemplate jedisTemplate) {
        this.jedisTemplate = jedisTemplate;
    }

    public String getFundbookDayTableName() {
        return fundbookDayTableName;
    }

    public void setFundbookDayTableName(String fundbookDayTableName) {
        this.fundbookDayTableName = fundbookDayTableName;
    }

    public Map<Integer, List<Fundbookcode>> getBookcodemap() {
        return bookcodemap;
    }

    public void setBookcodemap(Map<Integer, List<Fundbookcode>> bookcodemap) {
        this.bookcodemap = bookcodemap;
    }

    public long getUserCreateEndTime() {
        return userCreateEndTime;
    }

    public void setUserCreateEndTime(long userCreateEndTime) {
        this.userCreateEndTime = userCreateEndTime;
    }
}
