package com.springcaf.core.web.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.model.WebActionElementModel;
import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.WebErrorElementModel;
import com.springcaf.core.web.model.element.ActionCommandSaveElement;
import com.springcaf.core.web.model.form.WebFormModel;

public abstract class FormWebFormWidget<T> extends AbstractWidget {
	
	protected T dataObject = null;
	protected WebFormModel model = null;
	protected int labelSize = 3;
	protected Map<String, KeyValueListModel> listSet = new HashMap<String, KeyValueListModel>();
	protected Map<String, String> listRestSet = new HashMap<String, String>();
	protected List<String> formMessages = new ArrayList<String>();
	protected List<String> formProcessingErrors = new ArrayList<String>();
	protected Integer horizontalSplit = null;
	protected String formId = null;

	public FormWebFormWidget(String applicationBaseUrl)
	{
		super(applicationBaseUrl);
	}

	protected String getFormActionUrl()
	{
		if(this.model == null)
		{
			return "";
		}
		else
		{
			List<WebActionElementModel>editActions = this.model.getEditModeActions();
			for(WebActionElementModel action : editActions)
			{
				if(action instanceof ActionCommandSaveElement)
				{
					return action.getActionUrl();
				}
			}
		}
		
		return "";
	}
	
	/**
	 * Add keyValueList and RestUrl
	 * @param listName
	 * @param valueList
	 */
	public void addKeyValueListAndRestUrl(String listName, KeyValueListModel valueList, String restUrl)
	{
		this.listSet.put(listName, valueList);
		this.listRestSet.put(listName, restUrl);
	}
	
	/**
	 * Add a KeyValueList to the list set
	 * @param listName
	 * @param valueList
	 */
	public void addKeyValueList(String listName, KeyValueListModel valueList)
	{
		this.listSet.put(listName, valueList);
	}
	
	/**
	 * Get the name of the KeyValueList identified by listName
	 * @param listName
	 * @return
	 */
	public KeyValueListModel getKeyValueList(String listName)
	{
		if(listName == null)
		{
			return null;
		}
		return this.listSet.get(listName);
	}
	
	/**
	 * Add Rest URL for a list key
	 * @param listName
	 * @param restUrl
	 */
	protected void addRestUrl(String listName, String restUrl)
	{
		this.listRestSet.put(listName, restUrl);
	}
	
	public String getRestUrl(String listName)
	{
		if(listName == null)
		{
			return null;
		}
		
		return this.listRestSet.get(listName);
	}
	
	public T getDataObject() {
		return dataObject;
	}

	public void setDataObject(T dataObject) {
		this.dataObject = dataObject;
	}

	public WebFormModel getModel() {
		return model;
	}

	public void setModel(WebFormModel model) {
		this.model = model;
	}

	public Map<String, KeyValueListModel> getListSet() {
		return listSet;
	}

	public void setListSet(Map<String, KeyValueListModel> listSet) {
		this.listSet = listSet;
	}

	public int getLabelSize() {
		return labelSize;
	}

	public FormWebFormWidget<T> setLabelSize(int labelSize) {
		this.labelSize = labelSize;
		
		return this;
	}

	public Map<String, String> getListRestSet() {
		return listRestSet;
	}

	public void setListRestSet(Map<String, String> listRestSet) {
		this.listRestSet = listRestSet;
	}

	public List<String> getFormMessages() {
		return formMessages;
	}

	public void setFormMessages(List<String> formMessages) {
		this.formMessages = formMessages;
	}
	
	public FormWebFormWidget<T> addFormMessage(String message)
	{
		this.formMessages.add(message);
		
		return this;
	}
	
	public boolean hasMessage()
	{
		return this.formMessages.size() > 0;
	}

	public List<String> getFormProcessingErrors() {
		return formProcessingErrors;
	}

	public void setFormProcessingErrors(List<String> formProcessingErrors) {
		this.formProcessingErrors = formProcessingErrors;
	}
	
	public FormWebFormWidget<T> addProcessingError(String processingError)
	{
		this.formProcessingErrors.add(processingError);
		
		return this;
	}
	
	public boolean hasProcessingError()
	{
		return this.formProcessingErrors.size() > 0;
	}

	public Integer getHorizontalSplit() {
		return horizontalSplit;
	}

	public void setHorizontalSplit(Integer horizontalSplit) {
		this.horizontalSplit = horizontalSplit;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
	public void addWebElement(WebElementModel element)
	{
		if(this.model != null)
		{
			this.model.addWebElement(element);
		}
	}

	public void addWebActionElement(WebActionElementModel element)
	{
		if(this.model != null)
		{
			this.model.addWebActionElement(element);
		}
	}
	
	public void addWebErrorElement(WebErrorElementModel element)
	{
		if(this.model != null)
		{
			this.model.addWebErrorElement(element);
		}
	}

	/**
	 * Test if the postedFormName matches to the current form name
	 * @param postedFormName
	 * @return
	 */
	public boolean matchedPostedFormName(String postedFormName)
	{
		if(this.getModel() != null && !StringUtils.isNullOrEmpty(this.getModel().getFormName()))
		{
			if(this.getModel().getFormName().equals(postedFormName))
			{
				return true;
			}
		}
		
		return false;
	}
}
