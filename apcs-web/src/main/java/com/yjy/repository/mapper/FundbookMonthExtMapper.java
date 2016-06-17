package com.yjy.repository.mapper;

import com.yjy.entity.Fundbookcode;
import com.yjy.entity.Fundbookmonth;
import com.yjy.entity.UserBasicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface FundbookMonthExtMapper {

    void deleteFundbookMonth(@Param("fundBookCodes")List<Fundbookcode> fundBookCodes,
                             @Param("users")List<UserBasicInfo> users,
                             @Param("tableName")String tableName);


    List<Fundbookmonth> selectByExample(@Param("fundbookmonth")Fundbookmonth fundbookmonthExample,
                                      @Param("tableName")String tableName);

    void batchInsert(@Param("fundbookmonths") List<Fundbookmonth> fundbookmonths,
                     @Param("tablename") String tablename);

    //清空表
     void deleteAll(@Param("tableName")String tableName);


   }
