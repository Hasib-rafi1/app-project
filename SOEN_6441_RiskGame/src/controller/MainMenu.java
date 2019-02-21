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
	 * @return userIntInput
	 */
	public int displaymainMenu() {
		print.consoleOut("=================================");
		print.consoleOut("\t Risk Game\t");
		print.consoleOut("1.Map Generator");
		print.consoleOut("2.Start Game");
		print.consoleOut("5.Exit Game");
		
		print.consoleOut("=================================");	
		print.consoleOut(" Please Enter Your Choice: ");
		return print.userIntInput();
	}

}
