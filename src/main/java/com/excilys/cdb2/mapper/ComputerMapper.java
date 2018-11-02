package com.excilys.cdb2.mapper;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;
import com.excilys.cdb2.validator.isValidFormat;

public class ComputerMapper {

	private static Company company;
	private static ComputerBuilder computerBuilder;
	
	public Computer createPC(String name,LocalDate introduced,LocalDate discontinued, Company company) {

		return computerBuilder.setName(name).setIntroduced(introduced).setDiscontinued(discontinued).setCompany(company).build();
	}
	
	public Computer updatePC(long id, String name,Date introduced,Date discontinued, String companyName) {
		
		LocalDate ParseDateDebut = introduced != null ? introduced.toLocalDate() : null;
		LocalDate ParseDateEnd = discontinued != null ? discontinued.toLocalDate() : null;
		company.setName(companyName);

		return computerBuilder.setName(name).setIntroduced(ParseDateDebut).setDiscontinued(ParseDateEnd).setCompany(company).build();
	}
	
	public Computer enterIdPC(String idPC) {

		int intId = Integer.parseInt(idPC);
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(intId);
		computer = computerBuilder.build();

		return computer;
	}

	public static LocalDate enterDate(String date) throws ParseException {
		LocalDate ParseDate = null;
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if(isValidFormat.date("yyyy-MM-dd", date)) {
			ParseDate = LocalDate.parse(date, format);
		}

		return ParseDate;
	}

	public String enterCompanyName(String name) {
	
		company.setName(name);
		companyName = company.setName(name);
		return companyName;
	
	}
	
	public Computer compName (Company companyName) {
		
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setCompany(companyName);
		computer = computerBuilder.build();
		return computer;
		
	}
	
	public static int numberOfPage(String page, String limit) {
		int nbP = Integer.parseInt(page);
		int limitNumber = Integer.parseInt(limit);
		nbP = (nbP-1)*limitNumber;
		return nbP;
	}
}
