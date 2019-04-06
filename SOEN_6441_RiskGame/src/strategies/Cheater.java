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
 * 
 * @author naren
 * @version 1.0.0
 *
 */
public class Cheater implements PlayerStrategy, Serializable  {

	public String strategyName = "Cheater";
	
	/**
	 * Returns the strategy name of the strategy
	 */
	public String getStrategyName(){
		return strategyName;
	}
	
    /**
	 * Return false for the non-human(Cheater) strategy
	 */
	public boolean isHuman() {
		return false;
	}

	/**
	 * Method to execute reinforcement for the cheater strategy
	 * @param player
	 */
	public boolean reinforce(Player player) {
		for (Country country :  player.getattackPlayerCountry().get(player)) {
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
	
	/**
	 * Method to execute attack for the cheater strategy
	 * @param player
	 */
	public boolean attack(Player player) {
		ArrayList<Country> playersCountries = new ArrayList<Country>();
		playersCountries = player.getAssignedListOfCountries();
		for(int i = 0; i<playersCountries.size(); i++) {
			Country country =playersCountries.get(i);
			ArrayList<Country> getNeighbouringCountries = player.getOthersNeighbouringCountriesOnlyObject(country);
			System.out.println("Cheater:\t"+player.getPlayerName()+"\tattacking\t"+getNeighbouringCountries.size()+"\tneighbours now.");
			System.out.println(country.getCountryName());
			for(Country temp:getNeighbouringCountries) {
				Player defender=player.getPlayer(temp.getPlayerId());
				player.conquerCountryAutomate(defender,temp,country);
				temp.setnoOfArmies(1);
				playersCountries.remove(temp);
				System.out.println(temp.getCountryName());
				if(country.getnoOfArmies()<1) {
					country.setnoOfArmies(1);
				}
			}

		}
		return true;

	}
	
	/**
	 * Method to execute fortification for the cheater strategy
	 * @param player
	 */
	public boolean fortify(Player player) {
		int armiesCount;
		for (Country country : player.getattackPlayerCountry().get(player)) {
			System.out.println("Cheater player " + player.getPlayerName() + " is trying to fortify " + country.getCountryName()
			+ "(" + country.getnoOfArmies() + ")");
			ArrayList<Country> neighbouringCountries = player.getOthersNeighbouringCountriesOnlyObject(country);
			if (neighbouringCountries != null || neighbouringCountries.size() == 0) {
				System.out.println("Cannot fortify as there is no neigbouring county found from other player");
			} else {
				armiesCount = country.getnoOfArmies() * 2;
				country.setnoOfArmies(armiesCount);
				System.out.println("Finished fortification with country " + country.getCountryName() + " ("
						+ country.getnoOfArmies() + ")");
			}
		}	
		return true;
	}
}
