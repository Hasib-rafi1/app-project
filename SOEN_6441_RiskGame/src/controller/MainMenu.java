package controller;

import java.util.Scanner;


/**
 * This is a class to display the main menu
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class MainMenu {
	/**
	 * This is the method for Displaying main menu for game. 
	 * This function is used to show the user input to create or edit the map, start, load the game
	 * and user can exit if he wants to exit the game.
	 * @return 
	 */
	public static int displaymainMenu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("=================================");
		System.out.println("\t Risk Game\t");		
		System.out.println("\n1.Map Generator");
		System.out.println("2.Start Game");
		System.out.println("=================================");	
		System.out.println(" Please Enter Your Choice(1-5): ");
		return scanner.nextInt();
	}

}
