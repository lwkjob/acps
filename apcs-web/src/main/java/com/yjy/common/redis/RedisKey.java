package com.yjy.common.redis;




public class RedisKey {

	public static String FUNDBOOK_DAY_REPOOT = "FUNDBOOK_DAY_REPOOT"; //统计任务
	public static String FUNDBOOK_DAY_PRE_DAY_BALANCE = "FUNDBOOK_DAY_PRE_DAY_BALANCE"; //前一天的余额
	public static String USERS_OF_DAY = "USERS_OF_DAY"; //当天的活跃用户数
	public static String REPORT_OF_DAY_PUB_KEY = "REPORT_OF_DAY_PUB_KEY"; //每日统计任务发布订阅key
	public static String REPORT_OF_DAY_DISTRIBUTE = "REPORT_OF_DAY_PUB_KEY"; //每日统计集群任务分配
	public static String REPORT_OF_DAY_DISTRIBUTE_USER = "REPORT_OF_DAY_PUB_KEY"; //每日统计任务分配每个实例分到的用户


}
