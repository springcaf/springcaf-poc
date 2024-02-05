package com.springcaf.core.web.model;

public class WebErrorElementModel extends WebElementModel {

	private String errorMessage = null;
	
	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param errorMessage
	 */
	public WebErrorElementModel(String elementLabel, String elementName, String errorMessage)
	{
		super(elementLabel, elementName);
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public WebErrorElementModel setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		
		return this;
	}

	
}
