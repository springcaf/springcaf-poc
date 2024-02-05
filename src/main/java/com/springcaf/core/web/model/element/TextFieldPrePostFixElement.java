package com.springcaf.core.web.model.element;

public class TextFieldPrePostFixElement extends TextFieldElement {
	
	private String prefix = null;
	private String postfix = null;

	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public TextFieldPrePostFixElement(String elementLabel, String elementName)
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
	public TextFieldPrePostFixElement(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		super(elementLabel, elementName, required, elementDefaultValue);
	}

	public String getPrefix() {
		if(prefix == null)
		{
			return "";
		}
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getPostfix() {
		if(postfix == null)
		{
			return "";
		}
		return postfix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

}
