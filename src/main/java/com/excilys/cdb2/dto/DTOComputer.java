package com.excilys.cdb2.dto;

public class DTOComputer {
	private String id="0";
	private String name;
	private String introduced;  
	private String discontinued; 
	private String companyID;
	private String companyName;
	
	public DTOComputer(String name, String introduced, String discontinued, String companyID, String companyName) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
		this.companyName = companyName;
	}
	public DTOComputer(String id, String name, String introduced, String discontinued, String companyID,
			String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyID = companyID;
		this.companyName = companyName;
	}
	public DTOComputer() {
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntroduced() {
		return introduced;
	}
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	public String getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	public String getCompanyID() {
		return companyID;
	}
	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	@Override
	public String toString() {
		return "DTOComputer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyID=" + companyID + ", companyName=" + companyName + "]";
	}
}
