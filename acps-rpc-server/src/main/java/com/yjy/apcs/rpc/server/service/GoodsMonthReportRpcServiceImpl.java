package com.yjy.apcs.rpc.server.service;

import com.yjy.apcs.rpc.server.common.ReflectTools;
import com.yjy.apcs.rpc.server.report.GoodsMonthReportRpcService;
import com.yjy.apcs.rpc.server.report.TGoodsMonthPaginationVo;
import com.yjy.apcs.rpc.server.report.TGoodsMonthResponseReport;
import com.yjy.apcs.rpc.server.report.TRequestGoodsMonthVo;
import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsMonth;
import com.yjy.service.report.GoodsMonthService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
@Service("goodsMonthReportRpcServiceImpl")
public class GoodsMonthReportRpcServiceImpl implements GoodsMonthReportRpcService.Iface{

    private static Logger logger = LoggerFactory.getLogger(GoodsMonthReportRpcServiceImpl.class);


    @Resource
    private GoodsMonthService goodsMonthService;



    @Override
    public TGoodsMonthPaginationVo findGoodsMonthPagination(TRequestGoodsMonthVo requestGoodsMonthVo) throws TException {
        GoodsMonth goodsMonth = new GoodsMonth();
        goodsMonth.setSalerid(requestGoodsMonthVo.getSalerid());

        Pagination pagination = new Pagination(requestGoodsMonthVo.getCurrentPage(), requestGoodsMonthVo.getPageSize());

        TGoodsMonthPaginationVo paginationVo = gettGoodsMonthPaginationVo(goodsMonth, pagination);
        return paginationVo;
    }

    private TGoodsMonthPaginationVo gettGoodsMonthPaginationVo(GoodsMonth goodsMonth, Pagination pagination) {

        TGoodsMonthPaginationVo paginationVo = new TGoodsMonthPaginationVo();


        Pagination<GoodsMonth> resuletPagination = goodsMonthService.findGoodsMonthPagination(pagination, goodsMonth);


        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<GoodsMonth> data = resuletPagination.getData();
            List<TGoodsMonthResponseReport> resultdata = new ArrayList<>();
            if (data != null&&data.size()>0) {
                for (GoodsMonth orig : data) {
                    TGoodsMonthResponseReport dest = new TGoodsMonthResponseReport();
                    if (orig != null) {
                        ReflectTools.copyBean(dest, orig);
                    }
                    resultdata.add(dest);
                }
            }
            paginationVo.setData(resultdata);
        } catch (Exception e) {
            logger.error("返回结果复制对象报错", e);
        }
        return paginationVo;
    }


    @Override
    public TGoodsMonthPaginationVo findGoodsMonthById(com.yjy.apcs.rpc.server.report.TRequestGoodsMonthByIdVo requestGoodsMonthByIdVo) throws TException {
        GoodsMonth goodsMonth = new GoodsMonth();
        goodsMonth.setSalerid(requestGoodsMonthByIdVo.getSalerid());
        goodsMonth.setId(Long.valueOf(requestGoodsMonthByIdVo.getId()));
        Pagination pagination = new Pagination(1,1);
        TGoodsMonthPaginationVo paginationVo = gettGoodsMonthPaginationVo(goodsMonth, pagination);
        return paginationVo;
    }
}
