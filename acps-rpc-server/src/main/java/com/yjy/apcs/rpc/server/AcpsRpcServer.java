package com.yjy.apcs.rpc.server;

import com.yjy.apcs.rpc.server.report.FundCapitalRpcService;
import com.yjy.apcs.rpc.server.service.ReportRpcServiceImpl;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class AcpsRpcServer {
	Logger logger=  LoggerFactory.getLogger(AcpsRpcServer.class);


	@Resource(name="reportRpcServiceImpl")
	private ReportRpcServiceImpl reportRpcServiceImpl;


	private int point;


	public AcpsRpcServer(int point) {
		this.point=point;
	}

	public AcpsRpcServer(){
	}

	public TServer startServer() {

		try {
			TNonblockingServerTransport serverSocket = new TNonblockingServerSocket(this.point);
			TTransportFactory tTransportFactory=new TFramedTransport.Factory();

			TProtocolFactory protocolFactory=new TBinaryProtocol.Factory();

			FundCapitalRpcService.Processor process = new FundCapitalRpcService.Processor(reportRpcServiceImpl);


			TMultiplexedProcessor protocalClazz = new TMultiplexedProcessor();
			protocalClazz.registerProcessor("FundCapitalRpcService",process);
			TServer server = new TThreadedSelectorServer(
					new TThreadedSelectorServer
							.Args(serverSocket)
							.protocolFactory(protocolFactory)
							.transportFactory(tTransportFactory)
							.processor(protocalClazz)
			);
			logger.info("server started...");
			return server;

		} catch (TTransportException e) {
			logger.error("启动报错",e);
		}
		return null;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
}