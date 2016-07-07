package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsMarkup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundsMarkupExtMapper {
    List<FundsMarkup> selectByExample(@Param("pagination") Pagination pagination,@Param("fundsMarkup") FundsMarkup fundsMarkup);

    List<FundsMarkup> sumByExample(@Param("fundsMarkup") FundsMarkup fundsMarkup);

    int countByExample(@Param("fundsMarkup") FundsMarkup fundsMarkup);
}