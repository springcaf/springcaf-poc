package com.springcaf.core.web.widget;

import org.springframework.security.web.csrf.CsrfToken;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;

/**
 * The entity interface and its implementation is used as a renderer
 * to implement a model with its proper implementation (rendering)
 *
 */
public abstract class AbstractWidget implements WidgetConstants {
	
	protected String applicationBaseUrl = null;
	protected boolean editMode = false;
	protected CsrfToken csrfToken = null;
	
	/**
	 * Constructor
	 * @param applicationBaseUrl
	 */
	public AbstractWidget(String applicationBaseUrl)
	{
		this.applicationBaseUrl = applicationBaseUrl;
	}

	public abstract void renderToHtml(StringBuffer buffer) throws SpringcafException;
	
	/**
	 * Render to HTML
	 * @return
	 * @throws SpringcafException
	 */
	public String renderToHtml() throws SpringcafException
	{
		StringBuffer buffer = new StringBuffer();
		
		this.renderToHtml(buffer);
		
		return buffer.toString();
	}

	public String getApplicationBaseUrl() {
		return applicationBaseUrl;
	}

	public void setApplicationBaseUrl(String applicationBaseUrl) {
		this.applicationBaseUrl = applicationBaseUrl;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public AbstractWidget setEditMode(boolean editMode) {
		this.editMode = editMode;
		
		return this;
	}

	public CsrfToken getCsrfToken() {
		return csrfToken;
	}

	public AbstractWidget setCsrfToken(CsrfToken csrfToken) {
		this.csrfToken = csrfToken;
		
		return this;
	}
	
	protected void startDiv(StringBuffer buffer, String divClass)
	{
		buffer.append("<div");
		if(!StringUtils.isNullOrEmpty(divClass))
		{
			buffer.append(" class=\"" + divClass + "\"");
		}
		buffer.append(">" + LINE_FEED);
	}
	
	protected void closeDiv(StringBuffer buffer)
	{
		buffer.append("</div>" + LINE_FEED);
	}
	
	protected void startTable(StringBuffer buffer, String tableClass)
	{
		buffer.append("<table");
		if(!StringUtils.isNullOrEmpty(tableClass))
		{
			buffer.append(" class=\"" + tableClass + "\"");
		}
		buffer.append(">" + LINE_FEED);
	}
	
	protected void closeTable(StringBuffer buffer)
	{
		buffer.append("</table>" + LINE_FEED);
	}
	
	protected void startTr(StringBuffer buffer, String trClass)
	{
		buffer.append("<tr");
		if(!StringUtils.isNullOrEmpty(trClass))
		{
			buffer.append(" class=\"" + trClass + "\"");
		}
		buffer.append(">" + LINE_FEED);
	}
	
	protected void closeTr(StringBuffer buffer)
	{
		buffer.append("</tr>" + LINE_FEED);
	}
	
	protected void startTd(StringBuffer buffer)
	{
		buffer.append("<td>");
	}
	
	protected void closeTd(StringBuffer buffer)
	{
		buffer.append("</td>");
	}

	protected void addTd(StringBuffer buffer, String cellValue)
	{
		this.addTd(buffer, cellValue, null);
	}
	
	protected void addTdBold(StringBuffer buffer, String cellValue)
	{
		if(StringUtils.isNullOrEmpty(cellValue) || "null".equals(cellValue.trim()))
		{
			cellValue = "";
		}
		this.addTd(buffer, "<b>" + cellValue + "</b>", null);
	}
	
	protected void addTd(StringBuffer buffer, String cellValue, String cellClass)
	{
		if(StringUtils.isNullOrEmpty(cellValue) || "null".equals(cellValue.trim()))
		{
			cellValue = "";
		}
		if(StringUtils.isNullOrEmpty(cellClass))
		{
			buffer.append("<td>" + cellValue + "</td>" + LINE_FEED);
		}
		else
		{
			buffer.append("<td class=\"" + cellClass + "\">" + cellValue + "</td>" + LINE_FEED);
		}
	}

	protected void addTh(StringBuffer buffer, String value)
	{
		buffer.append("<th>" + value + "</th>");
	}
	
	protected void addTh(StringBuffer buffer, String value, String styleText)
	{
		buffer.append("<th style=\"" + styleText + "\">" + value + "</th>");
	}
	
}
