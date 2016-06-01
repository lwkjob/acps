package com.yjy.service;


import com.yjy.common.utils.JsonUtils;
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

/**
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



    private static Logger logger = LoggerFactory.getLogger(FundbookDayService.class);

    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private  static Calendar calendar = Calendar.getInstance();

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
    public int insertFundBookDay(Date startDate, Date endDate, List<Bookcode> bookcodes,List<UserBasicInfo> users) {
        //delete日清表指定时间之前的数据
        //1.1根据时间区间算出所有需要删数据的表名
        Map<String, DelTableName> deleteTableNameMap = getDeleteTableName(startDate, endDate);
         for(String key :deleteTableNameMap.keySet()){
             DelTableName delTableName = deleteTableNameMap.get(key);
             fundbookdayExtMapper.deleteFundbookDay(
                     bookcodes,
                     users,
                     delTableName.getTableName(),
                     Integer.parseInt(delTableName.getStartStr()),
                     Integer.parseInt(delTableName.getEndStr()));

         }
//        List<UserBasicInfo> users = userBasicExtMapper.getUsers(0, 0, 0, startDate.getTime() / 1000, endDate.getTime() / 1000);
        //取出指定时间之前的所有用户,插入到日清表中
        //更新日清表中当天sum(货),sum(钱),当天余
        return 1;
    }

    /**
     * 计算需要删除的表明和日期区间
     *
     * @return
     */
    public  Map<String, DelTableName> getDeleteTableName(Date startDate, Date endDate) {
        Map<String, DelTableName> map = new HashedMap();
        while (endDate.compareTo(startDate) != -1) {
            String startStr = simpleDateFormat_yyyyMMdd.format(startDate);
            String tableName= StringUtils.substring(startStr,0,6);//数据库中yyyyMM作为表名的后缀
            DelTableName delTableName = new DelTableName();
            if (map.get(tableName)!=null){
                String startStrTemp = map.get(tableName).getStartStr();
                delTableName.setTableName(tableName);
                delTableName.setStartStr(startStrTemp);
                delTableName.setEndStr(startStr);
            }else {
                delTableName.setTableName(tableName);
                delTableName.setStartStr(startStr);
                delTableName.setEndStr(startStr);
            }
            map.put(tableName,delTableName);
            startDate= getNextDayDate(startDate);
        }
        logger.info("需要删除的表明和日期区间:"+JsonUtils.toJson(map));
        return map;
    }

    //查询区间内全部账本数据
    public List<Fundbook> getFundbooks(Fundbook fundbook,String tableName,int startTime,int endTime){
      return   fundbookExtMapper.selectByWhere(fundbook,tableName,startTime,endTime,true);
    }


    /**
     * 计算范围时间内:每天-每个用户-每个账本 的sum(借),sum(贷),余
     * 取出来的数据必须是按照按照用户和发生时间排好序
     */
    public Map getFundbookDay(List<Fundbook> fundbooks){
        Map<String,Fundbookday>  map=new HashedMap(20000);
        for (Fundbook fundbook:fundbooks){
            calendar.setTimeInMillis(fundbook.getHappentime() * 1000l);
            String dayStr=simpleDateFormat_yyyyMMdd.format(calendar.getTime());
            String key=String.format("%s-%s-%s-%s-%s",dayStr,fundbook.getPlatformrole(),fundbook.getEntryuserrole(),fundbook.getAccbooknumber(),fundbook.getUserid());
            Fundbookday fundbookDaySum=map.get(key);
           if(map.get(key)==null){
                 fundbookDaySum = new Fundbookday();
                 copyPropertis(fundbook, dayStr, fundbookDaySum);
           }else {
               fundbookDaySum.setHappendebit(fundbook.getDebit().add(fundbookDaySum.getHappendebit()));
               fundbookDaySum.setHappencredit(fundbook.getCredit().add(fundbookDaySum.getHappencredit()));
               fundbookDaySum.setBalance(fundbook.getBalance());
           }
            fundbookDaySum.setPrevbalance(getPrevbalance(map,key));  //上期的余。。。。
            map.put(key, fundbookDaySum);
        }
        return map;
    }

    //获取前一天的日清余额
    private BigDecimal getPrevbalance(Map<String, Fundbookday> map, String key) {
        String currentDateStr=StringUtils.substring(key,0,8);
        Date currentDate =null;
        try {
              currentDate = simpleDateFormat_yyyyMMdd.parse(currentDateStr);
        } catch (ParseException e) {
           logger.error("日期转换出错",e);
        }
        Date preDayDate = getPreDayDate(currentDate);
        String preKey=simpleDateFormat_yyyyMMdd.format(preDayDate)+StringUtils.substring(key, 8);
        Fundbookday fundbookday = map.get(preKey);
        if (fundbookday!=null){
            return fundbookday.getBalance();
        }else{
            logger.info("没找到去数据库查询");
            String yyyyMM=StringUtils.substring(preKey,0,6);
            String tableName="fundbookday"+yyyyMM;
            String[] preKeyArray=StringUtils.split(preKey,"-");
            String bookdate=preKeyArray[0];
            String platformrole=preKeyArray[1];
            String entryuserrole=preKeyArray[2];
            String accbooknumber=preKeyArray[3];
            String userid=preKeyArray[4];
            Fundbookday fundbookday1=new Fundbookday();
            fundbookday1.setUserid(Integer.parseInt(userid));
            fundbookday1.setBookdate(Integer.parseInt(bookdate));
            fundbookday1.setPlatformrole(Integer.parseInt(platformrole));
            fundbookday1.setEntryuserrole(Integer.parseInt(entryuserrole));
            fundbookday1.setAccbooknumber(Integer.parseInt(accbooknumber));
            List<Fundbookday> fundbookdays = fundbookdayExtMapper.selectByMap(fundbookday1, tableName);
            if(fundbookdays!=null){
                return fundbookdays.get(0).getBalance();
            }else {
                logger.info("数据库都没找到preKey="+preKey);
                return new BigDecimal(0);
            }
        }
    }


    //复制账本数据到日清数据
    private void copyPropertis(Fundbook fundbook, String dayStr, Fundbookday fundbookDaySum) {
        fundbookDaySum.setPlatformrole(fundbook.getPlatformrole());
        fundbookDaySum.setEntryuserrole(fundbook.getEntryuserrole());
        fundbookDaySum.setAccbooknumber(fundbook.getAccbooknumber());
        fundbookDaySum.setAreacode(fundbook.getAreacode());
        fundbookDaySum.setAccbooknumber(fundbook.getAccbooknumber());
        fundbookDaySum.setHappendebit(fundbook.getDebit());
        fundbookDaySum.setHappencredit(fundbook.getCredit());
        fundbookDaySum.setBalance(fundbook.getBalance());
        fundbookDaySum.setBookdate(Integer.parseInt(dayStr));
        fundbookDaySum.setBookid(fundbook.getBookid());
        fundbookDaySum.setUserid(fundbook.getUserid());
    }
}
