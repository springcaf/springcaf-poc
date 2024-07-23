package com.springcaf.starter.feature.ui.widget;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.model.element.ActionCommandSaveElement;
import com.springcaf.core.web.model.element.DropDownListElement;
import com.springcaf.core.web.model.element.TextFieldElement;
import com.springcaf.core.web.util.WebRequestUtils;
import com.springcaf.core.web.widget.alte.AlteFormWebFormWidget;
import com.springcaf.starter.feature.ui.model.DropdownInput;

public class DropdownInputWidget extends AlteFormWebFormWidget<DropdownInput> {
	
	/**
	 * Constructor
	 * @param tableDetailLink
	 */
	public DropdownInputWidget(WebAppMeta webAppMeta, HttpServletRequest request, String postUrl, DropdownInput dataObject)
	{
		super(webAppMeta, request, dataObject, "DROPDOWN_INPUT_FORM");
		
		// edit fields
		this.addWebElement(new DropDownListElement("Division", "divisionId").setAddBlankRow(true).setDropDownListRef("divisionIdList").setRequired(true));
		this.addWebElement(new DropDownListElement("Department", "departmentId").setAddBlankRow(true).setDropDownListRef("departmentIdList").setDependsOn("divisionId").setRequired(true));
		this.addWebElement(new DropDownListElement("Brand", "brandId").setAddBlankRow(true).setDropDownListRef("brandIdList").setDependsOn("departmentId").setRequired(true));
		this.addWebElement(new TextFieldElement("Brand Description", "brandDescription").setDependsOn("departmentId"));
		this.addWebElement(new DropDownListElement("Quarter", "quarterId").setAddBlankRow(true).setDropDownListRef("quarterIdList").setRequired(true));
		
		// actions
		this.addWebActionElement(new ActionCommandSaveElement("Save", postUrl).setDisplayClass("btn btn-primary").setDisplayInEditMode());
	}
	
	public String getDisplayMessage()
	{
		DropdownInput postedEntity = this.getDataObject();
		
		if(postedEntity != null)
		{
			StringBuffer buffer = new StringBuffer();
			buffer.append("Division ID: " + postedEntity.getDivisionId() + "<br />");
			buffer.append("Department ID: " + postedEntity.getDepartmentId() + "<br />");
			buffer.append("Brand ID: " + postedEntity.getBrandId() + "<br />");
			buffer.append("Brand Description: " + postedEntity.getBrandDescription() + "<br />");
			buffer.append("Quarter ID: " + postedEntity.getQuarterId() + "<br />");

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
