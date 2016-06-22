/**
 * @(#)CustomObjectConvertHandlerImpl.java
 * Copyright 2012 naryou, Inc. All rights reserved.
 */
package com.yjy.common.spring;

import com.yjy.common.dao.Pagination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * @author  zhaoyuan
 * @date  2012-4-27
 * 获取当前分页对象的hanler
 */
@Component
public class PaginationConvertHandler implements CustomObjectConvertHandler{
	private static final Log LOG = LogFactory.getLog(PaginationConvertHandler.class);
	private static final String CURRENT_PAGE_TARGET = "page";

	private static final String PAGE_SIZE_TARGET = "iDisplayLength";

	private static final String SKIP_SIZE_TARGET = "skipSize";
	
	private static final String BEGIN_TARGET="iDisplayStart";
	@Override
	public Object execute(MethodParameter methodParameter,
			NativeWebRequest webRequest) {
		PaginationParser paginationParser = new PaginationParser((HttpServletRequest) webRequest.getNativeRequest(),(HttpServletResponse) webRequest.getNativeResponse());
		paginationParser.setCurrentPageTarget(CURRENT_PAGE_TARGET);
		paginationParser.setPageSizeTarget(PAGE_SIZE_TARGET);
		paginationParser.setSkipSizeTarget(SKIP_SIZE_TARGET);
		paginationParser.setBeginTarget(BEGIN_TARGET);
//		paginationParser.setPageSize(10);
		try {
			return paginationParser.parse();
		} catch (UnsupportedEncodingException e) {
			LOG.error(e);
			return null;
		}
	}
	@Override
	public Class<?> getObjectType() {
		return Pagination.class;
	}
}