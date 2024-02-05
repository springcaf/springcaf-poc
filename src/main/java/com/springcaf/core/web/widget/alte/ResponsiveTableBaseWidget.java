package com.springcaf.core.web.widget.alte;

import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.StringUtils;
import com.springcaf.core.web.meta.WebAppMeta;
import com.springcaf.core.web.widget.AbstractWidget;

public abstract class ResponsiveTableBaseWidget extends AbstractWidget {
	
	private List<? extends Object> dataObjectList = null;
	protected WebAppMeta webAppMeta = null;
	private boolean addHeaderRow = true;
	private boolean addFilterRow = false;
	private String tableStyle = null;
	protected String headerRowStyle = null;
	private String dataRowStyle = null;
	private String tableId = null;
	protected int pageSize = 0;
	protected int pageNumber = 0;
	
	public ResponsiveTableBaseWidget(WebAppMeta webAppMeta, 
			List<? extends Object> dataObjectList,
			String tableStyle,
			String headerRowStyle,
			String dataRowStyle) {
		super(webAppMeta.getRequest().getContextPath());
		
		this.webAppMeta = webAppMeta;
		this.dataObjectList = dataObjectList;
		this.tableStyle = tableStyle;
		this.headerRowStyle = headerRowStyle;
		this.dataRowStyle = dataRowStyle;
	}

	@Override
	public void renderToHtml(StringBuffer buffer) throws SpringcafException {
		
		if(dataObjectList != null)
		{
			int showRowCounter = 0;
			String tableClass = "";
			if(!StringUtils.isNullOrEmpty(tableStyle))
			{
				tableClass = " " + tableStyle;
			}
			
			String tableIdSegment = "";
			if(!StringUtils.isNullOrEmpty(tableId))
			{
				tableIdSegment = " id=\"" + tableId + "\"";
			}
			
			buffer.append("<div class=\"table-responsive\">" + LINE_FEED);
			buffer.append("<table " + tableIdSegment + " class=\"table " + tableClass + "\">" + LINE_FEED);

			buffer.append("<thead>");
			// add table header
			if(this.addHeaderRow)
			{
				addTableHeaderRowWithTr(buffer);
			}
			// add table filter row
			if(this.addFilterRow)
			{
				addTableFilterRowWithTr(buffer);
			}

			buffer.append("</thead>");

			buffer.append("<tbody>");
			
			// loop through to add table row
			for(Object dataObject : this.dataObjectList)
			{
				if(this.showRow(dataObject))
				{
					if(this.pageNumber == 0 || showRowCounter >= this.pageSize*(this.pageNumber-1))
					{
						addTableDataRowWithTr(buffer, dataObject);
					}

					showRowCounter++;
					if(this.pageSize > 0 && showRowCounter >= this.pageSize*this.pageNumber)
					{
						break;
					}
				}
			}
			buffer.append("</tbody>");
			
			buffer.append("</table>" + LINE_FEED);
			buffer.append("</div>" + LINE_FEED);
		}
		else
		{
			buffer.append("<div>No data found</div>");
		}

	}
	
	protected boolean showRow(Object dataObject)
	{
		return true;
	}
	
	protected void addTableHeaderRowWithTr(StringBuffer buffer) throws SpringcafException
	{
		this.startTr(buffer, headerRowStyle);
		addTableHeaderRow(buffer);
		this.closeTr(buffer);
	}
	
	private void addTableFilterRowWithTr(StringBuffer buffer) throws SpringcafException
	{
		this.startTr(buffer, headerRowStyle);
		addTableFilterRow(buffer);
		this.closeTr(buffer);
	}

	private void addTableDataRowWithTr(StringBuffer buffer, Object dataObject) throws SpringcafException
	{
		this.startTr(buffer, dataRowStyle);
		addTableRow(buffer, dataObject);
		this.closeTr(buffer);
	}

	abstract protected void addTableRow(StringBuffer buffer, Object dataObject) throws SpringcafException;
	
	abstract protected void addTableHeaderRow(StringBuffer buffer) throws SpringcafException;
	
	protected void addTableFilterRow(StringBuffer buffer) throws SpringcafException
	{
		
	}
	
