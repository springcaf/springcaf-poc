package com.springcaf.starter.feature.ui.widget;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.model.element.ActionCommandSaveElement;
import com.springcaf.core.web.model.element.DropDownListElement;
import com.springcaf.core.web.model.element.HiddenFieldElement;
import com.springcaf.core.web.model.element.TextFieldElement;
import com.springcaf.core.web.util.WebRequestUtils;
import com.springcaf.core.web.widget.alte.AlteFormWebFormWidget;
import com.springcaf.starter.feature.dataservice.jdbc.model.Person;
import com.springcaf.starter.feature.dataservice.jdbc.service.PersonDataService;

public class PersonInputWidget extends AlteFormWebFormWidget<Person> {
	
	/**
	 * Constructor
	 * @param tableDetailLink
	 */
	public PersonInputWidget(WebAppMeta webAppMeta, HttpServletRequest request, String postUrl, Person dataObject)
	{
		super(webAppMeta, request, dataObject, "PERSON_INPUT_FORM");
		
		// edit fields
		this.addWebElement(new HiddenFieldElement("Person ID", "personId"));
		this.addWebElement(new TextFieldElement("First Name", "firstName").setRequired(true));
		this.addWebElement(new TextFieldElement("Middle Name", "middleName").setRequired(false));
		this.addWebElement(new TextFieldElement("Last Name", "lastName").setRequired(true));
		this.addWebElement(new DropDownListElement("Home State", "homeState").setAddBlankRow(true).setDropDownListRef("usStateList").setRequired(false));
		
		// actions
		this.addWebActionElement(new ActionCommandSaveElement("Save", postUrl).setDisplayClass("btn btn-primary").setDisplayInEditMode());
	}
	
	public boolean handleFormPost(HttpServletRequest request, HttpServletResponse response, PersonDataService personDataService) throws SpringcafException
	{
		String postedFormName = WebRequestUtils.getFormName(request);
		
		if(this.matchedPostedFormName(postedFormName))
		{
			boolean valid = this.bindAndValidateData();

			if(valid)
			{
				Person postedEntity = this.getDataObject();
				try
				{
					personDataService.savePerson(postedEntity, webAppMeta.getEncodedUserId());
				}
				catch(Exception ex)
				{
					throw new SpringcafException(ex);
				}

			}
		}

		return true;
	}

}
