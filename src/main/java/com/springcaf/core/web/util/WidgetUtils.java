package com.springcaf.core.web.util;

import org.apache.commons.text.StringEscapeUtils;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.model.KeyValueModel;

public class WidgetUtils {

	public static final String LINE_FEED = "\r\n";

	/**
	 * Create a URL representation in HTML.
	 * @param label
	 * @param url
	 * @return
	 */
	public static String createUrl(String label, String url)
	{
		return buildUrlLink(label, url, null, null, null) ;
	}
	
	/**
	 * Create a URL representation in HTML.
	 * @param label
	 * @param url
	 * @return
	 */
	public static String createUrlNewWindow(String label, String url)
	{
		return buildUrlLink(label, url, null, null, "_blank") ;
	}
	
	/**
	 * Handle ? or & based on the base Url 
	 * @param baseUrl
	 * @param parmsSet - pre-built array of name=value 
	 * @return
	 * @throws SpringcafException
	 */
	public static String buildUrlWithParms(String baseUrl, String[] parmsSet) throws SpringcafException
	{
		if(parmsSet == null || parmsSet.length == 0)
		{
			return baseUrl;
		}
		
		StringBuffer buffer = new StringBuffer();
		for(String parmString : parmsSet)
		{
			buffer.append("&" + parmString);
		}
		
		if(baseUrl.indexOf("?") > 0)
		{
			return baseUrl + "&" + buffer.toString().substring(1);
		}
		else
		{
			return baseUrl + "?" + buffer.toString().substring(1);
		}
		
	}
	
	/**
	 * Build a URL link
	 * @param label
	 * @param url
	 * @param displayClass
	 * @param clickJsAction
	 * @return
	 */
	public static String createUrl(String label, String url, String displayClass, String clickJsAction)
	{
		return buildUrlLink(label, url, displayClass, clickJsAction, null);
	}
	
	/**
	 * Create an icon-based URL
	 * @param iconStr
	 * @param title
	 * @param url
	 * @param displayClass
	 * @return
	 */
	public static String createIconButtonUrl(String iconStr, String title, String url, String displayClass)
	{
		return createIconButtonUrl(iconStr, title, url, displayClass, null);
	}
	
	/**
	 * Create an icon-based URL
	 * @param iconStr
	 * @param title
	 * @param url
	 * @param displayClass
	 * @param clickJsAction
	 * @return
	 */
	public static String createIconButtonUrl(String iconStr, String title, String url, String displayClass, String clickJsAction)
	{
		if(!StringUtils.isNullOrEmpty(displayClass))
		{
			iconStr += " " + displayClass;
		}
		String label = "<i class=\"" + iconStr + "\" title=\"" + title + "\"></i>";

		return createUrl(label, url, null, clickJsAction);
	}
	
	/**
	 * Build a URL link
	 * @param label
	 * @param url
	 * @param displayClass
	 * @param clickJsAction
	 * @return
	 */
	private static String buildUrlLink(String label, String url, String displayClass, String clickJsAction, String target)
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("<a href=\"");
		buffer.append(url);
		buffer.append("\"");
		// display class
		if(!StringUtils.isNullOrEmpty(displayClass))
		{
			buffer.append(" class=\"" + displayClass + "\"");
		}
		// confirm delete by default label
		if(!StringUtils.isNullOrEmpty(clickJsAction))
		{
			buffer.append(" onclick=\"" + clickJsAction + ";\"");
		}
		// target
		if(!StringUtils.isNullOrEmpty(target))
		{
			buffer.append(" target=\"" + target + "\"");
		}
		buffer.append(">");
		buffer.append(label);
		buffer.append("</a>");
		
		return buffer.toString();
	}
	
	
	/**
	 * Wrap a string after a certain limit
	 * @param str
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String wrapHtmlString(String str, int limit)
	{
		return org.apache.commons.lang3.text.WordUtils.wrap(str, limit, "<br />", true, " ");
	}

	/**
	 * Render list into HTML option string
	 * @param listValues
	 * @param addBlankRow
	 * @param currentValue
	 * @return
	 */
	public static void renderDropdownListOptions(StringBuffer buffer, KeyValueListModel listValues, boolean addBlankRow, String currentValue)
	{
		// build the list values
		if(addBlankRow)
		{
			buffer.append("<option value=\"\"></option>");
		}
		if(listValues != null && listValues.getItems() != null)
		{
			for(KeyValueModel item : listValues.getItems())
			{
				if(item.isActive())
				{
					String key = item.getKey();
					String value = item.getValue();
					if(key == null)
					{
						key = "";
					}
					if(value == null)
					{
						value = "";
					}
					String selected = "";
					if(key.equals(currentValue))
					{
						selected = " selected";
					}
					buffer.append("<option value=\"" + key + "\"" + selected + ">" + value + "</option>" + LINE_FEED);
				}
			}
		}
	}
	
	public static void renderDropdownListOptionsWithSelectTag(StringBuffer buffer, KeyValueListModel listValues, boolean addBlankRow, String currentValue, String selectStyle)
	{
		String classStr = "";
		if(!StringUtils.isNullOrEmpty(selectStyle))
		{
			classStr = " class=\"" + selectStyle + "\"";
		}
		buffer.append("<select " + classStr + ">");
		WidgetUtils.renderDropdownListOptions(buffer, listValues, addBlankRow, currentValue);
		buffer.append("</select>");
	}
	
	/**
	 * Convert a list into HTML option string
	 * @param listValues
	 * @param addBlankRow
	 * @param currentValue
	 * @return
	 */
	public static String renderDropdownListOptions(KeyValueListModel listValues, boolean addBlankRow, String currentValue)
	{
		StringBuffer buffer = new StringBuffer();
		
		renderDropdownListOptions(buffer, listValues, addBlankRow, currentValue);
		
		return buffer.toString();
	}

	public static String htmlEncode(String str)
	{
		return StringEscapeUtils.escapeHtml4(str);
	}
}
