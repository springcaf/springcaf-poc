package com.springcaf.core.web.model;

import java.util.HashMap;
import java.util.Map;

import com.springcaf.core.exception.SpringcafException;

public class WebListSet {
	
	protected Map<String, KeyValueListModel> commonListMap = new HashMap<String, KeyValueListModel>();

	/**
	 * Constructor
	 */
	public WebListSet()
	{
		// default
	}
	
	/**
	 * Get a list
	 * @param key
	 * @return
	 * @throws SpringcafException
	 */
	public KeyValueListModel getList(String key) throws SpringcafException
	{
		KeyValueListModel commonList = commonListMap.get(key);
		
		if(commonList == null)
		{
			throw new SpringcafException("List for " + key + " does not exist");
		}
		
		return commonList;
	}
	
	public void addList(String key, KeyValueListModel list)
	{
		commonListMap.put(key, list);
	}
	
	public void addListItem(String key, String itemKey, String itemValue)
	{
		KeyValueListModel list = commonListMap.get(key);
		if(list == null)
		{
			list = new KeyValueListModel();
			commonListMap.put(key, list);
		}
		
		list.addKeyValueItem(itemKey, itemValue);
	}
	
	public Map<String, KeyValueListModel> getCommonListMap() {
		return commonListMap;
	}

	public void setCommonListMap(Map<String, KeyValueListModel> commonListMap) {
		this.commonListMap = commonListMap;
	}
	
}
