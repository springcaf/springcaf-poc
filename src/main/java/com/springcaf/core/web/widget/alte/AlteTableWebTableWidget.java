package com.springcaf.core.web.widget.alte;

import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.model.WebActionElementModel;
import com.springcaf.core.web.model.WebElementModel;
import com.springcaf.core.web.model.element.ActionCommandDeleteElement;
import com.springcaf.core.web.model.element.ActionCommandLinkElement;
import com.springcaf.core.web.model.element.ActionCommandSaveElement;
import com.springcaf.core.web.model.element.ActionNavLinkElement;
import com.springcaf.core.web.model.nav.NavUrlItemModel;
import com.springcaf.core.web.model.table.WebTableModel;
import com.springcaf.core.web.util.NavUtils;
import com.springcaf.core.web.widget.TableWebTableWidget;

public class AlteTableWebTableWidget extends TableWebTableWidget {
		
	private List<? extends Object> dataList = null;
	private WebTableModel model = null;

	/**
	 * Constructor
	 * @param applicationBaseUrl
	 */
	public AlteTableWebTableWidget(String applicationBaseUrl)
	{
		super(applicationBaseUrl);
	}
	
	/**
	 * Constructor
	 * @param applicationBaseUrl
	 * @param model
	 * @param dataList
	 */
	public AlteTableWebTableWidget(String applicationBaseUrl, WebTableModel model, List<? extends Object> dataList)
	{
		super(applicationBaseUrl);
		this.model = model;
		this.dataList = dataList;
	}
	
	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException
	{
		// start of table
		buffer.append("<table class=\"table table-striped\">" + LINE_FEED);
		
		// table heading
		buffer.append("<tr>" + LINE_FEED);
		for(WebElementModel element : this.getModel().getModelElements())
		{
			buffer.append("<th>" + element.getElementLabel() + "</th>" + LINE_FEED);
			
		}
		// action column
		if((!this.editMode && this.model.getReadModeActions().size() > 0) || (this.editMode && this.model.getEditModeActions().size() > 0))
		{
			buffer.append("<th></th>" + LINE_FEED);
		}
		buffer.append("</tr>" + LINE_FEED);
		
		// data data
		for(Object row : this.dataList)
		{
			buffer.append("<tr>" + LINE_FEED);
			for(WebElementModel element : this.getModel().getModelElements())
			{
				buffer.append("<td>");
				this.renderTableCell(buffer, element, row);
				buffer.append("</td>" + LINE_FEED);
			}
			
			// action cell
			if(!this.editMode && this.model.getReadModeActions().size() > 0)
			{
				buffer.append("<td>");
				for(WebActionElementModel action : this.model.getReadModeActions())
				{
					this.renderWebActionElement(buffer, action, row);
					buffer.append(FILLER1);
				}
				buffer.append("</td>" + LINE_FEED);
			}

			if(this.editMode && this.model.getEditModeActions().size() > 0)
			{
				buffer.append("<td>");
				for(WebActionElementModel action : this.model.getEditModeActions())
				{
					this.renderWebActionElement(buffer, action, row);
					buffer.append(FILLER1);
				}
				buffer.append("</td>" + LINE_FEED);
			}

			buffer.append("</tr>" + LINE_FEED);
		}
		
		// end of table
		buffer.append("</table>" + LINE_FEED);
	}

	private void renderTableCell(StringBuffer buffer, WebElementModel element, Object dataRow) throws SpringcafException
	{
		if(element instanceof ActionNavLinkElement)
		{
			ActionNavLinkElement nav = (ActionNavLinkElement)element;
			this.renderWebActionElement(buffer, nav, dataRow);
		}
		else
		{
			// default
			buffer.append(this.model.getObjectMemberValueAsString(dataRow, element.getElementName(), ""));
		}
	}
	
	private void renderWebActionElement(StringBuffer buffer, WebActionElementModel action, Object dataRow) throws SpringcafException
	{
		String actionKeyValue = "";
		if(!StringUtils.isNullOrEmpty(action.getActionKeyElementName()))
		{
			actionKeyValue = this.model.getObjectMemberValueAsString(dataRow, action.getActionKeyElementName(), "");
		}
		
		String actionLabel = action.getElementLabel();
		if(!StringUtils.isNullOrEmpty(action.getElementName()))
		{
			actionLabel = this.model.getObjectMemberValueAsString(dataRow, action.getElementName(), "");
		}
		
		// translate action label
		actionLabel = this.translateButtonLabel(actionLabel);
		
		if(action instanceof ActionCommandDeleteElement)
		{
			// delete needs to be before ActionCommandLinkElement
			buffer.append(NavUtils.rendereNavUrlModelToHtml(new NavUrlItemModel(actionLabel, action.getActionUrl() + actionKeyValue, actionKeyValue), applicationBaseUrl, null, "return confirmforaction('delete')"));
		}
		else if(action instanceof ActionNavLinkElement || action instanceof ActionCommandLinkElement)
		{
			buffer.append(NavUtils.rendereNavUrlModelToHtml(new NavUrlItemModel(actionLabel, action.getActionUrl() + actionKeyValue, actionKeyValue), applicationBaseUrl, null));
		}
		else if(action instanceof ActionCommandSaveElement)
		{
			buffer.append("<button type=\"submit\" title=\"Save\">" + actionLabel + "</button>");
		}
		
	}
	
	public List<? extends Object> getDataList() {
		return dataList;
	}

	public AlteTableWebTableWidget setDataList(List<? extends Object> dataList) {
		this.dataList = dataList;
		
		return this;
	}

	public WebTableModel getModel() {
		return model;
	}

	public AlteTableWebTableWidget setModel(WebTableModel model) {
		this.model = model;
		
		return this;
	}
	
	private String translateButtonLabel(String originalLabel)
	{
		if(WebTableModel.BUILTIN_LABEL_EDIT.equals(originalLabel))
		{
			return "<span class=\"glyphicon glyphicon-edit\"></span>";
		}
		else if(WebTableModel.BUILTIN_LABEL_NEW.equals(originalLabel))
		{
			return "<span class=\"glyphicon glyphicon-plus\"></span>";
		}
		else if(WebTableModel.BUILTIN_LABEL_DELETE.equals(originalLabel))
		{
			return "<span class=\"glyphicon glyphicon-trash\"></span>";
		}
		else if(WebTableModel.BUILTIN_LABEL_CANCEL.equals(originalLabel))
		{
			return "<span class=\"glyphicon glyphicon-remove\"></span>";
		}
		else if(WebTableModel.BUILTIN_LABEL_SAVE.equals(originalLabel))
		{
			return "<span class=\"glyphicon glyphicon-ok\"></span>";
		}
		return originalLabel;
	}
}
