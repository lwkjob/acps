package com.yjy.service.report;

import com.yjy.common.dao.Pagination;
import com.yjy.common.entity.report.GoodsBalance;
import com.yjy.repository.mapper.report.ext.GoodsBalanceExtMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/4.
 */
@Service
public class GoodsBalanceService {


    private static Logger logger= LoggerFactory.getLogger(GoodsBalanceService.class);



    @Resource
    private GoodsBalanceExtMapper goodsBalanceExtMapper;


    public Pagination<GoodsBalance> findGoodsBalancePagination(Pagination pagination,GoodsBalance goodsBalance) {
        List<GoodsBalance> list = goodsBalanceExtMapper.selectByExample(pagination, goodsBalance);
        pagination.setData(list);
        return pagination;
    }

    public List<GoodsBalance>  sumGoodsBalancePagination(GoodsBalance goodsBalance) {
        return goodsBalanceExtMapper.sumByExample(goodsBalance);

    }
}
