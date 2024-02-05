package com.springcaf.core.web.model.element;

public class ActionCommandDeleteElement extends ActionCommandLinkElement {
	
	/**
	 * Constructor
	 * @param actionLabel
	 * @param actionBaseUrl
	 */
	public ActionCommandDeleteElement(String actionLabel, String actionBaseUrl)
	{
		super(actionLabel, actionBaseUrl);
	}

	/**
	 * Constructor
	 * @param actionLabel
	 * @param actionBaseUrl
	 * @param navKeyElementName
	 */
	public ActionCommandDeleteElement(String actionLabel, String actionBaseUrl, String navKeyElementName)
	{
		super(actionLabel, actionBaseUrl, navKeyElementName);
	}
}
