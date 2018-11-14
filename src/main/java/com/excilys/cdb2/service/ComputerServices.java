package com.excilys.cdb2.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Company;
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

	@Autowired
	private CompanyServices companyServices;

	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> showComputers(String numberOfPage, String limitData) {
		long numPage = Pagination.numberOfPage(numberOfPage, limitData);
		long limitPage = Integer.parseInt(limitData);
		List<Computer> computers = computerDao.getAllComputers(limitPage, numPage);
		return computers;
	}

	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> showComputersFromSearch(String search, String numberOfPage, String limitData) {
		long numPage = Pagination.numberOfPage(numberOfPage, limitData);
		long limitPage = Integer.parseInt(limitData);
		List<Computer> computers = computerDao.searchComputers(search, limitPage, numPage);
		return computers;
	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws ValidationException
	 */
	public Computer showComputerDetail(String id) throws ValidationException {
		long idPC = Integer.parseInt(id);
		Computer computer = new Computer();
		computer.setId(idPC);
		return computerDao.getComputerDetails(computer);
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws ParseException 
	 * @throws ValidationException
	 */
	public void createComputer(String name, String introduced, String discontinued, String companyId) throws ParseException, ValidationException {
		LocalDate introducedLD = ComputerMapper.enterDate(introduced);
		LocalDate discontinuedLD = ComputerMapper.enterDate(discontinued);
		Computer computer = ComputerMapper.createPC(name, introducedLD, discontinuedLD,companyId);

		if(companyId != null) {
			Company company = new Company();
			int id = Integer.parseInt(companyId);
			company = companyServices.getCompanyById(id);
			computer.setCompany(company);
		}

		computerDao.setComputer(computer);
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws ValidationException 
	 * @throws ParseException
	 */
	public void modifyComputer(String id, String name, String introduced, String discontinued, String companyId) throws ParseException, ValidationException {
		Computer computer = ComputerMapper.stringToComputer(id, name, introduced, discontinued, companyId);
		
		if(companyId != null) {
			Company company = new Company();
			int idComp = Integer.parseInt(companyId);
			company = companyServices.getCompanyById(idComp);
			computer.setCompany(company);
		}
		System.out.println("test"+computer);
		
		computerDao.updateComputer(computer);
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 */
	public void deleteComputer(List<Long> ids) {	
		computerDao.removeComputer(ids);
	}

	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 */
	public int getNumberComputers() {	
		int nbComp = computerDao.getComputersCount();
		return nbComp;
	}

	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 */
	public int getNumberComputersFromSearch(String search) {	
		int nbComp = computerDao.getComputersCountFromSearch(search);
		return nbComp;
	}
}
