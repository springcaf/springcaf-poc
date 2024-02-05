package com.springcaf.starter.feature.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.model.form.WebFormModel;
import com.springcaf.starter.feature.dataservice.jdbc.model.Brand;
import com.springcaf.starter.feature.dataservice.jdbc.model.Department;
import com.springcaf.starter.feature.dataservice.jdbc.model.Division;
import com.springcaf.starter.feature.dataservice.jdbc.service.BrandDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.DepartmentDataService;
import com.springcaf.starter.feature.dataservice.jdbc.service.DivisionDataService;
import com.springcaf.starter.feature.ui.widget.DropdownInputWidget;

public class FeatureDropDownListServiceImpl implements FeatureDropDownListService {
	
	private Map<String, WebFormModel> registeredFormModels = new HashMap<String, WebFormModel>();
	
	@Autowired
	DivisionDataService divisionDataService;
	
	@Autowired
	DepartmentDataService departmentDataService;

	@Autowired
	BrandDataService brandDataService;

	/**
	 * Constructor
	 */
	public FeatureDropDownListServiceImpl()
	{
		this.registerFormModels();
	}
	
	private void registerFormModels()
	{
		DropdownInputWidget widget = new DropdownInputWidget(null, null, null, null);
		registeredFormModels.put(REFKEY_SELECT_divisionId, widget.getModel());
		registeredFormModels.put(REFKEY_SELECT_departmentId, widget.getModel());
		registeredFormModels.put(REFKEY_SELECT_brandId, widget.getModel());
	}
	
	@Override
	public KeyValueListModel getDropdownListByRef(String refKey, String refValue) throws SpringcafException, SQLException
	{
		if(REFKEY_SELECT_divisionId.equals(refKey))
		{
			return getDivisionList();
		}
		else if(REFKEY_SELECT_departmentId.equals(refKey))
		{
			Integer divisionId = Integer.valueOf(refValue);
			return getDepartmentList(divisionId);
		}
		else if(REFKEY_SELECT_brandId.equals(refKey))
		{
			Integer departmentId = Integer.valueOf(refValue);
			return getBrandList(departmentId);
		}
		else if(REFKEY_SELECT_quarterId.equals(refKey))
		{
			return getQuarterList();
		}
		
		return null;
	}
	
	public KeyValueListModel getDivisionList() throws SpringcafException, SQLException 
	{
		List<Division> divList = divisionDataService.findAllDivision();
		KeyValueListModel divisions = new KeyValueListModel();
		
		for(Division division : divList)
		{
			divisions.addKeyValueItem(division.getDivisionId().toString(), division.getDivisionName());
		}
		
		return divisions;
	}

	public KeyValueListModel getDepartmentList(Integer divisionId) throws SpringcafException, SQLException 
	{
		List<Department> deptList = departmentDataService.findAllDepartmentByDivisionId(divisionId);
		KeyValueListModel departments = new KeyValueListModel();
		
		for(Department department : deptList)
		{
			departments.addKeyValueItem(department.getDepartmentId().toString(), department.getDepartmentName());
		}
		
		return departments;
	}

	public KeyValueListModel getBrandList(Integer departmentId) throws SpringcafException, SQLException 
	{
		List<Brand> brandList = brandDataService.findAllBrandByDepartmentId(departmentId);
		KeyValueListModel brands = new KeyValueListModel();
		
		for(Brand brand : brandList)
		{
			brands.addKeyValueItem(brand.getBrandId().toString(), brand.getBrandName());
		}
		
		return brands;
	}
	
	public KeyValueListModel getQuarterList() throws SpringcafException, SQLException 
	{
		KeyValueListModel quarters = new KeyValueListModel();

		quarters.addKeyValueItem("1", "Q1");
		quarters.addKeyValueItem("2", "Q2");
		quarters.addKeyValueItem("3", "Q3");
		quarters.addKeyValueItem("4", "Q4");
		
		return quarters;
	}

	@Override
	public WebFormModel getRegisteredModel(String formElementKey)
	{
		return this.registeredFormModels.get(formElementKey);
	}
	
	public Map<String, WebFormModel> getRegisteredFormModels() {
		return registeredFormModels;
	}

	public void setRegisteredFormModels(
			Map<String, WebFormModel> registeredFormModels) {
		this.registeredFormModels = registeredFormModels;
	}

}
