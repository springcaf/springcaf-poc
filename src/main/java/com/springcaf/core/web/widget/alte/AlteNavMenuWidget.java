package com.springcaf.core.web.widget.alte;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.nav.NavSectionModel;
import com.springcaf.core.web.model.nav.NavUrlItemModel;
import com.springcaf.core.web.widget.NavBaseMenuWidget;

public abstract class AlteNavMenuWidget extends NavBaseMenuWidget {
	
	private List<NavSectionModel> sectionList = new ArrayList<NavSectionModel>();
	private String activeItemId = null;

	public AlteNavMenuWidget(String applicationBaseUrl) {
		super(applicationBaseUrl);
	}

	/**
	 * Build the basic URL item 
	 * @param buffer
	 * @param navUrlItem
	 * @param defaultIconClass
	 * @param overrideUrl
	 * @param postLabel - Additional elements after the label
	 * @param addLabelSpan add <span> around label
	 */
	protected void buildMenuItemNavUrl(StringBuffer buffer, NavUrlItemModel menuItem, String defaultIconClass, String overrideUrl, String postLabel, boolean addLabelSpan)
	{
		// build the URL
		String url = "#";
		if(!StringUtils.isNullOrEmpty(overrideUrl))
		{
			url = overrideUrl;
		}
		else if(!StringUtils.isNullOrEmpty(menuItem.getUrl()))
		{
			url = applicationBaseUrl + menuItem.getUrl();
		}
		
		// build the icon class
		String displayIconClass = "";
		if(!StringUtils.isNullOrEmpty(menuItem.getDisplayClass()))
		{
			displayIconClass = "<i class=\"" + menuItem.getDisplayClass() + "\"></i>";
		}
		else if(!StringUtils.isNullOrEmpty(defaultIconClass))
		{
			displayIconClass = "<i class=\"" + defaultIconClass + "\"></i> ";
		}
		
        buffer.append("<a href=\"" + url + "\">");
        buffer.append(displayIconClass);
        if(addLabelSpan)
        {
            buffer.append("<span>" + menuItem.getLabel() + " </span>");
        }
        else
        {
            buffer.append(menuItem.getLabel() + " ");
        }
        if(postLabel == null)
        {
        	postLabel = "";
        }
        buffer.append(postLabel);
        buffer.append("</a>");
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
