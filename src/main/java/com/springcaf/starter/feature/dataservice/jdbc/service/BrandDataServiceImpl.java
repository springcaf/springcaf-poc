package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.service.AbstractDataService;
import com.springcaf.starter.common.dataservice.jdbc.service.DefaultCrudDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.model.Brand;

/**
 * System generated class with service functions for the generated data object. 
 */
public class BrandDataServiceImpl extends DefaultCrudDataServiceImpl<Brand> implements BrandDataService
{
	/**
	 * Constructor
	 * @param databaseService
	 * @param schemaNameOverride
	 */
	public BrandDataServiceImpl(AbstractDataService databaseService, String schemaNameOverride)
	{
		super(databaseService, schemaNameOverride);
	}

	/**
	 * Entity Class
	 */
	@Override
	public Class<Brand> getEntityClass()
	{
		return Brand.class;
	}

	/************************************************************
	 * default service implementations
	 ***********************************************************/
	@Override
	public void saveBrand(Brand entity, String userId) throws SpringcafException, SQLException
	{
		this.saveEntity(entity, userId);
	}

	@Override
	public Brand findBrandByPk(Integer brandId) throws SpringcafException, SQLException
	{
		return this.getEntityByPk(brandId);
	}

	@Override
	public void deleteBrandByPk(Integer brandId) throws SpringcafException, SQLException
	{
		this.deleteEntityByPk(brandId);
	}

	@Override
	public List<Brand> findAllBrandByDepartmentId(Integer departmentId) throws SpringcafException, SQLException
	{
		// the search query
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + ".brand WHERE department_id = ? "
				+ "ORDER BY brand_name ";
		//map search key parms
		SqlParmSet searchParms = new SqlParmSet();
		searchParms.addSqlParm("department_id", departmentId);

		return databaseService.getObjectListFromSqlQuery(Brand.class, sqlQuery, searchParms);
	}

}
