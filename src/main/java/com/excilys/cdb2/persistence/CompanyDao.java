package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
//import org.apache.log4j.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.CompanyMapper;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Company;
import com.excilys.cdb2.model.Computer;

/**
 * This class does all the functionnalities about companies
 * @author Nassim BOUKHARI
 */
@Repository
public class CompanyDao {

	private static final String GET_ALL = "SELECT id,name FROM company";
	private static final String GET_ID_COMPANY = "SELECT id FROM company WHERE name =?";
	private static final String DELETE = "DELETE FROM company WHERE id =?";
	//private final static Logger LOGGER = Logger.getLogger(CompanyDao.class);
	
	@Autowired
	private ComputerDao computerDao;
	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public CompanyDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/**
	 * This method displays all the companies
	 * @author Nassim BOUKHARI
	 * @return 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public List<Company> getAllCompanies() throws IOException, ValidationException, ClassNotFoundException {
		List<Company> companies = jdbcTemplate.query(GET_ALL,
									(resultSet, rowNum) -> {
										return retrieveCompanyFromQuery(resultSet);
									});
		return companies;
	}
	
	/**
	 * This method get the Id of the company from his name
	 * @author Nassim BOUKHARI
	 */
	public long getCompanyId(String companyName) {
			String comp;
			Optional<String> newCompany = ComputerMapper.enterCompanyName(companyName);
			Computer computer = ComputerMapper.compName(newCompany);
			if(computer.getCompanyName().isPresent())
				comp = computer.getCompanyName().get().toString();
			else
				comp = "0";
			return (long) jdbcTemplate.queryForObject(GET_ID_COMPANY, new Object[] {comp}, (resultSet, rowNum) -> resultSet.getInt(1));
			
	}
	
	/**
	 * This method deletes a company
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void removeCompany(long id) throws IOException, ValidationException, ClassNotFoundException, SQLException {
			computerDao.removeComputerFromCompany(id);
			jdbcTemplate.update(DELETE, id);
	}
	
	private Company retrieveCompanyFromQuery(ResultSet rs) throws SQLException {
        return CompanyMapper.company(rs.getLong(1), rs.getString(2));
    }

}