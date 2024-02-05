package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;
import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.CommonCrudDataService;
import com.springcaf.starter.feature.dataservice.jdbc.model.Person;

/**
 * System generated class with service functions for the generated data object. 
 */
public interface PersonDataService extends CommonCrudDataService<Person>
{
	/************************************************************
	 * Service functions
	 ***********************************************************/
	public void savePerson(Person entity, String userId) throws SpringcafException, SQLException;
	public Person findPersonByPk(Integer personId) throws SpringcafException, SQLException;
	public void deletePersonByPk(Integer personId) throws SpringcafException, SQLException;
	public List<Person> findAllPerson() throws SpringcafException, SQLException;
}
