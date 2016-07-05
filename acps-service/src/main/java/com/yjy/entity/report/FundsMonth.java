package com.yjy.entity.report;

import java.io.Serializable;
import java.math.BigDecimal;

public class FundsMonth implements Serializable {
    private Long id;

    private Integer curyear;

    private Integer curmonth;

    private Integer salerid;

    private BigDecimal prevbalance;

    private BigDecimal curreceiptprice;

    private BigDecimal curredprice;

    private BigDecimal curmarkupprice;

    private BigDecimal cursettleprice;

    private BigDecimal curbalance;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCuryear() {
        return curyear;
    }

    public void setCuryear(Integer curyear) {
        this.curyear = curyear;
    }

    public Integer getCurmonth() {
        return curmonth;
    }

    public void setCurmonth(Integer curmonth) {
        this.curmonth = curmonth;
    }

    public Integer getSalerid() {
        return salerid;
    }

    public void setSalerid(Integer salerid) {
        this.salerid = salerid;
    }

    public BigDecimal getPrevbalance() {
        return prevbalance;
    }

    public void setPrevbalance(BigDecimal prevbalance) {
        this.prevbalance = prevbalance;
    }

    public BigDecimal getCurreceiptprice() {
        return curreceiptprice;
    }

    public void setCurreceiptprice(BigDecimal curreceiptprice) {
        this.curreceiptprice = curreceiptprice;
    }

    public BigDecimal getCurredprice() {
        return curredprice;
    }

    public void setCurredprice(BigDecimal curredprice) {
        this.curredprice = curredprice;
    }

    public BigDecimal getCurmarkupprice() {
        return curmarkupprice;
    }

    public void setCurmarkupprice(BigDecimal curmarkupprice) {
        this.curmarkupprice = curmarkupprice;
    }

    public BigDecimal getCursettleprice() {
        return cursettleprice;
    }

    public void setCursettleprice(BigDecimal cursettleprice) {
        this.cursettleprice = cursettleprice;
    }

    public BigDecimal getCurbalance() {
        return curbalance;
    }

    public void setCurbalance(BigDecimal curbalance) {
        this.curbalance = curbalance;
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
        FundsMonth other = (FundsMonth) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCuryear() == null ? other.getCuryear() == null : this.getCuryear().equals(other.getCuryear()))
            && (this.getCurmonth() == null ? other.getCurmonth() == null : this.getCurmonth().equals(other.getCurmonth()))
            && (this.getSalerid() == null ? other.getSalerid() == null : this.getSalerid().equals(other.getSalerid()))
            && (this.getPrevbalance() == null ? other.getPrevbalance() == null : this.getPrevbalance().equals(other.getPrevbalance()))
            && (this.getCurreceiptprice() == null ? other.getCurreceiptprice() == null : this.getCurreceiptprice().equals(other.getCurreceiptprice()))
            && (this.getCurredprice() == null ? other.getCurredprice() == null : this.getCurredprice().equals(other.getCurredprice()))
            && (this.getCurmarkupprice() == null ? other.getCurmarkupprice() == null : this.getCurmarkupprice().equals(other.getCurmarkupprice()))
            && (this.getCursettleprice() == null ? other.getCursettleprice() == null : this.getCursettleprice().equals(other.getCursettleprice()))
            && (this.getCurbalance() == null ? other.getCurbalance() == null : this.getCurbalance().equals(other.getCurbalance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCuryear() == null) ? 0 : getCuryear().hashCode());
        result = prime * result + ((getCurmonth() == null) ? 0 : getCurmonth().hashCode());
        result = prime * result + ((getSalerid() == null) ? 0 : getSalerid().hashCode());
        result = prime * result + ((getPrevbalance() == null) ? 0 : getPrevbalance().hashCode());
        result = prime * result + ((getCurreceiptprice() == null) ? 0 : getCurreceiptprice().hashCode());
        result = prime * result + ((getCurredprice() == null) ? 0 : getCurredprice().hashCode());
        result = prime * result + ((getCurmarkupprice() == null) ? 0 : getCurmarkupprice().hashCode());
        result = prime * result + ((getCursettleprice() == null) ? 0 : getCursettleprice().hashCode());
        result = prime * result + ((getCurbalance() == null) ? 0 : getCurbalance().hashCode());
        return result;
    }
}