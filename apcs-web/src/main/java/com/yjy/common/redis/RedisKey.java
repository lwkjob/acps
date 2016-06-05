package com.yjy.common.redis;



/**
 * Created with IntelliJ IDEA.
 * User: zhanghang
 * Date: 2015/5/26
 * Time: 19:00
 * To change this template use File | Settings | File Templates.
 */
public class RedisKey {
	
	public static String PRE_USER = "WL_USER_STATUS_";

	public static int ONE_DAY = 60 * 60 * 24;

    public static String pre = "WL_";

    public static String msgEventCache = pre + "MSGEVENT_";
    
    //abTest的公司类型队列
    public static String ABTEST_QUEUE_CACHE_BY_COMPANY = pre + "ABTEST_COMPANY";
    
    //abTest的个人类型队列
    public static String ABTEST_QUEUE_CACHE_BY_INDIVIDUAL = pre + "ABTEST_INDIVIDUAL";
    
    /**
     *  单个寻车方法的处理订单数量的缓存key
     ** @param selectDriverMethodId
     ** @return
     ** @date:2015-6-8
     ** @author: zengyang
     *
     */
    public static String getAbTestCacheKey(Long selectDriverMethodId){
    	return pre + "ABTEST_QUEUE_" + selectDriverMethodId;
    }
    
    //寻车引擎寻找到的车辆集合的大小缓存key
    public static String SELECT_ENGINE_POOL_SIZE = pre + "SELECT_ENGINE_POOL_SIZE";
    
    //寻车的类型个数比例缓存key {"company": 80, "driver": 20}，表示物流公司是80%，个人是20%
    public static String SELECT_ENGINE_TYPE_PROPORTION =	pre + "SELECT_ENGINE_TYPE_PROPORTION";
    
    public static String getSMSShareCodeKey(String mobile, Byte accountType, Byte businessType) {
    	return mobile + "_" + accountType + "_" + businessType;
    }
    
    /**
     * 用户登录后redis存储用户上下线状态
     * @param accountId
     * @param userType
     * @return
     */
    public static String getUserStatusKey(Long accountId, Byte userType) {
        return PRE_USER + accountId + "_" + userType;
    }
    
    public static String getCarrierOrderRecvStatusKey(Long accountId, Byte accountType) {
    	return "wl_carrier_st_" + accountId + "_" + accountType.intValue();
    }
    
    /**
     * 获取货主端APP登录后，用户登录的token redis key
     */
    public static String getShipperAPPLoginTokenKey(Long accountId) {
        return "WL_USER_TOKEN_" + accountId + "_S";
    }

    /**
     * 获取司机端APP登录后，用户登录的token redis key
     */
    public static String getDriverAPPLoginTokenKey(Long accountId) {
        return "WL_USER_TOKEN_" + accountId + "_D";
    }
    
    public static String getLoginTokenKey(Long accountId) {
        return "WL_USER_TOKEN_" + accountId;
    }
    
    /**
     * 拉黑用户rediskey
     * @param accountId
     * @return
     */
    public static String wlCarrierBlackUserKey(Long accountId) {
    	return "WL_D_BE_BLACKED_" + accountId;
    }

    /**
     * token失效key
     * @param accountId
     * @return
     */
    public static String wlCarrierTokenInvalidKey(Long accountId) {
    	return "WL_D_BE_INVALID_TOKEN_" + accountId;
    }
    
    /**
     * 物流车主上线判断是否输入重量体积标志key
     * @param accountId
     * @return
     */
    public static String wlCarrierOnlineWVKey(Long accountId) {
    	return "WL_D_ONLINE_W_V_" + accountId;
    }
    
    /**
     * 通过passportId获取account信息redis key
     * @param passportId
     * @return
     */
    public static String getAccountInfoByPassportId(Long passportId) {
    	return "ACCOUNT_INFO_OF_PASSPORTID_" + passportId;
    }
}
