package com.springcaf.core.web.widget.alte;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.web.csrf.CsrfToken;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.model.WebActionElementModel;
import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.WebErrorElementModel;
import com.springcaf.core.web.model.WebListSet;
import com.springcaf.core.web.model.WebModel;
import com.springcaf.core.web.model.element.ActionCommandSaveElement;
import com.springcaf.core.web.model.element.CheckboxFieldElement;
import com.springcaf.core.web.model.element.DateFieldElement;
import com.springcaf.core.web.model.element.DropDownListElement;
import com.springcaf.core.web.model.element.FileFieldElement;
import com.springcaf.core.web.model.element.HeadingElement;
import com.springcaf.core.web.model.element.HiddenFieldElement;
import com.springcaf.core.web.model.element.MultiCheckboxFieldElement;
import com.springcaf.core.web.model.element.PasswordFieldElement;
import com.springcaf.core.web.model.element.RadioboxFieldElement;
import com.springcaf.core.web.model.element.TextAreaElement;
import com.springcaf.core.web.model.element.TextFieldElement;
import com.springcaf.core.web.model.element.TextFieldPrePostFixElement;
import com.springcaf.core.web.model.form.WebFormModel;
import com.springcaf.core.web.model.nav.NavUrlItemModel;
import com.springcaf.core.web.util.NavUtils;
import com.springcaf.core.web.util.WebRequestUtils;
import com.springcaf.core.web.util.WidgetUtils;
import com.springcaf.core.web.widget.FormWebFormWidget;

public class AlteFormWebFormWidget<T> extends FormWebFormWidget<T> {
	
	private HttpServletRequest request = null;
	protected WebAppMeta webAppMeta = null;

