package com.excilys.cdb2.model;

public class CompanyBuilder {
	
	private long id;
	private String name;
	
	public CompanyBuilder setId(long id) {
		this.id = id;
		return this;
	}
	public CompanyBuilder setName(String name) {
		this.name = name;
		return this;
	}

	
	public Company build() {
		return new Company(id,name);
	}
}