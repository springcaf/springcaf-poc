package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;
import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.service.AbstractDataService;
import com.springcaf.starter.common.dataservice.jdbc.service.DefaultCrudDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.model.Person;

/**
 * System generated class with service functions for the generated data object. 
 */
public class PersonDataServiceImpl extends DefaultCrudDataServiceImpl<Person> implements PersonDataService
{
	/**
	 * Constructor
	 * @param databaseService
	 * @param schemaNameOverride
	 */
	public PersonDataServiceImpl(AbstractDataService databaseService, String schemaNameOverride)
	{
		super(databaseService, schemaNameOverride);
	}

	/**
	 * Entity Class
	 */
	@Override
	public Class<Person> getEntityClass()
	{
		return Person.class;
	}

	/************************************************************
	 * default service implementations
	 ***********************************************************/
	@Override
	public void savePerson(Person entity, String userId) throws SpringcafException, SQLException
	{
		this.saveEntity(entity, userId);
	}

	@Override
	public Person findPersonByPk(Integer personId) throws SpringcafException, SQLException
	{
		return this.getEntityByPk(personId);
	}

	@Override
	public void deletePersonByPk(Integer personId) throws SpringcafException, SQLException
	{
		this.deleteEntityByPk(personId);
	}

	@Override
	public List<Person> findAllPerson() throws SpringcafException, SQLException
	{
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + ".person";

		return databaseService.getObjectListFromSqlQuery(Person.class, sqlQuery, null);
	}

}
