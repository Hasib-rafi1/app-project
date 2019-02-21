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
				String mapPath = mapModel.getMapNameByUserInput();
				mapModel.importMapFile(mapPath);				
				break;
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

				mainMenu.displaymainMenu();
				break;
			}	

			while (selectMapMenuOption < 1 || selectMapMenuOption > 6) {			
				System.err.println("Error: Enter a valid choice (1-6).");			
				//selectMapMenuOption = this.MapView.displayMapMenu();
			}
		}
		return false; 

	}


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
	 * This method is used to edit the map.
	 */
	public void editMap() {
		// TODO Auto-generated method stub	
		System.out.println("--------------------");
		ArrayList<String> mapList = listofMapsinDirectory();
		int i = 1;
		for (String nameOfMap : mapList) {			
			print.consoleOut(i + "." +nameOfMap);		
			i++;
		}
		print.consoleOut("Enter map name that you want to edit from above list:");

		mapModel.getMapNameFromUser();

		/*//print the continent List to check if it is working

		print.consoleOut("start--------------------------");
		for (Continent nameOfContinent : mapModel.getContinentList()) {
		print.consoleOut("Continent List ->" +"" + nameOfContinent.getContinentName());
		}

		print.consoleOut("end--------------------------");
		 */





	}

	/**
	 *  @author Zakiya Jafrin
	 *  @version 1.0.0
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



