package com.springcaf.starter.app.nav;

import com.springcaf.core.web.model.nav.NavUrlItemModel;

public interface MainNavUrlConstants {
	
	public String URL_PAGE_1 = "/page1";
	public String URL_PAGE_2 = "/page2";
	public String URL_DROPDOWN_REFRESH_API = "/dropdownlistrefresh";
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Top level menus
	/////////////////////////////////////////////////////////////////////////////////////////
	NavUrlItemModel APPLICATION_TOP_PAGE_1 = new NavUrlItemModel("Page1", URL_PAGE_1);
	NavUrlItemModel APPLICATION_TOP_PAGE_2 = new NavUrlItemModel("Page2", URL_PAGE_2 + "/0");
	
	/////////////////////////////////////////////////////////////////////////////////////////
	// Application
	/////////////////////////////////////////////////////////////////////////////////////////
	NavUrlItemModel APPLICATION_HOME_MENU = new NavUrlItemModel("Home", "/");
	// sub menu item
	NavUrlItemModel APP_SUB_PROFILE_MENU = new NavUrlItemModel("Profile", "/profile");
	NavUrlItemModel APP_SUB_LOGIN_MENU = new NavUrlItemModel("Login", "/login");
	NavUrlItemModel APP_SUB_ERROR_MENU = new NavUrlItemModel("Error", "/error");
	NavUrlItemModel APP_SUB_INPROGRESS_PAGE_MENU = new NavUrlItemModel("In Progress", "/inprogress");
	NavUrlItemModel APP_SUB_NO_ACCESS_PAGE_MENU = new NavUrlItemModel("No Access", "/noaccess");
	
}
