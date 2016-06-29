package com.yjy.common.redis;




public class RedisKey {
	
	public static String USERS_OF_DAY = "USERS_OF_DAY"; //当天的活跃用户数
	public static String REPORT_OF_DAY_SUB_QUEUE = "REPORT_OF_DAY_SUB_QUEUE"; //每天子任务队列，在REPORT_OF_DAY_SUB_QUEUE取到数据后再这个队列取详细数据
	public static String REPORT_OF_DAY_SUB_QUEUE_TEMP = "REPORT_OF_DAY_SUB_QUEUE"; //每天子任务临时队列,主任务永远只放一个任务，子任务取
	public static String REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL = "REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL"; //没完成一个子任务就+1，主服务收到通知后取出来与zk节点中的总数做对比
	public static String REPORT_OF_DAY_QUEUE = "REPORT_OF_DAY_QUEUE"; //每天任务队列,主服务在这里取数据
	public static String BOOKCODEMAP= "BOOKCODEMAP"; // FundConstant.TYPEID_BUYER 做key的账本map
	public static String MONTH_CACHE= "MONTH_CACHE"; // FundConstant.TYPEID_BUYER 做key的账本map



}
