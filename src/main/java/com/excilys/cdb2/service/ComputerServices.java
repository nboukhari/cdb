package com.excilys.cdb2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.persistence.ComputerDao;

/**
 * This class that delivers the requests the to the class ComputerDao
 * @author Nassim BOUKHARI
 */
public class ComputerServices {
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 */
	public static List<Computer> showComputers(String NumberOfPage, String LimitData) throws IOException, ValidationException{
		List<Computer> computers = ComputerDao.getAllComputers(NumberOfPage, LimitData);
		return computers;
	}
	
	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 */
	public static Computer showComputerDetail(String id) throws IOException, ValidationException{
		return ComputerDao.getComputerDetails(id);
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ValidationException 
	 */
	public static void createComputer(String name, String introduced, String discontinued, String companyName) throws IOException, ParseException, ValidationException {
		ComputerDao.setComputer(name, introduced, discontinued, companyName);
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ParseException 
	 */
	public static Computer modifyComputer(String id, String name, String introduced, String discontinued, String companyName) throws IOException, ParseException, ValidationException {
		return ComputerDao.updateComputer(id, name, introduced, discontinued, companyName);
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 */
	public static void deleteComputer() throws IOException, ValidationException {	
		ComputerDao.removeComputer(null);
	}
	
	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 */
	public static int getNumberComputers() throws IOException, ValidationException {	
		int test = ComputerDao.getComputersCount();
		return test;
	}
}
