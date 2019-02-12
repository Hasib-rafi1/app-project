package views;

import helper.PrintConsoleAndUserInput;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.*; 
/**
 * This class is used to create a map. Also, this creates the window to show the map by using JFrame.
 * @author Gargi Sharma
 * @version 1.0.0
 */

public class MapView {
	
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();

	/**
	 * Create JFrame
	 */
	public static void createWindow( ){			
		JFrame mapWindow= new JFrame();
		mapWindow.setTitle("Generate Map");
		mapWindow.setVisible(true);
		mapWindow.setSize(1500,800);
		
		Container windowContent= mapWindow.getContentPane();
		windowContent.setBackground(Color.blue);		
		mapWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	/**
	 * @author Gargi sharma
	 * @version 1.0.0
	 * This method displays menu for map generator
	 * @param 
	 */
	public int displayMapMenu() {
		print.consoleOut("=================================");
		print.consoleOut("\t Map Generator menu\t");
		print.consoleOut("\n 1. Import Map From File");

	/*	System.out.println("\n 2. Design a New Map");
		System.out.println("\n 3. Edit The Map");
		System.out.println("\n 4. Save The Map");
		System.out.println("\n 5. Display The Map");
		System.out.println("\n 6. Back to The Main Menu");
		System.out.println("\n\n Please Enter Your Choice(1-6): ");*/


		print.consoleOut("=================================");
	
		return print.userIntInput();
	}

	

	
	

}
