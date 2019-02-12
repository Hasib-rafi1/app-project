package model;
import helper.PrintConsoleAndUserInput;
import views.MapView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;


public class MapModel {
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	public MapView MapView;
	
	public MapModel() {
		this.MapView = new MapView();		
	}
	
	
	private String mapName = "World.map";
	private String mapDir = "SOEN_6441_RiskGame/src/mapFiles/";



	/**
	 * @author Zakiya Jafrin
	 * @version 1.0.0
	 * This method reads the existing map file from the directory or the user created map file
	 * The extension of the file is .map
	 */
	public void readMapFile(){
		 		
		try {
			File file = new File(mapDir + mapName);
			BufferedReader readFileFromDir = new BufferedReader(new FileReader(file));
			String lineStream;

			while((lineStream = readFileFromDir.readLine()) != null){
				print.consoleOut(lineStream);
			}
			}
		catch (Exception e){
			System.out.print(e);
		}
	}
	
	/**
	 * @author Gargi Sharma
	 * @version 1.0.0
	 * This method reads the existing map file from the directory 
	 *
	 */

	public void importMapFile(String mapPath) {
		// TODO Auto-generated method stub
		
			MapModel map = null;
			ArrayList<String> mapFileText = new ArrayList<String>();

			
			// read file
			try {
				BufferedReader reader = new BufferedReader(new FileReader(mapPath));
				String readLine = "";
				while ((readLine = reader.readLine()) != null) {
					mapFileText.add(readLine);
				}
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ArrayList<String> contentOfMapFile = mapFileText; // put text in an arraylist		
		
			return;
		}
	

	
	
	

}
