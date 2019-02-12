package model;
import helper.PrintConsoleAndUserInput;
import views.MapView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class MapModel {

	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	MapView mapView = new MapView();
	

	/**
	 * This method reads the existing map file from the directory 
	 *
	 */

	public void importMapFile(String mapPath) {
		try {
			File file = new File(mapPath);
			BufferedReader readFileFromDir = new BufferedReader(new FileReader(mapPath));
			String lineStream;

			while((lineStream = readFileFromDir.readLine()) != null){
				print.consoleOut(lineStream);
			}
		}
		catch (Exception e){
			System.out.print(e);
		}


		// TODO Auto-generated method stub

//			MapModel map = null;
//			ArrayList<String> mapFileText = new ArrayList<String>();
//
//
//			// read file
//			try {
//				BufferedReader reader = new BufferedReader(new FileReader(mapPath));
//				String readLine = "";
//				while ((readLine = reader.readLine()) != null) {
//					mapFileText.add(readLine);
//				}
//				reader.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			ArrayList<String> contentOfMapFile = mapFileText; // put text in an arraylist
//
//			return;
		}
	

	
	
	

}
