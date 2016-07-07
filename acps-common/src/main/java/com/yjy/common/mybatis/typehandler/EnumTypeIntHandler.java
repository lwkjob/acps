/**
 * @(#)EnumTypeHandler.java
 * Copyright 2012 naryou, Inc. All rights reserved.
 */
package com.yjy.common.mybatis.typehandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author  zhaoyuan
 * @date  2012-11-23
 * mybatis enum转换handler<br/>
 * 必须有子类继承通过泛型继承EnumTypeIntHandler从而得到 type
 */
public class EnumTypeIntHandler<E extends Enum<E>> implements TypeHandler<E> {
	private Method method;
	private Class<E> type;
	
	
	private static final String STATIC_METHOD_NAME= "get";
	@SuppressWarnings("unchecked")
	public EnumTypeIntHandler() {
		 
		ParameterizedType parameterizedType = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.type = (Class<E>) parameterizedType.getActualTypeArguments()[0];
		try {
			method = type.getDeclaredMethod(STATIC_METHOD_NAME, int.class);
		} catch (Exception e) {
			throw new RuntimeException("Enum method not found : "+STATIC_METHOD_NAME+" in class "+type, e);
		}
		method.setAccessible(true);
	}
	@Override
	public void setParameter(PreparedStatement ps, int i, E parameter,
			JdbcType jdbcType) throws SQLException {
		throw new RuntimeException("EnumTypeIntHandler invoke setParameter Refused!");
	}

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        return getResult(rs.getString(columnName));
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        return getResult(rs.getString(columnIndex));
    }

	@Override
	public E getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		return getResult(cs.getString(columnIndex));
	}
	
	@SuppressWarnings("unchecked")
	private E getResult(String value) {
		if(StringUtils.isBlank(value)) return null;
		Integer intVal = Integer.parseInt(value);
		try {
			return (E) method.invoke(null, intVal);
		} catch (Exception e) {
			throw new RuntimeException("invoke enum static method error.", e);
		}
	}
}
