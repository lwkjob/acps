package com.yjy.repository.dto;

import java.math.BigDecimal;

/**
 *
 * 每个月,当前账本每个用户的分组和
 *
 * Created by liwenke on 16/6/7.
 */
public class SumMonthByBookcode {

    private String bookcode;
    private int userid;
    private BigDecimal debit;
    private BigDecimal credit;

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}
