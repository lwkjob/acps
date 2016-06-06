package com.yjy.repository.mapper;

import com.yjy.common.dao.Pagination;
import com.yjy.entity.Fundbook;
import com.yjy.entity.Fundbookcode;
import com.yjy.entity.UserBasicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liwenke on 16/5/30.
 */
public interface FundbookExtMapper {

    /**
     * @param fundbook
     * @param tableName 要查询的账本表如:fundbook201309
     * @param lastData  是否只取最后一条数据
     * @return
     */
    List<Fundbook> selectByExample(@Param("fundbook") Fundbook fundbook,
                                   @Param("tableName") String tableName,
                                   @Param("startTime") long startTime,
                                   @Param("endTime") long endTime,
                                   @Param("lastData") boolean lastData);



    /**
     * @param tableName 所有用户当天发生的当前正本数据
     * @param lastData  上一天的最后一条数据
     * @return
     */
    List<Fundbook> selectByOneDay(
                                   @Param("tableName") String tableName,
                                   @Param("usersid")int userid,
                                   @Param("bookcode") String bookcode,
                                   @Param("startTime") long startTime,
                                   @Param("endTime") long endTime,
                                   @Param("lastData") boolean lastData);



    void batchUpdateByPrimaryKeySelective(@Param("fundbooks") List<Fundbook> fundbooks,
                                          @Param("tableName") String tableName);


    //查询当期发生的多有用户id
  List<Integer>  selectUserids( @Param("tableName") String tableName);

    //当期发生数据的账本
  List<String>  selectBookcodes( @Param("tableName") String tableName,@Param("userid")int userid);


    //分页查询账本数据
    List<Fundbook> selectPageListByExample(@Param("fundbook") Fundbook fundbook,
                                           @Param("tableName") String tableName,
                                           @Param("startTime") long startTime,
                                           @Param("endTime") long endTime ,
                                           @Param("pagination") Pagination pagination
                                           );


}
