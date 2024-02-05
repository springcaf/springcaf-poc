package com.springcaf.core.web.widget.alte;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.nav.NavSectionModel;
import com.springcaf.core.web.model.nav.NavUrlItemModel;

public class AlteNavTopNavWidget extends AlteNavMenuWidget {
	
	private List<NavSectionModel> sectionList = new ArrayList<NavSectionModel>();
	private String activeItemId = null;
	
	/**
	 * Constructor
	 * @param applicationBaseUrl
	 * @param activeItemId
	 */
	public AlteNavTopNavWidget(String applicationBaseUrl, String activeItemId)
	{
		super(applicationBaseUrl);
		
		this.activeItemId = activeItemId;
	}

	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {
		
		// topnav block HTML
		for(NavSectionModel section : sectionList)
		{
			// render session items
	        for(NavUrlItemModel menuItem : section.getMenuList())
	        {
	        	renderNavMenuItem(buffer, menuItem, activeItemId);
	        }
	        
			// add additional HTML is appended
			if(!StringUtils.isNullOrEmpty(section.getAdditionalHtml()))
			{
				buffer.append(section.getAdditionalHtml());
			}
		}
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
			buffer.append("<li class=\"dropdown>" + LINE_FEED);
			if(menuItem.isAnyChildrenItemIdMatch(activeItemId))
			{
				buffer.append(" active");
			}
			buffer.append("\">" + LINE_FEED);

            buffer.append("<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">" + menuItem.getLabel() + "<span class=\"caret\"></span></a>" + LINE_FEED);
            buffer.append("<ul class=\"dropdown-menu\" role=\"menu\">" + LINE_FEED);
            
			for(NavUrlItemModel child : menuItem.getChildItemList())
			{
				if(child.isSeparator())
				{
					// divider
					buffer.append("<li class=\"divider\"></li>" + LINE_FEED);
				}
				else
				{
					// add the URL
					buffer.append("<li>");
					this.buildMenuItemNavUrl(buffer, child, null, null, null, false);
					buffer.append("</li>" + LINE_FEED);
				}
			}
          
			buffer.append("</ul>" + LINE_FEED);
			buffer.append("</li>" + LINE_FEED);
		}
		else if(menuItem.isSeparator())
		{
			// divider
			buffer.append("<li class=\"divider\"></li>" + LINE_FEED);
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
			this.buildMenuItemNavUrl(buffer, menuItem, null, null, null, false);
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
