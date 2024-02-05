package com.springcaf.core.jdbc.util;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.KeyType;
import com.springcaf.core.jdbc.model.SqlColumnNameSet;
import com.springcaf.core.jdbc.model.SqlParm;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.model.TableData;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.model.TableRow;
import com.springcaf.core.util.ObjectUtils;
import com.springcaf.core.util.StringFormatUtils;
import com.springcaf.core.util.StringUtils;

public final class JdbcUtils {
	
	static Logger logger = LogManager.getLogger(JdbcUtils.class);

    /**
	 * Create a new connection
	 * @param conn_str
	 * @param username
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection(String driverClass, String conn_str, String username, String password) throws ClassNotFoundException, SQLException
	{
		Class.forName(driverClass);
		
		// conn_str - //server:port/database
		Connection conn = DriverManager.getConnection(conn_str, username, password);

		return conn;
	}

	/**
	 * Close database connection
	 * @param conn
	 * @throws SQLException
	 */
	public static void closeConnection(Connection conn) throws SQLException
	{
		if(conn != null)
		{
			conn.close();
		}
	}
	
	/**
	 * Delete using a search parm set
	 * @param conn
	 * @param tableModel
	 * @param deleteParmSet
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static int withModelDeleteObject(Connection conn, TableModel tableModel, SqlParmSet deleteParmSet) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		// build the key columns used in search
		String[] keyColumns = null;
		if(deleteParmSet != null)
		{
			int index = 0;
			keyColumns = new String[deleteParmSet.getSqlParms().size()];
			for(SqlParm parm : deleteParmSet.getSqlParms())
			{
				keyColumns[index] = parm.getParmName();
				index++;
			}
		}
		
		String deleteSql = tableModel.getDeleteSql(keyColumns);
		
		// now build the binding list
		// run delete query
		logger.info("withModelDeleteObject: " + deleteSql);
		logger.info("deleteParmSet: " + deleteParmSet);
		int ret = JdbcDataUtils.runUpdateQuery(conn, deleteSql, deleteParmSet);
		
		return ret;
	}

	/**
	 * Insert into table using Object
	 * @param conn
	 * @param dataObject
	 * @param tableModel
	 * @param overrideParms
	 * @param skipColumns
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static int withModelInsertObject(Connection conn, Object dataObject, TableModel tableModel, SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		if(tableModel.getKeySourceType() == KeyType.AUTO_INCREMENT
				|| tableModel.getKeySourceType() == KeyType.NEXTVAL
				|| tableModel.getKeySourceType() == KeyType.UUID)
		{
			if(tableModel.getPrimaryKeyColumns() == null || tableModel.getPrimaryKeyColumns().length != 1)
			{
				throw new SpringcafException("Invalid primary key defined for withModelInsertObject() function. A single column is expected as primary key");
			}
		}
		
		// skip primary key if auto increment
		if(tableModel.getKeySourceType() == KeyType.AUTO_INCREMENT)
		{
			if(skipColumns == null)
			{
				skipColumns = new SqlColumnNameSet();
			}
			for(String keyColumnName : tableModel.getPrimaryKeyColumns())
			{
				skipColumns.addColumnName(keyColumnName);
			}
		}
		else if(tableModel.getKeySourceType() == KeyType.NEXTVAL)
		{
			String primaryKeyColumn = tableModel.getPrimaryKeyColumns()[0];
			Integer primaryKeyValue = withModelGetNextIntegerTableKey(conn, tableModel);
			ObjectUtils.setObjectMemberValue(dataObject, StringFormatUtils.underscoreToCamel(primaryKeyColumn, false), primaryKeyValue);
		}
		else if(tableModel.getKeySourceType() == KeyType.UUID)
		{
			String primaryKeyColumn = tableModel.getPrimaryKeyColumns()[0];
			String primaryKeyValue = StringUtils.generateUUID();
			ObjectUtils.setObjectMemberValue(dataObject, StringFormatUtils.underscoreToCamel(primaryKeyColumn, false), primaryKeyValue);
		}
		
		String insertSql = tableModel.getInsertSql(skipColumns);
		List<String>insertColumns = tableModel.getInsertColumnNames(skipColumns);
		
		// now build the binding list
		SqlParmSet insertParmSet = buildSqlParmList(tableModel, dataObject, insertColumns, overrideParmSet);

		// run insert query
		logger.info("withModelInsertObject: " + insertSql);
		logger.info("insertParmSet: " + insertParmSet);
		int ret = -1;
		if(tableModel.getKeySourceType() == KeyType.AUTO_INCREMENT)
		{
			ret = JdbcDataUtils.runUpdateQueryAndRetrieveAutoPkForInsert(conn, insertSql, insertParmSet, dataObject, tableModel.getPrimaryKeyColumns());
		}
		else
		{
			ret = JdbcDataUtils.runUpdateQuery(conn, insertSql, insertParmSet);
		}
		
		return ret;
	}

	/**
	 * Run a SQL query, and turn it into a single object defined by objClass
	 * @param conn
	 * @param objClass
	 * @param sqlQuery
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T selectObjectFromSqlQuery(Connection conn, Class<T> objClass, String sqlQuery, SqlParmSet searchParmSet) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		logger.info("selectObjectFromSqlQuery: " + sqlQuery);
		logger.info("searchParmSet: " + searchParmSet);
		TableData data = JdbcDataUtils.runSelectQuery(conn, sqlQuery, searchParmSet);
		if(data == null || data.getRows().size() == 0)
		{
			return null;
		}
		else if(data.getRows().size() > 1)
		{
			throw new SpringcafException("Too many record returned");
		}
		else
		{
			return JdbcOrmUtils.mapTableRowToObject(objClass, data.getModel(), data.getRows().get(0));
		}
	}

	/**
	 * Run a SQL query, and turn it into a single object defined by objClass
	 * @param conn
	 * @param objClass
	 * @param tableModel
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T withModelSelectObject(Connection conn, Class<T> objClass, TableModel tableModel, SqlParmSet searchParmSet) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		String selectSql = tableModel.getSelectSql(searchParmSet);

		logger.info("withModelSelectObject: " + selectSql);
		logger.info("searchParmSet: " + searchParmSet);
		return selectObjectFromSqlQuery(conn, objClass, selectSql, searchParmSet);
	}

	/**
	 * Turn a SQL query into a list of objects
	 * @param conn
	 * @param objClass
	 * @param sqlQuery
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> List<T> selectObjectListFromSqlQuery(Connection conn, Class<T> objClass, String sqlQuery, SqlParmSet searchParmSet) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		logger.info("selectObjectListFromSqlQuery: " + sqlQuery);
		logger.info("searchParmSet: " + searchParmSet);
		TableData data = JdbcDataUtils.runSelectQuery(conn, sqlQuery, searchParmSet);
		List<T>list = new ArrayList<T>();
		if(data == null)
		{
			return list;
		}

		for(TableRow row: data.getRows())
		{
			list.add(JdbcOrmUtils.mapTableRowToObject(objClass, data.getModel(), row));
		}
		
		return list;
	}

	/**
	 * Turn a SQL query into a list of objects
	 * @param conn
	 * @param objClass
	 * @param tableModel
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> List<T> withModelSelectObjectList(Connection conn, Class<T> objClass, TableModel tableModel, SqlParmSet searchParmSet) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		String selectSql = tableModel.getSelectSql(searchParmSet);
		
		logger.info("withModelSelectObjectList: " + selectSql);
		logger.info("searchParmSet: " + searchParmSet);
		return selectObjectListFromSqlQuery(conn, objClass, selectSql, searchParmSet);
	}

	/**
	 * Insert a list of objects into a table
	 * @param conn
	 * @param dataObjectList
	 * @param tableModel
	 * @param overrideParmSet
	 * @param skipColumns
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static int[] withModelInsertObjectList(Connection conn, List<? extends Object> dataObjectList, TableModel tableModel, SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		if(tableModel.getKeySourceType() == KeyType.AUTO_INCREMENT
				|| tableModel.getKeySourceType() == KeyType.NEXTVAL
				|| tableModel.getKeySourceType() == KeyType.UUID)
		{
			if(tableModel.getPrimaryKeyColumns() == null || tableModel.getPrimaryKeyColumns().length != 1)
			{
				throw new SpringcafException("Invalid primary key defined for withModelInsertObject() function. A single column is expected as primary key");
			}
		}
		
		// skip primary key if auto increment
		if(tableModel.getKeySourceType() == KeyType.AUTO_INCREMENT)
		{
			if(skipColumns == null)
			{
				skipColumns = new SqlColumnNameSet();
			}
			for(String keyColumnName : tableModel.getPrimaryKeyColumns())
			{
				skipColumns.addColumnName(keyColumnName);
			}
		}
		else if(tableModel.getKeySourceType() == KeyType.NEXTVAL)
		{
			String primaryKeyColumn = tableModel.getPrimaryKeyColumns()[0];
			Integer primaryKeyValue = withModelGetNextIntegerTableKey(conn, tableModel);
			for(Object dataObject : dataObjectList)
			{
				ObjectUtils.setObjectMemberValue(dataObject, StringFormatUtils.underscoreToCamel(primaryKeyColumn, false), primaryKeyValue);
				primaryKeyValue++;
			}
		}
		else if(tableModel.getKeySourceType() == KeyType.UUID)
		{
			String primaryKeyColumn = tableModel.getPrimaryKeyColumns()[0];
			for(Object dataObject : dataObjectList)
			{
				String primaryKeyValue = StringUtils.generateUUID();
				ObjectUtils.setObjectMemberValue(dataObject, StringFormatUtils.underscoreToCamel(primaryKeyColumn, false), primaryKeyValue);
			}
		}

		String insertSql = tableModel.getInsertSql(skipColumns);
		List<String>insertColumns = tableModel.getInsertColumnNames(skipColumns);
		
		// now build the binding list
		List<SqlParmSet> parmSetList = new ArrayList<SqlParmSet>();
		for(Object dataObject : dataObjectList)
		{
			SqlParmSet insertParmSet = buildSqlParmList(tableModel, dataObject, insertColumns, overrideParmSet);
			parmSetList.add(insertParmSet);
		}

		// run insert query
		logger.info("withModelInsertObjectList: " + insertSql);
		logger.info("total records: " + parmSetList.size());
		int ret[] = JdbcDataUtils.runBatchUpdateQuery(conn, insertSql, parmSetList);
		
		return ret;
	}

	/**
	 * Update a list of objects in a table
	 * @param conn
	 * @param dataObjectList
	 * @param tableModel
	 * @param overrideParmSet
	 * @param skipColumns
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static int[] withModelUpdateObjectList(Connection conn, List<? extends Object> dataObjectList, TableModel tableModel, SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		// handle skipColumns
		if(tableModel.getInsertOnlyColumns().size() > 0)
		{
			skipColumns = mergeSqlParmListIntoSkipColumns(skipColumns, tableModel.getInsertOnlyColumns());
		}
		
		String updateSql = tableModel.getUpdateSql(tableModel.getPrimaryKeyColumns(), skipColumns);
		List<String>insertColumns = tableModel.getUpdateColumnNames(tableModel.getPrimaryKeyColumns(), skipColumns);
		
		// now build the binding list
		List<SqlParmSet> parmSetList = new ArrayList<SqlParmSet>();
		for(Object dataObject : dataObjectList)
		{
			SqlParmSet updateParmSet = buildSqlParmList(tableModel, dataObject, insertColumns, overrideParmSet);
			parmSetList.add(updateParmSet);
		}

		// run insert query
		logger.info("withModelUpdateObjectList: " + updateSql);
		logger.info("total records: " + parmSetList.size());
		int ret[] = JdbcDataUtils.runBatchUpdateQuery(conn, updateSql, parmSetList);
		
		return ret;
	}

	/**
	 * Update table record using Object
	 * @param conn
	 * @param dataObject
	 * @param tableModel
	 * @param overrideParms
	 * @param skipColumns
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static int withModelUpdateObject(Connection conn, Object dataObject, TableModel tableModel, SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException, SQLException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		// handle skipColumns
		if(tableModel.getInsertOnlyColumns().size() > 0)
		{
			skipColumns = mergeSqlParmListIntoSkipColumns(skipColumns, tableModel.getInsertOnlyColumns());
		}
		
		String updateSql = tableModel.getUpdateSql(tableModel.getPrimaryKeyColumns(), skipColumns);
		List<String>updateColumns = tableModel.getUpdateColumnNames(tableModel.getPrimaryKeyColumns(), skipColumns);

		// now build the binding list
		SqlParmSet updateParmSet = buildSqlParmList(tableModel, dataObject, updateColumns, overrideParmSet);

		// run update query
		logger.info("withModelUpdateObject: " + updateSql);
		logger.info("updateParmSet: " + updateParmSet);
		int ret = JdbcDataUtils.runUpdateQuery(conn, updateSql, updateParmSet);
		
		return ret;
	}

	/**
	 * Get the next primary key value, in the case it is a combo key, the FIRST one needs to be the one getting an increment
	 * @param conn
	 * @param tableModel
	 * @return
	 * @throws SQLException
	 * @throws SpringcafException 
	 */
    public static int withModelGetNextIntegerTableKey(Connection conn, TableModel tableModel) throws SQLException, SpringcafException
    {
    	String[] primaryKeyColumns = tableModel.getPrimaryKeyColumns();
    	if(primaryKeyColumns != null && primaryKeyColumns.length != 1)
    	{
    		throw new SpringcafException("JdbcUtils.getNextIntegerTableKey(): primary key column is not properly defiend");
    	}

    	String sqlQuery = "SELECT NVL(MAX(" + primaryKeyColumns[0] + "),0) AS MAX_KEY_VALUE FROM " + tableModel.getQualifiedTableName();
    	
		logger.info("withModelGetNextIntegerTableKey: " + sqlQuery);
		TableData maxKeyResult = JdbcDataUtils.runSelectQuery(conn, sqlQuery, null);
		int maxKeyValue = (Integer)maxKeyResult.getValue(0, "MAX_KEY_VALUE");
		
		return maxKeyValue + 1;
    }
    
