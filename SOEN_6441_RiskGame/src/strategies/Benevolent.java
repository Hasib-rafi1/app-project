package strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import helper.RandomNumber;
import model.Country;
import model.Player;


import java.io.Serializable;

/**
 * This class is used for benevolent computer player strategy that focuses on protecting its weak countries 
 * (reinforces its weakest countries, never attacks, then fortifies in order to move armies to weaker countries).
 * @author Jaiganesh
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
		int minArmies = getMinimumArmies(player);
		List<Country> weakestCountries =  player.getattackPlayerCountry().get(player).stream()
				.filter(x -> x.getnoOfArmies() == minArmies).collect(Collectors.toList());

		System.out.println("Found " + weakestCountries.size() + " weakest countries. Now assigning "
				+ player.getNumberOfReinforcedArmies() + " armies");
		if (weakestCountries != null && weakestCountries.size() > 0) {
			
			int index = 0;
			while (player.getNumberOfReinforcedArmies() > 0) {
				Country c = weakestCountries.get(index);
				int armies = player.getNumberOfReinforcedArmies();
				player.decreaseReinforcementArmy();
				c.increaseArmyCount(1);
				System.out.println("Added reinforcement army in " + c.getCountryName() + "(" + c.getnoOfArmies() + ")");
				index++;
				if (index == weakestCountries.size())
					index = 0;
			}
		} else {
				System.out.println("Cannot find any weakest country");
		}
		
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
		ArrayList<Country> countryList = player.getattackPlayerCountry().get(player);
		for (Country fromCountry : countryList) {

			if (fromCountry == null)
				break;
			System.out.println("Found strongest country " + fromCountry.getCountryName() + ". Now finding weakest link...");
			ArrayList<Country> neighborCountries = player.getNeighbouringCountries(fromCountry);
			if (neighborCountries != null && neighborCountries.size() > 0) {
				Country toCountry = getWeakestCountry(neighborCountries);
				if (fromCountry != null && toCountry != null
						&& toCountry.getnoOfArmies() < fromCountry.getnoOfArmies()) {
					// fortify weakest country
					int armies = RandomNumber.getRandomNumberInRange(0, fromCountry.getnoOfArmies()  - 1);
					System.out.println("Benevolent player " + player.getPlayerName() + " - fortification from "
							+ fromCountry.getCountryName() + "(" + fromCountry.getnoOfArmies() + ") to "
							+ toCountry.getCountryName() + "(" + toCountry.getnoOfArmies() + ") with " + armies
							+ " armies");
					
					fromCountry.decreaseArmyCount(armies);
					toCountry.increaseArmyCount(armies);
					System.out.println("Finished fortifying "+armies+" armies to the destination country " + toCountry.getCountryName()
					+ " (" + toCountry.getnoOfArmies() + ")");
					break;
				}
			}
			System.out.println("Cannot find any neighbouring weaker country");
			
		}

		return true;
	}
}