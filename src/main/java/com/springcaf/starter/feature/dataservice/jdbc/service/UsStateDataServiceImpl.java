package com.springcaf.starter.feature.dataservice.jdbc.service;

import java.sql.SQLException;
import java.util.List;
import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.AbstractDataService;
import com.springcaf.core.web.model.KeyValueListModel;
import com.springcaf.starter.common.dataservice.jdbc.service.DefaultCrudDataServiceImpl;
import com.springcaf.starter.feature.dataservice.jdbc.model.UsState;

/**
 * System generated class with service functions for the generated data object. 
 */
public class UsStateDataServiceImpl extends DefaultCrudDataServiceImpl<UsState> implements UsStateDataService
{
	/**
	 * Constructor
	 * @param databaseService
	 * @param schemaNameOverride
	 */
	public UsStateDataServiceImpl(AbstractDataService databaseService, String schemaNameOverride)
	{
		super(databaseService, schemaNameOverride);
	}

	/**
	 * Entity Class
	 */
	@Override
	public Class<UsState> getEntityClass()
	{
		return UsState.class;
	}

	/************************************************************
	 * default service implementations
	 ***********************************************************/
	@Override
	public void saveUsState(UsState entity, String userId) throws SpringcafException, SQLException
	{
		this.saveEntity(entity, userId);
	}

	@Override
	public UsState findUsStateByPk(String stateCode) throws SpringcafException, SQLException
	{
		return this.getEntityByPk(stateCode);
	}

	@Override
	public void deleteUsStateByPk(String stateCode) throws SpringcafException, SQLException
	{
		this.deleteEntityByPk(stateCode);
	}

	@Override
	public List<UsState> findAllUsState() throws SpringcafException, SQLException
	{
		String sqlQuery = "SELECT * FROM " + schemaNameOverride + ".us_state "
				+ "ORDER BY state_name ";

		return databaseService.getObjectListFromSqlQuery(UsState.class, sqlQuery, null);
	}

	@Override
	public KeyValueListModel getUsStateList() throws SpringcafException, SQLException {
		
		KeyValueListModel usStateList = new KeyValueListModel();
		List<UsState> allUsStates = this.findAllUsState();
		
		for(UsState state : allUsStates)
		{
			usStateList.addKeyValueItem(state.getStateCode(), state.getStateName());
		}
		
		return usStateList;
	}

}