	/**
	 * Constructor
	 * @param webAppMeta
	 * @param request
	 * @param dataObject
	 * @param formName
	 */
	public AlteFormWebFormWidget(WebAppMeta webAppMeta, HttpServletRequest request, T dataObject, String formName)
	{
		super(request == null ? "" : request.getContextPath());
		this.request = request;
		this.model = new WebFormModel();
		this.model.setFormName(formName);
		this.dataObject = dataObject;
		this.webAppMeta = webAppMeta;
		
		// create and attach csrf token
		if(request != null)
		{
			CsrfToken csrfToken = (CsrfToken)request.getAttribute("_csrf");
			this.setCsrfToken(csrfToken);
		}
	}
	
	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {
		
		// create outer div
		buffer.append("<div>" + LINE_FEED);
		
		// outer layer depending on edit mode 
		if(this.editMode)
		{
			// build action URL
			String actionUrl = this.applicationBaseUrl + this.getFormActionUrl();

			String encType = this.getModel().getEnctype();
			String encString = "";
			if(!StringUtils.isNullOrEmpty(encType))
			{
				encString = " enctype=\"" + encType + "\"";
				
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
 			}
			
			if(this.horizontalSplit != null)
			{
				buffer.append("<form class=\"form-horizontal\"");
			}
			else
			{
				buffer.append("<form ");
			}
			if(!StringUtils.isNullOrEmpty(this.getFormId()))
			{
				buffer.append(" id=\"" + this.getFormId() + "\"");
			}
			buffer.append(" action=\"" + actionUrl + "\" method=\"post\"" + encString + ">" + LINE_FEED);
			
			// if there are any errors, render the errors here
			if(this.model.hasErrors())
			{
				buffer.append("<div class=\"bg-warning\">" + LINE_FEED);
				buffer.append("<p>Please fix the following input errors:</p>" + LINE_FEED);
				buffer.append("<ul>" + LINE_FEED);
				for(WebErrorElementModel error : this.model.getModelErrorElements())
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
			
			// add csrf token and formName hidden fields
			if(this.csrfToken != null)
			{
				buffer.append("<input type=\"hidden\" name=\"" + csrfToken.getParameterName() + "\" value=\""  + csrfToken.getToken() + "\" />" + LINE_FEED);
				
				if(!StringUtils.isNullOrEmpty(this.model.getFormName()))
				{
					buffer.append("<input type=\"hidden\" name=\"form_name\" value=\""  + this.model.getFormName() + "\" />" + LINE_FEED);
				}
			}
		}
		else
		{
		}
		
		// render elements
		this.renderFormElements(buffer);
		
		// render buttons
		this.renderButtons(buffer);
		
		// closing the outer layer
		if(this.editMode)
		{
			buffer.append("</form>" + LINE_FEED);
		}
		else
		{
		}

		// closing the outer div
		buffer.append("</div>" + LINE_FEED);
	}

	/**
	 * Render form elements
	 * @param buffer
	 * @throws SpringcafException 
	 */
	private void renderFormElements(StringBuffer buffer) throws SpringcafException
	{
		if(this.model == null)
		{
			return;
		}
		
		for(WebElementModel element : this.model.getModelElements())
		{
			if(element instanceof HiddenFieldElement)
			{
				// render without occupying a spot in the grid
				this.renderHiddenWebElement(buffer, model, element, dataObject);
			}
			else if (element instanceof CheckboxFieldElement) 
			{
				// render special checkbox element
				this.renderCheckboxWebElement(buffer, model, element, dataObject, editMode);
			} 
			else if (element instanceof MultiCheckboxFieldElement) 
			{
				List<String> values = ((MultiCheckboxFieldElement) element).getCheckValues();
				this.renderMultiCheckboxWebElement(buffer, model, element, values, editMode);
			} 
			else if (element instanceof RadioboxFieldElement) 
			{
				buffer.append("<div class=\"form-group\">" + LINE_FEED);
				List<String> values = ((RadioboxFieldElement) element).getRadioValues();
				this.renderRadioWebElement(buffer, model, element, values, editMode);
				buffer.append(LINE_FEED + "</div>" + LINE_FEED);
			} 
			else if (element instanceof HeadingElement) 
			{
				this.renderHeadingWebElement(buffer, model, element, editMode);
			}			
			else
			{
                buffer.append("<div class=\"form-group\">" + LINE_FEED);
                // render label
                this.renderWebElementLabel(buffer, model, element, dataObject, editMode, this.horizontalSplit);
                
                // render element content
                KeyValueListModel listValues = null;
                String restUrl = null;
                if(element instanceof DropDownListElement)
                {
                	String listRefName = ((DropDownListElement)element).getDropDownListRef();
                	listValues = this.getKeyValueList(listRefName);
                	List<String> dependentList = this.model.getDirectElementsDependingOnThis(element.getElementName());
                	if(dependentList != null && dependentList.size() > 0)
                	{
                    	restUrl = this.getRestUrl(listRefName);
                	}
                }
                
                this.renderWebElementContent(buffer, model, element, dataObject, listValues, restUrl, editMode, this.horizontalSplit);

                // close row
                buffer.append(LINE_FEED + "</div>" + LINE_FEED);
			}
		}
	}

	/**
	 * Render the buttons
	 * @param buffer
	 */
	private void renderButtons(StringBuffer buffer)
	{
		if(this.model == null)
		{
			return;
		}
		
		if(this.editMode)
		{
			List<WebActionElementModel>editActions = this.model.getEditModeActions();
			if(editActions.size() > 0)
			{
				// render the buttons
				buffer.append("<div class=\"form-group\">");
				
				if(this.horizontalSplit != null)
				{
					buffer.append("<label for=\"buttons\" class=\"col-sm-" + this.horizontalSplit + " control-label\"></label>");
					buffer.append("<div class=\"col-sm-" + (12-horizontalSplit) + "\">" + LINE_FEED);
				}
				
				for(WebActionElementModel action : editActions)
				{
					if(action instanceof ActionCommandSaveElement)
					{
						String classStr = "";
						if(!StringUtils.isNullOrEmpty(action.getDisplayClass()))
						{
							classStr = " class=\"" + action.getDisplayClass() + "\"";
						}
						buffer.append("<button" + classStr + " type=\"submit\" name=\"submit_btn\">" + action.getElementLabel() + "</button>");
					}
					else if(!StringUtils.isNullOrEmpty(action.getDisplayClass()))
					{
						buffer.append(FILLER2);
						buffer.append(NavUtils.rendereNavUrlModelToHtml(new NavUrlItemModel(action.getElementLabel(), action.getActionUrl(), action.getElementName()), applicationBaseUrl, action.getDisplayClass()));
					}
					else
					{
						buffer.append(FILLER6);
						buffer.append(NavUtils.rendereNavUrlModelToHtml(new NavUrlItemModel(action.getElementLabel(), action.getActionUrl(), action.getElementName()), applicationBaseUrl, ""));
					}
				}
				
				if(this.horizontalSplit != null)
				{
					buffer.append("</div>" + LINE_FEED);
				}

				buffer.append("</div>");	// form group
			}
		}
		else
		{
			List<WebActionElementModel>readActions = this.model.getReadModeActions();
			for(WebActionElementModel action : readActions)
			{
				buffer.append(NavUtils.rendereNavUrlModelToHtml(new NavUrlItemModel(action.getElementLabel(), action.getActionUrl(), action.getElementName()), applicationBaseUrl, "btn btn-default"));
			}
			
		}
		
	}
	
	public void loadDropdownListSet(WebListSet webListSet) throws SpringcafException
	{
		if(webListSet != null)
		{
			for(String key : webListSet.getCommonListMap().keySet())
			{
				this.addKeyValueList(key, webListSet.getList(key));
			}
		}
	}
	
	private void renderWebElementLabel(StringBuffer buffer, WebModel model, WebElementModel element, Object dataObject, boolean editMode, Integer horizontalSplit) throws SpringcafException
	{
		if(element instanceof HiddenFieldElement || element instanceof CheckboxFieldElement)
		{
			throw new SpringcafException("HiddenFieldElement or CheckboxFieldElement not supported in this function");
		}
		
		// setting up edit controls
		if(editMode && !element.isReadonly())
		{
			buffer.append("<label");
			if(element.isRequired())
			{
				if(horizontalSplit != null)
				{
					buffer.append(" class=\"required col-sm-" + horizontalSplit + " control-label\"");
				}
				else
				{
					buffer.append(" class=\"required\"");
				}
			}
			else if(horizontalSplit != null)
			{
				buffer.append(" class=\"col-sm-" + horizontalSplit + " control-label\"");
			}
				
			buffer.append(" for=\"" + model.getFormName() + "_" + element.getElementName() + "\">");
			buffer.append(element.getElementLabel());
			if(element.isRequired())
			{
				buffer.append(" <span class=\"required\">*</span>");
			}
			buffer.append("</label>");
		}
		else
		{
			buffer.append("<label>" + element.getElementLabel() + "</label>");
		}
	}
	
	private void renderWebElementContent(StringBuffer buffer, WebModel model, WebElementModel element, Object dataObject, KeyValueListModel listValues, String restUrl, boolean editMode, Integer horizontalSplit) throws SpringcafException
	{
		if(element instanceof HiddenFieldElement || element instanceof CheckboxFieldElement)
		{
			throw new SpringcafException("HiddenFieldElement or CheckboxFieldElement not supported in this function");
		}
		
		if(horizontalSplit != null)
		{
			buffer.append("<div class=\"col-sm-" + (12-horizontalSplit) + "\">" + LINE_FEED);
		}
		
		// setting up edit controls
		if(editMode && !element.isReadonly())
		{
			renderWebElementEditMode(buffer, model, element, dataObject, listValues, restUrl);
		}
		else
		{
			renderWebElementReadMode(buffer, model, element, dataObject, listValues);
		}
		
		if(horizontalSplit != null)
		{
			buffer.append("</div>" + LINE_FEED);
		}

	}
	
	private void renderHiddenWebElement(StringBuffer buffer, WebModel model, WebElementModel element, Object dataObject) throws SpringcafException
	{
		// process hidden element
		if(element instanceof HiddenFieldElement)
		{
			String value = element.getElementDefaultValue();
			if(StringUtils.isNullOrEmpty(value))
			{
				value = model.getObjectMemberValueAsString(dataObject, element.getElementName(), "");
			}
			buffer.append("<input type=\"hidden\"" + buildNameIdString(model, element) + " value=\"" + value + "\" />");
		}
	}
	
	private void renderHeadingWebElement(StringBuffer buffer, WebModel model, WebElementModel element, boolean editMode) {
		if(element instanceof HeadingElement) {
			buffer.append("<" + element.getElementName() + ">");
			buffer.append(element.getElementLabel());
			buffer.append("</" + element.getElementName() + ">");
			buffer.append("<p></p>");
		}
	}
	
	private void renderRadioWebElement(StringBuffer buffer, WebModel model, WebElementModel element, List<String> values, boolean editMode) {
		if(element instanceof RadioboxFieldElement) {
			buffer.append("<div class=\"radiobuttonsGroup\">");
			if(values.size()>0) {
				buffer.append("<p><b>" + element.getElementLabel() + "</b></p>");
				for(String value : values) {
					buffer.append("<label>");
					buffer.append("<input type='radio'");
					buffer.append(" name=" + element.getElementName());
					buffer.append(" value=" + value);
					buffer.append(">" + FILLER1 + value);
					buffer.append("</label><br/>");
				}
			}
			buffer.append("</div>");
		}
	}
	
	private void renderCheckboxWebElement(StringBuffer buffer, WebModel model, WebElementModel element, Object dataObject, boolean editMode) throws SpringcafException
	{
		// process checkbox element
		if(element instanceof CheckboxFieldElement)
		{
            buffer.append("<div class=\"checkbox form-check form-check-inline\">");

            // get the value
            String value = element.getElementDefaultValue();
			if(StringUtils.isNullOrEmpty(value))
			{
				value = model.getObjectMemberValueAsString(dataObject, element.getElementName(), "");
			}
			
			if(editMode && !element.isReadonly())
			{
				boolean checked = WebRequestUtils.convertCheckboxToTrueFalse(value);
				String checkedFlag = "";
				if(checked)
				{
					checkedFlag = " checked";
				}
				String idNameString = buildNameIdString(model, element);
				
				if(((CheckboxFieldElement)element).isLabelOnLeft())
				{
					buffer.append("  <label class=\"form-check-label\" ><b>" + element.getElementLabel() + "</b></label>");
		            buffer.append("  <input class=\"form-check-input\" type=\"checkbox\"" + idNameString + checkedFlag + "/> ");
				}
				else
				{
		            buffer.append("<label>");
		            buffer.append("  <input type=\"checkbox\"" + idNameString + checkedFlag + "> <b>" + element.getElementLabel() + "</b>");
		            buffer.append("</label>");
				}
			}
			else
			{
				buffer.append("<p><b>" + element.getElementLabel() + "</b>: " + value + "</p>");
			}

			buffer.append("</div>");
		}
	}
	
	private void renderMultiCheckboxWebElement(StringBuffer buffer, WebModel model, WebElementModel element, List<String> values, boolean editMode) throws SpringcafException
	{
		// process checkbox element
		if(element instanceof CheckboxFieldElement)
		{
            buffer.append("<div class=\"checkbox\">");
					
			if(values.size()>0) {
				buffer.append("<p><b>" + element.getElementLabel() + "</b></p>");
				for(String value : values) {
					buffer.append("<label>");
					buffer.append("<input type='checkbox'");
					buffer.append(" name=" + element.getElementName());
					buffer.append(" value=" + value);
					buffer.append(">" + FILLER1 + value);
					buffer.append("</label></br>");
				}
			}

			buffer.append("</div>");
		}
	}
	
	private void renderWebElementEditMode(StringBuffer buffer, WebModel model, WebElementModel element, Object dataObject, KeyValueListModel listValues, String restUrl) throws SpringcafException
	{
		// setting up edit controls
		// render the actual edit controls
		String elementValue = model.getObjectMemberValueAsString(dataObject, element.getElementName(), element.getElementDefaultValue());
		String idNameString = buildNameIdString(model, element);
		String requiredString = "";
		if(element.isRequired())
		{
			requiredString = " required";
		}
		
		if(element instanceof TextAreaElement)
		{
			buffer.append("<textarea rows=\"" + ((TextAreaElement)element).getElementHeight() + "\" class=\"form-control\"");
			buffer.append(idNameString + requiredString + ">");
			buffer.append(elementValue);
			buffer.append("</textarea>");
		}
		else if(element instanceof TextFieldElement)
		{
			if(!StringUtils.isNullOrEmpty(element.getMask())) {
				buffer.append("<input type=\"text\" class=\"form-control " + element.getMask() + "\"");
			}
			else {
				buffer.append("<input type=\"text\" class=\"form-control\"");
			}

			if(element.getMaxLength() > 0)
			{
				buffer.append(" maxlength=\"" + element.getMaxLength() + "\"");
			}
			
			if(!StringUtils.isNullOrEmpty(element.getPlaceHolder()))
			{
				String placeHolder = " placeholder=\"" + element.getPlaceHolder() + "\"";
				buffer.append(placeHolder);
			}
			buffer.append(idNameString + requiredString + " value=\"");
			buffer.append(elementValue);
			buffer.append("\" />");
			if(!StringUtils.isNullOrEmpty(element.getHelperText())){
				buffer.append("<p class='form-helper-text'>" + element.getHelperText() + "</p>");
			}
		}
		else if(element instanceof TextFieldPrePostFixElement)
		{
			buffer.append(((TextFieldPrePostFixElement) element).getPrefix() + "<input type=\"text\" class=\"form-control\"");
			if(element.getMaxLength() > 0)
			{
				buffer.append(" maxlength=\"" + element.getMaxLength() + "\"");
			}
			if(!StringUtils.isNullOrEmpty(element.getPlaceHolder()))
			{
				String placeHolder = " placeholder=\"" + element.getPlaceHolder() + "\"";
				buffer.append(placeHolder);
			}
			buffer.append(idNameString + requiredString + " value=\"");
			buffer.append(elementValue);
			buffer.append("\" />" + ((TextFieldPrePostFixElement) element).getPostfix());
		}
		else if(element instanceof DropDownListElement)
		{
			buffer.append("<select class=\"form-control\"");
			buffer.append(idNameString);
			if(!StringUtils.isNullOrEmpty(restUrl))
			{
				// add on change
				String onChangeStr = " onchange=\"elementvaluechanged('" + restUrl + "','" + (model.getFormName() + "_" + element.getElementName()) + "');\"";
				buffer.append(onChangeStr);
			}
			buffer.append(requiredString);
			buffer.append(" >");
			
			WidgetUtils.renderDropdownListOptions(buffer, listValues, ((DropDownListElement)element).isAddBlankRow(), elementValue);
			
			buffer.append("</select>");
		}
		else if(element instanceof FileFieldElement)
		{
			buffer.append("<input type=\"file\"");
			if(element.getMaxLength() > 0)
			{
				buffer.append(" maxlength=\"" + element.getMaxLength() + "\"");
			}
			buffer.append(idNameString + requiredString + " value=\"");
			buffer.append(elementValue);
			buffer.append("\" />");
		}
		else if(element instanceof DateFieldElement)
		{
			buffer.append("<div class=\"input-group date\">");
            buffer.append("<div class=\"input-group-addon\">");
            buffer.append("<i class=\"fa fa-calendar\"></i>");
            buffer.append("</div>");
            buffer.append("<input type=\"text\" class=\"form-control pull-right\"" + idNameString + requiredString + " value=\"");
			buffer.append(elementValue);
			buffer.append("\" />" + LINE_FEED);
			
			// add script to activate the datepicker
			String idValue = buildIdValue(model, element);
			buffer.append("<script>" + LINE_FEED);
			buffer.append("$(function () {" + LINE_FEED);
			buffer.append("$('#" + idValue + "').datepicker({" + LINE_FEED);
			buffer.append("    autoclose: true" + LINE_FEED);
			buffer.append("  })" + LINE_FEED);
		    buffer.append("})" + LINE_FEED);
		    buffer.append("</script>" + LINE_FEED);
			
			buffer.append("</div>");
		}
		else if(element instanceof PasswordFieldElement)
		{
			buffer.append("<input type=\"password\" class=\"form-control\"");
			if(element.getMaxLength() > 0)
			{
				buffer.append(" maxlength=\"" + element.getMaxLength() + "\"");
			}
			if(!StringUtils.isNullOrEmpty(element.getPlaceHolder()))
			{
				String placeHolder = " placeholder=\"" + element.getPlaceHolder() + "\"";
				buffer.append(placeHolder);
			}
			buffer.append(idNameString + requiredString + " value=\"");
			buffer.append(elementValue);
			buffer.append("\" />");
		}
		else
		{
			buffer.append("<input type=\"text\" class=\"form-control\"");
			if(element.getMaxLength() > 0)
			{
				buffer.append(" maxlength=\"" + element.getMaxLength() + "\"");
			}
			if(!StringUtils.isNullOrEmpty(element.getPlaceHolder()))
			{
				String placeHolder = " placeholder=\"" + element.getPlaceHolder() + "\"";
				buffer.append(placeHolder);
			}
			buffer.append(idNameString + requiredString + " value=\"");
			buffer.append(elementValue);
			buffer.append("\" />");
		}
		
	}

	private void renderWebElementReadMode(StringBuffer buffer, WebModel model, WebElementModel element, Object dataObject, KeyValueListModel listValues) throws SpringcafException
	{
		String elementValue = model.getObjectMemberValueAsString(dataObject, element.getElementName(), "");
		if(elementValue == null)
		{
			elementValue = "";
		}
		
		if(element instanceof TextAreaElement)
		{
			buffer.append("<p>" + elementValue.replaceAll("\r", "<br />") + "</p>");
		}
		else if(element instanceof TextFieldElement)
		{
			buffer.append("<p>" + elementValue + "</p>");
		}
		else if(element instanceof TextFieldPrePostFixElement)
		{
			buffer.append("<p>" + elementValue + "</p>");
		}
		else if(element instanceof DropDownListElement)
		{
			String displayValue = "";
			if(listValues != null && listValues.getValueFromList(elementValue) != null)
			{
				displayValue = listValues.getValueFromList(elementValue);
			}
			buffer.append("<p>" + displayValue + "</p>");
		}
		else if(element instanceof FileFieldElement)
		{
			buffer.append("<p>" + elementValue + "</p>");
		}
		else if(element instanceof PasswordFieldElement)
		{
			buffer.append("<p>" + "********" + "</p>");
		}
		else
		{
			buffer.append("<p>" + elementValue + "</p>");
		}
	}

	/**
	 * Build the name and ID part of the input HTML element
	 * @param model
	 * @param element
	 * @return
	 */
	private String buildNameIdString(WebModel model, WebElementModel element)
	{
		return " name=\"" + model.getFormName() + "[" + element.getElementName() + "]\" id=\"" + model.getFormName() + "_" + element.getElementName() + "\"";
	}
	
	private String buildIdValue(WebModel model, WebElementModel element)
	{
		return model.getFormName() + "_" + element.getElementName();
	}
	
	/**
	 * Bind and validate the posted form data
	 * @return
	 * @throws SpringcafException
	 */
	public boolean bindAndValidateData() throws SpringcafException {

		// bind and validate data
		WebRequestUtils.bindAndValidateFormPostRequest(this.model, this.request);
		model.bindFormDataToObject(dataObject);
		
		return !model.hasErrors();
	}

}
