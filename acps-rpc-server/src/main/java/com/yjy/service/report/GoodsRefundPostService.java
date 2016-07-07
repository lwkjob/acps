package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsRefundPost;
import com.yjy.repository.mapper.report.ext.GoodsRefundPostExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
@Service
public class GoodsRefundPostService {


    private static Logger logger= LoggerFactory.getLogger(GoodsRefundPostService.class);



    @Resource
    private GoodsRefundPostExtMapper goodsRefundPostExtMapper;


    public Pagination<GoodsRefundPost> findGoodsRefundPostPagination(Pagination pagination,GoodsRefundPost goodsRefundPost) {
        List<GoodsRefundPost> list = goodsRefundPostExtMapper.selectByExample(pagination, goodsRefundPost);
        pagination.setData(list);
        return pagination;
    }

    public List<GoodsRefundPost> sumGoodsRefundPostPagination(GoodsRefundPost goodsRefundPost) {
        return goodsRefundPostExtMapper.sumByExample(goodsRefundPost);
    }
}
