package com.springcaf.core.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.SqlColumnNameSet;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.model.TableData;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.util.JdbcCommonTableCrudUtils;
import com.springcaf.core.jdbc.util.JdbcUtils;

public abstract class AbstractDataService {
	
	static Logger logger = LogManager.getLogger(AbstractDataService.class);

	/**
	 * Get a reference to JdbcConnection
	 * @return
	 * @throws SQLException 
	 */
	public abstract Connection getReaderConnection() throws SQLException;
	public abstract Connection getWriterConnection() throws SQLException;
	
	/**
	 * Get the table model
	 * @param conn
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public TableModel getTableModel(Connection conn, String schemaName, String tableName) throws SQLException
	{
		TableModel tableModel = null;
		
		try
		{
			tableModel = JdbcUtils.getTableModel(conn, schemaName, tableName);
		}
		catch(SQLException sqlex)
		{
			throw sqlex;
		}
		
		return tableModel;
	}
	
	/**
	 * Get the table model
	 * @param schemaName
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public TableModel getTableModel(String schemaName, String tableName) throws SQLException
	{
		Connection conn = this.getReaderConnection();
		TableModel tableModel = null;
		
		try
		{
			tableModel = JdbcUtils.getTableModel(conn, schemaName, tableName);
		}
		catch(SQLException sqlex)
		{
			throw sqlex;
		}
		finally
		{
			conn.close();
		}
		
		return tableModel;
	}
	
	/**
	 * Turn a sql query into a single object
	 * @param conn
	 * @param objClass
	 * @param sqlQuery
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public <T> T getObjectFromSqlQuery(Connection conn, Class<T> objClass, String sqlQuery, SqlParmSet sqlParmSet) throws SpringcafException, SQLException
	{
		T obj = null;
		try
		{
			logger.info("getObjectFromSqlQuery: " + sqlQuery);
			logger.info("sqlParmSet: " + sqlParmSet);
			obj = JdbcUtils.selectObjectFromSqlQuery(conn, objClass, sqlQuery, sqlParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return obj;
	}
	
	/**
	 * Turn a sql query into a single object
	 * @param objClass
	 * @param sqlQuery
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public <T> T getObjectFromSqlQuery(Class<T> objClass, String sqlQuery, SqlParmSet sqlParmSet) throws SpringcafException, SQLException
	{
		Connection conn = this.getReaderConnection();
		T obj = null;
		try
		{
			logger.info("getObjectFromSqlQuery: " + sqlQuery);
			logger.info("sqlParmSet: " + sqlParmSet);
			obj = JdbcUtils.selectObjectFromSqlQuery(conn, objClass, sqlQuery, sqlParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
		
		return obj;
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
	 */
	public <T> List<T> getObjectListFromSqlQuery(Connection conn, Class<T> objClass, String sqlQuery, SqlParmSet sqlParmSet) throws SpringcafException, SQLException
	{
		List<T> list = null;

		try
		{
			logger.info("getObjectListFromSqlQuery: " + sqlQuery);
			logger.info("sqlParmSet: " + sqlParmSet);
			list = JdbcUtils.selectObjectListFromSqlQuery(conn, objClass, sqlQuery, sqlParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return list;
	}
	
	/**
	 * Turn a SQL query into a list of objects
	 * @param objClass
	 * @param sqlQuery
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public <T> List<T> getObjectListFromSqlQuery(Class<T> objClass, String sqlQuery, SqlParmSet sqlParmSet) throws SpringcafException, SQLException
	{
		Connection conn = this.getReaderConnection();
		List<T> list = null;

		try
		{
			logger.info("getObjectListFromSqlQuery: " + sqlQuery);
			logger.info("sqlParmSet: " + sqlParmSet);
			list = JdbcUtils.selectObjectListFromSqlQuery(conn, objClass, sqlQuery, sqlParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
		
		return list;
	}
	
	/**
	 * Get object from a table
	 * @param conn
	 * @param objClass
	 * @param entityModel
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public <T> T getObjectFromTable(Connection conn, Class<T> objClass, TableModel entityModel, SqlParmSet sqlParms) throws SpringcafException, SQLException
	{
		T obj = null;
		
		try
		{
			obj = JdbcUtils.withModelSelectObject(conn, objClass, entityModel, sqlParms);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return obj;
	}
	
	/**
	 * Get object from a table
	 * @param objClass
	 * @param entityModel
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public <T> T getObjectFromTable(Class<T> objClass, TableModel entityModel, SqlParmSet sqlParms) throws SpringcafException, SQLException
	{
		Connection conn = this.getReaderConnection();
		T obj = null;
		
		try
		{
			obj = JdbcUtils.withModelSelectObject(conn, objClass, entityModel, sqlParms);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
		
		return obj;
	}
	
	/**
	 * Turn a SQL query into a list of objects
	 * @param conn
	 * @param objClass
	 * @param schemaName
	 * @param tableName
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public List<? extends Object> getObjectListFromTable(Connection conn, Class<?> objClass, TableModel entityModel, SqlParmSet searchParms) throws SpringcafException, SQLException
	{
		List<? extends Object> list = null;

		try
		{
			list = JdbcUtils.withModelSelectObjectList(conn, objClass, entityModel, searchParms);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return list;
	}
	
	/**
	 * Turn a SQL query into a list of objects
	 * @param objClass
	 * @param schemaName
	 * @param tableName
	 * @param sqlParms
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public List<? extends Object> getObjectListFromTable(Class<?> objClass, TableModel entityModel, SqlParmSet searchParms) throws SpringcafException, SQLException
	{
		Connection conn = this.getReaderConnection();
		List<? extends Object> list = null;

		try
		{
			list = JdbcUtils.withModelSelectObjectList(conn, objClass, entityModel, searchParms);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
		
		return list;
	}
	
	/**
	 * Save the object, with a DataChangeHandler
	 * @param conn
	 * @param dataObject
	 * @param entityModel
	 * @param entityKeyValues
	 * @param overrideParms
	 * @param dataChangeHandler
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void saveObjectToTable(Connection conn, Object dataObject, TableModel entityModel, SqlParmSet overrideParms, DataChangeHandler dataChangeHandler) throws SpringcafException, SQLException
	{
		try
		{
			JdbcCommonTableCrudUtils.saveEntity(conn, dataObject, entityModel, overrideParms, null, dataChangeHandler);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}

	/**
	 * Save the object, with a DataChangeHandler
	 * @param dataObject
	 * @param entityModel
	 * @param entityKeyValues
	 * @param overrideParms
	 * @param dataChangeHandler
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void saveObjectToTable(Object dataObject, TableModel entityModel, SqlParmSet overrideParms, DataChangeHandler dataChangeHandler) throws SpringcafException, SQLException
	{
		Connection conn = this.getWriterConnection();
		try
		{
			JdbcCommonTableCrudUtils.saveEntity(conn, dataObject, entityModel, overrideParms, null, dataChangeHandler);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
	}

	/**
	 * Save the object, with a DataChangeHandler
	 * @param conn
	 * @param dataObjectList
	 * @param entityModel
	 * @param overrideParms
	 * @param dataChangeHandler
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void saveObjectListToTable(Connection conn, List<? extends Object> dataObjectList, TableModel entityModel, SqlParmSet overrideParms, DataChangeHandler dataChangeHandler) throws SpringcafException, SQLException
	{
		try
		{
			JdbcCommonTableCrudUtils.saveEntityList(conn, dataObjectList, entityModel, overrideParms, null, dataChangeHandler);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}
	
	/**
	 * Save the object, with a DataChangeHandler
	 * @param dataObjectList
	 * @param entityModel
	 * @param overrideParms
	 * @param dataChangeHandler
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void saveObjectListToTable(List<? extends Object> dataObjectList, TableModel entityModel, SqlParmSet overrideParms, DataChangeHandler dataChangeHandler) throws SpringcafException, SQLException
	{
		Connection conn = this.getWriterConnection();
		try
		{
			JdbcCommonTableCrudUtils.saveEntityList(conn, dataObjectList, entityModel, overrideParms, null, dataChangeHandler);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
	}
	
	/**
	 * Insert a set of objects into a table
	 * @param conn
	 * @param dataObjectList
	 * @param schemaNameOverride
	 * @param overrideParms
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void insertObjectSetToTable(Connection conn, List<? extends Object> dataObjectList, TableModel entityModel, SqlParmSet overrideParms, SqlColumnNameSet skipColumns) throws SpringcafException, SQLException
	{
		try
		{
			JdbcCommonTableCrudUtils.createEntityList(conn, dataObjectList, entityModel, overrideParms, skipColumns);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}
	
	/**
	 * Insert a set of objects into a table
	 * @param dataObjectList
	 * @param schemaNameOverride
	 * @param overrideParms
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void insertObjectSetToTable(List<? extends Object> dataObjectList, TableModel entityModel, SqlParmSet overrideParms, SqlColumnNameSet skipColumns) throws SpringcafException, SQLException
	{
		Connection conn = this.getWriterConnection();
		try
		{
			JdbcCommonTableCrudUtils.createEntityList(conn, dataObjectList, entityModel, overrideParms, skipColumns);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
	}
	
	/**
	 * Delete an object from the database
	 * @param conn
	 * @param refObject
	 * @param schemaNameOverride
	 * @param entityKeyValues
	 * @throws SpringcafException 
	 * @throws SQLException 
	 */
	public void deleteObjectFromTableByPk(Connection conn, TableModel entityModel, Object[] entityKeyValues) throws SpringcafException, SQLException
	{
		try
		{
			SqlParmSet deleteParmSet = entityModel.bindPrimaryKeySet(entityKeyValues);
			JdbcCommonTableCrudUtils.deleteEntity(conn, entityModel, deleteParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}

	/**
	 * Delete an object from the database
	 * @param refObject
	 * @param schemaNameOverride
	 * @param entityKeyValues
	 * @throws SpringcafException 
	 * @throws SQLException 
	 */
	public void deleteObjectFromTableByPk(TableModel entityModel, Object[] entityKeyValues) throws SpringcafException, SQLException
	{
		Connection conn = this.getWriterConnection();
		
		try
		{
			SqlParmSet deleteParmSet = entityModel.bindPrimaryKeySet(entityKeyValues);
			JdbcCommonTableCrudUtils.deleteEntity(conn, entityModel, deleteParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
	}

	/**
	 * Delete table data by search keys
	 * @param conn
	 * @param refObject
	 * @param schemaNameOverride
	 * @param deleteParmSet
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void deleteObjectFromTableBySearchKey(Connection conn, TableModel entityModel, SqlParmSet deleteParmSet) throws SpringcafException, SQLException
	{
		try
		{
			JdbcCommonTableCrudUtils.deleteEntity(conn, entityModel, deleteParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}

	/**
	 * Delete table data by search keys
	 * @param refObject
	 * @param schemaNameOverride
	 * @param deleteParmSet
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void deleteObjectFromTableBySearchKey(TableModel entityModel, SqlParmSet deleteParmSet) throws SpringcafException, SQLException
	{
		Connection conn = this.getWriterConnection();

		try
		{
			JdbcCommonTableCrudUtils.deleteEntity(conn, entityModel, deleteParmSet);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
	}

	/**
	 * Delete data from a table using query
	 * @param conn
	 * @param sqlQuery
	 * @param deleteParmSet
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void deleteObjectFromSqlQuery(Connection conn, String deleteSql, SqlParmSet deleteParmSet) throws SpringcafException, SQLException
	{
		try
		{
			JdbcUtils.runUpdateQuery(conn, deleteSql, deleteParmSet);
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}
	
	/**
	 * Delete data from a table using query
	 * @param sqlQuery
	 * @param deleteParmSet
	 * @throws SpringcafException
	 * @throws SQLException 
	 */
	public void deleteObjectFromSqlQuery(String deleteSql, SqlParmSet deleteParmSet) throws SpringcafException, SQLException
	{
		Connection conn = this.getWriterConnection();

		try
		{
			JdbcUtils.runUpdateQuery(conn, deleteSql, deleteParmSet);
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
	}
	
	/**
	 * Get the next primary key value from a table
	 * @param conn
	 * @param tableModel
	 * @return
	 * @throws SQLException
	 * @throws SpringcafException
	 */
    public int getNextIntegerTableKey(Connection conn, TableModel tableModel) throws SpringcafException, SQLException
    {
    	int ret = 0;

    	try
		{
			ret = JdbcUtils.withModelGetNextIntegerTableKey(conn, tableModel);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return ret;
    }
    
	/**
	 * Get the next primary key value from a table
	 * @param tableModel
	 * @return
	 * @throws SQLException
	 * @throws SpringcafException
	 */
    public int getNextIntegerTableKey(TableModel tableModel) throws SpringcafException, SQLException
    {
		Connection conn = this.getWriterConnection();
    	int ret = 0;

    	try
		{
			ret = JdbcUtils.withModelGetNextIntegerTableKey(conn, tableModel);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
    	finally
    	{
			conn.close();
    	}
		
		return ret;
    }
    
    /**
     * Get the record count for a table with search conditions
	 * @param conn
     * @param tableModel
     * @param searchParmSet
     * @return
     * @throws SQLException
     */
    public int getTableCountByKeys(Connection conn, TableModel tableModel, SqlParmSet searchParmSet) throws SpringcafException, SQLException
    {
    	int ret = 0;
		try
		{

			ret = JdbcUtils.withModelGetTableCountByKeys(conn, tableModel, searchParmSet);
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return ret;
    }
    
    /**
     * Get the record count for a table with search conditions
     * @param tableModel
     * @param searchParmSet
     * @return
     * @throws SQLException
     */
    public int getTableCountByKeys(TableModel tableModel, SqlParmSet searchParmSet) throws SpringcafException, SQLException
    {
		Connection conn = this.getReaderConnection();
    	int ret = 0;
		try
		{

			ret = JdbcUtils.withModelGetTableCountByKeys(conn, tableModel, searchParmSet);
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
		
		return ret;
    }
    
    /**
     * Run update query with parms
	 * @param conn
     * @param updateQuery
     * @param updateParmSet
     * @throws SpringcafException
     * @throws SQLException 
     */
	public void runUpdateQuery(Connection conn, String updateQuery, SqlParmSet updateParmSet) throws SpringcafException, SQLException
	{
		try
		{
			JdbcUtils.runUpdateQuery(conn, updateQuery, updateParmSet);
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
	}
	
    /**
     * Run update query with parms
     * @param updateQuery
     * @param updateParmSet
     * @throws SpringcafException
     * @throws SQLException 
     */
	public void runUpdateQuery(String updateQuery, SqlParmSet updateParmSet) throws SpringcafException, SQLException
	{
		Connection conn = this.getWriterConnection();
		
		try
		{
			JdbcUtils.runUpdateQuery(conn, updateQuery, updateParmSet);
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
	}
	
	/**
	 * Run select sql and return TableData
	 * @param conn
	 * @param selectQuery
	 * @param selectParmSet
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	public TableData runSelectQuery(Connection conn, String selectQuery, SqlParmSet selectParmSet) throws SpringcafException, SQLException
	{
		TableData tableData = null;
		try
		{
			tableData = JdbcUtils.runSelectQuery(conn, selectQuery, selectParmSet);

			conn.close();
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return tableData;
	}

	/**
	 * Run select sql and return TableData
	 * @param selectQuery
	 * @param selectParmSet
	 * @return
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	public TableData runSelectQuery(String selectQuery, SqlParmSet selectParmSet) throws SpringcafException, SQLException
	{
		Connection conn = this.getReaderConnection();
		TableData tableData = null;
		try
		{
			tableData = JdbcUtils.runSelectQuery(conn, selectQuery, selectParmSet);

			conn.close();
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		finally
		{
			conn.close();
		}
		
		return tableData;
	}

}
