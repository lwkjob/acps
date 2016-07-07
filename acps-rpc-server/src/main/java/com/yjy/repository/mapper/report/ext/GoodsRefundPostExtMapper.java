package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsRefundPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsRefundPostExtMapper {
    List<GoodsRefundPost> selectByExample(@Param("pagination") Pagination pagination,@Param("goodsRefundPost") GoodsRefundPost goodsRefundPost);

    List<GoodsRefundPost> sumByExample(@Param("goodsRefundPost") GoodsRefundPost goodsRefundPost);

    int countByExample(@Param("goodsRefundPost") GoodsRefundPost goodsRefundPost);
}