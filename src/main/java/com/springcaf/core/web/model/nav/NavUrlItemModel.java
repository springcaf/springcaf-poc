package com.springcaf.core.web.model.nav;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.AbstractWebModel;

public class NavUrlItemModel extends AbstractWebModel {
	
	private String label = null;
	private String url = null;
	private String displayClass = null;
	private List<NavUrlItemModel> childItemList = new ArrayList<NavUrlItemModel>();
	private boolean separator = false;
	
	/**
	 * Constructor
	 * @param label
	 * @param url
	 */
	public NavUrlItemModel(String label, String url)
	{
		this.label = label;
		this.url = url;
	}
	
	/**
	 * Constructor
	 * @param label
	 * @param url
	 * @param displayClass
	 */
	public NavUrlItemModel(String label, String url, String displayClass)
	{
		this.label = label;
		this.url = url;
		this.displayClass = displayClass;
	}

	/**
	 * Add a child nav item
	 * @param child
	 * @return
	 */
	public NavUrlItemModel addChildItem(NavUrlItemModel child)
	{
		return this.addChildItem(child.getLabel(), child.getUrl(), child.getDisplayClass());
	}
	
	/**
	 * Add a child nav item
	 * @param label
	 * @param url
	 * @return
	 */
	public NavUrlItemModel addChildItem(String label, String url)
	{
		NavUrlItemModel child = new NavUrlItemModel(label, url);
		this.childItemList.add(child);
		
		return child;
	}
	
	public NavUrlItemModel addDivider()
	{
		NavUrlItemModel child = new NavUrlItemModel(null, null, null).setSeparator(true);
		this.childItemList.add(child);
		
		return child;
	}
	
	/**
	 * Add a child nav item
	 * @param label
	 * @param url
	 * @param displayClass
	 * @return
	 */
	public NavUrlItemModel addChildItem(String label, String url, String displayClass)
	{
		NavUrlItemModel child = new NavUrlItemModel(label, url, displayClass);
		this.childItemList.add(child);
		
		return child;
	}
	
	public boolean hasChildren()
	{
		if(this.isSeparator())
		{
			return false;
		}
		
		return this.childItemList.size() > 0;
	}
	
	public String getDisplayClass() {
		return displayClass;
	}
	public void setDisplayClass(String displayClass) {
		this.displayClass = displayClass;
	}

	public String getLabel() {
		if(label == null)
		{
			return "";
		}
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		if(url == null)
		{
			return "";
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<NavUrlItemModel> getChildItemList() {
		return childItemList;
	}

	public void setChildItemList(List<NavUrlItemModel> childItemList) {
		this.childItemList = childItemList;
	}

	/**
	 * Direct match to the given itemId
	 * @param itemId
	 * @return
	 */
	public boolean isDirectItemIdMatch(String itemId)
	{
		if(StringUtils.isNullOrEmpty(itemId))
		{
			return false;
		}
		
		return itemId.equals(this.getItemId());
	}
	
	/**
	 * Direct child match to the given itemId
	 * @param itemId
	 * @return
	 */
	public boolean isChildrenItemIdMatch(String itemId)
	{
		if(StringUtils.isNullOrEmpty(itemId) || !this.hasChildren())
		{
			return false;
		}
		
		for(NavUrlItemModel subNavUrl : this.getChildItemList())
		{
			boolean ret = itemId.equals(subNavUrl.getItemId());
			
			if(ret)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Any self or children is a match
	 * @param itemId
	 * @return
	 */
	public boolean isAnyChildrenItemIdMatch(String itemId)
	{
		return this.isSelfOrChildMatchingItemId(this, itemId);
	}
	
	/**
	 * Test if the object or its children matches a given itemId
	 * @param navUrl
	 * @param itemId
	 * @return
	 */
	private boolean isSelfOrChildMatchingItemId(NavUrlItemModel navUrl, String itemId)
	{
		if(StringUtils.isNullOrEmpty(itemId))
		{
			return false;
		}
		
		if(itemId.equals(navUrl.getItemId()))
		{
			return true;
		}
		
		if(navUrl.hasChildren())
		{
			for(NavUrlItemModel subUrl : navUrl.getChildItemList())
			{
				boolean ret = isSelfOrChildMatchingItemId(subUrl, itemId);
				if(ret)
				{
					return true;
				}
			}
		}
		
		return false;
	}

	public String getItemId() {
		
		return this.getClass().getName().hashCode() + "-" + this.getLabel().hashCode() + "-" + this.getUrl().hashCode();
	}

	public boolean isSeparator() {
		return separator;
	}

	public NavUrlItemModel setSeparator(boolean separator) {
		this.separator = separator;
		
		return this;
	}
}
