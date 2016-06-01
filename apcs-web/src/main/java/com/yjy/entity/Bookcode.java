package com.yjy.entity;

import java.io.Serializable;

public class Bookcode implements Serializable {
    private Integer id;

    private Integer fundtype;

    private Integer bookcodeone;

    private Integer bookcodetwo;

    private Integer bookcodethree;

    private String bookcodeonedesc;

    private String bookcodetwodesc;

    private String bookcodethreedesc;

    private Integer idx;

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

    public String getBookcodeonedesc() {
        return bookcodeonedesc;
    }

    public void setBookcodeonedesc(String bookcodeonedesc) {
        this.bookcodeonedesc = bookcodeonedesc == null ? null : bookcodeonedesc.trim();
    }

    public String getBookcodetwodesc() {
        return bookcodetwodesc;
    }

    public void setBookcodetwodesc(String bookcodetwodesc) {
        this.bookcodetwodesc = bookcodetwodesc == null ? null : bookcodetwodesc.trim();
    }

    public String getBookcodethreedesc() {
        return bookcodethreedesc;
    }

    public void setBookcodethreedesc(String bookcodethreedesc) {
        this.bookcodethreedesc = bookcodethreedesc == null ? null : bookcodethreedesc.trim();
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
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
        Bookcode other = (Bookcode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getFundtype() == null ? other.getFundtype() == null : this.getFundtype().equals(other.getFundtype()))
            && (this.getBookcodeone() == null ? other.getBookcodeone() == null : this.getBookcodeone().equals(other.getBookcodeone()))
            && (this.getBookcodetwo() == null ? other.getBookcodetwo() == null : this.getBookcodetwo().equals(other.getBookcodetwo()))
            && (this.getBookcodethree() == null ? other.getBookcodethree() == null : this.getBookcodethree().equals(other.getBookcodethree()))
            && (this.getBookcodeonedesc() == null ? other.getBookcodeonedesc() == null : this.getBookcodeonedesc().equals(other.getBookcodeonedesc()))
            && (this.getBookcodetwodesc() == null ? other.getBookcodetwodesc() == null : this.getBookcodetwodesc().equals(other.getBookcodetwodesc()))
            && (this.getBookcodethreedesc() == null ? other.getBookcodethreedesc() == null : this.getBookcodethreedesc().equals(other.getBookcodethreedesc()))
            && (this.getIdx() == null ? other.getIdx() == null : this.getIdx().equals(other.getIdx()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getFundtype() == null) ? 0 : getFundtype().hashCode());
        result = prime * result + ((getBookcodeone() == null) ? 0 : getBookcodeone().hashCode());
        result = prime * result + ((getBookcodetwo() == null) ? 0 : getBookcodetwo().hashCode());
        result = prime * result + ((getBookcodethree() == null) ? 0 : getBookcodethree().hashCode());
        result = prime * result + ((getBookcodeonedesc() == null) ? 0 : getBookcodeonedesc().hashCode());
        result = prime * result + ((getBookcodetwodesc() == null) ? 0 : getBookcodetwodesc().hashCode());
        result = prime * result + ((getBookcodethreedesc() == null) ? 0 : getBookcodethreedesc().hashCode());
        result = prime * result + ((getIdx() == null) ? 0 : getIdx().hashCode());
        return result;
    }
}