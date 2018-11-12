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
	

	public Computer createPC(String name,LocalDate introduced,LocalDate discontinued, Company company) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		LocalDate ParseDateDebut = introduced != null ? introduced : null;
		LocalDate ParseDateEnd = discontinued != null ? discontinued : null;
		if(company != null) {
			
			company.setName(company.getName());
		}
		return computerBuilder.setName(name).setIntroduced(introduced).setDiscontinued(discontinued).setCompany(company).build();
	}

	public static Computer StringPC(String name,LocalDate introduced,LocalDate discontinued, String companyId) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		Computer computer = new Computer();
		Company company = new Company();

		LocalDate ParseDateDebut = introduced != null ? introduced : null;
		LocalDate ParseDateEnd = discontinued != null ? discontinued : null;
		if(!"".equals(companyId)) {
			int companyIdInt = Integer.valueOf(companyId);
			company.setId(companyIdInt);
		}
		return computerBuilder.setName(name).setIntroduced(ParseDateDebut).setDiscontinued(ParseDateEnd).setCompany(company).build();
	}

	public static Computer StringToComputer(String id, String name,String introduced,String discontinued, String companyId) throws ParseException {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		Company company = new Company();
		long newId = Integer.parseInt(id);
		LocalDate ParseDateDebut = introduced != null ? enterDate(introduced) : null;
		LocalDate ParseDateEnd = discontinued != null ? enterDate(discontinued) : null;
		if(!"".equals(companyId)) {
			int companyIdInt = Integer.valueOf(companyId);
			company.setId(companyIdInt);
		}
		return computerBuilder.setId(newId).setName(name).setIntroduced(ParseDateDebut).setDiscontinued(ParseDateEnd).setCompany(company).build();
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

	public static String enterCompanyName(String name) {
		Company company = new Company();
		if(name != null) {
			company.setName(name);
			return company.getName();
		}
		else {
			return null;
		}
	}

	public Computer compName (Company companyName) {

		if(companyName != null) {
			Computer computer;
			ComputerBuilder computerBuilder = new ComputerBuilder();
			computerBuilder.setCompany(companyName);
			computer = computerBuilder.build();
			return computer;
		}
		else {
			return null;
		}

	}

	public static int numberOfPage(String page, String limit) {
		System.out.println("page"+page+"  limit"+limit);
		int nbP = Integer.parseInt(page);
		int limitNumber = Integer.parseInt(limit);
		nbP = (nbP-1)*limitNumber;
		return nbP;
	}
}
