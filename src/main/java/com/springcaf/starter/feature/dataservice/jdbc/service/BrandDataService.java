package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.CommonCrudDataService;
import com.springcaf.starter.feature.dataservice.jdbc.model.Brand;

/**
 * System generated class with service functions for the generated data object. 
 */
public interface BrandDataService extends CommonCrudDataService<Brand>
{
	/************************************************************
	 * Service functions
	 ***********************************************************/
	public void saveBrand(Brand entity, String userId) throws SpringcafException, SQLException;
	public Brand findBrandByPk(Integer brandId) throws SpringcafException, SQLException;
	public void deleteBrandByPk(Integer brandId) throws SpringcafException, SQLException;
	public List<Brand> findAllBrandByDepartmentId(Integer departmentId) throws SpringcafException, SQLException;
}
