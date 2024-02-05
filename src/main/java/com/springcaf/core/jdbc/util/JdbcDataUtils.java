package com.springcaf.core.jdbc.util;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

import com.springcaf.core.exception.SpringcafException;
import com.springcaf.core.jdbc.model.JdbcJavaType;
import com.springcaf.core.jdbc.model.SqlParm;
import com.springcaf.core.jdbc.model.SqlParmSet;
import com.springcaf.core.jdbc.model.TableColumnModel;
import com.springcaf.core.jdbc.model.TableData;
import com.springcaf.core.jdbc.model.TableModel;
import com.springcaf.core.jdbc.model.TableRow;
import com.springcaf.core.util.ObjectUtils;

final class JdbcDataUtils {

	/**
	 * Run sql query to load results into a TableData object
	 * @param conn
	 * @param sql
	 * @param parmSet
	 * @return
	 * @throws SQLException
	 */
	public static TableData runSelectQuery(Connection conn, String sql, SqlParmSet parmSet) throws SQLException
	{
		TableData data = new TableData();
		PreparedStatement pstmt = conn.prepareStatement(sql);

		if(parmSet != null)
		{
			for(int i=1; i<=parmSet.getSqlParms().size(); i++)
			{
				SqlParm sqlParm = parmSet.getSqlParms().get(i-1);
				setSqlParameter(pstmt, i, sqlParm.getParmData(), sqlParm.getParmType());
			}
		}
		
		// query the result
		ResultSet rs = pstmt.executeQuery();
		
		// get the model
		TableModel model = JdbcMetaUtils.getTableModel(rs);
		data.setModel(model);
		
		// get the data
		while(rs.next())
		{
			data.addRow(getDataRow(rs, model));
		}
		
		// close resources
		rs.close();
		pstmt.close();
		
		return data;
	}
	
	/**
	 * Run an update query to insert/update/delete against the database
	 * @param conn
	 * @param sql
	 * @param parms
	 * @return
	 * @throws SQLException
	 */
	public static int runUpdateQuery(Connection conn, String sql, SqlParmSet parmSet) throws SQLException
	{
		PreparedStatement pstmt = conn.prepareStatement(sql);

		if(parmSet != null)
		{
			for(int i=1; i<=parmSet.getSqlParms().size(); i++)
			{
				SqlParm sqlParm = parmSet.getSqlParms().get(i-1);
				setSqlParameter(pstmt, i, sqlParm.getParmData(), sqlParm.getParmType());
			}
		}
		
		// query the result
		int count = pstmt.executeUpdate();
		
		// close resources
		pstmt.close();
		
		return count;
	}

	/**
	 * Insert an object with auto key
	 * @param conn
	 * @param dataObject
	 * @param sql
	 * @param parmSet
	 * @param primaryKeyColumnNames
	 * @return
	 * @throws SQLException
	 * @throws SpringcafException
	 */
	public static int runUpdateQueryAndRetrieveAutoPkForInsert(Connection conn, String sql, SqlParmSet parmSet, Object dataObject, String[] primaryKeyColumnNames) throws SQLException, SpringcafException
	{
		if(primaryKeyColumnNames == null || primaryKeyColumnNames.length != 1)
		{
			throw new SpringcafException("Invalid primary key defined for runInsertQueryRetrieveAutoPk() function");
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql, primaryKeyColumnNames);

		if(parmSet != null)
		{
			for(int i=1; i<=parmSet.getSqlParms().size(); i++)
			{
				SqlParm sqlParm = parmSet.getSqlParms().get(i-1);
				setSqlParameter(pstmt, i, sqlParm.getParmData(), sqlParm.getParmType());
			}
		}
		
		// query the result
		int count = pstmt.executeUpdate();
		
		// get the auto-generated primary key
		ResultSet rs = pstmt.getGeneratedKeys();
		if(rs.next())
		{
			Object intKey = rs.getObject(1);
			if(intKey instanceof BigInteger)
			{
				intKey = ((BigInteger)intKey).intValue();
			}

			// The generated order id
			ObjectUtils.setObjectMemberValue(dataObject, primaryKeyColumnNames[0], intKey);
		}
		
		// close resources
		pstmt.close();
		
		return count;
	}

	/**
	 * Execute batch updates
	 * @param conn
	 * @param updateSql
	 * @param parmSetList
	 * @return
	 * @throws SQLException
	 */
	public static int[] runBatchUpdateQuery(Connection conn, String updateSql, List<SqlParmSet> parmSetList) throws SQLException
	{
		int counts[] = null;
		try
		{
			PreparedStatement pstmt = conn.prepareStatement(updateSql);
			conn.setAutoCommit(false);

			for(SqlParmSet parmSet : parmSetList)
			{
				if(parmSet != null)
				{
					for(int i=1; i<=parmSet.getSqlParms().size(); i++)
					{
						SqlParm sqlParm = parmSet.getSqlParms().get(i-1);
						setSqlParameter(pstmt, i, sqlParm.getParmData(), sqlParm.getParmType());
					}
				}
				pstmt.addBatch();
			}
			
			// execute the query
			counts = pstmt.executeBatch();
			
			// commit the updates
			conn.commit();
			
			// close resources
			pstmt.close();
		}
		catch(SQLException sqle)
		{
			conn.rollback();
			throw sqle;
		}
		finally
		{
			conn.setAutoCommit(true);
		}
		
		return counts;
	}

