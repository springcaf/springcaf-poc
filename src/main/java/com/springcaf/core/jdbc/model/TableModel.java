package com.springcaf.core.jdbc.model;

import java.util.ArrayList;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.util.ObjectUtils;
import com.springcaf.core.util.StringFormatUtils;
import com.springcaf.core.util.StringUtils;

public class TableModel {
	
	private String schemaName = null;
	private String tableName = null;
	private List<TableColumnModel> columns = new ArrayList<TableColumnModel>();
	private String[] primaryKeyColumns = null;
	private String lastUpdateTimestampColumn = null;
	private KeyType keySourceType = KeyType.NO_KEY;
	private List<String> insertOnlyColumns = new ArrayList<String>();
	
	/**
	 * Constructor
	 */
	public TableModel()
	{
		// default constructor
	}
	
	/**
	 * Constructor
	 * @param schemaName
	 * @param tableName
	 */
	public TableModel(String schemaName, String tableName)
	{
		this.schemaName = schemaName;
		this.tableName = tableName;
	}
	
	/**
	 * Get the full table name with schema
	 * @return
	 */
	public String getQualifiedTableName()
	{
		if(StringUtils.isNullOrEmpty(this.schemaName))
		{
			return this.tableName;
		}
		return this.schemaName + "." + this.tableName;
	}
	
	/**
	 * Add a new column model to the table model
	 * @param columnName
	 * @param columnSqlType
	 * @param nullable
	 * @param columnPrecision
	 * @param columnScale
	 */
	public void addColumnModel(String columnName, int columnSqlType, boolean nullable, int columnPrecision, int columnScale)
	{
		this.columns.add(new TableColumnModel(columnName, columnSqlType, nullable, columnPrecision, columnScale));
	}
	
	/**
	 * Add a new column model to the table model
	 * @param columnName
	 * @param columnType
	 * @param nullable
	 * @param columnPrecision
	 * @param columnScale
	 */
	public void addColumnModel(String columnName, JdbcJavaType columnType, boolean nullable, int columnPrecision, int columnScale)
	{
		this.columns.add(new TableColumnModel(columnName, columnType, nullable, columnPrecision, columnScale));
	}

	/**
	 * lookup column model by name
	 * @param columnName
	 * @return
	 */
	public TableColumnModel getColumnModelByName(String columnName)
	{
		for(TableColumnModel column : this.columns)
		{
			if(column.getColumnName().equalsIgnoreCase(columnName))
			{
				return column;
			}
		}
		
		return null;
	}
	
	/**
	 * Create a select sql
	 * @param searchCriteria
	 * @return
	 */
	public String getSelectSql(SqlParmSet searchCriteria)
	{
		String ret = "SELECT ";
		for(TableColumnModel column: this.columns)
		{
			ret += column.getColumnName() + ",";
		}
		ret = StringUtils.trimLastStringBlock(ret, ",");
		
		ret += " FROM " + this.getQualifiedTableName();
		
		if(searchCriteria != null)
		{
			ret += " WHERE ";

			for(SqlParm parm : searchCriteria.getSqlParms())
			{
				ret += parm.getParmName() + " = ? AND ";
			}
			ret = StringUtils.trimLastStringBlock(ret, "AND");
		}
		
		return ret;
	}
	
	/**
	 * Get insert sql
	 * @param skipColumns
	 * @return
	 */
	public String getInsertSql(SqlColumnNameSet skipColumns)
	{
		String ret = "INSERT INTO " + this.getQualifiedTableName() + " (";
		
		// iterate through the columns
		for(TableColumnModel column : this.columns)
		{
			if(!inColumnNameSet(column.getColumnName(), skipColumns))
			{
				ret += column.getColumnName() + ",";
			}
		}
		ret = StringUtils.trimLastStringBlock(ret, ",");
		ret += ") values (";
		
		// iterate through the columns to add ?
		for(TableColumnModel column : this.columns)
		{
			if(!inColumnNameSet(column.getColumnName(), skipColumns))
			{
				ret += "?,";
			}
		}
		ret = StringUtils.trimLastStringBlock(ret, ",");
		ret += ")";
		
		return ret;
	}

	/**
	 * Get list of insert column names
	 * @param skipColumns
	 * @return
	 */
	public List<String> getInsertColumnNames(SqlColumnNameSet skipColumns)
	{
		List<String>colNames = new ArrayList<String>();
		
		// iterate through the columns to add column names
		for(TableColumnModel column : this.columns)
		{
			if(!inColumnNameSet(column.getColumnName(), skipColumns))
			{
				colNames.add(column.getColumnName());
			}
		}

		return colNames;
	}
	
