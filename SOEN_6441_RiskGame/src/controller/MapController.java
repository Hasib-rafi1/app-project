package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import helper.PrintConsoleAndUserInput;
import model.Continent;
import model.MapModel;
import views.MapView;

/**
 * This class is used to handle the operations to generate, edit the map.
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class MapController {
	Scanner scanner = new Scanner(System.in);
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	MapView mapView = new MapView();
	MapModel mapModel = new MapModel();
	MainMenu mainMenu = new MainMenu();


	ArrayList<Continent> continentsList = new ArrayList<>();

	/**
	 * 
	 * This method is used to select the map options(like import, design a new map, save a map).
	 *
	 * @return userinput
	 */
	
	public boolean startMap() {
		int selectMapMenuOption = 0;

		while (selectMapMenuOption != 3){		
			selectMapMenuOption = mapView.displayMapMenu();
			switch (selectMapMenuOption) {	

			// import map with the user input map name
			case 1: 
				listofMapsinDirectory();			
				String mapPath = mapModel.getMapNameByUserInput();
				mapModel = mapModel.readMapFile(mapPath);	

				/*	if (mapModel == null )
					print.consoleErr("Map is not valid based on the game rules.");
				else
					print.consoleOut("Map is imported successfully.");
				break;*/

			case 2:	
				createAndSaveUserMap();
				break;
			case 3:
				editMap();
				break;
			case 4:
				displayMap();
				break;
			case 5:

				//mainMenu.displaymainMenu();
				break;
			}	

			while (selectMapMenuOption < 1 || selectMapMenuOption > 6) {			
				System.err.println("Error: Enter a valid choice (1-6).");			
				//selectMapMenuOption = this.MapView.displayMapMenu();
			}
		}
		return false; 

	}

	/**
	 * This method is used to display the map
	 * @author Gargi Sharma
	 * @version 1.0.0
	 * 
	 */
	public  void displayMap() {		
		mapView.displayMapWindow();	
	}

	/**
	 * This method is used to create the user map and save it in directory.
	 *	@author Gargi Sharma
	 *  @version 1.0.0
	 */
	public void createAndSaveUserMap() {
		mapView.createJframe();
		mapView.saveButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub					
				StringBuffer mapContent = new StringBuffer(mapView.returnMapContent());				
				String mapName = mapView.returnMapName();	
				boolean checkMapIsCreated;
				checkMapIsCreated = mapModel.saveUserMapIntoDirectory(mapContent, mapName);

				if (checkMapIsCreated) {
					print.consoleOut("Map Created successfully in directory!");
				} else {
					print.consoleErr("Map is not created successfully!");					
				}
				mapView.closeFrameWindow();
			}
		});
	}


	/**
	 * @author Gargi Sharma
	 * @version 1.0.0
	 * This method is used to edit the map.
	 */
	public void editMap() {	
		// TODO Auto-generated method stub	

		listofMapsinDirectory();
		print.consoleOut("Please enter map name you want to edit from the list?");	
		String mapPath = mapModel.getMapNameByUserInput();	
		mapModel.readMapFile(mapPath);

		mapModel.checkMapIsValid();
		/*if (!mapModel.checkMapIsValid()) {
			print.consoleOut("Map is Invalid !");
		}else {
			print.consoleOut("valid");
		}*/


		int input = -1;
		while (input != 1) {
			print.consoleOut("=================================");
			print.consoleOut("\t Edit Map Menu\t");		
			print.consoleOut("1. Add Continent to the map?");
			print.consoleOut("2. Add Country to the map?");
			print.consoleOut("3. Delete Continent from the map?");
			print.consoleOut("4. Delete Country from the map?");
			print.consoleOut("5. Save the map?");
			print.consoleOut("6. Back to menu?");	
			print.consoleOut("=================================");	
			print.consoleOut(" Select number from above editing menu:");
			input = print.userIntInput();
			switch (input) {
			case 1:
				mapModel.addContinentNameToMapFile();
				
				break;
			case 2:
				mapModel.addCountryNameToMapFile();
				print.consoleOut("case2----------");
				break;
			case 3:
				// printing list of continents 
				print.consoleOut("=====================================================");
				print.consoleOut("Below is the list of Continents in selected map file:");
				ArrayList<Continent> continentList = mapModel.getContinentList();
				for (Continent nameOfContinent : continentList) {
					print.consoleOut(nameOfContinent.getContinentName());
				}			

				// Asking from the user to delete continent
				print.consoleOut("=====================================================");
				print.consoleOut("Enter name of the Continent you want to delete:");
				String deleteContinentEnteredByUser = scanner.nextLine();
				mapModel.deleteContinentFromMap(deleteContinentEnteredByUser);
				//print.consoleOut("=====================================================");
				//print.consoleOut("Continent '" + deleteContinentEnteredByUser + "' has been deleted !!!!!!");

			
				break;
			case 4:
				print.consoleOut("case2----------");
				break;
			case 5:
				print.consoleOut("case2----------");
				break;
			case 6:
				print.consoleOut("case6----------");
				break;
			default:
				print.consoleOut("Option not Available. Select Again!");
				break;
			}
		}

	}
	
	/**
	 * Gets The ContinentList form the map file
	 * @return the list of all map file
	 */
	public ArrayList<Continent> getContinentList() {
		return continentsList;
	}

	/**
	 * @author Zakiya Jafrin
	 * @version 1.0.0
	 * This method is used to list the .map files from the directory as an Arraylist
	 * @return mapFileList
	 */
	public ArrayList<String> listofMapsinDirectory(){
		ArrayList<String> mapFileList = new ArrayList<String>();
		File folder = new File(print.getMapDir());
		File[] listOfFiles = folder.listFiles();
		int i = 0, j = 1;
		for(File file : listOfFiles){		    
			if(file.isFile()){
				//System.out.println(file.getName());
				if (file.getName().toLowerCase().contains(".map")){
					mapFileList.add(listOfFiles[i].getName());
				}
			}
			i++;
		}
		print.consoleOut("\n"+ "The List of Maps is Given Below:-"+ "\n");
		for (String s : mapFileList) {
			print.consoleOut(j + "."+s);
			j++;
		}
		return mapFileList;
	}
	
	
	
}



