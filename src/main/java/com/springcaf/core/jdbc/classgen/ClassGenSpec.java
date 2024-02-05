package com.springcaf.core.jdbc.classgen;

import com.springcaf.core.jdbc.model.KeyType;

class ClassGenSpec {
	
	private String tableName = null;
	private String[] primaryKeyColumns = null;
	private String[] parentKeyColumns = null;
	private String lastUpdateTimestampColumn = null;
	private KeyType keySourceType = null;
	private String[] insertOnlyColumns = null;
	
	/**
	 * Constructor
	 * @param tableName
	 * @param primaryKeyColumns
	 * @param keySourceType
	 * @param lastUpdateTimestampColumn
	 */
	public ClassGenSpec(String tableName, String[] primaryKeyColumns, KeyType keySourceType, String lastUpdateTimestampColumn, String[] parentKeyColumns)
	{
		this.tableName = tableName;
		this.primaryKeyColumns = primaryKeyColumns;
		this.keySourceType = keySourceType;
		this.lastUpdateTimestampColumn = lastUpdateTimestampColumn;
		this.parentKeyColumns = parentKeyColumns;
	}
	
	public String[] getPrimaryKeyColumns() {
		return primaryKeyColumns;
	}
	public void setPrimaryKeyColumns(String[] primaryKeyColumns) {
		this.primaryKeyColumns = primaryKeyColumns;
	}
	public String getLastUpdateTimestampColumn() {
		return lastUpdateTimestampColumn;
	}
	public void setLastUpdateTimestampColumn(String lastUpdateTimestampColumn) {
		this.lastUpdateTimestampColumn = lastUpdateTimestampColumn;
	}
	public KeyType getKeySourceType() {
		return keySourceType;
	}
	public void setKeySourceType(KeyType keySourceType) {
		this.keySourceType = keySourceType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String[] getParentKeyColumns() {
		return parentKeyColumns;
	}

	public void setParentKeyColumns(String[] parentKeyColumns) {
		this.parentKeyColumns = parentKeyColumns;
	}

	public String[] getInsertOnlyColumns() {
		return insertOnlyColumns;
	}

	public void setInsertOnlyColumns(String[] insertOnlyColumns) {
		this.insertOnlyColumns = insertOnlyColumns;
	}

}
