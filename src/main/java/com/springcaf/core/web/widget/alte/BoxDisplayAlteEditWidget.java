package com.springcaf.core.web.widget.alte;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.DateUtils;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.WebErrorElementModel;
import com.springcaf.core.web.model.form.WebFormModel;
import com.springcaf.core.web.util.SessionUtils;
import com.springcaf.core.web.util.WebRequestUtils;
import com.springcaf.core.web.util.WidgetUtils;

public abstract class BoxDisplayAlteEditWidget<T> extends BoxDisplayAlteBaseWidget {

	protected WebFormModel formModel = null;
	protected T dataObject = null;
	protected String formName = null;
	protected String pageUrl = null;
	protected String pageFullUrl = null;
	protected List<String> formMessages = new ArrayList<String>();
	protected List<String> formProcessingErrors = new ArrayList<String>();

	/**
	 * Constructor
	 * @param webAppMeta
	 * @param boxStyle
	 * @param showHeader
	 * @param showBody
	 * @param showFooter
	 * @param formName
	 * @param editObject
	 * @param pageUrl
	 */
	public BoxDisplayAlteEditWidget(WebAppMeta webAppMeta, 
			String boxStyle, 
			boolean showHeader, 
			boolean showBody,
			boolean showFooter,
			String formName,
			String pageUrl,
			T editObject) {
		super(webAppMeta, boxStyle, showHeader, showBody, showFooter);
		
		this.formName = formName;
		this.formModel = new WebFormModel();
		this.formModel.setFormName(formName);
		
		this.pageUrl = pageUrl;
		this.pageFullUrl = this.applicationBaseUrl + pageUrl;
		this.dataObject = editObject;
	}
	
	@Override
	protected void addBoxBodyWrapper(StringBuffer buffer) throws SpringcafException
	{
		// if there are any errors, render the errors here
		if(this.formModel.hasErrors())
		{
			buffer.append("<div class=\"bg-warning\">" + LINE_FEED);
			buffer.append("<p>Please fix the following input errors:</p>" + LINE_FEED);
			buffer.append("<ul>" + LINE_FEED);
			for(WebErrorElementModel error : this.formModel.getModelErrorElements())
			{
				buffer.append("<li>" + error.getErrorMessage() + "</li>" + LINE_FEED);
			}
			buffer.append("</ul>" + LINE_FEED);
			buffer.append("</div>" + LINE_FEED);
		}
		// if processing errors, such as database update failed, display here
		else if(this.hasProcessingError())
		{
			buffer.append("<div class=\"bg-warning\">" + LINE_FEED);
			for(String processingError : this.getFormProcessingErrors())
			{
				buffer.append("<p>" + processingError + "</p>" + LINE_FEED);
			}
			buffer.append("</div>" + LINE_FEED);
		}
		// if no error, display messages if there is any
		else if(this.hasMessage())
		{
			buffer.append("<div class=\"bg-success\">" + LINE_FEED);
			for(String message : this.getFormMessages())
			{
				buffer.append("<p>" + message + "</p>" + LINE_FEED);
			}
			buffer.append("</div>" + LINE_FEED);
		}
		
		this.addBoxBody(buffer);
	}
	
	protected void startFormFileUpload(StringBuffer buffer, String postUrl)
	{
		String actionUrl = this.applicationBaseUrl + postUrl;
		if(actionUrl.indexOf("csrf") < 0 && this.csrfToken != null)
		{
			String csrfString = "_csrf=" + csrfToken.getToken();
			if(actionUrl.indexOf("?") > 0)
			{
				actionUrl += "&" + csrfString;
			}
			else
			{
				actionUrl += "?" + csrfString;
			}
		}
		
		buffer.append("<form action=\"" + actionUrl + "\" method=\"post\" enctype=\"multipart/form-data\">" + LINE_FEED);
		buffer.append("<input type=\"hidden\" name=\"form_name\" value=\"" + formName + "\" />"+ LINE_FEED);
		buffer.append("<input type=\"hidden\" name=\"_csrf\" value=\"" + SessionUtils.getCsrfToken(webAppMeta.getRequest()).getToken() + "\" />" + LINE_FEED);
	}
	
	protected void startForm(StringBuffer buffer, String postUrl)
	{
		String actionUrl = this.applicationBaseUrl + postUrl;
		buffer.append("<form action=\"" + actionUrl + "\" method=\"post\">" + LINE_FEED);
		buffer.append("<input type=\"hidden\" name=\"form_name\" value=\"" + formName + "\" />"+ LINE_FEED);
		buffer.append("<input type=\"hidden\" name=\"_csrf\" value=\"" + SessionUtils.getCsrfToken(webAppMeta.getRequest()).getToken() + "\" />" + LINE_FEED);
	}
	
