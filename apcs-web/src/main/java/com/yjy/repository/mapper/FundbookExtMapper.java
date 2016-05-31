package com.yjy.repository.mapper;

import com.yjy.entity.Fundbook;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by liwenke on 16/5/30.
 */
public interface FundbookExtMapper {

    /**
     *
     * @param fundbook
     * @param tableName 要查询的账本表如:fundbook201309
     * @param lastData 是否取最后一条数据
     * @return
     */
    List<Fundbook> selectByWhere(@Param("fundbook") Fundbook fundbook,
                                 @Param("tableName") String tableName,
                                 @Param("lastData") boolean lastData);


    void updateByPrimaryKeySelective(@Param("fundbook") Fundbook fundbooks,
                                     @Param("tableName") String tableName);


  void batchUpdateByPrimaryKeySelective(@Param("fundbooks") List<Fundbook> fundbooks,
                                     @Param("tableName") String tableName);


    Fundbook selectByPrimaryKey(@Param("tableName") String tableName,
                                @Param("bookid")Long bookid);


}
