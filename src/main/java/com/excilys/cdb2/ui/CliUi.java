package com.excilys.cdb2.ui;

import java.time.LocalDate;
import java.util.Scanner;
import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;
import com.excilys.cdb2.service.*;

/**
 * This class is the CLI
 * @author Nassim BOUKHARI
 */
public class CliUi {
	

	public static Scanner READER = new Scanner(System.in);
	
	public static void Cli(){
		
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
			System.out.println("0 - Quitter\n");
			System.out.println("Pour choisir une option, veuillez entrer un chiffre:");

			int nbCh = 0;
			ChooseUi choose = ChooseUi.values()[Integer.valueOf(EnterId(nbCh))];
			
			switch(choose) {
			case QUIT:
				quit = true;
			break;
			case LIST_ALL_COMPUTERS:
				ComputerServices.showComputers();
			break;
			case LIST_ALL_COMPANIES:
				CompanyServices.showCompanies();
			break;
			case GET_COMPUTER_DETAILS:
				ComputerServices.showComputerDetail();
			break;
			case CREATE_COMPUTER:
				ComputerServices.createComputer();
			break;
			case MODIFY_COMPUTER:
				ComputerServices.modifyComputer();
			break;
			case REMOVE_COMPUTER:
				ComputerServices.deleteComputer();
			break;
			default:
				System.out.println("Je n'ai pas compris votre requête, veuillez recommencer.");
				break;
			}
		}while(!quit);
		System.out.println("Au revoir.");
		READER.close();
	}
	
	public static int EnterId(int nbCh) {
		nbCh = READER.nextInt();
		return nbCh;
	}
	
	public static Computer enterId(int idPC) {
		
		System.out.println("Veuillez entrer l'id de l'ordinateur: ");
		idPC = Integer.parseInt(READER.next());
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(idPC);
		computer = computerBuilder.build();
		
		return computer;
	}
	
	public static Computer createPC(String name,String introduced,String discontinued) {
		
		System.out.println("Veuillez entrer le nom du nouvel ordinateur à créer: ");
		name = READER.nextLine();
		System.out.println("Veuillez entrer la date de lancement du nouvel ordinateur à créer: ");
		introduced = READER.nextLine();
		LocalDate introducedToLocalDate = LocalDate.parse(introduced);
		System.out.println("Veuillez entrer la date d'arrêt du nouvel ordinateur à créer: ");
		discontinued = READER.nextLine();
		LocalDate discontinuedToLocalDate = LocalDate.parse(discontinued);
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introducedToLocalDate);
		computerBuilder.setDiscontinued(discontinuedToLocalDate);
		computer = computerBuilder.build();
		
		return computer;
	}
	
	public static Computer updateName(String name) {
		
		System.out.println("Veuillez entrer le nouveau nom de l'ordinateur: ");
		name = READER.nextLine();
		System.out.println("Voici le nouveau nom de l'ordinateur: "+name+"\n");
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setName(name);
		computer = computerBuilder.build();

		return computer;
		
	}
	
	public static Computer updateIntroduced(String introduced) {
		
		System.out.println("Veuillez entrer la nouvelle date du lancement de l'ordinateur (au format YYYY-mm-dd): ");
		introduced = READER.nextLine();
		LocalDate introducedToLocalDate = LocalDate.parse(introduced);
		System.out.println("Voici la nouvelle date du lancement de l'ordinateur: "+introduced+"\n");
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setIntroduced(introducedToLocalDate);
		computer = computerBuilder.build();
		
		return computer;
	}
	
	public static Computer updateDiscontinued(String discontinued) {
		
		System.out.println("Veuillez entrer la nouvelle date de l'arrêt de l'ordinateur (au format YYYY-mm-dd): ");
		discontinued = READER.nextLine();
		LocalDate discontinuedToLocalDate = LocalDate.parse(discontinued);
		System.out.println("Voici la nouvelle date de l'arrêt de l'ordinateur: "+discontinued+"\n");
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setDiscontinued(discontinuedToLocalDate);
		computer = computerBuilder.build();
		
		return computer;
	}
	
	public static void main(String[] args) {
		CliUi.Cli();
	}
}