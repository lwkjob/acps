package com.yjy.web.vo;

/**
 * Created by liwenke on 16/5/30.
 */
public class UpdateBalanceVo {

    private String startDate;
    private String endDate;
    private Integer userid;
    private String accBook; //角色-用户角色-账本编号; 格式如: 1103-1151-1217
    private Integer typeid; //用户类型 3平台 2卖家 1买家

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getAccBook() {
        return accBook;
    }

    public void setAccBook(String accBook) {
        this.accBook = accBook;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }
}
