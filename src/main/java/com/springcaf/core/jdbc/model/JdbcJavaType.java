package com.springcaf.core.jdbc.model;

import java.util.HashMap;
import java.util.Map;

public enum JdbcJavaType {
	
	STRING, BOOLEAN, INTEGER, DOUBLE, DATE;
	
	private static Map<JdbcJavaType, String> typeMap = new HashMap<JdbcJavaType, String>();
	
	static {
		typeMap.put(STRING, "String");
		typeMap.put(BOOLEAN, "Boolean");
		typeMap.put(INTEGER, "Integer");
		typeMap.put(DOUBLE, "Double");
		typeMap.put(DATE, "java.util.Date");
	}

	public String getJavaType()
	{
		return typeMap.get(this);
	}
}
