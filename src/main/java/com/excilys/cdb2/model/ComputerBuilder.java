package com.excilys.cdb2.model;

public class ComputerBuilder {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private int companyId;
	
	public ComputerBuilder setId(int id) {
		this.id = id;
		return this;
	}
	public ComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public ComputerBuilder setIntroduced(String introduced) {
		this.introduced = introduced;
		return this;
	}
	public ComputerBuilder setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
		return this;
	}
	public ComputerBuilder setCompanyId(int companyId) {
		this.companyId = companyId;
		return this;
	}
	
	public Computer build() {
		return new Computer(id,name,introduced,discontinued,companyId);
	}
}
