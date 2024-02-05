package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebActionElementModel;

public class ActionNavLinkElement extends WebActionElementModel {
	
	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param navBaseUrl
	 */
	public ActionNavLinkElement(String elementLabel, String elementName, String navBaseUrl)
	{
		super(elementLabel, elementName, navBaseUrl, elementName);
	}

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param navBaseUrl
	 * @param navKeyElementName
	 */
	public ActionNavLinkElement(String elementLabel, String elementName, String navBaseUrl, String navKeyElementName)
	{
		super(elementLabel, elementName, navBaseUrl, navKeyElementName);
	}

}
