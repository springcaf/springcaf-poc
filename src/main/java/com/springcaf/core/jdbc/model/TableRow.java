package com.springcaf.core.jdbc.model;

import java.util.ArrayList;
import java.util.List;

public class TableRow {

	private List<Object> cells = new ArrayList<Object>();
	
	/**
	 * Constructor
	 */
	public TableRow()
	{
		// nothing to initialize
	}
	
	/**
	 * Get a value from the row
	 * @param indexKey
	 * @return
	 */
	public Object getValue(int indexKey)
	{
		if(indexKey < 0 || indexKey >= cells.size())
		{
			return null;
		}
		return cells.get(indexKey);
	}
	
	/**
	 * Add value to the list
	 * @param value
	 */
	public void addValue(Object value)
	{
		this.cells.add(value);
	}
	
	/**
	 * Set a value for the row
	 * @param indexKey
	 * @param value
	 */
	public void setValue(int indexKey, Object value)
	{
		if(indexKey < 0)
		{
			// skip
		}
		else if(indexKey >= cells.size())
		{
			cells.add(value);
		}
		else
		{
			cells.set(indexKey, value);
		}
	}

	/*********************************************************************************************
	 * Getters and setters
	 ********************************************************************************************/
	public List<Object> getCells() {
		return cells;
	}

	public void setCells(List<Object> cells) {
		this.cells = cells;
	}
	
	
}
