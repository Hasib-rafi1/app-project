package strategies;

import java.util.ArrayList;

import model.Country;
import model.Player;



/**
 * This class is used for benevolent computer player strategy that focuses on protecting its weak countries 
 * (reinforces its weakest countries, never attacks, then fortifies in order to move armies to weaker countries).
 * @author 
 * @version 1.0.0
 *
 */
public class Benevolent {
		
	public String strategyName = "Benevolent";

    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
	}

	public boolean reinforce() {
		
		return true;
	}
	public boolean attack() {
		System.out.println("No attack phase for Benevolent Strategy");
		return false;
	}
	
	public boolean fortify() {
		return true;
	}
	
	private Country getWeakestCountry(ArrayList<Country> countries) {
		Country country = null;
		int armiesCount = Integer.MAX_VALUE;
		for (Country c : countries) {
			if (c.getnoOfArmies() < armiesCount) {
				armiesCount = c.getnoOfArmies();
				country = c;
			}
		}
		return country;
	}
	
	public int getMinimumArmies(Player player) {
		int returnVal = Integer.MAX_VALUE;
		ArrayList<Country> assignedCountryList = player.getAssignedListOfCountries();
		for (Country country : assignedCountryList) {
			if (country.getnoOfArmies() < returnVal)
				returnVal = country.getnoOfArmies();
		}
		return returnVal;
	}
}