package com.springcaf.starter.app.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.springcaf.core.security.provider.BaseAuthenticationProvider;
import com.springcaf.core.security.user.AppManagedUser;

public class LoginPasswordAuthenticationProvider extends BaseAuthenticationProvider {

	static Logger logger = LogManager.getLogger(LoginPasswordAuthenticationProvider.class);

	@Override
	protected boolean checkUserCredentials(AppManagedUser user) {
		
		try
		{
			// user validated, set additional data
			user.setTenantId("tenantid");
			user.setDisplayName("User Name");
			user.setInternalUserId("demo-user-id");
			return true;
		}
		catch(Exception ex)
		{
			logger.error(ex);
			return false;
		}
	}

}
