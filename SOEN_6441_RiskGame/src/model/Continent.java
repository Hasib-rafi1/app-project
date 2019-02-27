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
	 * This method is used to get an integer value of the continent which is set as Continent ID
	 * @return continentID, ID of the continent.
	 */
	public int getContinentID() {
		return continentID;
	}


	/**
	 * This function sets the continent ID of the continent.
	 *
	 * @param continentID ,Id of the continent
	 */
	public void setContinentId(int continentID) {
		this.continentID = continentID;
	}
	/**
	 * This method is used to get the name of the Continent as a String
	 * @return continentName, name of the continent.
	 */
	public String getContinentName() {
		return continentName;
	}

	/**
	 * This method is used to get the control Value of the Continent in the conquest Map
	 * @return controlValue,  Control Value to the continent.
	 */
	public int getControlValue() {
		return controlValue;
	}

	/**
	 * This function sets the continent name.
	 *
	 * @param continentName , Name of the continent
	 */
	public void setContinentName(String continentName) {
		this.continentName = continentName;
	}

	/**
	 * This function sets the control value of the continent object.
	 *
	 * @param controlValue, Control Value of the continent
	 */
	public void setControlValue(int controlValue) {
		this.controlValue = controlValue;
	}

	/**
	 * This method is used to takes all the countries as a parameter and list them in an array list under the Continent Name
	 * @param country, name of the country of the Continent
	 */
	public void addCountriesToTheContinentList(Country country) {
		this.countriesOfTheContinent.add(country);

	}


	/**
	 * This function is used to return the country list.
	 *
	 * @return ArrayList country object
	 */
	public ArrayList<Country> getCountryList() {
		return countriesOfTheContinent;
	}
}
