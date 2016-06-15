package com.yjy.common.constant;

/**
 * Created by Administrator on 2016/6/1.
 */
public class FundConstant {

    public static final int FUND_TYPE_ASSETS = 9001; //资产   //资产类公式和损益类: 上期借余 + 本期发生借 - 本期发生贷 = 本期借余
    public static final int FUND_TYPE_DEBT = 9002;  //负债  //负债类公式: 上期贷余 + 本期发生贷 - 本期发生借 = 本期贷余
    public static final int FUND_TYPE_GAINS = 9003; //损益  //资产类公式和损益类: 上期借余 + 本期发生借 - 本期发生贷 = 本期借余

    public static final int ROLECODE_BUYER = 1101; //买家
    public static final int ROLECODE_SALES = 1102; //卖家
    public static final int ROLECODE_PLATFORM = 1103; //平台家

    public static final int TYPEID_BUYER = 1; //买家
    public static final int TYPEID_SALES = 2; //卖家
    public static final int TYPEID_PLATFORM = 3; //平台家

    public static  final String FUNDBOOK_TABLE_NAME_PRE ="fundbook"; //账本表名前缀+yyyyMM
    public static  final String FUNDBOOKDAY_TABLE_NAME_PRE ="fundbookday"; //日清表名前缀+yyyyMM
    public static  final String FUNDBOOKMONTH_TABLE_NAME_PRE ="fundbookmonth"; //月结表名前缀+yyyyMM


}
