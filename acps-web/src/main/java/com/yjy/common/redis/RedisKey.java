package com.yjy.common.redis;




public class RedisKey {
	
	public static String FUNDBOOK_DAY_REPOOT = "FUNDBOOK_DAY_REPOOT"; //统计任务
	public static String FUNDBOOK_DAY_PRE_DAY_BALANCE = "FUNDBOOK_DAY_PRE_DAY_BALANCE"; //前一天的余额
	public static String USERS_OF_DAY = "USERS_OF_DAY"; //当天的活跃用户数
	public static String REPORT_OF_DAY = "REPORT_OF_DAY"; //每日统计任务
	public static String REPORT_OF_DAY_SUB_QUEUE = "REPORT_OF_DAY_SUB_QUEUE"; //每天子任务队列
	public static String REPORT_OF_DAY_SUB_QUEUE_TEMP = "REPORT_OF_DAY_SUB_QUEUE"; //每天子任务临时队列
	public static String REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL = "REPORT_OF_DAY_SUB_QUEUE_TASK_TOTAL"; //每天子任务队列任务数量
	public static String REPORT_OF_DAY_QUEUE = "REPORT_OF_DAY_QUEUE"; //每天任务队列
	public static String REPORT_OF_DAY_SUB_LISSTENER = "REPORT_OF_DAY_SUB_LISSTENER"; //子任务监听
	public static String REPORT_OF_DAY_PUB_LISSTENER = "REPORT_OF_DAY_PUB_LISSTENER"; //监听子任务是否搞完了
	public static String    BOOKCODEMAP= "BOOKCODEMAP"; //按照分类号的账本



}
