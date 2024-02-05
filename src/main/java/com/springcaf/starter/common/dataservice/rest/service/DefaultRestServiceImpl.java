package com.springcaf.starter.common.dataservice.rest.service;

import org.springframework.http.HttpHeaders;

public class DefaultRestServiceImpl {
	
	/**
	 * Implement how a user ID can be retrieved from the Request Header
	 * @param reqHeaders
	 * @return
	 */
	protected String getValidUserId(HttpHeaders reqHeaders)
	{
		return "user_id";
	}

}
