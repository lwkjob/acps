package com.yjy.repository.mapper;


import com.yjy.entity.Fundbookcode;
import com.yjy.entity.Fundbookday;
import com.yjy.entity.UserBasicInfo;
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
}