	/**
	 * Set the parameter based on type
	 * @param pstmt
	 * @param parm
	 * @throws SQLException 
	 */
	private static void setSqlParameter(PreparedStatement pstmt, int parameterIndex, Object parm, int sqlType) throws SQLException
	{
		// set sqlnull if the input parm is null
		if(parm == null)
		{
			pstmt.setNull(parameterIndex, sqlType);
			return;
		}
		
		JdbcJavaType elementType = JdbcTypeConverterUtils.sqlTypeToElementType(sqlType);
		if(JdbcJavaType.BOOLEAN == elementType)
		{
			pstmt.setBoolean(parameterIndex, (Boolean)parm);
		}
		else if(JdbcJavaType.DATE == elementType && sqlType == java.sql.Types.TIMESTAMP)
		{
			Timestamp tmp = dateToSqlTimestamp((java.util.Date)parm);
			pstmt.setTimestamp(parameterIndex, tmp);
		}
		else if(JdbcJavaType.DATE == elementType && sqlType == java.sql.Types.TIME)
		{
			Time tmp = dateToSqlTime((java.util.Date)parm);
			pstmt.setTime(parameterIndex, tmp);
		}
		else if(JdbcJavaType.DATE == elementType && sqlType == java.sql.Types.DATE)
		{
			Date tmp = dateToSqlDate((java.util.Date)parm);
			pstmt.setDate(parameterIndex, tmp);
		}
		else if(JdbcJavaType.DOUBLE == elementType)
		{
			pstmt.setDouble(parameterIndex, (Double)parm);
		}
		else if(JdbcJavaType.INTEGER == elementType)
		{
			pstmt.setInt(parameterIndex, (Integer)parm);
		}
		else if(JdbcJavaType.STRING == elementType)
		{
			pstmt.setString(parameterIndex, (String)parm);
		}
		else
		{
			// ELEMENT_TYPE_STRING
			pstmt.setString(parameterIndex, (String)parm);
		}
	}
	
	/**
	 * Turn a ResultSet row into TableRow java object
	 * @param rs
	 * @param model
	 * @return
	 * @throws SQLException 
	 */
	private static TableRow getDataRow(ResultSet rs, TableModel model) throws SQLException
	{
		TableRow row = new TableRow();
		
		for(TableColumnModel column: model.getColumns())
		{
			row.addValue(getSqlCellValue(rs, column.getColumnName(), column.getColumnSqlType()));
		}
		
		return row;
	}
	
	private static Object getSqlCellValue(ResultSet rs, String columnName, int sqlType) throws SQLException
	{
		JdbcJavaType elementType = JdbcTypeConverterUtils.sqlTypeToElementType(sqlType);

		if(JdbcJavaType.BOOLEAN == elementType)
		{
			return rs.getBoolean(columnName);
		}
		else if(JdbcJavaType.DATE == elementType && sqlType == java.sql.Types.TIMESTAMP)
		{
			Timestamp tmp = rs.getTimestamp(columnName);
			java.util.Date date = tmp;
			return date;
		}
		else if(JdbcJavaType.DATE == elementType && sqlType == java.sql.Types.TIME)
		{
			Time tmp = rs.getTime(columnName);
			java.util.Date date = tmp;
			return date;
		}
		else if(JdbcJavaType.DATE == elementType && sqlType == java.sql.Types.DATE)
		{
			Date tmp = rs.getDate(columnName);
			java.util.Date date = tmp;
			return date;
		}
		else if(JdbcJavaType.DOUBLE == elementType)
		{
			return rs.getDouble(columnName);
		}
		else if(JdbcJavaType.INTEGER == elementType)
		{
			return rs.getInt(columnName);
		}
		else if(JdbcJavaType.STRING == elementType)
		{
			return rs.getString(columnName);
		}
		else
		{
			// ELEMENT_TYPE_STRING
			return rs.getString(columnName);
		}
	}
	
	/**
	 * Convert java.util.Date to java.sql.Time
	 * @param date
	 * @return
	 */
	private static java.sql.Time dateToSqlTime(java.util.Date date)
	{
		return new java.sql.Time(date.getTime());
	}
	
	/**
	 * Convert java.util.Date to java.sql.Timestamp
	 * @param date
	 * @return
	 */
	private static java.sql.Timestamp dateToSqlTimestamp(java.util.Date date)
	{
		return new java.sql.Timestamp(date.getTime());
	}
	
	/**
	 * Convert java.util.Date to java.sql.Date
	 * @param date
	 * @return
	 */
	private static java.sql.Date dateToSqlDate(java.util.Date date)
	{
		return new java.sql.Date(date.getTime());
	}
	
}
