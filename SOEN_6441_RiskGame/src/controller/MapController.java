package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import helper.PrintConsoleAndUserInput;
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
	 * This method is used to select the map options(like import, design a new map, save a map).
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
				print.consoleOut("Enter map name you want to import? ");				
				String mapNameByUserInput = scanner.nextLine().trim();
				String mapPath = mapNameByUserInput+".map";
			
//
				mapModel.importMapFile(print.getMapDir() + mapPath);
				
				break;
			
			case 2:// Display map				
				createUserMap();
				break;
			case 3:
				break;
			}			
			while (selectMapMenuOption < 1 || selectMapMenuOption > 6) {			
				System.err.println("Error: Enter a valid choice (1-6).");			
				//selectMapMenuOption = this.MapView.displayMapMenu();
			}
		}
		return false; 
		
	}
	
	private void createUserMap() {
		// TODO Auto-generated method stub
		mapView.createJframe();
		mapView.saveButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub				
				StringBuffer mapContent = new StringBuffer(mapView.returnMapContent());				
				String mapName = mapView.returnMapName();	
				boolean checkMapIsCreated;
				checkMapIsCreated = mapModel.saveUserMapIntoDirectory(mapContent, mapName);
				
			/*	if(mapName != null && !mapName.isEmpty()) {
					// write file into folder
					
				}else {
					print.consoleErr("Map is not created successfully!.Please save the name with filename");
				}*/


				/*if (checkMapIsCreated) {
					print.consoleOut("Map Created successfully!");
				} else {
					print.consoleErr("Map is not created successfully!");					
				}*/
				mapView.closeFrameWindow();
			}
		});
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