    public static int getColumnNextValue(Connection conn, String schemaName, String tableName, String columnName) throws SQLException
    {
    	String sqlQuery = "SELECT MAX(" + columnName + ") AS MAX_VALUE FROM " + schemaName + "." + tableName;
    	
		logger.info("getting maxValue: " + sqlQuery);
		TableData maxKeyResult = JdbcDataUtils.runSelectQuery(conn, sqlQuery, null);
		
		if(maxKeyResult.getTableSize() == 0)
		{
			return 1;
		}
		
		int maxKeyValue = (Integer)maxKeyResult.getValue(0, "MAX_VALUE");
		
		return maxKeyValue + 1;
    	
    }
    
    /**
     * Get the record count for a table with search conditions
     * @param conn
     * @param tableModel
     * @param searchParmSet
     * @return
     * @throws SQLException
     */
    public static int withModelGetTableCountByKeys(Connection conn, TableModel tableModel, SqlParmSet searchParmSet) throws SQLException
    {
    	String sqlQuery = "SELECT COUNT(*)  AS TABLE_COUNT_VALUE FROM " + tableModel.getQualifiedTableName();
    	if(searchParmSet != null && searchParmSet.getSqlParms().size() > 0)
    	{
    		sqlQuery += " WHERE " + searchParmSet.toWhereClauseSegment();
    	}
    	
		logger.info("withModelGetTableCountByKeys: " + sqlQuery);
		TableData countResult = JdbcDataUtils.runSelectQuery(conn, sqlQuery, searchParmSet);
		int count = (Integer)countResult.getValue(0, "TABLE_COUNT_VALUE");
		
		return count;
    }

