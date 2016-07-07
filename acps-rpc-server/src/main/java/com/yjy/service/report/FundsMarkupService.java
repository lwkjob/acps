package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsMarkup;
import com.yjy.repository.mapper.report.ext.FundsMarkupExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
@Service
public class FundsMarkupService {


    private static Logger logger= LoggerFactory.getLogger(FundsMarkupService.class);

    @Resource
    private FundsMarkupExtMapper fundsMarkupExtMapper;


    public Pagination<FundsMarkup> findFundsMarkupPagination(Pagination pagination,FundsMarkup fundsMarkup) {
        List<FundsMarkup> fundsMarkups = fundsMarkupExtMapper.selectByExample(pagination, fundsMarkup);
        pagination.setData(fundsMarkups);
        return pagination;
    }


    public List<FundsMarkup> sumFundsMarkupPagination(FundsMarkup fundsMarkup) {
        return fundsMarkupExtMapper.sumByExample( fundsMarkup);
    }
}