	/**
	 * Get update sql
	 * @param keyList
	 * @param skipColumns
	 * @return
	 */
	public String getUpdateSql(String[] keyList, SqlColumnNameSet skipColumns)
	{
		String ret = "UPDATE " + this.getQualifiedTableName() + " SET ";
		
		// iterate through the columns to add non-key columns
		for(TableColumnModel column : this.columns)
		{
			if(!inColumnNameSet(column.getColumnName(), skipColumns) && !StringUtils.inStringList(column.getColumnName(), keyList))
			{
				ret += column.getColumnName() + " = ?,";
			}
		}
		ret = StringUtils.trimLastStringBlock(ret, ",");
		ret += " WHERE ";
		
		// iterate through the columns to add key columns
		for(TableColumnModel column : this.columns)
		{
			if(StringUtils.inStringList(column.getColumnName(), keyList))
			{
				ret += column.getColumnName() + " = ? AND ";
			}
		}
		ret = StringUtils.trimLastStringBlock(ret, "AND");
		
		return ret;
	}

	/**
	 * Get list of update column names
	 * @param skipColumns
	 * @return
	 */
	public List<String> getUpdateColumnNames(String[] keyList, SqlColumnNameSet skipColumns)
	{
		List<String>colNames = new ArrayList<String>();
		
		// iterate through the columns to add non-key columns
		for(TableColumnModel column : this.columns)
		{
			if(!inColumnNameSet(column.getColumnName(), skipColumns) && !StringUtils.inStringList(column.getColumnName(), keyList))
			{
				colNames.add(column.getColumnName());
			}
		}

		// iterate through the columns to add key columns
		for(TableColumnModel column : this.columns)
		{
			if(StringUtils.inStringList(column.getColumnName(), keyList))
			{
				colNames.add(column.getColumnName());
			}
		}

		return colNames;
	}
	
	/**
	 * Get delete sql
	 * @param keyList
	 * @return
	 */
	public String getDeleteSql(String[] keyList)
	{
		String ret = "DELETE FROM " + this.getQualifiedTableName();
		ret += " WHERE ";
		
		// iterate through the columns to add key columns
		for(TableColumnModel column : this.columns)
		{
			if(StringUtils.inStringList(column.getColumnName(), keyList))
			{
				ret += column.getColumnName() + " = ? AND ";
			}
		}
		ret = StringUtils.trimLastStringBlock(ret, "AND");
		
		return ret;
	}

	/**
	 * Get list of delete column names
	 * @param keyList
	 * @return
	 */
	public List<String> getDeleteColumnNames(String[] keyList)
	{
		List<String>colNames = new ArrayList<String>();
		
		// iterate through the columns to add key columns
		for(TableColumnModel column : this.columns)
		{
			if(StringUtils.inStringList(column.getColumnName(), keyList))
			{
				colNames.add(column.getColumnName());
			}
		}

		return colNames;
	}
	
	/**
	 * Override the schema name
	 * @param overrideSchemaName
	 */
	public void overrideSchemaName(String overrideSchemaName)
	{
		if(!StringUtils.isNullOrEmpty(overrideSchemaName))
		{
			this.setSchemaName(overrideSchemaName);
		}
	}
	
	/**
	 * Check to see if a columnName is in the list
	 * @param columnName
	 * @param columnNameSet
	 * @return
	 */
	private boolean inColumnNameSet(String columnName, SqlColumnNameSet columnNameSet)
	{
		if(columnNameSet == null)
		{
			return false;
		}
		
		return columnNameSet.containColumnName(columnName);
	}
	
	/**
	 * bind the primary key fields to the object values into a SqlParmSet
	 * @param dataObject
	 * @return
	 * @throws SpringcafException
	 */
	public SqlParmSet bindPrimaryKeySet(Object dataObject) throws SpringcafException
	{
		SqlParmSet primaryKeyParmSet = new SqlParmSet();
		String[] entityKeyNames = this.getPrimaryKeyColumns();
		
		if(StringUtils.isArrayNullOrEmpty(entityKeyNames))
		{
			// nothing to select
			return primaryKeyParmSet;
		}
		
		if(entityKeyNames != null)
		{
			for(String entityKeyName : entityKeyNames)
			{
				Object keyValue = ObjectUtils.getObjectMemberValue(dataObject, StringFormatUtils.underscoreToCamel(entityKeyName, false));
				if(keyValue != null)
				{
					primaryKeyParmSet.addSqlParm(entityKeyName, keyValue);
				}
			}
		}
		
		return primaryKeyParmSet;
	}
	