	protected void closeForm(StringBuffer buffer)
	{
		buffer.append("</form>" + LINE_FEED);
	}
	
	protected void registerFormElement(WebElementModel element)
	{
		this.formModel.addWebElement(element);
	}
	
	private String buildInputIdNameString(String elementName, boolean required, String inputClass)
	{
		String ret = "";
		if(!StringUtils.isNullOrEmpty(inputClass))
		{
			ret += " class=\"" + inputClass + "\"";
		}
		ret += " name=\"" + formName + "[" + elementName + "]\"";		
		ret += " id=\"" + formName + "_" + elementName + "\"";
		if(required)
		{
			ret += " required";
		}
		
		return ret;
	}
	
	protected void buildInputLabel(StringBuffer buffer, String label, String elementName, boolean required)
	{
		buffer.append("<label");
		if(required)
		{
			buffer.append(" class=\"required\"");
		}
		buffer.append(" for=\"" + this.getFormName() + "_" + elementName + "\">");
		buffer.append(label);
		if(required)
		{
			buffer.append(" <span class=\"required\">*</span>");
		}
		buffer.append("</label>" + LINE_FEED);
	}

	protected void addFormInputHiddenField(StringBuffer buffer, String elementName, String currentValue)
	{
		buffer.append("<input type=\"hidden\"");
		buffer.append(this.buildInputIdNameString(elementName, false, null));
		if(!StringUtils.isNullOrEmpty(currentValue))
		{
			buffer.append(" value=\"" + currentValue + "\"");
		}
		buffer.append(" />");

	}

	protected void addFormInputCheckboxField(StringBuffer buffer, String label, String elementName, boolean checked)
	{
		// register element
		this.registerFormElement(new WebElementModel(label, elementName));

		// render element
		String checkedFlag = "";
		if(checked)
		{
			checkedFlag = " checked";
		}

        buffer.append("<input type=\"checkbox\"");
		buffer.append(this.buildInputIdNameString(elementName, false, null));
		buffer.append(" " + checkedFlag);
		buffer.append(" />");

	}

	protected void addFormInputTextField(StringBuffer buffer, String label, String elementName, String currentValue, String placeholder, boolean required, int maxLength, String inputClass)
	{
		// register element
		this.registerFormElement(new WebElementModel(label, elementName).setRequired(required).setMaxLength(maxLength));

		// render element
		buffer.append("<input type=\"text\"");
		buffer.append(this.buildInputIdNameString(elementName, required, inputClass));
		if(!StringUtils.isNullOrEmpty(currentValue))
		{
			buffer.append(" value=\"" + currentValue + "\"");
		}
		if(maxLength > 0)
		{
			buffer.append(" maxlength=\"" + maxLength + "\"");
		}
		if(!StringUtils.isNullOrEmpty(placeholder))
		{
			buffer.append(" placeholder=\"" + placeholder + "\"");
		}
		buffer.append(" />");

	}

	protected void addFormInputFileField(StringBuffer buffer, String label, String elementName, String currentValue, String placeholder, boolean required, int maxLength, String inputClass)
	{
		// register element
		this.registerFormElement(new WebElementModel(label, elementName).setRequired(required).setMaxLength(maxLength));

		// render element
		buffer.append("<input type=\"file\"");
		buffer.append(this.buildInputIdNameString(elementName, required, inputClass));
		if(!StringUtils.isNullOrEmpty(currentValue))
		{
			buffer.append(" value=\"" + currentValue + "\"");
		}
		if(maxLength > 0)
		{
			buffer.append(" maxlength=\"" + maxLength + "\"");
		}
		if(!StringUtils.isNullOrEmpty(placeholder))
		{
			buffer.append(" placeholder=\"" + placeholder + "\"");
		}
		buffer.append(" />");

	}

	protected void addFormInputPasswordField(StringBuffer buffer, String label, String elementName, String placeholder, boolean required, int maxLength, String inputClass)
	{
		// register element
		this.registerFormElement(new WebElementModel(label, elementName).setRequired(required).setMaxLength(maxLength));

		// render element
		buffer.append("<input type=\"password\" ");
		buffer.append(this.buildInputIdNameString(elementName, required, inputClass));
		if(maxLength > 0)
		{
			buffer.append(" maxlength=\"" + maxLength + "\"");
		}
		if(!StringUtils.isNullOrEmpty(placeholder))
		{
			buffer.append(" placeholder=\"" + placeholder + "\"");
		}
		buffer.append(" />");
	}
	
