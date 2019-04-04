package strategies;

import model.Player;

import java.io.Serializable;
import java.util.ArrayList;

import helper.PrintConsoleAndUserInput;
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
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	public String strategyName = "Aggressive";
	private Country attackerCountry;

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
		
		ArrayList<Country> assignedListOfCountries = player.getAssignedListOfCountries();		
		int sizeOfAssignedCountries = assignedListOfCountries.size();
		
		if (sizeOfAssignedCountries == 0) {
			return true;
		}

		int armyCount = 0;
		attackerCountry = getStrongestCountries(assignedListOfCountries, armyCount);		
		
		if (attackerCountry == null) {
			print.consoleErr("**** Sorry!!!! It cannot find any attacking country ****");			
		} else {
			String attackerCountryName = attackerCountry.getCountryName();
			int attackerNumberOfArmies = attackerCountry.getnoOfArmies();			
			print.consoleOut("Adding Reinforcement army in Country name: " + attackerCountryName + "with armies count = "+ attackerNumberOfArmies);
			
			int reinforcedNumberOfArmies = player.getNumberOfReinforcedArmies();
			player.setNumberOfReinforcedArmies(0);
			attackerCountry.increaseArmyCount(reinforcedNumberOfArmies);
		
			print.consoleOut("Added Reinforcement army in country name: " + attackerCountryName + "with armies count = "+ attackerNumberOfArmies);
		}
		return true;
	}

	@Override
	public boolean attack(Player player) {
		// TODO Auto-generated method stub
		return false;

	}

	@Override
	public boolean fortify(Player player) {
		// TODO Auto-generated method stub
		//return false;
		ArrayList<Country> assignedListOfCountries = player.getAssignedListOfCountries();		
		int sizeOfAssignedCountries = assignedListOfCountries.size();
		
		Country sourceCountry = getStrongestCountries(assignedListOfCountries, 1);
		
	
		return false;
	}

	/**
	 * This method is used to get the strongest countries from thelist.
	 * @param assignedListOfCountries  arraylist of assigned countries
	 * @param armiesCount lower limit count of armies
	 * @return strongestCountry strongest country 
	 */
	public Country getStrongestCountries(ArrayList<Country> assignedListOfCountries, int armyCount) {
		Country strongestCountry = null;
		//int armiesCount = thresholdArmyCount;
		for (Country list : assignedListOfCountries) {
			if (list.getnoOfArmies() > armyCount) {
				armyCount = list.getnoOfArmies();
				strongestCountry = list;
			}
		}
		return strongestCountry;
	}



}
