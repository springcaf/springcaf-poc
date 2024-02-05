package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class CheckboxFieldElement extends WebElementModel {
	
	private boolean labelOnLeft = false;

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public CheckboxFieldElement(String elementLabel, String elementName)
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
	public CheckboxFieldElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

	public boolean isLabelOnLeft() {
		return labelOnLeft;
	}

	public CheckboxFieldElement setLabelOnLeft(boolean labelOnLeft) {
		this.labelOnLeft = labelOnLeft;
		
		return this;
	}

}
