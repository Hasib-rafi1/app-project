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
	public boolean reinforce(Player reInforcedPlayer) {	

		// Get assigned list of countries with its size
		ArrayList<Country> assignedListOfCountries = reInforcedPlayer.getAssignedListOfCountries();		
		int sizeOfAssignedCountries = assignedListOfCountries.size();
		if (sizeOfAssignedCountries == 0) {
			return true;
		}		

		// pass the assigned countries and army count to the  strongest countries method
		int armyCount = 0;
		attackerCountry = getStrongestCountries(assignedListOfCountries, armyCount);				

		// check if the attacker country is null( has no countries)
		if (attackerCountry == null) {
			print.consoleErr("**** Sorry!!!! It cannot find any attacking country ****");			
		} else {

			// get attacker country name and armies
			String attackerCountryName = attackerCountry.getCountryName();
			int attackerNumberOfArmies = attackerCountry.getnoOfArmies();			
			print.consoleOut("Reinforcement army adding in Country name: " + attackerCountryName + "with army numbers = "+ attackerNumberOfArmies);

			// get player reinforced armies and set it to 0 the increase army count
			int reinforcedNumberOfArmies = reInforcedPlayer.getNumberOfReinforcedArmies();
			reInforcedPlayer.setNumberOfReinforcedArmies(0);
			attackerCountry.increaseArmyCount(reinforcedNumberOfArmies);

			print.consoleOut("Reinforcement army has been added  in country name: " + attackerCountryName + "with armies count = "+ attackerNumberOfArmies);
		}
		return true;
	}

	@Override
	public boolean attack(Player playerToAttack) {
		// TODO Auto-generated method stub

		// Get the player, country and armies imformation
		String playerName = playerToAttack.getPlayerName();
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



		ArrayList<Country> neighbourCountriesForAttack = playerToAttack.getOthersNeighbouringCountriesOnlyObject(attackerCountry);
		int sizeForneighbourCountriesForAttack = neighbourCountriesForAttack.size();


		//System.out.println("size of sizeForneighbourCountriesForAttack----------"+ sizeForneighbourCountriesForAttack);
		if (neighbourCountriesForAttack == null || sizeForneighbourCountriesForAttack == 0) {
			print.consoleOut("*** Sorry !! Not able to find any neighbouting country to attack from this Country ***");
			return false;
		}

		for (Country sourceCountry: neighbourCountriesForAttack) {

			print.consoleOut(attackerCountry.getCountryName() + "(" + attackerCountry.getnoOfArmies()
			+ ") is attacking to " + sourceCountry.getCountryName() + "(" + sourceCountry.getnoOfArmies() + ")");

			// Perform attack until country is acquired or the attacking country is lost
			int attackerPlayerId = playerToAttack.getPlayerId();
			
			while (sourceCountry.getPlayerId() != attackerPlayerId && sourceCountry.getnoOfArmies() > 0) {
				if (attackerCountry.getnoOfArmies() == 1) {
					break;
				}
				attackDetails(attackerCountry, sourceCountry, playerToAttack);
			}

			if (attackerCountry.getnoOfArmies() == 1) {
				// Cannot perform attack now
				break;
			}
		}
		return true;	


	}



	private boolean attackDetails(Country fromCountry, Country sourceCountry, Player player) {

		int attackerDiceCount = player.getNumberDices(fromCountry, "Attacker");

		int defenderDiceCount = player.getNumberDices(sourceCountry, "Defender");


		player.diceRoller(attackerDiceCount);
		Player defenderPlayer = player.getPlayer(sourceCountry.getPlayerId());
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
				sourceCountry.decreaseArmyCount(1);

			} else {
				System.out.println("Defender wins for dice " + (i + 1));
				fromCountry.decreaseArmyCount(1);
			}

			if (fromCountry.getnoOfArmies() == 1) {
				System.out.println("Attacker not able to Attack ");
				break;
			}
			if (sourceCountry.getnoOfArmies() == 0) {
				System.out.println("Defender lost all armies in " + (i + 1) + " dice roll");
				break;
			}

		}
		// Check if defending armies are 0 then acquire the country with cards
		if (sourceCountry.getnoOfArmies() == 0) {
			player.conquerCountryAutomate(defenderPlayer,sourceCountry,fromCountry);
			return true;
		}
		return false;
	}


	@Override
	public boolean fortify(Player playerToFortify) {

		// Get the player, country and armies information
		String playerName = playerToFortify.getPlayerName();

		// Get assigned list of countries with its size
		ArrayList<Country> assignedListOfCountries = playerToFortify.getAssignedListOfCountries();		
		int sizeOfAssignedCountries = assignedListOfCountries.size();		

		Country destinationCountry = null;	
		Country sourceCountry = getStrongestCountries(assignedListOfCountries, 1);


		for (Country listOfCountries : assignedListOfCountries) {
			ArrayList<Country> neighborCountries = playerToFortify.getNeighbouringCountries(listOfCountries);

			//neighborCountries.removeIf(obj -> obj.getCountryName().equals(sourceCountry.getCountryName()));
			neighborCountries.removeIf((Country object) -> object.getCountryName().equals(sourceCountry.getCountryName()));

			int armiesCount = listOfCountries.getnoOfArmies();
			if (armiesCount > 0 && sourceCountry != listOfCountries) {
				armiesCount = listOfCountries.getnoOfArmies();
				destinationCountry = listOfCountries;
			}
		}

		if (sourceCountry != null && destinationCountry != null) {
			int armiesCount = sourceCountry.getnoOfArmies() - 1;
			/*	print.consoleOut(
					"Aggressive player " + playerToFortify.getPlayerName() + " - fortification from " + sourceCountry.getCountryName()
					+ "(" + sourceCountry.getnoOfArmies() + ") to " + destinationCountry.getCountryName() + "("
					+ destinationCountry.getnoOfArmies() + ") with " + armies + " armies");*/


			sourceCountry.decreaseArmyCount(armiesCount);
			destinationCountry.increaseArmyCount(armiesCount);

			/*print.consoleOut("Finished fortification with destination country " + destinationCountry.getCountryName()
			+ " (" + destinationCountry.getnoOfArmies() + ")");*/
			return true;
		} else {
			print.consoleOut("Aggressive player " + playerName + " cannot find any country for fortification.");
			return false;

		}		
	}




	/**
	 * This method is used to get the strongest countries from thelist.
	 * @param assignedListOfCountries  arraylist of assigned countries
	 * @param armyCount lower limit count of armies
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


	private Country getCountriesToAttackneighbourCountries(Player playerToAttack, ArrayList<Country> CountriesToAttack) {
		Country sourceCountry = null;
		String playerName = playerToAttack.getPlayerName();
		int armies = Integer.MAX_VALUE;
		for (Country neighbourCountries : CountriesToAttack) {
			if (neighbourCountries.getnoOfArmies() < armies) {
				armies = neighbourCountries.getnoOfArmies();
				sourceCountry = neighbourCountries;
			}
		}
		return sourceCountry;
	}


}
