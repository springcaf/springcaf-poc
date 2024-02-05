package com.springcaf.core.web.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.ObjectUtils;
import com.springcaf.core.util.StringUtils;

public class WebModel extends AbstractWebModel {

	protected List<WebElementModel> modelElements = new ArrayList<WebElementModel>();
	protected List<WebActionElementModel> modelActionElements = new ArrayList<WebActionElementModel>();
	protected List<WebErrorElementModel> modelErrorElements = new ArrayList<WebErrorElementModel>();
	protected Map<String, String> formData = new HashMap<String, String>();
	protected String formName = null;
	protected String dateFormat = "MM/dd/yyyy";
	protected String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	
	public WebModel addWebElement(WebElementModel element)
	{
		this.modelElements.add(element);
		
		return this;
	}
	
	public WebElementModel getWebElement(int index)
	{
		return this.modelElements.get(index);
	}

	public WebModel addWebActionElement(WebActionElementModel element)
	{
		this.modelActionElements.add(element);
		
		return this;
	}
	
	public WebActionElementModel getWebActionElement(int index)
	{
		return this.modelActionElements.get(index);
	}

	public WebModel addWebErrorElement(WebErrorElementModel element)
	{
		this.modelErrorElements.add(element);
		
		return this;
	}
	
	public WebErrorElementModel getWebErrorElement(int index)
	{
		return this.modelErrorElements.get(index);
	}

	public List<WebElementModel> getModelElements() {
		return modelElements;
	}

	public void setModelElements(List<WebElementModel> modelElements) {
		this.modelElements = modelElements;
	}

	public List<WebActionElementModel> getModelActionElements() {
		return modelActionElements;
	}

	public void setModelActionElements(
			List<WebActionElementModel> modelActionElements) {
		this.modelActionElements = modelActionElements;
	}
	
	/**
	 * Get all the read mode actions
	 * @return
	 */
	public List<WebActionElementModel> getReadModeActions()
	{
		List<WebActionElementModel> readModeActions = new ArrayList<WebActionElementModel>();
		
		for(WebActionElementModel element : this.modelActionElements)
		{
			if(element.isReadModeAction())
			{
				readModeActions.add(element);
			}
		}
		
		return readModeActions;
	}
	
	/**
	 * Get all the edit mode actions
	 * @return
	 */
	public List<WebActionElementModel> getEditModeActions()
	{
		List<WebActionElementModel> editModeActions = new ArrayList<WebActionElementModel>();
		
		for(WebActionElementModel element : this.modelActionElements)
		{
			if(element.isEditModeAction())
			{
				editModeActions.add(element);
			}
		}
		
		return editModeActions;
	}
	
	public List<WebErrorElementModel> getModelErrorElements() {
		return modelErrorElements;
	}

	public void setModelErrorElements(List<WebErrorElementModel> modelErrorElements) {
		this.modelErrorElements = modelErrorElements;
	}
	
	/**
	 * Check if the model has error elements
	 * @return
	 */
	public boolean hasErrors()
	{
		return this.modelErrorElements.size() > 0;
	}

	public Map<String, String> getFormData() {
		return formData;
	}

	public void setFormData(Map<String, String> formData) {
		this.formData = formData;
	}
	
	public String getFormDataElement(String elementKey)
	{
		return this.formData.get(elementKey);
	}
	
	public WebModel setFormDataField(String elementKey, String elementValue)
	{
		this.formData.put(elementKey, elementValue);
		
		return this;
	}

	public String getFormName() {
		
		if(formName == null)
		{
			return "form";
		}
		
		return formName;
	}

	public WebModel setFormName(String formName) {
		this.formName = formName;
		
		return this;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public WebModel setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
		
		return this;
	}

	public String getDateTimeFormat() {
		return dateTimeFormat;
	}

	public WebModel setDateTimeFormat(String dateTimeFormat) {
		this.dateTimeFormat = dateTimeFormat;
		
		return this;
	}
	
