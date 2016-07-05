package com.yjy.entity.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsRefundPost implements Serializable {
    private Long id;

    private Byte invoicetype;

    private Integer parentid;

    private String invoicenumber;

    private Integer buyerid;

    private String buyerCompanyname;

    private String buyerLinkman;

    private Integer salerid;

    private Long happentime;

    private BigDecimal totalpostprice;

    private BigDecimal accreturnpostprice;

    private BigDecimal redpacketreturnpostprice;

    private BigDecimal changepostprice;

    private BigDecimal balance;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(Byte invoicetype) {
        this.invoicetype = invoicetype;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getInvoicenumber() {
        return invoicenumber;
    }

    public void setInvoicenumber(String invoicenumber) {
        this.invoicenumber = invoicenumber == null ? null : invoicenumber.trim();
    }

    public Integer getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(Integer buyerid) {
        this.buyerid = buyerid;
    }

    public String getBuyerCompanyname() {
        return buyerCompanyname;
    }

    public void setBuyerCompanyname(String buyerCompanyname) {
        this.buyerCompanyname = buyerCompanyname == null ? null : buyerCompanyname.trim();
    }

    public String getBuyerLinkman() {
        return buyerLinkman;
    }

    public void setBuyerLinkman(String buyerLinkman) {
        this.buyerLinkman = buyerLinkman == null ? null : buyerLinkman.trim();
    }

    public Integer getSalerid() {
        return salerid;
    }

    public void setSalerid(Integer salerid) {
        this.salerid = salerid;
    }

    public Long getHappentime() {
        return happentime;
    }

    public void setHappentime(Long happentime) {
        this.happentime = happentime;
    }

    public BigDecimal getTotalpostprice() {
        return totalpostprice;
    }

    public void setTotalpostprice(BigDecimal totalpostprice) {
        this.totalpostprice = totalpostprice;
    }

    public BigDecimal getAccreturnpostprice() {
        return accreturnpostprice;
    }

    public void setAccreturnpostprice(BigDecimal accreturnpostprice) {
        this.accreturnpostprice = accreturnpostprice;
    }

    public BigDecimal getRedpacketreturnpostprice() {
        return redpacketreturnpostprice;
    }

    public void setRedpacketreturnpostprice(BigDecimal redpacketreturnpostprice) {
        this.redpacketreturnpostprice = redpacketreturnpostprice;
    }

    public BigDecimal getChangepostprice() {
        return changepostprice;
    }

    public void setChangepostprice(BigDecimal changepostprice) {
        this.changepostprice = changepostprice;
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
        GoodsRefundPost other = (GoodsRefundPost) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getInvoicetype() == null ? other.getInvoicetype() == null : this.getInvoicetype().equals(other.getInvoicetype()))
            && (this.getParentid() == null ? other.getParentid() == null : this.getParentid().equals(other.getParentid()))
            && (this.getInvoicenumber() == null ? other.getInvoicenumber() == null : this.getInvoicenumber().equals(other.getInvoicenumber()))
            && (this.getBuyerid() == null ? other.getBuyerid() == null : this.getBuyerid().equals(other.getBuyerid()))
            && (this.getBuyerCompanyname() == null ? other.getBuyerCompanyname() == null : this.getBuyerCompanyname().equals(other.getBuyerCompanyname()))
            && (this.getBuyerLinkman() == null ? other.getBuyerLinkman() == null : this.getBuyerLinkman().equals(other.getBuyerLinkman()))
            && (this.getSalerid() == null ? other.getSalerid() == null : this.getSalerid().equals(other.getSalerid()))
            && (this.getHappentime() == null ? other.getHappentime() == null : this.getHappentime().equals(other.getHappentime()))
            && (this.getTotalpostprice() == null ? other.getTotalpostprice() == null : this.getTotalpostprice().equals(other.getTotalpostprice()))
            && (this.getAccreturnpostprice() == null ? other.getAccreturnpostprice() == null : this.getAccreturnpostprice().equals(other.getAccreturnpostprice()))
            && (this.getRedpacketreturnpostprice() == null ? other.getRedpacketreturnpostprice() == null : this.getRedpacketreturnpostprice().equals(other.getRedpacketreturnpostprice()))
            && (this.getChangepostprice() == null ? other.getChangepostprice() == null : this.getChangepostprice().equals(other.getChangepostprice()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getInvoicetype() == null) ? 0 : getInvoicetype().hashCode());
        result = prime * result + ((getParentid() == null) ? 0 : getParentid().hashCode());
        result = prime * result + ((getInvoicenumber() == null) ? 0 : getInvoicenumber().hashCode());
        result = prime * result + ((getBuyerid() == null) ? 0 : getBuyerid().hashCode());
        result = prime * result + ((getBuyerCompanyname() == null) ? 0 : getBuyerCompanyname().hashCode());
        result = prime * result + ((getBuyerLinkman() == null) ? 0 : getBuyerLinkman().hashCode());
        result = prime * result + ((getSalerid() == null) ? 0 : getSalerid().hashCode());
        result = prime * result + ((getHappentime() == null) ? 0 : getHappentime().hashCode());
        result = prime * result + ((getTotalpostprice() == null) ? 0 : getTotalpostprice().hashCode());
        result = prime * result + ((getAccreturnpostprice() == null) ? 0 : getAccreturnpostprice().hashCode());
        result = prime * result + ((getRedpacketreturnpostprice() == null) ? 0 : getRedpacketreturnpostprice().hashCode());
        result = prime * result + ((getChangepostprice() == null) ? 0 : getChangepostprice().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        return result;
    }
}