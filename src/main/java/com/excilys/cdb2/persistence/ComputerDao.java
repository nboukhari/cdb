package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

//import org.apache.log4j.Logger;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;

/**
 * This class does all the functionnalities about computers
 * @author Nassim BOUKHARI
 */
public class ComputerDao {

	private static final String GET_ALL = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id LIMIT ?,?";
	private static final String SEARCH = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.name LIKE ? LIMIT ?,?";
	private static final String GET_ONE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id =?;";
	private static final String DELETE = "DELETE FROM computer WHERE id in (?);";
	private static final String SEARCH_COUNT = "SELECT COUNT(*) FROM computer WHERE name LIKE ?";
	private static final String COUNT = "SELECT COUNT(*) FROM computer";
	//private final static Logger LOGGER = Logger.getLogger(ComputerDao.class);

	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static List<Computer> getAllComputers(String NumberOfPage, String LimitData) throws IOException, ValidationException, ClassNotFoundException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = ConnectionDAO.getConnection()){

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
	public static List<Computer> searchComputers(String search, String numberOfPage, String limitData) throws IOException, ValidationException, ClassNotFoundException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = ConnectionDAO.getConnection()){

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
	public static Computer getComputerDetails(String idPC) throws IOException, ValidationException, ClassNotFoundException {

		Computer computer = null;

		try (Connection cn = ConnectionDAO.getConnection()){

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
	public static void setComputer(String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException, ParseException, ValidationException, ClassNotFoundException {


		try (Connection cn = ConnectionDAO.getConnection()){

			Optional<String> companyName = ComputerMapper.enterCompanyName(companyNameStr);
			Optional<LocalDate> introducedPC = ComputerMapper.enterDate(introducedStr);
			Optional<LocalDate> discontinuedPC = ComputerMapper.enterDate(discontinuedStr);
			Computer computer = ComputerMapper.createPC(namePC, introducedPC, discontinuedPC,companyName);
			PreparedStatement ppdStmt = cn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			ppdStmt.setString(1, computer.getName());
			if(computer.getIntroduced().isPresent())
				ppdStmt.setObject(2, computer.getIntroduced().get().toString());
			else
				ppdStmt.setNull(2, java.sql.Types.DATE);
			if(computer.getDiscontinued().isPresent())
				ppdStmt.setObject(3, computer.getDiscontinued().get().toString());
			else
				ppdStmt.setNull(3, java.sql.Types.DATE);
			if(computer.getCompanyName().isPresent())
				ppdStmt.setString(4, computer.getCompanyName().get().toString());
			else
				ppdStmt.setNull(4, 0);
			ppdStmt.executeUpdate();
			ResultSet rs = ppdStmt.getGeneratedKeys();


		}
		catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de la création de l'ordinateur.");
		}

	}


	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws ClassNotFoundException 
	 */
	public static Computer updateComputer(String idPC, String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException, ParseException, ValidationException, ClassNotFoundException {

		Computer computer = null;
		try (Connection cn = ConnectionDAO.getConnection()){

			long newIdPC = Integer.parseInt(idPC);
			Optional<LocalDate> newDateDebut = ComputerMapper.enterDate(introducedStr);
			Optional<LocalDate> newDateEnd = ComputerMapper.enterDate(discontinuedStr);
			Optional<String> newCompany = ComputerMapper.enterCompanyName(companyNameStr);
			computer = new Computer(newIdPC, namePC, newDateDebut, newDateEnd,newCompany);

			PreparedStatement ppdStmtUpdate = cn.prepareStatement(UPDATE);

			ppdStmtUpdate.setString(1, computer.getName());
			if(computer.getIntroduced().isPresent()) {
				ppdStmtUpdate.setObject(2, computer.getIntroduced().get().toString());
			}
			else {
				ppdStmtUpdate.setNull(2, java.sql.Types.DATE);
			}
			if(computer.getDiscontinued().isPresent()) {
				ppdStmtUpdate.setObject(3, computer.getDiscontinued().get().toString());
			}
			else {
				ppdStmtUpdate.setNull(3, java.sql.Types.DATE);
			}
			if(computer.getCompanyName().isPresent()) {
				ppdStmtUpdate.setString(4, computer.getCompanyName().get().toString());
			}
			else {
				ppdStmtUpdate.setNull(4, 0);
			}
			ppdStmtUpdate.setLong(5, computer.getId());
			ppdStmtUpdate.executeUpdate();
		} catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de la mise à jour de l'ordinateur.");
		}
		return computer;
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static void removeComputer(List<Long> ids) throws IOException, ValidationException, ClassNotFoundException {

		for (Long id : ids) {
			try (Connection cn = ConnectionDAO.getConnection()){

				PreparedStatement ppdStmt = cn.prepareStatement(DELETE);
				ppdStmt.setLong(1, id);
				ppdStmt.executeUpdate();

			} catch (SQLException e) {

				//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
				throw new ValidationException("Une erreur est survenue lors de la suppression de l'ordinateur.");
			}
		}
	}

	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static int getComputersCount() throws IOException, ValidationException, ClassNotFoundException {
		int nbComp = 0;
		try (Connection cn = ConnectionDAO.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(COUNT);
			ResultSet rs = ppdStmt.executeQuery(COUNT);
			while(rs.next()) {
				nbComp = rs.getInt(1);
			}
		}
		catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de l'affichage du nombre d'ordinateur.");
		}
		return nbComp;
	}
	
	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 * @throws ValidationException 
	 * @throws ClassNotFoundException 
	 */
	public static int getComputersCountFromSearch(String search) throws IOException, ValidationException, ClassNotFoundException {
		int nbComp = 0;
		try (Connection cn = ConnectionDAO.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(SEARCH_COUNT);
			ppdStmt.setString(1, search+"%");
			ResultSet rs = ppdStmt.executeQuery();
			while(rs.next()) {
				nbComp = rs.getInt(1);
			}
		}
		catch (SQLException e) {
			//LOGGER.error("Une erreur SQL est survenue, voici la cause : "+e);
			throw new ValidationException("Une erreur est survenue lors de l'affichage du nombre d'ordinateur." +e);
		}
		return nbComp;
	}
}