	protected void addFormInputTextArea(StringBuffer buffer, String label, String elementName, String currentValue, String placeholder, boolean required, String inputClass, int rowHeight)
	{
		// register element
		this.registerFormElement(new WebElementModel(label, elementName).setRequired(required));

		// render element
		buffer.append("<textarea ");
		buffer.append(this.buildInputIdNameString(elementName, required, inputClass));
		if(rowHeight > 0)
		{
			buffer.append(" rows=\"" + rowHeight + "\"");
		}
		if(!StringUtils.isNullOrEmpty(placeholder))
		{
			buffer.append(" placeholder=\"" + placeholder + "\"");
		}
		buffer.append(">");
		if(!StringUtils.isNullOrEmpty(currentValue))
		{
			buffer.append(currentValue);
		}
		buffer.append("</textarea>");
	}

	protected void addFormInputDateField(StringBuffer buffer, String label, String elementName, Date currentValue, String placeholder, boolean required, String inputClass)
	{
		// register element
		this.registerFormElement(new WebElementModel(label, elementName).setRequired(required));

		// render element
		buffer.append("<input type=\"text\"");
		buffer.append(this.buildInputIdNameString(elementName, required, inputClass));
		if(currentValue != null)
		{
			buffer.append(" value=\"" + DateUtils.convertDateToString(currentValue, DateUtils.DATE_STANDARD_FORMAT) + "\"");
		}
		if(!StringUtils.isNullOrEmpty(placeholder))
		{
			buffer.append(" placeholder=\"" + placeholder + "\"");
		}
		buffer.append(" />" + LINE_FEED);
		buffer.append("<script>" + LINE_FEED);
		buffer.append("$(function () {" + LINE_FEED);
		buffer.append("$('#" + formName + "_" + elementName + "').datepicker({" + LINE_FEED);
		buffer.append("    autoclose: true" + LINE_FEED);
		buffer.append("})" + LINE_FEED);
		buffer.append("})" + LINE_FEED);
		buffer.append("</script>" + LINE_FEED);
	}
	
	protected void addFormInputDropdownList(StringBuffer buffer, String label, String elementName, KeyValueListModel dropdownList, boolean addBlankRow, String currentValue, boolean required, String inputClass)
	{
		// register element
		this.registerFormElement(new WebElementModel(label, elementName).setRequired(required));

		// render element
		// build <select> section
		buffer.append("<select ");
		buffer.append(this.buildInputIdNameString(elementName, required, inputClass));
		buffer.append(">" + LINE_FEED);
		
		WidgetUtils.renderDropdownListOptions(buffer, dropdownList, addBlankRow, currentValue);
		
		buffer.append("</select>" + LINE_FEED);
		
	}

	protected void addFormSubmitButton(StringBuffer buffer, String buttonName, String inputClass)
	{
		buffer.append("<button type=\"submit\" ");
		if(!StringUtils.isNullOrEmpty(inputClass))
		{
			buffer.append(" class=\"" + inputClass + "\"");
		}
		buffer.append(" value=\"submit\">" + buttonName + "</button>" + LINE_FEED);
	}

	/**
	 * Bind and validate the posted form data
	 * @return
	 * @throws SpringcafException
	 */
	public boolean bindAndValidateData() throws SpringcafException {

		// bind and validate data
		WebRequestUtils.bindAndValidateFormPostRequest(formModel, webAppMeta.getRequest());
		formModel.bindFormDataToObject(dataObject);
		
		return !formModel.hasErrors();
	}

	public WebFormModel getFormModel() {
		return formModel;
	}

