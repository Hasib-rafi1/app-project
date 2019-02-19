package model;

import java.util.ArrayList;

/**
 * This is a main class to read and store different elements of the continent
 * @author Zakiya Jafrin
 * @version 1.0.0
 */
public class Continent {

    private int continentID;
    private String continentName;
    private int controlValue;
    private ArrayList<Country> countriesOfTheContinent= new ArrayList<>();

    /**
     * The Constructor is created to set the all parameters of the continent Element
     * @param continentID
     * @param continentName
     * @param controlValue
     */
    Continent(int continentID, String continentName, int controlValue){
        this.continentID = continentID;
        this.continentName = continentName;
        this.controlValue = controlValue;
    }

    /**
     * Gets an integer value of the continent which is set as Continent ID
     * @return
     */
    public int getContinentID() {
        return continentID;
    }

    /**
     * Gets the name of the Continent as a String
     * @return name of the continent String
     */
    public String getContinentName() {
        return continentName;
    }

    /**
     * Gets the control Value of the Continent in the conquest Map
     * @return the Integer Control Value
     */
    public int getControlValue() {
        return controlValue;
    }

    /**
     * Takes all the countries as a parameter and list them in an array list under the Continent Name
     * @param country
     */
    public void addCountriesToTheContinentList(Country country) {
        this.countriesOfTheContinent.add(country);
    }
}
