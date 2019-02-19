package model;
import helper.PrintConsoleAndUserInput;
import views.MapView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;


public class MapModel {

	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	MapView mapView = new MapView();
	

	/**
	 * This method reads the existing map file from the directory 
	 *
	 */

	public void importMapFile(String mapPath) {
		try {
			boolean getContinents = false;
			boolean getTerritories = false;
			int continentID= 0;
			int territoryID = 0;

			HashMap<String, Country> territoriesOfContinent= new HashMap<>();
			HashMap<Country, String[]> neighboursOfCountry = new HashMap<>();

//			File file = new File(mapPath);
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

				}


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
