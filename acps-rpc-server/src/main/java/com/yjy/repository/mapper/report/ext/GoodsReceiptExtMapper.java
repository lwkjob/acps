package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsReceipt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsReceiptExtMapper {
    List<GoodsReceipt> selectByExample(@Param("pagination") Pagination pagination,@Param("goodsReceipt") GoodsReceipt goodsReceipt);

    List<GoodsReceipt> sumByExample(@Param("goodsReceipt") GoodsReceipt goodsReceipt);

    int countByExample(@Param("goodsReceipt") GoodsReceipt goodsReceipt);
}