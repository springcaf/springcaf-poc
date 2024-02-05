package com.springcaf.starter.feature.dataservice.jdbc.model;

import com.springcaf.core.jdbc.annotation.InsertOnly;
import com.springcaf.core.jdbc.annotation.JdbcDataField;
import com.springcaf.core.jdbc.annotation.JdbcDataTable;
import com.springcaf.core.jdbc.annotation.PrimaryKey;
import com.springcaf.core.jdbc.model.KeyType;

/**
 * System generated class used to represent the query as an object. 
 */
@JdbcDataTable(tableName="person")
@PrimaryKey(keyType=KeyType.AUTO_INCREMENT, keyColumns={"person_id"})
public class Person
{
	@JdbcDataField(columnName="person_id", columnSize=10, required=true)
	private Integer personId;

	@JdbcDataField(columnName="first_name", columnSize=200, required=true)
	private String firstName;

	@JdbcDataField(columnName="middle_name", columnSize=200)
	private String middleName;

	@JdbcDataField(columnName="last_name", columnSize=200, required=true)
	private String lastName;

	@JdbcDataField(columnName="home_state", columnSize=2)
	private String homeState;

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
	public Person()
	{
		// Default constructor
	}

	/**
	 * getter 
	 */
	public Integer getPersonId()
	{
		return this.personId;
	}

	/**
	 * setter 
	 */
	public void setPersonId(Integer personId)
	{
		this.personId=personId;
	}

	/**
	 * getter 
	 */
	public String getFirstName()
	{
		return this.firstName;
	}

	/**
	 * setter 
	 */
	public void setFirstName(String firstName)
	{
		this.firstName=firstName;
	}

	/**
	 * getter 
	 */
	public String getMiddleName()
	{
		return this.middleName;
	}

	/**
	 * setter 
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName=middleName;
	}

	/**
	 * getter 
	 */
	public String getLastName()
	{
		return this.lastName;
	}

	/**
	 * setter 
	 */
	public void setLastName(String lastName)
	{
		this.lastName=lastName;
	}

	/**
	 * getter 
	 */
	public String getHomeState()
	{
		return this.homeState;
	}

	/**
	 * setter 
	 */
	public void setHomeState(String homeState)
	{
		this.homeState=homeState;
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
		buffer.append("person_id = " + this.getPersonId() + "\r\n");
		buffer.append("first_name = " + this.getFirstName() + "\r\n");
		buffer.append("middle_name = " + this.getMiddleName() + "\r\n");
		buffer.append("last_name = " + this.getLastName() + "\r\n");
		buffer.append("home_state = " + this.getHomeState() + "\r\n");
		buffer.append("created_at = " + this.getCreatedAt() + "\r\n");
		buffer.append("created_by = " + this.getCreatedBy() + "\r\n");
		buffer.append("updated_at = " + this.getUpdatedAt() + "\r\n");
		buffer.append("updated_by = " + this.getUpdatedBy() + "\r\n");
		
		return buffer.toString();
	}

}
