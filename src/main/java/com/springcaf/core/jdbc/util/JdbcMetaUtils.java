package com.springcaf.core.jdbc.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.util.StringUtils;

final class JdbcMetaUtils {
	
	/**
	 * Get the table model
	 * @param conn
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static TableModel getTableModel(Connection conn, String schemaName, String tableName) throws SQLException
	{
		String fullTableName = tableName;
		if(!StringUtils.isNullOrEmpty(schemaName))
		{
			fullTableName = schemaName + "." + tableName;
		}

		String samplingSql = "SELECT * FROM " + fullTableName + " WHERE 1=2";
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(samplingSql);
		
		TableModel model = getTableModel(rs);
		model.setSchemaName(schemaName);
		model.setTableName(tableName);
		
		// close resources
		rs.close();
		stmt.close();
		
		return model;
	}
	
	/**
	 * Get table model from ResultSet
	 * @param rs
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static TableModel getTableModel(ResultSet rs) throws SQLException
	{
		TableModel model = new TableModel();
		
		ResultSetMetaData meta = rs.getMetaData();
		
		// process through the result
		for(int i=1; i<=meta.getColumnCount(); i++)
		{
			String columnName = meta.getColumnName(i);
			model.addColumnModel(columnName, 
					meta.getColumnType(i),
					meta.isNullable(i) != ResultSetMetaData.columnNoNulls,
					meta.getPrecision(i),
					meta.getScale(i));
		}
		
		return model;
	}
}
