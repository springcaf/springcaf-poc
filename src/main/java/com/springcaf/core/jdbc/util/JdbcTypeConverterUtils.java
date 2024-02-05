package com.springcaf.core.jdbc.util;

import com.springcaf.core.jdbc.model.JdbcJavaType;

public final class JdbcTypeConverterUtils {
	
	/**
	 * Convert sql type to element type
	 * @param sqlType
	 * @return
	 */
	public static JdbcJavaType sqlTypeToElementType(int sqlType)
	{
		JdbcJavaType ret = JdbcJavaType.STRING;
		switch(sqlType)
		{
			case java.sql.Types.BIGINT:
			case java.sql.Types.INTEGER:
			case java.sql.Types.SMALLINT:
			case java.sql.Types.TINYINT:
				ret = JdbcJavaType.INTEGER;
				break;
			case java.sql.Types.BIT:
			case java.sql.Types.BOOLEAN:
				ret = JdbcJavaType.BOOLEAN;
				break;
			case java.sql.Types.CHAR:
			case java.sql.Types.CLOB:
			case java.sql.Types.LONGNVARCHAR:
			case java.sql.Types.LONGVARCHAR:
			case java.sql.Types.NCHAR:
			case java.sql.Types.NCLOB:
			case java.sql.Types.NVARCHAR:
			case java.sql.Types.VARCHAR:
				ret = JdbcJavaType.STRING;
				break;
			case java.sql.Types.DECIMAL:
			case java.sql.Types.DOUBLE:
			case java.sql.Types.FLOAT:
			case java.sql.Types.NUMERIC:
			case java.sql.Types.REAL:
				ret = JdbcJavaType.DOUBLE;
				break;
			case java.sql.Types.DATE:
			case java.sql.Types.TIME:
			case java.sql.Types.TIMESTAMP:
				ret = JdbcJavaType.DATE;
				break;
			case java.sql.Types.ARRAY:
			case java.sql.Types.BINARY:
			case java.sql.Types.BLOB:
			case java.sql.Types.DATALINK:
			case java.sql.Types.DISTINCT:
			case java.sql.Types.JAVA_OBJECT:
			case java.sql.Types.LONGVARBINARY:
			case java.sql.Types.NULL:
			case java.sql.Types.OTHER:
			case java.sql.Types.REF:
			case java.sql.Types.ROWID:
			case java.sql.Types.SQLXML:
			case java.sql.Types.STRUCT:
			case java.sql.Types.VARBINARY:
				ret = JdbcJavaType.STRING;
				break;
		}
		
		return ret;
	}
	
	/**
	 * Convert sqlType to type string value for class gen purpose
	 * @param sqlType
	 * @return
	 */
	public static String sqlTypeToTypeString(int sqlType)
	{
		String ret = String.valueOf(sqlType);
		switch(sqlType)
		{
			case java.sql.Types.BIGINT:
				ret = "java.sql.Types.BIGINT";
				break;
			case java.sql.Types.INTEGER:
				ret = "java.sql.Types.INTEGER";
				break;
			case java.sql.Types.SMALLINT:
				ret = "java.sql.Types.SMALLINT";
				break;
			case java.sql.Types.TINYINT:
				ret = "java.sql.Types.TINYINT";
				break;
			case java.sql.Types.BIT:
				ret = "java.sql.Types.BIT";
				break;
			case java.sql.Types.BOOLEAN:
				ret = "java.sql.Types.BOOLEAN";
				break;
			case java.sql.Types.CHAR:
				ret = "java.sql.Types.CHAR";
				break;
			case java.sql.Types.CLOB:
				ret = "java.sql.Types.CLOB";
				break;
			case java.sql.Types.LONGNVARCHAR:
				ret = "java.sql.Types.LONGNVARCHAR";
				break;
			case java.sql.Types.LONGVARCHAR:
				ret = "java.sql.Types.LONGVARCHAR";
				break;
			case java.sql.Types.NCHAR:
				ret = "java.sql.Types.NCHAR";
				break;
			case java.sql.Types.NCLOB:
				ret = "java.sql.Types.NCLOB";
				break;
			case java.sql.Types.NVARCHAR:
				ret = "java.sql.Types.NVARCHAR";
				break;
			case java.sql.Types.VARCHAR:
				ret = "java.sql.Types.VARCHAR";
				break;
			case java.sql.Types.DECIMAL:
				ret = "java.sql.Types.DECIMAL";
				break;
			case java.sql.Types.DOUBLE:
				ret = "java.sql.Types.DOUBLE";
				break;
			case java.sql.Types.FLOAT:
				ret = "java.sql.Types.FLOAT";
				break;
			case java.sql.Types.NUMERIC:
				ret = "java.sql.Types.NUMERIC";
				break;
			case java.sql.Types.REAL:
				ret = "java.sql.Types.REAL";
				break;
			case java.sql.Types.DATE:
				ret = "java.sql.Types.DATE";
				break;
			case java.sql.Types.TIME:
				ret = "java.sql.Types.TIME";
				break;
			case java.sql.Types.TIMESTAMP:
				ret = "java.sql.Types.TIMESTAMP";
				break;
			case java.sql.Types.ARRAY:
				ret = "java.sql.Types.ARRAY";
				break;
			case java.sql.Types.BINARY:
				ret = "java.sql.Types.BINARY";
				break;
			case java.sql.Types.BLOB:
				ret = "java.sql.Types.BLOB";
				break;
			case java.sql.Types.DATALINK:
				ret = "java.sql.Types.DATALINK";
				break;
			case java.sql.Types.DISTINCT:
				ret = "java.sql.Types.DISTINCT";
				break;
			case java.sql.Types.JAVA_OBJECT:
				ret = "java.sql.Types.JAVA_OBJECT";
				break;
			case java.sql.Types.LONGVARBINARY:
				ret = "java.sql.Types.LONGVARBINARY";
				break;
			case java.sql.Types.NULL:
				ret = "java.sql.Types.NULL";
				break;
			case java.sql.Types.OTHER:
				ret = "java.sql.Types.OTHER";
				break;
			case java.sql.Types.REF:
				ret = "java.sql.Types.REF";
				break;
			case java.sql.Types.ROWID:
				ret = "java.sql.Types.ROWID";
				break;
			case java.sql.Types.SQLXML:
				ret = "java.sql.Types.SQLXML";
				break;
			case java.sql.Types.STRUCT:
				ret = "java.sql.Types.STRUCT";
				break;
			case java.sql.Types.VARBINARY:
				ret = "java.sql.Types.VARBINARY";
				break;
		}
		
		return ret;
	}
	
}
