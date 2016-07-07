package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsSettle;
import com.yjy.repository.mapper.report.ext.FundsSettleExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */

@Service
public class FundsSettleService {

    private static Logger logger = LoggerFactory.getLogger(FundsSettleService.class);

    @Resource
    private FundsSettleExtMapper fundsSettleExtMapper;


    public Pagination<FundsSettle> findFundsSettlePagination(Pagination pagination, FundsSettle fundsSettle) {
        List<FundsSettle> fundsSettles = fundsSettleExtMapper.selectByExample(pagination, fundsSettle);
        pagination.setData(fundsSettles);
        return pagination;
    }

    public  List<FundsSettle> sumFundsSettlePagination(  FundsSettle fundsSettle) {
        return fundsSettleExtMapper.sumByExample(fundsSettle);
    }



}
