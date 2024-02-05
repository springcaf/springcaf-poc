package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;
import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.CommonCrudDataService;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.starter.feature.dataservice.jdbc.model.UsState;

/**
 * System generated class with service functions for the generated data object. 
 */
public interface UsStateDataService extends CommonCrudDataService<UsState>
{
	/************************************************************
	 * Service functions
	 ***********************************************************/
	public void saveUsState(UsState entity, String userId) throws SpringcafException, SQLException;
	public UsState findUsStateByPk(String stateCode) throws SpringcafException, SQLException;
	public void deleteUsStateByPk(String stateCode) throws SpringcafException, SQLException;
	public List<UsState> findAllUsState() throws SpringcafException, SQLException;
	public KeyValueListModel getUsStateList() throws SpringcafException, SQLException;
}
