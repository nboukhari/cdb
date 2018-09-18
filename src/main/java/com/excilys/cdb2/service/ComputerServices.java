package com.excilys.cdb2.service;

import com.excilys.cdb2.persistence.ComputerDao;

/**
 * This class that delivers the requests the to the class ComputerDao
 * @author Nassim BOUKHARI
 */
public class ComputerServices {
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public static void showComputers(){
		ComputerDao.getAllComputers();
	}
	
	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 */
	public static void showComputerDetail(){
		ComputerDao.getComputerDetails();
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 */
	public static void createComputer() {
		ComputerDao.setComputer();
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 */
	public static void modifyComputer() {
		ComputerDao.updateComputer();
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 */
	public static void deleteComputer() {	
		ComputerDao.removeComputer();
	}
}
