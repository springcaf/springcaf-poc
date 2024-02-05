package com.springcaf.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class DateUtils {

	private static final long CONVERTER_UNIT = 24*3600*1000;
	public static final String DATE_LONG_FORMAT = "MM/dd/yyyy hh:mm:ss a";
	public static final String DATE_STANDARD_FORMAT = "MM/dd/yyyy";
	public static final String DATE_SHORT_FORMAT = "MM/dd/yy";

	/**
	 * Parse a string into a date
	 * @param dateStr
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String dateFormat) throws ParseException
	{
		if(StringUtils.isNullOrEmpty(dateStr))
		{
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		return formatter.parse(dateStr);
	}
	
	/**
	 * Parse a date base on timezone
	 * @param dateStr
	 * @param dateFormat
	 * @param timeZone
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String dateStr, String dateFormat, TimeZone timeZone) throws ParseException
	{
		if(StringUtils.isNullOrEmpty(dateStr))
		{
			return null;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		if(timeZone != null)
		{
			formatter.setTimeZone(timeZone);
		}
		
		return formatter.parse(dateStr);
	}
	
	/**
	 * Convert a Date into a formatted string
	 * @param date
	 * @param dateFormat
	 * @return
	 */
	public static String convertDateToString(Date date, String dateFormat)
	{
		if(date == null)
		{
			return "";
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		
		return formatter.format(date);
	}
	
	/**
	 * Format date with default value
	 * @param date
	 * @param dateFormat
	 * @param defaultValue
	 * @param timeZone
	 * @return
	 */
	public static String convertDateToString(Date date, String dateFormat, String defaultValue, TimeZone timeZone)
	{
		if(date == null)
		{
			return defaultValue;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		if(timeZone != null)
		{
			formatter.setTimeZone(timeZone);
		}
		
		return formatter.format(date);
	}
	
	/**
	 * Add numOfYears to fromDate
	 * @param fromDate
	 * @param numOfYears
	 * @return
	 */
	public static Date addYears(Date fromDate, int numOfYears)
	{
		if(fromDate == null)
		{
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.YEAR, numOfYears);
		Date dateAfter = cal.getTime();
		
		return dateAfter;
	}

	/**
	 * Add numOfMonths to fromDate
	 * @param fromDate
	 * @param numOfMonths
	 * @return
	 */
	public static Date addMonths(Date fromDate, int numOfMonths)
	{
		if(fromDate == null)
		{
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.MONTH, numOfMonths);
		Date dateAfter = cal.getTime();
		
		return dateAfter;
	}

	/**
	 * Add numOfDays to fromDate
	 * @param fromDate
	 * @param numOfDays
	 * @return
	 */
	public static Date addDays(Date fromDate, int numOfDays)
	{
		if(fromDate == null)
		{
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.DATE, numOfDays);
		Date dateAfter = cal.getTime();
		
		return dateAfter;
	}

	/**
	 * Add numOfHours to fromDate
	 * @param fromDate
	 * @param numOfHours
	 * @return
	 */
	public static Date addHours(Date fromDate, int numOfHours)
	{
		if(fromDate == null)
		{
			return null;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.HOUR_OF_DAY, numOfHours);
		Date dateAfter = cal.getTime();
		
		return dateAfter;
	}

	/**
	 * Date1 is x days within date2
	 * @param date1
	 * @param date2
	 * @param xDays
	 * @return
	 */
	public static boolean isWithinDays(Date date1, Date date2, int xDays)
	{
		if(date1 == null || date2 == null)
		{
			return false;
		}
		if(addDays(date1, xDays).compareTo(date2) >= 0)
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Date1 is x hours within date2
	 * @param date1
	 * @param date2
	 * @param xHours
	 * @return
	 */
	public static boolean isWithinHours(Date date1, Date date2, int xHours)
	{
		if(date1 == null || date2 == null)
		{
			return false;
		}
		if(addHours(date1, xHours).compareTo(date2) >= 0)
		{
			return true;
		}
		
		return false;
	}
	
	public static Date truncateDate(Date inDate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(inDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}
	
	public static Long daysFromToday(Date date1)
	{
		if(date1 == null)
		{
			return null;
		}
		
		Date today = truncateDate(new Date());
		
		return dateDiff(today, date1);
	}
	
	public static Long daysToToday(Date date1)
	{
		if(date1 == null)
		{
			return null;
		}
		
		Date today = truncateDate(new Date());
		
		return dateDiff(date1, today);
	}
	
	public static Date truncateAndaddHours(Date inDate, int hours)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(inDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		
		return cal.getTime();
	}

	/**
	 * Calculates toDate - fromDate
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static Long dateDiff(Date fromDate, Date toDate)
	{
		if(fromDate == null || toDate == null)
		{
			return null;
		}
		
		long result = (toDate.getTime() - fromDate.getTime())/CONVERTER_UNIT;
		
		return result;
	}
	
	public static boolean betweenDates(Date inDate, Date fromDate, Date toDate)
	{
		if(inDate == null)
		{
			return false;
		}
		
		if(fromDate == null)
		{
			fromDate = addDays(inDate, -1);
		}
		if(toDate == null)
		{
			toDate = addDays(inDate, 1);
		}
		
		if((fromDate.getTime() - inDate.getTime() <= 0) && ((toDate.getTime() - inDate.getTime() > 0)))
		{
			return true;
		}
		
		return false;
	}
	
	public static String getFriendlyTimestamp(Date inputDate, TimeZone timeZone)
	{
		if(inputDate == null)
		{
			return "time unknown";
		}
		
		Date now = new Date();
		
		if(inputDate.compareTo(now) > 0)
		{
			return DateUtils.convertDateToString(inputDate, DateUtils.DATE_STANDARD_FORMAT, "", timeZone);
		}
		
		if(DateUtils.isWithinDays(inputDate, now, 1))
		{
			long diff = now.getTime() - inputDate.getTime();
			if(DateUtils.isWithinHours(inputDate, now, 1))
			{
				long min = (diff/60000);
				if(min <= 1)
				{
					return min + " minute ago";
				}
				else
				{
					return min + " minutes ago";
				}
			}
			else
			{
				long hour = (diff/3600000);
				if(hour <= 1)
				{
					return hour + " hour ago";
				}
				else
				{
					return hour + " hours ago";
				}
			}
		}
		
		return DateUtils.convertDateToString(inputDate, DateUtils.DATE_STANDARD_FORMAT, "", timeZone);
	}
	
	public static boolean valueChanged(Date fromDate, Date toDate)
	{
		if(fromDate == null && toDate == null)
		{
			return false;
		}
		else if(fromDate == null || toDate == null)
		{
			return true;
		}
		else
		{
			return fromDate.compareTo(toDate) != 0;
		}
	}

	public static int getThisYear()
	{
		return getYear(new Date());
	}
	
	/**
	 * Get the year from input date
	 * @param date
	 * @return
	 */
	public static int getYear(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		
		return year;
	}
	
	/**
	 * Get month from input date
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);

		return month + 1;
	}
	
	/**
	 * Get day of month from input date
	 * @param date
	 * @return
	 */
	public static int getDayOfMonth(Date date)
	{
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

		return dayOfMonth;
	}
	
	public static Date getNextSemiMonthly(Date inDate)
	{
		if(inDate == null)
		{
			return null;
		}
		
		// input date
		Calendar cal = Calendar.getInstance();
		cal.setTime(inDate);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		// last day of month
		int lastDayOfMonth = getLastDayOfMonth(inDate);
		
		// first or second of month
		boolean firstHalf = true;
		if(day > 15)
		{
			firstHalf = false;
		}
		
		// get next date
		if(firstHalf)
		{
			int newDayVal = day + (lastDayOfMonth-15);
			if(newDayVal < 16)
			{
				newDayVal = 16;
			}
			cal.set(Calendar.DAY_OF_MONTH, newDayVal);
		}
		else
		{
			int newDayVal = 15 - (lastDayOfMonth - day);
			if(newDayVal < 1)
			{
				newDayVal = 1;
			}
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, newDayVal);
		}
		
		Date nextDate = cal.getTime();
		return nextDate;
	}

	public static int getLastDayOfMonth(Date inDate)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(inDate);
		
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static Date getThisMonday()
	{
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Date temp = c.getTime();
		if(temp.getTime() > (new Date()).getTime())
		{
			temp = addDays(temp, -7);
		}
		
		return truncateDate(temp);
	}
	
	public static Date getBeginningMonday(Date anchorDate)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(anchorDate);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		Date temp = c.getTime();
		if(temp.getTime() > anchorDate.getTime())
		{
			temp = addDays(temp, -7);
		}
		
		return truncateDate(temp);
	}

}
