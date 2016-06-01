package com.yjy.repository.mapper;


import com.yjy.entity.Bookcode;
import com.yjy.entity.Fundbookday;
import com.yjy.entity.UserBasicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundbookdayExtMapper {

    List<Fundbookday> selectByMap(@Param("Fundbookday") Fundbookday fundbookday,
                                  @Param("tablename") String tablename);

    void deleteFundbookDay(@Param("bookcodes")List<Bookcode> bookcodes,
                           @Param("users")List<UserBasicInfo> users,
                           @Param("tablename")String tablename,
                           @Param("startTime")int startTime,
                           @Param("endTime")int endTime);

}