package com.springcaf.core.web.model.element;

import com.springcaf.core.web.model.WebElementModel;

public class DropDownListElement extends WebElementModel {
	
	private String dropDownListRef = null;
	private boolean addBlankRow = false;
	
	/**
	 * Constructor
	 * @param elementLabel
	 * @param elementName
	 */
	public DropDownListElement(String elementLabel, String elementName)
	{
		super(elementLabel, elementName);
	}

	public String getDropDownListRef() {
		return dropDownListRef;
	}

	public DropDownListElement setDropDownListRef(String dropDownListRef) {
		this.dropDownListRef = dropDownListRef;
		
		return this;
	}

	public boolean isAddBlankRow() {
		return addBlankRow;
	}

	public DropDownListElement setAddBlankRow(boolean addBlankRow) {
		this.addBlankRow = addBlankRow;
		
		return this;
	}

}
