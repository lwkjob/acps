package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsReceipt;
import com.yjy.repository.mapper.report.ext.GoodsReceiptExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
@Service
public class GoodsReceiptService {


    private static Logger logger= LoggerFactory.getLogger(GoodsReceiptService.class);



    @Resource
    private GoodsReceiptExtMapper goodsReceiptExtMapper;


    public Pagination<GoodsReceipt> findGoodsReceiptPaginationList(Pagination pagination,GoodsReceipt goodsReceipt) {
        List<GoodsReceipt> list = goodsReceiptExtMapper.selectByExample(pagination, goodsReceipt);
        pagination.setData(list);
        return pagination;
    }

    public List<GoodsReceipt> sumGoodsReceiptPaginationList(GoodsReceipt goodsReceipt) {
       return goodsReceiptExtMapper.sumByExample(goodsReceipt);

    }
}
