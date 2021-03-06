package com.yjy.repository.mapper;

import com.yjy.entity.UserBasicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liwenke on 16/5/31.
 */
public interface UserBasicExtMapper {


    /**
     *
     * @param startIndex limit 的开始数
     * @param endIndex
     * @param typeid 用户类型 3平台 2卖家 1买家
     * @param startTime 注册用户的开始时间
     * @param endTime
     * @return
     */
    List<UserBasicInfo> getUsers(@Param("startIndex") int startIndex,
                                 @Param("endIndex") int endIndex,
                                 @Param("typeid") int typeid,
                                 @Param("startTime") long startTime,
                                 @Param("endTime") long endTime);
}
