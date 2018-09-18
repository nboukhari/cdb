package com.excilys.cdb2.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
	private static final String GET_ALL ="SELECT id,name FROM computer";
	private static final String GET_ONE = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id =?;";
	private static final String INSERT = "INSERT INTO computer (name,introduced,discontinued) VALUES (?,?,?);";
	private static final String UPDATE ="UPDATE computer set ? = ? where id =?;";
	private static final String DELETE ="DELETE from computer where id =?;";
	public static final Scanner READER = new Scanner(System.in);
	private static Connection cn;
	private static Statement st;

	/**
	 * This method displays all the computers
	 * @author Nassim BOUKHARI
	 */
	public static void getAllComputers() {
		try {
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = cn.createStatement();
			ResultSet rs = st.executeQuery(GET_ALL);
			System.out.println("***Liste des ordinateurs***\n");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"|"+rs.getString(2));
			}
			rs.close();
			closeConnection(cn, st);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method displays all the details about a computer
	 * @author Nassim BOUKHARI
	 */
	public static void getComputerDetails() {
		try {
			int idPC = 0;
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = cn.createStatement();
			Computer computer = CliUi.enterId(idPC);
			PreparedStatement ppdStmt = cn.prepareStatement(GET_ONE);
			ppdStmt.setLong(1, computer.getId());;
			ResultSet rs = ppdStmt.executeQuery();
			System.out.println("***Détails de l'ordinateur***\n");
			if(rs.next()) {
				System.out.println("Id: "+rs.getInt("id")+"\nName: "+rs.getString("name")+"\nDate de lancement: "+rs.getString("introduced")+"\nDate d'arrêt: "+rs.getString("discontinued")+"\nId de l'entreprise: "+rs.getString("company_id"));
			}
			else {
				System.out.println("L'ordinateur que vous avez spécifié n'existe pas.");
			}
			rs.close();
			closeConnection(cn, st);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a computer
	 * @author Nassim BOUKHARI
	 */
	public static void setComputer() {
		try {
			String namePC = null;
			String introducedPC = null;
			String discontinuedPC = null;
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = cn.createStatement();
			Computer computer = CliUi.createPC(namePC, introducedPC, discontinuedPC);
			PreparedStatement ppdStmt = cn.prepareStatement(INSERT);
			ppdStmt.setString(1, computer.getName());
			ppdStmt.setObject(2, computer.getIntroduced());
			ppdStmt.setObject(3, computer.getDiscontinued());
			ppdStmt.executeUpdate();
			closeConnection(cn, st);
		} catch (SQLException e) {
			//System.out.println("Le nom que vous avez entré n'est pas valide.\n");
			e.printStackTrace();
		}
	}

	/**
	 * This method updates a computer
	 * @author Nassim BOUKHARI
	 */
	public static void updateComputer() {
		try {
			boolean quit = false;
			int idPC = 0;
			String choice = null;
			String columnToModify = null;
			String newValue = null;
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = cn.createStatement();
			Computer computerId = CliUi.enterId(idPC);
			if(computerId.getId() > 0) {
				String PC = "SELECT name,introduced,discontinued FROM computer WHERE id="+computerId.getId()+"";
				ResultSet getPC = st.executeQuery(PC);
				while(getPC.next())
					System.out.println("\nVoici les informations concernant l'ordinateur que vous pouvez modifier: \nNom: "+getPC.getString("name")+"\nDate de lancement: "+getPC.getString("introduced")+"\nDate d'arrêt: "+getPC.getString("discontinued")+"\n");
				do {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("Que voulez vous modifier? ");
					System.out.println("1 - Le nom\n2 - La date de lancement\n3 - La date d'arrêt\n0 - Quitter la modification");
					
					choice = READER.nextLine();
					switch(choice) {
					case("0"):
						quit = true;
					System.out.println("Vous avez choisi de quitter la modification.");
					break;
					case("1"):
						try {
							columnToModify = "name";
							Computer computerName = CliUi.updateName(newValue);
							String sql = "UPDATE computer set "+columnToModify+" ='"+computerName.getName()+"' where id = '"+computerId.getId()+"'";
							st.executeUpdate(sql);
						}
					catch(Exception e) {
						System.out.println("Le nom que vous avez entrée n'est pas valide.\n");
					}
					break;
					case("2"):
						try {
							columnToModify = "introduced";
							Computer computerIntroduced = CliUi.updateIntroduced(newValue);
							String sql2 = "UPDATE computer set "+columnToModify+" ='"+computerIntroduced.getIntroduced()+"' where id = '"+computerId.getId()+"'";
							st.executeUpdate(sql2);
						}
					catch(Exception e) {
						System.out.println("La date que vous avez entrée n'est pas valide.\n");
					}
					break;
					case("3"):
						try {
							columnToModify = "discontinued";
							Computer computerDiscontinued = CliUi.updateDiscontinued(newValue);
							String sql3 = "UPDATE computer set "+columnToModify+" ='"+computerDiscontinued.getDiscontinued()+"' where id = '"+computerId.getId()+"'";
							st.executeUpdate(sql3);
						}
					catch(Exception e) {
						System.out.println("La date que vous avez entrée n'est pas valide.");
					}
					break;
					default:
						System.out.println("Je n'ai pas compris votre requête, veuillez recommencer.\n");
						break;
					}
				}while(!quit);
			}
			else {
				System.out.println("L'id que vous avez entré est incorrect.");
			}
			closeConnection(cn, st);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method deletes a computer
	 * @author Nassim BOUKHARI
	 */
	public static void removeComputer() {
		try {
			int idPC = 0;
			cn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
			st = cn.createStatement();
			Computer computer = CliUi.enterId(idPC);
			PreparedStatement ppdStmt = cn.prepareStatement(DELETE);
			ppdStmt.setLong(1, computer.getId());;
			ppdStmt.executeUpdate();
			System.out.println("***Ordinateur supprimé***\n");
			closeConnection(cn, st);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection(Connection cn, Statement st) {
		try {
			cn.close();
			st.close();
		} catch (SQLException e) {
			System.out.println("Impossible de fermer la connexion.");
		}
	}
}