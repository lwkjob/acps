/**
 * @(#)CustomObjectConvertHandler.java
 * Copyright 2012 naryou, Inc. All rights reserved.
 */
package com.yjy.common.spring;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author  zhaoyuan
 * @date  2012-4-27
 * description
 */
public interface CustomObjectConvertHandler {
	public Class<?> getObjectType();
	public Object execute(MethodParameter methodParameter, NativeWebRequest webRequest);
	
}