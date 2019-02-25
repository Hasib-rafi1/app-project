package model;

import java.util.ArrayList;

/**
 * This is a main class to read and store different elements of the country
 * @author Zakiya Jafrin
 * @version 1.0.0
 */
public class Country {
    private int countryId;
    private String countryName;
    private int xCoordinate;
    private int yCoordinate;
    private int continentID;
   
    private ArrayList<String> neighboursString = new ArrayList<>();
    private ArrayList<Country> neighboursOfCountry= new ArrayList<>();

    /**
     * The Constructor is created to set the all parameters of the country Element
     * @param countryId
     * @param countryName
     * @param xCoordinate
     * @param yCoordinate
     */
    Country (int countryId, String countryName, int xCoordinate, int yCoordinate){
        this.countryId = countryId;
        this.countryName = countryName;
        this.xCoordinate =xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * This method is used to get an integer value of the country which is set as Country ID
     * @return the country ID Integer
     */
    public int getCountryId(){
        return countryId;
    }

    /**
     * This method is used to get the name of the Country.
     * @return the country Name String
     */
    public String getCountryName(){
        return countryName;
    }

    /**
     * This method is used to get the X-coordinate of the corresponding Country
     * @return the integer value of x coordinate
     */
    public int getxCoordinate(){
        return xCoordinate;
    }

    /**
     * This method is used to get the Y-coordinate of the corresponding Country
     * @return the integer value of y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }

    /**
     * This method is used to get an integer value of the continent which is set as Country ID
     * @return the continent ID.
     */
    public int getContinentID() {
        return continentID;
    }

    /**
     * This method is used to sets an integer value of the continent which is set as Country ID
     * @param continentID, ID of the continent
     */
    public void setContinentID(int continentID) {
    	this.continentID = continentID;
    }

    /**
     * This method is used to get all the neighbours that a country has as an Array list
     * @return arrayList of the neighbouring country
     */
    public ArrayList<Country> getNeighboursOfCountry() {
        return neighboursOfCountry;
    }
    
    /**
     * From the country-neighbour HashMap adds the neighbours to the country individually
     * @param neighbour, add Neighbours to Countries
     */
    public void addNeighboursToTheCountries(Country neighbour){
        neighboursOfCountry.add(neighbour);
    }
       
    
    /**
	 * This method is used to get the list of neighbour Strings
	 * 
	 * @return neighboursString
	 */
	public ArrayList<String> getNeighboursString() {
		return neighboursString;
	}
	
	/**
	 * This method is used to add name for neighbour string
	 * 
	 * @param newNeighbour, String
	 *           
	 */
	public void addNeighborString(String newNeighbour) {
		if (!this.neighboursString.contains(newNeighbour)) {
			this.neighboursString.add(newNeighbour);
		}
	}  
    
}
