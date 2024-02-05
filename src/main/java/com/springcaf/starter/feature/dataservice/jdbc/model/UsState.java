package com.springcaf.starter.feature.dataservice.jdbc.model;

import com.springcaf.core.jdbc.annotation.InsertOnly;
import com.springcaf.core.jdbc.annotation.JdbcDataField;
import com.springcaf.core.jdbc.annotation.JdbcDataTable;
import com.springcaf.core.jdbc.annotation.PrimaryKey;
import com.springcaf.core.jdbc.model.KeyType;

/**
 * System generated class used to represent the query as an object. 
 */
@JdbcDataTable(tableName="us_state")
@PrimaryKey(keyType=KeyType.TABLE_MAX, keyColumns={"state_code"})
public class UsState
{
	@JdbcDataField(columnName="state_code", columnSize=2, required=true)
	private String stateCode;

	@JdbcDataField(columnName="state_name", columnSize=200, required=true)
	private String stateName;

	@JdbcDataField(columnName="created_at", required=true)
	@InsertOnly
	private java.util.Date createdAt;

	@JdbcDataField(columnName="created_by", columnSize=36)
	@InsertOnly
	private String createdBy;

	@JdbcDataField(columnName="updated_at")
	private java.util.Date updatedAt;

	@JdbcDataField(columnName="updated_by", columnSize=36)
	private String updatedBy;

	/**
	 * Constructor
	 */
	public UsState()
	{
		// Default constructor
	}

	/**
	 * getter 
	 */
	public String getStateCode()
	{
		return this.stateCode;
	}

	/**
	 * setter 
	 */
	public void setStateCode(String stateCode)
	{
		this.stateCode=stateCode;
	}

	/**
	 * getter 
	 */
	public String getStateName()
	{
		return this.stateName;
	}

	/**
	 * setter 
	 */
	public void setStateName(String stateName)
	{
		this.stateName=stateName;
	}

	/**
	 * getter 
	 */
	public java.util.Date getCreatedAt()
	{
		return this.createdAt;
	}

	/**
	 * setter 
	 */
	public void setCreatedAt(java.util.Date createdAt)
	{
		this.createdAt=createdAt;
	}

	/**
	 * getter 
	 */
	public String getCreatedBy()
	{
		return this.createdBy;
	}

	/**
	 * setter 
	 */
	public void setCreatedBy(String createdBy)
	{
		this.createdBy=createdBy;
	}

	/**
	 * getter 
	 */
	public java.util.Date getUpdatedAt()
	{
		return this.updatedAt;
	}

	/**
	 * setter 
	 */
	public void setUpdatedAt(java.util.Date updatedAt)
	{
		this.updatedAt=updatedAt;
	}

	/**
	 * getter 
	 */
	public String getUpdatedBy()
	{
		return this.updatedBy;
	}

	/**
	 * setter 
	 */
	public void setUpdatedBy(String updatedBy)
	{
		this.updatedBy=updatedBy;
	}

	/**
	 * toString method 
	 */
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append("state_code = " + this.getStateCode() + "\r\n");
		buffer.append("state_name = " + this.getStateName() + "\r\n");
		buffer.append("created_at = " + this.getCreatedAt() + "\r\n");
		buffer.append("created_by = " + this.getCreatedBy() + "\r\n");
		buffer.append("updated_at = " + this.getUpdatedAt() + "\r\n");
		buffer.append("updated_by = " + this.getUpdatedBy() + "\r\n");
		
		return buffer.toString();
	}

}
