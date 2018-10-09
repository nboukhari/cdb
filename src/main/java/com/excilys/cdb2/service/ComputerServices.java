package com.excilys.cdb2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

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
	 */
	public static List<Computer> showComputers(String NumberOfPage, String LimitData) throws IOException{
		List<Computer> computers = ComputerDao.getAllComputers(NumberOfPage, LimitData);
		return computers;
	}
	
	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static Computer showComputerDetail(String id) throws IOException{
		return ComputerDao.getComputerDetails(id);
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void createComputer(String name, String introduced, String discontinued, String companyName) throws IOException, ParseException {
		ComputerDao.setComputer(name, introduced, discontinued, companyName);
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static Computer modifyComputer(String id, String name, String introduced, String discontinued, String companyName) throws IOException {
		return ComputerDao.updateComputer(id, name, introduced, discontinued, companyName);
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> deleteComputer(String id) throws IOException {	
		List<Computer> computers = ComputerDao.removeComputer(id);
		return computers;
	}
	
	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public static int getNumberComputers() throws IOException, SQLException {	
		int test = ComputerDao.getComputersCount();
		return test;
	}
}