	public void setFormModel(WebFormModel formModel) {
		this.formModel = formModel;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	protected void addFormGroupReadOnlyField(StringBuffer buffer, String label, String elementName, String elementValue)
	{
		// build the form input section
		this.startDiv(buffer, "form-group");
		this.buildInputLabel(buffer, label, elementName, false);
		buffer.append("<span>" + elementValue + "</span>");
		this.closeDiv(buffer);
	}
	
	protected void addFormGroupCheckboxField(StringBuffer buffer, String label, String elementName, boolean checked)
	{
		// build the form input section
		this.startDiv(buffer, "form-group");
		this.buildInputLabel(buffer, label, elementName, false);
		buffer.append("<label>");
		this.addFormInputCheckboxField(buffer, label, elementName, checked);
		buffer.append("</label>");
		this.closeDiv(buffer);
	}
	
	protected void addFormGroupPasswordField(StringBuffer buffer, String label, String elementName, String placeHolder, boolean required, int maxLength)
	{
		// build the form input section
		this.startDiv(buffer, "form-group");
		this.buildInputLabel(buffer, label, elementName, required);
		this.addFormInputPasswordField(buffer, label, elementName, placeHolder, required, maxLength, "form-control");
		this.closeDiv(buffer);
	}
	
	protected void addFormGroupTextField(StringBuffer buffer, String label, String elementName, boolean required, int maxLength, String elementValue)
	{
		this.addFormGroupTextField(buffer, label, elementName, "", required, maxLength, elementValue);
	}
	
	protected void addFormGroupTextField(StringBuffer buffer, String label, String elementName, String placeHolder, boolean required, int maxLength, String elementValue)
	{
		// build the form input section
		this.startDiv(buffer, "form-group");
		this.buildInputLabel(buffer, label, elementName, required);
		this.addFormInputTextField(buffer, label, elementName, elementValue, placeHolder, required, maxLength, "form-control");
		this.closeDiv(buffer);
	}
	
	protected void addFormGroupFileField(StringBuffer buffer, String label, String elementName, String placeHolder, boolean required, int maxLength, String elementValue)
	{
		// build the form input section
		this.startDiv(buffer, "form-group");
		this.buildInputLabel(buffer, label, elementName, required);
		this.addFormInputFileField(buffer, label, elementName, elementValue, placeHolder, required, maxLength, "form-control");
		this.closeDiv(buffer);
	}
	
	protected void addFormGroupTextArea(StringBuffer buffer, String label, String elementName, boolean required, String elementValue, int size)
	{
		this.startDiv(buffer, "form-group");
		this.buildInputLabel(buffer, label, elementName, required);
		this.addFormInputTextArea(buffer, label, elementName, elementValue, "", required, "form-control", size);
		this.closeDiv(buffer);
	}

	protected void addFormGroupDropdownList(StringBuffer buffer, String label, String elementName, boolean required, String elementValue, KeyValueListModel optionsList, boolean addBlank)
	{
		this.startDiv(buffer, "form-group");
		this.buildInputLabel(buffer, label, elementName, required);
		this.addFormInputDropdownList(buffer, label, elementName, optionsList, addBlank, elementValue, required, "form-control");
		this.closeDiv(buffer);
	}
	
	protected void addFormGroupSubmitButton(StringBuffer buffer, String label, String cancelLink)
	{
		this.startDiv(buffer, "form-group");
		this.addFormSubmitButton(buffer, label, "btn btn-primary");
		if(!StringUtils.isNullOrEmpty(cancelLink))
		{
			buffer.append(FILLER2 + cancelLink);
		}
		this.closeDiv(buffer);
	}

	public List<String> getFormMessages() {
		return formMessages;
	}

	public void setFormMessages(List<String> formMessages) {
		this.formMessages = formMessages;
	}

	public BoxDisplayAlteEditWidget<T> addFormMessage(String message)
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

	public BoxDisplayAlteEditWidget<T> addProcessingError(String processingError)
	{
		this.formProcessingErrors.add(processingError);
		
		return this;
	}
	
	public boolean hasProcessingError()
	{
		return this.formProcessingErrors.size() > 0;
	}
	
	public void addErrorMessage(String label, String field, String errorMessage)
	{
		formModel.addWebErrorElement(new WebErrorElementModel(label, field, errorMessage));
	}

	public boolean hasErrors()
	{
		if(this.hasProcessingError() || (this.formModel != null && this.formModel.hasErrors()))
		{
			return true;
		}
		
		return false;
	}
	
	public MultipartFile getUploadedFile(HttpServletRequest request, String elementName)
	{
		MultipartFile dataFile = null;
		String dataFileFieldName = this.getFormName() + "[" + elementName + "]";
		if(request instanceof MultipartHttpServletRequest)
		{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			dataFile = multipartRequest.getFile(dataFileFieldName);
		}

		return dataFile;
	}
	
	public T getEditObject()
	{
		return this.dataObject;
	}
	
	public void setEditObject(T dataObject)
	{
		this.dataObject = dataObject;
	}
}
