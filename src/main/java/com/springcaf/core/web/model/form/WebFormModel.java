package com.springcaf.core.web.model.form;

import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.ObjectUtils;
import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.WebModel;

public class WebFormModel extends WebModel {
	
	private String enctype = null;
	
	/**
	 * Constructor
	 */
	public WebFormModel()
	{
		// default
	}
	
	/**
	 * Constructor
	 * @param modelElements
	 */
	public WebFormModel(List<WebElementModel> modelElements)
	{
		this.modelElements = modelElements;
	}
	
	/**
	 * Bind the form data into a data object
	 * @param dataObject
	 * @throws SpringcafException
	 */
	public void bindFormDataToObject(Object dataObject) throws SpringcafException
	{
		for(String formKey : this.getFormData().keySet())
		{
			ObjectUtils.setObjectMemberValueWithString(dataObject, formKey, this.getFormData().get(formKey), this.getDateFormat());
		}
	}

	public String getEnctype() {
		return enctype;
	}

	public void setEnctype(String enctype) {
		this.enctype = enctype;
	}
	
	/**
	 * Print out the form data
	 */
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		
		for(String formKey : this.getFormData().keySet())
		{
			buffer.append(formKey + " = " + this.getFormData().get(formKey) + "\r\n");
		}
		
		return buffer.toString();
	}
}
