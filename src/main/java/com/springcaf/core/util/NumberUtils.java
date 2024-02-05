package com.springcaf.core.util;

import java.text.DecimalFormat;

public final class NumberUtils {
	
	private static final DecimalFormat amountCommaFormatter = new DecimalFormat("#,###.00");
	private static final DecimalFormat integerCommaFormatter = new DecimalFormat("#,###");
	
	/**
	 * Parse integer value
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static int parseInt(String str, int defaultValue)
	{
		int ret = defaultValue;
		try
		{
			ret = Integer.parseInt(str);
		}
		catch(Exception ex)
		{
			ret = defaultValue;
		}

		return ret;
	}
	
	/**
	 * Format an amount into a 2 decimal value as a String
	 * @param amount
	 * @return
	 */
	public static String formatValueAsAmount(Object amount)
	{
		if(amount == null)
		{
			return "";
		}
		if(amount instanceof Integer)
		{
			return amountCommaFormatter.format((Integer)amount);
		}
		else if(amount instanceof Long)
		{
			return amountCommaFormatter.format((Long)amount);
		}
		else if(amount instanceof Double)
		{
			return amountCommaFormatter.format((Double)amount);
		}
		else if(amount instanceof String)
		{
			return amountCommaFormatter.format(Double.parseDouble(amount.toString()));
		}
		else
		{
			return amount.toString();
		}
	}

	/**
	 * Format an integer into a comma String
	 * @param intNum
	 * @return
	 */
	public static String formatValueAsInteger(Object intNum)
	{
		if(intNum == null)
		{
			return "";
		}
		if(intNum instanceof Integer)
		{
			return integerCommaFormatter.format((Integer)intNum);
		}
		else if(intNum instanceof Long)
		{
			return integerCommaFormatter.format((Long)intNum);
		}
		else if(intNum instanceof Double)
		{
			return integerCommaFormatter.format((Double)intNum);
		}
		else if(intNum instanceof String)
		{
			return integerCommaFormatter.format(Double.parseDouble(intNum.toString()));
		}
		else
		{
			return intNum.toString();
		}
	}

	/**
	 * Format a value as percent with 2 decimal points
	 * @param pctNum
	 * @return
	 */
	public static String formatValueAsPercent(Object pctNum)
	{
		if(pctNum == null)
		{
			return "";
		}
		if(pctNum instanceof Integer)
		{
			return String.format("%.2f", ((Integer)pctNum).doubleValue() * 100);
		}
		else if(pctNum instanceof Long)
		{
			return String.format("%.2f", ((Long)pctNum).doubleValue() * 100);
		}
		else if(pctNum instanceof Double)
		{
			return String.format("%.2f", ((Double)pctNum).doubleValue() * 100);
		}
		else if(pctNum instanceof String)
		{
			return String.format("%.2f", Double.parseDouble(pctNum.toString()) * 100);
		}
		else
		{
			return pctNum.toString();
		}
	}

	/**
	 * Get an integer percentage from two integer numbers
	 * @param num
	 * @param total
	 * @return
	 */
	public static int getPercentValue(int num, int total)
	{
		if(total == 0)
		{
			return 0;
		}
		
		return Double.valueOf(100.0 * num/total).intValue();
	}	

	/**
	 * Convert a Long value to an Integer value
	 * @param value
	 * @return
	 */
	public static int getIntValue(Long value)
	{
		if(value == null)
		{
			return 0;
		}
		
		return value.intValue();
	}
	
	/**
	 * Get integer value from double
	 * @param value
	 * @return
	 */
	public static int getIntValue(Double value)
	{
		if(value == null)
		{
			return 0;
		}
		
		return value.intValue();
	}
	
	
}
