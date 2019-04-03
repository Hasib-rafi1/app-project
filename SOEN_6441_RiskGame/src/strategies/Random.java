package strategies;

import model.Country;
import model.Player;

import java.io.Serializable;
import java.util.ArrayList;


import helper.RandomNumber;


/**
 * This class is used for A random computer player strategy that reinforces random a random country,
 * attacks a random number of times a random country, 
 * and fortifies a random country, all following the standard rules for each phase.
 * @author 
 * @version 1.0.0
 *
 */
public class Random implements PlayerStrategy, Serializable {

    private String strategyName = "Random";

    public String getStrategyName(){
        return strategyName;
    }

    public boolean isHuman(){
        return false;
    }

    public boolean reinforce(Player player){

       ArrayList<Country> countryList = player.getAssignedListOfCountries();
        int random = 0;
        if (countryList.isEmpty())
            return true;
        else if (countryList.size() > 1)
            random = RandomNumber.getRandomNumberInRange(0, countryList.size() - 1);
        int armies = player.getNumberOfReinforcedArmies();
        Country country = countryList.get(random);

        player.setNumberOfReinforcedArmies(0);
        country.increaseArmyCount(armies);
        return true;
    }

    public boolean attack(Player player){
    	int totalAttack = RandomNumber.getRandomNumberInRange(1, 10);

		ArrayList<Country> countryList = player.getpossibleAttackerCountries();
		int randomIndex = 0;
		if (countryList == null || countryList.size() == 0)
			return false;
		else if (countryList.size() > 1)
			randomIndex = RandomNumber.getRandomNumberInRange(0, countryList.size() - 1);

		Country fromCountry = countryList.get(randomIndex);
		System.out.print("Randomly selectd " + fromCountry.getCountryName() + " (" + fromCountry.getnoOfArmies()
				+ ") for attack");

		ArrayList<Country> neighborCountries = player
				.getOthersNeighbouringCountriesOnlyObject(fromCountry);

		if (neighborCountries.isEmpty()) {
			System.out.print("No neighbour found");
			return false;
		} else if (neighborCountries.size() == 1)
			randomIndex = 0;
		else
			randomIndex = RandomNumber.getRandomNumberInRange(0, neighborCountries.size() - 1);

		Country toCountry = neighborCountries.get(randomIndex);
		System.out.print("Randomly selectd " + toCountry.getCountryName() + " (" + toCountry.getnoOfArmies()
				+ ") as a defender");

		for (int i = 0; i < totalAttack; i++) {

			attackDetails(fromCountry, toCountry, player);
		}
		return true;
    }
    
    /**
	 * This method will execute core operation of Attack Phase
	 * 
	 * @param fromCountry
	 * @param toCountry
	 * @param attackerPlayer
	 */
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
            int defencerDice = defendingDices.get(i);

            System.out.print("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);
            if (attackerDice > defencerDice) {
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
        	player.conquerCountry(defenderPlayer);
        }
        return true;

	}

    public boolean fortify(Player player){
    	ArrayList<Country> countryList = player.getAssignedListOfCountries();
		int randomIndex = 0;

		if (countryList.isEmpty())
			return false;
		else if (countryList.size() > 1)
			randomIndex = RandomNumber.getRandomNumberInRange(0, countryList.size() - 1);

		Country sourceCountry = countryList.get(randomIndex);
		System.out.println("Randomly select " + sourceCountry.getCountryName() + "(" + sourceCountry.getnoOfArmies()
				+ ") country for fortification");
		ArrayList<Country> neigbouringCountries = player.getConnectedCountriesRecursively(sourceCountry,
				(ArrayList<Country>) player.getAssignedListOfCountries().clone(), new ArrayList<Country>());

		if (neigbouringCountries != null && neigbouringCountries.size() > 0) {
			int destinationRandomIndex = RandomNumber.getRandomNumberInRange(0, neigbouringCountries.size() - 1);
			Country destinationCountry = neigbouringCountries.get(destinationRandomIndex);
			System.out.println("Randomly select " + destinationCountry.getCountryName() + "("
					+ destinationCountry.getnoOfArmies() + ") country for fortification");
			if (destinationCountry != null) {
				int armies = RandomNumber.getRandomNumberInRange(0, sourceCountry.getnoOfArmies() - 1);
				sourceCountry.decreaseArmyCount(armies);
				destinationCountry.increaseArmyCount(armies);
			}
			System.out.println("Finished fortification with destination country " + destinationCountry.getCountryName()
					+ " (" + destinationCountry.getnoOfArmies() + ")");
		}

		return true;  
    }



}

