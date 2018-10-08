package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb2.mapper.ComputerMapper;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;

/**
 * This class does all the functionnalities about computers
 * @author Nassim BOUKHARI
 */
public class ComputerDao {

	private static final String GET_ALL = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id LIMIT ?,?";
	private static final String GET_ONE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id =?;";
	private static final String DELETE = "DELETE FROM computer WHERE id =?;";
	private static final String COUNT = "SELECT COUNT(*) FROM computer";


	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static List<Computer> getAllComputers(String NumberOfPage, String LimitData) throws IOException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = ConnectionDAO.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(GET_ALL);
			int numPage = ComputerMapper.numberOfPage(NumberOfPage);
			long limitPage = ComputerMapper.stringToInt(LimitData);
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
			System.out.println("Une erreur SQL est survenue, voici la cause : "+e);
		}
		return computers;

	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static Computer getComputerDetails(String idPC) throws IOException {

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
				System.out.println("L'ordinateur que vous avez spécifié n'existe pas.");

			}
			rs.close();
		} catch (SQLException e) {

			System.out.println("Une erreur SQL est survenue, voici la cause : "+e);

		}
		return computer;
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void setComputer(String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException, ParseException {


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
			System.out.println("Une erreur SQL est survenue, voici la cause : "+e);
		}
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static Computer updateComputer(String idPC, String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException {

		Computer computer = null;
		try (Connection cn = ConnectionDAO.getConnection()){

			try {
				long newIdPC = ComputerMapper.enterId(idPC);
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

			}
			catch(Exception e) {
				System.out.println("L'uns des champs n'est pas dans le bon format, veuillez recommencer.\n");
			}
		} catch (SQLException e) {
			System.out.println("Une erreur SQL est survenue, voici la cause : "+e);
		}
		return computer;
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> removeComputer() throws IOException {

		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = ConnectionDAO.getConnection()){

			String idPC = "0";
			Computer computer = ComputerMapper.enterIdPC(idPC);
			PreparedStatement ppdStmt = cn.prepareStatement(DELETE);
			ppdStmt.setLong(1, computer.getId());;
			ppdStmt.executeUpdate();
			computerBuilder.setId(computer.getId());
			computer = computerBuilder.build();
			computers.add(computer);

		} catch (SQLException e) {

			System.out.println("Une erreur SQL est survenue, voici la cause : "+e);

		}
		return computers;
	}

	/**
	 * This method displays number of computers
	 * @author Nassim BOUKHARI
	 */
	public static int getComputersCount() throws SQLException, IOException {
		int nbComp = 0;
		try (Connection cn = ConnectionDAO.getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(COUNT);
			ResultSet rs = ppdStmt.executeQuery(COUNT);
			while(rs.next()) {
				nbComp = rs.getInt(1);
			}
		}
		catch (SQLException e) {
			System.out.println("Une erreur SQL est survenue, voici la cause : "+e);
		}
		return nbComp;
	}
}