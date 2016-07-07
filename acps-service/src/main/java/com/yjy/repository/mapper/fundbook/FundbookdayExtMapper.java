package com.yjy.repository.mapper.fundbook;


import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.Fundbookday;
import com.yjy.common.entity.fundbook.UserBasicInfo;
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


   List<SumMonthByBookcode> getSumMonthGroupBookcodeWhereUserid(@Param("tablename")String tablename,
                                               @Param("userid") Integer userid);

    //清空表
    void deleteAll(@Param("tableName")String tableName);


    List<Fundbookday> selectByExample(@Param("fundbookday") Fundbookday fundbookday,
                                      @Param("tablename") String tablename,
                                      @Param("pagination") Pagination pagination,
                                      @Param("userids") List<Integer> userids);



}