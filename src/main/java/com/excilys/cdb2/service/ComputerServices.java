package com.excilys.cdb2.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.persistence.ComputerDao;

/**
 * This class that delivers the requests the to the class ComputerDao
 * @author Nassim BOUKHARI
 */
@Service
public class ComputerServices {
	
	@Autowired
	private ComputerDao computerDao;
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> showComputers(String numberOfPage, String limitData) throws IOException, ValidationException, ClassNotFoundException{
		List<Computer> computers = computerDao.getAllComputers(numberOfPage, limitData);
		return computers;
	}
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> showComputersFromSearch(String search, String numberOfPage, String limitData) throws IOException, ValidationException, ClassNotFoundException{
		List<Computer> computers = computerDao.searchComputers(search, numberOfPage, limitData);
		return computers;
	}
	
	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public Computer showComputerDetail(String id) throws IOException, ValidationException, ClassNotFoundException{
		return computerDao.getComputerDetails(id);
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public void createComputer(String name, String introduced, String discontinued, String companyName) throws IOException, ParseException, ValidationException, ClassNotFoundException {
		computerDao.setComputer(name, introduced, discontinued, companyName);
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public Computer modifyComputer(String id, String name, String introduced, String discontinued, String companyName) throws IOException, ParseException, ValidationException, ClassNotFoundException {
		return computerDao.updateComputer(id, name, introduced, discontinued, companyName);
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public void deleteComputer(List<Long> ids) throws IOException, ValidationException, ClassNotFoundException {	
		computerDao.removeComputer(ids);
	}
	
	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public int getNumberComputers() throws IOException, ValidationException, ClassNotFoundException {	
		int nbComp = computerDao.getComputersCount();
		return nbComp;
	}
	
	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public int getNumberComputersFromSearch(String search) throws IOException, ValidationException, ClassNotFoundException {	
		int nbComp = computerDao.getComputersCountFromSearch(search);
		return nbComp;
	}
}
