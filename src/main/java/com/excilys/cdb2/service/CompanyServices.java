package com.excilys.cdb2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.persistence.CompanyDao;

/**
 * This class that delivers the requests the to the class CompanyDao
 * @author Nassim BOUKHARI
 */
public class CompanyServices {
	
	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static List<Company> showCompanies() throws IOException, ValidationException, ClassNotFoundException{
		List<Company> companies = CompanyDao.getAllCompanies();
		return companies;
	}
	
	public static void deleteCompany(long id) throws ClassNotFoundException, IOException, ValidationException, SQLException {
		CompanyDao.removeCompany(id);
	}
}