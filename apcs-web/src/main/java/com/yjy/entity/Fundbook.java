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

    private BigDecimal debit;

    private BigDecimal credit;

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
            && (this.getDebit() == null ? other.getDebit() == null : this.getDebit().equals(other.getDebit()))
            && (this.getCredit() == null ? other.getCredit() == null : this.getCredit().equals(other.getCredit()))
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
        result = prime * result + ((getDebit() == null) ? 0 : getDebit().hashCode());
        result = prime * result + ((getCredit() == null) ? 0 : getCredit().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }
}