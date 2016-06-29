/**
 * @(#)ExecutorInterceptor.java Copyright 2012 naryou, Inc. All rights reserved.
 */
package com.yjy.common.mybatis.interceptor;

import com.yjy.common.dao.Pagination;
import com.yjy.common.mybatis.MyBatisDynamicQueryUtils;
import com.yjy.common.utils.Reflections;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.TextSqlNode;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Executor.query()方法拦截器<br/>
 * 该拦截器有2个功能
 * 1、是把map查询参数中代用分页的的参数整合到一个map中，方便后面的代码调用<br/>
 * 2、实现自定义的动态SQL语句<br/>
 * 3、缓存的处理<br/>
 *
 * @author zhaoyuan
 * @version 1.0, 2012-3-12
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class ExecutorInterceptor implements Interceptor {
    private static final Log LOG = LogFactory.getLog(ExecutorInterceptor.class);

    private static final Map<String, String> dynamicQueryMap = new HashMap<String, String>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Object paramObj = args[1];
        if (paramObj instanceof Map) {
            processMapParam(invocation, args, paramObj);
            processDynamicQuery(invocation);
        } else if (paramObj instanceof Pagination) {
            processPaginationParam(invocation, args, paramObj);
        }
        Object value = invocation.proceed();
        return value;
    }


    /**
     * 处理动态查询语句
     *
     * @param invocation
     * @author zhaoyuan
     */
    private void processDynamicQuery(Invocation invocation) {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) args[1];

		/*DynamicSqlSource dynamicSqlSource = getDynamicSqlSource(invocation);
        if(dynamicSqlSource == null) return;
		try {
			MixedSqlNode mixedSqlNode = (MixedSqlNode) ReflectUtils.getValueByFieldName(dynamicSqlSource, "rootSqlNode");
			@SuppressWarnings("unchecked")
			List<SqlNode> contents = (List<SqlNode>) ReflectUtils.getValueByFieldName(mixedSqlNode, "contents");
			for(SqlNode sqlNode: contents ) {
				if(!(sqlNode instanceof TextSqlNode)) continue;
				TextSqlNode textSqlNode = (TextSqlNode) sqlNode;
				String queryString = (String) ReflectUtils.getValueByFieldName(textSqlNode, "text");
				if(!MyBatisDynamicQueryUtils.isDynamicQuery(queryString))
					continue;
				String dynamicSql = MyBatisDynamicQueryUtils.parser(queryString, params);
				ReflectUtils.setValueByFieldName(textSqlNode,  "text", dynamicSql);
			} 
		} catch (Exception e) {
			LOG.error("reflect mybatis sql node error.",e); 
		}*/

        String dynamicQuery = getDynamicSqlSource(invocation);
        if (dynamicQuery == null) return;
        String dynamicSql = MyBatisDynamicQueryUtils.parser(dynamicQuery, params);
        rePutDynamicSqlSource(dynamicSql, mappedStatement);
        args[0] = mappedStatement;
        Reflections.setFieldValue(invocation, "args", args);
    }

    private void rePutDynamicSqlSource(String dynamicSql, MappedStatement mappedStatement) {
        SqlSource sqlSource = mappedStatement.getSqlSource();
        DynamicSqlSource dynamicSqlSource = (DynamicSqlSource) sqlSource;

        try {
            MixedSqlNode mixedSqlNode = (MixedSqlNode) Reflections.getFieldValue(dynamicSqlSource, "rootSqlNode");
            @SuppressWarnings("unchecked")
            List<SqlNode> contents = (List<SqlNode>) Reflections.getFieldValue(mixedSqlNode, "contents");
            SqlNode sqlNode = contents.get(0);

            TextSqlNode textSqlNode = (TextSqlNode) sqlNode;

            Reflections.setFieldValue(textSqlNode, "text", dynamicSql);

        } catch (Exception e) {
            LOG.error("reflect mybatis sql node error.", e);
        }
    }

    private String getDynamicSqlSource(Invocation invocation) {
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        String id = mappedStatement.getId();
        String dynamicQuery = dynamicQueryMap.get(id);
        if (dynamicQuery != null) return dynamicQuery;
        SqlSource sqlSource = mappedStatement.getSqlSource();
        if (!(sqlSource instanceof DynamicSqlSource)) {
            return null;
        }
        DynamicSqlSource dynamicSqlSource = (DynamicSqlSource) sqlSource;
        MixedSqlNode mixedSqlNode;
        try {
            mixedSqlNode = (MixedSqlNode) Reflections.getFieldValue(dynamicSqlSource, "rootSqlNode");
            @SuppressWarnings("unchecked")
            List<SqlNode> contents = (List<SqlNode>) Reflections.getFieldValue(mixedSqlNode, "contents");
            if (contents == null || contents.size() != 1) return null;
            SqlNode sqlNode = contents.get(0);

            if (!(sqlNode instanceof TextSqlNode)) return null;
            TextSqlNode textSqlNode = (TextSqlNode) sqlNode;
            dynamicQuery = (String) Reflections.getFieldValue(textSqlNode, "text");
            if (!MyBatisDynamicQueryUtils.isDynamicQuery(dynamicQuery))
                return null;

        } catch (Exception e) {
            LOG.error("reflect mybatis sql node error.", e);
            return null;
        }
        dynamicQueryMap.put(id, dynamicQuery);
        return dynamicQuery;
    }

    @SuppressWarnings("unchecked")
    private void processMapParam(Invocation invocation, Object[] args, Object paramObj) {
        Map<String, Object> paramMap = (Map<String, Object>) paramObj;
        Map<String, Object> realParam = null;
        Pagination pagination = null;
        for (String key : paramMap.keySet()) {
            Object value = paramMap.get(key);
            if (value instanceof Map) {
                realParam = (Map<String, Object>) value;
            }
            if (value instanceof Pagination) {
                pagination = (Pagination) value;
            }
        }
        if (realParam == null) {
            realParam = paramMap;
        }
        if (pagination != null) {
            realParam.put("pagination", pagination);
        }
        args[1] = realParam;
        Reflections.setFieldValue(invocation, "args", args);
    }

    private void processPaginationParam(Invocation invocation, Object[] args,
                                        Object paramObj) {
        Pagination pagination = (Pagination) paramObj;
        Map<String, Object> realParam = new HashMap<String, Object>();
        realParam.put("pagination", pagination);
        args[1] = realParam;
        Reflections.setFieldValue(invocation, "args", args);
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}
