package com.excilys.cdb2.model;

import java.time.LocalDate;
import java.util.Optional;

public class ComputerBuilder {
	private long id;
	private String name;
	private Optional<LocalDate> introduced;
	private Optional<LocalDate> discontinued;
	private Optional<String> companyName;
	
	public ComputerBuilder setId(long id) {
		this.id = id;
		return this;
	}
	public ComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}
	public ComputerBuilder setIntroduced(Optional<LocalDate> introduced) {
		this.introduced = introduced;
		return this;
	}
	public ComputerBuilder setDiscontinued(Optional<LocalDate> discontinued) {
		this.discontinued = discontinued;
		return this;
	}
	
	public ComputerBuilder setCompanyName(Optional<String> companyName) {
		this.companyName = companyName;
		return this;
	}
	
	public Computer build() {
		return new Computer(id,name,introduced,discontinued,companyName);
	}
}
