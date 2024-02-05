package com.springcaf.core.web.model.table;

import java.util.List;

import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.WebModel;

public class WebTableModel extends WebModel {
	
	public static final String BUILTIN_LABEL_EDIT = "BUILTIN_LABEL_EDIT";
	public static final String BUILTIN_LABEL_NEW = "BUILTIN_LABEL_NEW";
	public static final String BUILTIN_LABEL_DELETE = "BUILTIN_LABEL_DELETE";
	public static final String BUILTIN_LABEL_CANCEL = "BUILTIN_LABEL_CANCEL";
	public static final String BUILTIN_LABEL_SAVE = "BUILTIN_LABEL_SAVE";

	/**
	 * Constructor
	 */
	public WebTableModel()
	{
		// default
	}
	
	/**
	 * Constructor
	 * @param modelElements
	 */
	public WebTableModel(List<WebElementModel> modelElements)
	{
		this.modelElements = modelElements;
	}
	
}
