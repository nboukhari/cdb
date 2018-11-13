package com.excilys.cdb2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.persistence.CompanyDao;

/**
 * This class that delivers the requests the to the class CompanyDao
 * @author Nassim BOUKHARI
 */
@Service
public class CompanyServices {
	
	@Autowired
	private CompanyDao companyDao;
	
	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public List<Company> showCompanies() throws IOException, ValidationException, ClassNotFoundException{
		List<Company> companies = companyDao.getAllCompanies();
		return companies;
	}
	
	@Transactional
	public void deleteCompany(long id) throws ClassNotFoundException, IOException, ValidationException, SQLException {
		companyDao.removeCompany(id);
	}
}