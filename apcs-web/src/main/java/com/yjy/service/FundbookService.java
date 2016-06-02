package com.yjy.service;


import com.yjy.common.utils.JsonUtils;
import com.yjy.constant.FundConstant;
import com.yjy.entity.Fundbook;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.UserBasicInfo;
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
 * 账本刷余逻辑
 * Created by Administrator on 2016/5/31.
 */
@Service("fundbookService")
public class FundbookService {

    @Resource
    private FundbookExtMapper fundbookExtMapper;

    @Resource
    private UserBasicExtMapper userBasicExtMapper;

    private static Logger logger = LoggerFactory.getLogger(FundbookService.class);

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");

    /**
     * 根据日期段一个月一个月的更新
     *
     * @param bookcodes
     */
    public void oneByOneUpdateBalance(Date startDate, Date endDate, List<Fundbookcode> bookcodes, int typeid,List<UserBasicInfo> users) {
        // 当期活跃用户
        if (users==null){
              users = userBasicExtMapper.getUsers(0, 0, typeid, 0, startDate.getTime() / 1000);
        }

        //轮训用户
        //这里可以考虑多线程
        for (UserBasicInfo userBasicInfo : users) {

            //轮训每一个月
            while (!(endDate.compareTo(startDate) == -1)) {
                String preTableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + getPreMonthsDateStr(startDate);
                String tableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + simpleDateFormat_yyyyMM.format(startDate);
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
            return simpleDateFormat_yyyyMM.parse(dateStr);
        } catch (Exception e) {
            logger.info("传入的执行时间有错误", e);
            throw new RuntimeException("传入的执行时间有错误");
        }
    }


    //取前一月字符串格式
    public String getPreMonthsDateStr(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, -1);
        return simpleDateFormat_yyyyMM.format(preDate);
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
    public void doUpdateBalance(List<Fundbookcode> bookcodes,
                                Integer userId,
                                String preTableName,
                                String tableName) {


        //轮每一个训账本
        //这里可以考虑多线程
        for (Fundbookcode bookcode : bookcodes) {


            logger.info(String.format("正在执行是数据信息:bookcodes:%s,userId:%s,preTableName:%s,tableName:%s", JsonUtils.toJson(bookcode), userId, preTableName, tableName));
            Fundbook fundbook = new Fundbook();
            fundbook.setBookcode(bookcode.getBookcode());
            fundbook.setUserid(userId);

            List<Fundbook> prefundbooks = null;
            try {
                prefundbooks = fundbookExtMapper.selectByExample(fundbook, preTableName, 0, 0, true);
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
            List<Fundbook> fundbooks = fundbookExtMapper.selectByExample(fundbook, tableName, 0, 0, false);
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
