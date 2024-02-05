package com.springcaf.core.web.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.WebErrorElementModel;
import com.springcaf.core.web.model.WebModel;
import com.springcaf.core.web.model.element.CheckboxFieldElement;
import com.springcaf.core.web.widget.WidgetConstants;

public final class WebRequestUtils {
	
	/**
	 * Test if the request is a postback
	 * @param request
	 * @return
	 */
	public static boolean isPostback(HttpServletRequest request)
	{
		return "post".equalsIgnoreCase(request.getMethod());
	}
	
	/**
	 * Get form name from request object, which is a hidden parameter in the post action of the form
	 * This is true only for the form generated from the widget.
	 * @param request
	 * @return
	 */
	public static String getFormName(HttpServletRequest request)
	{
		return request.getParameter(WidgetConstants.FORM_NAME_PARM);
	}
	
	/**
	 * Get a request parameter value
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static String getWebFormParameterValue(HttpServletRequest request, String parameterName)
	{
		return request.getParameter(parameterName);
	}
	
	/**
	 * Get the parameter values if the parm has multiple values
	 * @param request
	 * @param parameterName
	 * @return
	 */
	public static String[] getWebFormParameterValues(HttpServletRequest request, String parameterName)
	{
		return request.getParameterValues(parameterName);
	}

	/**
	 * Parse parameters defined by the form, which means the parameter has a name like FORM_NAME_parm_name
	 * @param model
	 * @param request
	 * @return
	 */
	private static void parseWebFormData(WebModel model, HttpServletRequest request)
	{
		// loop through to set the values
		Enumeration<String> formParms = request.getParameterNames();
		List<String> realParameterNames = new ArrayList<String>();
		while(formParms.hasMoreElements())
		{
			String parameterName = formParms.nextElement();
			if(!StringUtils.isNullOrEmpty(parameterName) && parameterName.startsWith(model.getFormName()) && parameterName.length() > model.getFormName().length())
			{
				String realParameterName = parameterName.substring(model.getFormName().length() + 1, parameterName.length()-1);
				realParameterNames.add(realParameterName);
				if(request.getParameterValues(parameterName) != null)
				{
					String[] parts = request.getParameterValues(parameterName);
					model.setFormDataField(realParameterName, StringUtils.collapseStringArray(parts, ","));
				}
				else if(request.getParameter(parameterName) != null)
				{
					model.setFormDataField(realParameterName, request.getParameter(parameterName));
				}
			}
		}
		
		// checkbox needs special handling
		for(WebElementModel webElement : model.getModelElements())
		{
			if(webElement instanceof CheckboxFieldElement)
			{
				if(realParameterNames.contains(webElement.getElementName()))
				{
					model.setFormDataField(webElement.getElementName(), "true");
				}
				else
				{
					model.setFormDataField(webElement.getElementName(), "false");
				}
			}
		}
	}
	
	/**
	 * Validate form data for required, number and date types, etc.
	 * @param model
	 * @param request
	 */
	private static void validateWebFormData(WebModel model, HttpServletRequest request)
	{
		for(WebElementModel element : model.getModelElements())
		{
			String elementValue = model.getFormDataElement(element.getElementName());
			
			if(element.isRequired())
			{
				if(StringUtils.isNullOrEmpty(elementValue))
				{
					model.addWebErrorElement(new WebErrorElementModel(element.getElementLabel(), element.getElementName(), element.getElementLabel() + " is required."));
				}
			}
		}

	}
	
	/**
	 * Parse the form data into WebModel and validate against the model definitions.
	 * @param model
	 * @param request
	 */
	public static void bindAndValidateFormPostRequest(WebModel model, HttpServletRequest request)
	{
		// first parse the form data
		parseWebFormData(model, request);
		
		// next validate the form data according to form model
		validateWebFormData(model, request);
	}
	
	/**
	 * Get username from the request
	 * @param request
	 * @return
	 */
	public static String getUsername(HttpServletRequest request)
	{
		if(request == null || request.getUserPrincipal() == null || request.getUserPrincipal().getName() == null)
		{
			return null;
		}
		
		return request.getUserPrincipal().getName();
	}
	
	/**
	 * Get username from the request and convert to lower case
	 * @param request
	 * @return
	 */
	public static String getUsernameLowerCase(HttpServletRequest request)
	{
		String username = getUsername(request);
		
		if(username == null)
		{
			return null;
		}
		
		return username.toLowerCase();
	}
	
	/**
	 * Save object to session with key
	 * @param request
	 * @param key
	 * @param obj
	 */
	public static void saveToSession(HttpServletRequest request, String key, Object obj)
	{
		HttpSession session = request.getSession();
		if(session != null)
		{
			session.setAttribute(key, obj);
		}
	}
	
	/**
	 * Retreive object from session with key
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object retrieveFromSession(HttpServletRequest request, String key)
	{
		HttpSession session = request.getSession();
		if(session != null)
		{
			return session.getAttribute(key);
		}
		
		return null;
	}
	
	/**
	 * Convert a posted checkbox value to true/false
	 * @param checkboxValue
	 * @return
	 */
	public static boolean convertCheckboxToTrueFalse(String checkboxValue)
	{
		if("Y".equalsIgnoreCase(checkboxValue) 
				|| "yes".equalsIgnoreCase(checkboxValue) 
				|| "true".equalsIgnoreCase(checkboxValue)
				|| "on".equalsIgnoreCase(checkboxValue))
		{
			return true;
		}
		
		return false;
	}
	

}
