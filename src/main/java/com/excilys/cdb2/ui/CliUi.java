package com.excilys.cdb2.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
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

	public static void Cli() throws IOException{

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
			String nbCh = "0";
			try {
				ChooseUi choose = ChooseUi.values()[Integer.valueOf(enterNbChoice(nbCh))];
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
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Je n'ai pas compris votre requête, veuillez recommencer. (MENU)");
			}
		}while(!quit);
		READER.close();
		System.out.println("Au revoir.");
	}

	public static int enterNbChoice(String nbCh) {
		nbCh = READER.nextLine();
		int intCh = Integer.parseInt(nbCh);
		return intCh;


	}
	
	public static int numberOfPage(String Page) {
		System.out.println("Quelle est la page que vous souhaitez afficher? (Appuyez sur 0 si vous souhaitez quitter)");
		Page = READER.nextLine();
		int NbP = Integer.parseInt(Page)*10;
		return NbP;
	}
	
	public static long enterId() {

		System.out.println("Veuillez entrer l'id de l'ordinateur: ");
		String idPC = READER.nextLine();
		int intId = Integer.parseInt(idPC);

		return intId;
	}

	public static String enterName() {

		System.out.println("Veuillez entrer le nom de l'ordinateur: ");
		String name = READER.nextLine();

		return name;
	}

	public static Optional<LocalDate> enterDateDebut() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Optional<LocalDate> dateToLocalDate = Optional.empty();
		System.out.println("Veuillez entrer la date de lancement de l'ordinateur (format YYYY-mm-dd): ");
		String date = READER.nextLine();
		LocalDate ParseDate = LocalDate.parse(date,format);
		 dateToLocalDate = Optional.ofNullable(ParseDate);

		return dateToLocalDate;
	}

	public static Optional<LocalDate> enterDateEnd() {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Optional<LocalDate> dateToLocalDate = Optional.empty();
		System.out.println("Veuillez entrer la date d'arrêt de l'ordinateur (format YYYY-mm-dd): ");
		String date = READER.nextLine();
		LocalDate ParseDate = LocalDate.parse(date,format);
		dateToLocalDate = Optional.ofNullable(ParseDate);

		return dateToLocalDate;
	}

	public static Computer enterIdPC(String idPC) {

		System.out.println("Veuillez entrer l'id de l'ordinateur: ");
		idPC = READER.nextLine();
		int intId = Integer.parseInt(idPC);
		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(intId);
		computer = computerBuilder.build();

		return computer;
	}

	public static Computer createPC(String name,Optional<LocalDate> introduced,Optional<LocalDate> discontinued) {

		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introduced);
		computerBuilder.setDiscontinued(discontinued);
		computer = computerBuilder.build();

		return computer;
	}

	public static Computer updateName(String name) {

		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setName(name);
		computer = computerBuilder.build();

		return computer;

	}

	public static Computer updateIntroduced(Optional<LocalDate> introduced) {

		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setIntroduced(introduced);
		computer = computerBuilder.build();

		return computer;
	}

	public static Computer updateDiscontinued(Optional<LocalDate> discontinued) {

		Computer computer;
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setDiscontinued(discontinued);
		computer = computerBuilder.build();

		return computer;
	}

	public static void main(String[] args) throws IOException {
		CliUi.Cli();
	}
}