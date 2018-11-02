package com.excilys.cdb2.ui;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cdb2.configuration.AppConfig;

import com.excilys.cdb2.exception.ValidationException;
import com.excilys.cdb2.service.CompanyServices;
import com.excilys.cdb2.service.ComputerServices;

/**
 * This class is the CLI
 * @author Nassim BOUKHARI
 */
@Component
public class CliUi {

	public Scanner READER = new Scanner(System.in);
	private final static Logger logger = LoggerFactory.getLogger("CliUi");
	
	@Autowired
	private ComputerServices computerServices;
	
	@Autowired
	private CompanyServices companyServices;
	
	public void Cli() throws IOException, ParseException, ValidationException, ClassNotFoundException, SQLException{
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
					
					companyServices.deleteCompany(enterLong(entry));
					break;
				default:
				}
			}
			catch(NumberFormatException e) {
				logger.error("Je n'ai pas compris votre requête, veuillez recommencer. (MENU)");
			}
		}while(!quit);
		READER.close();
		logger.info("Au revoir.");
	}

	public int enterNbChoice(String nbCh) {

		nbCh = READER.nextLine();
		int intCh = Integer.parseInt(nbCh);
		return intCh;

	}
	
	public long enterLong(String nb) {
		nb = READER.nextLine();
		int longNb = Integer.parseInt(nb);
		return longNb;
	}

	public String enterName() {
		logger.info("Veuillez entrer le nom de l'ordinateur: ");
		String name = READER.nextLine();

		return name;
	}

	public static void main(String[] args) throws IOException, ParseException, ValidationException, ClassNotFoundException, SQLException {
		ApplicationContext context = new  AnnotationConfigApplicationContext(AppConfig.class);
		context.getBean(CliUi.class).Cli();
	}
}