package com.springcaf.core.web.util;

import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.nav.NavUrlItemModel;

public class NavUtils {
	
	/**
	 * Test if an URL is a relative or absolute URL
	 * @param url
	 * @return
	 */
	public static boolean isRelativeUrl(String url)
	{
		if(StringUtils.isNullOrEmpty(url))
		{
			return true;
		}
		if(url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://") || url.toLowerCase().startsWith("ftp://") 
				|| url.toLowerCase().startsWith("mail://") || url.toLowerCase().startsWith("file:///") )
		{
			return false;
		}
		return true;
	}

	/**
	 * Render a NavUrlModel into html code
	 * @param urlModel
	 * @param applicationBaseUrl
	 * @param displayClass
	 * @return
	 */
	public static String rendereNavUrlModelToHtml(NavUrlItemModel urlModel, String applicationBaseUrl, String displayClass)
	{
		return rendereNavUrlModelToHtml(urlModel, applicationBaseUrl, displayClass, null);
	}
	
	/**
	 * Render a NavUrlModel into html code
	 * @param urlModel
	 * @param applicationBaseUrl
	 * @param displayClass
	 * @param clickJsAction
	 * @return
	 */
	public static String rendereNavUrlModelToHtml(NavUrlItemModel urlModel, String applicationBaseUrl, String displayClass, String clickJsAction) 
	{
		StringBuffer buffer = new StringBuffer();
		// display only the text
		if(StringUtils.isNullOrEmpty(urlModel.getUrl()))
		{
			buffer.append("<span");
			String theClass = displayClass;
			if(theClass == null)
			{
				theClass = "";
			}
			if(!StringUtils.isNullOrEmpty(theClass))
			{
				buffer.append(" class=\"" + theClass.trim() + "\"");
			}
			buffer.append(">");
			buffer.append(urlModel.getLabel());
			buffer.append("</span>");
		}
		else
		{
			String thisUrl = urlModel.getUrl();
			if(!StringUtils.isNullOrEmpty(applicationBaseUrl) && NavUtils.isRelativeUrl(thisUrl))
			{
				thisUrl = applicationBaseUrl + thisUrl;
			}
			
			buffer.append("<a href=\"");
			buffer.append(thisUrl);
			buffer.append("\"");
			if(!StringUtils.isNullOrEmpty(displayClass))
			{
				buffer.append(" class=\"" + displayClass + "\"");
			}
			// confirm delete by default label
			if(!StringUtils.isNullOrEmpty(clickJsAction))
			{
				buffer.append(" onclick=\"" + clickJsAction + ";\"");
			}
			buffer.append(">");
			buffer.append(urlModel.getLabel());
			buffer.append("</a>");
		}

		return buffer.toString();
	}

}
