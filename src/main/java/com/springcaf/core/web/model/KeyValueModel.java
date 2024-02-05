package com.springcaf.core.web.model;


public class KeyValueModel extends AbstractWebModel {
	private String key = null;
	private String value = null;
	private boolean active = true;
	
	/**
	 * Constructor
	 * @param key
	 * @param value
	 */
	public KeyValueModel(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	/**
	 * Constructor
	 * @param key
	 * @param value
	 * @param active
	 */
	public KeyValueModel(String key, String value, boolean active)
	{
		this.key = key;
		this.value = value;
		this.active = active;
	}

	public String getKey() {
		if(key == null)
		{
			return "";
		}
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		
		if(value == null)
		{
			return "";
		}
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
