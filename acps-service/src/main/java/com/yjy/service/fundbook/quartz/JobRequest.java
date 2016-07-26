package com.yjy.service.fundbook.quartz;

import java.util.List;

/**
 * Created by Administrator on 2016/7/25.
 */
public class JobRequest {

    private String jobDate; //20160301
    private String loadPredayBalance;   //是否加载前一天余额
    private List<Integer> userids;  //用户列表

    public String getJobDate() {
        return jobDate;
}

    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }

    public String getLoadPredayBalance() {
        return loadPredayBalance;
    }

    public void setLoadPredayBalance(String loadPredayBalance) {
        this.loadPredayBalance = loadPredayBalance;
    }

    public List<Integer> getUserids() {
        return userids;
    }

    public void setUserids(List<Integer> userids) {
        this.userids = userids;
    }
}
