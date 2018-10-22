package com.excilys.cdb2.mapper;

import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.CompanyBuilder;

public class CompanyMapper {

	public static Company company(long id, String name) {
		
		CompanyBuilder companyBuilder = new CompanyBuilder().setId(id).setName(name);
		Company company = companyBuilder.build();

		return company;
	}
}
