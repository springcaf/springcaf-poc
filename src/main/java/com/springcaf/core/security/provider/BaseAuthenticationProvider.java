package com.springcaf.core.security.provider;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.springcaf.core.security.user.AppManagedUser;
import com.springcaf.core.util.StringUtils;

@Component
public abstract class BaseAuthenticationProvider implements AuthenticationProvider {
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException 
	{
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
   
        AppManagedUser user = new AppManagedUser(username, password);
        
		if(user == null || StringUtils.isNullOrEmpty(user.getUsername()) || StringUtils.isNullOrEmpty(user.getPassword()))
		{
			user.setUsername("");
			user.setPassword("");
		}
		else
		{
			boolean validUser = this.checkUserCredentials(user);
			
			if(!validUser)
			{
				user.setUsername("");
				user.setPassword("");
			}
		}

   
        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("Invalid username or password.");
        }
   
        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("Invalid username or password.");
        }
   
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
   
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	
	/**
	 * Default dummy function to validate username/password
	 * @param user
	 */
	abstract protected boolean checkUserCredentials(AppManagedUser user);

}
