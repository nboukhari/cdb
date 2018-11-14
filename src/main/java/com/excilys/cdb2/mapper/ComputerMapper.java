package com.excilys.cdb2.mapper;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;
import com.excilys.cdb2.validator.isValidFormat;

public class ComputerMapper {

	public static Computer createPC(String name,LocalDate introduced,LocalDate discontinued, String companyId) {
		
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setName(name);
		
		if(introduced != null) {
			computerBuilder.setIntroduced(introduced);
		}
		
		if(discontinued != null) {
			computerBuilder.setDiscontinued(discontinued);
		}
		
		if(companyId != null) {
			int companyIdInt = Integer.valueOf(companyId);
			Company company = new Company();
			company.setId(companyIdInt);
			computerBuilder.setCompany(company);
		}
		
		return computerBuilder.build();
	}

	public static Computer stringToComputer(String id, String name,String introduced,String discontinued, String companyId) throws ParseException {
		
		ComputerBuilder computerBuilder = new ComputerBuilder();
		long newId = Integer.parseInt(id);
		computerBuilder.setId(newId);
		computerBuilder.setName(name);
		
		if(introduced != null) {
			LocalDate ParseDateDebut = enterDate(introduced);
			computerBuilder.setIntroduced(ParseDateDebut);
		}
		
		if(discontinued != null) {
			LocalDate ParseDateEnd = enterDate(discontinued);
			computerBuilder.setDiscontinued(ParseDateEnd);
		}
		
		if(companyId != null) {
			int companyIdInt = Integer.valueOf(companyId);
			Company company = new Company();
			company.setId(companyIdInt);
			computerBuilder.setCompany(company);
		}
		
		return computerBuilder.build();
	}

	public static LocalDate enterDate(String date) throws ParseException {
		if(date != null) {
			LocalDate ParseDate = null;
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			if(isValidFormat.date("yyyy-MM-dd", date)) {
				ParseDate = LocalDate.parse(date, format);
			}

			return ParseDate;
		}
		else {
			return null;
		}
	}
}
