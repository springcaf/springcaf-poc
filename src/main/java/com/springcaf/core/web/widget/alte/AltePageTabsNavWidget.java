package com.springcaf.core.web.widget.alte;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.model.nav.PageNavTabModel;
import com.springcaf.core.web.model.nav.PageNavTabsModel;
import com.springcaf.core.web.widget.AbstractWidget;

public class AltePageTabsNavWidget extends AbstractWidget {
	
	protected PageNavTabsModel tabsModel = null;
	protected int activeTabIndex = 0;
	protected String rightDropdownLabel = "More";
	
	/**
	 * Constructor
	 * @param request
	 * @param activeTabIndex
	 * @param dynamicTabName
	 */
	public AltePageTabsNavWidget(HttpServletRequest request, 
			int activeTabIndex) {
		super(request.getContextPath());
		
		this.tabsModel = new PageNavTabsModel();
		this.activeTabIndex = activeTabIndex;
	}

	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {
		
		// render the fixed tabs
		for(PageNavTabModel tabModel : tabsModel.getFixedTabs())
		{
			String url = this.getApplicationBaseUrl() + tabModel.getTabUrl();
			String link = "<a href=\"" + url + "\" ><b>" + tabModel.getTabName() + "</b></a>";
			if(tabModel.getTabIndex() == this.activeTabIndex)
			{
				buffer.append("<li class=\"active\">" + link + "</li>" + LINE_FEED);
			}
			else
			{
				buffer.append("<li>" + link + "</li>" + LINE_FEED);
			}
		}

		// right dropdown
		if(tabsModel.getRightDropdown() != null && tabsModel.getRightDropdown().size() > 0)
		{
			this.renderDropdownList(buffer, tabsModel.getRightDropdown(), this.rightDropdownLabel, "pull-right");
		}
	}
	
	private void renderDropdownList(StringBuffer buffer, List<PageNavTabModel> menuList, String dropdownLabel, String alignment)
	{
		// dropdown menu items
		buffer.append("<li class=\"dropdown " + alignment + "\">" + LINE_FEED);
		buffer.append("<a class=\"dropdown-toggle\" data-toggle=\"dropdown\" href=\"#\">" + LINE_FEED);
		buffer.append(dropdownLabel + " <span class=\"caret\"></span>" + LINE_FEED);
		buffer.append("</a>" + LINE_FEED);
		buffer.append("<ul class=\"dropdown-menu\">" + LINE_FEED);
		
		for(PageNavTabModel tabModel : menuList)
		{
			String url = this.getApplicationBaseUrl() + tabModel.getTabUrl();
			buffer.append("<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"" + url + "\">" + tabModel.getTabName() + "</a></li>" + LINE_FEED);
		}
		
		buffer.append("</ul>" + LINE_FEED);
		buffer.append("</li>" + LINE_FEED);
	}

	public PageNavTabsModel getTabsModel() {
		return tabsModel;
	}

	public void setTabsModel(PageNavTabsModel tabsModel) {
		this.tabsModel = tabsModel;
	}

	public int getActiveTabIndex() {
		return activeTabIndex;
	}

	public void setActiveTabIndex(int activeTabIndex) {
		this.activeTabIndex = activeTabIndex;
	}
}
