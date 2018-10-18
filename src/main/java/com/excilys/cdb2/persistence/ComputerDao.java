package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

//import org.apache.log4j.Logger;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;

/**
 * This class does all the functionnalities about computers
 * @author Nassim BOUKHARI
 */
@Repository
public class ComputerDao {

	private static final String GET_ALL = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id LIMIT ?,?";
	private static final String SEARCH = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.name LIKE ? LIMIT ?,?";
	private static final String GET_ONE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id =?;";
	private static final String DELETE = "DELETE FROM computer WHERE id = ?;";
	private static final String DELETE_COMPUTERS_COMPANY = "DELETE FROM computer WHERE company_id =?";
	private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
	private static final String COUNT = "SELECT COUNT(*) FROM computer";
	//private final static Logger LOGGER = Logger.getLogger(ComputerDao.class);
	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public ComputerDao(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	
	@Autowired
	private ConnectionDAO connectionDAO = new ConnectionDAO();
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> getAllComputers(String NumberOfPage, String LimitData) throws IOException, ValidationException, ClassNotFoundException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		try (Connection cn = connectionDAO.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(GET_ALL);
			int numPage = ComputerMapper.numberOfPage(NumberOfPage, LimitData);
			long limitPage = Integer.parseInt(LimitData);
			ppdStmt.setInt(1, numPage);
			ppdStmt.setLong(2, limitPage);
			ResultSet rs = ppdStmt.executeQuery();
			while(rs.next()) {
				Optional<LocalDate> introduced = Optional.empty();
				Optional<LocalDate> discontinued = Optional.empty();
				Optional<String> companyName = Optional.empty();
				long pcId = rs.getLong("computer.id");
				String name = rs.getString("computer.name");
				Date dateDebut = rs.getDate("computer.introduced");
				LocalDate ParseDateDebut = dateDebut != null ? dateDebut.toLocalDate() : null;
				introduced = Optional.ofNullable(ParseDateDebut);

				Date dateEnd = rs.getDate("computer.discontinued");
				LocalDate ParseDateEnd = dateEnd != null ? dateEnd.toLocalDate() : null;
				discontinued = Optional.ofNullable(ParseDateEnd);
				String strCompName = rs.getString("company.name");
				companyName = Optional.ofNullable(strCompName);

				computerBuilder.setId(pcId);
				computerBuilder.setName(name);
				computerBuilder.setIntroduced(introduced);
				computerBuilder.setDiscontinued(discontinued);
				computerBuilder.setCompanyName(companyName);
				computer = computerBuilder.build();
				computers.add(computer);
				
			}
			rs.close();

		} catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de l'affichage des ordinateurs.");
		}
		return computers;

	}
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public List<Computer> searchComputers(String search, String numberOfPage, String limitData) throws IOException, ValidationException, ClassNotFoundException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = connectionDAO.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(SEARCH);
			int numPage = ComputerMapper.numberOfPage(numberOfPage, limitData);
			long limitPage = Integer.parseInt(limitData);
			ppdStmt.setString(1, search+"%");
			ppdStmt.setInt(2, numPage);
			ppdStmt.setLong(3, limitPage);
			ResultSet rs = ppdStmt.executeQuery();
			while(rs.next()) {
				Optional<LocalDate> introduced = Optional.empty();
				Optional<LocalDate> discontinued = Optional.empty();
				Optional<String> companyName = Optional.empty();
				long pcId = rs.getLong("computer.id");
				String name = rs.getString("computer.name");
				Date dateDebut = rs.getDate("computer.introduced");
				LocalDate ParseDateDebut = dateDebut != null ? dateDebut.toLocalDate() : null;
				introduced = Optional.ofNullable(ParseDateDebut);

				Date dateEnd = rs.getDate("computer.discontinued");
				LocalDate ParseDateEnd = dateEnd != null ? dateEnd.toLocalDate() : null;
				discontinued = Optional.ofNullable(ParseDateEnd);
				String strCompName = rs.getString("company.name");
				companyName = Optional.ofNullable(strCompName);

				computerBuilder.setId(pcId);
				computerBuilder.setName(name);
				computerBuilder.setIntroduced(introduced);
				computerBuilder.setDiscontinued(discontinued);
				computerBuilder.setCompanyName(companyName);
				computer = computerBuilder.build();
				computers.add(computer);
				
			}
			rs.close();

		} catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de l'affichage des ordinateurs."+e);
		}
		return computers;

	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public Computer getComputerDetails(String idPC) throws IOException, ValidationException, ClassNotFoundException {

		Computer computer = null;

		try (Connection cn = connectionDAO.getConnection()){

			computer = ComputerMapper.enterIdPC(idPC);
			PreparedStatement ppdStmt = cn.prepareStatement(GET_ONE);
			ppdStmt.setLong(1, computer.getId());
			ResultSet rs = ppdStmt.executeQuery();

			if(rs.next()) {
				Optional<LocalDate> introduced = Optional.empty();
				Optional<LocalDate> discontinued = Optional.empty();
				Optional<String> companyName = Optional.empty();

				String name = rs.getString("computer.name");

				Date dateDebut = rs.getDate("computer.introduced");
				LocalDate ParseDateDebut = dateDebut != null ? dateDebut.toLocalDate() : null;
				introduced = Optional.ofNullable(ParseDateDebut);

				Date dateEnd = rs.getDate("computer.discontinued");
				LocalDate ParseDateEnd = dateEnd != null ? dateEnd.toLocalDate() : null;
				discontinued = Optional.ofNullable(ParseDateEnd);

				String strCompName = rs.getString("company.name");
				companyName = Optional.ofNullable(strCompName);

				computer = new Computer(computer.getId(), name, introduced, discontinued, companyName);
			}
			

			else {
				//LOGGER.error("L'ordinateur que vous avez spécifié n'existe pas.");
			}
			
			rs.close();
		} catch (SQLException e) {

			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de l'affichage des détails de l'ordinateur.");
		}
		return computer;
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
	public void removeComputerFromCompany(long idComp, Connection cn) {
		
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

		return jdbcTemplate.queryForObject(SEARCH_COUNT, new Object[] {new StringBuilder(search).append("%").toString()},
				(resultSet, rowNum) -> resultSet.getInt(1));
	}
}