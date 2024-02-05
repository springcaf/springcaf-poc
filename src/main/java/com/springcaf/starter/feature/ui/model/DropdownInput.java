package com.springcaf.starter.feature.ui.model;

public class DropdownInput {
	
	private Integer divisionId = null;
	private Integer departmentId = null;
	private Integer brandId = null;
	private Integer quarterId = null;
	private String brandDescription = null;
	
	public String getBrandDescription() {
		return brandDescription;
	}
	public void setBrandDescription(String brandDescription) {
		this.brandDescription = brandDescription;
	}
	public Integer getQuarterId() {
		return quarterId;
	}
	public void setQuarterId(Integer quarterId) {
		this.quarterId = quarterId;
	}
	public Integer getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(Integer divisionId) {
		this.divisionId = divisionId;
	}
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

}