package com.springcaf.core.web.widget;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.model.nav.NavSectionModel;
import com.springcaf.core.web.model.nav.NavUrlItemModel;

public abstract class PageWidget extends AbstractWidget {

	protected List<NavSectionModel> mainNavModel = new ArrayList<NavSectionModel>();
	protected String currentNavId = null;
	protected String userDisplayName = null;
	protected String tenantDisplayName = null;

	public PageWidget(String applicationBaseUrl)
	{
		super(applicationBaseUrl);
		
	}
	
	/**
	 * Add a section model
	 * @param section
	 * @return
	 */
	public PageWidget addNavSectionModel(NavSectionModel section)
	{
		this.mainNavModel.add(section);
		
		return this;
	}
	
	public String getCurrentNavId() {
		return currentNavId;
	}

	public void setCurrentNavId(String currentNavId) {
		this.currentNavId = currentNavId;
	}

	public List<NavSectionModel> getMainNavModel() {
		return mainNavModel;
	}
	
	public NavUrlItemModel findNavUrlItemByKey(String itemId)
	{
		NavUrlItemModel result = null;
		
		if(this.mainNavModel != null)
		{
			for(NavSectionModel section : this.mainNavModel)
			{
				result = section.findNavUrlItemById(itemId);
				if(result != null)
				{
					return result;
				}
			}
		}
		
		return result;
	}

	abstract public String getRenderedHtmlMainNav() throws SpringcafException;

	public String getUserDisplayName() {
		return userDisplayName;
	}

	public void setUserDisplayName(String userDisplayName) {
		this.userDisplayName = userDisplayName;
	}

	public String getTenantDisplayName() {
		return tenantDisplayName;
	}

	public void setTenantDisplayName(String tenantDisplayName) {
		this.tenantDisplayName = tenantDisplayName;
	}

}
