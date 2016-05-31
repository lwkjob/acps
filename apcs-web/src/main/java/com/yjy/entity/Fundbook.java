package com.yjy.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Fundbook implements Serializable {
    private Long bookid;

    private Long entryid;

    private Integer platformrole;

    private Integer entryuserrole;

    private Integer accbooknumber;

    private String entrynumber;

    private Integer userid;

    private Integer areacode;

    private String invoicenumber;

    private Long happentime;

    private BigDecimal goods;

    private BigDecimal money;

    private BigDecimal balance;

    private static final long serialVersionUID = 1L;

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
    }

    public Long getEntryid() {
        return entryid;
    }

    public void setEntryid(Long entryid) {
        this.entryid = entryid;
    }

    public Integer getPlatformrole() {
        return platformrole;
    }

    public void setPlatformrole(Integer platformrole) {
        this.platformrole = platformrole;
    }

    public Integer getEntryuserrole() {
        return entryuserrole;
    }

    public void setEntryuserrole(Integer entryuserrole) {
        this.entryuserrole = entryuserrole;
    }

    public Integer getAccbooknumber() {
        return accbooknumber;
    }

    public void setAccbooknumber(Integer accbooknumber) {
        this.accbooknumber = accbooknumber;
    }

    public String getEntrynumber() {
        return entrynumber;
    }

    public void setEntrynumber(String entrynumber) {
        this.entrynumber = entrynumber == null ? null : entrynumber.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAreacode() {
        return areacode;
    }

    public void setAreacode(Integer areacode) {
        this.areacode = areacode;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber == null ? null : invoicenumber.trim();
    }

    public Long getHappentime() {
        return happentime;
    }

    public void setHappentime(Long happentime) {
        this.happentime = happentime;
    }

    public BigDecimal getGoods() {
        return goods;
    }

    public void setGoods(BigDecimal goods) {
        this.goods = goods;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Fundbook other = (Fundbook) that;
        return (this.getBookid() == null ? other.getBookid() == null : this.getBookid().equals(other.getBookid()))
            && (this.getEntryid() == null ? other.getEntryid() == null : this.getEntryid().equals(other.getEntryid()))
            && (this.getPlatformrole() == null ? other.getPlatformrole() == null : this.getPlatformrole().equals(other.getPlatformrole()))
            && (this.getEntryuserrole() == null ? other.getEntryuserrole() == null : this.getEntryuserrole().equals(other.getEntryuserrole()))
            && (this.getAccbooknumber() == null ? other.getAccbooknumber() == null : this.getAccbooknumber().equals(other.getAccbooknumber()))
            && (this.getEntrynumber() == null ? other.getEntrynumber() == null : this.getEntrynumber().equals(other.getEntrynumber()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getAreacode() == null ? other.getAreacode() == null : this.getAreacode().equals(other.getAreacode()))
            && (this.getInvoicenumber() == null ? other.getInvoicenumber() == null : this.getInvoicenumber().equals(other.getInvoicenumber()))
            && (this.getHappentime() == null ? other.getHappentime() == null : this.getHappentime().equals(other.getHappentime()))
            && (this.getGoods() == null ? other.getGoods() == null : this.getGoods().equals(other.getGoods()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBookid() == null) ? 0 : getBookid().hashCode());
        result = prime * result + ((getEntryid() == null) ? 0 : getEntryid().hashCode());
        result = prime * result + ((getPlatformrole() == null) ? 0 : getPlatformrole().hashCode());
        result = prime * result + ((getEntryuserrole() == null) ? 0 : getEntryuserrole().hashCode());
        result = prime * result + ((getAccbooknumber() == null) ? 0 : getAccbooknumber().hashCode());
        result = prime * result + ((getEntrynumber() == null) ? 0 : getEntrynumber().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getAreacode() == null) ? 0 : getAreacode().hashCode());
        result = prime * result + ((getInvoicenumber() == null) ? 0 : getInvoicenumber().hashCode());
        result = prime * result + ((getHappentime() == null) ? 0 : getHappentime().hashCode());
        result = prime * result + ((getGoods() == null) ? 0 : getGoods().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }
}