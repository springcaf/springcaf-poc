package com.springcaf.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcaf.core.exception.SpringcafException;

public class JsonObjectUtils {
	
	public static <T> T parseJsonString(String jsonString, Class<T> objClass) throws SpringcafException
	{
		try
		{
	     	ObjectMapper mapper = new ObjectMapper();
	     	T object = mapper.readValue(jsonString, objClass);
	    	
	    	return object;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}

	public static String toJsonString(Object object) throws SpringcafException
	{
		try
		{
	    	ObjectMapper mapper = new ObjectMapper();

	    	return mapper.writeValueAsString(object);
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}

	}
	


}
