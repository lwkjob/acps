package com.yjy.test.funbook;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.dao.BaseDao;
import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.fundbook.Fundbook;
import com.yjy.common.entity.fundbook.Fundbookday;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.JedisVo;
import com.yjy.common.utils.DateTools;
import com.yjy.common.utils.JsonUtils;
import com.yjy.service.fundbook.FundbookDayService;
import com.yjy.service.fundbook.FundbookService;
import com.yjy.service.fundbook.FundbookcodeService;
import com.yjy.service.fundbook.UserService;
import com.yjy.service.fundbook.distributed.ScheduleServiceDayNew;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by liwenke on 16/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:test-config.xml") // 加载配置
public class FundbookdayTest {

    @Resource
    private FundbookDayService fundbookDayService;


 @Resource
    private BaseDao baseDao;



    private static Logger logger= LoggerFactory.getLogger(FundbookdayTest.class);

    @Test
    public void testSum(){
        String month="201605";
        Fundbookday fundbookday = fundbookDayService.sumThisMonth(month);
        logger.info(fundbookday.getHappencredit().toPlainString()+"");
        logger.info(fundbookday.getHappendebit().toPlainString()+"");
    }

    @Test
     public void getGroupByuserBookcode() throws InterruptedException, IOException {
        final String month="201605";
        SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        Date d=DateTools.parseDateFromString_yyyyMM(month, logger);
        int  endDateInt= Integer.parseInt(StringUtils.substring(DateTools.getCurrentMonthLastDay(d, simpleDateFormat_yyyyMMdd), 6, 8));
        final CountDownLatch countDownLatch=new CountDownLatch(endDateInt);
        final Map<String,Object> m=new ConcurrentHashMap<>();
        ExecutorService executorService= Executors.newFixedThreadPool(8);
        for (int i=1;i<=endDateInt;i++){
            final String tableName=FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE+ month + (i < 10 ? "0" +i : i);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    String sql = "SELECT BookCode,sum(HappenDebit) HappenDebit,sum(HappenCredit)  HappenCredit from " + tableName + " GROUP BY BookCode HAVING HappenDebit<>0 or HappenCredit<>0";
                    logger.info(sql);
                    List<Fundbookday> fundbookdayList = fundbookDayService.getBySql(sql);
                    m.put(tableName, fundbookdayList);
                    countDownLatch.countDown();
                }
            });



        }
        countDownLatch.await();
        System.out.println(m.size());

        //求和
        Map<String,Fundbookday> resultMap=new HashMap<>();
        for(String key:m.keySet()){
            List<Fundbookday> fundbookdayList= (List<Fundbookday>) m.get(key);
            for (Fundbookday fundbookday:fundbookdayList){
                Fundbookday fundbookday1= resultMap.get(fundbookday.getBookcode());
                if(fundbookday1!=null){
                    fundbookday.setHappendebit(fundbookday1.getHappendebit().add(fundbookday.getHappendebit()));
                    fundbookday.setHappencredit(fundbookday1.getHappencredit().add(fundbookday.getHappencredit()));
                }
                resultMap.put(fundbookday.getBookcode(),fundbookday);
            }
        }
//        转成List
        List<Fundbookday> resultList=new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();
        for (String key:resultMap.keySet()){
            Fundbookday fundbookday = resultMap.get(key);
            resultList.add(fundbookday);
        }

        //排序
        Collections.sort(resultList, new Comparator<Fundbookday>() {
            @Override
            public int compare(Fundbookday o1, Fundbookday o2) {
                return o1.getBookcode().compareTo(o2.getBookcode());
            }
        });

        //打印
        for (Fundbookday fundbookday:resultList){

            stringBuilder.append(fundbookday.getBookcode() +"\t"+fundbookday.getHappendebit().toPlainString()+"\t"+fundbookday.getHappencredit().toPlainString()).append("\r\n");
        }

        FileUtils.write(new File("d://groupbook.txt"),stringBuilder.toString());

        logger.info("\r\n"+stringBuilder.toString());
        logger.info("搞完了额");

    }
    @Test
    public void getGroupByuser() throws InterruptedException, IOException {
        final String month="201605";
        SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
        Date d=DateTools.parseDateFromString_yyyyMM(month, logger);
        int  endDateInt= Integer.parseInt(StringUtils.substring(DateTools.getCurrentMonthLastDay(d, simpleDateFormat_yyyyMMdd), 6, 8));
        final CountDownLatch countDownLatch=new CountDownLatch(endDateInt);
        final Map<String,Object> m=new ConcurrentHashMap<>();
//        BlockingQueue blockingQueue= new ConcurrentLinkedQueue();
        for (int i=1;i<=endDateInt;i++){
            final String tableName=FundConstant.FUNDBOOKDAY_TABLE_NAME_PRE+ month + (i < 10 ? "0" +i : i);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String sql="SELECT UserId,sum(HappenCredit) HappenCredit ,sum(HappenDebit) HappenDebit from "+tableName+" where BookCode='9002-1004-1101-1222-1205'and userid=74875  GROUP BY UserId HAVING HappenCredit<>0 or HappenDebit<>0;";
                    logger.info("开始查询"+tableName);
                  List<Fundbookday> fundbookdayList = fundbookDayService.getBySql(sql);
                    logger.info("结束查询" +sql+" "+tableName);
                    m.put(tableName, fundbookdayList);
                    countDownLatch.countDown();
                }
            }).start();

        }
        countDownLatch.await();
        logger.info("查询结束了");

        //求和
        StringBuilder stringBuilder2=new StringBuilder("\r\n");
        Map<String,Fundbookday> resultMap=new ConcurrentHashMap<>();
        for(String key:m.keySet()){
            //查询得到的数据
            List<Fundbookday> fundbookdayList= (List<Fundbookday>) m.get(key);
            for (Fundbookday fundbookday:fundbookdayList){
                stringBuilder2.append(key).append("--").append(fundbookday.getUserid() + "\t").append(fundbookday.getHappencredit()).append("\t").append(fundbookday.getHappendebit()).append("\r\n");
                Fundbookday fundbookday1= resultMap.get(String.valueOf(fundbookday.getUserid()));
                if(fundbookday1!=null){
                    fundbookday.setHappendebit(fundbookday1.getHappendebit().add(fundbookday.getHappendebit()));
                    fundbookday.setHappencredit(fundbookday1.getHappencredit().add(fundbookday.getHappencredit()));
                }

                resultMap.put(String.valueOf(fundbookday.getUserid()), fundbookday);
            }
            stringBuilder2.append("\r\n");
        }
        logger.info(stringBuilder2.toString());
//        转成List
        List<Fundbookday> resultList=new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();
        for (String key:resultMap.keySet()){
            Fundbookday fundbookday = resultMap.get(key);
            resultList.add(fundbookday);
        }

        //排序
        Collections.sort(resultList, new Comparator<Fundbookday>() {
            @Override
            public int compare(Fundbookday o1, Fundbookday o2) {
                return o1.getUserid().compareTo(o2.getUserid());
            }
        });

        //打印
        for (Fundbookday fundbookday:resultList){

            stringBuilder.append(fundbookday.getUserid() +"\t"+fundbookday.getHappendebit().toPlainString()+"\t"+fundbookday.getHappencredit().toPlainString()).append("\r\n");
        }

        File file = new File("d://result.txt");
        FileUtils.write(file,stringBuilder.toString());
        logger.info("\r\n"+stringBuilder.toString());
        logger.info("搞完了额");

    }


}
