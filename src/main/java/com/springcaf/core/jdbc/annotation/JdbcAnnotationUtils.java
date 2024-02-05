package com.springcaf.core.jdbc.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.JdbcJavaType;
import com.springcaf.core.jdbc.model.KeyType;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.util.StringUtils;

public class JdbcAnnotationUtils {
	
	public static TableModel getTableModel(Object obj, String schemaNameOverride) throws SpringcafException
	{
		return getTableModel(obj.getClass(), schemaNameOverride);
	}

	public static <T> TableModel getTableModel(Class<T> cls, String schemaNameOverride) throws SpringcafException
	{
		// parse tableName
		JdbcDataTable tableAnno = getAnnotation(cls, JdbcDataTable.class);
		String tableName = tableAnno.tableName();
		if(StringUtils.isNullOrEmpty(tableName))
		{
			throw new SpringcafException("Missing tableName attribute");
		}
		TableModel tableModel = new TableModel(schemaNameOverride, tableName);
		
		// set schema name override
		tableModel.setSchemaName(schemaNameOverride);
		
		// set primary key
		PrimaryKey pkAnno = getAnnotation(cls, PrimaryKey.class);
		String[] keyColumns = pkAnno.keyColumns();
		KeyType keyType = pkAnno.keyType();
		if(keyColumns != null)
		{
			if(keyType == null)
			{
				keyType = KeyType.NO_KEY;
			}
			
			tableModel.setPrimaryKeyColumnsOfKeySourceType(keyColumns, keyType);
		}
		
		// set column specs and insertOnly flags
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields)
		{
			JdbcJavaType columnType = getColumnType(field);
			boolean insertOnly = false;
			String columnName = null;
			int precision = 0;
			int scale = 0;
			boolean required = false;
			boolean annotated = false;
			for (Annotation fieldAnno : field.getAnnotations())
			{
				if ( fieldAnno instanceof JdbcDataField) {
					annotated = true;
					JdbcDataField anno = (JdbcDataField) fieldAnno;
					columnName = anno.columnName();
					precision = anno.columnSize();
					scale = anno.decimal();
					required = anno.required();
					
					if(precision == 0)
					{
						if(columnType == JdbcJavaType.DATE)
						{
							precision = 19;
						}
						else if(columnType == JdbcJavaType.BOOLEAN)
						{
							precision = 1;
						}
					}
				}
				
				if(fieldAnno instanceof InsertOnly)
				{
					insertOnly = true;
				}
			}
			
			if(annotated)
			{
				tableModel.addColumnModel(columnName, columnType, !required, precision, scale);
				
				if(insertOnly)
				{
					tableModel.addInsertOnlyColumn(columnName);
				}
			}
		}
		
		return tableModel;
	}
	
	@SuppressWarnings("unchecked")
	private static <T, T1 extends Annotation> T1 getAnnotation(Class<T> objClass, Class<T1> annotationClass) throws SpringcafException
	{
		Annotation classAnno = objClass.getAnnotation(annotationClass);
		if(classAnno != null)
		{
			if(classAnno instanceof Annotation)
			{
				return (T1)classAnno;
			}
		}

		throw new SpringcafException("Missing annotation for " + annotationClass.getName());
	}
	
	private static JdbcJavaType getColumnType(Field field)
	{
		JdbcJavaType columnType = JdbcJavaType.STRING;
		if(field.getType() == String.class)
		{
			columnType = JdbcJavaType.STRING;
		}
		else if(field.getType() == Integer.class)
		{
			columnType = JdbcJavaType.INTEGER;
		}
		else if(field.getType() == Double.class)
		{
			columnType = JdbcJavaType.DOUBLE;
		}
		else if(field.getType() == java.util.Date.class)
		{
			columnType = JdbcJavaType.DATE;
		}
		else if(field.getType() == Boolean.class)
		{
			columnType = JdbcJavaType.BOOLEAN;
		}
		
		return columnType;
	}
}
