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

	private static final String GET_ALL = "SELECT id,name FROM computer";
	private static final String GET_ONE = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued) VALUES (?,?,?);";
	private static final String UPDATE = "UPDATE computer set [column] = ? where id =?;";
	private static final String DELETE = "DELETE from computer where id =?;";


	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
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
				long pcId = rs.getLong("id");
				String name = rs.getString("name");

				System.out.println(pcId+"|"+name);

				computerBuilder.setId(pcId);
				computerBuilder.setName(name);
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
	public static List<Computer> getComputerDetails() throws IOException {

		Computer computer;
		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = getConnection()){

			String idPC = "0";
			computer = CliUi.enterIdPC(idPC);
			PreparedStatement ppdStmt = cn.prepareStatement(GET_ONE);
			ppdStmt.setLong(1, computer.getId());
			ResultSet rs = ppdStmt.executeQuery();

			if(rs.next()) {
				Optional<LocalDate> introduced = Optional.empty();
				Optional<LocalDate> discontinued = Optional.empty();
				
				String name = rs.getString("name");
				
				Date dateDebut = rs.getDate("introduced");
				LocalDate ParseDateDebut = dateDebut != null ? dateDebut.toLocalDate() : null;
				introduced = Optional.ofNullable(ParseDateDebut);
				
				Date dateEnd = rs.getDate("discontinued");
				LocalDate ParseDateEnd = dateEnd != null ? dateEnd.toLocalDate() : null;
				discontinued = Optional.ofNullable(ParseDateEnd);
				
				long companyId = rs.getLong("company_id");

				
				computerBuilder.setId(computer.getId());
				computerBuilder.setName(name);
				computerBuilder.setIntroduced(introduced);
				computerBuilder.setDiscontinued(discontinued);
				computerBuilder.setCompanyId(companyId);
				computer = computerBuilder.build();
				computers.add(computer);
				
				System.out.println(computers);
			}

			else {

				System.out.println("L'ordinateur que vous avez spécifié n'existe pas.");

			}
			rs.close();
		} catch (SQLException e) {

			System.out.println("Erreur SQL");

		}
		return computers;
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static List<Computer> setComputer() throws IOException, ParseException {

		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = getConnection()){

			String namePC = CliUi.enterName();
			Optional<LocalDate> introducedPC = CliUi.enterDateDebut();
			Optional<LocalDate> discontinuedPC = CliUi.enterDateEnd();
			Computer computer = CliUi.createPC(namePC, introducedPC, discontinuedPC);
			PreparedStatement ppdStmt = cn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			ppdStmt.setString(1, computer.getName());
			if(computer.getIntroduced().isPresent())
				ppdStmt.setObject(2, computer.getIntroduced().get());
			if(computer.getDiscontinued().isPresent())
				ppdStmt.setObject(3, computer.getDiscontinued().get());
			ppdStmt.executeUpdate();
			ResultSet rs = ppdStmt.getGeneratedKeys();

			while (rs.next()) {
				int idPC = rs.getInt(1);
				computerBuilder.setId(idPC);
				computerBuilder.setName(computer.getName());
				computerBuilder.setIntroduced(computer.getIntroduced());
				computerBuilder.setDiscontinued(computer.getDiscontinued());
				computer = computerBuilder.build();
				computers.add(computer);
			}

		} 
		catch (SQLException e) {
			System.out.print("Erreur SQL: ");
			e.printStackTrace();
		}
		return computers;
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 * @throws IOException 
	 */
	public static List<Computer> updateComputer() throws IOException {

		ArrayList<Computer> computers = new ArrayList<Computer>();
		ComputerBuilder computerBuilder = new ComputerBuilder();

		try (Connection cn = getConnection()){

			boolean quit = false;
			String idPC = "0";
			String columnToModify;
			Computer computer = CliUi.enterIdPC(idPC);

			if(computer.getId() > 0) {
				PreparedStatement ppdStmt = cn.prepareStatement(GET_ONE);
				ppdStmt.setLong(1, computer.getId());
				ResultSet getPC = ppdStmt.executeQuery();

				while(getPC.next()) {

					System.out.println("\nVoici les informations concernant l'ordinateur que vous pouvez modifier: \nNom: "
							+getPC.getString("name")+"\nDate de lancement: "
							+getPC.getString("introduced")+"\nDate d'arrêt: "
							+getPC.getString("discontinued")+"\n");

				}

				do {

					System.out.println("Que voulez vous modifier? ");
					System.out.println("1 - Le nom\n2 - La date de lancement\n3 - La date d'arrêt\n0 - Quitter la modification");
					String nbChoice = "0";

					try {

						ChooseDao choose = ChooseDao.values()[Integer.valueOf(CliUi.enterNbChoice(nbChoice))];
						switch(choose) {

						case QUIT:
							quit = true;
							System.out.println("Vous avez choisi de quitter la modification.");
							break;

						case MODIFY_NAME:
							try {

								columnToModify = UPDATE.replace("[column]", "name");
								String newValue = CliUi.enterName();
								computer = CliUi.updateName(newValue);
								PreparedStatement ppdStmtUpdate = cn.prepareStatement(columnToModify);
								ppdStmtUpdate.setString(1, computer.getName());
								ppdStmtUpdate.setLong(2, computer.getId());
								ppdStmtUpdate.executeUpdate();
								computerBuilder.setId(computer.getId());
								computerBuilder.setName(computer.getName());
								computers.add(computer);

							}
							catch(Exception e) {

								System.out.println("Le nom que vous avez entrée n'est pas valide.\n");
							}
							break;

						case MODIFY_DEBUT:
							try {

								columnToModify = UPDATE.replace("[column]", "introduced");
								Optional<LocalDate> newValueDate = CliUi.enterDateDebut();
								computer = CliUi.updateIntroduced(newValueDate);
								PreparedStatement ppdStmtUpdate = cn.prepareStatement(columnToModify);
								
								if (computer.getIntroduced().isPresent()) {
									ppdStmtUpdate.setObject(1, computer.getIntroduced().get());
								}
								ppdStmtUpdate.setLong(2, computer.getId());
								ppdStmtUpdate.executeUpdate();
								computerBuilder.setId(computer.getId());
								computerBuilder.setIntroduced(computer.getIntroduced());
								computers.add(computer);

							}
							catch(Exception e) {

								System.out.println("La date que vous avez entrée n'est pas valide.\n");

							}
							break;

						case MODIFY_END:
							try {

								columnToModify = UPDATE.replace("[column]", "discontinued");
								Optional<LocalDate> newValueDate = CliUi.enterDateEnd();
								computer = CliUi.updateDiscontinued(newValueDate);
								PreparedStatement ppdStmtUpdate = cn.prepareStatement(columnToModify);
								
								if (computer.getDiscontinued().isPresent()) {
									ppdStmtUpdate.setObject(1, computer.getDiscontinued().get());
								}
								
								ppdStmtUpdate.setLong(2, computer.getId());
								ppdStmtUpdate.executeUpdate();
								computerBuilder.setId(computer.getId());
								computerBuilder.setIntroduced(computer.getDiscontinued());
								computers.add(computer);

							}
							catch(Exception e) {

								System.out.println("La date que vous avez entrée n'est pas valide.");

							}
							break;

						default:
							System.out.println("Je n'ai pas compris votre requête, veuillez recommencer.\n");
						}

					}

					catch(Exception e) {

						System.out.println("Je n'ai pas compris votre requête, veuillez recommencer.");
					}

				}while(!quit);
			}

			else {
				System.out.println("L'id que vous avez entré est incorrect.");
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
	 * This method connects to the database
	 * @author Nassim BOUKHARI
	 */
	public static Connection getConnection() throws IOException, SQLException {
		Connection cn = null;
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