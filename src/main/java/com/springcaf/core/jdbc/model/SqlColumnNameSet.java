package com.springcaf.core.jdbc.model;

import java.util.HashSet;
import java.util.Set;

public class SqlColumnNameSet {
	
	private Set<String> columnNames = new HashSet<String>();
	
	/**
	 * Add a new column name
	 * @param columnName
	 * @return
	 */
	public SqlColumnNameSet addColumnName(String columnName)
	{
		this.columnNames.add(columnName);
		
		return this;
	}
	
	/**
	 * Check to see if the set contains a particular column name
	 * @param columnName
	 * @return
	 */
	public boolean containColumnName(String columnName)
	{
		if(this.columnNames.contains(columnName))
		{
			return true;
		}
		
		return false;
	}

	public Set<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(Set<String> columnNames) {
		this.columnNames = columnNames;
	}
	
	

}
