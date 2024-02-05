package com.springcaf.core.web.widget.alte;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.widget.PageWidget;
import com.springcaf.core.web.widget.WebWidgetStyle;

public class AltePageWidget extends PageWidget {
	
	private WebWidgetStyle webWidgetStyle = WebWidgetStyle.LEFT_MAIN_NAV;

	/**
	 * Constructor
	 * @param applicationBaseUrl
	 * @param webWidgetStyle
	 */
	public AltePageWidget(String applicationBaseUrl, WebWidgetStyle webWidgetStyle) {
		super(applicationBaseUrl);
		
		this.webWidgetStyle = webWidgetStyle;
	}
	
	

	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRenderedHtmlMainNav() throws SpringcafException 
	{
		AlteNavMenuWidget navMenuWidget = null;
		if(webWidgetStyle == WebWidgetStyle.LEFT_MAIN_NAV)
		{
			navMenuWidget = new AlteNavLeftNavWidget(applicationBaseUrl, this.getCurrentNavId());
		}
		else if(webWidgetStyle == WebWidgetStyle.TOP_MAIN_NAV)
		{
			navMenuWidget = new AlteNavTopNavWidget(applicationBaseUrl, this.getCurrentNavId());
		}

		if(navMenuWidget != null)
		{
			navMenuWidget.setSectionList(mainNavModel);
			return navMenuWidget.renderToHtml();
		}
		else
		{
			return "";
		}
	}

}
