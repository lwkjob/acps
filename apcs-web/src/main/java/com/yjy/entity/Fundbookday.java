package com.yjy.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Fundbookday implements Serializable {
    private Long id;

    private Long bookid;

    private Integer platformrole;

    private Integer entryuserrole;

    private Integer accbooknumber;

    private Integer userid;

    private Integer areacode;

    private Integer bookdate;

    private BigDecimal prevbalance;

    private BigDecimal happendebit;

    private BigDecimal happencredit;

    private BigDecimal balance;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookid() {
        return bookid;
    }

    public void setBookid(Long bookid) {
        this.bookid = bookid;
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

    public Integer getBookdate() {
        return bookdate;
    }

    public void setBookdate(Integer bookdate) {
        this.bookdate = bookdate;
    }

    public BigDecimal getPrevbalance() {
        return prevbalance;
    }

    public void setPrevbalance(BigDecimal prevbalance) {
        this.prevbalance = prevbalance;
    }

    public BigDecimal getHappendebit() {
        return happendebit;
    }

    public void setHappendebit(BigDecimal happendebit) {
        this.happendebit = happendebit;
    }

    public BigDecimal getHappencredit() {
        return happencredit;
    }

    public void setHappencredit(BigDecimal happencredit) {
        this.happencredit = happencredit;
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
        Fundbookday other = (Fundbookday) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getBookid() == null ? other.getBookid() == null : this.getBookid().equals(other.getBookid()))
            && (this.getPlatformrole() == null ? other.getPlatformrole() == null : this.getPlatformrole().equals(other.getPlatformrole()))
            && (this.getEntryuserrole() == null ? other.getEntryuserrole() == null : this.getEntryuserrole().equals(other.getEntryuserrole()))
            && (this.getAccbooknumber() == null ? other.getAccbooknumber() == null : this.getAccbooknumber().equals(other.getAccbooknumber()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getAreacode() == null ? other.getAreacode() == null : this.getAreacode().equals(other.getAreacode()))
            && (this.getBookdate() == null ? other.getBookdate() == null : this.getBookdate().equals(other.getBookdate()))
            && (this.getPrevbalance() == null ? other.getPrevbalance() == null : this.getPrevbalance().equals(other.getPrevbalance()))
            && (this.getHappendebit() == null ? other.getHappendebit() == null : this.getHappendebit().equals(other.getHappendebit()))
            && (this.getHappencredit() == null ? other.getHappencredit() == null : this.getHappencredit().equals(other.getHappencredit()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getBookid() == null) ? 0 : getBookid().hashCode());
        result = prime * result + ((getPlatformrole() == null) ? 0 : getPlatformrole().hashCode());
        result = prime * result + ((getEntryuserrole() == null) ? 0 : getEntryuserrole().hashCode());
        result = prime * result + ((getAccbooknumber() == null) ? 0 : getAccbooknumber().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getAreacode() == null) ? 0 : getAreacode().hashCode());
        result = prime * result + ((getBookdate() == null) ? 0 : getBookdate().hashCode());
        result = prime * result + ((getPrevbalance() == null) ? 0 : getPrevbalance().hashCode());
        result = prime * result + ((getHappendebit() == null) ? 0 : getHappendebit().hashCode());
        result = prime * result + ((getHappencredit() == null) ? 0 : getHappencredit().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }
}