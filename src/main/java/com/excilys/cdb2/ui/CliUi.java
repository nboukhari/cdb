package com.excilys.cdb2.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.excilys.cdb2.model.Computer;
import com.excilys.cdb2.model.ComputerBuilder;
import com.excilys.cdb2.service.*;

/**
 * This class is the CLI
 * @author Nassim BOUKHARI
 */
public class CliUi {
	

	public static final Scanner READER = new Scanner(System.in);
	
	public static void Cli(){
		boolean quit = false;
		do {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
			String caseNumber = READER.nextLine();

			switch(caseNumber) {
			case("0"):
				quit = true;
			break;
			case("1"):
				ComputerServices.showComputers();
			break;
			case("2"):
				CompanyServices.showCompanies();
			break;
			case("3"):
				ComputerServices.showComputerDetail();
			break;
			case("4"):
				ComputerServices.createComputer();
			break;
			case("5"):
				ComputerServices.modifyComputer();
			break;
			case("6"):
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
		System.out.println("Veuillez entrer la date d'arrêt du nouvel ordinateur à créer: ");
		discontinued = READER.nextLine();
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introduced);
		computerBuilder.setDiscontinued(discontinued);
		computer = computerBuilder.build();
		
		//Computer computer = new Computer(0,name,introduced,discontinued,0);
		//System.out.println(name+" "+introduced+" "+discontinued);
		
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
		System.out.println("Voici la nouvelle date du lancement de l'ordinateur: "+introduced+"\n");
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setIntroduced(introduced);
		computer = computerBuilder.build();
		
		return computer;
	}
	
	public static Computer updateDiscontinued(String discontinued) {
		
		System.out.println("Veuillez entrer la nouvelle date de l'arrêt de l'ordinateur (au format YYYY-mm-dd): ");
		discontinued = READER.nextLine();
		System.out.println("Voici la nouvelle date de l'arrêt de l'ordinateur: "+discontinued+"\n");
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setDiscontinued(discontinued);
		computer = computerBuilder.build();
		
		return computer;
	}
	
	public static void main(String[] args) {
		CliUi.Cli();
	}
}