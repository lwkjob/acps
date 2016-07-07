package com.yjy.repository.mapper.report.ext;


import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsOut;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public interface GoodsOutExtMapper {

    List<GoodsOut> selectByExample(@Param("pagination") Pagination pagination,@Param("goodsOut") GoodsOut goodsOut);

    List<GoodsOut> sumByExample(@Param("goodsOut") GoodsOut goodsOut);

    int countByExample(@Param("goodsOut") GoodsOut goodsOut);
}
