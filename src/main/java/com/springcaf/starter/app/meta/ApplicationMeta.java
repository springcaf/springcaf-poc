package com.springcaf.starter.app.meta;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.starter.app.service.ApplicationFrameworkService;

public class ApplicationMeta extends WebAppMeta {
	
	private ApplicationFrameworkService applicationFrameworkService = null;

	/**
	 * Constructor
	 * @param applicationFrameworkService
	 * @param request
	 * @param response
	 * @throws SpringcafException
	 */
	public ApplicationMeta(ApplicationFrameworkService applicationFrameworkService, HttpServletRequest request, HttpServletResponse response) throws SpringcafException {
		super(request, response);
		
		this.applicationFrameworkService = applicationFrameworkService;
	}

	public ApplicationFrameworkService getApplicationFrameworkService() {
		return applicationFrameworkService;
	}

	public void setApplicationFrameworkService(ApplicationFrameworkService applicationFrameworkService) {
		this.applicationFrameworkService = applicationFrameworkService;
	}

	@Override
	public String getEncodedUserId()
	{
		// do real implementation here
		return "loggedin-user";
		
	}
	
}
