package com.springcaf.core.jdbc.classgen;

import com.springcaf.core.jdbc.model.JdbcJavaType;
import com.springcaf.core.jdbc.model.KeyType;
import com.springcaf.core.jdbc.model.TableColumnModel;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.util.StringFormatUtils;
import com.springcaf.core.util.StringUtils;

final class ClassGenObjectModelUtils implements ClassGenConstants {

	/**
	 * Create an object class from a table model
	 * @param tableModel
	 * @param packageName
	 * @param className
	 * @return
	 */
	public static String createObjectClassFromTableModel(TableModel tableModel, String packageName, String className)
	{

		StringBuffer buffer = new StringBuffer();
		
		/*******************************************************************************
		 * Build class definition
		 ******************************************************************************/
		buffer.append("package " + packageName + ";" + LINE_FEED + LINE_FEED);
		
		buffer.append("import com.springcaf.core.jdbc.annotation.InsertOnly;" + LINE_FEED);
		buffer.append("import com.springcaf.core.jdbc.annotation.JdbcDataField;" + LINE_FEED);
		buffer.append("import com.springcaf.core.jdbc.annotation.JdbcDataTable;" + LINE_FEED);
		buffer.append("import com.springcaf.core.jdbc.annotation.PrimaryKey;" + LINE_FEED);
		buffer.append("import com.springcaf.core.jdbc.model.KeyType;" + LINE_FEED + LINE_FEED);
		buffer.append("/**" + LINE_FEED);
		buffer.append(" * System generated class used to represent the query as an object. " + LINE_FEED);
		buffer.append(" */" + LINE_FEED);
		buffer.append("@JdbcDataTable(tableName=\"" + tableModel.getTableName() + "\")" + LINE_FEED);
		createPrimaryKeyAnnotation(buffer, tableModel);
		//@PrimaryKey(keyType=KeyType.UUID, keyColumns={"note_id"})
		buffer.append("public class " + className + LINE_FEED);
		buffer.append("{" + LINE_FEED);
		
		/*******************************************************************************
		 * private variables
		 ******************************************************************************/
		// loop through the column specs
		for(TableColumnModel column: tableModel.getColumns())
		{
			JdbcJavaType modelElementType = column.getColumnType();
			
			// column annotations
			buffer.append(TAB + "@JdbcDataField(columnName=\"" + column.getColumnName() + "\"");
			if(modelElementType == JdbcJavaType.STRING)
			{
				buffer.append(", columnSize=" + column.getPrecision());
			}
			else if(modelElementType == JdbcJavaType.INTEGER)
			{
				buffer.append(", columnSize=" + column.getPrecision());
			}
			if(modelElementType == JdbcJavaType.DOUBLE)
			{
				buffer.append(", columnSize=" + column.getPrecision());
				buffer.append(", decimal=" + column.getScale());
			}
			if(!column.isNullable())
			{
				buffer.append(", required=true");
			}
			buffer.append(")" + LINE_FEED);
			
			// insert only
			if(tableModel.getInsertOnlyColumns() != null && tableModel.getInsertOnlyColumns().contains(column.getColumnName()))
			{
				buffer.append(TAB + "@InsertOnly" + LINE_FEED);
			}
			
			// model element type
			String variableName = StringFormatUtils.underscoreToCamel(column.getColumnName(), false);
			buffer.append(TAB + "private " + modelElementType.getJavaType() + " " + variableName + ";" + LINE_FEED + LINE_FEED);
		}
		//buffer.append(TAB + LINE_FEED);
		
		/*******************************************************************************
		 * Build constructor method
		 ******************************************************************************/
		buffer.append(TAB + "/**" + LINE_FEED);
		buffer.append(TAB + " * Constructor" + LINE_FEED);
		buffer.append(TAB + " */" + LINE_FEED);
		buffer.append(TAB + "public " + className + "()" + LINE_FEED);
		buffer.append(TAB + "{" + LINE_FEED);
		// constructor body
		buffer.append(TAB + TAB + "// Default constructor" + LINE_FEED);
		buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
		
		/*******************************************************************************
		 * Build getter and setter methods
		 ******************************************************************************/
		// loop through the column specs
		for(TableColumnModel column: tableModel.getColumns())
		{
			String methodPostfix = StringFormatUtils.underscoreToCamel(column.getColumnName(), true);
			String variableName = StringFormatUtils.underscoreToCamel(column.getColumnName(), false);
			
			// model element type
			JdbcJavaType modelElementType = column.getColumnType();
			
			// getter method
			String getterPrefix = "get";
			//if(SqlTypeConverter.ELEMENT_TYPE_BOOLEAN.equals(modelElementType))
			//{
			//	getterPrefix = "is";
			//}
			buffer.append(TAB + "/**" + LINE_FEED);
			buffer.append(TAB + " * getter " + LINE_FEED);
			buffer.append(TAB + " */" + LINE_FEED);
			buffer.append(TAB + "public " + modelElementType.getJavaType() + " " + getterPrefix + methodPostfix + "()" + LINE_FEED);
			buffer.append(TAB + "{" + LINE_FEED);
			buffer.append(TAB + TAB + "return this." + variableName + ";" + LINE_FEED);
			buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
			
			// setter method
			buffer.append(TAB + "/**" + LINE_FEED);
			buffer.append(TAB + " * setter " + LINE_FEED);
			buffer.append(TAB + " */" + LINE_FEED);
			buffer.append(TAB + "public void set" + methodPostfix + "(" + modelElementType.getJavaType() + " " + variableName + ")" + LINE_FEED);
			buffer.append(TAB + "{" + LINE_FEED);
			buffer.append(TAB + TAB + "this." + variableName + "=" + variableName + ";" + LINE_FEED);
			buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
		}

		/*******************************************************************************
		 * Create a toString methods
		 ******************************************************************************/
		//createCopyMethods(buffer, model, className);

		/*******************************************************************************
		 * Create a toString methods
		 ******************************************************************************/
		buffer.append(TAB + "/**" + LINE_FEED);
		buffer.append(TAB + " * toString method " + LINE_FEED);
		buffer.append(TAB + " */" + LINE_FEED);
		buffer.append(TAB + "public String toString()" + LINE_FEED);
		buffer.append(TAB + "{" + LINE_FEED);
		// loop through the column specs
		buffer.append(TAB + TAB + "StringBuffer buffer = new StringBuffer();" + LINE_FEED);
		for(TableColumnModel column: tableModel.getColumns())
		{
			String methodPostfix = StringFormatUtils.underscoreToCamel(column.getColumnName(), true);
			
			// model element type
			@SuppressWarnings("unused")
			JdbcJavaType modelElementType = column.getColumnType();
			
			// getter method
			String getterPrefix = "get";
			//if(SqlTypeConverter.ELEMENT_TYPE_BOOLEAN.equals(modelElementType))
			//{
			//	getterPrefix = "is";
			//}
			buffer.append(TAB + TAB + "buffer.append(\"" + column.getColumnName() + " = \" + " + "this." + getterPrefix + methodPostfix + "() + " + "\"\\r\\n\");" + LINE_FEED);
		}
		buffer.append(TAB + TAB + LINE_FEED);
		buffer.append(TAB + TAB + "return buffer.toString();" + LINE_FEED);
		buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);

