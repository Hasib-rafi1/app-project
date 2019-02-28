package model;
import helper.Colors;
import java.util.ArrayList;

/**
 * This is a main class to read and store different elements of the country
 * @author Zakiya Jafrin
 * @author Jaiganesh
 * @version 1.0.0
 */
public class Country {
    private int countryId;
    private String countryName;
    private int continentID;
    private int xCoordinate;
    private int yCoordinate;
    private int playerId;
    private int armyNumbers;
    private Colors countryColor;


    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Colors getCountryColor() {
        return countryColor;
    }

    public void setCountryColor(Colors countryColor) {
        this.countryColor = countryColor;
    }

    private ArrayList<String> neighboursString = new ArrayList<>();
    private ArrayList<Country> neighboursOfCountry= new ArrayList<>();

    /**
     * The Constructor is created to set the all parameters of the country Element
     * @param countryId
     * @param countryName
     * @param xCoordinate
     * @param yCoordinate
     */


    public Country(int countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public Country (int countryId, String countryName, int xCoordinate, int yCoordinate){
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

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * This method is used to get the name of the Country.
     * @return the country Name String
     */
    public String getCountryName(){
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * This method is used to get the X-coordinate of the corresponding Country
     * @return the integer value of x coordinate
     */
    public int getxCoordinate(){
        return xCoordinate;
    }


    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * This method is used to get the Y-coordinate of the corresponding Country
     * @return the integer value of y coordinate
     */
    public int getyCoordinate() {
        return yCoordinate;
    }


    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
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
        if (this.neighboursString.contains(newNeighbour)) {
            // Do nothing
        } else {
            this.neighboursString.add(newNeighbour);
        }
    }

    public int getnoOfArmies() {
        return armyNumbers;
    }

    public void increaseArmyCount() {
        armyNumbers++;
    }

    public void increaseArmyCount(int count) {
        armyNumbers += count;
    }

    public void decreseArmyCount() {
        armyNumbers--;
    }

    public void decreaseArmyCount(int count) {
        armyNumbers -= count;
    }

}
