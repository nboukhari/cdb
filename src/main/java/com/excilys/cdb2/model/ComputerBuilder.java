package com.excilys.cdb2.model;

import java.time.LocalDate;

public class ComputerBuilder {
	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	public ComputerBuilder setId(long id) {
		this.id = id;
		return this;
	}
	public ComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public ComputerBuilder setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
		return this;
	}
	public ComputerBuilder setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
		return this;
	}
	
	public ComputerBuilder setCompany(Company company) {
		this.company = company;
		return this;
	}
	
	public Computer build() {
		return new Computer(id,name,introduced,discontinued,company);
	}
}
