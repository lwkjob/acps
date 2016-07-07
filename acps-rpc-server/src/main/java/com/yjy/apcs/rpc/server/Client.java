package com.yjy.apcs.rpc.server;

import com.yjy.apcs.rpc.server.report.ReportDetailRpcService;
import com.yjy.apcs.rpc.server.report.TReportDetailPaginationVo;
import com.yjy.apcs.rpc.server.report.TRequestReportDetailVo;
import com.yjy.apcs.rpc.server.report.TResponseReportDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.*;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Administrator on 2016/7/5.
 */
public class Client {
    private static final Logger logger = LoggerFactory.getLogger(AcpsRpcStartupMain.class);


    public static void main(String[] args) throws Exception{
//        TTransport tTransport=new TFastFramedTransport(new TSocket("192.168.2.12",1320));
        TTransport tTransport=new TFastFramedTransport(new TSocket("192.168.2.130",1320));
//        TTransport tTransport=new TFastFramedTransport(new TSocket("172.31.9.125",1320));
        tTransport.open();

        TProtocol protocol=new TBinaryProtocol(tTransport);

        TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"ReportDetailRpcService");

        ReportDetailRpcService.Client client=new ReportDetailRpcService.Client(protocol,mp1);

        TRequestReportDetailVo requestRrportVo=new TRequestReportDetailVo();
//      requestRrportVo.setBuyerCompanyname("1");
//      requestRrportVo.setBuyerLinkman("2");
        requestRrportVo.setInvoicenumber("DD5115020004691");
        requestRrportVo.setBuyerCompanyname("DD5115020004691");
        requestRrportVo.setBuyerLinkman("DD5115020004691");
        requestRrportVo.setPageSize(10);
        requestRrportVo.setCurrentPage(1);
//        requestRrportVo.setIsSum(true);
        TReportDetailPaginationVo paginationGoodsOutList = client.findGoodsOutPagination(requestRrportVo);
//        TReportDetailPaginationVo paginationGoodsOutList = client.findGoodsBalancePagination(requestRrportVo);

        List<TResponseReportDetail> data = paginationGoodsOutList.getData();
        if (data!=null)
        for(TResponseReportDetail goodsOut:data){
            logger.info(goodsOut.getBuyerCompanyname()+"  "+goodsOut.getInvoicenumber()+" "+goodsOut.getTotalgoodsprice());
        }
//        TimeUnit.SECONDS.sleep(100000);
        tTransport.close();


    }
}

