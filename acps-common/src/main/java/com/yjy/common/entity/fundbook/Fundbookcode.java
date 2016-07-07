package com.yjy.common.entity.fundbook;

import java.io.Serializable;

public class Fundbookcode implements Serializable {
    private Integer id;

    private Integer fundtype;

    private Integer bookcodeone;

    private Integer rolecode;

    private Integer bookcodetwo;

    private Integer bookcodethree;

    private String bookcode;

    private String bookcodedesc;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFundtype() {
        return fundtype;
    }

    public void setFundtype(Integer fundtype) {
        this.fundtype = fundtype;
    }

    public Integer getBookcodeone() {
        return bookcodeone;
    }

    public void setBookcodeone(Integer bookcodeone) {
        this.bookcodeone = bookcodeone;
    }

    public Integer getRolecode() {
        return rolecode;
    }

    public void setRolecode(Integer rolecode) {
        this.rolecode = rolecode;
    }

    public Integer getBookcodetwo() {
        return bookcodetwo;
    }

    public void setBookcodetwo(Integer bookcodetwo) {
        this.bookcodetwo = bookcodetwo;
    }

    public Integer getBookcodethree() {
        return bookcodethree;
    }

    public void setBookcodethree(Integer bookcodethree) {
        this.bookcodethree = bookcodethree;
    }

    public String getBookcode() {
        return bookcode;
    }

    public void setBookcode(String bookcode) {
        this.bookcode = bookcode == null ? null : bookcode.trim();
    }

    public String getBookcodedesc() {
        return bookcodedesc;
    }

    public void setBookcodedesc(String bookcodedesc) {
        this.bookcodedesc = bookcodedesc == null ? null : bookcodedesc.trim();
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
        Fundbookcode other = (Fundbookcode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFundtype() == null ? other.getFundtype() == null : this.getFundtype().equals(other.getFundtype()))
            && (this.getBookcodeone() == null ? other.getBookcodeone() == null : this.getBookcodeone().equals(other.getBookcodeone()))
            && (this.getRolecode() == null ? other.getRolecode() == null : this.getRolecode().equals(other.getRolecode()))
            && (this.getBookcodetwo() == null ? other.getBookcodetwo() == null : this.getBookcodetwo().equals(other.getBookcodetwo()))
            && (this.getBookcodethree() == null ? other.getBookcodethree() == null : this.getBookcodethree().equals(other.getBookcodethree()))
            && (this.getBookcode() == null ? other.getBookcode() == null : this.getBookcode().equals(other.getBookcode()))
            && (this.getBookcodedesc() == null ? other.getBookcodedesc() == null : this.getBookcodedesc().equals(other.getBookcodedesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFundtype() == null) ? 0 : getFundtype().hashCode());
        result = prime * result + ((getBookcodeone() == null) ? 0 : getBookcodeone().hashCode());
        result = prime * result + ((getRolecode() == null) ? 0 : getRolecode().hashCode());
        result = prime * result + ((getBookcodetwo() == null) ? 0 : getBookcodetwo().hashCode());
        result = prime * result + ((getBookcodethree() == null) ? 0 : getBookcodethree().hashCode());
        result = prime * result + ((getBookcode() == null) ? 0 : getBookcode().hashCode());
        result = prime * result + ((getBookcodedesc() == null) ? 0 : getBookcodedesc().hashCode());
        return result;
    }
}