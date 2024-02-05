package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class TextAreaElement extends WebElementModel {
	
	private int elementHeight = 10;

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public TextAreaElement(String elementLabel, String elementName)
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
	public TextAreaElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

	public int getElementHeight() {
		return elementHeight;
	}

	public TextAreaElement setElementHeight(int elementHeight) {
		this.elementHeight = elementHeight;
		
		return this;
	}

}
