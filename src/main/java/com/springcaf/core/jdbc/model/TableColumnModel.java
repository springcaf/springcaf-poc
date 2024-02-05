package com.springcaf.core.jdbc.model;

import com.springcaf.core.jdbc.util.JdbcTypeConverterUtils;

public class TableColumnModel {
	
	private String columnName = null;
	private JdbcJavaType columnType = null;
	private int precision = 256;
	private int scale = 0;
	private boolean nullable = true;
	private String dataFormat = null;
	private int columnSqlType = java.sql.Types.VARCHAR;
	
	/**
	 * Constructor
	 * @param columnName
	 * @param columnType
	 * @param nullable
	 * @param columnPrecision
	 * @param columnScale
	 */
	public TableColumnModel(String columnName, JdbcJavaType columnType, boolean nullable, int columnPrecision, int columnScale)
	{
		this.columnName = columnName;
		this.columnType = columnType;
		this.precision = columnPrecision;
		this.scale = columnScale;
		this.setNullable(nullable);
		
		int columnSqlType = SqlParm.PARM_TYPE_VARCHAR;
		if(columnType == JdbcJavaType.BOOLEAN)
		{
			columnSqlType = SqlParm.PARM_TYPE_BOOLEAN;
		}
		else if(columnType == JdbcJavaType.DATE)
		{
			columnSqlType = SqlParm.PARM_TYPE_DATE;
		}
		else if(columnType == JdbcJavaType.INTEGER)
		{
			columnSqlType = SqlParm.PARM_TYPE_INTEGER;
		}
		else if(columnType == JdbcJavaType.DOUBLE)
		{
			columnSqlType = SqlParm.PARM_TYPE_DOUBLE;
		}
		this.columnSqlType = columnSqlType;
	}
	
	/**
	 * Constructor
	 * @param columnName
	 * @param columnSqlType
	 * @param nullable
	 * @param columnPrecision
	 * @param columnScale
	 */
	public TableColumnModel(String columnName, int columnSqlType, boolean nullable, int columnPrecision, int columnScale)
	{
		this.columnName = columnName;
		this.columnType = JdbcTypeConverterUtils.sqlTypeToElementType(columnSqlType);
		this.precision = columnPrecision;
		this.scale = columnScale;
		this.setNullable(nullable);
		this.columnSqlType = columnSqlType;
	}
	
	/**
	 * List of java.sql.Types
	 * Compiled from Types.java (version 1.5 : 49.0, super bit)
	 * public static final int BIT = -7;
	 * public static final int TINYINT = -6;
	 * public static final int SMALLINT = 5;
	 * public static final int INTEGER = 4;
	 * public static final int BIGINT = -5;
	 * public static final int FLOAT = 6;
	 * public static final int REAL = 7;
	 * public static final int DOUBLE = 8;
	 * public static final int NUMERIC = 2;
	 * public static final int DECIMAL = 3;
	 * public static final int CHAR = 1;
	 * public static final int VARCHAR = 12;
	 * public static final int LONGVARCHAR = -1;
	 * public static final int DATE = 91;
	 * public static final int TIME = 92;
	 * public static final int TIMESTAMP = 93;
	 * public static final int BINARY = -2;
	 * public static final int VARBINARY = -3;
	 * public static final int LONGVARBINARY = -4;
	 * public static final int NULL = 0;
	 * public static final int OTHER = 1111;
	 * public static final int JAVA_OBJECT = 2000;
	 * public static final int DISTINCT = 2001;
	 * public static final int STRUCT = 2002;
	 * public static final int ARRAY = 2003;
	 * public static final int BLOB = 2004;
	 * public static final int CLOB = 2005;
	 * public static final int REF = 2006;
	 * public static final int DATALINK = 70;
	 * public static final int BOOLEAN = 16;
	 * public static final int ROWID = -8;
	 * public static final int NCHAR = -15;
	 * public static final int NVARCHAR = -9;
	 * public static final int LONGNVARCHAR = -16;
	 * public static final int NCLOB = 2011;
	 * public static final int SQLXML = 2009;
	 */
	
	/**
	 * Test if a column is of Date/Time type
	 * @return
	 */
	public boolean isDateTimeType()
	{
		if(this.columnSqlType == java.sql.Types.DATE ||
			this.columnSqlType == java.sql.Types.TIME	||
			this.columnSqlType == java.sql.Types.TIMESTAMP)
		{
			return true;
		}
		
		return false;
	}

	/*********************************************************************************************
	 * Getters and setters
	 ********************************************************************************************/
	public int getColumnSqlType() {
		return columnSqlType;
	}
	public void setColumnSqlType(int columnType) {
		this.columnSqlType = columnType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public JdbcJavaType getColumnType() {
		return columnType;
	}
	public void setColumnType(JdbcJavaType columnType) {
		this.columnType = columnType;
	}
	public boolean isNullable() {
		return nullable;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public String getDataFormat() {
		return dataFormat;
	}
	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public int getColumnSize() {
		return this.precision;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
}
