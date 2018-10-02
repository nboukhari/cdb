package com.excilys.cdb2.mapper;

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

	public static long enterId(String idPC) {

		long intId = Integer.parseInt(idPC);
		return intId;

	}
	
	public static int stringToInt(String stringData) {
		int intData = Integer.parseInt(stringData);
		return intData;
	}

	public static Optional<LocalDate> enterDate(String date) throws ParseException {

		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Optional<LocalDate> dateToLocalDate = Optional.empty();
		if(isValidFormat.Date("yyyy-MM-dd", date)) {
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
	
	public static int numberOfPage(String Page) {
		int NbP = Integer.parseInt(Page);
		NbP = (NbP-1)*10;
		return NbP;
	}
}
