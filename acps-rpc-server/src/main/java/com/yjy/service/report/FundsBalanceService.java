package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsBalance;
import com.yjy.repository.mapper.report.ext.FundsBalanceExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */

@Service
public class FundsBalanceService {

    private static Logger logger= LoggerFactory.getLogger(FundsBalanceService.class);

    @Resource
    private FundsBalanceExtMapper fundsBalanceExtMapper;



    public Pagination<FundsBalance> findFundsBalancePagination(Pagination pagination,FundsBalance fundsBalance){
        List<FundsBalance> fundsBalances = fundsBalanceExtMapper.selectByExample(pagination, fundsBalance);
        pagination.setData(fundsBalances);
        return pagination;
    }

    public  List<FundsBalance>  sumFundsBalancePagination(FundsBalance fundsBalance){
        return fundsBalanceExtMapper.sumByExample( fundsBalance);
    }

}
