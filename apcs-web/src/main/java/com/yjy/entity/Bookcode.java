package com.yjy.entity;

import java.io.Serializable;

public class Bookcode implements Serializable {
    private Integer id;

    private Integer platformrole;

    private Integer entryuserrole;

    private Integer accbooknumber;

    private String pname;

    private String ename;

    private String aname;

    private Integer idx;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename == null ? null : ename.trim();
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname == null ? null : aname.trim();
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
            && (this.getPlatformrole() == null ? other.getPlatformrole() == null : this.getPlatformrole().equals(other.getPlatformrole()))
            && (this.getEntryuserrole() == null ? other.getEntryuserrole() == null : this.getEntryuserrole().equals(other.getEntryuserrole()))
            && (this.getAccbooknumber() == null ? other.getAccbooknumber() == null : this.getAccbooknumber().equals(other.getAccbooknumber()))
            && (this.getPname() == null ? other.getPname() == null : this.getPname().equals(other.getPname()))
            && (this.getEname() == null ? other.getEname() == null : this.getEname().equals(other.getEname()))
            && (this.getAname() == null ? other.getAname() == null : this.getAname().equals(other.getAname()))
            && (this.getIdx() == null ? other.getIdx() == null : this.getIdx().equals(other.getIdx()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPlatformrole() == null) ? 0 : getPlatformrole().hashCode());
        result = prime * result + ((getEntryuserrole() == null) ? 0 : getEntryuserrole().hashCode());
        result = prime * result + ((getAccbooknumber() == null) ? 0 : getAccbooknumber().hashCode());
        result = prime * result + ((getPname() == null) ? 0 : getPname().hashCode());
        result = prime * result + ((getEname() == null) ? 0 : getEname().hashCode());
        result = prime * result + ((getAname() == null) ? 0 : getAname().hashCode());
        result = prime * result + ((getIdx() == null) ? 0 : getIdx().hashCode());
        return result;
    }
}