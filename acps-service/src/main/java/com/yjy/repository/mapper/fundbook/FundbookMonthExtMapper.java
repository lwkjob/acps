package com.yjy.repository.mapper.fundbook;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.fundbook.Fundbookcode;
import com.yjy.common.entity.fundbook.Fundbookmonth;
import com.yjy.common.entity.fundbook.UserBasicInfo;
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



    List<Fundbookmonth> selectByExamplePage(@Param("fundbookmonth")Fundbookmonth fundbookmonthExample,
                                            @Param("tableName")String tableName,
                                            @Param("pagination") Pagination pagination,
                                            @Param("userids") List<Integer> userids);

    List<Fundbookmonth> loadMonthRrport( @Param("tableName")String tableName,
                                            @Param("pagination") Pagination pagination,
                                            @Param("userids") List<Integer> userids);

    void batchInsert(@Param("fundbookmonths") List<Fundbookmonth> fundbookmonths,
                     @Param("tablename") String tablename);

    //清空表
     void deleteAll(@Param("tableName")String tableName);


    List<Fundbookmonth> findMonthTemp(@Param("fundbookmonth")Fundbookmonth fundbookmonthExample,
                                        @Param("tableName")String tableName);

    //创建月结临时表
    void createFundmonthtemp(@Param("fundmonthtemptableName")String fundmonthtemptableName,
                             @Param("fundbooktableName")String fundbooktableName);
   }
