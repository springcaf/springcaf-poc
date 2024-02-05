package com.springcaf.core.jdbc.classgen;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.util.JdbcUtils;
import com.springcaf.core.util.StringFormatUtils;

public final class JavaClassGenUtils implements ClassGenConstants {
	
	/**
	 * Generate a set of Java classes from a table
	 * @param conn
	 * @param schemaName
	 * @param tableName
	 * @param primaryKeyNames
	 * @param keySourceType
	 * @param lastUpdateTimestampColumn
	 * @param baseFolder
	 * @param basePackageName
	 * @param replaceExisting
	 * @param userIdWithTypeString: String userId, or Integer userKey, etc.
	 * return: bean mapping
	 * @throws IOException
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	public static String generateEntityClassesFromTable(Connection conn, String schemaName, String tableName, ClassGenSpec classGenTableSpec, String baseFolder, String basePackageName, String commonDataImplPackageName, String commonDataImplClassName, boolean replaceExisting, String userIdWithTypeString, String[] parentKeyColumns) throws IOException, SpringcafException, SQLException
	{
		TableModel tableModel = JdbcUtils.getTableModel(conn, schemaName, tableName);
		tableModel.setPrimaryKeyColumnsOfKeySourceType(classGenTableSpec.getPrimaryKeyColumns(), classGenTableSpec.getKeySourceType());
		tableModel.setLastUpdateTimestampColumn(classGenTableSpec.getLastUpdateTimestampColumn());
		
		// insertOnlyColumns
		if(classGenTableSpec.getInsertOnlyColumns() != null)
		{
			for(String insertOnlyColumn : classGenTableSpec.getInsertOnlyColumns())
			{
				tableModel.addInsertOnlyColumn(insertOnlyColumn);
			}
		}

		// build the base class name from the table_name
		String baseClassName = StringFormatUtils.underscoreToCamel(tableName, true);
		
		// create the data object
		String objClassContent = ClassGenObjectModelUtils.createObjectClassFromTableModel(tableModel, basePackageName + ".model", baseClassName);
		String objClassFileName = ClassGenFileUtils.createJavaClass(baseFolder, basePackageName + ".model", baseClassName, objClassContent, replaceExisting);
		System.out.println("Written to " + objClassFileName);

		// create the entity service
		String serviceClassContent = ClassGenServiceUtils.createEntityServiceClassFromTableModel(tableModel, basePackageName + ".service", baseClassName + "DataService", userIdWithTypeString, parentKeyColumns);
		String serviceClassFileName = ClassGenFileUtils.createJavaClass(baseFolder, basePackageName + ".service", baseClassName + "DataService", serviceClassContent, replaceExisting);
		System.out.println("Written to " + serviceClassFileName);
		
		// create the entity service impl
		String serviceImplClassContent = ClassGenServiceUtils.createEntityServiceImplClassFromTableModel(tableModel, basePackageName + ".service", commonDataImplPackageName, commonDataImplClassName, baseClassName + "DataServiceImpl", userIdWithTypeString, parentKeyColumns);
		String serviceImplClassFileName = ClassGenFileUtils.createJavaClass(baseFolder, basePackageName + ".service", baseClassName + "DataServiceImpl", serviceImplClassContent, replaceExisting);
		System.out.println("Written to " + serviceImplClassFileName);
		
		// bean mapping string
		String beanMappingString = "<bean id=\"" 
						+ StringFormatUtils.firstCharToLower(baseClassName)
						+ "Service\""
						+ " class=\""
						+ basePackageName
						+ "."
						+ baseClassName
						+ "ServiceImpl"
						+ "\" />";
		
		return beanMappingString;
	}
	
	/**
	 * Generate a Java Model Class from a query
	 * @param conn
	 * @param sql
	 * @param parms
	 * @param packageName
	 * @param queryName
	 * @return
	 * @throws IOException
	 * @throws SpringcafException
	 * @throws SQLException
	 */
	public static void generateModelClassFromQuery(Connection conn, String sql, SqlParmSet parmSet, String baseFolder, String packageName, String queryName) throws IOException, SpringcafException, SQLException
	{
		TableModel tableModel = JdbcUtils.runSelectQuery(conn, sql, parmSet).getModel();

		String classContent = ClassGenObjectModelUtils.createObjectClassFromTableModel(tableModel, packageName, StringFormatUtils.underscoreToCamel(queryName, true));
		String classFileName = ClassGenFileUtils.createJavaClass(baseFolder, packageName, StringFormatUtils.underscoreToCamel(queryName, true), classContent, true);

		System.out.println("Written to " + classFileName);
	}
	
}
