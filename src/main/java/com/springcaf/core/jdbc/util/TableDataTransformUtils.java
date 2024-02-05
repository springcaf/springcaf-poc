package com.springcaf.core.jdbc.util;

import java.sql.Types;

import com.springcaf.core.jdbc.model.TableColumnModel;
import com.springcaf.core.jdbc.model.TableData;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.model.TableRow;

public final class TableDataTransformUtils {
	
	/**
	 * Transpose a table data object
	 * @param from
	 * @return
	 */
	public static TableData transpose(TableData fromData, String anchorColumn, String newColumnName)
	{
		TableData toData = new TableData();
		
		TableModel fromModel = fromData.getModel();
		
		// model
		TableModel toModel = new TableModel();
		toModel.addColumnModel(newColumnName, Types.VARCHAR, false, 0, 0);
		for(int i=0; i<fromData.getTableSize(); i++)
		{
			toModel.addColumnModel(fromData.getValue(i, anchorColumn).toString(), Types.VARCHAR, false, 0, 0);
		}
		toData.setModel(toModel);
		
		// data
		for(TableColumnModel fromColumn: fromModel.getColumns())
		{
			if(!anchorColumn.equalsIgnoreCase(fromColumn.getColumnName()))
			{
				TableRow toRow = new TableRow();
				toRow.addValue(fromColumn.getColumnName());
				for(int i=0; i<fromData.getTableSize(); i++)
				{
					toRow.addValue(fromData.getValue(i, fromColumn.getColumnName()));
				}
				toData.addRow(toRow);
			}
		}
		
		return toData;
	}

}
