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
 * @author 
 * @version 1.0.0
 *
 */
public class Cheater implements PlayerStrategy, Serializable  {
	
	
	Game game;
	Player player1;
	public String strategyName = "Cheater";
    public String getStrategyName(){
        return strategyName;
    }
	public boolean isHuman() {
		return false;
	}
	
	public boolean reinforce(Player player) {
		for (Country country : player.getAssignedListOfCountries()) {
			player.setNumberOfReinforcedArmies(0);
			int armies = country.getnoOfArmies();
			country.setnoOfArmies(armies * 2);
		}
		return true;
		
	}
	public boolean attack(Player player) {
		for(Country country : player.getAssignedListOfCountries()) {
			ArrayList<String> getNeighbouringCountries = game.getOthersNeighbouringCountriesOnly(country.getCountryName());
			//Player.conquerCountry();
		}
		return true;
		
	}
	
	public boolean fortify(Player player) {
		int armiesCount;
		for (Country country : player.getAssignedListOfCountries()) {
			ArrayList<String> getNeighbouringCountries = game.getOthersNeighbouringCountriesOnly(country.getCountryName());
			for(String tempM:getNeighbouringCountries) {
				ArrayList<Country> getPlayerOwnedCountries = player1.getAssignedListOfCountries();
				for(Country tempN:getPlayerOwnedCountries) {
					if(tempN.toString()!=tempM) {
						armiesCount=tempN.getnoOfArmies();
						tempN.setnoOfArmies(armiesCount*2);
					}
				}
			}		
		}
		return true;
	}
}
