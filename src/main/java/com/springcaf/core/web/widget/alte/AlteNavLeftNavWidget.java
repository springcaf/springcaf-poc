package com.springcaf.core.web.widget.alte;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.model.nav.NavSectionModel;
import com.springcaf.core.web.model.nav.NavUrlItemModel;

public class AlteNavLeftNavWidget extends AlteNavMenuWidget {
	
	private List<NavSectionModel> sectionList = new ArrayList<NavSectionModel>();
	private String activeItemId = null;
	
	/**
	 * Constructor
	 * @param applicationBaseUrl
	 * @param activeItemId
	 */
	public AlteNavLeftNavWidget(String applicationBaseUrl, String activeItemId)
	{
		super(applicationBaseUrl);
		
		this.activeItemId = activeItemId;
	}

	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {
		
		// leftnav block HTML
		buffer.append("<section class=\"sidebar\">" + LINE_FEED);
		buffer.append("<ul class=\"sidebar-menu\" data-widget=\"tree\">" + LINE_FEED);

		for(NavSectionModel section : sectionList)
		{
			// first line is the menu section header
			buffer.append("<li class=\"header\">" + section.getSectionName() + "</li>" + LINE_FEED);
	        
	        for(NavUrlItemModel menuItem : section.getMenuList())
	        {
	        	renderNavMenuItem(buffer, menuItem, activeItemId);
	        }
		}

        // close leftnav block HTML
        buffer.append("</ul>" + LINE_FEED);
        buffer.append("</section>" + LINE_FEED);
	}

	/**
	 * 
	 * @param buffer
	 * @param menuItem
	 * @param activeItemId
	 */
	@Override
	protected void renderNavMenuItem(StringBuffer buffer, NavUrlItemModel menuItem, String activeItemId)
	{
		if(menuItem.hasChildren())
		{
			String liClass = "class=\"treeview\"";
			if(menuItem.isAnyChildrenItemIdMatch(activeItemId))
			{
				liClass = " class=\"active treeview menu-open\" ";
			}
			buffer.append("<li " + liClass + ">" + LINE_FEED);
			
			// add the URL
			String postLabel = "<span class=\"pull-right-container\"><i class=\"fa fa-angle-left pull-right\"></i></span>";
			this.buildMenuItemNavUrl(buffer, menuItem, "fa fa-circle-o", "#", postLabel, true);
			buffer.append(LINE_FEED);

			buffer.append("<ul class=\"treeview-menu\">" + LINE_FEED);

			for(NavUrlItemModel child : menuItem.getChildItemList())
			{
				renderNavMenuItem(buffer, child, activeItemId);
			}
			
			buffer.append("</ul>" + LINE_FEED);
			buffer.append("</li>" + LINE_FEED);
		}
		else if(menuItem.isSeparator())
		{
			// do nothing for leftnav
		}
		else
		{
			String liClass = "";
			if(menuItem.isAnyChildrenItemIdMatch(activeItemId))
			{
				liClass = " class=\"active\" ";
			}
	        
	        buffer.append("<li" + liClass + ">" + LINE_FEED);
	        
			// add the URL
			this.buildMenuItemNavUrl(buffer, menuItem, "fa fa-circle-o", null, null, true);
			buffer.append(LINE_FEED);

			buffer.append("</li>" + LINE_FEED);
		}
	}

	public List<NavSectionModel> getSectionList() {
		return sectionList;
	}

	public void setSectionList(List<NavSectionModel> sectionList) {
		this.sectionList = sectionList;
	}

	public String getActiveItemId() {
		return activeItemId;
	}

	public void setActiveItemId(String activeItemId) {
		this.activeItemId = activeItemId;
	}

}
