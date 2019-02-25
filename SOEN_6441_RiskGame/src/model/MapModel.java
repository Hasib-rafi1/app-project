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
 * @author Zakiya Jafrin
 * @version 1.0.0
 */
public class MapModel {

	Scanner scanner = new Scanner(System.in);

	//Making the objects of classes
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	MapView mapView = new MapView();

	// Arraylist of countries, continents
	ArrayList<Continent> continentsList = new ArrayList<>();	


	/**
	 * This method is used to import the existing file from the directory. It reads the map file and stores the
	 * corresponding values for countries and continents.
	 * @param mapPath, map directory path
	 * @return map
	 */
	public MapModel readMapFile(String mapPath) {
		
		MapModel map = null;
		if (mapPath.isEmpty())
			return map;
		map = new MapModel();
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

				// Get continents and store them in continentList
				if (getContinents){
					String[] continentElements= lineStream.split("=");
					Continent continent = new Continent (continentID++, continentElements[0],
							Integer.parseInt(continentElements[1]));
					continentsList.add(continent);
					// printed it after while loop, we dont need it just to print
					//print the continent List to check if it is working
					//for the final project we do not need it here, this is just a showcase
					//also for iteration it prints the continents multiple times
				/*	for (Continent nameOfContinent : getContinentsList()) {
						print.consoleOut("Continent List ->" +
								"" + nameOfContinent.getContinentName());
					}*/
				}


				// Get Territories form the stream of sentences and store them country object with all of three values
				if (getTerritories){
					String[] territoryElements = lineStream.split(",");
					int xCoordinate = Integer.parseInt(territoryElements[1]);
					int yCoordinate = Integer.parseInt(territoryElements[2]);
					String belongsToContinent = territoryElements[3];

					Country country = new Country(territoryID++, territoryElements[0], xCoordinate, yCoordinate);
					territoriesOfContinent.put(belongsToContinent, country);
					String[] neighboursFromArray = Arrays.copyOfRange(territoryElements, 4, territoryElements.length);
					neighboursOfCountry.put(country, neighboursFromArray); // all the neighbours are put as an array they are not interconnected.

					//to list the countries depending on their continentName
					//ex: NorthAmerica: Alaska, Canada etc
					for (int i = 0; i < continentsList.size(); i++) {
						if (continentsList.get(i).getContinentName().equals(belongsToContinent)) {
							continentsList.get(i).addCountriesToTheContinentList(country);
							country.setContinentID(continentsList.get(i).getContinentID());
							break;
						}
					}
				
				}

				//in the array the neighbour and the country is NOT interconnected
				//here, we are getting the country name as Country object as key, and each of the neighbours
				// INdividually as string array. thus the country object is paired with neighbouring countries array
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
			
			for (Continent nameOfContinent : getContinentsList()) {
				print.consoleOut("Continent List ->" +
						"" + nameOfContinent.getContinentName());
			}
			print.consoleOut("=============================");
			for (Country nameOfCountry : getCountryList()) {
				print.consoleOut("Country List ->" +
						"" + nameOfCountry.getCountryName());
			}
			print.consoleOut("=============================");
			readFileFromDir.close();
		}
		catch (Exception e){
			//print.printException(e);
		}
		return map;

	}

	/**
	 * @author Gargi Sharma
	 * @version 1.0.0	
	 * @param mapContent, content of map file
	 * @param mapName, map name
	 * @return true if the map is valid and false if it is not valid
	 */
	public boolean createAndValidateMap(StringBuffer mapContent, String mapName) {	
		checkMapIsValid();
		if (checkMapIsValid()) {
			saveUserMapIntoDirectory(mapContent, mapName);
			return true;
		} else {
			System.out.println("in else condition of checkMapIsValid functin------");
			return false;
		}

	}

	/**
	 * This method is used to check whether the map is valid or not.
	 * @author Gargi Sharma
	 * @version 1.0.0	
	 * @return false
	 */
	public boolean checkMapIsValid() {	
		// TODO Auto-generated method stub		
		return false;		
	}


	/**
	 * @author Gargi Sharma
	 * @version 1.0.0
	 * This function is used to return the list of countries
	 * @return countriesList , list of countries
	 */
	public ArrayList<Country> getCountryList() {
		ArrayList<Country> countriesList = new ArrayList<>();
		for (Iterator<Continent> continents = continentsList.iterator(); continents.hasNext();) {
			countriesList.addAll(continents.next().getCountryList());
		}
		return countriesList;
	}


	/**
	 * @author Gargi Sharma
	 * @version 1.0.0	
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
	 * @version 1.0.0	
	 * This method is used to take the user input of map file with full directory path.
	 * @return mapPath
	 */
	public String getMapNameByUserInput() {		
		String mapDirectory = print.getMapDir();					
		String mapNameByUserInput = scanner.nextLine().trim();
		String mapPathWithMapName = mapDirectory + mapNameByUserInput;
		String mapPath = mapPathWithMapName+".map";	
		return mapPath;
	}

	/**
	 * @author Gargi Sharma
	 * @version 1.0.0	
	 * This method is used to take the user input of map name
	 * @return mapNameByUserInput, map name entered by the user
	 */
	public String getMapNameFromUser() {
		// TODO Auto-generated method stub						
		String mapNameByUserInput = scanner.nextLine().trim();
		return mapNameByUserInput;
	}


	/**
	 * @author Gargi Sharma
	 * @version 1.0.0	
	 * Gets The ContinentList form the map file
	 * @return the list of all map file
	 */
	public ArrayList<Continent> getContinentList() {
		return continentsList;
	}

	/**
	 * @author Gargi Sharma
	 * @version 1.0.0	
	 * This method is used to delete the continent.
	 * @param deleteContinentEnteredByUser, name of the continent
	 * @return true
	 */
	public boolean deleteContinentFromMap(String deleteContinentEnteredByUser) {
		return false;
		// TODO Auto-generated method stub

	}

	/**
	 * This method is used to add the continent to the continent list.
	 * @param continent,  object of the continent
	 *           
	 */
	public void addContinent(Continent continent) {
		continentsList.add(continent);
	}

	/**
	 * This method is used to allow user to to edit map and add new continent into the map.
	 */
	public void addContinentNameToMapFile() {
		
		// Asks the number of CONTINENTS a user wants to add.
		print.consoleOut("How many continents you want to add in a Map?");
		int numberOfContinentsToAdd = print.userIntInput();
		
	
		// It increments the number of continents a user wants to add. 
		// Plus it asks to enter the continent names for each number a user has selected.
		for (int k = 0; k < numberOfContinentsToAdd; k++) {
			int incrementOfContinentNumber = k+1;
			print.consoleOut("Enter CONTINENT NAME for number " +incrementOfContinentNumber+ "continent and enter the control value in next line.");
			String continentNameByUser = scanner.nextLine();
			int controlValueByUser = print.userIntInput();
			// add to the continent list
			Continent addedcontinent = new Continent(k, continentNameByUser, controlValueByUser);		
			
			
			// Asks the number of COUNTRIES a user wants to add in a given continent. And eneter the x and y coordinates
			print.consoleOut("How many countries you want to create in"+continentNameByUser+" continent:\n");
			int numberOfCountriesByUser = print.userIntInput();
			for (int i = 0; i < numberOfCountriesByUser; i++) {
				
				// Enter country name
				print.consoleOut("Enter country name for country number " + (i + 1));
				String countryNameByUser = scanner.nextLine();
				
				// Enter x coordinates of country name
				print.consoleOut("Enter x coordinates for");
				int xCoordinateOfCountry = print.userIntInput();				
				
				// Enter y coordinates of country name
				print.consoleOut("Enter y coordinates for");
				int yCoordinateOfCountry = print.userIntInput();					
				
				// put in a continent and set continent id
				Country country = new Country(i, countryNameByUser, yCoordinateOfCountry, yCoordinateOfCountry);
				country.setContinentID(k);
				
				// Enter the number of neighbor countries
				print.consoleOut("\nEnter the number of neighbor countries you want to add:\n");
	                int numberOfNeighbourCountriesByUser = print.userIntInput();
	                
	                for (int k1 = 0; k1 < numberOfNeighbourCountriesByUser; k1++) {
	                	// Enter the names of neighbor countries
	                	print.consoleOut("Enter country name for neighbor country number " + (k1 + 1) + "\n");
	                    String neighborCountryName = scanner.nextLine();
	                    
	                    // put the neighbor countries into the country
	                    country.addNeighborString(neighborCountryName);
	                    
	                    // get the country list as new list by comparing the camel case letter. 
	                    // Then add the neighbor countries
	                    for (Country newList: getCountryList()) {
	                        if (newList.getCountryName().equalsIgnoreCase(neighborCountryName)){
	                        	newList.addNeighborString(continentNameByUser);
	                        }
	                    }
	                }
	               
	                // It add a COUNTRY to the continent
	                addedcontinent.addCountriesToTheContinentList(country);
	              
			}
			
			//It adds the continent which is entered by user
			addContinent(addedcontinent);
			
		}
			    
	}

	/**
	 * This method is used to add country name to the list.
	 */
	public void addCountryNameToMapFile() {
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Continent> getContinentsList() {
		return continentsList;
	}
}
