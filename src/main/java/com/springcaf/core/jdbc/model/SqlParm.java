package com.springcaf.core.jdbc.model;

public class SqlParm {
	// JDBC parms type
	public static int PARM_TYPE_VARCHAR = 12;
	public static int PARM_TYPE_INTEGER = 4;
	public static int PARM_TYPE_BOOLEAN = 16;
	public static int PARM_TYPE_DOUBLE = 8;
	public static int PARM_TYPE_DATE = java.sql.Types.TIMESTAMP;	// use SQL Timestamp type
	
	private String parmName = null;
	private Object parmData = null;
	private int parmType = PARM_TYPE_VARCHAR;

	/**
	 * Constructor
	 */
	public SqlParm(String parmName, Object parmData)
	{
		this.parmName = parmName;
		this.parmData = parmData;
	}
	
	/**
	 * Constructor
	 * @param parmName
	 * @param parmData
	 * @param parmType
	 */
	public SqlParm(String parmName, Object parmData, int parmType)
	{
		this.parmName = parmName;
		this.parmData = parmData;
		this.parmType = parmType;
	}

	/**
	 * Create a java.sql.Date for the current timestamp
	 * @return
	 */
	public static java.sql.Date getCurrentSystemDate()
	{
		return new java.sql.Date(new java.util.Date().getTime());
	}

	/**
	 * Create a java.sql.Timestamp for the current timestamp
	 * @return
	 */
	public static java.sql.Timestamp getCurrentSystemTimestamp()
	{
		return new java.sql.Timestamp(new java.util.Date().getTime());
	}

	public Object getParmData() {
		return parmData;
	}

	public void setParmData(Object parmData) {
		this.parmData = parmData;
	}

	public int getParmType() {
		return parmType;
	}

	public void setParmType(int parmType) {
		this.parmType = parmType;
	}

	public String getParmName() {
		return parmName;
	}

	public void setParmName(String parmName) {
		this.parmName = parmName;
	}
	
	public String toString()
	{
		return "parmName=" + this.parmName + "\r\n" + "parmType=" + this.parmType + "\r\n" + "parmData=" + this.parmData + "\r\n";
	}

}
