package com.yjy.service.report;

import com.yjy.common.dao.BaseDao;
import com.yjy.common.dao.Pagination;
import com.yjy.common.utils.MybatisUtils;
import com.yjy.common.entity.report.FundsMonth;
import com.yjy.common.entity.report.FundsMonthExample;
import com.yjy.repository.mapper.report.FundsMonthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/7/4.
 */

@Service
public class FundsMonthService {

    private static Logger logger= LoggerFactory.getLogger(FundsMonthService.class);

    @Resource
    private FundsMonthMapper fundsMonthMapper;

    @Resource
    private BaseDao basedao;

    public Pagination<FundsMonth> findFundsMonthPagination(Pagination pagination,FundsMonth fundsMonth){
        FundsMonthExample fundsMonthExample=new FundsMonthExample();
        FundsMonthExample fundsMonthExample1 = MybatisUtils.convertObj2Example(fundsMonth, fundsMonthExample);
        return basedao.pagedQuery(fundsMonthMapper, fundsMonthExample1, pagination.getCurrentPage(),pagination.getPageSize());
    }

}
