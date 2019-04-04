package strategies;

import java.io.Serializable;
import java.util.ArrayList;


import model.Country;
import model.Game;
import model.Player;

/**
 * This class is used for cheater computer player strategy whose reinforce() method doubles the number of armies on all its countries,
 * whose attack() method automatically conquers all the neighbors of all its countries, and whose fortify() method 
 * doubles the number of armies on its countries that have neighbors that belong to other players.
 * @author Jai
 * @version 1.0.0
 *
 */
public class Cheater implements PlayerStrategy, Serializable  {
	
	public String strategyName = "Cheater";
    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
	}
	
	public boolean reinforce(Player player) {
		for (Country country : player.getAssignedListOfCountries()) {
			System.out.println(
					"Adding reinforcement army in " + country.getCountryName() + "(" + country.getnoOfArmies() + ")");
			player.setNumberOfReinforcedArmies(0);
			int armies = country.getnoOfArmies();
			country.setnoOfArmies(armies * 2);
			System.out.println(
					"Added reinforcement army in " + country.getCountryName() + "(" + country.getnoOfArmies() + ")");
		}
		return true;
		
	}
	public boolean attack(Player player) {
		for(Country country : player.getAssignedListOfCountries()) {
			ArrayList<Country> getNeighbouringCountries = player.getOthersNeighbouringCountriesOnlyObject(country);
			System.out.println("Cheater:\t"+player.getPlayerName()+"\tattacking\t"+getNeighbouringCountries.size()+"neighbours now.");
			for(Country temp:getNeighbouringCountries) {
				Player defender=player.getPlayer(temp.getPlayerId());
				temp.setnoOfArmies(1);
				player.conquerCountry(defender);
			}
				
		}
		return true;
		
	}
	
	public boolean fortify(Player player) {
		int armiesCount;
		for (Country country : player.getAssignedListOfCountries()) {
			System.out.println("Cheater player " + player.getPlayerName() + " is trying to fortify " + country.getCountryName()
			+ "(" + country.getnoOfArmies() + ")");
			ArrayList<Country> getNeighbouringCountries = player.getOthersNeighbouringCountriesOnlyObject(country);
			for(Country country1:getNeighbouringCountries) {
				armiesCount=country1.getnoOfArmies();
				country1.setnoOfArmies(armiesCount*2);
				System.out.println("-- Finished fortification with country " + country1.getCountryName() + " ("
						+ country1.getnoOfArmies() + ")");
			}
		}	
		return true;
	}
}
