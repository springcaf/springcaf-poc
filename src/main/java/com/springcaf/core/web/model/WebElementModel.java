package com.springcaf.core.web.model;


public class WebElementModel extends AbstractWebModel {
	private String elementLabel = null;
	private String elementName = null;
	private boolean required = false;
	private String elementDefaultValue = null;
	private boolean readonly = false;
	private int maxLength = -1;
	private String dependsOn = null;
	private String displayFormat = null;
	protected boolean displayAlighRight = false;
	protected String helperText = null;
	protected String placeHolder = null;
	protected String mask = null;
	
	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public WebElementModel(String elementLabel, String elementName)
	{
		this.elementLabel = elementLabel;
		this.elementName = elementName;
	}
	
	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param required
	 * @param elementDefaultValue
	 */
	public WebElementModel(String elementLabel, String elementName, boolean required, String elementDefaultValue)
	{
		this.elementLabel = elementLabel;
		this.elementName = elementName;
		this.required = required;
		this.elementDefaultValue = elementDefaultValue;
	}

	public String getElementName() {
		return elementName;
	}
	public WebElementModel setElementName(String elementName) {
		this.elementName = elementName;
		return this;
	}
	public String getElementDefaultValue() {
		if(elementDefaultValue == null)
		{
			return "";
		}
		
		return elementDefaultValue;
	}
	public WebElementModel setElementDefaultValue(String elementDefaultValue) {
		this.elementDefaultValue = elementDefaultValue;
		return this;
	}

	public String getElementLabel() {
		return elementLabel;
	}

	public WebElementModel setElementLabel(String elementLabel) {
		this.elementLabel = elementLabel;
		return this;
	}

	public boolean isRequired() {
		return required;
	}

	public WebElementModel setRequired(boolean required) {
		this.required = required;
		return this;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public WebElementModel setReadonly(boolean readonly) {
		this.readonly = readonly;
		return this;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public WebElementModel setMaxLength(int maxLength) {
		this.maxLength = maxLength;
		
		return this;
	}

	public String getDependsOn() {
		return dependsOn;
	}

	public WebElementModel setDependsOn(String dependsOn) {
		this.dependsOn = dependsOn;
		
		return this;
	}

	public String getDisplayFormat() {
		return displayFormat;
	}

	public WebElementModel setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
		
		return this;
	}

	public boolean isDisplayAlighRight() {
		return displayAlighRight;
	}

	public void setDisplayAlighRight(boolean displayAlighRight) {
		this.displayAlighRight = displayAlighRight;
	}

	public String getHelperText() {
		return helperText;
	}

	public WebElementModel setHelperText(String helperText) {
		this.helperText = helperText;
		
		return this;
	}

	public String getMask() {
		return mask;
	}

	public WebElementModel setMask(String mask) {
		this.mask = mask;
		
		return this;
	}

	public String getPlaceHolder() {
		return placeHolder;
	}

	public WebElementModel setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
		
		return this;
	}
	
	
}
