package com.yjy.apcs.rpc.server.service;

import com.yjy.apcs.rpc.server.common.ReflectTools;
import com.yjy.apcs.rpc.server.report.FundsMonthReportRpcService;
import com.yjy.apcs.rpc.server.report.TFundsMonthPaginationVo;
import com.yjy.apcs.rpc.server.report.TFundsMonthResponseReport;
import com.yjy.apcs.rpc.server.report.TRequestFundsMonthVo;
import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsMonth;
import com.yjy.service.report.FundsMonthService;
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

@Service("fundsMonthReportRpcServiceImpl")
public class FundsMonthReportRpcServiceImpl implements FundsMonthReportRpcService.Iface {

    private static Logger logger = LoggerFactory.getLogger(FundsMonthReportRpcServiceImpl.class);

    @Resource
    private FundsMonthService fundsMonthService;




    @Override
    public TFundsMonthPaginationVo findFundsMonthPagination(TRequestFundsMonthVo requestFundsMonthVo) throws TException {


        FundsMonth fundsMonth = new FundsMonth();
        fundsMonth.setSalerid(requestFundsMonthVo.getSalerid());

        Pagination pagination = new Pagination(requestFundsMonthVo.getCurrentPage(), requestFundsMonthVo.getPageSize());

        TFundsMonthPaginationVo paginationVo = gettFundsMonthPaginationVo(fundsMonth, pagination);
        return paginationVo;
    }

    private TFundsMonthPaginationVo gettFundsMonthPaginationVo(FundsMonth fundsMonth, Pagination pagination) {
        TFundsMonthPaginationVo paginationVo = new TFundsMonthPaginationVo();


        Pagination<FundsMonth> resuletPagination = fundsMonthService.findFundsMonthPagination(pagination, fundsMonth);


        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<FundsMonth> data = resuletPagination.getData();
            List<TFundsMonthResponseReport> resultdata = new ArrayList<>();
            if (data != null&&data.size()>0) {
                for (FundsMonth orig : data) {
                    TFundsMonthResponseReport dest = new TFundsMonthResponseReport();
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
    public TFundsMonthPaginationVo findFundsMonthById(com.yjy.apcs.rpc.server.report.TRequestFundsMonthByIdVo requestFundsMonthByIdVo) throws TException {
        FundsMonth fundsMonth = new FundsMonth();
        fundsMonth.setSalerid(requestFundsMonthByIdVo.getSalerid());
        fundsMonth.setId(Long.valueOf(requestFundsMonthByIdVo.getId()));
        Pagination pagination = new Pagination(1,1);
        TFundsMonthPaginationVo paginationVo = gettFundsMonthPaginationVo(fundsMonth, pagination);
        return paginationVo;
    }
}
