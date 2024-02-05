package com.springcaf.core.jdbc.service;

import java.sql.SQLException;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;

public interface CommonCrudDataService <T> {

	abstract public Class<T> getEntityClass();

	// application-level entity operations
	public void saveEntity(T entity, String userId) throws SpringcafException, SQLException;
	public T getEntityByPk(String primaryKey) throws SpringcafException, SQLException;
	public T getEntityByPk(Integer primaryKey) throws SpringcafException, SQLException;
	public T getEntityByPk(String[] primaryKeys) throws SpringcafException, SQLException;
	public void deleteEntityByPk(String primaryKey) throws SpringcafException, SQLException;
	public void deleteEntityByPk(Integer primaryKey) throws SpringcafException, SQLException;
	public void deleteEntityByPk(String[] primaryKeys) throws SpringcafException, SQLException;
	public List<T> getEntityList(String sqlQuery) throws SpringcafException, SQLException;
	public List<T> getEntityList(String whereString, String sortString) throws SpringcafException, SQLException;
}
