package com.yjy.apcs.rpc.server.service;


import com.yjy.apcs.rpc.server.report.FundCapitalRpcService;
import com.yjy.apcs.rpc.server.report.TPaginationVo;
import com.yjy.apcs.rpc.server.report.TRequestReportVo;
import com.yjy.common.dao.Pagination;
import com.yjy.entity.report.GoodsOut;
import com.yjy.service.report.GoodsOutService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
@Service("reportRpcServiceImpl")
public class ReportRpcServiceImpl implements FundCapitalRpcService.Iface {

    private static Logger logger=  LoggerFactory.getLogger(ReportRpcServiceImpl.class);

    @Resource
    private GoodsOutService goodsOutService;


    @Override
    public TPaginationVo findPaginationGoodsOutList(TRequestReportVo requestReportVo) throws TException {
        GoodsOut goodsOutExample=new GoodsOut();
        goodsOutExample.setInvoicenumber(requestReportVo.getInvoicenumber());
        goodsOutExample.setBuyerLinkman(requestReportVo.getBuyerLinkman());
        goodsOutExample.setBuyerCompanyname(requestReportVo.getBuyerCompanyname());
        Pagination pagination=new Pagination(requestReportVo.getCurrentPage(),requestReportVo.getPageSize());
        TPaginationVo paginationVo=new TPaginationVo();
        Pagination<GoodsOut> resuletPagination = goodsOutService.findPaginationGoodsOutList(pagination, goodsOutExample);
        try {
            paginationVo.setCurrentPage(resuletPagination.getCurrentPage());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setPageSize(resuletPagination.getPageSize());
            paginationVo.setCount(resuletPagination.getCount());
            List<GoodsOut> data = resuletPagination.getData();
            List<com.yjy.apcs.rpc.server.report.TResponseReport> resultdata=new ArrayList<>();
            if(data!=null){
                for(GoodsOut orig:data){
                    com.yjy.apcs.rpc.server.report.TResponseReport dest=new com.yjy.apcs.rpc.server.report.TResponseReport();
                    copyBean(dest,orig);
                    resultdata.add(dest);
                }
            }
            paginationVo.setData(resultdata);
        }catch (Exception e){
            logger.error("返回结果复制对象报错",e);
        }
        return paginationVo;
    }

    private  void copyBean(Object dest,Object orig) throws Exception{
        Field[] fields=orig.getClass().getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            Object o = field.get(orig);
            String fieldName = field.getName();
            String fileTypeStr=field.getType().getName();
            if(o==null||fieldName.equals("serialVersionUID")){
                continue;
            }

            Object value=null;
            BigDecimal bigDecimal=null;
            if(fileTypeStr.equals("java.math.BigDecimal")){
                bigDecimal=(BigDecimal)o;
                value= bigDecimal.doubleValue();
            }else {
                value=o;
            }
            Field destField=   dest.getClass().getDeclaredField(fieldName);
            String setMethonName = "set"+fieldName.substring(0, 1).toUpperCase()  + fieldName.substring(1);
            destField.setAccessible(true);
            Method setMethon=dest.getClass().getMethod(setMethonName, destField.getType());
            setMethon.invoke(dest,value);
            destField.setAccessible(true);
        }
    }
}
