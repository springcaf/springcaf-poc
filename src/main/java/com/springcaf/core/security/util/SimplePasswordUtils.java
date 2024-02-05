package com.springcaf.core.security.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.springcaf.core.util.StringUtils;

public class SimplePasswordUtils {
	
    private static final String PASSWORD_PATTERN = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}";

	public static String validatePassword(String password1, String password2)
	{
		if(StringUtils.isNullOrEmpty(password1))
		{
			return "Password cannot be empty";
		}
		else if(!password1.equals(password2))
		{
			return "Password values do not match";
		}
		
		return validatePasswordStrength(password1);
	}

	/**
	 * Validate password strength
	 * (?=.*[a-z])     : This matches the presence of at least one lowercase letter.
	 * (?=.*d)         : This matches the presence of at least one digit i.e. 0-9.
	 * (?=.*[@#$%])    : This matches the presence of at least one special character.
	 * ((?=.*[A-Z])    : This matches the presence of at least one capital letter.
	 * {6,16}          : This limits the length of password from minimum 6 letters to maximum 16 letters.
	 * @param password
	 * @return
	 */
	private static String validatePasswordStrength(String password)
	{
		if(!validatePattern(password, PASSWORD_PATTERN))
		{
			return "Password needs at least 8 letters/numbers with at least one lower, upper, special character and number.";
		}
		
		return "";
	}
	
    private static boolean validatePattern(final String password, final String pattern)
    {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(password);
        return m.matches();
    }
	
}
