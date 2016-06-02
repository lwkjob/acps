package com.yjy.service;

import com.yjy.entity.UserBasicInfo;
import com.yjy.repository.mapper.UserBasicExtMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户
 * Created by Administrator on 2016/6/2.
 */
@Service("userService")
public class UserService {

    @Resource
    private UserBasicExtMapper userBasicExtMapper;


    public List<UserBasicInfo> getUsers(int startIndex,
                                        int endIndex,
                                        int typeid,
                                        int startTime,
                                        int endTime) {

        return userBasicExtMapper.getUsers(startIndex, endIndex, typeid, startTime, endTime);

    }
}
