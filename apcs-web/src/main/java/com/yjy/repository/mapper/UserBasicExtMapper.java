package com.yjy.repository.mapper;

import com.yjy.entity.UserBasicInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by liwenke on 16/5/31.
 */
public interface UserBasicExtMapper {

    List<UserBasicInfo> getUsers(Map map);
}
