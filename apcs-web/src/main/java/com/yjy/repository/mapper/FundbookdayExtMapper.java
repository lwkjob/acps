package com.yjy.repository.mapper;


import com.yjy.common.dao.Pagination;
import com.yjy.entity.Fundbook;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.Fundbookday;
import com.yjy.entity.UserBasicInfo;
import com.yjy.repository.dto.SumMonthByBookcode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundbookdayExtMapper {




    List<Fundbookday> selectByExample(@Param("fundbookday") Fundbookday fundbookday,
                                      @Param("tablename") String tablename);

    void deleteFundbookDay(@Param("fundBookCodes")List<Fundbookcode> fundBookCodes,
                           @Param("users")List<UserBasicInfo> users,
                           @Param("tablename")String tablename,
                           @Param("startTime")int startTime,
                           @Param("endTime")int endTime);

    void batchInsert(@Param("fundbookdays")List<Fundbookday> fundbookdays,
                     @Param("tablename")String tablename);


   List<SumMonthByBookcode> sumMonthByBookcode(@Param("tablename")String tablename,
                                               @Param("bookcode") String bookcode);


    /**
     * @param fundbookday
     * @param tableName  刷余额的时候取前一个月的最后一条数据
     * @param lastData
     * @return
     */
    List<Fundbookday> selectByExample2(@Param("fundbookday") Fundbookday fundbookday,
                                   @Param("tableName") String tableName,
                                   @Param("lastData") boolean lastData);
    //清空表
    void deleteAll(@Param("tableName")String tableName);


    List<Fundbookday> selectByExample(@Param("fundbookday") Fundbookday fundbookday,
                                      @Param("tablename") String tablename,
                                      @Param("pagination") Pagination pagination);



}