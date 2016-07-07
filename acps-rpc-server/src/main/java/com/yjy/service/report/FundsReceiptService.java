package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsReceipt;
import com.yjy.repository.mapper.report.ext.FundsReceiptExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */

@Service
public class FundsReceiptService {

    private static Logger logger= LoggerFactory.getLogger(FundsReceiptService.class);

    @Resource
    private FundsReceiptExtMapper fundsReceiptExtMapper;



    public Pagination<FundsReceipt> findFundsReceiptPagination(Pagination pagination,FundsReceipt fundsReceipt){
        List<FundsReceipt> fundsReceipts = fundsReceiptExtMapper.selectByExample(pagination, fundsReceipt);
        pagination.setData(fundsReceipts);
        return pagination;
    }

    public  List<FundsReceipt> sumFundsReceiptPagination(FundsReceipt fundsReceipt){
         return fundsReceiptExtMapper.sumByExample(fundsReceipt);
    }
}
