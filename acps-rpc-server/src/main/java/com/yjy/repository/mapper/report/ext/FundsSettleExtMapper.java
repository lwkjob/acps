package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsSettle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundsSettleExtMapper {

    List<FundsSettle> selectByExample(@Param("pagination") Pagination pagination, @Param("fundsSettle") FundsSettle fundsSettle);
    List<FundsSettle> sumByExample( @Param("fundsSettle") FundsSettle fundsSettle);

    int countByExample(@Param("fundsSettle") FundsSettle fundsSettle);
}