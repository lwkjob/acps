package com.yjy.web.vo;

/**
 * Created by liwenke on 16/5/30.
 */
public class UpdateBalanceVo {

    private String startDate;
    private String endDate;
    private Integer userId;
    private String accBook; //角色-用户角色-账本编号; 格式如: 1103-1151-1217

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccBook() {
        return accBook;
    }

    public void setAccBook(String accBook) {
        this.accBook = accBook;
    }
}
