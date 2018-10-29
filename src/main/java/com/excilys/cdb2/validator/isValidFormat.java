package com.excilys.cdb2.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
//import org.apache.log4j.Logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb2.exception.ValidationException;

public class isValidFormat {

	private final static Logger logger = LoggerFactory.getLogger("isValidFormat");

	public static boolean date(String format, String value) {
		LocalDate ldt;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format);

		try {
			ldt = LocalDate.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} 
		catch (DateTimeParseException e) {
			if(value.length()>1) {
				logger.info("Mauvais format");
				logger.info("ce que vous avez entré "+value);
			}
		}
		return false;
	}

	public static String dateSuperior(String introduced, String discontinued) throws ParseException, ValidationException {
		String result = "ok";
		if(!introduced.equals("") && !discontinued.equals("")) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf.parse(introduced);
			java.util.Date date2 = sdf.parse(discontinued);

			if(date1.compareTo(date2) > 0) {
				return result ="ko";		
			}
		}
		return result;
	}
}