package com.springcaf.core.jdbc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.springcaf.core.util.StringUtils;

public class SqlParmSet {
	
	private List<SqlParm> sqlParms = new ArrayList<SqlParm>();
	
	/**
	 * Constructor
	 */
	public SqlParmSet()
	{
		// default
	}
	
	/**
	 * Add a new SqlParm
	 * @param sqlParm
	 * @return
	 */
	public SqlParmSet addSqlParm(SqlParm sqlParm)
	{
		this.sqlParms.add(sqlParm);
		
		return this;
	}

	/**
	 * Add a new SqlParm
	 * @param parmName
	 * @param parmData
	 * @param parmType
	 * @return
	 */
	public SqlParmSet addSqlParm(String parmName, Object parmData, int parmType)
	{
		this.sqlParms.add(new SqlParm(parmName, parmData, parmType));
		
		return this;
	}

	/**
	 * Add a generic type of SqlParm
	 * @param parmName
	 * @param parmData
	 * @return
	 */
	public SqlParmSet addSqlParm(String parmName, Object parmData)
	{
		if(parmData instanceof String)
		{
			return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_VARCHAR);
		}
		else if(parmData instanceof Date)
		{
			return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_DATE);
		}
		else if(parmData instanceof Integer)
		{
			return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_INTEGER);
		}
		else if(parmData instanceof Boolean)
		{
			return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_BOOLEAN);
		}
		else if(parmData instanceof Double)
		{
			return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_DOUBLE);
		}
		else
		{
			return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_VARCHAR);
		}
		
	}
	
	/**
	 * Add a specific type of SqlParm
	 * @param parmName
	 * @param parmData
	 * @return
	 */
	public SqlParmSet addVarcharSqlParm(String parmName, String parmData)
	{
		return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_VARCHAR);
	}
	
	/**
	 * Add a specific type of SqlParm
	 * @param parmName
	 * @param parmData
	 * @return
	 */
	public SqlParmSet addIntegerSqlParm(String parmName, Integer parmData)
	{
		return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_INTEGER);
	}
	
	/**
	 * Add a specific type of SqlParm
	 * @param parmName
	 * @param parmData
	 * @return
	 */
	public SqlParmSet addBooleanSqlParm(String parmName, Boolean parmData)
	{
		return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_BOOLEAN);
	}
	
	/**
	 * Add a specific type of SqlParm
	 * @param parmName
	 * @param parmData
	 * @return
	 */
	public SqlParmSet addDoubleSqlParm(String parmName, Double parmData)
	{
		return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_DOUBLE);
	}
	
	/**
	 * Add a specific type of SqlParm
	 * @param parmName
	 * @param parmData
	 * @return
	 */
	public SqlParmSet addDateSqlParm(String parmName, Date parmData)
	{
		return this.addSqlParm(parmName, parmData, SqlParm.PARM_TYPE_DATE);
	}
	
	public List<SqlParm> getSqlParms() {
		return sqlParms;
	}

	public void setSqlParms(List<SqlParm> sqlParms) {
		this.sqlParms = sqlParms;
	}

	/**
	 * Get parmData for a given parmName
	 * @param parmName
	 * @return
	 */
	public Object getParmData(String parmName)
	{
		for(SqlParm parm : this.getSqlParms())
		{
			if(parm.getParmName().equalsIgnoreCase(parmName))
			{
				return parm.getParmData();
			}
		}
		
		return null;
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		
		for(SqlParm parm: this.getSqlParms())
		{
			buffer.append(parm.getParmName() + " = " + parm.getParmData() + "\r\n");
		}
		
		return buffer.toString();
	}
	
	/**
	 * Check if the sql parm contains any parms
	 * @return
	 */
	public boolean containsParms()
	{
		return this.sqlParms.size() > 0;
	}
	
	/**
	 * Turn the sqlparmset into a where clause with ?
	 * @return
	 */
	public String toWhereClauseSegment()
	{
		StringBuffer buffer = new StringBuffer();
		for(SqlParm parm : this.getSqlParms())
		{
			buffer.append(" " + parm.getParmName() + " = ? AND");
		}
		
		return StringUtils.trimLastStringBlock(buffer.toString(), "AND");
	}
	
	/**
	 * Insert only parms
	 * @return
	 */
/*	public List<SqlParm> getInsertOnlySqlParms()
	{
		List<SqlParm> list = new ArrayList<SqlParm>();
		
		for(SqlParm parm : this.getSqlParms())
		{
			if(parm.isInsertUseOnly())
			{
				list.add(parm);
			}
		}
		
		return list;
	}*/
	
	/**
	 * Update only parms
	 * @return
	 */
/*	public List<SqlParm> getUpdateOnlySqlParms()
	{
		List<SqlParm> list = new ArrayList<SqlParm>();
		
		for(SqlParm parm : this.getSqlParms())
		{
			if(parm.isUpdateUseOnly())
			{
				list.add(parm);
			}
		}
		
		return list;
	}*/

	/**
	 * Check if the parm set is empty
	 * @return
	 */
	public boolean isEmpty()
	{
		return this.getSqlParms().size() == 0;
	}
}
