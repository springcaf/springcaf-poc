package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.service.AbstractDataService;
import com.springcaf.starter.common.dataservice.jdbc.service.DefaultCrudDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.model.Department;

/**
 * System generated class with service functions for the generated data object. 
 */
public class DepartmentDataServiceImpl extends DefaultCrudDataServiceImpl<Department> implements DepartmentDataService
{
	/**
	 * Constructor
	 * @param databaseService
	 * @param schemaNameOverride
	 */
	public DepartmentDataServiceImpl(AbstractDataService databaseService, String schemaNameOverride)
	{
		super(databaseService, schemaNameOverride);
	}

	/**
	 * Entity Class
	 */
	@Override
	public Class<Department> getEntityClass()
	{
		return Department.class;
	}

	/************************************************************
	 * default service implementations
	 ***********************************************************/
	@Override
	public void saveDepartment(Department entity, String userId) throws SpringcafException, SQLException
	{
		this.saveEntity(entity, userId);
	}

	@Override
	public Department findDepartmentByPk(Integer departmentId) throws SpringcafException, SQLException
	{
		return this.getEntityByPk(departmentId);
	}

	@Override
	public void deleteDepartmentByPk(Integer departmentId) throws SpringcafException, SQLException
	{
		this.deleteEntityByPk(departmentId);
	}

	@Override
	public List<Department> findAllDepartmentByDivisionId(Integer divisionId) throws SpringcafException, SQLException
	{
		// the search query
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + ".department WHERE division_id = ? "
				+ "ORDER BY department_name ";
		//map search key parms
		SqlParmSet searchParms = new SqlParmSet();
		searchParms.addSqlParm("division_id", divisionId);

		return databaseService.getObjectListFromSqlQuery(Department.class, sqlQuery, searchParms);
	}

}
