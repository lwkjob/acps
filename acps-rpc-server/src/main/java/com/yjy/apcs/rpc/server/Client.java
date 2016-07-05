package com.yjy.apcs.rpc.server;

import com.yjy.apcs.rpc.server.report.FundCapitalRpcService;
import com.yjy.apcs.rpc.server.report.TPaginationVo;
import com.yjy.apcs.rpc.server.report.TRequestReportVo;
import com.yjy.apcs.rpc.server.report.TResponseReport;
import org.apache.thrift.TNonblockingMultiFetchClient;
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
        TTransport tTransport=new TFastFramedTransport(new TSocket("localhost",1320));
        tTransport.open();

//        TProtocol protocol=new TCompactProtocol(tTransport);
        TProtocol protocol=new TBinaryProtocol(tTransport);
        TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"FundCapitalRpcService");
        FundCapitalRpcService.Client client=new FundCapitalRpcService.Client(protocol,mp1);

        TRequestReportVo requestRrportVo=new TRequestReportVo();
//      requestRrportVo.setBuyerCompanyname("1");
//      requestRrportVo.setBuyerLinkman("2");
        requestRrportVo.setInvoicenumber("DD5115020004691");
        requestRrportVo.setPageSize(10);
        requestRrportVo.setCurrentPage(1);
        TPaginationVo paginationGoodsOutList = client.findPaginationGoodsOutList(requestRrportVo);

        List<TResponseReport> data = paginationGoodsOutList.getData();
        if (data!=null)
        for(TResponseReport goodsOut:data){
            logger.info(goodsOut.getBuyerCompanyname()+"  "+goodsOut.getInvoicenumber()+" "+goodsOut.getTotalgoodsprice());
        }
//        TimeUnit.SECONDS.sleep(100000);
        tTransport.close();
    }
}

