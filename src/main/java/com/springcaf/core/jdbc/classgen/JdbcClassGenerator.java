package com.springcaf.core.jdbc.classgen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.KeyType;

public class JdbcClassGenerator {
	private String schemaName = null;
	private String baseFolder = null;
	private String basePackageName = null;
	private String commonDataImplPackageName = null;
	private String commonDataImplClassName = null;
	private String lastUpdateTimestampColumn = null;
	private String userIdWithTypeString = null;
	private String[] insertOnlyColumns = null;
	private Connection conn = null;

	/**
	 * Constructor
	 * @param schemaName
	 * @param baseFolder
	 * @param packageName
	 * @param commonDataImplPackageName
	 * @param commonDataImplClassName
	 * @param lastUpdateTimestampColumn
	 * @param userIdWithTypeString
	 * @param insertOnlyColumns
	 * @param conn
	 */
	public JdbcClassGenerator(String schemaName, String baseFolder, String basePackageName, String commonDataImplPackageName, String commonDataImplClassName, String lastUpdateTimestampColumn, String userIdWithTypeString, String[] insertOnlyColumns, Connection conn)
	{
		this.schemaName = schemaName;
		this.baseFolder = baseFolder;
		this.basePackageName = basePackageName;
		this.commonDataImplPackageName = commonDataImplPackageName;
		this.commonDataImplClassName = commonDataImplClassName;
		this.lastUpdateTimestampColumn = lastUpdateTimestampColumn;
		this.userIdWithTypeString = userIdWithTypeString;
		this.insertOnlyColumns = insertOnlyColumns;
		this.conn = conn;
	}
	
	/**
	 * Generate classes from a table
	 * @param tableSpec
	 * @param replaceExisting
	 * @return
	 * @throws IOException
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	private String createClassesForTable(ClassGenSpec tableSpec, boolean replaceExisting) throws IOException, SpringcafException, SQLException
	{
		tableSpec.setInsertOnlyColumns(insertOnlyColumns);
		String beanMapping = JavaClassGenUtils.generateEntityClassesFromTable(conn, schemaName, tableSpec.getTableName(), tableSpec, baseFolder, basePackageName, commonDataImplPackageName, commonDataImplClassName, replaceExisting, userIdWithTypeString, tableSpec.getParentKeyColumns());
		return beanMapping;
	}

	public String createClassesForTableNoKey(String tableName, String[] parentKeyColumns, boolean replaceExisting) throws IOException, SpringcafException, SQLException
	{
		ClassGenSpec tableSpec = new ClassGenSpec(tableName, null, KeyType.NO_KEY, lastUpdateTimestampColumn, parentKeyColumns);
		return createClassesForTable(tableSpec, replaceExisting);
	}
	
	public String createClassesForTablePKeyUuid(String tableName, String[] primaryKeyColumns, String[] parentKeyColumns, boolean replaceExisting) throws IOException, SpringcafException, SQLException
	{
		ClassGenSpec tableSpec = new ClassGenSpec(tableName, primaryKeyColumns, KeyType.UUID, lastUpdateTimestampColumn, parentKeyColumns);
		return createClassesForTable(tableSpec, replaceExisting);
	}
	
	public String createClassesForTablePKeyTableMax(String tableName, String[] primaryKeyColumns, String[] parentKeyColumns, boolean replaceExisting) throws IOException, SpringcafException, SQLException
	{
		ClassGenSpec tableSpec = new ClassGenSpec(tableName, primaryKeyColumns, KeyType.TABLE_MAX, lastUpdateTimestampColumn, parentKeyColumns);
		return createClassesForTable(tableSpec, replaceExisting);
	}
	
	public String createClassesForTablePKeyNextVal(String tableName, String[] primaryKeyColumns, String[] parentKeyColumns, boolean replaceExisting) throws IOException, SpringcafException, SQLException
	{
		ClassGenSpec tableSpec = new ClassGenSpec(tableName, primaryKeyColumns, KeyType.NEXTVAL, lastUpdateTimestampColumn, parentKeyColumns);
		return createClassesForTable(tableSpec, replaceExisting);
	}
	
	public String createClassesForTablePKeyAutoInc(String tableName, String[] primaryKeyColumns, String[] parentKeyColumns, boolean replaceExisting) throws IOException, SpringcafException, SQLException
	{
		ClassGenSpec tableSpec = new ClassGenSpec(tableName, primaryKeyColumns, KeyType.AUTO_INCREMENT, lastUpdateTimestampColumn, parentKeyColumns);
		return createClassesForTable(tableSpec, replaceExisting);
	}
	
	public String createClassesForTablePKeyExternal(String tableName, String[] primaryKeyColumns, String[] parentKeyColumns, boolean replaceExisting) throws IOException, SpringcafException, SQLException
	{
		ClassGenSpec tableSpec = new ClassGenSpec(tableName, primaryKeyColumns, KeyType.EXTERNAL_KEY, lastUpdateTimestampColumn, parentKeyColumns);
		return createClassesForTable(tableSpec, replaceExisting);
	}
	
	
}
