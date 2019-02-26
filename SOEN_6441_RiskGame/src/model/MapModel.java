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
import java.util.Iterator;
import java.util.Scanner;




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

			/*	for (Continent nameOfContinent : getContinentsList()) {
				print.consoleOut("Continent List ->" +
						"" + nameOfContinent.getContinentName());
			}
			print.consoleOut("=============================");
			for (Country nameOfCountry : getCountryList()) {
				print.consoleOut("Country List ->" +
						"" + nameOfCountry.getCountryName());
			}
			print.consoleOut("=============================");*/
			readFileFromDir.close();
		}
		catch(IOException exception){
			System.out.println(exception);
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
	 * @version 1.0.0	
	 * @return false
	 */
	public boolean checkMapIsValid() {	
		// TODO Auto-generated method stub		
		return false;		
	}


	/**
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
	 * This method is used to take the user input of map name
	 * @return mapNameByUserInput, map name entered by the user
	 */
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







	/* ------------------------------add/edit/delete map functions start here -----------------------------*/



	/**
	 * This method is used to add new continent into the map when editing map operation is performed.
	 */
	public void addContinentNameToMapFile() {

		// Asks the number of CONTINENTS a user wants to add.
		print.consoleOut("How many continents you want to add in a Map?");
		int numberOfContinentsToAdd = print.userIntInput();


		// It increments the number of continents a user wants to add. 
		// Plus it asks to enter the continent names for each number a user has selected.
		for (int k = 0; k < numberOfContinentsToAdd; k++) {
			int incrementOfContinentNumber = k+1;
			print.consoleOut("Input the CONTINENT NAME for number " +incrementOfContinentNumber+ "continent and its caontrol value.");
			String continentNameByUser = scanner.nextLine();
			int controlValueByUser = print.userIntInput();
			// add to the continent list
			Continent addedcontinent = new Continent(k, continentNameByUser, controlValueByUser);		


			// Asks the number of COUNTRIES a user wants to add in a given continent. And eneter the x and y coordinates
			print.consoleOut("How many countries you want to create in"+continentNameByUser+" continent:\n");
			int numberOfCountriesByUser = print.userIntInput();
			for (int i = 0; i < numberOfCountriesByUser; i++) {

				int numberOfCountriesCounter = (i + 1);
				// Enter country name
				print.consoleOut("Input the country name for country number " + numberOfCountriesCounter);
				String countryNameByUser = scanner.nextLine();

				// Enter x coordinates of country name
				print.consoleOut("Input x coordinates for");
				int xCoordinateOfCountry = print.userIntInput();				

				// Enter y coordinates of country name
				print.consoleOut("Input y coordinates for");
				int yCoordinateOfCountry = print.userIntInput();					

				// put in a continent and set continent id
				Country country = new Country(i, countryNameByUser);
				country.setxCoordinate(xCoordinateOfCountry);
				country.setyCoordinate(yCoordinateOfCountry);
				country.setContinentID(i);


				// Enter the number of neighbor countries
				print.consoleOut("\nInput  the number of neighbor countries you want to add:\n");
				int numberOfNeighbourCountriesByUser = print.userIntInput();

				for (int j = 0; j < numberOfNeighbourCountriesByUser; j++) {
					int neighborCountriesCounter = (j + 1);
					// Enter the names of neighbor countries
					print.consoleOut("Input the country name for neighbor country number " + neighborCountriesCounter + "\n");
					String neighborCountryName = scanner.nextLine();

					// put the neighbor countries into the country
					country.addNeighborString(neighborCountryName);

					// get the country list as new list by ignoring the camel case letter. 
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
	 * This method is used to add the continent to the continent list.
	 * @param continent,  object of the continent
	 *           
	 */
	public void addContinent(Continent continent) {
		continentsList.add(continent);
	}


	/**
	 * This method is used to add country name to the continent.
	 * @param continentID 
	 * @param continentName 
	 * @return 
	 */
	public boolean addCountryToContinentInMap(String continentName, int continentID) {


		Continent listOfCurrentContinents  = continentsList.stream()
				.filter(x-> x.getContinentName().equalsIgnoreCase(continentName))
				.findAny()
				.orElse(null);



		if(listOfCurrentContinents == null){
			print.consoleErr("Error!!! Continent name does not Exist.");
			return false;
		}


		print.consoleOut("Input the number of Countries you want to create in this continent:\n");
		int numberOfCountriesToAddInContinent = scanner.nextInt();

		for (int i = 0; i < numberOfCountriesToAddInContinent; i++) {
			//	int incrementCountryCounter = (i + 1);
			print.consoleOut("Input the Country Name for country no: => ");
			String countryName = print.userStrInput();




			print.consoleOut("Input x coordinate:");
			int xCoordinate = scanner.nextInt();

			print.consoleOut("Input y coordinate:");
			int yCoordinate = scanner.nextInt();

			Country country = new Country(i, countryName, xCoordinate, yCoordinate);
			country.setContinentID(continentID);

			print.consoleOut("Input the number of neighbor countries you want to create?\n");
			int neighborCountries = scanner.nextInt();

			for (int k = 0; k < neighborCountries; k++) {
				int incrementCounterForNeighborCountries =  (k + 1);
				print.consoleOut("Input the country name for adjacency country number: " + incrementCounterForNeighborCountries);

				String neighbourName = scanner.nextLine();

				country.addNeighborString(neighbourName);


				for (Country countryList: getCountryList()) {
					if (countryList.getCountryName().equalsIgnoreCase(neighbourName)){
						countryList.addNeighborString(countryName);
					}
				}

			}
			//continent.addCountriesToTheContinentList(country);
		}
		return false;


	}

	/**
	 * @author Gargi Sharma
	 * @version 1.0.0	
	 * This method is used to delete the continent.
	 * @param deleteContinentEnteredByUser, name of the continent
	 * @return true
	 */
	public boolean deleteContinentFromMap(String deleteContinentEnteredByUser) {

		ArrayList<Country> countriesListOfCurrentContinent = new ArrayList<>();

		/*	for (Continent checkExistingContinents : continentsList) {
			System.out.println(checkExistingContinents.getContinentName()+"----");
			System.out.println(deleteContinentEnteredByUser+"******");
			if (checkExistingContinents.getContinentName().equals(deleteContinentEnteredByUser) ) {				
			} else {
				print.consoleErr("Error!!!"+deleteContinentEnteredByUser+" does not exists in selected map.");
				return false;
			}	

			countriesListOfCurrentContinent = checkExistingContinents.getCountryList();
		}*/
		Continent currentContinent = continentsList.stream()
				.filter(x-> x.getContinentName().equalsIgnoreCase(deleteContinentEnteredByUser))
				.findAny()
				.orElse(null);



		if(currentContinent==null){
			print.consoleOut("Continent name is invalid");
			return false;
		}
		//countriesListOfCurrentContinent = currentContinent.getCountryList();
		for ( Continent continent: continentsList){
			for (Country country : continent.getCountryList()) {
				for (int i = 0; i < country.getNeighboursString().size() ; i++) {
					String coutryNameToDelete = country.getNeighboursString().get(i);
					Country c = countriesListOfCurrentContinent.stream()
							.filter(x -> x.getCountryName().equalsIgnoreCase(coutryNameToDelete))
							.findAny().orElse(null);
					if (c!=null){
						country.getNeighboursString().remove(i);
					}
					else{
					}
				}
			}
		}
		continentsList.remove(currentContinent);

		return true;

	}

	public void saveEditedMap() {
		StringBuffer textContentInFile = new StringBuffer();

		// Add the [Continents] parameter in the text file
		textContentInFile.append("[Continents]\r\n");

		// Appending continent name with its control value
		for (Continent continentInformation : continentsList) {
			String continentName = continentInformation.getContinentName();
			int controlValue = continentInformation.getControlValue();			  
			textContentInFile.append(continentName+"="+ controlValue + "\r\n");
		}

		// Appending Country name and its coordinates with its neighbor countries
		textContentInFile.append("\n[Territories]\r\n");

		for (Continent continentInformation : continentsList) {
			for (Country countriesInformation : continentInformation.getCountryList()) {

				// fetching and putting the data into variables
				String countryName = countriesInformation.getCountryName();
				int xCoordinates = countriesInformation.getxCoordinate();
				int yCoordinates = countriesInformation.getyCoordinate();
				String continentName = continentInformation.getContinentName();

				// Append territories parameters
				textContentInFile.append(countryName + "," + xCoordinates+ "," + yCoordinates + "," + continentName+"\r\n");
				for (String neighborCountries : countriesInformation.getNeighboursString()) {
					textContentInFile.append("," + neighborCountries);
				}
				textContentInFile.append("\r\n");
			}
		}
		/*	BufferedWriter bw = null;
		try {

			File filePath = new File(print.getMapDir()+".map");
			if (!filePath.exists()) {
				filePath.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(filePath);
			bw = new BufferedWriter(fileWriter);
			bw.write(new String(textContentInFile));

		} catch (IOException ioe) {
			ioe.printStackTrace();

		}
		finally{ 
			try{
				if(bw!=null)
					bw.close();
			}catch(Exception ex){
				print.consoleErr("Error in closing the BufferedWriter"+ex);
			}
		}*/	
		System.out.println(textContentInFile);
	}

	/**
	 * his method is printing continents
	 */
	public void printingContinents() {
		print.consoleOut("**************LIST OF CONTINENTS*****************\n");	
		int i=1;
		for (Continent continentInformation : continentsList) {
			int continentID = continentInformation.getContinentID();
			String continentName = continentInformation.getContinentName();
			int controlValue = continentInformation.getControlValue();	

			print.consoleOut(i+"."+continentName+"="+ controlValue+"\n");
			i++;
		}
	}

	/**
	 * This method is printing territories and its neighbors.
	 */
	public void printingTerritoriesAndNeighborCountries() {
		// Printing the territories
		print.consoleOut("[Territories]");	
		for (Continent continentInformation : continentsList) {
			for (Country countriesInformation : continentInformation.getCountryList()) {			
				// fetching and putting the data into variables
				String countryName = countriesInformation.getCountryName();
				int xCoordinates = countriesInformation.getxCoordinate();
				int yCoordinates = countriesInformation.getyCoordinate();
				String continentName = countriesInformation.getCountryName();

				// Append territories parameters
				print.consoleOut(countryName + "," + xCoordinates+ "," + yCoordinates + "," + continentName);
			}
		} 
	}
	
	public String getMapDir() {
		return print.getMapDir();
	}
}

