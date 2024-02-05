package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebActionElementModel;

public class ActionCommandSaveElement extends WebActionElementModel {
	
	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param navBaseUrl
	 */
	public ActionCommandSaveElement(String saveCommandLabel, String actionUrl)
	{
		super(saveCommandLabel, null, actionUrl, null);
	}

}
