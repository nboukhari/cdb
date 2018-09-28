package com.excilys.cdb2.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class isValidFormat {
	
	public static boolean Date(String format, String value) {
		LocalDate ldt;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format);

		try {
			ldt = LocalDate.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} 
		catch (DateTimeParseException e) {
			System.out.println("Mauvais format");
			if(value.length()>1)
			System.out.println("ce que vous avez entré "+value);
		}
		return false;
	}
	
}


