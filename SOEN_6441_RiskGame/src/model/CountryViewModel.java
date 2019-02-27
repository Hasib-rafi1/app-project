package model;

import java.util.ArrayList;
import helper.Colors;
/**
 * An object model for country with payers information and number of armies belongs to one country 
 * for creating a view.
 * @author Hasibul Huq
 *
 */
public class CountryViewModel {
	private int countryId;
	private String countryName;
	private int xCoordinate;
	private int yCoordinate;
	private int numberOfArmies;
	private Colors colorOfCountry;
	private int playerID;
	private ArrayList<String> neighbours = new ArrayList<>();
	
	/**
     * This method is used to get an integer value of the country which is set as Country ID
     * @return the country ID Integer
     */
	public int getCountryId() {
		return countryId;
	}
	
	/**
	 * This method is going to set the country id
	 * @param countryId
	 */
	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}
	
	/**
     * This method is used to get the name of the Country.
     * @return the country Name String
     */
	public String getCountryName() {
		return countryName;
	}
	
	/**
	 * This method will set the name of the country
	 * @param countryName
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	/**
	 * This method will return the x coordinate of the view 
	 * @return xCoordinate as an integer value
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}
	
	/**
	 * this method will set xCoordinate as an integer value
	 * @param xCoordinate
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}
	/**
	 * This method will return yCoordinate as an integer value
	 * @return yCoordinate as an integer value
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}
	
	/**
	 * This method is setting yCoordinate value
	 * @param yCoordinate
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
	/**
	 * This method will return the numbers of armies that the country have right now
	 * @return numberOfArmies as an integer value
	 */
	public int getNumberOfArmies() {
		return numberOfArmies;
	}
	
	/**
	 * This method is setting the numbers
	 * @param numberOfArmies
	 */
	public void setNumberOfArmies(int numberOfArmies) {
		this.numberOfArmies = numberOfArmies;
	}
	
	/**
	 * This method will the return the country color according to the player color
	 * @return colors
	 */
	public Colors getColorOfCountry() {
		return colorOfCountry;
	}
	
	/**
	 * This method is storing the color of the player who currently acquired the country 
	 * @param colorOfCountry
	 */
	public void setColorOfCountry(Colors colorOfCountry) {
		this.colorOfCountry = colorOfCountry;
	}
	
	/**
	 * This function is returning the data of player id who is currently acquired the country 
	 * @return player id as integer value
	 */
	public int getPlayerID() {
		return playerID;
	}
	
	/**
	 * This method is setting player id who is currently acquired the country
	 * @param playerID
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * This function is returning the array list of the neighbours of the country
	 * @return neighbours
	 */
	public ArrayList<String> getNeighbours() {
		return neighbours;
	}
	
	/**
	 * The function is going to set the neighbours of the specific country.
	 * @param neighbours
	 */
	public void setNeighbours(ArrayList<String> neighbours) {
		this.neighbours = neighbours;
	}
	
}
