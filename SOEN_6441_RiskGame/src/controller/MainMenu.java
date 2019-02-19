package controller;

import helper.PrintConsoleAndUserInput;

import java.util.Scanner;


/**
 * This is a class to display the main menu
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class MainMenu {
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	/**
	 * This is the method for Displaying main menu for game. 
	 * This function is used to show the user input to create or edit the map, start, load the game
	 * and user can exit if he wants to exit the game.
	 * @return 
	 */
	public int displaymainMenu() {
		print.consoleOut("=================================");
		print.consoleOut("\t Risk Game\t");
		System.out.println("1.Map Generator");
		System.out.println("2.Start Game");
		System.out.println("=================================");	
		System.out.println(" Please Enter Your Choice: ");
		return print.userIntInput();
	}

}
