package com.springcaf.starter.app.nav;

import com.springcaf.core.web.model.nav.NavSectionModel;

public class MainTopNavSection extends NavSectionModel implements MainNavUrlConstants {
	
	/**
	 * Constructor
	 */
	public MainTopNavSection()
	{
		super("TOPNAV");

		///////////////////////////////////////////////////
		// Top menu
		///////////////////////////////////////////////////
		this.addNavUrlItem(MainNavUrlConstants.APPLICATION_TOP_PAGE_1);
		this.addNavUrlItem(MainNavUrlConstants.APPLICATION_TOP_PAGE_2);
	}
}
