package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.CommonCrudDataService;
import com.springcaf.starter.feature.dataservice.jdbc.model.Department;

/**
 * System generated class with service functions for the generated data object. 
 */
public interface DepartmentDataService extends CommonCrudDataService<Department>
{
	/************************************************************
	 * Service functions
	 ***********************************************************/
	public void saveDepartment(Department entity, String userId) throws SpringcafException, SQLException;
	public Department findDepartmentByPk(Integer departmentId) throws SpringcafException, SQLException;
	public void deleteDepartmentByPk(Integer departmentId) throws SpringcafException, SQLException;
	public List<Department> findAllDepartmentByDivisionId(Integer divisionId) throws SpringcafException, SQLException;
}
