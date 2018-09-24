package com.excilys.cdb2.model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {

	private long id;
	private String name;
	private Optional<LocalDate> introduced;
	private Optional<LocalDate> discontinued;
	private long companyId;
	
	public Computer(long id, String name, Optional<LocalDate> introduced, Optional<LocalDate> discontinued, long companyId){
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Optional<LocalDate> getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Optional<LocalDate> introduced) {
		this.introduced = introduced;
	}
	public Optional<LocalDate> getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Optional<LocalDate> discontinued) {
		this.discontinued = discontinued;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + "]";
	}

}