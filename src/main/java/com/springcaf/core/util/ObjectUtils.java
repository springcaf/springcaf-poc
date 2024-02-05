package com.springcaf.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import com.springcaf.core.exception.SpringcafException;

public final class ObjectUtils {
	
	/**
	 * Get the member value as an Object
	 * @param obj
	 * @param memberName
	 * @return
	 * @throws SpringcafException
	 */
	public static Object getObjectMemberValue(Object obj, String memberName) throws SpringcafException
	{
		// Find the getter method
		Method getter = getGetterMethodByMemberName(obj, memberName);
		
		if(getter != null)
		{
			try
			{
				Object returnValue = getter.invoke(obj);
				
				return returnValue;
			}
        	catch(InvocationTargetException ex)
        	{
        		throw new SpringcafException(ex);
        	} 
        	catch (IllegalArgumentException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        	catch (IllegalAccessException ex) 
        	{
        		throw new SpringcafException(ex);
			}
		}
		
		return null;
	}
	
	/**
	 * Get an object member value via reflection of getter method
	 * @param object
	 * @param memberName
	 * @return
	 * @throws SpringcafException 
	 */
	public static String getObjectMemberValueAsString(Object obj, String memberName, String dateFormat) throws SpringcafException
	{
		if(obj == null)
		{
			return null;
		}
		// Find the getter method
		Method getter = getGetterMethodByMemberName(obj, memberName);
		
		if(getter != null)
		{
			try
			{
				Class<?> returnType = getter.getReturnType();
				Object returnValue = getter.invoke(obj);
				
				if(returnValue == null)
				{
					return null;
				}
				
				if(returnType.equals(java.lang.String.class))
				{
					return (String)returnValue;
				}
				else if(returnType.equals(java.util.Date.class))
				{
					return DateUtils.convertDateToString((Date)returnValue, dateFormat);
				}
				else
				{
					return String.valueOf(returnValue);
				}

			}
        	catch(InvocationTargetException ex)
        	{
        		throw new SpringcafException(ex);
        	} 
        	catch (IllegalArgumentException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        	catch (IllegalAccessException ex) 
        	{
        		throw new SpringcafException(ex);
			}
		}
		
		return null;
	}

	/**
	 * Set the member value
	 * @param obj
	 * @param memberName
	 * @param memberValue
	 * @throws SpringcafException
	 */
	public static void setObjectMemberValue(Object obj, String memberName, Object memberValue) throws SpringcafException
	{
		// Find the setter method
		Method setter = getSetterMethodByMemberName(obj, memberName);

		if(setter != null)
		{
			try
			{
				setter.invoke(obj, memberValue);
			}
        	catch(InvocationTargetException ex)
        	{
        		throw new SpringcafException(ex);
        	} 
        	catch (IllegalArgumentException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        	catch (IllegalAccessException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        }
	}
	
	/**
	 * Set multiple members with values
	 * @param obj
	 * @param memberNames
	 * @param memberValues
	 * @throws SpringcafException
	 */
	public static void setObjectMembersValues(Object obj, String[] memberNames, Object[] memberValues) throws SpringcafException
	{
		if(memberNames == null || memberValues == null || memberNames.length != memberValues.length)
		{
			throw new SpringcafException("ObjectUtils.setObjectMembersValues: invalid parmeters");
		}
		for(int i=0; i<memberNames.length; i++)
		{
			setObjectMemberValue(obj, memberNames[i], memberValues[i]);
		}
	}

	/**
	 * Set the member value of an object
	 * @param obj
	 * @param memberName
	 * @param memberValue
	 * @return
	 * @throws SpringcafException
	 */
	public static void setObjectMemberValueWithString(Object obj, String memberName, String memberValue, String dateFormat) throws SpringcafException
	{
		// Find the setter method
		Method setter = getSetterMethodByMemberName(obj, memberName);

		if(setter != null)
		{
			try
			{
				Class<?>[] paramTypes = setter.getParameterTypes();
				Class<?> param1Type = paramTypes[0];
				Object finalValue = memberValue;

				if(param1Type.equals(java.lang.String.class))
				{
					finalValue = memberValue;
				}
				else if(param1Type.equals(java.util.Date.class))
				{
					finalValue = DateUtils.parseDate(memberValue, dateFormat);
				}
				else if(param1Type.equals(java.lang.Integer.class))
				{
					finalValue = Integer.parseInt(memberValue);
				}
				else if(param1Type.equals(java.lang.Double.class))
				{
					finalValue = Double.parseDouble(memberValue);
				}
				else if(param1Type.equals(java.lang.Boolean.class))
				{
					finalValue = StringUtils.StringToBoolean(memberValue);
				}
				setter.invoke(obj, finalValue);

			}
        	catch(InvocationTargetException ex)
        	{
        		throw new SpringcafException(ex);
        	} 
        	catch (IllegalArgumentException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        	catch (IllegalAccessException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
			catch (ParseException ex) 
			{
				throw new SpringcafException(ex);
			}
		
        }
	}
	
	/**
	 * Format object into a string
	 * @param objValue
	 * @param dateFormat
	 * @return
	 */
	public static String formatObjectAsString(Object objValue, String dateFormat)
	{
		if(objValue == null)
		{
			return "";
		}
		else if(objValue instanceof String)
		{
			return (String)objValue;
		}
		else if(objValue instanceof Date)
		{
			return DateUtils.convertDateToString((Date)objValue, dateFormat);
		}
		else
		{
			return objValue.toString();
		}
	}

	/**
	 * Convert member to method name
	 * @param memberName
	 * @param prefix
	 * @return
	 */
	private static String memberNameToMethodName(String memberName, String prefix)
	{
		if(memberName.indexOf("_") > 0)
		{
			memberName = StringFormatUtils.underscoreToCamel(memberName, false);
		}
		
		return prefix + StringFormatUtils.firstCharToUpper(memberName);
	}
	
	/**
	 * Get the setter method from the object by memberName
	 * @param obj
	 * @param memberName
	 * @return
	 * @throws SpringcafException
	 */
	private static Method getSetterMethodByMemberName(Object obj, String memberName) throws SpringcafException
	{
		// get the class of the object
		Class<? extends Object> objClass = obj.getClass();
		
		// use reflection to bind the data
        Method[] declaredMethods = objClass.getDeclaredMethods();
        
        for(Method method: declaredMethods)
        {
        	try
        	{
            	// call getter methods to get values and set to tableRow
            	String methodName = method.getName();
            	if(methodName.startsWith("set"))
            	{
            		String memberFunctionName = memberNameToMethodName(memberName, "set");
            		if(methodName.equals(memberFunctionName))
            		{
            			return method;
            		}
            	}
        	}
        	catch (IllegalArgumentException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        }
        
        return null;
	}
	
	/**
	 * Get the getter method from the object by memberName
	 * @param obj
	 * @param memberName
	 * @return
	 * @throws SpringcafException
	 */
	private static Method getGetterMethodByMemberName(Object obj, String memberName) throws SpringcafException
	{
		// get the class of the object
		Class<? extends Object> objClass = obj.getClass();
		
		// use reflection to bind the data
        Method[] declaredMethods = objClass.getDeclaredMethods();
        
        for(Method method: declaredMethods)
        {
        	try
        	{
            	// call getter methods to get values and set to tableRow
            	String methodName = method.getName();
            	if(methodName.startsWith("get"))
            	{
            		String memberFunctionName = memberNameToMethodName(memberName, "get");
            		if(methodName.equals(memberFunctionName))
            		{
            			return method;
            		}
            	}
            	else if(methodName.startsWith("is"))
            	{
            		String memberFunctionName = memberNameToMethodName(memberName, "is");
            		if(methodName.equals(memberFunctionName))
            		{
            			return method;
            		}
            	}
        	}
        	catch (IllegalArgumentException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        }
        
        return null;
	}
	
	/**
	 * Dump the object content into a string
	 * @param obj
	 * @return
	 * @throws SpringcafException 
	 */
	public static String printObjectContents(Object obj) throws SpringcafException
	{
		StringBuffer buffer = new StringBuffer();
		
		// get the class of the object
		Class<? extends Object> objClass = obj.getClass();
		
		// use reflection to bind the data
        Method[] declaredMethods = objClass.getDeclaredMethods();
        
        for(Method method: declaredMethods)
        {
        	try
        	{
            	// call getter methods to get values and set to tableRow
            	String methodName = method.getName();
            	if(methodName.startsWith("get") || methodName.startsWith("is"))
            	{
            		buffer.append(methodName + " = " + method.invoke(obj) + "\r\n");
            	}
        	}
        	catch (IllegalArgumentException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        	catch (IllegalAccessException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        	catch (InvocationTargetException ex) 
        	{
        		throw new SpringcafException(ex);
			} 
        }
        
        return buffer.toString();
	}
}
