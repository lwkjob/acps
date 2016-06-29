package com.yjy.service.distributed;

/**
 * Created by liwenke on 16/6/28.
 */
public class ScheduleVo {

    private String bookdate; //yyyymmdd
    private long totalTask;//数据条数


    public ScheduleVo() {
    }


    public ScheduleVo(String bookdate, long totalTask) {
        this.bookdate = bookdate;
        this.totalTask = totalTask;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public long getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(long totalTask) {
        this.totalTask = totalTask;
    }
}
