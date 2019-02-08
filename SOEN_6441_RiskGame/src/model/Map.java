package model;


import helper.PrintConsoleAndUserInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Map {

	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	
	private String mapName = "World.map";
	private String mapDir = "SOEN_6441_RiskGame/src/mapFiles/";

	public Map(){}
	
	public Map(String mapName) {
		super();
		this.mapName = mapName;

	}

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
	

}
