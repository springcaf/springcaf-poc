package com.springcaf.core.web.model.table;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.springcaf.core.util.NumberUtils;
import com.springcaf.core.util.StringUtils;

public class SortAndFilterState {

	public static final String SORT_ASC= "ASC";
	public static final String SORT_DESC = "DESC";

	private Map<String, String> filterMap = new LinkedHashMap<String, String>();
	private String sortKey = null;
	private String sortOrder = SORT_ASC;
	private String pageSize = "10";
	private Map<String, String> pageNumMap = new HashMap<String, String>();

	/**
	 * Constructor
	 */
	public SortAndFilterState()
	{
		// default
	}
	
	public void setFilter(String filterKey, String filterValue)
	{
		this.resetPageNumMap();
		filterMap.put(filterKey, filterValue);
	}
	
	public String getFilter(String filterKey)
	{
		return filterMap.get(filterKey);
	}
	
	public String getSortKey()
	{
		return this.sortKey;
	}
	
	public String getSortOrder()
	{
		if(!SORT_DESC.equals(sortOrder))
		{
			return SORT_ASC;
		}
		
		return sortOrder;
	}
	
	public void setSort(String sortKey)
	{
		this.resetPageNumMap();
		if(this.sortKey != null && this.sortKey.equals(sortKey))
		{
			// flip the direction
			String sortOrder = this.getSortOrder();
			if(SORT_DESC.equals(sortOrder))
			{
				this.sortOrder = SORT_ASC;
			}
			else
			{
				this.sortOrder = SORT_DESC;
			}
		}
		else
		{
			this.sortKey = sortKey;
			this.sortOrder = SORT_ASC;
		}
	}
	
	public void resetFilters()
	{
		filterMap.clear();
	}
	
	public Map<String, String> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, String> filterMap) {
		this.filterMap = filterMap;
	}

	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		for(String filterKey : this.filterMap.keySet())
		{
			buffer.append(filterKey + " = " + filterMap.get(filterKey) + "\r\n");
		}
		
		buffer.append("Sort Key = " + this.getSortKey() + "\r\n");
		buffer.append("Sort Order = " + this.getSortOrder() + "\r\n");
		
		return buffer.toString();
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.resetPageNumMap();
		this.pageSize = pageSize;
	}
	
	public boolean isSortDecending()
	{
		return SORT_DESC.equals(sortOrder);
	}
	
	private void resetPageNumMap()
	{
		for(String key : this.pageNumMap.keySet())
		{
			this.pageNumMap.put(key, "1");
		}
	}

	public int calculatePageNumber(String initPageNum, String pageKey)
	{
		String calcPageNum = initPageNum;
		if(StringUtils.isNullOrEmpty(calcPageNum))
		{
			calcPageNum = this.pageNumMap.get(pageKey);
			if(StringUtils.isNullOrEmpty(calcPageNum))
			{
				calcPageNum = "1";
			}
		}
		
		this.pageNumMap.put(pageKey, calcPageNum);
		int pageNumber = NumberUtils.parseInt(calcPageNum, 1);

		return pageNumber;
	}
}