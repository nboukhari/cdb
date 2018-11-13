package com.excilys.cdb2.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.Pagination;
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
		long numPage = Pagination.numberOfPage(numberOfPage, limitData);
		long limitPage = Integer.parseInt(limitData);
		List<Computer> computers = computerDao.getAllComputers(limitPage, numPage);
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
		long numPage = Pagination.numberOfPage(numberOfPage, limitData);
		long limitPage = Integer.parseInt(limitData);
		List<Computer> computers = computerDao.searchComputers(search, limitPage, numPage);
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
		long idPC = Integer.parseInt(id);
		Computer computer = new Computer();
		computer.setId(idPC);
		return computerDao.getComputerDetails(computer);
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public void createComputer(String name, String introduced, String discontinued, String companyId) throws IOException, ParseException, ValidationException, ClassNotFoundException {
		LocalDate introducedLD = ComputerMapper.enterDate(introduced);
		LocalDate discontinuedLD = ComputerMapper.enterDate(discontinued);
		Computer computer = ComputerMapper.StringPC(name, introducedLD, discontinuedLD,companyId);
		computerDao.setComputer(computer);
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public void modifyComputer(String id, String name, String introduced, String discontinued, String companyId) throws IOException, ParseException, ValidationException, ClassNotFoundException {
		Computer computer = ComputerMapper.StringToComputer(id, name, introduced, discontinued, companyId);
		computerDao.updateComputer(computer);
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
