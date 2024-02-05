package com.springcaf.core.web.model.nav;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.AbstractWebModel;

public class NavSectionModel extends AbstractWebModel {
	
	private String sectionName = null;
	private List<NavUrlItemModel> menuList = new ArrayList<NavUrlItemModel>();
	private String additionalHtml = null;
	
	/**
	 * Constructor
	 * @param sectionName
	 */
	public NavSectionModel(String sectionName)
	{
		this.sectionName = sectionName;
	}

	/**
	 * Add a nav url item
	 * @param item
	 * @return
	 */
	public NavUrlItemModel addNavUrlItem(NavUrlItemModel item)
	{
		return this.addNavUrlItem(item.getLabel(), item.getUrl(), item.getDisplayClass());
	}

	/**
	 * Add a nav url item
	 * @param label
	 * @param url
	 * @param itemId
	 * @return
	 */
	public NavUrlItemModel addNavUrlItem(String label, String url)
	{
		NavUrlItemModel menuItem = new NavUrlItemModel(label, url);
		this.menuList.add(menuItem);
		
		return menuItem;
	}
	
	/**
	 * Add a nav url item
	 * @param label
	 * @param url
	 * @param displayClass
	 * @param itemId
	 * @return
	 */
	public NavUrlItemModel addNavUrlItem(String label, String url, String displayClass)
	{
		NavUrlItemModel menuItem = new NavUrlItemModel(label, url, displayClass);
		this.menuList.add(menuItem);
		
		return menuItem;
	}
	
	public NavUrlItemModel findNavUrlItemById(String itemId)
	{
		if(StringUtils.isNullOrEmpty(itemId))
		{
			return null;
		}
		return this.findNavUrlItemById(this.getMenuList(), itemId);
	}
	
	private NavUrlItemModel findNavUrlItemById(List<NavUrlItemModel> itemList, String targetItemId)
	{
		for(NavUrlItemModel item : itemList)
		{
			if(targetItemId.equals(item.getItemId()))
			{
				return item;
			}
			if(item.hasChildren())
			{
				NavUrlItemModel temp = findNavUrlItemById(item.getChildItemList(), targetItemId);
				if(temp != null)
				{
					return temp;
				}
			}
		}
		
		return null;
	}
	
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public List<NavUrlItemModel> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<NavUrlItemModel> menuList) {
		this.menuList = menuList;
	}
	
	public boolean hasChildren()
	{
		return this.menuList.size() > 0;
	}

	public String getAdditionalHtml() {
		return additionalHtml;
	}

	public void setAdditionalHtml(String additionalHtml) {
		this.additionalHtml = additionalHtml;
	}

}
