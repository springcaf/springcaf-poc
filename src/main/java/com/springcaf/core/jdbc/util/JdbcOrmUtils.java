package com.springcaf.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.TableColumnModel;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.model.TableRow;
import com.springcaf.core.util.StringFormatUtils;

final class JdbcOrmUtils {
	
	/**
	 * Map a single table row into 
	 * @param objClass
	 * @param data
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SpringcafException 
	 */
	public static <T> T mapTableRowToObject(Class<T> objClass, TableModel model, TableRow row) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SpringcafException
	{
		// validate input
		if(model == null || row == null)
		{
			throw new SpringcafException("Invalid TableModel or TableRow in TableRow to Object mapping");
		}

		// create an instance of the object
		@SuppressWarnings("deprecation")
		T obj = objClass.newInstance();
		
		// use reflection to bind the data
        Method[] declaredMethods = objClass.getDeclaredMethods();
        
        for(Method method: declaredMethods)
        {
        	// call setter methods
        	String methodName = method.getName();
        	if(methodName.startsWith("set"))
        	{
        		String dataFieldName = convertMethodNameToKeyName(methodName, 4);
        		Object value = row.getValue(model.getColumnIndex(dataFieldName));
        		if(value != null)
        		{
        			method.invoke(obj, value);
        		}
        		else
        		{
        			// put a underline before each number digit
        			dataFieldName = StringFormatUtils.addUnderscoreBeforeNumber(dataFieldName);
            		value = row.getValue(model.getColumnIndex(dataFieldName));
            		if(value != null)
            		{
            			method.invoke(obj, value);
            		}
        		}
        	}
        }

		return obj;
	}
	
	/**
	 * Map an object to a table row for insert/update/delete
	 * @param obj
	 * @param model
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SpringcafException
	 */
	public static TableRow mapObjectToTableRow(Object obj, TableModel model) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, SpringcafException
	{
		// validate input
		if(model == null || obj == null)
		{
			throw new SpringcafException("Invalid TableModel or Object in Object to TableRow mapping");
		}
		
		// create tableRow
		TableRow row = new TableRow();
		
		// get the data object map
		Map<String, Object> map = dataObjectToMap(obj);

		for(TableColumnModel column: model.getColumns())
		{
			row.addValue(map.get(column.getColumnName().toLowerCase()));
		}

		return row;
	}
	
	/**
	 * Convert an object into key value HashMap
	 * @param dataObject
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	private static Map<String, Object> dataObjectToMap(Object dataObject) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		// get the class of the object
		Class<? extends Object> objClass = dataObject.getClass();
		
		// use reflection to bind the data
        Method[] declaredMethods = objClass.getDeclaredMethods();
        
        for(Method method: declaredMethods)
        {
        	// call getter methods to get values and set to tableRow
        	String methodName = method.getName();
        	boolean isStatic = Modifier.isStatic(method.getModifiers());
        	if(!isStatic && (methodName.startsWith("get") || methodName.startsWith("is")))
        	{
        		String dataFieldName = convertMethodNameToKeyName(methodName, 4);
        		Object value = method.invoke(dataObject);
        		
        		map.put(dataFieldName.toLowerCase(), value);
        	}
        }
		
        return map;
	}
	
	/**
	 * Convert method name to table column names
	 * @param methodName
	 * @return
	 */
	private static String convertMethodNameToKeyName(String methodName, int startIndex)
	{
		String underscore = StringFormatUtils.camelToUnderscore(methodName);
		
		return underscore.substring(startIndex);
	}

}
