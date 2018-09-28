package com.excilys.cdb2.mapper;

import java.util.Optional;

import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;

public class CompanyMapper {
public static Computer CompName (Optional<String> companyName) {
		
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setCompanyName(companyName);
		computer = computerBuilder.build();
		return computer;
		
	}
}
