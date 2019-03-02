package controller;

import controller.MainMenu;
import controller.MapController;
import helper.PrintConsoleAndUserInput;


/**
 * This is a main class to run the game
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class MainController {
	/**
	 * This is a main method to run the game.
	 * This function is used to enter the user input and call the functions to create or edit the map, start, load the game
	 * and user can exit if he wants to exit the game.
	 * This function also displays the error message to select valid user input.
	 * @param args, arguments passed in the main method of class
	 *
	 */
	public static void main(String[] args) {

		MainMenu mainMenu = new MainMenu();
		MapController mapController = new MapController();
		PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
		GameController gameController = new GameController();

		int selectMainMenuOption = 0;
		boolean checkMapStatus = false;
		do {
			selectMainMenuOption = mainMenu.displaymainMenu();
			switch (selectMainMenuOption)
			{
			case 1:
				mapController.generateMap();
				break;
			case 2:
				gameController.initializeMap();
				gameController.initializeGame();
				break;
			case 3:
				print.consoleErr("Thanks for playing this Game.");
				System.exit(0);
			default :
				System.err.println("\n\t Error! Select option from the menu list (1 to 5):");
				break;
			}

		}
		while (selectMainMenuOption != 5);
		//System.exit(0);
	}
}
