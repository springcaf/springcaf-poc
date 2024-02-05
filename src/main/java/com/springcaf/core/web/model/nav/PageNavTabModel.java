package com.springcaf.core.web.model.nav;

public class PageNavTabModel {
	
	private String tabName = null;
	private String tabUrl = null;
	private int tabIndex;
	
	/**
	 * Constructor
	 * @param tabName
	 * @param tabBaseUrl
	 * @param tabIndex
	 */
	public PageNavTabModel(String tabName, String tabUrl, int tabIndex)
	{
		this.tabName = tabName;
		this.tabUrl = tabUrl;
		this.tabIndex = tabIndex;
	}

	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

	public String getTabUrl() {
		return tabUrl;
	}

	public void setTabUrl(String tabUrl) {
		this.tabUrl = tabUrl;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

}
