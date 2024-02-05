package com.springcaf.starter.feature.dataservice.jdbc.model;

import com.springcaf.core.jdbc.annotation.InsertOnly;
import com.springcaf.core.jdbc.annotation.JdbcDataField;
import com.springcaf.core.jdbc.annotation.JdbcDataTable;
import com.springcaf.core.jdbc.annotation.PrimaryKey;
import com.springcaf.core.jdbc.model.KeyType;

/**
 * System generated class used to represent the query as an object. 
 */
@JdbcDataTable(tableName="division")
@PrimaryKey(keyType=KeyType.TABLE_MAX, keyColumns={"division_id"})
public class Division
{
	@JdbcDataField(columnName="division_id", columnSize=10, required=true)
	private Integer divisionId;

	@JdbcDataField(columnName="division_name", columnSize=200, required=true)
	private String divisionName;

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
	public Division()
	{
		// Default constructor
	}

	/**
	 * getter 
	 */
	public Integer getDivisionId()
	{
		return this.divisionId;
	}

	/**
	 * setter 
	 */
	public void setDivisionId(Integer divisionId)
	{
		this.divisionId=divisionId;
	}

	/**
	 * getter 
	 */
	public String getDivisionName()
	{
		return this.divisionName;
	}

	/**
	 * setter 
	 */
	public void setDivisionName(String divisionName)
	{
		this.divisionName=divisionName;
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
		buffer.append("division_id = " + this.getDivisionId() + "\r\n");
		buffer.append("division_name = " + this.getDivisionName() + "\r\n");
		buffer.append("created_at = " + this.getCreatedAt() + "\r\n");
		buffer.append("created_by = " + this.getCreatedBy() + "\r\n");
		buffer.append("updated_at = " + this.getUpdatedAt() + "\r\n");
		buffer.append("updated_by = " + this.getUpdatedBy() + "\r\n");
		
		return buffer.toString();
	}

}
