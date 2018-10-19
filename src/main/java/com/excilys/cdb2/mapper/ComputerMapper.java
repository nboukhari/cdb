package com.excilys.cdb2.mapper;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;
import com.excilys.cdb2.validator.isValidFormat;

public class ComputerMapper {

	public static Computer enterIdPC(String idPC) {

		int intId = Integer.parseInt(idPC);
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(intId);
		computer = computerBuilder.build();

		return computer;
	}

	public static Optional<LocalDate> enterDate(String date) throws ParseException {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Optional<LocalDate> dateToLocalDate = Optional.empty();
		if(isValidFormat.date("yyyy-MM-dd", date)) {
			LocalDate ParseDate = LocalDate.parse(date, format);
			dateToLocalDate = Optional.ofNullable(ParseDate);
		}

		return dateToLocalDate;
	}

	public static Optional<String> enterCompanyName(String name) {
		Optional<String> companyName = Optional.empty();
		companyName = Optional.ofNullable(name);
		return companyName;
	}

	public static Computer createPC(String name,Optional<LocalDate> introduced,Optional<LocalDate> discontinued, Optional<String> companyName) {

		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introduced);
		computerBuilder.setDiscontinued(discontinued);
		computerBuilder.setCompanyName(companyName);
		computer = computerBuilder.build();

		return computer;
	}
	
	public static Computer updatePC(long id, String name,Date introduced,Date discontinued, String companyName) {
		
		Optional<LocalDate> introducedOptional = Optional.empty();
		Optional<LocalDate> discontinuedOptional = Optional.empty();
		Optional<String> companyNameOptional = Optional.empty();
		LocalDate ParseDateDebut = introduced != null ? introduced.toLocalDate() : null;
		introducedOptional = Optional.ofNullable(ParseDateDebut);

		LocalDate ParseDateEnd = discontinued != null ? discontinued.toLocalDate() : null;
		discontinuedOptional = Optional.ofNullable(ParseDateEnd);
		companyNameOptional = Optional.ofNullable(companyName);

		
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(id);
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introducedOptional);
		computerBuilder.setDiscontinued(discontinuedOptional);
		computerBuilder.setCompanyName(companyNameOptional);
		computer = computerBuilder.build();

		return computer;
	}
	
public static Computer compName (Optional<String> companyName) {
		
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setCompanyName(companyName);
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
