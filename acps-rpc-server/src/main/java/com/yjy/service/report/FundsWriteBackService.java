package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsWriteBack;
import com.yjy.repository.mapper.report.ext.FundsWriteBackExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */

@Service
public class FundsWriteBackService {

    private static Logger logger = LoggerFactory.getLogger(FundsWriteBackService.class);

    @Resource
    private FundsWriteBackExtMapper fundsWriteBackExtMapper;


    public Pagination<FundsWriteBack> findFundsWriteBackPagination(Pagination pagination, FundsWriteBack fundsWriteBack) {
        List<FundsWriteBack> fundsWriteBacks = fundsWriteBackExtMapper.selectByExample(pagination, fundsWriteBack);
        pagination.setData(fundsWriteBacks);
        return pagination;
    }

    public List<FundsWriteBack>  sumFundsWriteBackPagination(FundsWriteBack fundsWriteBack) {
        return fundsWriteBackExtMapper.sumByExample(fundsWriteBack);

    }


}
