package com.yjy.test.funbook;

import com.yjy.common.constant.FundConstant;
import com.yjy.common.dao.Pagination;
import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.common.utils.JsonUtils;
import com.yjy.entity.Fundbook;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.FundbookcodeExample;
import com.yjy.entity.Fundbookday;
import com.yjy.jedisPubSub.SubPubMessage;
import com.yjy.service.FundbookDayService;
import com.yjy.service.FundbookService;
import com.yjy.service.FundbookcodeService;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenke on 16/6/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:test-config.xml") // 加载配置
public class FundbookTest {

    @Resource
    private FundbookService fundbookService;

     @Resource
    private FundbookDayService fundbookDayService;


    @Resource
    private FundbookcodeService fundbookcodeService;

    @Resource
    private JedisTemplate jedisTemplate;

    private static Logger logger= LoggerFactory.getLogger(Fundbook.class);

    //分页查询账本
    @Test
    public void testPaginnationFundbook(){
        Pagination<Fundbook> pagination=new Pagination(1,2);
        Fundbook example=new Fundbook();
        example.setBookid(8l);
        long startTime=1377964800l;
        long endTime=1377964803l;
        String tablename="fundbook201309";
        List<Fundbook> list =  fundbookService.selectPageListByExample(example,tablename,startTime,endTime,pagination);
        for (Fundbook fundbook:list){
            logger.info(""+fundbook.getBookid()+" "+fundbook.getHappentime());
        }
    }

    //分页更新上月最后一天的余额到redis,从日结表中查
    @Test
    public void getByBookdete(){
        Fundbookday fundbookday=new Fundbookday();
        fundbookday.setBookdate(20131031);
        fundbookDayService.getByBookdete(fundbookday);
    }


    //更新余额
    @Test
    public void oneByOneUpdateBalance(){
        Date startDate= DateTools.parseDateFromString_yyyyMM("201511",logger);
        Date endDate= DateTools.parseDateFromString_yyyyMM("201511",logger);
        fundbookService.oneByOneUpdateBalance(startDate, endDate, null, null);
    }


    @Test
    public void testSub(){
        SubPubMessage subPubMessage=new SubPubMessage();
        subPubMessage.setStartDate("20131201");
        subPubMessage.setEndDate("20131231");
        subPubMessage.setBookcodes(null);
        subPubMessage.setUsers(null);
        logger.info("主线程");
        jedisTemplate.publish(RedisKey.REPORT_OF_DAY, JsonUtils.toJson(subPubMessage));
        logger.info("主线程发布完了");
    }


}
