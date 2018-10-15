package com.excilys.cdb2.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
//import org.apache.log4j.Logger;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.servlet.AddComputer;

public class isValidFormat {

	//private final static Logger LOGGER = Logger.getLogger(isValidFormat.class);

	public static boolean date(String format, String value) {
		LocalDate ldt;
		DateTimeFormatter fomatter = DateTimeFormatter.ofPattern(format);

		try {
			ldt = LocalDate.parse(value, fomatter);
			String result = ldt.format(fomatter);
			return result.equals(value);
		} 
		catch (DateTimeParseException e) {
			if(value.length()>1)
				System.out.println("Mauvais format");
			System.out.println("ce que vous avez entré "+value);
		}
		return false;
	}

	public void test (String introduced, String discontinued) throws ParseException, ValidationException {
		if(!introduced.equals("") && !discontinued.equals("")) {

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf.parse(introduced);
			java.util.Date date2 = sdf.parse(discontinued);

			if(date1.compareTo(date2) > 0) {
				String dateError="ko";
			//	LOGGER.error("La date de début est supérieure à la date de fin.");
				throw new ValidationException("La date de début est supérieure à la date de fin.");


			}
		}

	}
}
