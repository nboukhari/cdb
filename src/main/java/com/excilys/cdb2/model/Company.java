package com.excilys.cdb2.model;

public class Company {
	private long id;
	private String name;

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}