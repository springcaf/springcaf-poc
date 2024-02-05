package com.springcaf.core.jdbc.model;

/**
 * To use this class, the database column names have to be item_id, item_value
 *
 */
public class GenericDropDownItem {
	
	private String itemId = null;
	private String itemValue = null;
	
	/**
	 * Constructor
	 */
	public GenericDropDownItem()
	{
		// default
	}
	
	/**
	 * Constructor
	 * @param itemId
	 * @param itemValue
	 */
	public GenericDropDownItem(String itemId, String itemValue)
	{
		this.itemId = itemId;
		this.itemValue = itemValue;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemValue() {
		return itemValue;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	
}
