package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsBalance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundsBalanceExtMapper {
    List<FundsBalance> selectByExample(@Param("pagination") Pagination pagination,@Param("fundsBanlace") FundsBalance fundsBanlace);

    List<FundsBalance> sumByExample(@Param("fundsBanlace") FundsBalance fundsBanlace);

    int countByExample(@Param("fundsBanlace") FundsBalance fundsBanlace);
}