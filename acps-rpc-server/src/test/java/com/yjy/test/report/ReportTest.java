package com.yjy.test.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsOut;
import com.yjy.service.report.FundsMonthService;
import com.yjy.service.report.GoodsOutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:test-config.xml") // 加载配置
public class ReportTest {

    private static Logger logger= LoggerFactory.getLogger(ReportTest.class);


    @Resource
    private FundsMonthService fundsMonthService;


    @Resource
    private GoodsOutService goodsOutService;

    @Test
    public void findPaginationMonthList(){
//        Pagination pagination=new Pagination(2,10);
//        FundsMonthExample fundsMonthExample=new FundsMonthExample();
//        Pagination<FundsMonth> quereyPagination= fundsMonthService.findFundsMonthPagination(pagination, fundsMonthExample);
//        List<FundsMonth> data = quereyPagination.getData();
//        logger.info(quereyPagination.getCurrentPage()+ " "+quereyPagination.getPageSize());
//        for (FundsMonth fundsMonth: data){
//            logger.info(fundsMonth.getSalerid()+"  "+fundsMonth.getCurbalance());
//        }
    }

    @Test
    public void findGoodsOutList(){
        Pagination pagination=new Pagination(1,10);
        GoodsOut goodsOutExample=new GoodsOut();
        goodsOutExample.setBuyerCompanyname("百姓药业火炬药品");
//        goodsOutExample.setInvoicenumber("DD6515120001859");
//          goodsOutExample.setBuyerLinkman("梁建");
         goodsOutService.findGoodsOutPagination(pagination, goodsOutExample);
        List<GoodsOut> goodsOuts = goodsOutService.sumGoodsOutPagination(goodsOutExample);
        List<GoodsOut> data = pagination.getData();
        logger.info(pagination.getCurrentPage()+ " "+pagination.getPageSize());
        for (GoodsOut goodsOut: data){
            logger.info(goodsOut.getSalerid()+"  "+goodsOut.getBuyerCompanyname()+ " "+goodsOut.getInvoicenumber());
        }
    }
}
