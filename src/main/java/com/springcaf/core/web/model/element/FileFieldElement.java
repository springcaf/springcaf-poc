package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class FileFieldElement extends WebElementModel {

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public FileFieldElement(String elementLabel, String elementName)
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
	public FileFieldElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

}