	/**
	 * Get value from an object field
	 * @param obj
	 * @param elementName
	 * @param defaultValue
	 * @return
	 * @throws SpringcafException
	 */
	public String getObjectMemberValueAsString(Object obj, String elementName, String defaultValue) throws SpringcafException
	{
		String val = ObjectUtils.getObjectMemberValueAsString(obj, elementName, this.dateFormat);

		// if obj is null, try to get it from the formData section
		if(obj == null && val == null)
		{
			val = this.formData.get(elementName);
		}
		
		if(val == null)
		{
			return defaultValue;
		}
		
		return val.toString();
	}
	
	/**
	 * Format and print out the form data
	 * @return
	 */
	public String printFormData()
	{
		StringBuffer buffer = new StringBuffer();
		
		for(String key : this.formData.keySet())
		{
			String value = this.formData.get(key);
			buffer.append(key + " = " + value + "\r\n");
		}
		
		return buffer.toString();
	}
	
	/**
	 * Get elements directly depends on this element
	 * @param thisElementName
	 * @return
	 */
	public List<String> getDirectElementsDependingOnThis(String thisElementName)
	{
		List<String> dependencyList = new ArrayList<String>();
		
		this.getElementsDependingOnThis(thisElementName, dependencyList, true);
		
		return dependencyList;
	}
	
	/**
	 * Get all elements depends on this element
	 * @param thisElementName
	 * @return
	 */
	public List<String> getAllElementsDependingOnThis(String thisElementName)
	{
		List<String> dependencyList = new ArrayList<String>();
		
		this.getElementsDependingOnThis(thisElementName, dependencyList, false);
		
		return dependencyList;
	}
	
	/**
	 * Get elements indirectly depends on this element
	 * @param thisElementName
	 * @return
	 */
	public List<String> getIndirectElementsDependingOnThis(String thisElementName)
	{
		List<String> allDependencyList = this.getAllElementsDependingOnThis(thisElementName);
		List<String> directDependencyList = this.getDirectElementsDependingOnThis(thisElementName);

		List<String> indirectDependencyList = new ArrayList<String>();
		
		if(directDependencyList.isEmpty())
		{
			return indirectDependencyList;
		}
		
		for(String elementName : allDependencyList)
		{
			if(!directDependencyList.contains(elementName))
			{
				indirectDependencyList.add(elementName);
			}
		}
		
		return indirectDependencyList;
	}
	
	
	/**
	 * Get the list of elements the given element depends on
	 * @param elementName
	 * @param directDependencyOnly
	 */
	private void getElementsDependingOnThis(String thisElementName, List<String> dependsOnList, boolean directDependencyOnly)
	{
		for(WebElementModel element : this.getModelElements())
		{
			String dependsOnValue = element.getDependsOn();
			if(!StringUtils.isNullOrEmpty(dependsOnValue))
			{
				String[] subElementNames = dependsOnValue.split(",");
				
				for(String subElementName : subElementNames)
				{
					if(subElementName.equals(thisElementName))
					{
						dependsOnList.add(element.getElementName());
						
						if(!directDependencyOnly)
						{
							this.getElementsDependingOnThis(element.getElementName(), dependsOnList, directDependencyOnly);
						}
					}
				}
				
			}
		}

	}
	
	/**
	 * Get element by name
	 * @param elementName
	 * @return
	 */
	public WebElementModel getWebElementByName(String elementName)
	{
		for(WebElementModel element : this.getModelElements())
		{
			if(element.getElementName().equals(elementName))
			{
				return element;
			}
		}
		
		return null;
	}
	
	/**
	 * Find the element by form element_id. Format is FORM_NAME_elementName
	 * @param elementName
	 * @return
	 */
	public WebElementModel getWebElementByFormElementId(String formElementId)
	{
		String formName = this.getFormName();
		
		if(formElementId == null || !formElementId.startsWith(formName))
		{
			return null;
		}
		
		String elementName = formElementId.substring(formName.length()+1);
		
		return this.getWebElementByName(elementName);
	}
}
