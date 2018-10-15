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
	 * @throws ClassNotFoundException 
	 */
	public static List<Computer> showComputers(String numberOfPage, String limitData) throws IOException, ValidationException, ClassNotFoundException{
		List<Computer> computers = ComputerDao.getAllComputers(numberOfPage, limitData);
		return computers;
	}
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static List<Computer> showComputersFromSearch(String search, String numberOfPage, String limitData) throws IOException, ValidationException, ClassNotFoundException{
		List<Computer> computers = ComputerDao.searchComputers(search, numberOfPage, limitData);
		return computers;
	}
	
	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static Computer showComputerDetail(String id) throws IOException, ValidationException, ClassNotFoundException{
		return ComputerDao.getComputerDetails(id);
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static void createComputer(String name, String introduced, String discontinued, String companyName) throws IOException, ParseException, ValidationException, ClassNotFoundException {
		ComputerDao.setComputer(name, introduced, discontinued, companyName);
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public static Computer modifyComputer(String id, String name, String introduced, String discontinued, String companyName) throws IOException, ParseException, ValidationException, ClassNotFoundException {
		return ComputerDao.updateComputer(id, name, introduced, discontinued, companyName);
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static void deleteComputer() throws IOException, ValidationException, ClassNotFoundException {	
		ComputerDao.removeComputer(null);
	}
	
	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static int getNumberComputers() throws IOException, ValidationException, ClassNotFoundException {	
		int test = ComputerDao.getComputersCount();
		return test;
	}
	
	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static int getNumberComputersFromSearch(String search) throws IOException, ValidationException, ClassNotFoundException {	
		int test = ComputerDao.getComputersCountFromSearch(search);
		return test;
	}
}
