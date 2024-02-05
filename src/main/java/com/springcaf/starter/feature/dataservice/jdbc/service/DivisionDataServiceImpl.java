package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.AbstractDataService;
import com.springcaf.starter.common.dataservice.jdbc.service.DefaultCrudDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.model.Division;

/**
 * System generated class with service functions for the generated data object. 
 */
public class DivisionDataServiceImpl extends DefaultCrudDataServiceImpl<Division> implements DivisionDataService
{
	/**
	 * Constructor
	 * @param databaseService
	 * @param schemaNameOverride
	 */
	public DivisionDataServiceImpl(AbstractDataService databaseService, String schemaNameOverride)
	{
		super(databaseService, schemaNameOverride);
	}

	/**
	 * Entity Class
	 */
	@Override
	public Class<Division> getEntityClass()
	{
		return Division.class;
	}

	/************************************************************
	 * default service implementations
	 ***********************************************************/
	@Override
	public void saveDivision(Division entity, String userId) throws SpringcafException, SQLException
	{
		this.saveEntity(entity, userId);
	}

	@Override
	public Division findDivisionByPk(Integer divisionId) throws SpringcafException, SQLException
	{
		return this.getEntityByPk(divisionId);
	}

	@Override
	public void deleteDivisionByPk(Integer divisionId) throws SpringcafException, SQLException
	{
		this.deleteEntityByPk(divisionId);
	}

	@Override
	public List<Division> findAllDivision() throws SpringcafException, SQLException
	{
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + ".division "
				+ "ORDER BY division_name ";

		return databaseService.getObjectListFromSqlQuery(Division.class, sqlQuery, null);
	}

}
