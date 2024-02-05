package com.springcaf.core.util;

public class StringFormatUtils {
	
	/**
	 * Turn camel notation into underscore: each upper case becomes underscore + lower case
	 * @param camel
	 * @return
	 */
	public static String camelToUnderscore(String camel)
	{
		if(camel == null)
		{
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<camel.length(); i++)
		{
			char x = camel.charAt(i);
			if(Character.isUpperCase(x))
			{
				buffer.append("_" + Character.toLowerCase(x));
			}
			else
			{
				buffer.append(x);
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * Add an underscore before a number in the name
	 * @param str
	 * @return
	 */
	public static String addUnderscoreBeforeNumber(String str)
	{
		if(str == null)
		{
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		boolean isDigit = false;
		for(int i=str.length()-1; i>=0; i--)
		{
			char x = str.charAt(i);
			if(Character.isDigit(x))
			{
				isDigit = true;
				buffer.append(x);
			}
			else
			{
				if(isDigit)
				{
					buffer.append("_");
				}
				isDigit = false;
				buffer.append(x);
			}
		}
		
		return buffer.reverse().toString();
	}
	
	/**
	 * Convert underscore to Camel notation
	 * @param underscore
	 * @param firstLetterUpper
	 * @return
	 */
	public static String underscoreToCamel(String underscore, boolean firstLetterUpper)
	{
		if(underscore == null)
		{
			return null;
		}
		
		underscore = underscore.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		boolean nextCharUpper = false;
		for(int i=0; i<underscore.length(); i++)
		{
			
			char x = underscore.charAt(i);
			if(i==0)
			{
				if(firstLetterUpper)
				{
					x = Character.toUpperCase(x);
				}
				buffer.append(x);
			}
			else
			{
				if(x == '_')
				{
					nextCharUpper = true;
				}
				else
				{
					if(nextCharUpper)
					{
						x = Character.toUpperCase(x);
						nextCharUpper = false;
					}
					buffer.append(x);
				}
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * Convert underscore to Camel notation
	 * @param underscore
	 * @param firstLetterUpper
	 * @return
	 */
	public static String underscoreToLabel(String underscore)
	{
		if(underscore == null)
		{
			return null;
		}
		
		underscore = underscore.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		boolean nextCharUpper = false;
		for(int i=0; i<underscore.length(); i++)
		{
			
			char x = underscore.charAt(i);
			if(i==0)
			{
				if(x != '_')
				{
					x = Character.toUpperCase(x);
					buffer.append(x);
				}
			}
			else
			{
				if(x == '_')
				{
					nextCharUpper = true;
					buffer.append(" ");
				}
				else
				{
					if(nextCharUpper)
					{
						x = Character.toUpperCase(x);
						nextCharUpper = false;
					}
					buffer.append(x);
				}
			}
		}
		
		return buffer.toString();
	}
	
	/**
	 * Change the first letter to upper case
	 * @param str
	 * @return
	 */
	public static String firstCharToUpper(String str)
	{
		if(StringUtils.isNullOrEmpty(str))
		{
			return str;
		}
		
		if(Character.isLowerCase(str.charAt(0)))
		{
			return String.valueOf(str.charAt(0)).toUpperCase() + str.substring(1);
		}
		else
		{
			return str;
		}
	}

	/**
	 * Change the first letter to lower case
	 * @param str
	 * @return
	 */
	public static String firstCharToLower(String str)
	{
		if(StringUtils.isNullOrEmpty(str))
		{
			return str;
		}
		
		if(Character.isUpperCase(str.charAt(0)))
		{
			return String.valueOf(str.charAt(0)).toLowerCase() + str.substring(1);
		}
		else
		{
			return str;
		}
	}


}
