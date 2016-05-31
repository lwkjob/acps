/**
 * @(#)PaginationInterceptor.java Copyright 2012 naryou, Inc. All rights reserved.
 */
package com.yjy.common.mybatis.interceptor;

import com.yjy.common.dao.Pagination;
import com.yjy.common.mybatis.DataBaseDialect;
import com.yjy.common.utils.HibernateUtils;
import com.yjy.common.utils.Reflections;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * <p>mybatis分页拦截器</p>
 * <p>分页拦截器拦截基于映射器(Mapper)的mapper映射</p>
 * <p>如：<code>mapper.find(Map<String,Object>,Pagination pagination)</code></p>
 * <p>拦截器中会重写SQL语句转换成分页语句。并会根据SQL语句进行count查询注入到pagination对象中。</p>
 * <p>目前支持mysql、oracle</p>
 *
 * @author zhaoyuan
 * @version 1.0, 2012-3-6
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PaginationInterceptor implements Interceptor {
    /**
     * 数据库方言<br/>
     * 默认是mysql
     */
    private DataBaseDialect dataBaseDialect = DataBaseDialect.MYSQL;

    public void setDataBaseDialect(DataBaseDialect dataBaseDialect) {
        this.dataBaseDialect = dataBaseDialect;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (!(invocation.getTarget() instanceof RoutingStatementHandler)) {
            return invocation.proceed();
        }
        RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation
                .getTarget();
        BaseStatementHandler delegate = (BaseStatementHandler) Reflections.getFieldValue(statementHandler, "delegate");
        MappedStatement mappedStatement = (MappedStatement) Reflections.getFieldValue(delegate, "mappedStatement");
        BoundSql boundSql = delegate.getBoundSql();

        Object parameterObject = boundSql.getParameterObject();

        if (!(parameterObject instanceof Map)) {
            return invocation.proceed();
        }
        Pagination pagination = loadPagination(parameterObject);
        if (pagination == null) {
            return invocation.proceed();
        }
        setPaginationCount(boundSql.getSql(), invocation, mappedStatement, boundSql,
                pagination);
        resetPageSql(boundSql, pagination);
        return invocation.proceed();
    }

    /**
     * 重写SQL语句为分页语句和重置查询参数
     *
     * @param boundSql
     * @param pagination
     * @return
     * @author zhaoyuan
     */
    private void resetPageSql(BoundSql boundSql,
                              Pagination pagination) {
        String sql = boundSql.getSql();
        String pageSql = generatePageSql(sql, pagination);
        Reflections.setFieldValue(boundSql, "sql", pageSql);
    }

    /**
     * 设置查询数
     *
     * @param sql
     * @param invocation
     * @param mappedStatement
     * @param boundSql
     * @param pagination
     * @throws SQLException
     * @author zhaoyuan
     */
    private void setPaginationCount(String sql, Invocation invocation,
                                    MappedStatement mappedStatement, BoundSql boundSql,
                                    Pagination pagination) throws SQLException, Exception {
        String countSQL = HibernateUtils.countHql(sql);
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement countStmt = connection.prepareStatement(countSQL);

        /**
         * zhaoyuan 2014年10月10日18:58:59 修改。
         * 修改原因： new BoundSql(),生成的 countBS 里面丢失了 xml里面 foreach 动态参数。
         * 表现为：additionalParameters 为空，metaParameters 为空。
         * 修改方法：直接使用原始查询的 BoundSql 作为 总数统计 查询的BoundSql。
         */
//        String originSql = boundSql.getSql();

//        BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),
//				countSQL, boundSql.getParameterMappings(),
//				boundSql.getParameterObject());

//        ReflectUtils.setValueByFieldName(boundSql, "sql", countSQL);
        setParameters(countStmt, mappedStatement, boundSql,
                boundSql.getParameterObject());
        ResultSet rs = countStmt.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt(1);
        }
        rs.close();
        countStmt.close();
        pagination.setCount(count);
//        ReflectUtils.setValueByFieldName(boundSql, "sql", countSQL);

    }

    /**
     * 对SQL参数(?)设值,参考
     * org.apache.ibatis.executor.parameter.DefaultParameterHandler
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public void setParameters(PreparedStatement ps,
                              MappedStatement mappedStatement, BoundSql boundSql,
                              Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters")
                .object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql
                .getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration
                    .getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null
                    : configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry
                            .hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName
                            .startsWith(ForEachSqlNode.ITEM_PREFIX)
                            && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value)
                                    .getValue(
                                            propertyName.substring(prop
                                                    .getName().length())
                                    );
                        }
                    } else {
                        value = metaObject == null ? null : metaObject
                                .getValue(propertyName);
                    }
                    @SuppressWarnings("rawtypes")
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException(
                                "There was no TypeHandler found for parameter "
                                        + propertyName + " of statement "
                                        + mappedStatement.getId()
                        );
                    }
                    typeHandler.setParameter(ps, i + 1, value,
                            parameterMapping.getJdbcType());
                }
            }
        }
    }

    /**
     * 提出分页对象因为在mybatis中，对应mapper映射器只会创建一个map参数<br/>
     * 所以对于有分页的查询map中会封装查询map参数和分页对象，所以要从中提取出来<br/>
     *
     * @param parameterObject
     * @return
     * @author zhaoyuan
     */

    private Pagination loadPagination(Object parameterObject) {
        @SuppressWarnings("unchecked")
        Map<String, Object> params = (Map<String, Object>) parameterObject;
        for (String key : params.keySet()) {
            Object value = params.get(key);
            if (value instanceof Pagination) {
                return (Pagination) value;
            }
        }
        return null;
    }

    /**
     * 根据数据库方言，生成特定的分页sql<br/>
     *
     * @param sql
     * @param pagination
     * @return
     */
    private String generatePageSql(String sql, Pagination pagination) {
        if (dataBaseDialect == DataBaseDialect.MYSQL) {
            StringBuffer pageSql = new StringBuffer(sql);
            pageSql.append(" limit " + pagination.getBegin() + "," + pagination.getPageSize());
            return pageSql.toString();
        }
        if (dataBaseDialect == DataBaseDialect.SQLSERVER2005) {
            int orderByIndex = sql.toLowerCase().lastIndexOf("order by");
            if (orderByIndex <= 0) {
                throw new UnsupportedOperationException(
                        "must specify 'order by' statement to support limit operation with offset in sql server 2005");
            }
            String sqlOrderBy = sql.substring(orderByIndex + 8);
            String sqlRemoveOrderBy = sql.substring(0, orderByIndex);
            int insertPoint = getSqlAfterSelectInsertPoint(sql);
            return new StringBuffer(sql.length() + 100)
                    .append("with tempPagination as(")
                    .append(sqlRemoveOrderBy)
                    .insert(insertPoint + 23, " ROW_NUMBER() OVER(ORDER BY " + sqlOrderBy + ") as RowNumber,")
                    .append(") select * from tempPagination where RowNumber between " + pagination.getBegin() + " and " + pagination.getPageSize()).toString();
        }
        if (dataBaseDialect == DataBaseDialect.ORACLE10G) {
            sql = sql.trim();
            String forUpdateClause = null;
            boolean isForUpdate = false;
            final int forUpdateIndex = sql.toLowerCase().lastIndexOf("for update");
            if (forUpdateIndex > -1) {
                // save 'for update ...' and then remove it
                forUpdateClause = sql.substring(forUpdateIndex);
                sql = sql.substring(0, forUpdateIndex - 1);
                isForUpdate = true;
            }
            boolean hasOffset = pagination.getBegin() > 0;
            StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
            if (hasOffset) {
                pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
            } else {
                pagingSelect.append("select * from ( ");
            }
            pagingSelect.append(sql);
            if (hasOffset) {
                pagingSelect.append(" ) row_ where rownum <= " + pagination.getEnd() + ") where rownum_ > " + pagination.getBegin());
            } else {
                pagingSelect.append(" ) where rownum <= " + pagination.getEnd());
            }

            if (isForUpdate) {
                pagingSelect.append(" ");
                pagingSelect.append(forUpdateClause);
            }

            return pagingSelect.toString();
        }

        throw new RuntimeException("mybatis DataBaseDialect is not found");

    }

    private int getSqlAfterSelectInsertPoint(String sql) {
        int selectIndex = sql.toLowerCase().indexOf("select");
        final int selectDistinctIndex = sql.toLowerCase().indexOf("select distinct");
        return selectIndex + (selectDistinctIndex == selectIndex ? 15 : 6);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

}

