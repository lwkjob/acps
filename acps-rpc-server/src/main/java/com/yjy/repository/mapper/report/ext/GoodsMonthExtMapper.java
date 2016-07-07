package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsMonth;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsMonthExtMapper {
    List<GoodsMonth> selectByExample(@Param("pagination") Pagination pagination,@Param("goodsMonth") GoodsMonth goodsMonth);


    int countByExample(@Param("goodsMonth") GoodsMonth goodsMonth);
}