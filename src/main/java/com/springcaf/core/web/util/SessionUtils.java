package com.springcaf.core.web.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;

import com.springcaf.core.web.model.table.SortAndFilterState;

public class SessionUtils {
	
	private static final String SESSION_VAR_SORT_AND_FILTER = "sort-filter-";
	
	/**
	 * Get cached sort and filter
	 * @param request
	 * @return
	 */
	public static SortAndFilterState getSessionSortAndFilter(HttpServletRequest request)
	{
		Object sortAndFilter = request.getSession().getAttribute(SESSION_VAR_SORT_AND_FILTER);
		
		if(sortAndFilter != null)
		{
			return (SortAndFilterState)sortAndFilter;
		}
		
		return null;
	}
	
	/**
	 * Set cached sort and filter
	 * @param request
	 * @param sortAndFilter
	 */
	public static void setUserSortAndFilter(HttpServletRequest request, SortAndFilterState sortAndFilter)
	{
		request.getSession().setAttribute(SESSION_VAR_SORT_AND_FILTER, sortAndFilter);
	}
	
	public static CsrfToken getCsrfToken(HttpServletRequest request)
	{
		CsrfToken csrfToken = (CsrfToken)request.getAttribute("_csrf");
		
		return csrfToken;
	}
	
	/**
	 * Save the cacheObject
	 * @param request
	 * @param cacheObj
	 */
	public static void setCacheObject(HttpServletRequest request, String cacheKey, Object cacheObj)
	{
		request.getSession().setAttribute(cacheKey, cacheObj);
	}
	
	/**
	 * Get the cached object
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCacheObject(HttpServletRequest request, String cacheKey, Class<T> objClass)
	{
		Object obj = request.getSession().getAttribute(cacheKey);
		
		if(obj == null)
		{
			return null;
		}
		
		return (T)obj;
	}
}
