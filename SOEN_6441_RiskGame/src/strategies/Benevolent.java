package strategies;

import java.util.ArrayList;

import model.Country;
import model.Player;


import java.io.Serializable;

/**
 * This class is used for benevolent computer player strategy that focuses on protecting its weak countries 
 * (reinforces its weakest countries, never attacks, then fortifies in order to move armies to weaker countries).
 * @author 
 * @version 1.0.0
 *
 */
public class Benevolent implements PlayerStrategy,Serializable{
		
	public String strategyName = "Benevolent";

    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
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
	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean attack(Player player) {
		// TODO Auto-generated method stub
		System.out.println("No attack phase for Benevolent Strategy");
		return false;
	}
	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		return true;
	}
}