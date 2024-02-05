package com.springcaf.starter.feature.dataservice.jdbc.model;

import com.springcaf.core.jdbc.annotation.InsertOnly;
import com.springcaf.core.jdbc.annotation.JdbcDataField;
import com.springcaf.core.jdbc.annotation.JdbcDataTable;
import com.springcaf.core.jdbc.annotation.PrimaryKey;
import com.springcaf.core.jdbc.model.KeyType;

/**
 * System generated class used to represent the query as an object. 
 */
@JdbcDataTable(tableName="department")
@PrimaryKey(keyType=KeyType.TABLE_MAX, keyColumns={"department_id"})
public class Department
{
	@JdbcDataField(columnName="department_id", columnSize=10, required=true)
	private Integer departmentId;

	@JdbcDataField(columnName="department_name", columnSize=200, required=true)
	private String departmentName;

	@JdbcDataField(columnName="division_id", columnSize=10, required=true)
	private Integer divisionId;

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
	public Department()
	{
		// Default constructor
	}

	/**
	 * getter 
	 */
	public Integer getDepartmentId()
	{
		return this.departmentId;
	}

	/**
	 * setter 
	 */
	public void setDepartmentId(Integer departmentId)
	{
		this.departmentId=departmentId;
	}

	/**
	 * getter 
	 */
	public String getDepartmentName()
	{
		return this.departmentName;
	}

	/**
	 * setter 
	 */
	public void setDepartmentName(String departmentName)
	{
		this.departmentName=departmentName;
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
		buffer.append("department_id = " + this.getDepartmentId() + "\r\n");
		buffer.append("department_name = " + this.getDepartmentName() + "\r\n");
		buffer.append("division_id = " + this.getDivisionId() + "\r\n");
		buffer.append("created_at = " + this.getCreatedAt() + "\r\n");
		buffer.append("created_by = " + this.getCreatedBy() + "\r\n");
		buffer.append("updated_at = " + this.getUpdatedAt() + "\r\n");
		buffer.append("updated_by = " + this.getUpdatedBy() + "\r\n");
		
		return buffer.toString();
	}

}
