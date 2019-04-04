package strategies;

import model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;



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
		System.out.println(attackerCountry+"---------------------------------------");
		if (attackerCountry == null) {
			print.consoleErr("**** Sorry!!!! It cannot find any attacking country ****");			
		} else {
			String attackerCountryName = attackerCountry.getCountryName();
			int attackerNumberOfArmies = attackerCountry.getnoOfArmies();			
		//	print.consoleOut("====================Adding Reinforcement army in Country name: " + attackerCountryName + "with armies count = "+ attackerNumberOfArmies);

			int reinforcedNumberOfArmies = player.getNumberOfReinforcedArmies();
			player.setNumberOfReinforcedArmies(0);
			attackerCountry.increaseArmyCount(reinforcedNumberOfArmies);

			//print.consoleOut("==============++++++Added Reinforcement army in country name: " + attackerCountryName + "with armies count = "+ attackerNumberOfArmies);
		}
		return true;
	}

	@Override
	public boolean attack(Player player) {
		// TODO Auto-generated method stub

		// Get the player, country and armies imformation
		String playerName = player.getPlayerName();
		
		System.out.println("====="+ playerName);
		String attackerCountryName = attackerCountry.getCountryName();
		int attackerNumberOfArmies = attackerCountry.getnoOfArmies();

		// Check if there is no country to attack
		if (attackerCountry == null) {
			print.consoleOut("There is no country to attack" );
			return false;
		}		
		print.consoleOut("Aggressive player name "+ playerName +" - attack - attacking from "
				+ attackerCountryName+ " with attacker armies count = "+ attackerNumberOfArmies);

		//ArrayList<Country> CountriesToAttack = player.getOthersNeighbouringCountriesOnlyObject(attackerCountry.getCountryName());



		for (Country country : player.getAssignedListOfCountries()) {

			ArrayList<Country> neighbourCountriesForAttack = player.getOthersNeighbouringCountriesOnlyObject(country);
			int sizeForneighbourCountriesForAttack = neighbourCountriesForAttack.size();
			
			
			System.out.println("size of sizeForneighbourCountriesForAttack----------"+ sizeForneighbourCountriesForAttack);
			if (neighbourCountriesForAttack == null || sizeForneighbourCountriesForAttack == 0) {
				print.consoleOut("*** Sorry !! Not able to find any neighbouting country to attack from this Country ***");
				return false;
			}

			while (sizeForneighbourCountriesForAttack > 0) {
				System.out.println("in while");
				Country toCountry = getCountryToAttack(player, neighbourCountriesForAttack);

				if (toCountry == null) {
					neighbourCountriesForAttack.remove(toCountry);
					continue;
				}

				print.consoleOut(attackerCountry.getCountryName() + "(" + attackerCountry.getnoOfArmies()
				+ ") is attacking to " + toCountry.getCountryName() + "(" + toCountry.getnoOfArmies() + ")");

				// Perform attack until country is acquired or the attacking country is lost

				while (toCountry.getPlayer().getPlayerId() != player.getPlayerId()
						&& toCountry.getnoOfArmies() > 0) {
					if (attackerCountry.getnoOfArmies() == 1)
						break;
					attackDetails(attackerCountry, toCountry, player);
				}

				if (attackerCountry.getnoOfArmies() == 1) {
					// Cannot perform attack now
					break;
				}
				neighbourCountriesForAttack.remove(toCountry);
			}
		}
		return true;	


	}



	private boolean attackDetails(Country fromCountry, Country toCountry, Player player) {

		int attackerDiceCount = player.getNumberDices(fromCountry, "Attacker");

		int defenderDiceCount = player.getNumberDices(toCountry, "Defender");


		player.diceRoller(attackerDiceCount);
		Player defenderPlayer = player.getPlayer(toCountry.getPlayerId());
		defenderPlayer.diceRoller(defenderDiceCount);

		ArrayList<Integer> diceResults1 = player.getDiceResults();
		ArrayList<Integer> diceResults2 = defenderPlayer.getDiceResults();

		ArrayList<Integer> attackingDices = diceResults1;
		ArrayList<Integer> defendingDices = diceResults2;

		int totalNumberOfDice = attackingDices.size() < defendingDices.size() ? attackingDices.size()
				: defendingDices.size();

		for (int i = 0; i < totalNumberOfDice; i++) {

			int attackerDice = attackingDices.get(i);
			int defenderDice = defendingDices.get(i);

			System.out.print("Attacker dice - " + attackerDice + "  to Defender dice - " + defenderDice);
			if (attackerDice > defenderDice) {
				System.out.println("Attacker wins for dice " + (i + 1));
				toCountry.decreaseArmyCount(1);

			} else {
				System.out.println("Defender wins for dice " + (i + 1));
				fromCountry.decreaseArmyCount(1);
			}

			if (fromCountry.getnoOfArmies() == 1) {
				System.out.println("Attacker not able to Attack ");
				break;
			}
			if (toCountry.getnoOfArmies() == 0) {
				System.out.println("Defender lost all armies in " + (i + 1) + " dice roll");
				break;
			}

		}
		// Check if defending armies are 0 then acquire the country with cards
		if (toCountry.getnoOfArmies() == 0) {
			player.conquerCountryAutomate(defenderPlayer,toCountry,fromCountry);
			return true;
		}
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


	private Country getCountryToAttack(Player attackerPlayer, ArrayList<Country> CountriesToAttack) {
		Country toCountry = null;
		int armies = Integer.MAX_VALUE;
		for (Country neighbourCountry : CountriesToAttack) {
			if (neighbourCountry.getnoOfArmies() < armies) {
				armies = neighbourCountry.getnoOfArmies();
				toCountry = neighbourCountry;
			}
		}

		return toCountry;
	}


}
