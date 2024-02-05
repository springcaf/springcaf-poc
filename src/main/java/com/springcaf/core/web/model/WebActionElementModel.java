package com.springcaf.core.web.model;

import com.springcaf.core.util.StringUtils;

public abstract class WebActionElementModel extends WebElementModel {

	private String actionUrl = null;
	private String actionKeyElementName = null;
	private static final String DISPLAY_IN_MODE_NONE = "DISPLAY_IN_MODE_NONE";
	private static final String DISPLAY_IN_MODE_READ = "DISPLAY_IN_MODE_READ";
	private static final String DISPLAY_IN_MODE_EDIT = "DISPLAY_IN_MODE_EDIT";
	private static final String DISPLAY_IN_MODE_BOTH = "DISPLAY_IN_MODE_BOTH";
	
	private String displayInMode = DISPLAY_IN_MODE_READ;
	private String displayClass = null;
	private String confirmActionLabel = null;
	
	
	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 * @param actionUrl
	 * @param actionKeyElementName
	 */
	public WebActionElementModel(String elementLabel, String elementName, String actionUrl, String actionKeyElementName)
	{
		super(elementLabel, elementName);
		this.actionUrl = actionUrl;
		this.actionKeyElementName = actionKeyElementName;
	}
	
	public String getActionUrl() {
		if(this.actionUrl == null)
		{
			return "";
		}
		return actionUrl;
	}

	public WebActionElementModel setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
		
		return this;
	}

	public String getActionKeyElementName() {
		return actionKeyElementName;
	}

	public WebActionElementModel setActionKeyElementName(String actionKeyElementName) {
		this.actionKeyElementName = actionKeyElementName;
		
		return this;
	}
	
	public String getDisplayInMode() {
		return displayInMode;
	}

	public WebActionElementModel setDisplayInMode(String displayInMode) {
		this.displayInMode = displayInMode;
		
		return this;
	}
	
	public WebActionElementModel setDisplayInNoneMode()
	{
		this.displayInMode = DISPLAY_IN_MODE_NONE;
		
		return this;
	}

	public WebActionElementModel setDisplayInReadMode()
	{
		this.displayInMode = DISPLAY_IN_MODE_READ;
		
		return this;
	}

	public WebActionElementModel setDisplayInEditMode()
	{
		this.displayInMode = DISPLAY_IN_MODE_EDIT;
		
		return this;
	}

	public WebActionElementModel setDisplayInBothMode()
	{
		this.displayInMode = DISPLAY_IN_MODE_BOTH;
		
		return this;
	}

	public boolean isReadModeAction()
	{
		return (DISPLAY_IN_MODE_READ.equals(this.displayInMode) || DISPLAY_IN_MODE_BOTH.equals(this.displayInMode));
	}
	
	public boolean isEditModeAction()
	{
		return (DISPLAY_IN_MODE_EDIT.equals(this.displayInMode) || DISPLAY_IN_MODE_BOTH.equals(this.displayInMode));
	}

	public String getDisplayClass() {
		return displayClass;
	}

	public WebActionElementModel setDisplayClass(String displayClass) {
		this.displayClass = displayClass;
		
		return this;
	}

	public boolean isConfirmAction() {
		return !StringUtils.isNullOrEmpty(this.confirmActionLabel);
	}
	
	public String getConfirmActionLabel()
	{
		return this.confirmActionLabel;
	}

	public WebActionElementModel setConfirmAction(String confirmActionLabel) {
		this.confirmActionLabel = confirmActionLabel;
		
		return this;
	}
}
