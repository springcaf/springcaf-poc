package com.springcaf.core.web.model.element;

import java.util.List;

import com.springcaf.core.web.model.WebElementModel;

public class RadioboxFieldElement extends WebElementModel {

	private List<String> radioValues = null;
	public List<String> getRadioValues() {
		return radioValues;
	}

	public RadioboxFieldElement setRadioValues(List<String> radioValues) {
		this.radioValues = radioValues;
		return this;
	}

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public RadioboxFieldElement(String elementLabel, String elementName)
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
	public RadioboxFieldElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

}
