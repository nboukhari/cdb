package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import org.apache.log4j.Logger;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;

/**
 * This class does all the functionnalities about computers
 * @author Nassim BOUKHARI
 */
@Repository
public class ComputerDao {

	private static final String GET_ALL = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id LIMIT ?,?";
	private static final String SEARCH = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.name LIKE ? OR company.name LIKE ? LIMIT ?,?";
	private static final String GET_ONE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id =?;";
	private static final String DELETE = "DELETE FROM computer WHERE id = ?;";
	private static final String DELETE_COMPUTERS_COMPANY = "DELETE FROM computer WHERE company_id =?";
	private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer LEFT JOIN company on company.id = computer.company_id WHERE computer.name LIKE ? OR company.name LIKE ?";
	private static final String COUNT = "SELECT COUNT(*) FROM computer";
	//private final static Logger LOGGER = Logger.getLogger(ComputerDao.class);
	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public ComputerDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> getAllComputers(String NumberOfPage, String LimitData){


			int numPage = ComputerMapper.numberOfPage(NumberOfPage, LimitData);
			long limitPage = Integer.parseInt(LimitData);
			
			List<Computer> computers = jdbcTemplate.query(GET_ALL,
	                preparedStatement -> {
	                    preparedStatement.setInt(1, numPage);
	                    preparedStatement.setLong(2, limitPage);
	                }, (resultSet, rowNum) -> {
	                    return retrieveComputerFromQuery(resultSet);
	                });
		return computers;

	}
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public List<Computer> searchComputers(String search, String numberOfPage, String limitData){


			int numPage = ComputerMapper.numberOfPage(numberOfPage, limitData);
			long limitPage = Integer.parseInt(limitData);
			List<Computer> computers = jdbcTemplate.query(SEARCH,
	                preparedStatement -> {
	                	preparedStatement.setString(1, "%"+search+"%");
	                	preparedStatement.setString(2, "%"+search+"%");
	                    preparedStatement.setInt(3, numPage);
	                    preparedStatement.setLong(4, limitPage);
	                }, (resultSet, rowNum) -> {
	                    return retrieveComputerFromQuery(resultSet);
	                });
		return computers;

	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 */
	public Computer getComputerDetails(String idPC){
		
		long id = Integer.parseInt(idPC);
		return jdbcTemplate.queryForObject(GET_ONE, new Object[] {id}, (resultSet, rowNum) -> retrieveComputerFromQuery(resultSet));
		
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public void setComputer(String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException, ParseException, ValidationException, ClassNotFoundException {

			String introducedOptional;
			String discontinuedOptional;
			String companyNameOptional;
			Optional<String> companyName = ComputerMapper.enterCompanyName(companyNameStr);
			Optional<LocalDate> introducedPC = ComputerMapper.enterDate(introducedStr);
			Optional<LocalDate> discontinuedPC = ComputerMapper.enterDate(discontinuedStr);
			Computer computer = ComputerMapper.createPC(namePC, introducedPC, discontinuedPC,companyName);
			if(computer.getIntroduced().isPresent())
				introducedOptional = computer.getIntroduced().get().toString();
			else
				introducedOptional = null;
			if(computer.getDiscontinued().isPresent())
				discontinuedOptional = computer.getDiscontinued().get().toString();
			else
				discontinuedOptional = null;
			if(computer.getCompanyName().isPresent())
				companyNameOptional = computer.getCompanyName().get().toString();
			else
				companyNameOptional = null;
			
			jdbcTemplate.update(INSERT, 
								computer.getName(),
								introducedOptional,
								discontinuedOptional,
								companyNameOptional);
	
	}


	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public void updateComputer(String idPC, String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws ParseException {


			long newIdPC = Integer.parseInt(idPC);
			String introducedOptional;
			String discontinuedOptional;
			String companyNameOptional;
			Optional<LocalDate> newDateDebut = ComputerMapper.enterDate(introducedStr);
			Optional<LocalDate> newDateEnd = ComputerMapper.enterDate(discontinuedStr);
			Optional<String> newCompany = ComputerMapper.enterCompanyName(companyNameStr);
			Computer computer = new Computer(newIdPC, namePC, newDateDebut, newDateEnd,newCompany);

			if(computer.getIntroduced().isPresent())
				introducedOptional = computer.getIntroduced().get().toString();
			else
				introducedOptional = null;
			if(computer.getDiscontinued().isPresent())
				discontinuedOptional = computer.getDiscontinued().get().toString();
			else
				discontinuedOptional = null;
			if(computer.getCompanyName().isPresent())
				companyNameOptional = computer.getCompanyName().get().toString();
			else
				companyNameOptional = null;
			
			jdbcTemplate.update(UPDATE, 
								computer.getName(),
								introducedOptional,
								discontinuedOptional,
								companyNameOptional,
								newIdPC);
	
	}


	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 */
	public void removeComputer(List<Long> ids) {

		for (Long id : ids) {
			jdbcTemplate.update(DELETE, id);
		}
	}
	
	/**
	 * This method deletes a computer that is in a company
	 * @author Nassim BOUKHARI
	 */
	public void removeComputerFromCompany(long idC) {
		
		jdbcTemplate.update(DELETE_COMPUTERS_COMPANY);
	}
	

	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCount() {

		return jdbcTemplate.query(COUNT, (resultSet, rowNum) -> resultSet.getInt(1)).get(0);
	}
	
	/**
	 * This method displays computers from the search
	 * @author Nassim BOUKHARI
	 */
	public int getComputersCountFromSearch(String search){

		return jdbcTemplate.queryForObject(SEARCH_COUNT, new Object[] {new StringBuilder("%").append(search).append("%").toString(), new StringBuilder("%").append(search).append("%").toString()}, 
				(resultSet, rowNum) -> resultSet.getInt(1));
	}
	
	private Computer retrieveComputerFromQuery(ResultSet rs) throws SQLException {
        return ComputerMapper.updatePC(rs.getLong(1), rs.getString(2), rs.getDate(3), rs.getDate(4), rs.getString(5));
    }
}