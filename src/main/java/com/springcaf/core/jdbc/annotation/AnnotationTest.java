package com.springcaf.core.jdbc.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@JdbcDataTable(tableName = "Class AnnotationTest")
public class AnnotationTest {
	
	@JdbcDataField(columnName = "field1_column", columnSize = 5)
	private String field1;

	@JdbcDataMethod(stringSample = "annotationTest()", intSample = 2)
	public void annotationTest() {

	}

	public static void main(String[] args) {
		
		Annotation classAnn = AnnotationTest.class.getAnnotation(JdbcDataTable.class);
		if(classAnn != null)
		{
			if (classAnn instanceof JdbcDataTable) {
				JdbcDataTable ann = (JdbcDataTable) classAnn;
				System.out.println(ann.tableName());
			}
			
		}

		Method[] methods = AnnotationTest.class.getDeclaredMethods();
		for (Method m : methods) {
			for (Annotation a : m.getAnnotations()) {
				if (a instanceof JdbcDataMethod) {
					JdbcDataMethod ann = (JdbcDataMethod) a;
					System.out.println(ann.stringSample());
					System.out.println(ann.intSample());
				}
			}
		}
		
		Field[] fields = AnnotationTest.class.getDeclaredFields();
		
		for (Field f : fields)
		{
			System.out.println(f.getDeclaringClass());
			System.out.println(f.getClass());
			System.out.println(f.getType());
			System.out.println(f.getGenericType());
			
			for (Annotation a : f.getAnnotations())
			{
				if ( a instanceof JdbcDataField) {
					JdbcDataField ann = (JdbcDataField) a;
					System.out.println(ann.columnName());
					System.out.println(ann.columnSize());
				}
			}
		}
	}

}