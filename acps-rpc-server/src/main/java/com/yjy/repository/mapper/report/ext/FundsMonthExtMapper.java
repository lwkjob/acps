package com.yjy.repository.mapper.report.ext;


import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsMonth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
public interface FundsMonthExtMapper {

    List<FundsMonth> selectByExample(@Param("pagination") Pagination pagination,@Param("fundsMonth") FundsMonth fundsMonth);

    int countByExample(@Param("fundsMonth") FundsMonth fundsMonth);
}
