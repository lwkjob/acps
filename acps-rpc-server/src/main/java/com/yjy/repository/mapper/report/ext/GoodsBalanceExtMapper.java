package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsBalance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsBalanceExtMapper {
    List<GoodsBalance> selectByExample(@Param("pagination") Pagination pagination,@Param("goodsBalance") GoodsBalance goodsBalance);

    List<GoodsBalance> sumByExample(@Param("goodsBalance") GoodsBalance goodsBalance);

    int countByExample(@Param("goodsBalance") GoodsBalance goodsBalance);
}