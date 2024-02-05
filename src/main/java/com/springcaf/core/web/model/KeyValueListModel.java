package com.springcaf.core.web.model;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;

public class KeyValueListModel extends AbstractWebModel {
	
	private List<KeyValueModel> items = new ArrayList<KeyValueModel>();
	
	/**
	 * Constructor
	 */
	public KeyValueListModel()
	{
		// default
	}
	
	/**
	 * Constructor
	 * @param keysAsValues
	 */
	public KeyValueListModel(String[] keysAsValues)
	{
		if(keysAsValues != null)
		{
			for(String key : keysAsValues)
			{
				this.items.add(new KeyValueModel(key, key));
			}
		}
	}

	/**
	 * Constructor
	 * @param keysAndValues
	 * @throws SpringcafException
	 */
	public KeyValueListModel(String[][] keysAndValues) throws SpringcafException
	{
		if(keysAndValues != null)
		{
			for(String[] keyAndValue : keysAndValues)
			{
				if(keyAndValue == null || keyAndValue.length != 2)
				{
					throw new SpringcafException("Input for KeyValueListELement is invalid");
				}
				this.items.add(new KeyValueModel(keyAndValue[0], keyAndValue[1]));
			}
		}
	}

	public List<KeyValueModel> getItems() {
		return items;
	}

	public void setItems(List<KeyValueModel> items) {
		this.items = items;
	}

	/**
	 * Add item
	 * @param item
	 */
	public KeyValueListModel addKeyValueItem(KeyValueModel item)
	{
		this.items.add(item);
		
		return this;
	}
	
	/**
	 * Add a keyValue item
	 * @param key
	 * @param value
	 * @return
	 */
	public KeyValueListModel addKeyValueItem(String key, String value)
	{
		this.items.add(new KeyValueModel(key, value));
		
		return this;
	}
	
	public KeyValueListModel addKeyValueItem(String key, String value, boolean active)
	{
		this.items.add(new KeyValueModel(key, value, active));
		
		return this;
	}

	/**
	 * Get item
	 * @param key
	 * @return
	 */
	public KeyValueModel getKeyValueItemByKey(String key)
	{
		if(key == null)
		{
			return null;
		}
		for(KeyValueModel item : this.items)
		{
			if(key.equalsIgnoreCase(item.getKey()))
			{
				return item;
			}
		}
		
		return null;
	}
	
	/**
	 * Get the value matching the key from the list. If null, return blank
	 * @param key
	 * @return
	 */
	public String getValueFromList(String key)
	{
		KeyValueModel model = this.getKeyValueItemByKey(key);
		
		if(model != null)
		{
			return model.getValue();
		}
		
		return "";
	}

	public String getValueFromList(String key, boolean activeOnly)
	{
		KeyValueModel model = this.getKeyValueItemByKey(key);
		
		if(model != null)
		{
			if(!activeOnly)
			{
				return model.getValue();
			}
			else if(model.isActive())
			{
				return model.getValue();
			}
		}
		
		return "";
	}
	
	public String toString()
	{
		String ret = "";
		for(KeyValueModel pair : this.items)
		{
			ret += "key=" + pair.getKey() + ", value=" + pair.getValue() + "\r\n";
		}
		
		return ret;
	}
	
	public String getKeyByIndex(Integer index)
	{
		if(index == null || index >= this.items.size())
		{
			return "";
		}
		
		return this.items.get(index).getKey();
		
	}
	
	public String getValueByIndex(Integer index)
	{
		if(index == null || index >= this.items.size())
		{
			return "";
		}
		
		return this.items.get(index).getValue();
		
	}
	
	public boolean isKeyInList(String key)
	{
		for(KeyValueModel item : this.items)
		{
			if(item.getKey() != null && item.getKey().equals(key))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public KeyValueListModel clone()
	{
		KeyValueListModel clone = new KeyValueListModel();

		for(KeyValueModel item : this.items)
		{
			clone.addKeyValueItem(item.getKey(), item.getValue());
		}
	
		return clone;
	}
	
	/**
	 * Get the key matching the value from the list. If null, return blank
	 * @param value
	 * @return
	 */
	public String getKeyByValue(String value)
	{
		for(KeyValueModel kv : this.items)
		{
			if(value.equals(kv.getValue()))
			{
				return kv.getKey();
			}
		}

		return "";
	}


}
