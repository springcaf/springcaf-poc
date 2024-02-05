package com.springcaf.core.security.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Collection;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;

import com.springcaf.core.security.user.TenantOidcUser;

public class WebSecurityUtils {
	
	/**
	 * Check security context to verify a user role
	 * @param securityContext
	 * @param userRole
	 * @return
	 */
	public static boolean checkUserHasRole(SecurityContext securityContext, String userRole)
	{
		if(!isLoggedInUser(securityContext))
		{
			return false;
		}
		
		@SuppressWarnings("unchecked")
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)securityContext.getAuthentication().getAuthorities();

		for(SimpleGrantedAuthority role : authorities)
		{
			if(role.getAuthority().equals(userRole))
			{
				return true;
			}
		}
		
		return false;
	}

	/**
	 * Get the userName currently signed in.
	 * @param securityContext
	 * @return
	 */
	public static String getLoggedInUserName(SecurityContext securityContext)
	{
		if(!isLoggedInUser(securityContext))
		{
			return null;
		}

		return securityContext.getAuthentication().getName();
	}
	
	public static TenantOidcUser getLoggedInUser(SecurityContext securityContext)
	{
		if(!isLoggedInUser(securityContext))
		{
			return null;
		}

		return (TenantOidcUser)securityContext.getAuthentication().getPrincipal();
	}
	
	public static TenantOidcUser getTenantOidcUser(SecurityContext securityContext)
	{
		if(!isLoggedInUser(securityContext))
		{
			return null;
		}

		return (TenantOidcUser)securityContext.getAuthentication().getPrincipal();
	}
	
	public static boolean isLoggedInUser(SecurityContext securityContext)
	{
		if(securityContext == null || securityContext.getAuthentication() == null)
		{
			return false;
		}

		Authentication authentication = securityContext.getAuthentication();
		if(authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated())
		{
			return false;
		}

		return true;
	}
	
	public static String generateSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		
		return byteToString(bytes);
	}

	private static String byteToString(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	public static String getPasswordHashWithSalt(String password, String salt)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(stringToByte(salt));
		byte[] hashedBytes = digest.digest(stringToByte(password));

		return byteToString(hashedBytes);
	}

	private static byte[] stringToByte(String input) {

		return Base64.decodeBase64(input);
	}
	
	public static boolean checkPassword(String rawPassword, String salt, String hashedPassword) throws NoSuchAlgorithmException
	{
		String hashedRawPassword = getPasswordHashWithSalt(rawPassword, salt);
		
		if(hashedPassword.equals(hashedRawPassword))
		{
			return true;
		}
		
		return false;
	}
}
