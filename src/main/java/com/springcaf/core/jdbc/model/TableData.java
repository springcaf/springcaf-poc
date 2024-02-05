package com.springcaf.core.jdbc.model;

import java.util.ArrayList;
import java.util.List;

public class TableData {
	
	private TableModel model = null;
	private List<TableRow> rows = new ArrayList<TableRow>();
	
	/**
	 * Add a new table row
	 * @param row
	 */
	public void addRow(TableRow row)
	{
		rows.add(row);
	}
	
	/**
	 * Get the size of the data (number of rows)
	 * @return
	 */
	public int getTableSize()
	{
		return this.rows.size();
	}
	
	/**
	 * Get the cell value of the table
	 * @param row
	 * @param columnName
	 * @return
	 */
	public Object getValue(int row, String columnName)
	{
		if(row < 0 || row >= this.getRows().size())
		{
			return null;
		}
		TableRow dataRow = this.getRows().get(row);
		
		if(dataRow != null)
		{
			int columnIndex = -1;
			if(model != null)
			{
				columnIndex = model.getColumnIndex(columnName);
			}
			return dataRow.getValue(columnIndex);
		}
		
		return null;
	}
	
	/*********************************************************************************************
	 * Getters and setters
	 ********************************************************************************************/
	public TableModel getModel() {
		return model;
	}
	public void setModel(TableModel model) {
		this.model = model;
	}
	public List<TableRow> getRows() {
		return rows;
	}
	public void setRows(List<TableRow> rows) {
		this.rows = rows;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		for(TableColumnModel column : this.getModel().getColumns())
		{
			buffer.append(column.getColumnName() + ",");
		}
		buffer.append("\r\n");
		
		for(TableRow row: this.getRows())
		{
			for(TableColumnModel column : this.model.getColumns())
			{
				buffer.append(row.getValue(this.model.getColumnIndex(column.getColumnName())) + ",");
			}
			buffer.append("\r\n");
		}

		return buffer.toString();
	}

}
