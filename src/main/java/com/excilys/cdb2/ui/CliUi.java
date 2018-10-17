package com.excilys.cdb2.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
//import org.apache.log4j.Logger;
import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.service.*;

/**
 * This class is the CLI
 * @author Nassim BOUKHARI
 */
public class CliUi {

	public static Scanner READER = new Scanner(System.in);
	//private final static Logger LOGGER = Logger.getLogger(CliUi.class);
	private static ComputerServices computerServices = new ComputerServices();
	private static CompanyServices companyServices = new CompanyServices();
	public static void Cli() throws IOException, ParseException, ValidationException, ClassNotFoundException, SQLException{
		String entry = "";
		boolean quit = false;
		do {
			System.out.println("\nBonjour, vous êtes dans une interface en ligne de commande");
			System.out.println("Menu:");
			System.out.println("1 - Liste des ordinateurs");
			System.out.println("2 - Liste des entreprises");
			System.out.println("3 - Afficher les détails d'un ordinateur");
			System.out.println("4 - Créer un ordinateur");
			System.out.println("5 - Modifier un ordinateur");
			System.out.println("6 - Supprimer un ordinateur");
			System.out.println("7 - Supprimer une compagnie");
			System.out.println("0 - Quitter\n");
			System.out.println("Pour choisir une option, veuillez entrer un chiffre:");
			String nbCh = "0";
			try {
				ChooseUi choose = ChooseUi.values()[Integer.valueOf(enterNbChoice(nbCh))];
				switch(choose) {
				case QUIT:
					quit = true;
					break;
				case LIST_ALL_COMPUTERS:
					computerServices.showComputers("1", "10");
					break;
				case LIST_ALL_COMPANIES:
					companyServices.showCompanies();
					break;
				case GET_COMPUTER_DETAILS:
					computerServices.showComputerDetail(null);
					break;
				case CREATE_COMPUTER:				
					computerServices.createComputer(null, null, null, null);
					break;
				case MODIFY_COMPUTER:
					computerServices.modifyComputer(null, null, null, null, null);
					break;
				case REMOVE_COMPUTER:
					//ComputerServices.deleteComputer(List<long> ids);
					break;
				case REMOVE_COMPANY:
					System.out.println("Entrer le nom de l'entreprise :");
					long id = enterLong(entry);
					companyServices.deleteCompany(id);
					break;
				default:
				}
			}
			catch(NumberFormatException e) {
				//LOGGER.error("Je n'ai pas compris votre requête, veuillez recommencer. (MENU)");
			}
		}while(!quit);
		READER.close();
		//LOGGER.info("Au revoir.");
	}

	public static int enterNbChoice(String nbCh) {

		nbCh = READER.nextLine();
		int intCh = Integer.parseInt(nbCh);
		return intCh;

	}
	
	public static long enterLong(String nb) {
		nb = READER.nextLine();
		int longNb = Integer.parseInt(nb);
		return longNb;
	}

	public static String enterName() {
		//LOGGER.info("Veuillez entrer le nom de l'ordinateur: ");
		String name = READER.nextLine();

		return name;
	}

	public static void main(String[] args) throws IOException, ParseException, ValidationException, ClassNotFoundException, SQLException {
		CliUi.Cli();
	}
}