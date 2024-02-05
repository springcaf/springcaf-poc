package com.springcaf.starter.util.codegen;

import java.sql.Connection;
import java.sql.SQLException;

import com.springcaf.core.jdbc.classgen.JdbcClassGenerator;
import com.springcaf.core.jdbc.util.JdbcUtils;

public class DataObjectGenerator {
	
	public static void main(String[] args)
	{
		try
		{
			String schemaName = "starterdb";
			String baseFolder = "./src/main/java";
			String basePackageName = "com.springcaf.starter.feature.dataservice.jdbc";
			String commonDataImplPackageName = "com.springcaf.starter.common.dataservice.jdbc.service";
			String commonDataImplClassName = "DefaultCrudDataServiceImpl";
			String lastUpdateTimestampColumn = "updated_at"; 
			String userIdWithTypeString = "String userId";
			String[] insertOnlyColumns = new String[] {"created_at", "created_by"};
			
			Connection conn = createVerticaConnection();
			JdbcClassGenerator classGen = new JdbcClassGenerator(schemaName, baseFolder, basePackageName, commonDataImplPackageName, commonDataImplClassName, lastUpdateTimestampColumn, userIdWithTypeString, insertOnlyColumns, conn);
			
			//classGen.createClassesForTablePKeyTableMax("division", new String[]{"division_id"}, null, true);
			//classGen.createClassesForTablePKeyTableMax("department", new String[]{"department_id"}, new String[]{"division_id"}, true);
			//classGen.createClassesForTablePKeyTableMax("brand", new String[]{"brand_id"}, new String[]{"department_id"}, true);
			//classGen.createClassesForTablePKeyTableMax("us_state", new String[]{"state_code"}, null, true);
			classGen.createClassesForTablePKeyAutoInc("person", new String[]{"person_id"}, null, true);
			
			closeConnection(conn);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	private static Connection createVerticaConnection() throws ClassNotFoundException, SQLException
	{
		String driver = "com.mysql.cj.jdbc.Driver";
		String server = "jdbc:mysql://localhost:3306/starterdb?useSSL=false&serverTimezone=UTC";
		String userName = "starteruser";
		String password = "starterpwd";
		return JdbcUtils.getConnection(driver, server, userName, password);
	}
	
	private static void closeConnection(Connection conn) throws SQLException
	{
		JdbcUtils.closeConnection(conn);
	}
	
}
