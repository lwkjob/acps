package com.yjy.service;


import com.yjy.common.utils.JsonUtils;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private static SimpleDateFormat simpleDateForma3 = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) {
        long now = System.currentTimeMillis() / 1000;
        System.out.println(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1299723341 * 1000l);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String mm = simpleDateFormat.format(calendar.getTime());

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
        try {
            Date date = simpleDateFormat2.parse("2013-09");
            Date date2 = simpleDateFormat2.parse("2013-08");
            Date date1 = simpleDateForma3.parse("20130901");
            Date parse = simpleDateForma3.parse("20130902");
            logger.info(date1.getTime() / 1000 + "");
            logger.info(parse.getTime() / 1000 + "");
        } catch (Exception e) {
            logger.info("时间转换错误", e);
        }


        System.out.println(mm);


    }


    public List<Bookcode> getBookcodes() {
        List<Bookcode> bookcodes = bookcodeMapper.selectByExample(null);
        return bookcodes;
    }

    public List<UserBasicInfo> getUsers(Map map) {

        return userBasicExtMapper.getUsers(map);

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
                String preTableName = "fundbook" + getPreDateStr(startDate);
                String tableName = "fundbook" + simpleDateFormat.format(startDate);
                doUpdateBalance(bookcodes,
                        userBasicInfo.getUserid(),
                        preTableName,
                        tableName);
                startDate = getNextDate(startDate);
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


    //取前一天字符串格式
    public String getPreDateStr(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, -1);
        return simpleDateFormat.format(preDate);
    }

    //取后一天
    public Date getNextDate(Date startDate) {
        Date preDate = DateUtils.addMonths(startDate, 1);
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
            fundbook.setPlatformrole(bookcode.getPlatformrole());
            fundbook.setEntryuserrole(bookcode.getEntryuserrole());
            fundbook.setAccbooknumber(bookcode.getAccbooknumber());
            fundbook.setUserid(userId);

            List<Fundbook> prefundbooks = null;
            try {
                prefundbooks = fundbookExtMapper.selectByWhere(fundbook, preTableName, true);
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
            List<Fundbook> fundbooks = fundbookExtMapper.selectByWhere(fundbook, tableName, false);
            logger.info("开始统计:" + JsonUtils.toJson(fundbook) + " userId:" + userId + " tableName:" + tableName);
            long startTime = System.currentTimeMillis();


            //轮训账本表中每一条数据
            if (fundbooks != null && fundbooks.size() > 0) {
                for (int i = 0; i < fundbooks.size(); i++) {
                    Fundbook iFundbook = fundbooks.get(i);
                    BigDecimal preBalance = preFundbook.getBalance() == null ? new BigDecimal(0) : preFundbook.getBalance();
                    BigDecimal iMoney = iFundbook.getMoney();
                    BigDecimal iBalance = preBalance.add(iMoney);
                    iFundbook.setBalance(iBalance);
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
