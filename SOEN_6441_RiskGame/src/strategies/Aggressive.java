package strategies;

import model.Player;

import java.io.Serializable;
import java.util.ArrayList;

import model.Country;

/**
 * This class is used for aggressive computer player strategy that focuses on attack (reinforces its strongest country, 
 * then always attack with it until it cannot attack anymore, then fortifies in order to 
 * maximize aggregation of forces in one country).
 * @author Gargi sharma
 * @version 1.0.0
 *
 */
public class Aggressive implements PlayerStrategy, Serializable {
	public String strategyName = "Aggressive";

    public String getStrategyName(){
        return strategyName;
    }

	@Override
	public boolean isHuman() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean reinforce(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean attack(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * This method is used to get the strongest countries from thelist.
	 * @param assignedListOfCountries  arraylist of assigned countries
	 * @param armiesCount lower limit count of armies
	 * @return strongestCountry strongest country 
	 */
	public Country getStrongestCountries(ArrayList<Country> assignedListOfCountries, int armiesCount) {
		Country strongestCountry = null;
		//int armiesCount = thresholdArmyCount;
		for (Country list : assignedListOfCountries) {
			if (list.getnoOfArmies() > armiesCount) {
				armiesCount = list.getnoOfArmies();
				strongestCountry = list;
			}
		}
		return strongestCountry;
	}



}
