package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsOut;
import com.yjy.repository.mapper.report.ext.GoodsOutExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
@Service
public class GoodsOutService {


    private static Logger logger= LoggerFactory.getLogger(GoodsOutService.class);

    @Resource
    private GoodsOutExtMapper goodsOutExtMapper;


    public Pagination<GoodsOut> findGoodsOutPagination(Pagination pagination,GoodsOut goodsOutExample) {
        List<GoodsOut> goodsOuts = goodsOutExtMapper.selectByExample(pagination, goodsOutExample);
        pagination.setData(goodsOuts);
        return pagination;
    }

    public List<GoodsOut>  sumGoodsOutPagination(GoodsOut goodsOutExample) {
        return   goodsOutExtMapper.sumByExample(goodsOutExample);
    }
}
