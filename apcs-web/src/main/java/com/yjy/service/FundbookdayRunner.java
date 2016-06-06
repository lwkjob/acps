package com.yjy.service;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.utils.JsonUtils;
import com.yjy.constant.FundConstant;
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

    private Logger logger= LoggerFactory.getLogger(FundbookdayRunner.class);

    private List<Fundbookcode> bookcodes;

    private List<UserBasicInfo> users;

    private Date bookDate;

    private UserBasicExtMapper userBasicExtMapper;

    private FundbookdayExtMapper fundbookdayExtMapper;

    private Map<String, Fundbookday> fundbookdayMap;

    private int typeid;

    private String bookDateStr;

    private  String preDateStr;

    private JedisTemplate jedisTemplate;
    @Override
    public void run() {

        long start=System.currentTimeMillis();
        List<Fundbookday> fundbookdays = new ArrayList<>();
        String fundbookDayTableName= FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE +StringUtils.substring(bookDateStr,0,6);

        int i=0;
        int index=0;
        for (Fundbookcode bookcode : bookcodes) {//2.每个账本
            i++;
            // 当期活跃用户
            if (users==null){
                users = userBasicExtMapper.getUsers(0, 0, typeid, 0, bookDate.getTime() / 1000);
            }
            int totalData=bookcodes.size()*users.size();

            for (UserBasicInfo userBasicInfo : users) {
                index++;
                //3.4每个账本
                Fundbookday fundbookday = new Fundbookday();
                fundbookday.setUserid(userBasicInfo.getUserid());
                fundbookday.setBookdate(Integer.parseInt(bookDateStr));
                fundbookday.setBookcode(bookcode.getBookcode());
                //遍历没个用户今天是否产生账本信息
                String mapKey = String.format("%s|-%s|-%s", fundbookday.getBookdate(), fundbookday.getBookcode(), fundbookday.getUserid());
                String mapPreKey = String.format("%s|-%s|-%s", preDateStr, fundbookday.getBookcode(), fundbookday.getUserid());
                Fundbookday fundbookdayActive = fundbookdayMap.get(mapKey);
                String preBalanceStr= jedisTemplate.get(mapPreKey); //查询前一天的余
                BigDecimal preBalance=null;
                if(StringUtils.isNotBlank(preBalanceStr)){
                    preBalance=new BigDecimal(preBalanceStr);
                    jedisTemplate.del(mapPreKey);
                }else {
                    preBalance=new BigDecimal("0");
                }
                if (fundbookdayActive!=null){
                    fundbookday.setPrevbalance(preBalance);
                    fundbookday.setBalance(fundbookdayActive.getBalance());
                    fundbookday.setAreacode(fundbookdayActive.getAreacode());
                    fundbookday.setHappencredit(fundbookdayActive.getHappencredit());
                    fundbookday.setHappendebit(fundbookdayActive.getHappendebit());
                }else {
                    BigDecimal bigDecimal0 = new BigDecimal(0);
                    fundbookday.setAreacode(0);
                    fundbookday.setBalance(preBalance);
                    fundbookday.setPrevbalance(preBalance);
                    fundbookday.setHappencredit(bigDecimal0);
                    fundbookday.setHappendebit(bigDecimal0);
                }
                jedisTemplate.set(mapKey,fundbookday.getBalance().floatValue()+"");
                fundbookdays.add(fundbookday);


                if(fundbookdays.size()%40000==0||index==totalData){
                    //每3万条插入一次
                    long memeryRunTime=System.currentTimeMillis();

                    logger.info("内存计算完" + (float) (memeryRunTime - start) / 1000 + " " + bookDateStr + " " + i + "剩余账本" + (bookcodes.size() - i) + ",当前数据量:" + fundbookdays.size());
                    fundbookdayExtMapper.batchInsert(fundbookdays,fundbookDayTableName);
                    long insertRunTime=System.currentTimeMillis();

                    logger.info("插入完成账本" + (float) (insertRunTime - memeryRunTime) / 1000 + " " + bookDateStr + " " + JsonUtils.toJson(bookcode));
                    fundbookdays=new ArrayList<>();
                    start=System.currentTimeMillis();
                }
            }


//                    int startInt = Integer.parseInt(delTableName.getStartStr());
//                    int entInt = Integer.parseInt(delTableName.getEndStr());
//                    List<Fundbookcode> delBookcode=new ArrayList();
//                    delBookcode.add(bookcode);       //数据太多只能一个一个账本的删除
//                    logger.info("删除重新统计数据"+bookDateStr + " " + JsonUtils.toJson(bookcode));
//                    fundbookdayExtMapper.deleteFundbookDay(
//                            delBookcode,
//                            users,
//                            fundbookDayTableName,
//                            startInt,
//                            entInt
//                    );


        }

    }


    public List<Fundbookcode> getBookcodes() {
        return bookcodes;
    }

    public void setBookcodes(List<Fundbookcode> bookcodes) {
        this.bookcodes = bookcodes;
    }

    public List<UserBasicInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserBasicInfo> users) {
        this.users = users;
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

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
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
}
