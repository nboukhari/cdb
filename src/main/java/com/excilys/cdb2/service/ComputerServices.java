package com.excilys.cdb2.service;

import java.io.IOException;
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
		List<Computer> computers = ComputerDao.getComputerDetails();
		return computers;
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static List<Computer> createComputer() throws IOException, ParseException {
		List<Computer> computers = ComputerDao.setComputer();
		return computers;
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> modifyComputer() throws IOException {
		List<Computer> computers = ComputerDao.updateComputer();
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
}