	/**
	 * Pass-through update sql
	 * @param conn
	 * @param updateSql
	 * @param parms
	 * @return
	 * @throws SQLException
	 */
	public static int runUpdateQuery(Connection conn, String updateSql, SqlParmSet updateParmSet) throws SQLException
	{
		logger.info("runUpdateQuery: " + updateSql);
		logger.info("updateParmSet: " + updateParmSet);
		return JdbcDataUtils.runUpdateQuery(conn, updateSql, updateParmSet);
	}
	
	/**
	 * Run select query
	 * @param conn
	 * @param selectSql
	 * @param selectParmSet
	 * @return
	 * @throws SQLException
	 */
	public static TableData runSelectQuery(Connection conn, String selectSql, SqlParmSet selectParmSet) throws SQLException
	{
		logger.info("runSelectQuery: " + selectSql);
		logger.info("SqlParmSet: " + selectParmSet);
		return JdbcDataUtils.runSelectQuery(conn, selectSql, selectParmSet);
	}
	
	/**
	 * Map a data object into the list of SqlParms for the sql query
	 * @param model
	 * @param dataObject
	 * @param parmColumnNames
	 * @param overrideParms
	 * @return
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws SpringcafException
	 */
	private static SqlParmSet buildSqlParmList(TableModel model, Object dataObject, List<String> parmColumnNames, SqlParmSet overrideParmSet) throws IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SpringcafException
	{
		TableRow row = JdbcOrmUtils.mapObjectToTableRow(dataObject, model);
		SqlParmSet sqlParmSet = new SqlParmSet();
		for(String columnName : parmColumnNames)
		{
			Object value = null;
			
			// get the value from override parms first
			if(overrideParmSet != null)
			{
				value = overrideParmSet.getParmData(columnName);
			}
					
			// get from TableRow if null
			if(value == null)
			{
				int columnIndex = model.getColumnIndex(columnName);
				value = row.getValue(columnIndex);
			}
			
			sqlParmSet.addSqlParm(columnName, value, model.getColumnModelByName(columnName).getColumnSqlType());
		}

		return sqlParmSet;
	}
	
	/**
	 * Merge a SqlParm list into a skipColumns list
	 * @param skipColumns
	 * @param parmList
	 * @return
	 */
	private static SqlColumnNameSet mergeSqlParmListIntoSkipColumns(SqlColumnNameSet skipColumns, List<String> insertOnlyColumns)
	{
		if(insertOnlyColumns == null || insertOnlyColumns.size() == 0)
		{
			return skipColumns;
		}
		if(skipColumns == null)
		{
			skipColumns = new SqlColumnNameSet();
		}
	
		// copy column names into skip columns
		for(int i=0; i<insertOnlyColumns.size(); i++)
		{
			skipColumns.addColumnName(insertOnlyColumns.get(i));
		}
		
		return skipColumns;
	}
	
	/**
	 * Get table model for a database table
	 * @param conn
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static TableModel getTableModel(Connection conn, String schemaName, String tableName) throws SQLException
	{
		return JdbcMetaUtils.getTableModel(conn, schemaName, tableName);
	}
}
