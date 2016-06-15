package com.yjy.service;

import com.yjy.common.redis.JedisTemplate;
import com.yjy.common.redis.RedisKey;
import com.yjy.common.utils.DateTools;
import com.yjy.entity.UserBasicInfo;
import com.yjy.repository.mapper.UserBasicExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 用户
 * Created by Administrator on 2016/6/2.
 */
@Service("userService")
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserBasicExtMapper userBasicExtMapper;


    @Resource
    private JedisTemplate jedisTemplate;




    //把每天的活跃用户缓存到redis里
    public int cacheUsers(String startDateStr, String endDateStr) {
        Date startDate = DateTools.parseDateFromString_yyyyMMdd(startDateStr, logger);
        Date endDate = DateTools.parseDateFromString_yyyyMMdd(endDateStr, logger);
        while (endDate.compareTo(startDate) != -1) {
            String currentDayStr=DateTools.formate_yyyyMMdd(startDate);
            Date createEndTime = DateTools.parseDateFormat_yyyyMMddHHmmss(currentDayStr + "23:59:59", logger);
            //  今天的活跃用户数
            List<UserBasicInfo> userOfMonthList = userBasicExtMapper.getUsers(0, 0, 0, 0, createEndTime.getTime() / 1000l);

            jedisTemplate.setObject(RedisKey.USERS_OF_DAY + currentDayStr, userOfMonthList);
            logger.info(RedisKey.USERS_OF_DAY + currentDayStr+"用户缓存");
            startDate = DateTools.getNextDayDate(startDate);
        }
        return 1;
    }

    public UserBasicInfo getUsersByUserId(int userid){
        UserBasicInfo userBasicInfo=new UserBasicInfo();
        userBasicInfo.setUserid(userid);
        List<UserBasicInfo> usersByExample = userBasicExtMapper.getUsersByExample(userBasicInfo);
        return usersByExample.size()>0?usersByExample.get(0):null;
    }
}
