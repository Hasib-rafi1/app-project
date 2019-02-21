package model;
import helper.PrintConsoleAndUserInput;
import views.MapView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;



/**
 * This Class is to read and Validate the created or existing Map file according to the requirement
 *  @author Zakiya Jafrin
 * @version 1.0.0
 */
public class MapModel {
	Scanner scanner = new Scanner(System.in);
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	MapView mapView = new MapView();
	ArrayList<Continent> continentsList = new ArrayList<>();
	
	/**
	 * This method is used to import the existing file from the directory. It reads the map file and stores the
	 * corresponding values for countries and continents.
	 * @param mapPath, map directory path
	 */
	public void importMapFile(String mapPath) {
		try {
			boolean getContinents = false;
			boolean getTerritories = false;
			int continentID= 0;
			int territoryID = 0;

			HashMap<String, Country> territoriesOfContinent= new HashMap<>();
			HashMap<Country, String[]> neighboursOfCountry = new HashMap<>();

			BufferedReader readFileFromDir = new BufferedReader(new FileReader(mapPath));
			String lineStream;
			while((lineStream = readFileFromDir.readLine()) != null){
				if (lineStream.trim().length() == 0)
					continue;
				else if (lineStream.contains("[Continents]")) {
					getContinents = true;
					continue;
				} else if (lineStream.contains("[Territories]")){
					getContinents = false;
					getTerritories = true;
					continue;
				}

				if (getContinents){
					String[] continentElements= lineStream.split("=");
					Continent continent = new Continent (continentID++, continentElements[0],
							Integer.parseInt(continentElements[1]));
					continentsList.add(continent);

					//print the continent List to check if it is working
					for (Continent nameOfContinent : getContinentList()) {
						print.consoleOut("Continent List ->" +
								"" + nameOfContinent.getContinentName());
					}
				}

				if (getTerritories){
					String[] territoryElements = lineStream.split(",");
					int xCoordinate = Integer.parseInt(territoryElements[1]);
					int yCoordinate = Integer.parseInt(territoryElements[2]);
					String belongsToContinent = territoryElements[3];

					Country country = new Country(territoryID++, territoryElements[0], xCoordinate, yCoordinate);
					territoriesOfContinent.put(belongsToContinent, country);
					String[] neighboursFromArray = Arrays.copyOfRange(territoryElements, 4, territoryElements.length);
					neighboursOfCountry.put(country, neighboursFromArray);

					for (int i = 0; i < continentsList.size(); i++) {
						if (continentsList.get(i).getContinentName().equals(belongsToContinent)) {
							continentsList.get(i).addCountriesToTheContinentList(country);
							country.setContinentID(continentsList.get(i).getContinentID());
							break;
						}
					}

				}

				Country neighbours = null;
				for (HashMap.Entry<Country, String[]> countryNeighbourPair : neighboursOfCountry.entrySet()) {
					Country countryOfPair = countryNeighbourPair.getKey();
					String[] neighbourOfPair = countryNeighbourPair.getValue();
					for (int i = 0; i < neighbourOfPair.length; i++) {
						neighbours = territoriesOfContinent.get(neighbourOfPair[i]);
						countryOfPair.addNeighboursToTheCountries(neighbours);
					}
				}
			}
			readFileFromDir.close();
		}
		catch (Exception e){
			print.printException(e);
		}

	}


	/**
	 * @author Gargi sharma
	 * @param mapContent, content of map file
	 * @param mapName, map name
	 * @return true if the map is valid and false if it is not valid
	 */
	public boolean createAndValidateMap(StringBuffer mapContent, String mapName) {	
		checkMapIsValid();
		if (checkMapIsValid()) {
			System.out.println("in if condition of checkMapIsValid functin------");
			//this.saveUserMapIntoDirectory(mapContent, mapName);
			return true;
		} else {
			System.out.println("in else condition of checkMapIsValid functin------");
			return false;
		}


	}



	private boolean checkMapIsValid() {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 *  @author Gargi sharma
	 * This method is used to save the user map into mapFiles folder.
	 * @param mapContent, content of the map file
	 * @param mapName, name of the map
	 * @return true if file is created 
	 */
	public boolean saveUserMapIntoDirectory(StringBuffer mapContent, String mapName) {
		BufferedWriter bw = null;
		try {

			File filePath = new File(print.getMapDir() + mapName + ".map");
			if (!filePath.exists()) {
				filePath.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(filePath);
			bw = new BufferedWriter(fileWriter);
			bw.write(System.getProperty( "line.separator" ));
			bw.write(new String(mapContent));
			return true;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return false;
		}
		finally{ 
			try{
				if(bw!=null)
					bw.close();
			}catch(Exception ex){
				print.consoleErr("Error in closing the BufferedWriter"+ex);
			}
		}	
	}
	/**
	 * @author Gargi Sharma
	 * This method is used to take the user input of map file
	 * @return mapPath
	 */
	public String getMapNameByUserInput() {		
		String mapDirectory = print.getMapDir();					
		String mapNameByUserInput = scanner.nextLine().trim();
		String mapPathWithMapName = mapDirectory + mapNameByUserInput;
		String mapPath = mapPathWithMapName+".map";	
		return mapPath;

	}

	public String getMapNameFromUser() {
		// TODO Auto-generated method stub						
		String mapNameByUserInput = scanner.nextLine().trim();
		return mapNameByUserInput;

	}
	/**
	 * Gets The ContinentList form the map file
	 * @return the list of all map file
	 */
	public ArrayList<Continent> getContinentList() {
		return continentsList;
	}
}
