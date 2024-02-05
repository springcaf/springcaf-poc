package com.springcaf.core.util;

import java.util.List;
import java.util.UUID;

import org.apache.commons.text.StringEscapeUtils;

public final class StringUtils {
	
	/**
	 * Test if a string is null or empty
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmpty(String str)
	{
		if(str == null || str.trim().equals(""))
		{
			return true;
		}
		
		return false;
	}

	public static boolean isArrayNullOrEmpty(String[] array)
	{
		if(array == null || array.length == 0)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Generate UUID
	 * @return
	 */
	public static String generateUUID()
	{
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Trim the last occurance of a string block from the input string. Used to trim generated code
	 * @param str
	 * @return
	 */
	public static String trimLastStringBlock(String str, String block)
	{
		if(str == null || !str.trim().endsWith(block))
		{
			return str;
		}
		
		int pos = str.lastIndexOf(block);
		if(pos > 0)
		{
			return str.substring(0, pos);
		}
		
		return str;
	}
	
	/**
	 * Check if a string is in a list of strings
	 * @param field
	 * @param skipList
	 * @return
	 */
	public static boolean inStringList(String field, String[] strList)
	{
		if(strList == null || field == null)
		{
			return false;
		}
		for(String tmp : strList)
		{
			if(field.equals(tmp))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Collapse a string array into a delimited string
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String collapseStringArray(String[] array, String separator)
	{
		if(array == null)
		{
			return null;
		}
		
		StringBuffer buffer = new StringBuffer();
		int counter = 0;
		for(String part : array)
		{
			if(part == null)
			{
				part = "";
			}
			buffer.append(part);
			if(counter < array.length - 1)
			{
				buffer.append(separator);
			}
				
			counter++;
		}
		
		return buffer.toString();
	}
	
	/**
	 * Turn a common true value in a string into a boolean value of Boolean type
	 * @param boolVal
	 * @return
	 */
	public static Boolean StringToBoolean(String boolVal)
	{
		if(!isNullOrEmpty(boolVal) && (boolVal.equalsIgnoreCase("on") || 
				boolVal.equalsIgnoreCase("Y") || 
				boolVal.equalsIgnoreCase("Yes") || 
				boolVal.equalsIgnoreCase("true") || 
				boolVal.equalsIgnoreCase("1")))
		{
			return true;
		}
		
		return false;
	}
	
	public static String flattenStringArray(String[] strArray, String prefix, String postfix, String separater)
	{
		if(strArray == null)
		{
			return null;
		}
		if(strArray.length == 0)
		{
			return prefix + postfix;
		}
			
		StringBuffer buffer = new StringBuffer();
		for(String str : strArray)
		{
			buffer.append(prefix + str + postfix + separater);
		}
		
		return StringUtils.trimLastStringBlock(buffer.toString(), separater);
	}
	
	public static boolean valueChanged(String fromStr, String toStr)
	{
		if(StringUtils.isNullOrEmpty(fromStr) && StringUtils.isNullOrEmpty(toStr))
		{
			return false;
		}
		else if(StringUtils.isNullOrEmpty(fromStr) || StringUtils.isNullOrEmpty(toStr))
		{
			return true;
		}
		else
		{
			return !fromStr.equals(toStr);
		}
	}
	
	/**
	 * Remove quotes from input str
	 * @param str
	 * @return
	 */
	public static String removeQuotes(String str)
	{
		if(str == null)
		{
			return null;
		}
		
		return str.replaceAll("'", "").replaceAll("\"", "");
	}
	
	/**
	 * Remove HTML tags from input str
	 * @param str
	 * @return
	 */
	public static String removeHtmlTags(String str)
	{
		if(str == null)
		{
			return null;
		}
		
		return str.replaceAll("\\<.*?\\>", "");
	}
	
	/**
	 * Escape HTML tags
	 * @param str
	 * @return
	 */
	public static String escapeHtmlTags(String str)
	{
		return StringEscapeUtils.escapeHtml4(str);
	}
	
	/**
	 * Convert a String list into a String array
	 * @param list
	 * @return
	 */
	public static String[] convertListToArray(List<String> list)
	{
		if(list == null || list.size() == 0)
		{
			return null;
		}
		
        String[] arr = new String[list.size()];
        arr = list.toArray(arr);
        
        return arr;
	}
	
}
