package com.yjy.repository.mapper;

import com.yjy.entity.Fundbook;
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
                                   @Param("startTime") int startTime,
                                   @Param("endTime") int endTime,
                                   @Param("lastData") boolean lastData);




    void batchUpdateByPrimaryKeySelective(@Param("fundbooks") List<Fundbook> fundbooks,
                                          @Param("tableName") String tableName);




}
