package com.springcaf.core.web.widget.alte;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.model.nav.NavSectionModel;
import com.springcaf.core.web.model.nav.NavUrlItemModel;

public class AlteNavBreadcrumbWidget extends AlteNavMenuWidget {
	
	private List<NavSectionModel> sectionList = new ArrayList<NavSectionModel>();
	private String activeItemId = null;
	
	/**
	 * Constructor
	 * @param applicationBaseUrl
	 * @param activeItemId
	 */
	public AlteNavBreadcrumbWidget(String applicationBaseUrl, String activeItemId)
	{
		super(applicationBaseUrl);
		
		this.activeItemId = activeItemId;
	}

	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {
		
		for(@SuppressWarnings("unused") NavSectionModel section : sectionList)
		{
			// build nav sections
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
