package com.springcaf.starter.common.ui.widget;

import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.util.WidgetUtils;
import com.springcaf.core.web.widget.alte.BoxDisplayAlteEditWidget;

public abstract class DefaultEditBoxWidget<T> extends BoxDisplayAlteEditWidget<T> {
	
	public static final String EDIT_PARM = "edit";
	public static final String EDIT_ACTION_DELETE = "delete";
	public static final String EDIT_ACTION_MOVE_FROM = "movefrom";
	public static final String EDIT_ACTION_MOVE_TO = "to";

	public DefaultEditBoxWidget(WebAppMeta webAppMeta,
							boolean showFooter, 
							String formName,
							String postUrl,
							T editObject) 
	
	{
		super(webAppMeta, "box-solid", true, true, showFooter, formName, postUrl, editObject);
	}

	protected String createIconCommandAdd(String title, String baseUrl, String inputParm, String colorClass)
	{
		return WidgetUtils.createIconButtonUrl("fa fa-plus-circle", title, baseUrl + "?" + EDIT_PARM + "=" + inputParm, colorClass);
	}
	
	protected String createIconCommandEdit(String title, String baseUrl, String inputParm, String colorClass)
	{
		return WidgetUtils.createIconButtonUrl("fa fa-pencil", title, baseUrl + "?" + EDIT_PARM + "=" + inputParm, colorClass);
	}
	
	protected String createIconCommandDelete(String title, String baseUrl, String inputParm, String colorClass)
	{
		return WidgetUtils.createIconButtonUrl("fa fa-close", title, baseUrl + "?" + EDIT_ACTION_DELETE + "=" + inputParm, colorClass, "return confirmforaction('delete')");
	}
	
	protected String createIconCommandDeleteWithJsCommand(String title, String baseUrl, String inputParm, String colorClass, String jsCommand)
	{
		return WidgetUtils.createIconButtonUrl("fa fa-close", title, baseUrl + "?" + EDIT_ACTION_DELETE + "=" + inputParm, colorClass, jsCommand);
	}
	
	protected String createIconCommandMoveUp(String baseUrl, String fromParm, String toParm, String colorClass)
	{
		return WidgetUtils.createIconButtonUrl("fa fa-arrow-up", "move up", baseUrl + "?" + EDIT_ACTION_MOVE_FROM + "=" + fromParm + "&" + EDIT_ACTION_MOVE_TO + "=" + toParm, colorClass);
	}
	
	protected String createIconCommandMoveDown(String baseUrl, String fromParm, String toParm, String colorClass)
	{
		return WidgetUtils.createIconButtonUrl("fa fa-arrow-down", "move down", baseUrl + "?" + EDIT_ACTION_MOVE_FROM + "=" + fromParm + "&" + EDIT_ACTION_MOVE_TO + "=" + toParm, colorClass);
	}
	
	protected KeyValueListModel getYesNoList()
	{
		KeyValueListModel yestNolistModel = new KeyValueListModel();
		yestNolistModel.addKeyValueItem("Y", "Yes");
		yestNolistModel.addKeyValueItem("N", "No");
		
		return yestNolistModel;
	}
	
	protected KeyValueListModel getNoYesList()
	{
		KeyValueListModel yestNolistModel = new KeyValueListModel();
		yestNolistModel.addKeyValueItem("N", "No");
		yestNolistModel.addKeyValueItem("Y", "Yes");
		
		return yestNolistModel;
	}

}
