package com.excilys.cdb2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

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
	public static List<Computer> showComputers() throws IOException{
		List<Computer> computers = ComputerDao.getAllComputers();
		return computers;
	}
	
	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> showComputerDetail() throws IOException{
		List<Computer> computers = ComputerDao.getComputerDetails(null);
		return computers;
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static List<Computer> createComputer() throws IOException, ParseException {
		List<Computer> computers = ComputerDao.setComputer(null, null, null, null);
		return computers;
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> modifyComputer() throws IOException {
		List<Computer> computers = ComputerDao.updateComputer(null, null, null, null, null);
		return computers;
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> deleteComputer() throws IOException {	
		List<Computer> computers = ComputerDao.removeComputer();
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
