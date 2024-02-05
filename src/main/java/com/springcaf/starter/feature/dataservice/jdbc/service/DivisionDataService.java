package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.CommonCrudDataService;
import com.springcaf.starter.feature.dataservice.jdbc.model.Division;

/**
 * System generated class with service functions for the generated data object. 
 */
public interface DivisionDataService extends CommonCrudDataService<Division>
{
	/************************************************************
	 * Service functions
	 ***********************************************************/
	public void saveDivision(Division entity, String userId) throws SpringcafException, SQLException;
	public Division findDivisionByPk(Integer divisionId) throws SpringcafException, SQLException;
	public void deleteDivisionByPk(Integer divisionId) throws SpringcafException, SQLException;
	public List<Division> findAllDivision() throws SpringcafException, SQLException;
}
