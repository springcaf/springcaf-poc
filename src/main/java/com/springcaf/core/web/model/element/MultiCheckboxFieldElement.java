package com.springcaf.core.web.model.element;

import java.util.List;

import com.springcaf.core.web.model.WebElementModel;

public class MultiCheckboxFieldElement extends WebElementModel {
	
	private List<String> checkValues = null;

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public MultiCheckboxFieldElement(String elementLabel, String elementName)
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
	public MultiCheckboxFieldElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

	public List<String> getCheckValues() {
        return checkValues;
    }

    public MultiCheckboxFieldElement setCheckValues(List<String> radioValues) {
        this.checkValues = radioValues;
        return this;
    }
}
