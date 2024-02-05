package com.springcaf.starter.common.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.annotation.JdbcAnnotationUtils;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.service.AbstractDataService;
import com.springcaf.core.jdbc.service.CommonCrudDataService;
import com.springcaf.core.util.StringUtils;

public abstract class DefaultCrudDataServiceImpl<T> implements CommonCrudDataService<T> {
	
	protected String schemaNameOverride = "starterdb";
	protected AbstractDataService databaseService;
	
	/**
	 * Constructor
	 * @param databaseService
	 * @param schemaNameOverride
	 */
	public DefaultCrudDataServiceImpl(AbstractDataService databaseService, String schemaNameOverride)
	{
		this.databaseService = databaseService;
		this.schemaNameOverride = schemaNameOverride;
	}

	@Override
	public void saveEntity(T entity, String userId) throws SpringcafException, SQLException {
		
		// override parms
		SqlParmSet overrideParms = new SqlParmSet();
		overrideParms.addDateSqlParm("created_at", new Date());
		overrideParms.addSqlParm("created_by", userId);
		overrideParms.addDateSqlParm("updated_at", new Date());
		overrideParms.addSqlParm("updated_by", userId);
		
		// get table model
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(entity, schemaNameOverride);

		// call the entity save method
		databaseService.saveObjectToTable(entity, tableModel, overrideParms, null);
	}

	@Override
	public T getEntityByPk(String primaryKey) throws SpringcafException, SQLException {
		
		// get table metadata
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(this.getEntityClass(), schemaNameOverride);
		this.validateTableConfigurations(tableModel);
		String tableName = tableModel.getTableName();
		String primaryKeyName = tableModel.getPrimaryKeyColumns()[0];

		// the search query
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + "." + tableName + " WHERE " + primaryKeyName + " = ? ";
		//map search key parms
		SqlParmSet searchParms = new SqlParmSet();
		searchParms.addSqlParm(primaryKeyName, primaryKey);

		return (T)databaseService.getObjectFromSqlQuery(this.getEntityClass(), sqlQuery, searchParms);
	}

	@Override
	public T getEntityByPk(Integer primaryKey) throws SpringcafException, SQLException {
		
		// get table metadata
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(this.getEntityClass(), schemaNameOverride);
		this.validateTableConfigurations(tableModel);
		String tableName = tableModel.getTableName();
		String primaryKeyName = tableModel.getPrimaryKeyColumns()[0];

		// the search query
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + "." + tableName + " WHERE " + primaryKeyName + " = ? ";
		//map search key parms
		SqlParmSet searchParms = new SqlParmSet();
		searchParms.addSqlParm(primaryKeyName, primaryKey);

		return (T)databaseService.getObjectFromSqlQuery(this.getEntityClass(), sqlQuery, searchParms);
	}

	@Override
	public T getEntityByPk(String[] primaryKeys) throws SpringcafException, SQLException {
		
		// get table metadata
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(this.getEntityClass(), schemaNameOverride);
		this.validateTableConfigurations(tableModel);
		String tableName = tableModel.getTableName();
		String[] primaryKeyNames = tableModel.getPrimaryKeyColumns();
		
		if(primaryKeyNames == null || primaryKeys == null || primaryKeyNames.length != primaryKeys.length)
		{
			throw new SpringcafException("Invalid primary key for the method");
		}

		// the search query
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + "." + tableName + " WHERE ";
		String where = "";
		for(String primaryKeyName : primaryKeyNames)
		{
			where += primaryKeyName + " = ? AND ";
		}
		sqlQuery += StringUtils.trimLastStringBlock(where, "AND");
		//map search key parms
		SqlParmSet searchParms = new SqlParmSet();
		int i = 0;
		for(String primaryKeyName : primaryKeyNames)
		{
			searchParms.addSqlParm(primaryKeyName, primaryKeys[i]);
			i++;
		}

		return (T)databaseService.getObjectFromSqlQuery(this.getEntityClass(), sqlQuery, searchParms);
	}

	@Override
	public void deleteEntityByPk(String primaryKey) throws SpringcafException, SQLException {
		
		// get table metadata
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(this.getEntityClass(), schemaNameOverride);
		this.validateTableConfigurations(tableModel);

		// call delete
		databaseService.deleteObjectFromTableByPk(tableModel, new Object[]{primaryKey});
	}

	@Override
	public void deleteEntityByPk(Integer primaryKey) throws SpringcafException, SQLException {
		
		// get table metadata
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(this.getEntityClass(), schemaNameOverride);
		this.validateTableConfigurations(tableModel);

		// call delete
		databaseService.deleteObjectFromTableByPk(tableModel, new Object[]{primaryKey});
	}

	@Override
	public void deleteEntityByPk(String[] primaryKeys) throws SpringcafException, SQLException {
		
		// get table metadata
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(this.getEntityClass(), schemaNameOverride);
		this.validateTableConfigurations(tableModel);

		// call delete
		databaseService.deleteObjectFromTableByPk(tableModel, primaryKeys);
	}

	@Override
	public List<T> getEntityList(String sqlQuery) throws SpringcafException, SQLException {

		return (List<T>)databaseService.getObjectListFromSqlQuery(this.getEntityClass(), sqlQuery, null);
	}

	@Override
	public List<T> getEntityList(String whereString,
			String sortString) throws SpringcafException, SQLException {

		// get table metadata
		TableModel tableModel = JdbcAnnotationUtils.getTableModel(this.getEntityClass(), schemaNameOverride);
		this.validateTableConfigurations(tableModel);
		String tableName = tableModel.getTableName();
		//String primaryKeyName = tableModel.getPrimaryKeyColumns()[0];
		
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + "." + tableName;
		if(!StringUtils.isNullOrEmpty(whereString))
		{
			sqlQuery += " WHERE " + whereString;
		}
		if(!StringUtils.isNullOrEmpty(sortString))
		{
			sqlQuery += " ORDER BY " + sortString;
		}

		return (List<T>)databaseService.getObjectListFromSqlQuery(this.getEntityClass(), sqlQuery, null);
	}

	/**
	 * Assume the table name has been configured in the table model
	 * and table has a (single) primary key of String
	 * @param tableModel
	 * @throws SpringcafException 
	 */
	private void validateTableConfigurations(TableModel tableModel) throws SpringcafException
	{
		if(tableModel == null)
		{
			throw new SpringcafException("TableModel not found");
		}
		if(StringUtils.isNullOrEmpty(tableModel.getTableName()))
		{
			throw new SpringcafException("Invalid tableName");
		}
		if(tableModel.getPrimaryKeyColumns() == null || tableModel.getPrimaryKeyColumns().length < 1)
		{
			throw new SpringcafException("Invalid primary key configuratin for table " + tableModel.getTableName());
		}
	}

	public String getSchemaNameOverride() {
		return schemaNameOverride;
	}

	public void setSchemaNameOverride(String schemaNameOverride) {
		this.schemaNameOverride = schemaNameOverride;
	}
}
