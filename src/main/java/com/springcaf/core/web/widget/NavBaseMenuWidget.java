package com.springcaf.core.web.widget;

import com.springcaf.core.web.model.nav.NavUrlItemModel;

public abstract class NavBaseMenuWidget extends AbstractWidget {
	
	/**
	 * Constructor
	 * @param applicationBaseUrl
	 */
	public NavBaseMenuWidget(String applicationBaseUrl)
	{
		super(applicationBaseUrl);
	}

	protected abstract void renderNavMenuItem(StringBuffer buffer, NavUrlItemModel menuItem, String activeItemId);
}
