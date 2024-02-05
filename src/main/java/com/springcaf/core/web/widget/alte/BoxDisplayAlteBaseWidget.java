package com.springcaf.core.web.widget.alte;

import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.widget.AbstractWidget;

public abstract class BoxDisplayAlteBaseWidget extends AbstractWidget {

	protected WebAppMeta webAppMeta = null;
	private boolean showHeader = false;
	private boolean showBody = false;
	private boolean showFooter = false;
	private String boxStyle = null;
	private String boxHeaderStyle = null;
	private String boxBodyStyle = null;
	private String boxFooterStyle = null;
	private int bodyHeight = 0;
	
	/**
	 * Constructor
	 * @param webAppMeta
	 * @param boxStyle
	 * @param showHeader
	 * @param showBody
	 * @param showFooter
	 */
	public BoxDisplayAlteBaseWidget(WebAppMeta webAppMeta, 
			String boxStyle, 
			boolean showHeader, 
			boolean showBody,
			boolean showFooter) {
		super(webAppMeta.getRequest().getContextPath());
		
		this.webAppMeta = webAppMeta;
		this.boxStyle = boxStyle;
		this.showHeader = showHeader;
		this.showBody = showBody;
		this.showFooter = showFooter;
	}

	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {

		// create box <div>
		buffer.append("<div class=\"box");
		if(!StringUtils.isNullOrEmpty(boxStyle))
		{
			buffer.append(" " + boxStyle);
		}
		buffer.append("\">" + LINE_FEED);

		// header 
		if(showHeader)
		{
			buffer.append("<div class=\"box-header");
			if(!StringUtils.isNullOrEmpty(boxHeaderStyle))
			{
				buffer.append(" " + boxHeaderStyle);
			}
			buffer.append("\">" + LINE_FEED);

			addBoxHeader(buffer);
			buffer.append("</div>" + LINE_FEED);
		}
		if(showBody)
		{
			buffer.append("<div class=\"box-body\"");
			if(!StringUtils.isNullOrEmpty(boxBodyStyle))
			{
				buffer.append(" " + boxBodyStyle);
			}
			if(bodyHeight > 0)
			{
				buffer.append(" style=\"min-height: " + bodyHeight + "px;\"");
			}
			buffer.append(">" + LINE_FEED);

			addBoxBodyWrapper(buffer);
			buffer.append("</div>" + LINE_FEED);
		}
		if(showFooter)
		{
			buffer.append("<div class=\"box-footer");
			if(!StringUtils.isNullOrEmpty(boxFooterStyle))
			{
				buffer.append(" " + boxFooterStyle);
			}
			buffer.append("\">" + LINE_FEED);

			addBoxFooter(buffer);
			buffer.append("</div>" + LINE_FEED);
		}
		
		// create the end <div>
		buffer.append("</div>" + LINE_FEED);
	}

	protected void addBoxHeader(StringBuffer buffer) throws SpringcafException {
		// no content by default
	}
	
	protected void addBoxBodyWrapper(StringBuffer buffer) throws SpringcafException
	{
		this.addBoxBody(buffer);
	}
	
	// force creation of body content in sub classes
	abstract protected void addBoxBody(StringBuffer buffer) throws SpringcafException;
	
	protected void addBoxFooter(StringBuffer buffer) throws SpringcafException {
		// no content by default
	}
	
	protected void addBoxTitle(StringBuffer buffer, String title, String iconClass, String rightItem)
	{
		this.addBoxTitle(buffer, title, null, iconClass, rightItem);
	}

	protected void addBoxTitle(StringBuffer buffer, String title, String subTitle, String iconClass, String rightItem)
	{
		buffer.append("<h3 class=\"box-title\">");
		if(!StringUtils.isNullOrEmpty(iconClass))
		{
			buffer.append("<i class=\"" + iconClass + "\"></i> ");
		}
		buffer.append(title + "</h3>");
		
		if(!StringUtils.isNullOrEmpty(subTitle))
		{
			buffer.append("<span>" + FILLER2 + subTitle + "</span>");
		}

		if(!StringUtils.isNullOrEmpty(rightItem))
		{
			buffer.append("<span class=\"pull-right\">" + rightItem + "</span>");
		}

		buffer.append(LINE_FEED);
	}

	public int getBodyHeight() {
		return bodyHeight;
	}

	public void setBodyHeight(int bodyHeight) {
		this.bodyHeight = bodyHeight;
	}

	public void setBoxHeaderStyle(String boxHeaderStyle)
	{
		this.boxHeaderStyle = boxHeaderStyle;
	}
	
	public void setBoxBodyStyle(String boxBodyStyle)
	{
		this.boxBodyStyle = boxBodyStyle;
	}
	
	public void setBoxFooterStyle(String boxFooterStyle)
	{
		this.boxFooterStyle = boxFooterStyle;
	}

	public HttpServletRequest getRequest() {
		return this.webAppMeta.getRequest();
	}

	public TimeZone getUserTimeZone()
	{
		return this.webAppMeta.getUserTimeZone();
	}
	
	public String getEncodedUserId()
	{
		return this.webAppMeta.getEncodedUserId();
	}
}
