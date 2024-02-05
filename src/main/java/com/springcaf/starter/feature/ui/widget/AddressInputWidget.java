package com.springcaf.starter.feature.ui.widget;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.DateUtils;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.model.element.ActionCommandSaveElement;
import com.springcaf.core.web.model.element.CheckboxFieldElement;
import com.springcaf.core.web.model.element.DateFieldElement;
import com.springcaf.core.web.model.element.DropDownListElement;
import com.springcaf.core.web.model.element.TextFieldElement;
import com.springcaf.core.web.util.WebRequestUtils;
import com.springcaf.core.web.widget.alte.AlteFormWebFormWidget;
import com.springcaf.starter.feature.ui.model.AddressInput;

public class AddressInputWidget extends AlteFormWebFormWidget<AddressInput> {
	
	/**
	 * Constructor
	 * @param tableDetailLink
	 */
	public AddressInputWidget(WebAppMeta webAppMeta, HttpServletRequest request, String postUrl, AddressInput dataObject)
	{
		super(webAppMeta, request, dataObject, "ADDRESS_INPUT_FORM");
		
		
		// edit fields
		this.addWebElement(new TextFieldElement("Address 1", "address1").setRequired(true));
		this.addWebElement(new TextFieldElement("Address 2", "address2"));
		this.addWebElement(new TextFieldElement("City", "city").setRequired(true));
		this.addWebElement(new DropDownListElement("State", "state").setAddBlankRow(true).setDropDownListRef("usStateList").setRequired(true));
		this.addWebElement(new TextFieldElement("Zip", "zip").setRequired(true).setDependsOn("state"));
		this.addWebElement(new DateFieldElement("Start Date", "startDate").setRequired(true));
		this.addWebElement(new DateFieldElement("End Date", "endDate"));
		this.addWebElement(new CheckboxFieldElement("Is Primary", "primary"));
		
		// actions
		this.addWebActionElement(new ActionCommandSaveElement("Save", postUrl).setDisplayClass("btn btn-primary").setDisplayInEditMode());
	}
	
	private String getDisplayMessage()
	{
		AddressInput postedEntity = this.getDataObject();
		
		if(postedEntity != null)
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("Address 1: " + postedEntity.getAddress1() + "<br />");
			buffer.append("Address 2: " + postedEntity.getAddress2() + "<br />");
			buffer.append("City: " + postedEntity.getCity() + "<br />");
			buffer.append("State: " + postedEntity.getState() + "<br />");
			buffer.append("Zip: " + postedEntity.getZip() + "<br />");
			buffer.append("Start Date: " + DateUtils.convertDateToString(postedEntity.getStartDate(), "MM/dd/yyyy") + "<br />");
			buffer.append("End Date: " + DateUtils.convertDateToString(postedEntity.getEndDate(), "MM/dd/yyyy") + "<br />");
			buffer.append("Primary: " + postedEntity.getPrimary() + "<br />");

			return buffer.toString();
		}
		
		return "";
	}
	
	public boolean handleFormPost(HttpServletRequest request, HttpServletResponse response) throws SpringcafException
	{
		String postedFormName = WebRequestUtils.getFormName(request);
		
		if(this.matchedPostedFormName(postedFormName))
		{
			boolean valid = this.bindAndValidateData();

			if(valid)
			{
				this.addFormMessage(this.getDisplayMessage());
			}
		}

		return true;
	}

}
