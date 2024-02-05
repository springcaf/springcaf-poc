package com.springcaf.starter.app.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.nav.NavUrlItemModel;
import com.springcaf.core.web.util.NavUtils;
import com.springcaf.core.web.util.WidgetUtils;
import com.springcaf.core.web.widget.PageWidget;
import com.springcaf.core.web.widget.WebWidgetStyle;
import com.springcaf.core.web.widget.alte.AltePageWidget;
import com.springcaf.starter.app.nav.MainNavUrlConstants;
import com.springcaf.starter.app.nav.MainTopNavSection;

public class ApplicationFrameworkService {
	
    /**
	 * Constructor
	 */
	public ApplicationFrameworkService()
	{
		
	}
	
	/**
	 * Initialize pageWidget
	 * @param request
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	private PageWidget getPageWidget(HttpServletRequest request) throws SpringcafException, SQLException
	{
		PageWidget pageWidget = new AltePageWidget(request.getContextPath(), WebWidgetStyle.TOP_MAIN_NAV);

		return pageWidget;
	}

	/**
	 * Setup the page widget
	 * @param model
	 * @param contextPath
	 * @param request
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IOException 
	 */
	public void setupPageWidget(Model model, HttpServletRequest request, HttpServletResponse response, NavUrlItemModel currentNav) throws SpringcafException, SQLException, IOException
	{
		if(currentNav == null)
		{
			currentNav = MainNavUrlConstants.APPLICATION_HOME_MENU;
		}
		
		String contextPath = request.getContextPath();
		PageWidget pageWidget = this.getPageWidget(request);

		pageWidget.addNavSectionModel(new MainTopNavSection());
		pageWidget.setCurrentNavId(currentNav.getItemId());
		pageWidget.setUserDisplayName("User Name");
		pageWidget.setTenantDisplayName(WidgetUtils.htmlEncode("Tenant1"));
		model.addAttribute("pageWidget", pageWidget);
		
		// logo text
		String logoText = "Springcaf-POC";
		model.addAttribute("logoText", logoText);

		// pageTitle
		String pageTitle = "Home";
		if(currentNav != null)
		{
			String currentNavLabel = currentNav.getLabel();
			if(!StringUtils.isNullOrEmpty(currentNavLabel))
			{
				pageTitle = currentNavLabel;
				if(!StringUtils.isNullOrEmpty(currentNav.getUrl()) &&
						!MainNavUrlConstants.APPLICATION_HOME_MENU.equals(currentNav))
				{
					pageTitle = NavUtils.rendereNavUrlModelToHtml(currentNav, contextPath, null);
				}

			}
		}
		model.addAttribute("pageTitle", pageTitle);
		
		// breadcrumb
		String breadcrumbStr = "";
		model.addAttribute("breadcrumb", breadcrumbStr);
	}

}
