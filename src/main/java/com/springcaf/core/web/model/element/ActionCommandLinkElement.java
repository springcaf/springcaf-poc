package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebActionElementModel;

public class ActionCommandLinkElement extends WebActionElementModel {
	
	/**
	 * Constructor
	 * @param actionLabel
	 * @param actionBaseUrl
	 */
	public ActionCommandLinkElement(String actionLabel, String actionBaseUrl)
	{
		super(actionLabel, null, actionBaseUrl, null);
	}

	/**
	 * Constructor
	 * @param actionLabel
	 * @param actionBaseUrl
	 * @param navKeyElementName
	 */
	public ActionCommandLinkElement(String actionLabel, String actionBaseUrl, String navKeyElementName)
	{
		super(actionLabel, null, actionBaseUrl, navKeyElementName);
	}

}
