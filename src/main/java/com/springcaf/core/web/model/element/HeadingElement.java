package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class HeadingElement extends WebElementModel {

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public HeadingElement(String elementLabel, String elementName)
	{
		super(elementLabel, elementName);
	}

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param required
	 * @param elementDefaultValue
	 */
	public HeadingElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

}
