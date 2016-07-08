package com.yjy.apcs.rpc.server.service;


import com.yjy.apcs.rpc.server.common.ReflectTools;
import com.yjy.apcs.rpc.server.report.ReportDetailRpcService;
import com.yjy.apcs.rpc.server.report.TReportDetailPaginationVo;
import com.yjy.apcs.rpc.server.report.TRequestReportDetailVo;
import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.*;
import com.yjy.service.report.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
@Service("reportDetailRpcServiceImpl")
public class ReportDetailRpcServiceImpl implements ReportDetailRpcService.Iface {

    private static Logger logger = LoggerFactory.getLogger(ReportDetailRpcServiceImpl.class);

    @Resource
    private GoodsOutService goodsOutService;
    @Resource
    private GoodsBalanceService goodsBalanceService;
    @Resource
    private GoodsReceiptService goodsReceiptService;
    @Resource
    private GoodsRefundPostService goodsRefundPostService;
    @Resource
    private FundsBalanceService fundsBalanceService;
    @Resource
    private FundsMarkupService fundsMarkupService;
    @Resource
    private FundsReceiptService fundsReceiptService;
    @Resource
    private FundsWriteBackService fundsWriteBackService;
    @Resource
    private FundsSettleService fundsSettleService;


    @Override
    public TReportDetailPaginationVo findGoodsOutPagination(TRequestReportDetailVo requestReportVo) throws TException {
        GoodsOut goodsOutExample = new GoodsOut();
        goodsOutExample.setInvoicenumber(requestReportVo.getInvoicenumber());
        goodsOutExample.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        goodsOutExample.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        goodsOutExample.setParentid(requestReportVo.getParentId());
        goodsOutExample.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());

        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<GoodsOut> resuletPagination = new Pagination<>(1, 1);
        if (requestReportVo.isIsSum()) {
            //求和
            List<GoodsOut> goodsOuts = goodsOutService.sumGoodsOutPagination(goodsOutExample);
            resuletPagination.setData(goodsOuts);
        } else {
            resuletPagination = goodsOutService.findGoodsOutPagination(pagination, goodsOutExample);
        }

        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<GoodsOut> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (GoodsOut orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
    public TReportDetailPaginationVo findGoodsBalancePagination(TRequestReportDetailVo requestReportVo) throws TException {
        GoodsBalance goodsBalance = new GoodsBalance();
        goodsBalance.setInvoicenumber(requestReportVo.getInvoicenumber());
        goodsBalance.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        goodsBalance.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        goodsBalance.setParentid(requestReportVo.getParentId());
        goodsBalance.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<GoodsBalance> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<GoodsBalance> goodsBalances = goodsBalanceService.sumGoodsBalancePagination(goodsBalance);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = goodsBalanceService.findGoodsBalancePagination(pagination, goodsBalance);
        }

        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<GoodsBalance> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (GoodsBalance orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
    public TReportDetailPaginationVo findGoodsReceiptPagination(TRequestReportDetailVo requestReportVo) throws TException {
        GoodsReceipt goodsReceipt = new GoodsReceipt();
        goodsReceipt.setInvoicenumber(requestReportVo.getInvoicenumber());
        goodsReceipt.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        goodsReceipt.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        goodsReceipt.setParentid(requestReportVo.getParentId());
        goodsReceipt.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();
        Pagination<GoodsReceipt> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<GoodsReceipt> goodsBalances = goodsReceiptService.sumGoodsReceiptPaginationList(goodsReceipt);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = goodsReceiptService.findGoodsReceiptPaginationList(pagination, goodsReceipt);
        }

        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());

            List<GoodsReceipt> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (GoodsReceipt orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();

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
    public TReportDetailPaginationVo findGoodsRefundPagination(TRequestReportDetailVo requestReportVo) throws TException {
        GoodsRefundPost goodsRefundPost = new GoodsRefundPost();
        goodsRefundPost.setInvoicenumber(requestReportVo.getInvoicenumber());
        goodsRefundPost.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        goodsRefundPost.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        goodsRefundPost.setParentid(requestReportVo.getParentId());
        goodsRefundPost.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<GoodsRefundPost> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<GoodsRefundPost> goodsBalances = goodsRefundPostService.sumGoodsRefundPostPagination(goodsRefundPost);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = goodsRefundPostService.findGoodsRefundPostPagination(pagination, goodsRefundPost);
        }

        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<GoodsRefundPost> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (GoodsRefundPost orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
    public TReportDetailPaginationVo findFundsBalancePagination(TRequestReportDetailVo requestReportVo) throws TException {
        FundsBalance fundsBalance = new FundsBalance();
        fundsBalance.setInvoicenumber(requestReportVo.getInvoicenumber());
        fundsBalance.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        fundsBalance.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        fundsBalance.setParentid(requestReportVo.getParentId());
        fundsBalance.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<FundsBalance> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<FundsBalance> goodsBalances = fundsBalanceService.sumFundsBalancePagination(fundsBalance);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = fundsBalanceService.findFundsBalancePagination(pagination, fundsBalance);
        }


        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<FundsBalance> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (FundsBalance orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
    public TReportDetailPaginationVo findFundsMarkupPagination(TRequestReportDetailVo requestReportVo) throws TException {
        FundsMarkup fundsMarkup = new FundsMarkup();
        fundsMarkup.setInvoicenumber(requestReportVo.getInvoicenumber());
        fundsMarkup.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        fundsMarkup.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        fundsMarkup.setParentid(requestReportVo.getParentId());
        fundsMarkup.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<FundsMarkup> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<FundsMarkup> goodsBalances = fundsMarkupService.sumFundsMarkupPagination(fundsMarkup);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = fundsMarkupService.findFundsMarkupPagination(pagination, fundsMarkup);
        }


        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<FundsMarkup> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (FundsMarkup orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
    public TReportDetailPaginationVo findFundsReceiptPagination(TRequestReportDetailVo requestReportVo) throws TException {
        FundsReceipt fundsReceipt = new FundsReceipt();
        fundsReceipt.setInvoicenumber(requestReportVo.getInvoicenumber());
        fundsReceipt.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        fundsReceipt.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        fundsReceipt.setParentid(requestReportVo.getParentId());
        fundsReceipt.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<FundsReceipt> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<FundsReceipt> goodsBalances = fundsReceiptService.sumFundsReceiptPagination(fundsReceipt);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = fundsReceiptService.findFundsReceiptPagination(pagination, fundsReceipt);
        }

        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<FundsReceipt> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (FundsReceipt orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
    public TReportDetailPaginationVo findFundsSettlePagination(TRequestReportDetailVo requestReportVo) throws TException {
        FundsSettle fundsSettle = new FundsSettle();
        fundsSettle.setInvoicenumber(requestReportVo.getInvoicenumber());
        fundsSettle.setParentid(requestReportVo.getParentId());
        fundsSettle.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<FundsSettle> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<FundsSettle> goodsBalances = fundsSettleService.sumFundsSettlePagination(fundsSettle);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = fundsSettleService.findFundsSettlePagination(pagination, fundsSettle);
        }

        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<FundsSettle> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (FundsSettle orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
    public TReportDetailPaginationVo findFundsWriteBackPagination(TRequestReportDetailVo requestReportVo) throws TException {
        FundsWriteBack fundsWriteBack = new FundsWriteBack();
        fundsWriteBack.setInvoicenumber(requestReportVo.getInvoicenumber());
        fundsWriteBack.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        fundsWriteBack.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        fundsWriteBack.setParentid(requestReportVo.getParentId());
        fundsWriteBack.setSalerid(requestReportVo.getSalerid());
        Pagination pagination = new Pagination(requestReportVo.getCurrentPage(), requestReportVo.getPageSize());
        TReportDetailPaginationVo paginationVo = new TReportDetailPaginationVo();

        Pagination<FundsWriteBack> resuletPagination = new Pagination<>();
        if (requestReportVo.isIsSum()) {
            //求和
            List<FundsWriteBack> goodsBalances = fundsWriteBackService.sumFundsWriteBackPagination(fundsWriteBack);
            resuletPagination.setData(goodsBalances);
        } else {
            resuletPagination = fundsWriteBackService.findFundsWriteBackPagination(pagination, fundsWriteBack);
        }


        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<FundsWriteBack> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReportDetail> resultdata = new ArrayList<>();
            if (data != null && data.size() > 0) {
                for (FundsWriteBack orig : data) {
                    com.yjy.apcs.rpc.server.report.TResponseReportDetail dest = new com.yjy.apcs.rpc.server.report.TResponseReportDetail();
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
}
