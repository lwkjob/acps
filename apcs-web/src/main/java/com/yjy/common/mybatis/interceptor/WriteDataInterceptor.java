/**
 * @(#)WriteInterceptor.java
 * Copyright 2012 naryou, Inc. All rights reserved.
 */
package com.yjy.common.mybatis.interceptor;

import com.yjy.common.mybatis.CacheManager;
import com.yjy.common.utils.ApplicationContextUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * @author  zhaoyuan
 * @date  2012-12-19
 * description
 */
@Intercepts({@Signature(type= Executor.class,method = "update",
args = {MappedStatement.class, Object.class})})
public class WriteDataInterceptor implements Interceptor {
	private static CacheManager memcachedManager;
	public CacheManager getMemcachedManager() {
		if(memcachedManager == null) {
			memcachedManager = (CacheManager) ApplicationContextUtils.getService("memcachedManager");
		}
		return memcachedManager;
	}
	 
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object[] args = invocation.getArgs();
		MappedStatement mappedStatement = (MappedStatement) args[0];
		Object paramObj = args[1];
		if(paramObj == null)
			return invocation.proceed();
		if(mappedStatement.getSqlCommandType()== SqlCommandType.UPDATE||
				mappedStatement.getSqlCommandType()== SqlCommandType.DELETE) {
			return deleteCache(invocation, paramObj);
		}
		return invocation.proceed();
	}
	 
	private Object deleteCache(Invocation invocation,Object paramObj) throws Throwable {
		Object value =  invocation.proceed();
		return value;
	}


	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		
	}
}