	/**
	 * Add table cell
	 * @param buffer
	 * @param cellValue
	 */
	protected void addTableHeaderCell(StringBuffer buffer, String cellValue)
	{
		this.addTableHeaderCell(buffer, null, cellValue, "");
	}

	/**
	 * Add table cell
	 * @param buffer
	 * @param cellId
	 * @param cellValue
	 */
	protected void addTableHeaderCell(StringBuffer buffer, String cellId, String cellValue)
	{
		this.addTableHeaderCell(buffer, cellId, cellValue, "");
	}

	/**
	 * Add a table cell code block
	 * @param buffer
	 * @param cellId
	 * @param cellValue
	 * @param cellStyle
	 */
	protected void addTableHeaderCell(StringBuffer buffer, String cellId, String cellValue, String cellStyle)
	{
		String cellClass = "";
		if(!StringUtils.isNullOrEmpty(cellStyle))
		{
			cellClass = " class=\"" + cellStyle + "\"";
		}
		
		// append an ID attribute
		String idAppend = "";
		if(!StringUtils.isNullOrEmpty(cellId))
		{
			idAppend = " id=\"" + cellId + "\"";
		}

		buffer.append("<th nowrap " + idAppend + cellClass + ">" + cellValue + "</th>" + LINE_FEED);
	}

	/**
	 * Create table cell and wrap text (default)
	 * @param buffer
	 * @param cellValue
	 */
	protected void addTableDataCell(StringBuffer buffer, String cellValue)
	{
		this.addTableDataCell(buffer, cellValue, "", true);
	}

	/**
	 * Table cell with nowrap
	 * @param buffer
	 * @param cellValue
	 */
	protected void addTableDataCellNoWrap(StringBuffer buffer, String cellValue)
	{
		this.addTableDataCell(buffer, cellValue, "", false);
	}

	/**
	 * Create table cell and wrap text (default)
	 * @param buffer
	 * @param cellValue
	 * @param cellStyle
	 */
	protected void addTableDataCell(StringBuffer buffer, String cellValue, String cellStyle)
	{
		this.addTableDataCell(buffer, cellValue, cellStyle, true);
	}

	/**
	 * Table cell with nowrap
	 * @param buffer
	 * @param cellValue
	 * @param cellStyle
	 */
	protected void addTableDataCellNoWrap(StringBuffer buffer, String cellValue, String cellStyle)
	{
		this.addTableDataCell(buffer, cellValue, cellStyle, false);
	}

	/**
	 * Create a table cell code block
	 * @param buffer
	 * @param cellValue
	 * @param cellStyle
	 * @param wrapText
	 */
	private void addTableDataCell(StringBuffer buffer, String cellValue, String cellStyle, boolean wrapText)
	{
		if(cellValue == null)
		{
			cellValue = "";
		}
		String cellClass = "";
		if(!wrapText)
		{
			cellClass += " nowrap";
		}
		if(!StringUtils.isNullOrEmpty(cellStyle))
		{
			cellClass += " class=\"" + cellStyle + "\"";
		}
		buffer.append("<td" + cellClass + ">" + cellValue + "</td>" + LINE_FEED);
	}

	public boolean isAddHeaderRow() {
		return addHeaderRow;
	}

	public void setAddHeaderRow(boolean addHeaderRow) {
		this.addHeaderRow = addHeaderRow;
	}

	public int getRecordCount()
	{
		int count = 0;
		// loop through to count
		for(Object dataObject : this.dataObjectList)
		{
			if(this.showRow(dataObject))
			{
				count++;
			}
		}
		
		return count;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
 
	public HttpServletRequest getRequest() {
		return this.webAppMeta.getRequest();
	}

	public TimeZone getUserTimeZone()
	{
		return this.webAppMeta.getUserTimeZone();
	}
	
	public String getEncodedUserId()
	{
		return this.webAppMeta.getEncodedUserId();
	}

	public boolean isAddFilterRow() {
		return addFilterRow;
	}

	public void setAddFilterRow(boolean addFilterRow) {
		this.addFilterRow = addFilterRow;
	}
	
	public void setPaging(int pageSize, int pageNumber)
	{
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
	}
}
