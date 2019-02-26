
package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import helper.PrintConsoleAndUserInput;
import model.Continent;
import model.Country;
import model.MapModel;
import views.MapView;

/**
 * This class is used to handle the operations to generate, edit the map.
 * @author Gargi Sharma
 * @author Zakiya Jafrin
 * @version 1.0.0
 */
public class MapController {
	Scanner scanner = new Scanner(System.in);
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	MapView mapView = new MapView();
	MapModel mapModel = new MapModel();
	MainMenu mainMenu = new MainMenu();

	// It stores all the continents of map File in this list
	ArrayList<Continent> continentsList = new ArrayList<>();


	/**
	 * 
	 * This method is used to select the map options(like import, design a new map, save a map).
	 *
	 * @return userinput
	 */
	public boolean generateMap() {
		int selectMapMenuOption = 0;

		while (selectMapMenuOption != 3){		
			selectMapMenuOption = mapView.displayMapMenu();
			switch (selectMapMenuOption) {	

			case 1: // IMPORT MAP FILE				
				listofMapsinDirectory();			
				String mapPath = mapModel.getMapNameByUserInput();								

				// check if the entered map file name is exists in directory or not
				File tempFile = new File(mapPath);				
				boolean exists = tempFile.exists();
				if (exists) {
					// Read map file by selecting the map name
					mapModel.readMapFile(mapPath);	
					// printing map values

					mapModel.printingContinents();   // this method print continents
					mapModel.printingTerritoriesAndNeighborCountries(); // this method print territories
					mapModel.printNeighboursGivenContry(); //prints the neighbours of a country given a country name
				} else {
					print.consoleErr("File not found!!!. Please enter the coreect name of map.");
				}						

				break;

			case 2:	// Create and save user Map
				createAndSaveUserMap();
				break;

			case 3: // Edit map
				editMap();
				break;

			
			case 4: // back to main menu

				mainMenu.displaymainMenu();
				break;
			}	

			while (selectMapMenuOption < 1 || selectMapMenuOption > 6) {			
				print.consoleErr("Error!!! Enter a valid choice (1-6).");			
				selectMapMenuOption = mapView.displayMapMenu();
			}
		}
		return false; 

	}


	/**
	 * This method is used to create the user map and save it in directory.
	 *	
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
					print.consoleOut("Map has been created successfully in directory!");
				} else {
					print.consoleErr("Error!!!! Map has not been created successfully!");					
				}
				mapView.closeFrameWindow();
			}
		});
	}

	/**
	 * This method is used to check if the entered map file name is exists in directory or not
	 */
	public void checkMapFileExists() {	
		String mapPath = mapModel.getMapNameByUserInput();
		File tempFile = new File(mapPath);				
		boolean exists = tempFile.exists();
		if (exists) {
			mapModel.readMapFile(mapPath);
		} else {
			print.consoleErr("File not found!!!. Please enter the coreect name of map.");

		}
	}


	/**
	 * @author Gargi Sharma
	 * @version 1.0.0
	 * This method is used to edit the map.
	 */
	public void editMap() {	
		// Printing all the map files	
		listofMapsinDirectory();

		// Select map name by user and check file exists or not
		print.consoleOut("Please enter the map name you want to edit from the list?");	
		checkMapFileExists();
		
		int inputForEditMap = -1;
		while (inputForEditMap != 1) {
			
			// Check if the map is valid according to risk rules
			boolean checkValidationOfMap = mapModel.checkMapIsValid();
			inputForEditMap = mapView.editMapMenu();
			
			switch (inputForEditMap) {
			case 1:  // 1. Add Continent to the map?
				mapModel.printingContinents();
				mapModel.addContinentNameToMapFile();
				
				mapModel.saveEditedMap();
				
				/* if(checkValidationOfMap){
					 mapModel.saveEditedMap();
                    print.consoleOut("Continent has been added successfully!");
                 }else{
                	 print.consoleErr("Invalid Map! Try again!!!");
                 }*/

				break;
			case 2:  // 2. Add Country to the map?
				
				//mapModel.printingContinents();
				//print.consoleOut("**************LIST OF CONTINENTS*****************\r\n");	
				int i=1;
				   int continentID = 0;
				for (Continent continentInformation : continentsList) {
					continentID = continentInformation.getContinentID();
					String continentName = continentInformation.getContinentName();
					int controlValue = continentInformation.getControlValue();	
					
					print.consoleOut(i+"."+continentName+"="+ controlValue+"\r\n");
					i++;
				}
				print.consoleOut("");	
				print.consoleOut("Enter the Continent Name from the list in which you want to add new country?");
                String continentName = scanner.nextLine();
				mapModel.addCountryToContinentInMap(continentName,continentID);

				 mapModel.saveEditedMap();
				/* if(checkValidationOfMap){
					 mapModel.saveEditedMap();
                    print.consoleOut("Country has been added successfully!");
                 }else{
                	 print.consoleErr("Invalid Map! Try again!!!");
                 }*/
				break;
			case 3:  // 3. Delete Continent from map?
				

				// printing list of continents 
				print.consoleOut("************************************************");
				print.consoleOut("Below is the list of Continents in selected map file:");
				ArrayList<Continent> continentList = mapModel.getContinentList();
				for (Continent nameOfContinent : continentList) {
					print.consoleOut(nameOfContinent.getContinentName());
				}			

				// Asking from the user to delete continent
				print.consoleOut("************************************************");
				print.consoleOut("Enter name of the Continent you want to delete:");
				String deleteContinentEnteredByUser = scanner.nextLine();
				
				 boolean isContinentDeleted = mapModel.deleteContinentFromMap(deleteContinentEnteredByUser);
				 if(isContinentDeleted){
					 print.consoleOut("Continent '"+deleteContinentEnteredByUser+"' is deleted successfuly!");
                }
                else {
                	print.consoleErr("Error!!Continent can not deleted");
                }
			    /*   if(isContinentDeleted){
	                    try{
	                        if (mapModel.checkMapIsValid()){
	                            mapModel.saveEditedMap();
	                          print.consoleOut("Continent '"+deleteContinentEnteredByUser+"' is deleted successfuly!");
	                        }
	                        else{
	                        	print.consoleOut("Map is invalid!");
	                        }
	                    }catch (  Exception e){
	                    	print.consoleOut(" Empty Map !");
	                    }
                   }
                   else {
                   	print.consoleErr("Error!!Continent can not deleted");
                   }*/

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


