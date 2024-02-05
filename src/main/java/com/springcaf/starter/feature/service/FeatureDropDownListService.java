package com.springcaf.starter.feature.service;

import java.sql.SQLException;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.core.web.model.form.WebFormModel;

public interface FeatureDropDownListService {
	
	public String REFKEY_SELECT_divisionId = "DROPDOWN_INPUT_FORM_divisionId";
	public String REFKEY_SELECT_departmentId = "DROPDOWN_INPUT_FORM_departmentId";
	public String REFKEY_SELECT_brandId = "DROPDOWN_INPUT_FORM_brandId";
	public String REFKEY_SELECT_quarterId = "DROPDOWN_INPUT_FORM_quarterId";

	public KeyValueListModel getDropdownListByRef(String refKey, String refValue) throws SpringcafException, SQLException;
	public WebFormModel getRegisteredModel(String formElementKey);
	public KeyValueListModel getDivisionList() throws SpringcafException, SQLException; 
	public KeyValueListModel getDepartmentList(Integer divisionId) throws SpringcafException, SQLException; 
	public KeyValueListModel getBrandList(Integer departmentId) throws SpringcafException, SQLException;
	public KeyValueListModel getQuarterList() throws SpringcafException, SQLException; 

}
