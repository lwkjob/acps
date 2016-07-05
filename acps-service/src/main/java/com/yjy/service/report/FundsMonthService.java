package com.yjy.service.report;

import com.yjy.common.dao.BaseDao;
import com.yjy.common.dao.Pagination;
import com.yjy.entity.report.FundsMonth;
import com.yjy.entity.report.FundsMonthExample;
import com.yjy.repository.mapper.report.ext.FundsMonthExtMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/7/4.
 */

@Service
public class FundsMonthService {

    @Resource
    private FundsMonthExtMapper fundsMonthExtMapper;

    @Resource
    private BaseDao baseDao;

    public Pagination<FundsMonth> findPaginationMonthList(Pagination pagination,FundsMonthExample example){
      return   baseDao.pagedQuery(fundsMonthExtMapper,example,pagination.getCurrentPage());
    };


}
