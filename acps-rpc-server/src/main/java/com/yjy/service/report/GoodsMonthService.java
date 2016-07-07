package com.yjy.service.report;

import com.yjy.common.dao.BaseDao;
import com.yjy.common.dao.Pagination;
import com.yjy.common.utils.MybatisUtils;
import com.yjy.common.entity.report.GoodsMonth;
import com.yjy.common.entity.report.GoodsMonthExample;
import com.yjy.repository.mapper.report.GoodsMonthMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/7/4.
 */

@Service
public class GoodsMonthService {

    private static Logger logger = LoggerFactory.getLogger(GoodsMonthService.class);

    @Resource
    private GoodsMonthMapper goodsMonthMapper;

    @Resource
    private BaseDao baseDao;

    public Pagination<GoodsMonth> findGoodsMonthPagination(Pagination pagination, GoodsMonth goodsMonth) {
        GoodsMonthExample goodsMonthExample=new GoodsMonthExample();
        GoodsMonthExample goodsMonthExample1 = MybatisUtils.convertObj2Example(goodsMonth, goodsMonthExample);
        return baseDao.pagedQuery(goodsMonthMapper,goodsMonthExample1,pagination.getCurrentPage(),pagination.getPageSize());
    }


}
