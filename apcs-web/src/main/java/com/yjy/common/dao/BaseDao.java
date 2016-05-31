package com.yjy.common.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.MethodInvoker;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by yaobo on 2014/5/27.
 */

@Repository("baseDao")
//@Repository
public class BaseDao extends SqlSessionDaoSupport {

    private static Integer DEFAULT_PAGE_SIZE = 20;

    protected Log log = LogFactory.getLog(this.getClass());

    @Autowired
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

//    /**
//     * sqlmap中必须提供一个查询总数的statement. 命名规则为select*Count()
//     * parameter中必须提供PageInfo.PAGE_NO,PageInfo.PAGE_SIZE 2个参数。 不提供使用默认的查询数
//     *
//     * @param statement
//     * @param parameter
//     * @return
//     */
//    public Page pagedQuery(String statement, Map parameter) {
//        return this.pagedQuery(statement, parameter, true);
//    }
//
//    public Page pagedQuery(String statement, Map parameter, boolean useAutoPage) {
//        log.debug("Invoke pagedQuery() begin...");
//        Integer totalCount = (Integer) this.getSqlSession().selectOne(statement + "Count", parameter);
//        if (totalCount == null) {
//            totalCount = 0;
//        }
//        Page page = null;
//        if (totalCount > 0) {
//            Integer pageNo = parameter.get("pageNo") == null ? 1 : (Integer) parameter.get("pageNo");
//            Integer pageSize = parameter.get("pageSize") == null ? DEFAULT_PAGE_SIZE : (Integer) parameter.get("pageSize");
//            Integer startIndex = (pageNo - 1) * pageSize;
//            List dataList = null;
//            parameter.put("startIndex", startIndex);
//            parameter.put("pageSize", pageSize);
//            if (useAutoPage) {
//                dataList = this.getSqlSession().selectList(statement, parameter, new RowBounds(startIndex, pageSize));
//            } else {
//                dataList = this.getSqlSession().selectList(statement, parameter);
//            }
//            page = new PageImpl(dataList, new PageRequest(pageNo - 1, pageSize), totalCount);
//        } else {
//            page = new PageImpl(new ArrayList(), new PageRequest(0, DEFAULT_PAGE_SIZE), totalCount);
//        }
//        log.debug("Invoke pagedQuery() end...");
//        return page;
//    }

    public Pagination pagedQuery(Object mapperInterface, Object example, Map parameter) throws RuntimeException {
        log.debug("Invoke pagedQuery() begin...");
        Pagination page = null;
        try {
            //取消count时的order by
            String orderByClause = BeanUtils.getProperty(example, "orderByClause");
            BeanUtils.setProperty(example, "orderByClause", "");
            //count()-

            MethodInvoker methodInvoker = new MethodInvoker();
            methodInvoker.setTargetObject(mapperInterface);
            methodInvoker.setTargetMethod("countByExample");
            methodInvoker.setArguments(new Object[]{example});
            methodInvoker.prepare();
            Integer totalCount = (Integer) methodInvoker.invoke();

            Integer pageNo = (parameter.get("pageNo") == null || ((Integer) parameter.get("pageNo")).intValue() <= 0) ? 1 : (Integer) parameter.get("pageNo");
            Integer pageSize = parameter.get("pageSize") == null ? DEFAULT_PAGE_SIZE : (Integer) parameter.get("pageSize");

            if (totalCount > 0) {
                Integer startIndex = (pageNo - 1) * pageSize;
                //设置分页
                if (StringUtils.isBlank(orderByClause)) {
                    orderByClause = " id desc";
                }
                orderByClause += " limit " + startIndex + ", " + pageSize;
                BeanUtils.setProperty(example, "orderByClause", orderByClause);
                //分页查询
                methodInvoker.setTargetMethod("selectByExample");
                methodInvoker.setArguments(new Object[]{example});
                methodInvoker.prepare();
                List dataList = (List) methodInvoker.invoke();
                page = new Pagination(pageNo, totalCount, pageSize, dataList);
            } else {
                page = new Pagination(pageNo, 0, pageSize, Collections.emptyList());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
        log.debug("Invoke pagedQuery() end...");
        return page;
    }

    public Pagination pagedQuery(Object mapperInterface, Object example, Integer pageNo, Integer pageSize) throws RuntimeException {
        log.debug("Invoke pagedQuery() begin...");
        Map paramMap = new HashMap();
        paramMap.put("pageNo", pageNo);
        paramMap.put("pageSize", pageSize);
        log.debug("Invoke pagedQuery() end...");
        return pagedQuery(mapperInterface, example, paramMap);
    }

    public Pagination pagedQuery(Object mapperInterface, Object example, Integer pageNo) throws RuntimeException {
        log.debug("Invoke pagedQuery() begin...");
        Map paramMap = new HashMap();
        paramMap.put("pageNo", pageNo);
        log.debug("Invoke pagedQuery() end...");
        return pagedQuery(mapperInterface, example, paramMap);
    }
}