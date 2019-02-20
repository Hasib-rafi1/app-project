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
	

	/**
	 * 
	 * This method is used to select the map options(like import, design a new map, save a map).
	 *
	 * @return userinput
	 */
	public boolean startMap() {
		int selectMapMenuOption = 0;
		MapModel map = null;

		while (selectMapMenuOption != 3){		
			selectMapMenuOption = mapView.displayMapMenu();
			switch (selectMapMenuOption) {	

			// import map with the user input map name
			case 1: 
				listofMapsinDirectory();	
				String mapDirectory = print.getMapDir();
				print.consoleOut("Enter map name you want to import? ");
				String mapPath = mapModel.getMapNameWithPath();					
				mapModel.importMapFile(mapDirectory + mapPath);				
				break;


			case 2:	
				createAndSaveUserMap();
				break;
			case 3:
				editMap();
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
					print.consoleOut("Map Created successfully!");
				} else {
					print.consoleErr("Map is not created successfully!");					
				}
				mapView.closeFrameWindow();
			}
		});
	}


	/**
	 * This method is used to edit the map.
	 * @author Gargi Sharma
	 * @version 1.0.0
	 */
	public void editMap() {
		// TODO Auto-generated method stub
		listofMapsinDirectory();	
		print.consoleOut("Enter map name you want to edit? ");
		String getMapNameInput = mapModel.getMapNameFromUser();
		
		int input = -1;
		while (input != 6) {		
			print.consoleOut("Edit Map Menu");
			print.consoleOut(" 1. Delete Continent");		
			print.consoleOut(" Enter option:");
			input = print.userIntInput();
			switch (input) {
			case 1:				
				
				
				ArrayList<Continent> continentList = mapModel.getContinentsList();
				print.consoleOut("case1--------------"+mapModel.getContinentsList());
				for (Continent nameOfContinent : continentList) {
					System.out.println(nameOfContinent+"---=====");
					//print.consoleOut("Continent List ->" +
						//	"" + nameOfContinent.getContinentName());
				}
			
				/*
				IOHelper.print("Enter name of the Continent you want to delete:");
				String continentToDelete = IOHelper.getNextString();
				map.deleteContinent(continentToDelete);
				IOHelper.print("Continent '" + continentToDelete + "' is deleted successfuly!");
				break;*/

			case 6:
				break;
			default:
				print.consoleErr("Option not Available. Select Again!");
				break;
			}
		}
	}
	
	

	/**
	 *  @author Zakiya Jafrin
	 *  @version 1.0.0
	 * This method is used to list the .map files from the directory as an Arraylist
	 *
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
			print.consoleOut(j + ") "+s);
			j++;
		}
		return mapFileList;

	}
}
