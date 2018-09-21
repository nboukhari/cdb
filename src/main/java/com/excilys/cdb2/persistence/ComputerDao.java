package com.excilys.cdb2.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.ui.*;

/**
 * This class does all the functionnalities about computers
 * @author Nassim BOUKHARI
 */
public class ComputerDao {

	private static final String URL = "jdbc:mysql://localhost/computer-database-db";
	private static final String LOGIN = "admincdb";
	private static final String PASSWORD = "qwerty1234";
	private static String NUMBEROFPAGE = "0";
	private static final String GET_ALL = "SELECT id,name FROM computer limit ?,10";
	private static final String GET_ONE = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued) VALUES (?,?,?);";
	private static final String UPDATE = "UPDATE computer set [column] = ? where id =?;";
	private static final String DELETE = "DELETE from computer where id =?;";
	
	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public static void getAllComputers() {
		try (Connection cn = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
			do {
				PreparedStatement ppdStmt = cn.prepareStatement(GET_ALL);
				int page = CliUi.numberOfPage(NUMBEROFPAGE);
				ppdStmt.setInt(1, page);
				ResultSet rs = ppdStmt.executeQuery();
				System.out.println("***Liste des ordinateurs***\n");
				while(rs.next()) {
					System.out.println(rs.getInt(1)+"|"+rs.getString(2));
				}
			}while(CliUi.numberOfPage(NUMBEROFPAGE) != 0 && CliUi.numberOfPage(NUMBEROFPAGE) > 0);
		} catch (SQLException e) {
			System.out.println("Erreur SQL");
		}

	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 */
	public static void getComputerDetails() {
		try (Connection cn = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
			
			String idPC = "0";
			Computer computer = CliUi.enterIdPC(idPC);
			PreparedStatement ppdStmt = cn.prepareStatement(GET_ONE);
			ppdStmt.setLong(1, computer.getId());
			ResultSet rs = ppdStmt.executeQuery();
			
			if(rs.next()) {
				
				System.out.println("Id: "+rs.getInt("id")+"\nName: "
						+rs.getString("name")+"\nDate de lancement: "
						+rs.getString("introduced")+"\nDate d'arrêt: "
						+rs.getString("discontinued")+"\nId de l'entreprise: "
						+rs.getString("company_id"));
				
			}
			else {
				
				System.out.println("L'ordinateur que vous avez spécifié n'existe pas.");
				
			}
			rs.close();
		} catch (SQLException e) {
			
			System.out.println("Erreur SQL");
			
		}
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 */
	public static void setComputer() {

		try (Connection cn = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
			
			String namePC = CliUi.enterName();
			LocalDate introducedPC = CliUi.enterDateDebut();
			LocalDate discontinuedPC = CliUi.enterDateEnd();
			Computer computer = CliUi.createPC(namePC, introducedPC, discontinuedPC);
			PreparedStatement ppdStmt = cn.prepareStatement(INSERT);
			ppdStmt.setString(1, computer.getName());
			ppdStmt.setObject(2, computer.getIntroduced());
			ppdStmt.setObject(3, computer.getDiscontinued());
			ppdStmt.executeUpdate();
			
		} 
		catch (SQLException e) {
			
			System.out.println("Erreur SQL");
			
		}
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 */
	public static void updateComputer() {
		
		try (Connection cn = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
			
			boolean quit = false;
			String idPC = "0";
			String columnToModify;
			Computer computerId = CliUi.enterIdPC(idPC);
			
			if(computerId.getId() > 0) {
				PreparedStatement ppdStmt = cn.prepareStatement(GET_ONE);
				ppdStmt.setLong(1, computerId.getId());
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
								Computer computerName = CliUi.updateName(newValue);
								PreparedStatement ppdStmtUpdate = cn.prepareStatement(columnToModify);
								ppdStmtUpdate.setString(1, computerName.getName());
								ppdStmtUpdate.setLong(2, computerId.getId());
								ppdStmtUpdate.executeUpdate();
								
							}
							catch(Exception e) {
								
								System.out.println("Le nom que vous avez entrée n'est pas valide.\n");
							}
							break;
							
						case MODIFY_DEBUT:
							try {
								
								columnToModify = UPDATE.replace("[column]", "introduced");
								LocalDate newValueDate = CliUi.enterDateDebut();
								Computer computerIntroduced = CliUi.updateIntroduced(newValueDate);
								PreparedStatement ppdStmtUpdate = cn.prepareStatement(columnToModify);
								ppdStmtUpdate.setObject(1, computerIntroduced.getIntroduced());
								ppdStmtUpdate.setLong(2, computerId.getId());
								ppdStmtUpdate.executeUpdate();
								
							}
							catch(Exception e) {
								
								System.out.println("La date que vous avez entrée n'est pas valide.\n");
								
							}
							break;
							
						case MODIFY_END:
							try {
								
								columnToModify = UPDATE.replace("[column]", "discontinued");
								LocalDate newValueDate = CliUi.enterDateEnd();
								Computer computerDiscontinued = CliUi.updateDiscontinued(newValueDate);
								PreparedStatement ppdStmtUpdate = cn.prepareStatement(columnToModify);
								ppdStmtUpdate.setObject(1, computerDiscontinued.getDiscontinued());
								ppdStmtUpdate.setLong(2, computerId.getId());
								ppdStmtUpdate.executeUpdate();
								
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
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 */
	public static void removeComputer() {
		
		try (Connection cn = DriverManager.getConnection(URL, LOGIN, PASSWORD)){
			
			String idPC = "0";
			Computer computer = CliUi.enterIdPC(idPC);
			PreparedStatement ppdStmt = cn.prepareStatement(DELETE);
			ppdStmt.setLong(1, computer.getId());;
			ppdStmt.executeUpdate();

		} catch (SQLException e) {
			
			System.out.println("Erreur SQL");
			
		}
	}
}