	public SqlParmSet bindPrimaryKeySet(Object[] keyValues) throws SpringcafException
	{
		SqlParmSet primaryKeyParmSet = new SqlParmSet();
		String[] entityKeyNames = this.getPrimaryKeyColumns();
		
		if(StringUtils.isArrayNullOrEmpty(entityKeyNames) || keyValues == null)
		{
			// nothing to select
			return primaryKeyParmSet;
		}
		
		// validate key and values
		if(entityKeyNames.length != keyValues.length)
		{
			throw new SpringcafException("bindPrimaryKeySet: invalid keyValues parameters passed for the primary key fields");
		}
		
		if(entityKeyNames != null)
		{
			for(int i=0; i<entityKeyNames.length; i++)
			{
				Object keyValue = keyValues[i];
				if(keyValue != null)
				{
					primaryKeyParmSet.addSqlParm(entityKeyNames[i], keyValue);
				}
			}
		}
		
		return primaryKeyParmSet;
	}
	
	
	/*********************************************************************************************
	 * Getters and setters
	 ********************************************************************************************/
	public String getSchemaName() {
		if(schemaName == null)
		{
			return "";
		}
		return schemaName;
	}
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public List<TableColumnModel> getColumns() {
		return columns;
	}
	public void setColumns(List<TableColumnModel> columns) {
		this.columns = columns;
	}

	public String[] getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}

	private TableModel setPrimaryKeyColumns(String[] primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
		
		return this;
	}
	
	public TableModel setPrimaryKeyColumnsOfKeySourceType(String[] primaryKeyColumns, KeyType keySourceType) {
		this.keySourceType = keySourceType;
		this.primaryKeyColumns = primaryKeyColumns;
		
		return this;
	}
	
	public TableModel setPrimaryKeyColumnsAutoInc(String[] primaryKeyColumns)
	{
		this.keySourceType = KeyType.AUTO_INCREMENT;
		return this.setPrimaryKeyColumns(primaryKeyColumns);
	}

	public TableModel setPrimaryKeyColumnsNextval(String[] primaryKeyColumns)
	{
		this.keySourceType = KeyType.NEXTVAL;
		return this.setPrimaryKeyColumns(primaryKeyColumns);
	}
	
	public TableModel setPrimaryKeyColumnsTableMax(String[] primaryKeyColumns)
	{
		this.keySourceType = KeyType.TABLE_MAX;
		return this.setPrimaryKeyColumns(primaryKeyColumns);
	}
	
	public TableModel setPrimaryKeyColumnsUuid(String[] primaryKeyColumns)
	{
		this.keySourceType = KeyType.UUID;
		return this.setPrimaryKeyColumns(primaryKeyColumns);
	}

	public TableModel setPrimaryKeyColumnsExternal(String[] primaryKeyColumns)
	{
		this.keySourceType = KeyType.EXTERNAL_KEY;
		return this.setPrimaryKeyColumns(primaryKeyColumns);
	}

	public String getLastUpdateTimestampColumn() {
		return lastUpdateTimestampColumn;
	}

	public TableModel setLastUpdateTimestampColumn(String lastUpdateTimestampColumn) {
		this.lastUpdateTimestampColumn = lastUpdateTimestampColumn;
		
		return this;
	}

	public KeyType getKeySourceType() {
		if(this.primaryKeyColumns == null || this.primaryKeyColumns.length == 0)
		{
			return KeyType.NO_KEY;
		}
		return keySourceType;
	}

	@SuppressWarnings("unused")
	private TableModel setKeySourceType(KeyType keySourceType) {
		this.keySourceType = keySourceType;
		
		return this;
	}
	
	public boolean hasPrimaryKeys()
	{
		if(this.primaryKeyColumns == null || this.primaryKeyColumns.length == 0)
		{
			return false;
		}
		return true;
	}

	public List<String> getInsertOnlyColumns() {
		return insertOnlyColumns;
	}

	public void setInsertOnlyColumns(List<String> insertOnlyColumns) {
		this.insertOnlyColumns = insertOnlyColumns;
	}
	
	/**
	 * Add insert only column to the model definition
	 * @param columnName
	 * @return
	 */
	public TableModel addInsertOnlyColumn(String columnName)
	{
		this.insertOnlyColumns.add(columnName);
		
		return this;
	}
	
	/**
	 * Get column index from the tableModel
	 * @param columnName
	 * @return
	 */
	public int getColumnIndex(String columnName)
	{
		int index = 0;
		for(TableColumnModel column: columns)
		{
			if(column.getColumnName().equalsIgnoreCase(columnName))
			{
				return index;
			}
			index++;
		}
		
		// no exact match, do fuzzy match
		return this.getColumnIndexFuzzy(columnName);
	}
	
	/**
	 * Do some fuzzy match
	 * @param columnName
	 * @return
	 */
	private int getColumnIndexFuzzy(String columnName)
	{
		int index = 0;
		for(TableColumnModel column: columns)
		{
			if(StringFormatUtils.underscoreToCamel(column.getColumnName(), false).equalsIgnoreCase(StringFormatUtils.underscoreToCamel(columnName, false)))
			{
				return index;
			}
			index++;
		}
		
		return -1;
	}
	
	/**
	 * Get column list (comma separated)
	 * @return
	 */
	public String getColumnList()
	{
		StringBuffer buffer = new StringBuffer();
		for(TableColumnModel column : this.columns)
		{
			buffer.append(column.getColumnName() + ", ");
		}
		
		return buffer.toString();
	}
}
