package com.springcaf.core.jdbc.classgen;

import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.util.StringFormatUtils;
import com.springcaf.core.util.StringUtils;

final class ClassGenServiceUtils implements ClassGenConstants {
	
	/**
	 * Create the service definition class
	 * @param tableModel
	 * @param packageName
	 * @param className
	 * @param userIdWithTypeString
	 * @param parentKeys
	 * @return
	 */
	public static String createEntityServiceClassFromTableModel(TableModel tableModel, String packageName, String className, String userIdWithTypeString, String[] parentKeys)
	{
		StringBuffer buffer = new StringBuffer();
		
		// calculate model package name
		String objPackageName = packageName.substring(0, packageName.lastIndexOf(".")) + ".model";
		String objClassName = className.substring(0, className.lastIndexOf("DataService"));
		
		/*******************************************************************************
		 * Build class definition
		 ******************************************************************************/
		buffer.append("package " + packageName + ";" + LINE_FEED + LINE_FEED);
		// imports
		buffer.append("import java.sql.SQLException;" + LINE_FEED);
		buffer.append("import java.util.List;" + LINE_FEED);
		buffer.append("import com.springcaf.core.exception.SpringcafException;" + LINE_FEED);
		buffer.append("import com.springcaf.core.jdbc.service.CommonCrudDataService;" + LINE_FEED);
		buffer.append("import " + objPackageName + "." + objClassName + ";" + LINE_FEED + LINE_FEED);
		buffer.append("/**" + LINE_FEED);
		buffer.append(" * System generated class with service functions for the generated data object. " + LINE_FEED);
		buffer.append(" */" + LINE_FEED);
		buffer.append("public interface " + className + " extends CommonCrudDataService<" + objClassName + ">" + LINE_FEED);
		buffer.append("{" + LINE_FEED);
		
		/*******************************************************************************
		 * create service definitions
		 ******************************************************************************/
		buffer.append(TAB + "/************************************************************" + LINE_FEED);
		buffer.append(TAB + " * Service functions" + LINE_FEED);
		buffer.append(TAB + " ***********************************************************/" + LINE_FEED);
		buffer.append(TAB + "public void save" + objClassName + "(" + objClassName + " entity, " + userIdWithTypeString + ") throws SpringcafException, SQLException;" + LINE_FEED);
		if(tableModel.hasPrimaryKeys())
		{
			// by primary key
			buffer.append(TAB + "public " + objClassName + " find" + objClassName + "ByPk(" + formatTableModelPrimaryKeysAsParms(tableModel) + ") throws SpringcafException, SQLException;" + LINE_FEED);
			buffer.append(TAB + "public void delete" + objClassName + "ByPk(" + formatTableModelPrimaryKeysAsParms(tableModel) + ") throws SpringcafException, SQLException;" + LINE_FEED);
		}
		else
		{
			// N/A
		}
		if(parentKeys != null && parentKeys.length > 0)
		{
			String lastColumn = parentKeys[parentKeys.length-1];
			String byName = StringFormatUtils.underscoreToCamel(lastColumn, true);
			String varList = convertColumnListToFunctionParms(tableModel, parentKeys);
			buffer.append(TAB + "public List<" + objClassName + "> findAll" + objClassName + "By" + byName + "(" + varList + ") throws SpringcafException, SQLException;" + LINE_FEED);
		}
		else
		{
			buffer.append(TAB + "public List<" + objClassName + "> findAll" + objClassName + "() throws SpringcafException, SQLException;" + LINE_FEED);
		}
		
		/*******************************************************************************
		 * close the class definition
		 ******************************************************************************/
		buffer.append("}" + LINE_FEED);
		
		return buffer.toString();
	}

