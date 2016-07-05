package com.yjy.repository.mapper.report.ext;


import com.yjy.common.dao.Pagination;
import com.yjy.entity.report.FundsMonth;
import com.yjy.entity.report.GoodsOut;

/**
 * Created by Administrator on 2016/7/4.
 */
public interface FundsMonthExtMapper {

    Pagination<GoodsOut>  findPaginationFundsMonthList(Pagination pagination,FundsMonth fundsMonthExample);
}
