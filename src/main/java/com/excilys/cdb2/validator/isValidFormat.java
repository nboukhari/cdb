package com.excilys.cdb2.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class isValidFormat {
	public static boolean Date(String format, String value) {
		LocalDateTime ldt;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format);

		try {
			ldt = LocalDateTime.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} 
		catch (DateTimeParseException e) {
			System.out.println("Mauvais format");
		}
		return false;
	}
}


