package com.springcaf.core.web.meta;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.security.user.AbstractTenantUser;

public abstract class WebAppMeta {
	
	protected AbstractTenantUser securityUser = null;
	protected HttpServletRequest request = null; 
	protected HttpServletResponse response = null;
	protected TimeZone userTimeZone = TimeZone.getTimeZone("US/Central");
	
	/**
	 * Constructor
	 * @param applicationFrameworkService
	 * @param request
	 * @param response
	 * @throws SpringcafException 
	 */
	public WebAppMeta(HttpServletRequest request, 
			HttpServletResponse response) throws SpringcafException
	{
		this.request = request;
		this.response = response;
	}
	
	public String getTenantId()
	{
		if(this.securityUser != null)
		{
			return this.securityUser.getTenantId();
		}
		
		return null;
	}
	
	public String getUserEmail()
	{
		if(this.securityUser != null)
		{
			return this.securityUser.getUserEmail();
		}
		
		return null;
	}

	public AbstractTenantUser getSecurityUser()
	{
		return this.securityUser;
	}
	
	public void setSecurityUser(AbstractTenantUser securityUser) {
		this.securityUser = securityUser;
	}

	public String getEncodedUserId()
	{
		if(this.securityUser != null)
		{
			return this.securityUser.getEncodedUserId();
		}
		
		return null;
		
	}
	
	public String getUserDisplayName()
	{
		if(this.securityUser != null)
		{
			return this.securityUser.getDisplayName();
		}
		
		return "";
	}

	public TimeZone getUserTimeZone() {
		return userTimeZone;
	}

	public void setUserTimeZone(TimeZone userTimeZone) {
		this.userTimeZone = userTimeZone;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
}
