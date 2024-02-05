package com.springcaf.core.jdbc.service;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.TableModel;

public interface DataChangeHandler {
	
	public void beforeDatabaseChange(TableModel entityModel, Object oldObject, Object newObject) throws SpringcafException;
	public void afterDatabaseChange(TableModel entityModel, Object oldObject, Object newObject) throws SpringcafException;
}
