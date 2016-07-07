package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsReceipt;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundsReceiptExtMapper {
    List<FundsReceipt> selectByExample(@Param("pagination") Pagination pagination,@Param("fundsReceipt") FundsReceipt fundsReceipt);

    List<FundsReceipt>sumByExample(@Param("fundsReceipt") FundsReceipt fundsReceipt);

    int countByExample(@Param("fundsReceipt") FundsReceipt fundsReceipt);
}