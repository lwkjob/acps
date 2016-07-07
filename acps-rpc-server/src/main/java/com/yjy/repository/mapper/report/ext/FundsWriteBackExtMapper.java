package com.yjy.repository.mapper.report.ext;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.FundsWriteBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FundsWriteBackExtMapper {
    List<FundsWriteBack> selectByExample(@Param("pagination") Pagination pagination,@Param("fundsWriteBack") FundsWriteBack fundsWriteBack);

    List<FundsWriteBack> sumByExample(@Param("fundsWriteBack") FundsWriteBack fundsWriteBack);

    int countByExample(@Param("fundsWriteBack") FundsWriteBack fundsWriteBack);
}