package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class TextFieldElement extends WebElementModel {

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public TextFieldElement(String elementLabel, String elementName)
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
	public TextFieldElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

}
