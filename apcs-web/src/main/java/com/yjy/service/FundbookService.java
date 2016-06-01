package com.yjy.service;


import com.yjy.common.utils.JsonUtils;
import com.yjy.constant.FundConstant;
import com.yjy.entity.Bookcode;
import com.yjy.entity.Fundbook;
import com.yjy.entity.UserBasicInfo;
import com.yjy.repository.mapper.BookcodeMapper;
import com.yjy.repository.mapper.FundbookExtMapper;
import com.yjy.repository.mapper.UserBasicExtMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2016/5/31.
 */
@Service("fundbookService")
public class FundbookService {


    @Resource
    private FundbookExtMapper fundbookExtMapper;

    @Resource
    private BookcodeMapper bookcodeMapper;

    @Resource
    private UserBasicExtMapper userBasicExtMapper;


    private static Logger logger = LoggerFactory.getLogger(FundbookService.class);

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private static Calendar calendar = Calendar.getInstance();

    public static void main(String[] args) {
//        long now = System.currentTimeMillis() / 1000;
//        System.out.println(now);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(1299723341 * 1000l);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        String mm = simpleDateFormat.format(calendar.getTime());
//
//        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");

        List<Fundbook> fundbooks = new ArrayList<>();
        Fundbook fundbook1 = new Fundbook();
        Fundbook fundbook2 = new Fundbook();
        Fundbook fundbook3 = new Fundbook();
        fundbook1.setHappentime(3l);
        fundbook2.setHappentime(1l);
        fundbook3.setHappentime(2l);

        fundbooks.add(fundbook1);
        fundbooks.add(fundbook2);
        fundbooks.add(fundbook3);

        Collections.sort(fundbooks, new Comparator<Fundbook>() {
            @Override
            public int compare(Fundbook o1, Fundbook o2) {
                return (o1.getHappentime() - o2.getHappentime()) == 0 ? 0 : (o1.getHappentime() - o2.getHappentime()) > 0 ? 1 : -1;
            }
        });

        logger.info(JsonUtils.toJson(fundbooks));
        try {
//            Date date = simpleDateFormat2.parse("2013-09");
//            Date date2 = simpleDateFormat2.parse("2013-08");
            Date dateStart = simpleDateFormat_yyyyMMdd.parse("20130927");
            Date dateEnd = simpleDateFormat_yyyyMMdd.parse("20131008");
//            logger.info(date1.getTime() / 1000 + "");
//            logger.info(parse.getTime() / 1000 + "");
//            new FundbookService().getDeleteTableName(dateStart,dateEnd);
        } catch (Exception e) {
            logger.info("时间转换错误", e);
        }


    }


    public List<Bookcode> getBookcodes() {
        List<Bookcode> bookcodes = bookcodeMapper.selectByExample(null);
        return bookcodes;
    }

    public List<UserBasicInfo> getUsers(int startIndex,
                                        int endIndex,
                                        int typeid,
                                        int startTime,
                                        int endTime) {

        return userBasicExtMapper.getUsers(startIndex, endIndex, typeid, startTime, endTime);

    }

    /**
     * 根据日期段一个月一个月的更新
     *
     * @param bookcodes
     * @param users
     */
    public void oneByOneUpdateBalance(Date startDate, Date endDate, List<Bookcode> bookcodes, List<UserBasicInfo> users) {

        //轮训用户
        //这里可以考虑多线程
        for (UserBasicInfo userBasicInfo : users) {

            //轮训每一个月
            while (!(endDate.compareTo(startDate) == -1)) {
                String preTableName = "fundbook" + getPreMonthsDateStr(startDate);
                String tableName = "fundbook" + simpleDateFormat.format(startDate);
                doUpdateBalance(bookcodes,
                        userBasicInfo.getUserid(),
                        preTableName,
                        tableName);
                startDate = getNextMonthsDate(startDate);
            }
        }
    }


    public Date parseDateFromString(String dateStr) {
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }


    //取前一月字符串格式
    public String getPreMonthsDateStr(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, -1);
        return simpleDateFormat.format(preDate);
    }

    //取后一月
    public Date getNextMonthsDate(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, 1);
        return preDate;
    }

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


    //轮每一个训账本更新余额
    public void doUpdateBalance(List<Bookcode> bookcodes,
                                Integer userId,
                                String preTableName,
                                String tableName) {


        //轮每一个训账本
        //这里可以考虑多线程
        for (Bookcode bookcode : bookcodes) {


            logger.info(String.format("正在执行是数据信息:bookcodes:%s,userId:%s,preTableName:%s,tableName:%s", JsonUtils.toJson(bookcode), userId, preTableName, tableName));
            Fundbook fundbook = new Fundbook();
            fundbook.setPlatformrole(bookcode.getBookcodeone());
            fundbook.setEntryuserrole(bookcode.getBookcodetwo());
            fundbook.setAccbooknumber(bookcode.getBookcodethree());
            fundbook.setUserid(userId);

            List<Fundbook> prefundbooks = null;
            try {
                prefundbooks = fundbookExtMapper.selectByWhere(fundbook, preTableName, 0, 0, true);
            } catch (Exception e) {
                //Table 'ypzdw_fundcapital.fundbook201308' doesn't exist 表不存在
                logger.error("数据库报错:" + e.getMessage());
            }


            Fundbook preFundbook = null; //上一条数据

            if (prefundbooks == null || prefundbooks.size() == 0) {
                preFundbook = new Fundbook();
            } else {
                preFundbook = prefundbooks.get(0);
            }

            //取账本中的数据
            List<Fundbook> fundbooks = fundbookExtMapper.selectByWhere(fundbook, tableName, 0, 0, false);
            logger.info("开始统计:" + JsonUtils.toJson(fundbook) + " userId:" + userId + " tableName:" + tableName);
            long startTime = System.currentTimeMillis();


            //轮训账本表中每一条数据
            if (fundbooks != null && fundbooks.size() > 0) {
                for (int i = 0; i < fundbooks.size(); i++) {
                    Fundbook iFundbook = fundbooks.get(i);
                    BigDecimal preBalance = preFundbook.getBalance() == null ? new BigDecimal(0) : preFundbook.getBalance();
                    if (bookcode.getFundtype().intValue()== FundConstant.FUND_TYPE_DEBT){
                        //负债类公式: 上期贷余 + 本期发生贷 - 本期发生借 = 本期贷余
                        BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                        BigDecimal debit = iFundbook.getDebit();  //当期发生借
                        BigDecimal iBalance = preBalance.add(credit).min(debit);
                        iFundbook.setBalance(iBalance);
                    }else{
                        BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                        BigDecimal debit = iFundbook.getDebit();  //当期发生借
                        //资产类公式和损益类: 上期借余 + 本期发生借 - 本期发生贷 = 本期借余
                        BigDecimal iBalance = preBalance.add(debit).min(credit);
                        iFundbook.setBalance(iBalance);
                    }
                    //轮训下一个条
                    preFundbook = iFundbook;
                }

                fundbookExtMapper.batchUpdateByPrimaryKeySelective(fundbooks, tableName);

            }
            long endTime = System.currentTimeMillis();
            logger.info("总条数:" + fundbooks.size() + " 执行时间:" + (float) (endTime - startTime) / 1000 + "秒");
        }
    }


}
