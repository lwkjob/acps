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
import java.util.*;

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
         long start= System.currentTimeMillis();
        Map<String,List<Fundbookday>> insertIntoMap=new HashedMap();
        Map<String, DelTableName> deleteTableNameMap = getDeleteTableName(startDate, endDate);

         for(String key :deleteTableNameMap.keySet()){
             DelTableName delTableName = deleteTableNameMap.get(key);
             fundbookdayExtMapper.deleteFundbookDay(
                     bookcodes,
                     users,
                     delTableName.getTableName(),
                     Integer.parseInt(delTableName.getStartStr()),
                     Integer.parseInt(delTableName.getEndStr()));

             //2 统计每天每个用户每个账本数据
             Fundbook fundbookExample=new Fundbook(); //查询条件

             List<Fundbook>  fundbooks=  getFundbooks(fundbookExample,
                     delTableName.getTableName(),
                     Integer.parseInt(delTableName.getStartStr()),
                     Integer.parseInt(delTableName.getEndStr()));

             Map<String,Fundbookday> fundbookdayMap = getFundbookDay(fundbooks);

             List<Fundbookday> fundbookdays=new ArrayList<>();
             //3 每个表，每个用户，每天，每个账本一条数据
                //3.1每个表
             for (UserBasicInfo userBasicInfo:users){
                 //3.2每个用户
                Date startDateByTable=parseDateFromStr(simpleDateFormat_yyyyMMdd,delTableName.getStartStr());
                Date dateDateByTable=parseDateFromStr(simpleDateFormat_yyyyMMdd, delTableName.getEndStr());
                 while (dateDateByTable.compareTo(startDateByTable)!=-1){
                     //3.3每天
                     for(Bookcode bookcode:bookcodes){
                         //3.4每个账本
                         String bookDateStr=simpleDateFormat_yyyyMMdd.format(startDate);
                         Fundbookday fundbookday=new Fundbookday();
                         fundbookday.setUserid(userBasicInfo.getUserid());
                         fundbookday.setBookdate(Integer.parseInt(bookDateStr));
                         fundbookday.setPlatformrole(bookcode.getBookcodeone());
                         fundbookday.setEntryuserrole(bookcode.getBookcodetwo());
                         fundbookday.setAccbooknumber(bookcode.getBookcodethree());

                         String mapkey=String.format("%s-%s-%s-%s-%s",
                                 bookDateStr,bookcode.getBookcodeone(),
                                 bookcode.getBookcodetwo(),
                                 bookcode.getBookcodethree(),
                                 userBasicInfo.getUserid());
                         Fundbookday fundbookday1 = fundbookdayMap.get(mapkey);
                         if(fundbookday1!=null){
                             fundbookday.setAreacode(fundbookday1.getAreacode());
                             fundbookday.setPrevbalance(fundbookday1.getPrevbalance());
                             fundbookday.setBalance(fundbookday1.getBalance());
                             fundbookday.setHappencredit(fundbookday1.getHappencredit());
                             fundbookday.setHappendebit(fundbookday1.getHappendebit());
                         }
                         fundbookdays.add(fundbookday);
                     }
                     startDateByTable=getNextDayDate(startDate);
                 }
             }
             insertIntoMap.put(delTableName.getTableName(),fundbookdays);
         }
        long end = System.currentTimeMillis();
        logger.info("内存计算完了"+(float)(end-start)/1000+"秒");
        //批量插入
        for(String key:insertIntoMap.keySet()){
            List<Fundbookday> fundbookdays = insertIntoMap.get(key);
            fundbookdayExtMapper.batchInsert(key,fundbookdays);
        }
        return 1;
    }

    private Date parseDateFromStr(SimpleDateFormat simpleDateFormat,String dateStr){

       Date date=null;
        try {

               date=simpleDateFormat.parse(dateStr);
        }catch (Exception e){
            logger.error("日期转换报错",e);
        }
        return date;
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
    public  Map<String,Fundbookday> getFundbookDay(List<Fundbook> fundbooks){
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
            fundbookDaySum.setPrevbalance(getPrevbalance(map,key));  //设置上期的余。。。。
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
