package com.springcaf.core.jdbc.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.KeyType;
import com.springcaf.core.jdbc.model.SqlColumnNameSet;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.service.DataChangeHandler;
import com.springcaf.core.util.ObjectUtils;
import com.springcaf.core.util.StringUtils;

public final class JdbcCommonTableCrudUtils {
	
	/**
	 * Create an entity using external PK. The keys should be set on the object before it is passed in.
	 * @param conn
	 * @param dataObject
	 * @param entityModel
	 * @param entityKeyValues
	 * @param overrideParmSet
	 * @return
	 * @throws SpringcafException
	 */
	public static Object createEntity(Connection conn, Object dataObject, TableModel entityModel,
			SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException 
	{
		try
		{
			JdbcUtils.withModelInsertObject(conn, dataObject, entityModel, overrideParmSet, skipColumns);
		}
		catch(SpringcafException dle)
		{
			throw dle;
		}
		catch(Exception ex)
		{
			throw new SpringcafException(ex);
		}
		
		return dataObject;
	}

	/**
	 * Insert a set of objects into a table
	 * @param conn
	 * @param dataObjectList
	 * @param entityModel
	 * @param overrideParmSet
	 * @throws SpringcafException
	 */
	public static void createEntityList(Connection conn, List<? extends Object> dataObjectList, TableModel entityModel,
			SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException 
	{
		try
		{
			if(dataObjectList == null || dataObjectList.size() == 0)
			{
				// nothing to do
				return;
			}

			JdbcUtils.withModelInsertObjectList(conn, dataObjectList, entityModel, overrideParmSet, skipColumns);
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
	 * Update an entity
	 * @param conn
	 * @param dataObject
	 * @param entityModel
	 * @param entityKeyValues
	 * @param overrideParmSet
	 * @throws SpringcafException
	 */
	public static void updateEntity(Connection conn, Object dataObject, TableModel entityModel,
			SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException 
	{
		try
		{
			JdbcUtils.withModelUpdateObject(conn, dataObject, entityModel, overrideParmSet, skipColumns);
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
	 * Update an entity list
	 * @param conn
	 * @param dataObjectList
	 * @param entityModel
	 * @param entityKeyValues
	 * @param overrideParmSet
	 * @throws SpringcafException
	 */
	public static void updateEntityList(Connection conn, List<? extends Object> dataObjectList, TableModel entityModel,
			SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns) throws SpringcafException 
	{
		try
		{
			JdbcUtils.withModelUpdateObjectList(conn, dataObjectList, entityModel, overrideParmSet, skipColumns);
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
	 * Delete an entity
	 * @param conn
	 * @param dataObject - primary keys need to be set on the object
	 * @param schemaNameOverride
	 * @param entityKeyValues
	 * @throws SpringcafException
	 */
	public static void deleteEntity(Connection conn, Object dataObject, TableModel entityModel) throws SpringcafException 
	{
		try
		{
			// map primary keys into searchParmSet
			SqlParmSet deleteParmSet = entityModel.bindPrimaryKeySet(dataObject);
			
			if(deleteParmSet.isEmpty())
			{
				// nothing to delete
				return;
			}
			
			JdbcUtils.withModelDeleteObject(conn, entityModel, deleteParmSet);
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
	 * Delete dataset by search parms
	 * @param conn
	 * @param entityModel
	 * @param searchParmSet
	 * @throws SpringcafException
	 */
	public static void deleteEntity(Connection conn, TableModel entityModel, 
			SqlParmSet deleteParmSet) throws SpringcafException 
	{
		try
		{
			JdbcUtils.withModelDeleteObject(conn, entityModel, deleteParmSet);
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
	 * Select a single entity
	 * @param conn
	 * @param dataObject - dataObject primary keys need to be set before it is used 
	 * @param schemaNameOverride
	 * @param entityKeyValues
	 * @return
	 * @throws SpringcafException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T retrieveEntity(Connection conn, T dataObject, TableModel entityModel) throws SpringcafException 
	{
		try
		{
			SqlParmSet primaryKeyParmSet = entityModel.bindPrimaryKeySet(dataObject);
			
			if(primaryKeyParmSet.isEmpty())
			{
				return null;
			}

			return JdbcUtils.withModelSelectObject(conn, (Class<T>)dataObject.getClass(), entityModel, primaryKeyParmSet);
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
	 * Select an entity list
	 * @param conn
	 * @param dataObject
	 * @param schemaNameOverride
	 * @param searchParmSet
	 * @return
	 * @throws SpringcafException
	 */
	public static <T> List<T> retrieveEntityList(Connection conn,
			Class<T> objClass, TableModel entityModel, SqlParmSet searchParmSet) throws SpringcafException 
	{
		try
		{
			return JdbcUtils.withModelSelectObjectList(conn, objClass, entityModel, searchParmSet);
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
	 * Save entity with a data change handler
	 * @param conn
	 * @param dataObject
	 * @param tableModel
	 * @param entityKeyValues
	 * @param overrideParmSet
	 * @param dataChangeHandler
	 * @throws SpringcafException
	 */
	public static void saveEntity(Connection conn, Object dataObject, TableModel tableModel,
			SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns, DataChangeHandler dataChangeHandler) throws SpringcafException 
	{
		// check if the object exists
		Object currentEntity = retrieveEntity(conn, dataObject, tableModel);
		
		// check conflicting updates
		checkConflictingUpdates(tableModel, currentEntity, dataObject);
		
		// Give external caller a chance to handle the changes
		if(dataChangeHandler != null)
		{
			dataChangeHandler.beforeDatabaseChange(tableModel, currentEntity, dataObject);
		}
		
		// run insert or update
		if(currentEntity != null)
		{
			updateEntity(conn, dataObject, tableModel, overrideParmSet, skipColumns);
		}
		else
		{
			createEntity(conn, dataObject, tableModel, overrideParmSet, skipColumns);
		}
		
		// Give external caller a chance to handle the changes
		if(dataChangeHandler != null)
		{
			dataChangeHandler.afterDatabaseChange(tableModel, currentEntity, dataObject);
		}
	}

	/**
	 * Save entity list
	 * @param conn
	 * @param dataObjectList
	 * @param tableModel
	 * @param overrideParmSet
	 * @param skipColumns
	 * @param dataChangeHandler
	 * @throws SpringcafException
	 */
	public static void saveEntityList(Connection conn, List<? extends Object> dataObjectList, TableModel tableModel,
			SqlParmSet overrideParmSet, SqlColumnNameSet skipColumns, DataChangeHandler dataChangeHandler) throws SpringcafException 
	{
		if(tableModel.getKeySourceType() == KeyType.NO_KEY)
		{
			// always insert
			createEntityList(conn, dataObjectList, tableModel, overrideParmSet, skipColumns);
		}
		else if(tableModel.getKeySourceType() == KeyType.AUTO_INCREMENT
				|| tableModel.getKeySourceType() == KeyType.NEXTVAL
				|| tableModel.getKeySourceType() == KeyType.UUID)
		{
			// process the list into 2 separate buckets, one for insert, and one for update
			List<Object> insertList = new ArrayList<Object>();
			List<Object> updateList = new ArrayList<Object>();
			
			for(Object dataObject : dataObjectList)
			{
				if(tableModel.bindPrimaryKeySet(dataObject).isEmpty())
				{
					insertList.add(dataObject);
				}
				else
				{
					updateList.add(dataObject);
				}
			}
			// call create and update separately
			createEntityList(conn, insertList, tableModel, overrideParmSet, skipColumns);
			updateEntityList(conn, updateList, tableModel, overrideParmSet, skipColumns);
		}
		else
		{
			// external business case or anything else, loop through the records individually
			for(Object dataObject : dataObjectList)
			{
				saveEntity(conn, dataObject, tableModel, overrideParmSet, skipColumns, dataChangeHandler);
			}
		}
		
	}
			
	/**
	 * Check to make sure the entity hasn't been updated in the database by someone else since the last retrieved timestamp.
	 * @param entityModel
	 * @param oldObject
	 * @param newObject
	 * @throws SpringcafException
	 */
	private static void checkConflictingUpdates(TableModel entityModel, Object oldObject, Object newObject) throws SpringcafException
	{
		// if the model defined an last update column, then do the conflict checking
		if(StringUtils.isNullOrEmpty(entityModel.getLastUpdateTimestampColumn()))
		{
			return;
		}
		if(oldObject == null || newObject == null)
		{
			return;
		}
		
		Object oldObjectUpdatedTime = ObjectUtils.getObjectMemberValue(oldObject, entityModel.getLastUpdateTimestampColumn());
		Object newObjectUpdatedTime = ObjectUtils.getObjectMemberValue(newObject, entityModel.getLastUpdateTimestampColumn());
		
		if(oldObjectUpdatedTime != null && newObjectUpdatedTime != null)
		{
			if(((Date)oldObjectUpdatedTime).after((Date)newObjectUpdatedTime))
			{
				throw new SpringcafException("Conflicting updates: The object has been changed by other processes since last select. Please reload your data and make updates again.");
			}
		}
	}
	
}
