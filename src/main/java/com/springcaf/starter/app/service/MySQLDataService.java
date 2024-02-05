package com.springcaf.starter.app.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.service.AbstractDataService;

public class MySQLDataService extends AbstractDataService {
	
	@Autowired
	DataSource readerDataSource;

	@Autowired
	DataSource writerDataSource;

	@Override
	public Connection getReaderConnection() throws SQLException {
		
		return readerDataSource.getConnection();
	}

	@Override
	public Connection getWriterConnection() throws SQLException {
		
		return writerDataSource.getConnection();
	}

	public void deleteTableDataByTenantId(String tableName, String tenantId) throws SpringcafException, SQLException
	{
		String deleteTableData = "DELETE FROM " + tableName + " WHERE tenant_id = '" + tenantId + "'";
		this.deleteObjectFromSqlQuery(deleteTableData, null);
	}
}
