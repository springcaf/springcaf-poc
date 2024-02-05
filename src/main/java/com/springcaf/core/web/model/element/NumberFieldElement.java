package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class NumberFieldElement extends WebElementModel {

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public NumberFieldElement(String elementLabel, String elementName)
	{
		super(elementLabel, elementName);
		
		this.displayAlighRight = true;
	}

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param required
	 * @param elementDefaultValue
	 */
	public NumberFieldElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

}
