package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class HiddenFieldElement extends WebElementModel {

	/**
	 * Constructor
	 * @param elementName
	 */
	public HiddenFieldElement(String elementName)
	{
		super(null, elementName);
	}

	/**
	 * Constructor
	 * @param elementName
	 * @param elementDefaultValue
	 */
	public HiddenFieldElement(String elementName, String elementDefaultValue)
	{
		super(null, elementName, false, elementDefaultValue);
	}

}
