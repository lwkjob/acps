package com.yjy.service;


import com.yjy.common.dao.Pagination;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.utils.DateTools;
import com.yjy.common.constant.FundConstant;
import com.yjy.entity.Fundbook;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.Fundbookday;
import com.yjy.entity.UserBasicInfo;
import com.yjy.repository.mapper.FundbookExtMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 账本刷余逻辑
 * Created by Administrator on 2016/5/31.
 */
@Service("fundbookService")
public class FundbookService {

    private static Logger logger = LoggerFactory.getLogger(FundbookService.class);


    @Resource
    private FundbookExtMapper fundbookExtMapper;


    @Resource
    private JedisTemplate jedisTemplate;

    private static SimpleDateFormat simpleDateFormat_yyyyMM = new SimpleDateFormat("yyyyMM");
    private static SimpleDateFormat simpleDateFormat_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");

    /**
     * 根据日期段一个月一个月的更新余额
     *
     * @param bookcodes
     */
    public void oneByOneUpdateBalance(Date startDate, Date endDate, final List<Fundbookcode> bookcodes,  List<Integer> userids) {

        //轮训每一个月

        while (!(endDate.compareTo(startDate) == -1)) {
            //前一个月最后一天
            final String preMonthLastDay = DateTools.getPreMonthLastDay(startDate, simpleDateFormat_yyyyMMdd);
            final String startStr_yyyyMM = simpleDateFormat_yyyyMM.format(startDate);
            final String tableName = FundConstant.FUNDBOOK_TABLE_NAME_PRE + startStr_yyyyMM;
            List<Integer> selectUserids = null;
            if (userids != null) {
                selectUserids = userids;
            } else {
                //发生数据的用户
                selectUserids = fundbookExtMapper.selectUserids(tableName);
            }
            final int dataSize = selectUserids.size(); //01,23,4;
            final int pageSize = 80;
            final int cacheThreadCount = (dataSize / pageSize) + 1;
            logger.info("总用户:" + dataSize + " 刷余额" + startStr_yyyyMM + " " + cacheThreadCount + "页");
            final CountDownLatch countDownLatch = new CountDownLatch(cacheThreadCount);
            ExecutorService executorService = Executors.newFixedThreadPool(cacheThreadCount);
            for (int j = 1; j <= cacheThreadCount; j++) {
                final int jm = j;
                final List<Integer> selectUseridss = selectUserids;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Fundbook> insertFunbooks = new ArrayList<>();
                        for (int i = (jm - 1) * pageSize; !(i > (jm * pageSize - 1) || i > (dataSize - 1)); i++) {
                            int userid = selectUseridss.get(i);
                            //当期有发生数据的账本
                            List<String> bookcodeList = fundbookExtMapper.selectBookcodes(tableName, userid);

                            for (String bookcode : bookcodeList) {
                                if (bookcodes != null && bookcodes.size() > 0 && !isContains(bookcodes, bookcode))//如果不要求刷的 就不用刷了
                                    continue;

                                Fundbookday fundbookday = new Fundbookday(); //查询上一个月的(查询条件)
                                fundbookday.setBookcode(bookcode);
                                fundbookday.setUserid(userid);
                                //前一个月 这个用户 这个账本 最后一条数据
                                String prebalanceStr = null;
                                if (!startStr_yyyyMM.equals("201309")) {//201309之前没有数据
                                    String prebalancekey = String.format("%s|-%s|-%s", preMonthLastDay, bookcode, userid);
                                    prebalanceStr = jedisTemplate.get(prebalancekey);
                                }
                                BigDecimal firstPreBalance = new BigDecimal("0");
                                Fundbook preFundbook = new Fundbook();
                                if (prebalanceStr != null) {
                                    firstPreBalance = new BigDecimal(prebalanceStr);
                                }
                                preFundbook.setBalance(firstPreBalance);

                                //取当前用户账本中的数据
                                Fundbook fundbook = new Fundbook(); // (查询条件)
                                fundbook.setBookcode(bookcode);
                                fundbook.setUserid(userid);
                                List<Fundbook> fundbooks = fundbookExtMapper.selectByExample(fundbook, tableName, 0, 0, false);

                                //轮训账本表中每一条数据
                                if (fundbooks != null && fundbooks.size() > 0) {
                                    for (int m = 0; m < fundbooks.size(); m++) {
                                        Fundbook iFundbook = fundbooks.get(m);
                                        BigDecimal preBalance = preFundbook.getBalance();
                                        if (Integer.parseInt(StringUtils.substring(bookcode, 0, 4)) == FundConstant.FUND_TYPE_DEBT) {
                                            //负债类公式: 上期贷余 + 本期发生贷 - 本期发生借 = 本期贷余
                                            BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                                            BigDecimal debit = iFundbook.getDebit();  //当期发生借
                                            BigDecimal iBalance = preBalance.add(credit).subtract(debit);
                                            iFundbook.setBalance(iBalance);
                                        } else {
                                            BigDecimal credit = iFundbook.getCredit();  //当期发生贷
                                            BigDecimal debit = iFundbook.getDebit();  //当期发生借
                                            //资产类公式和损益类: 上期借余 + 本期发生借 - 本期发生贷 = 本期借余
                                            BigDecimal iBalance = preBalance.add(debit).subtract(credit);
                                            iFundbook.setBalance(iBalance);
                                        }
                                        insertFunbooks.add(iFundbook);
                                        if (insertFunbooks.size() % 10000 == 0) {
                                            logger.info(jm + " 准备更新数量" + insertFunbooks.size());
                                            fundbookExtMapper.batchUpdateByPrimaryKeySelective(insertFunbooks, tableName);
                                            insertFunbooks = new ArrayList<>();
                                            logger.info(jm + " " + i + " 更新完成一次");
                                        }
                                        //轮训下一个条
                                        preFundbook = iFundbook;
                                    }
                                }

                            }

                        }
                        if(insertFunbooks.size()>0){
                            logger.info(jm + " 本线程最后一次更新余额" + insertFunbooks.size());
                            fundbookExtMapper.batchUpdateByPrimaryKeySelective(insertFunbooks, tableName);
                        }
                        insertFunbooks = null;
                        countDownLatch.countDown();
                    }
                });
            }
            try {

                countDownLatch.await();
                executorService.shutdown();
            } catch (Exception e) {
                logger.error("刷新余额", e);
            }
            logger.info(tableName + "本月余额刷完了");

            startDate = getNextMonthsDate(startDate);

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

    private boolean isContains(List<Fundbookcode> bookcodes, String bookCode) {
        for (Fundbookcode s : bookcodes) {
            if (s.getBookcode().equals(bookCode))
                return true;
        }
        return false;
    }


    //分页查询账本数据
    public List<Fundbook> selectPageListByExample(Fundbook fundbook,
                                                  String tableName,
                                                  long startTime,
                                                  long endTime,
                                                  Pagination pagination
    ) {

        return fundbookExtMapper.selectPageListByExample(fundbook, tableName, startTime, endTime, pagination);

    }

}
