package com.springcaf.core.web.model.nav;

import java.util.ArrayList;
import java.util.List;

public class PageNavTabsModel {
	
	private List<PageNavTabModel> fixedTabs = new ArrayList<PageNavTabModel>();
	private List<PageNavTabModel> rightDropdown = new ArrayList<PageNavTabModel>();

	/**
	 * Constructor
	 */
	public PageNavTabsModel()
	{
	}

	public void addFixedTabModel(String tabName, String tabUrl, Integer tabIndex)
	{
		this.fixedTabs.add(new PageNavTabModel(tabName, tabUrl, tabIndex));
	}
	
	public void addRightDropdownTabModel(String tabName, String tabUrl, Integer tabIndex)
	{
		this.rightDropdown.add(new PageNavTabModel(tabName, tabUrl, tabIndex));
	}
	
	public List<PageNavTabModel> getFixedTabs() {
		return fixedTabs;
	}

	public void setFixedTabs(List<PageNavTabModel> fixedTabs) {
		this.fixedTabs = fixedTabs;
	}

	public String getTabNameByIndex(int tabIndex)
	{
		for(PageNavTabModel tabModel : this.getRightDropdown())
		{
			if(tabModel.getTabIndex() == tabIndex)
			{
				return tabModel.getTabName();
			}
		}

		for(PageNavTabModel tabModel : this.getFixedTabs())
		{
			if(tabModel.getTabIndex() == tabIndex)
			{
				return tabModel.getTabName();
			}
		}
		
		return null;
	}

	public List<PageNavTabModel> getRightDropdown() {
		return rightDropdown;
	}

	public void setRightDropdown(List<PageNavTabModel> rightDropdown) {
		this.rightDropdown = rightDropdown;
	}

}
