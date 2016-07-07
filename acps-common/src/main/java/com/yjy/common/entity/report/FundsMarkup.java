package com.yjy.common.entity.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundsMarkup implements Serializable {
    private Long id;

    private Integer parentid;

    private String invoicenumber;

    private Integer buyerid;

    private String buyerCompanyname;

    private String buyerLinkman;

    private Integer salerid;

    private Long happentime;

    private BigDecimal totalgoodsprice;

    private BigDecimal balance;

    private BigDecimal accgoodsprice;

    private BigDecimal salerspecialprice;

    private BigDecimal saleragioprice;

    private BigDecimal salerfullamount;

    private Byte invoicetype;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getTotalgoodsprice() {
        return totalgoodsprice;
    }

    public void setTotalgoodsprice(BigDecimal totalgoodsprice) {
        this.totalgoodsprice = totalgoodsprice;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getAccgoodsprice() {
        return accgoodsprice;
    }

    public void setAccgoodsprice(BigDecimal accgoodsprice) {
        this.accgoodsprice = accgoodsprice;
    }

    public BigDecimal getSalerspecialprice() {
        return salerspecialprice;
    }

    public void setSalerspecialprice(BigDecimal salerspecialprice) {
        this.salerspecialprice = salerspecialprice;
    }

    public BigDecimal getSaleragioprice() {
        return saleragioprice;
    }

    public void setSaleragioprice(BigDecimal saleragioprice) {
        this.saleragioprice = saleragioprice;
    }

    public BigDecimal getSalerfullamount() {
        return salerfullamount;
    }

    public void setSalerfullamount(BigDecimal salerfullamount) {
        this.salerfullamount = salerfullamount;
    }

    public Byte getInvoicetype() {
        return invoicetype;
    }

    public void setInvoicetype(Byte invoicetype) {
        this.invoicetype = invoicetype;
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
        FundsMarkup other = (FundsMarkup) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getParentid() == null ? other.getParentid() == null : this.getParentid().equals(other.getParentid()))
            && (this.getInvoicenumber() == null ? other.getInvoicenumber() == null : this.getInvoicenumber().equals(other.getInvoicenumber()))
            && (this.getBuyerid() == null ? other.getBuyerid() == null : this.getBuyerid().equals(other.getBuyerid()))
            && (this.getBuyerCompanyname() == null ? other.getBuyerCompanyname() == null : this.getBuyerCompanyname().equals(other.getBuyerCompanyname()))
            && (this.getBuyerLinkman() == null ? other.getBuyerLinkman() == null : this.getBuyerLinkman().equals(other.getBuyerLinkman()))
            && (this.getSalerid() == null ? other.getSalerid() == null : this.getSalerid().equals(other.getSalerid()))
            && (this.getHappentime() == null ? other.getHappentime() == null : this.getHappentime().equals(other.getHappentime()))
            && (this.getTotalgoodsprice() == null ? other.getTotalgoodsprice() == null : this.getTotalgoodsprice().equals(other.getTotalgoodsprice()))
            && (this.getBalance() == null ? other.getBalance() == null : this.getBalance().equals(other.getBalance()))
            && (this.getAccgoodsprice() == null ? other.getAccgoodsprice() == null : this.getAccgoodsprice().equals(other.getAccgoodsprice()))
            && (this.getSalerspecialprice() == null ? other.getSalerspecialprice() == null : this.getSalerspecialprice().equals(other.getSalerspecialprice()))
            && (this.getSaleragioprice() == null ? other.getSaleragioprice() == null : this.getSaleragioprice().equals(other.getSaleragioprice()))
            && (this.getSalerfullamount() == null ? other.getSalerfullamount() == null : this.getSalerfullamount().equals(other.getSalerfullamount()))
            && (this.getInvoicetype() == null ? other.getInvoicetype() == null : this.getInvoicetype().equals(other.getInvoicetype()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getParentid() == null) ? 0 : getParentid().hashCode());
        result = prime * result + ((getInvoicenumber() == null) ? 0 : getInvoicenumber().hashCode());
        result = prime * result + ((getBuyerid() == null) ? 0 : getBuyerid().hashCode());
        result = prime * result + ((getBuyerCompanyname() == null) ? 0 : getBuyerCompanyname().hashCode());
        result = prime * result + ((getBuyerLinkman() == null) ? 0 : getBuyerLinkman().hashCode());
        result = prime * result + ((getSalerid() == null) ? 0 : getSalerid().hashCode());
        result = prime * result + ((getHappentime() == null) ? 0 : getHappentime().hashCode());
        result = prime * result + ((getTotalgoodsprice() == null) ? 0 : getTotalgoodsprice().hashCode());
        result = prime * result + ((getBalance() == null) ? 0 : getBalance().hashCode());
        result = prime * result + ((getAccgoodsprice() == null) ? 0 : getAccgoodsprice().hashCode());
        result = prime * result + ((getSalerspecialprice() == null) ? 0 : getSalerspecialprice().hashCode());
        result = prime * result + ((getSaleragioprice() == null) ? 0 : getSaleragioprice().hashCode());
        result = prime * result + ((getSalerfullamount() == null) ? 0 : getSalerfullamount().hashCode());
        result = prime * result + ((getInvoicetype() == null) ? 0 : getInvoicetype().hashCode());
        return result;
    }
}