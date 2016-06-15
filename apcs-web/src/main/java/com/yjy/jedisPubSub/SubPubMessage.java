package com.yjy.jedisPubSub;

import com.yjy.entity.Fundbookcode;
import com.yjy.entity.UserBasicInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/6/15.
 */
public class SubPubMessage {

    private String startDate;
    private String endDate;
    private List<Fundbookcode> bookcodes;
    private List<UserBasicInfo> users;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Fundbookcode> getBookcodes() {
        return bookcodes;
    }

    public void setBookcodes(List<Fundbookcode> bookcodes) {
        this.bookcodes = bookcodes;
    }

    public List<UserBasicInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserBasicInfo> users) {
        this.users = users;
    }
}


