package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

import helper.PrintConsoleAndUserInput;
import model.Map;

import views.MapView;

/**
 * This class is used to handle the operations to generate, edit the map.
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class GameController {

	/**
	 *  @author Zakiya Jafrin
	 *  @version 1.0.0
	 * This method is used to list the .map files from the directory as an Arraylist
	 *
	 */
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	public ArrayList<String> listofMapsinDirectory(){
		ArrayList<String> mapFileList = new ArrayList<String>();
		File folder = new File("SOEN_6441_RiskGame/src/mapFiles/");
		File[] listOfFiles = folder.listFiles();
		int i = 0, j = 1;
		for(File file : listOfFiles){
			if(file.isFile()){
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


//		for (int i = 0; i < listOfFiles.length; i++) {
//			if (listOfFiles[i].isFile()) {
//				if (listOfFiles[i].getName().toLowerCase().contains(".map"))
//					mapFileList.add(listOfFiles[i].getName());
//			}
//		}
// 		print.consoleOut(Arrays.toString(mapFileList.toArray()));
	}



	/**
	 * This function is used to create a map.
	 * 
	 */
	public static void generateMap() {		
		MapView showMapView = new MapView();   // calling the view
		showMapView.createJFrame();
	
	}


	public static void editMap() {
		// TODO Auto-generated method stub
		System.out.println("case2 ");

	}

	public static void startGame() {
		// TODO Auto-generated method stub
		System.out.println("case3 ");		
	}

	public static void loadGame() {
		// TODO Auto-generated method stub
		System.out.println("case4 ");		
	}



}
