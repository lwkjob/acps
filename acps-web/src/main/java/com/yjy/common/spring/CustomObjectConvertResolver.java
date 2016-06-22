/**
 * @(#)ObjectConvertInterceptor.java
 *
 * Copyright 2011 JUST IN MOBILE, Inc. All rights reserved.
 */
package com.yjy.common.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * description
 * @author  zhaoyuan
 * @version 1.0,2011-4-27
 */
public class CustomObjectConvertResolver implements HandlerMethodArgumentResolver {
	@Autowired(required=false)
	private CustomObjectConvertHandler[] customObjectConvertHandlers;
	 

	private Object execute(MethodParameter methodParameter,NativeWebRequest webRequest) {
		Class<?> paramType = methodParameter.getParameterType();
		if(customObjectConvertHandlers == null) return null;
		for(CustomObjectConvertHandler handler : customObjectConvertHandlers) {
			if(handler.getObjectType().equals(paramType)) {
				return handler.execute(methodParameter, webRequest);
			}
		}
		throw new RuntimeException("CustomObjectConvertHandler object is not fund");
	}

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> paramType = parameter.getParameterType();
        if(customObjectConvertHandlers == null) return false;
        for(CustomObjectConvertHandler handler : customObjectConvertHandlers) {
            if(handler.getObjectType().equals(paramType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object[] paramAnnos = parameter.getParameterAnnotations();
        if(paramAnnos == null||paramAnnos.length == 0) return null;
        for(Object anno : paramAnnos) {
            if(!ObjectConvertAnno.class.isInstance(anno))continue;
            ObjectConvertAnno objectConvertAnno = (ObjectConvertAnno) anno;
            Object object = execute( parameter, webRequest);
            if(objectConvertAnno.required()&&object == null) {
                throw new IllegalStateException("Missing parameter '" + parameter.getParameterName() + "' of type [" + parameter.getParameterType().getName()+ "]");
            }
            return object;
        }
        return null;
    }
}
