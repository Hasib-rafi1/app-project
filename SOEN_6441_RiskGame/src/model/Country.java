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
    private ArrayList<Country> neighboursOfCountry= new ArrayList<>();


    /**
     * The Constructor is created to set the all parameters of the country Element
     * @param countryId
     * @param countryName
     * @param xCoordinate
     * @param yCoordinate
     */
    Country (int countryId, String countryName, int xCoordinate, int yCoordinate)
    {
        this.countryId = countryId;
        this.countryName = countryName;
        this.xCoordinate =xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    /**
     * Gets an integer value of the country which is set as Country ID
     * @return the country ID Integer
     */
    public int getCountryId()
    {
        return countryId;
    }

    /**
     * Gets the name of the Country
     * @return the country Name String
     */
    public String getCountryName()
    {
        return countryName;
    }

    /**
     * Gets the X-coordinate of the corresponding Country
     * @return the integer value of x coordinate
     */
    public int getxCoordinate()
    {
        return xCoordinate;
    }

    /**
     * Gets the Y-coordinate of the corresponding Country
     * @return the integer value of y coordinate
     */
    public int getyCoordinate()
    {
        return yCoordinate;
    }

    /**
     * Gets an integer value of the continent which is set as Country ID
     * @return the continent ID.
     */
    public int getContinentID() {
        return continentID;
    }

    /**
     * Sets an integer value of the continent which is set as Country ID
     */
    public void setContinentID(int continentID) {
        this.continentID = continentID;
    }

    /**
     * Gets all the neighbours that a country has as an Array list
     * @return arrayList of the neighbouring country
     */
    public ArrayList<Country> getNeighboursOfCountry() {
        return neighboursOfCountry;
    }

    /**
     * From the country-neighbour HashMap adds the neighbours to the country individually
     * @param neighbour
     */
    public void addNeighboursToTheCountries(Country neighbour){
        neighboursOfCountry.add(neighbour);

    }
}
