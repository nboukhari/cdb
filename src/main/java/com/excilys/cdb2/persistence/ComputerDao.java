package com.excilys.cdb2.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
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
import java.util.Properties;

import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;
import com.excilys.cdb2.ui.*;

/**
 * This class does all the functionnalities about computers
 * @author Nassim BOUKHARI
 */
public class ComputerDao {

	private static final String GET_ALL = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id";
	private static final String GET_ONE = "select computer.id, computer.name, computer.introduced, computer.discontinued, company.name from computer LEFT JOIN company on company.id = computer.company_id WHERE computer.id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued,company_id) VALUES (?,?,?,?);";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id =?;";
	private static final String DELETE = "DELETE FROM computer WHERE id =?;";
	private static final String COUNT = "SELECT COUNT(*) FROM computer";
	private static final String GET_ID_COMPANY = "SELECT id FROM company WHERE name =?;";


	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static List<Computer> getAllComputers() throws IOException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = getConnection()){

			PreparedStatement ppdStmt = cn.prepareStatement(GET_ALL);
			ResultSet rs = ppdStmt.executeQuery(GET_ALL);
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
				//System.out.println(pcId+"|"+name+"|"+companyName);

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
			e.printStackTrace();
		}
		return computers;

	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> getComputerDetails(String idPC) throws IOException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = getConnection()){

			computer = CliUi.enterIdPC(idPC);
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

				computerBuilder.setId(computer.getId());
				computerBuilder.setName(name);
				computerBuilder.setIntroduced(introduced);
				computerBuilder.setDiscontinued(discontinued);
				computerBuilder.setCompanyName(companyName);
				computer = computerBuilder.build();
				computers.add(computer);

				System.out.println(computers);
			}

			else {

				System.out.println("L'ordinateur que vous avez spécifié n'existe pas.");

			}
			rs.close();
		} catch (SQLException e) {

			e.printStackTrace();

		}
		return computers;
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static List<Computer> setComputer(String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException, ParseException {

		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = getConnection()){

			//namePC = CliUi.enterName();
			Optional<String> companyName = CliUi.enterCompanyName(companyNameStr);
			Optional<LocalDate> introducedPC = CliUi.enterDateDebut(introducedStr);
			Optional<LocalDate> discontinuedPC = CliUi.enterDateEnd(discontinuedStr);
			Computer computer = CliUi.createPC(namePC, introducedPC, discontinuedPC,companyName);
			PreparedStatement ppdStmt = cn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			ppdStmt.setString(1, computer.getName());
			if(computer.getIntroduced().isPresent())
				ppdStmt.setObject(2, computer.getIntroduced().get());
			else
				ppdStmt.setNull(2, java.sql.Types.DATE);
			if(computer.getDiscontinued().isPresent())
				ppdStmt.setObject(3, computer.getDiscontinued().get());
			else
				ppdStmt.setNull(3, java.sql.Types.DATE);
			if(computer.getCompanyName().isPresent())
				ppdStmt.setString(4, computer.getCompanyName().get());
			else
				ppdStmt.setInt(4, 0);
			System.out.println("TEEEEST "+computer);
			ppdStmt.executeUpdate();
			ResultSet rs = ppdStmt.getGeneratedKeys();
			System.out.println("test  "+ppdStmt);
			while (rs.next()) {
				int idPC = rs.getInt(1);
				computerBuilder.setId(idPC);
				computerBuilder.setName(computer.getName());
				computerBuilder.setIntroduced(computer.getIntroduced());
				computerBuilder.setDiscontinued(computer.getDiscontinued());
				computer = computerBuilder.build();
				computers.add(computer);
				System.out.println("TEEEEST "+computers);
			}

		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return computers;
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> updateComputer(String idPC, String namePC, String introducedStr, String discontinuedStr, String companyNameStr) throws IOException {

		ArrayList<Computer> computers = new ArrayList<Computer>();

		try (Connection cn = getConnection()){

					try {
						long newIdPC = CliUi.enterId(idPC);
						Optional<LocalDate> newDateDebut = CliUi.enterDateDebut(introducedStr);
						Optional<LocalDate> newDateEnd = CliUi.enterDateEnd(discontinuedStr);
						Optional<String> newCompany = CliUi.enterCompanyName(companyNameStr);
						Computer computer = CliUi.updatePC(newIdPC, namePC, newDateDebut, newDateEnd,newCompany);

						PreparedStatement ppdStmtUpdate = cn.prepareStatement(UPDATE);

						ppdStmtUpdate.setString(1, computer.getName());
						if(computer.getIntroduced().isPresent()) {
							ppdStmtUpdate.setObject(2, computer.getIntroduced().get().toString());
						}
						else
							ppdStmtUpdate.setNull(2, java.sql.Types.DATE);
						if(computer.getDiscontinued().isPresent())
							ppdStmtUpdate.setObject(3, computer.getDiscontinued().get().toString());
						else
							ppdStmtUpdate.setNull(3, java.sql.Types.DATE);
						if(computer.getCompanyName().isPresent())
							ppdStmtUpdate.setString(4, computer.getCompanyName().get());
						else
							ppdStmtUpdate.setInt(4, 0);
						ppdStmtUpdate.setLong(5, computer.getId());
						ppdStmtUpdate.executeUpdate();
						computers.add(computer);
						
					}
					catch(Exception e) {
						System.out.println("L'uns des champs n'est pas dans le bon format, veuillez recommencer.\n");
					}
			} catch (SQLException e) {
				System.out.println("Erreur SQL");
			}
			return computers;
		}

		/**
		 * This method deletes a computer
		 * @author Nassim BOUKHARI
		 * @throws IOException 
		 */
		public static List<Computer> removeComputer() throws IOException {

			ArrayList<Computer> computers = new ArrayList<Computer>();
			ComputerBuilder computerBuilder = new ComputerBuilder();

			try (Connection cn = getConnection()){

				String idPC = "0";
				Computer computer = CliUi.enterIdPC(idPC);
				PreparedStatement ppdStmt = cn.prepareStatement(DELETE);
				ppdStmt.setLong(1, computer.getId());;
				ppdStmt.executeUpdate();
				computerBuilder.setId(computer.getId());
				computer = computerBuilder.build();
				computers.add(computer);

			} catch (SQLException e) {

				System.out.println("Erreur SQL");

			}
			return computers;
		}

		/**
		 * This method displays number of computers
		 * @author Nassim BOUKHARI
		 */
		public static int getComputersCount() throws SQLException, IOException {
			int nbComp = 0;
			try (Connection cn = getConnection()){

				PreparedStatement ppdStmt = cn.prepareStatement(COUNT);
				ResultSet rs = ppdStmt.executeQuery(COUNT);
				while(rs.next()) {
					nbComp = rs.getInt(1);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return nbComp;
		}
		
		public static long getCompanyId(String CompanyName) throws SQLException, IOException {
			int IdComp = 0;
			try (Connection cn = getConnection()){
				Optional<String> newCompany = CliUi.enterCompanyName(CompanyName);
				Computer computer = CliUi.CompName(newCompany);
				PreparedStatement ppdStmt = cn.prepareStatement(GET_ID_COMPANY);
				System.out.println("WAAAH"+computer.getCompanyName().get().toString());
				ppdStmt.setString(1, computer.getCompanyName().get().toString());
				System.out.println("waye "+ppdStmt);
				ResultSet rs = ppdStmt.executeQuery(GET_ID_COMPANY);
				while(rs.next()) {
					System.out.println("WAWAWAWA "+rs);
					IdComp = rs.getInt(1);
					System.out.println("TEST "+IdComp);
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			return IdComp;
		}
		
		/**
		 * This method connects to the database
		 * @author Nassim BOUKHARI
		 */
		public static Connection getConnection() throws IOException, SQLException {
			Connection cn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			try {
				Properties prop = new Properties();
				InputStream inputStream = ComputerDao.class.getClassLoader().getResourceAsStream("config.properties");
				prop.load(inputStream);
				String URL = prop.getProperty("URL");
				String LOGIN = prop.getProperty("LOGIN");
				String PASSWORD = prop.getProperty("PASSWORD");
				cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
				return cn;
			} catch (Exception e) {
				System.out.println("Exception: " + e);
			}
			return cn;
		}
	}