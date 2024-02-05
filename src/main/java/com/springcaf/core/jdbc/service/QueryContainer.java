package com.springcaf.core.jdbc.service;


import java.util.LinkedHashMap;
import java.util.Map;

public class QueryContainer {
	
	private Map<String, String> namedQueries = new LinkedHashMap<String, String>();
	
	/**
	 * Constructor
	 */
	public QueryContainer()
	{
		// default
	}

	/**
	 * Add a named query
	 * @param name
	 * @param query
	 */
	public void addNamedQuery(String name, String query)
	{
		this.namedQueries.put(name, query);
	}
	
	/**
	 * Merge the container into current container
	 * @param container
	 */
	public void mergeQueryContainer(QueryContainer container)
	{
		if(container == null)
		{
			return;
		}
		
		for(String name : container.namedQueries.keySet())
		{
			this.namedQueries.put(name, container.getNamedQuery(name));
		}
	}
	
	/**
	 * get the query string for a given name
	 * @param name
	 * @return
	 */
	public String getNamedQuery(String name)
	{
		return this.namedQueries.get(name);
	}
	
	/**
	 * convert the list to a long string value for display purpose
	 */
	public String contentDump()
	{
		StringBuffer buffer = new StringBuffer();
		
		for(String queryName : this.namedQueries.keySet())
		{
			buffer.append("[" + queryName + "]=" + "\r\n");
			buffer.append(this.getNamedQuery(queryName) + "\r\n");
		}
		buffer.append("Total queries: " + this.namedQueries.size() + "\r\n");
		
		return buffer.toString();
	}
	
	public Map<String, String> getNamedQueries() {
		return namedQueries;
	}

	public void setNamedQueries(Map<String, String> namedQueries) {
		this.namedQueries = namedQueries;
	}

}