		/*******************************************************************************
		 * close the class definition
		 ******************************************************************************/
		buffer.append("}" + LINE_FEED);
		
		return buffer.toString();
	}
	
	private static void createPrimaryKeyAnnotation(StringBuffer buffer, TableModel tableModel)
	{
		// primary key methods
		if(tableModel.getKeySourceType() == KeyType.AUTO_INCREMENT)
		{
			buffer.append("@PrimaryKey(keyType=KeyType.AUTO_INCREMENT, keyColumns={" + StringUtils.flattenStringArray(tableModel.getPrimaryKeyColumns(), "\"", "\"", ",") + "})" + LINE_FEED);
		}
		else if(tableModel.getKeySourceType() == KeyType.EXTERNAL_KEY)
		{
			buffer.append("@PrimaryKey(keyType=KeyType.BUSINESS_KEY, keyColumns={" + StringUtils.flattenStringArray(tableModel.getPrimaryKeyColumns(), "\"", "\"", ",") + "})" + LINE_FEED);
		}
		else if(tableModel.getKeySourceType() == KeyType.NEXTVAL)
		{
			buffer.append("@PrimaryKey(keyType=KeyType.NEXTVAL, keyColumns={" + StringUtils.flattenStringArray(tableModel.getPrimaryKeyColumns(), "\"", "\"", ",") + "})" + LINE_FEED);
		}
		else if(tableModel.getKeySourceType() == KeyType.UUID)
		{
			buffer.append("@PrimaryKey(keyType=KeyType.UUID, keyColumns={" + StringUtils.flattenStringArray(tableModel.getPrimaryKeyColumns(), "\"", "\"", ",") + "})" + LINE_FEED);
		}
		else if(tableModel.getKeySourceType() == KeyType.TABLE_MAX)
		{
			buffer.append("@PrimaryKey(keyType=KeyType.TABLE_MAX, keyColumns={" + StringUtils.flattenStringArray(tableModel.getPrimaryKeyColumns(), "\"", "\"", ",") + "})" + LINE_FEED);
		}
	}
	
	/**
	 * Create the copy methods (optional)
	 * @param buffer
	 * @param tableModel
	 * @param className
	 */
	public static void createCopyMethods(StringBuffer buffer, TableModel tableModel, String className)
	{
		/*******************************************************************************
		 * Create a copyDataForUpdate methods
		 ******************************************************************************/
		buffer.append(TAB + "/**" + LINE_FEED);
		buffer.append(TAB + " * copyDataForUpdate method - copy attributes from one object to another " + LINE_FEED);
		buffer.append(TAB + " */" + LINE_FEED);
		buffer.append(TAB + "public void copyDataForUpdate(" + className + " fromObject)" + LINE_FEED);
		buffer.append(TAB + "{" + LINE_FEED);
		// loop through the column specs
		for(TableColumnModel column: tableModel.getColumns())
		{
			String methodPostfix = StringFormatUtils.underscoreToCamel(column.getColumnName(), true);
			
			// model element type
			@SuppressWarnings("unused")
			JdbcJavaType modelElementType = column.getColumnType();
			
			// getter method
			String getterPrefix = "get";
			//if(SqlTypeConverter.ELEMENT_TYPE_BOOLEAN.equals(modelElementType))
			//{
			//	getterPrefix = "is";
			//}
			buffer.append(TAB + TAB + "this.set" + methodPostfix + "(fromObject." + getterPrefix + methodPostfix + "()" + ");" + LINE_FEED);
		}
		buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);

		/*******************************************************************************
		 * Create a copyDataAll methods
		 ******************************************************************************/
		buffer.append(TAB + "/**" + LINE_FEED);
		buffer.append(TAB + " * copyDataAll method - copy attributes from one object to another " + LINE_FEED);
		buffer.append(TAB + " */" + LINE_FEED);
		buffer.append(TAB + "public void copyDataAll(" + className + " fromObject)" + LINE_FEED);
		buffer.append(TAB + "{" + LINE_FEED);
		// loop through the column specs
		for(TableColumnModel column: tableModel.getColumns())
		{
			String methodPostfix = StringFormatUtils.underscoreToCamel(column.getColumnName(), true);
			
			// model element type
			@SuppressWarnings("unused")
			JdbcJavaType modelElementType = column.getColumnType();
			
			// getter method
			String getterPrefix = "get";
			//if(SqlTypeConverter.ELEMENT_TYPE_BOOLEAN.equals(modelElementType))
			//{
			//	getterPrefix = "is";
			//}
			buffer.append(TAB + TAB + "this.set" + methodPostfix + "(fromObject." + getterPrefix + methodPostfix + "()" + ");" + LINE_FEED);
		}
		buffer.append(TAB + "}" + LINE_FEED + LINE_FEED);
	}
}