	/**
	 * Create the service impl class
	 * @param tableModel
	 * @param packageName
	 * @param commonDataImplPackageName
	 * @param commonDataImplClassName
	 * @param className
	 * @param userIdWithTypeString
	 * @param parentKeys
	 * @return
	 */
	public static String createEntityServiceImplClassFromTableModel(TableModel tableModel, String packageName, String commonDataImplPackageName, String commonDataImplClassName, String className, String userIdWithTypeString, String[] parentKeys)
	{
		StringBuffer buffer = new StringBuffer();
		
		// calculate model package name
		String objPackageName = packageName.substring(0, packageName.lastIndexOf(".")) + ".model";
		String objClassName = className.substring(0, className.lastIndexOf("DataServiceImpl"));
		String serviceClassName = className.substring(0, className.lastIndexOf("Impl"));
		
		// build the primary key name list
		String pKeyString = "";
		for(String keyColumn : tableModel.getPrimaryKeyColumns())
		{
			pKeyString += StringFormatUtils.underscoreToCamel(keyColumn, false) + ", ";
		}
		pKeyString = StringUtils.trimLastStringBlock(pKeyString, ",");
		
		/*******************************************************************************
		 * Build class definition
		 ******************************************************************************/
		buffer.append("package " + packageName + ";" + LINE_FEED + LINE_FEED);
		// imports
		buffer.append("import java.sql.SQLException;" + LINE_FEED);
		buffer.append("import java.util.List;" + LINE_FEED);
		buffer.append("import com.springcaf.core.exception.SpringcafException;" + LINE_FEED);
		buffer.append("import com.springcaf.core.jdbc.model.SqlParmSet;" + LINE_FEED);
		buffer.append("import com.springcaf.core.jdbc.service.AbstractDataService;" + LINE_FEED);
		buffer.append("import " + commonDataImplPackageName + "." + commonDataImplClassName + ";" + LINE_FEED);
		buffer.append("import " + objPackageName + "." + objClassName + ";" + LINE_FEED + LINE_FEED);
		buffer.append("/**" + LINE_FEED);
		buffer.append(" * System generated class with service functions for the generated data object. " + LINE_FEED);
		buffer.append(" */" + LINE_FEED);
		buffer.append("public class " + className + " extends " + commonDataImplClassName + "<" + objClassName + "> implements " + serviceClassName + LINE_FEED);
		buffer.append("{" + LINE_FEED);
		
		/*******************************************************************************
		 * Constructor
		 ******************************************************************************/
		buffer.append(TAB + "/**" + LINE_FEED);
		buffer.append(TAB + " * Constructor" + LINE_FEED);
		buffer.append(TAB + " * @param databaseService" + LINE_FEED);
		buffer.append(TAB + " * @param schemaNameOverride" + LINE_FEED);
		buffer.append(TAB + " */" + LINE_FEED);
		buffer.append(TAB + "public " + className + "(AbstractDataService databaseService, String schemaNameOverride)" + LINE_FEED);
		buffer.append(TAB + "{" + LINE_FEED);
		buffer.append(TAB + TAB + "super(databaseService, schemaNameOverride);" + LINE_FEED);
		buffer.append(TAB + "}" + LINE_FEED+LINE_FEED);

		/*******************************************************************************
		 * Entity Class
		 ******************************************************************************/
		buffer.append(TAB + "/**" + LINE_FEED);
		buffer.append(TAB + " * Entity Class" + LINE_FEED);
		buffer.append(TAB + " */" + LINE_FEED);
		buffer.append(TAB + "@Override" + LINE_FEED);
		buffer.append(TAB + "public Class<" + objClassName + "> getEntityClass()" + LINE_FEED);
		buffer.append(TAB + "{" + LINE_FEED);
		buffer.append(TAB + TAB + "return " + objClassName + ".class;" + LINE_FEED);
		buffer.append(TAB + "}" + LINE_FEED+LINE_FEED);
		
		/*******************************************************************************
		 * create default service implementations
		 ******************************************************************************/
		buffer.append(TAB + "/************************************************************" + LINE_FEED);
		buffer.append(TAB + " * default service implementations" + LINE_FEED);
		buffer.append(TAB + " ***********************************************************/" + LINE_FEED);
		//////////////////////////////////////////////// 
		// save
		////////////////////////////////////////////////
		buffer.append(TAB + "@Override" + LINE_FEED);
		buffer.append(TAB + "public void save" + objClassName + "(" + objClassName + " entity, " + userIdWithTypeString + ") throws SpringcafException, SQLException" + LINE_FEED);
		buffer.append(TAB + "{" + LINE_FEED);
		String[] userIdValues = userIdWithTypeString.split(" ");
		buffer.append(TAB + TAB + "this.saveEntity(entity, " + userIdValues[1] + ");" + LINE_FEED);
		buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
		if(tableModel.hasPrimaryKeys())
		{
			//////////////////////////////////////////////// 
			// findByPk
			//////////////////////////////////////////////// 
			buffer.append(TAB + "@Override" + LINE_FEED);
			buffer.append(TAB + "public " + objClassName + " find" + objClassName + "ByPk(" + formatTableModelPrimaryKeysAsParms(tableModel) + ") throws SpringcafException, SQLException" + LINE_FEED);
			buffer.append(TAB + "{" + LINE_FEED);
			buffer.append(TAB + TAB + "return this.getEntityByPk(" + pKeyString + ");" + LINE_FEED);
			buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
			//////////////////////////////////////////////// 
			// deleteByPk
			//////////////////////////////////////////////// 
			buffer.append(TAB + "@Override" + LINE_FEED);
			buffer.append(TAB + "public void delete" + objClassName + "ByPk(" + formatTableModelPrimaryKeysAsParms(tableModel) + ") throws SpringcafException, SQLException" + LINE_FEED);
			buffer.append(TAB + "{" + LINE_FEED);
			buffer.append(TAB + TAB + "this.deleteEntityByPk(" + pKeyString + ");" + LINE_FEED);
			buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
		}
		else
		{
			// N/A
		}
		if(parentKeys != null && parentKeys.length > 0)
		{
			String lastColumn = parentKeys[parentKeys.length-1];
			String byName = StringFormatUtils.underscoreToCamel(lastColumn, true);
			String varList = convertColumnListToFunctionParms(tableModel, parentKeys);
			//////////////////////////////////////////////// 
			// findAllByParentKey
			//////////////////////////////////////////////// 
			buffer.append(TAB + "@Override" + LINE_FEED);
			buffer.append(TAB + "public List<" + objClassName + "> findAll" + objClassName + "By" + byName + "(" + varList + ") throws SpringcafException, SQLException" + LINE_FEED);
			buffer.append(TAB + "{" + LINE_FEED);
			buffer.append(TAB + TAB + "// the search query" + LINE_FEED);
			buffer.append(TAB + TAB + "String sqlQuery = \"SELECT * FROM " + getQualifiedTableName(tableModel) + " WHERE" + StringUtils.flattenStringArray(parentKeys, " ", " = ? ", "AND") + "\";" + LINE_FEED); 
			buffer.append(TAB + TAB + "//map search key parms" + LINE_FEED);
			buffer.append(TAB + TAB + "SqlParmSet searchParms = new SqlParmSet();" + LINE_FEED);
			for(String keyColumn : parentKeys)
			{
				buffer.append(TAB + TAB + "searchParms.addSqlParm(\"" + keyColumn + "\", " + StringFormatUtils.underscoreToCamel(keyColumn, false) + ");" + LINE_FEED);
			}
			buffer.append(LINE_FEED);
			buffer.append(TAB + TAB + "return databaseService.getObjectListFromSqlQuery(" + objClassName + ".class, sqlQuery, searchParms);" + LINE_FEED);
			buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
		}
		else
		{
			//////////////////////////////////////////////// 
			// findAll
			//////////////////////////////////////////////// 
			buffer.append(TAB + "@Override" + LINE_FEED);
			buffer.append(TAB + "public List<" + objClassName + "> findAll" + objClassName + "() throws SpringcafException, SQLException" + LINE_FEED);
			buffer.append(TAB + "{" + LINE_FEED);
			buffer.append(TAB + TAB + "String sqlQuery = \"SELECT * FROM " + getQualifiedTableName(tableModel) + "\";" + LINE_FEED + LINE_FEED); 
			buffer.append(TAB + TAB + "return databaseService.getObjectListFromSqlQuery(" + objClassName + ".class, sqlQuery, null);" + LINE_FEED);
			buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
		}
		
		/*******************************************************************************
		 * close the class definition
		 ******************************************************************************/
		buffer.append("}" + LINE_FEED);
		
		return buffer.toString();
	}

	/**
	 * Build primary key parameters
	 * @param tableModel
	 * @return
	 */
	private static String formatTableModelPrimaryKeysAsParms(TableModel tableModel)
	{
		if(tableModel == null)
		{
			return "";
		}
		
		return convertColumnListToFunctionParms(tableModel, tableModel.getPrimaryKeyColumns());
	}
	
	private static String convertColumnListToFunctionParms(TableModel tableModel, String[] columnList)
	{
		if(tableModel == null || columnList == null || columnList.length == 0)
		{
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		for(String key : columnList)
		{
			buffer.append(tableModel.getColumnModelByName(key).getColumnType().getJavaType() + " " + StringFormatUtils.underscoreToCamel(key, false) + ",");
		}
		
		return StringUtils.trimLastStringBlock(buffer.toString(), ",");
	}
	
	private static String getQualifiedTableName(TableModel tableModel)
	{
		String tableQualifiedName = tableModel.getQualifiedTableName();
		if(!StringUtils.isNullOrEmpty(tableModel.getSchemaName()))
		{
			tableQualifiedName = "\" + schemaNameOverride + \"." + tableModel.getTableName();
		}
		
		return tableQualifiedName;
	}
}
