package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import helper.Colors;
import helper.Card;

// TODO: Auto-generated Javadoc
/**
 * this is a player class which contains the players attributes and basic setter getter functions 
 * to get and set the value out of it. Some business logic is also added in this class
 * @author Md Hasibul Huq
 * @version 1.0.0
 */
public class Player {
	
	/** The player id. */
	private int playerId;
	
	/** The number of initial armies. */
	private int numberOfInitialArmies;
	
	/** The number of reinforced armies. */
	private int numberOfReinforcedArmies;
	
	/** The player name. */
	private String playerName;
	
	/** The color. */
	private Colors color;
	
	/** The dice results. */
	private ArrayList<Integer> diceResults = new ArrayList<>();
	
	/** The assigned list of countries. */
	private ArrayList<Country> assignedListOfCountries = new ArrayList<Country>();
	
	/** To assign a card after the attack phase if the country is Conquered. */
	private Boolean isConquered = false;

	/** The assigned Risk Card of the player. */
	private ArrayList<Card> playerCards = new ArrayList<>();
	
	/**  The Conquered continents. */
	private ArrayList<Continent> playerContinents = new ArrayList<>();

	/** The initial armiesafter exchange. */
	private Integer initialArmiesafterExchange = 0;

	/**
	 * This is a constructor of Player Class which sets playerId, name, and
	 * color.
	 *
	 * @param playerId the player id
	 * @param name the name
	 */
	public Player(int playerId, String name) {
		super();
		this.playerId = playerId;
		this.playerName = name;
		this.color = getPlayerColor(playerId);
	}

	/**
	 * This method is going to provide the players id.
	 *
	 * @return integer value of playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * This method is setting the value of playerId of a player object.
	 *
	 * @param playerId ID of player
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * This function is providing the assigned army number of a player.
	 *
	 * @return integer value of assigned armies
	 */
	public int getNumberOfInitialArmies() {
		return numberOfInitialArmies;
	}

	/**
	 * This is function is going to set the assigned army numbers in the specific object.
	 *
	 * @param numberOfInitialArmies number of initial armies
	 */
	public void setNumberOfInitialArmies(int numberOfInitialArmies) {
		this.numberOfInitialArmies = numberOfInitialArmies;
	}

	/**
	 * This function is going to return the reinforced army number.
	 *
	 * @return integer value number of reinforced army
	 */
	public int getNumberOfReinforcedArmies() {
		return numberOfReinforcedArmies;
	}

	/**
	 * This function is setting the reinforced army.
	 *
	 * @param noOfReinforcedArmies number of enforced armies
	 */
	public void setNumberOfReinforcedArmies(int noOfReinforcedArmies) {
		this.numberOfReinforcedArmies = noOfReinforcedArmies;
	}

	/**
	 * This function is going to return name of the player.
	 *
	 * @return string value.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * This function sets the name of the player.
	 *
	 * @param playerName name of player
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * This function returns the color.
	 *
	 * @return ENUM value of color
	 */
	public Colors getColor() {
		return color;
	}

	/**
	 * Setting the color.
	 *
	 * @param color set color
	 */
	public void setColor(Colors color) {
		this.color = color;
	}
	
	/**
	 * This method is going to decrease the number of initial armies after each assigning 
	 * in initial assigning step.
	 */
	public void decreasenumberOfInitialArmies() {
		if(numberOfInitialArmies>0) {
			numberOfInitialArmies = numberOfInitialArmies -1;
		}
	}

	/**
	 * This method is going to decrease the unassigned reinforcement armies count. when the reinforcement 
	 * armies are distributed.  
	 */
	public void decreaseReinforcementArmy() {
		if(numberOfReinforcedArmies>0) {
			numberOfReinforcedArmies= numberOfReinforcedArmies -1;
		}
	}

	/**
	 * Gets the number dices.
	 *
	 * @param country the country
	 * @param playerStatus the player status
	 * @return allowableAttackingArmies
	 */
	public int getNumberDices(Country country, String playerStatus) {
		int allowableAttackingArmies = 0;
		int maximumDiceCount = 0;
		if (playerStatus.equals("Attacker")) {
			if(country.getnoOfArmies()>3) {
				allowableAttackingArmies =3;
			}else {
				allowableAttackingArmies = country.getnoOfArmies() - 1;	
			}
		} else {
			if(country.getnoOfArmies()>2) {
				allowableAttackingArmies =2;
			}else {
				allowableAttackingArmies = country.getnoOfArmies();	
			}
		}
		return allowableAttackingArmies;
	}
	
	/**
	 * This method is used to return the assigned countries to each Player.
	 * 
	 * @return assignedListOfCountries
	 */
	public ArrayList<Country> getAssignedListOfCountries() {
		return assignedListOfCountries;
	}
	
	/**
	 * Assigns the current country to player.
	 *
	 * @param country the country
	 */
	public void assignCountryToPlayer(Country country) {
		assignedListOfCountries.add(country);
	}
	
	/**
	 * Unassigns the current country to player.
	 *
	 * @param country the country
	 */
	public void unAssignCountryToPlayer(Country country) {
		assignedListOfCountries.remove(country);
	}

	/**
	 * This method will process attack for the selected player and for the defender player.
	 *
	 * @param defenderPlayer the defender player
	 * @param attackerCountry the attacker country
	 * @param defenderCountry the defender country
	 * @param attackerDiceCount the attacker dice count
	 * @param defenderDiceCount the defender dice count
	 * @param playerCountry the player country
	 */
	public void attackPhaseActions(Player defenderPlayer, Country attackerCountry, Country defenderCountry, int attackerDiceCount, int defenderDiceCount,HashMap<Player, ArrayList<Country>> playerCountry) {
		diceRoller(attackerDiceCount);
		defenderPlayer.diceRoller(defenderDiceCount);

		ArrayList<Integer> attackingDices = diceResults;
		ArrayList<Integer> defendingDices = defenderPlayer.diceResults;

		int totalNumberOfDice = attackingDices.size() < defendingDices.size() ? attackingDices.size()
				: defendingDices.size();

		for (int i = 0; i < totalNumberOfDice; i++) {

			int attackerDice = attackingDices.get(i);
			int defencerDice = defendingDices.get(i);

			System.out.print("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);

			if (attackerDice > defencerDice) {
				System.out.println("Attacker wins for dice " + (i + 1));
				defenderCountry.decreaseArmyCount(1);

			} else {
				System.out.println("Defender wins for dice " + (i + 1));
				attackerCountry.decreaseArmyCount(1);
			}

			if (attackerCountry.getnoOfArmies() == 1) {
				System.out.println("Attacker not able to Attack ");
				break;
			} 
			if (defenderCountry.getnoOfArmies() == 0) {
				System.out.println("Defender lost all armies in " + (i + 1) + " dice roll");
				break;
			}

		}
		// Check if defending armies are 0 then acquire the country with cards
		if (defenderCountry.getnoOfArmies() == 0) {
			defenderCountry.setPlayerId(playerId);
			defenderCountry.setCountryColor(attackerCountry.getCountryColor());
			defenderPlayer.unAssignCountryToPlayer(defenderCountry);
			assignCountryToPlayer(defenderCountry);
			playerCountry.get(this).add(defenderCountry);
			playerCountry.get(defenderPlayer).remove(defenderCountry);
			isConquered =true;
			// attacker has to put minimum one army defending country (By Game rules)
			attackerCountry.decreaseArmyCount(1);
			defenderCountry.increaseArmyCount(1);
			
			if (defenderPlayer.getAssignedListOfCountries().size() == 0) {
				ArrayList<Card> defendersCards = defenderPlayer.getCards();
				defenderPlayer.removeCards();
				for(Card card: defendersCards) {
					this.addCard(card);
				}
			}
		}
	}

	/**
	 * This method will roll a Dice.
	 *
	 * @param diceCount the dice count
	 */
	private void diceRoller(int diceCount) {
		diceResults.clear();
		for (int i = 0; i < diceCount; i++) {
			diceResults.add(this.getRandomDiceNumber());
		}
	}

	/**
	 * This will generate the random integers between  1 to 6.
	 *
	 * @return random integer
	 */
	public int getRandomDiceNumber() {
		Random r = new Random();
		return r.nextInt((6 - 1) + 1) + 1;
	}
	
	/**
	 * This method calculates the corresponding reinforcement armies from a particular player from the number of countries owned by the layer.
	 * @param playerCountry Player
	 * @param continents Continents
	 * @return total number of armies in reinforcement
	 */
	public int calculationForNumberOfArmiesInReinforcement(HashMap<Player, ArrayList<Country>> playerCountry,ArrayList<Continent> continents) {
		int countries_count = (int) Math.floor(playerCountry.get(this).stream().count() / 3);
		if (playerCountry.containsKey(this)) {
			ArrayList<Country> assignedCountries = playerCountry.get(this);

			List<Integer> assignedCountryIds = assignedCountries.stream().map(c -> c.getCountryId()).collect(Collectors.toList());

			for (Continent continent : continents) {
				List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId()).collect(Collectors.toList());

				boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);

				if (hasPlayerAllCountries){
					countries_count += continent.getControlValue();
				}
			}
		}
		countries_count = countries_count+ initialArmiesafterExchange;
		return countries_count;
	}
	
	/**
	 * This method checks whether the source and destination countries belongs to the player and moves the armies from source to destination.
	 * @param sourceCountry  source as string
	 * @param destinationCountry destination countries as string
	 * @param armies count of armies as int
	 * @return true
	 */
	public boolean fortificationPhase(Country sourceCountry, Country destinationCountry, int armies){
		if (sourceCountry == null || destinationCountry == null) {
			System.out.println("Source or destination country is invalid!");
			return false;
		}

		if (armies == 0) {
			System.out.println("No armies to move");
			return false;
		}
		sourceCountry.decreaseArmyCount(armies);
		destinationCountry.increaseArmyCount(armies);
		
		return true;
	}

	/**
	 * Gets the checks if is conqured.
	 *
	 * @return the checks if is conqured
	 */
	public boolean getIsConqured() {
		return isConquered;
	}
	
	/**
	 * Sets the checks if is conqured.
	 *
	 * @param isConqueredTemp the new checks if is conqured
	 */
	public void setIsConqured(boolean isConqueredTemp) {
		isConquered = isConqueredTemp;
	}
	
	/**
	 * Gets the cards.
	 *
	 * @return the cards
	 */
	public ArrayList<Card> getCards() {
		return playerCards;
	}

	/**
	 * Removes the cards.
	 */
	public void removeCards() {
		playerCards.clear();
	}

	/**
	 * Adds the card.
	 *
	 * @param card the card
	 */
	public void addCard(Card card) {
		playerCards.add(card);
	}

	/**
	 * set Conquer continents for the player.
	 *
	 * @param continents Continents
	 */
	public void setConcuredContinents(ArrayList<Continent> continents) {
		List<Integer> assignedCountryIds = this.getAssignedListOfCountries().stream().map(c -> c.getCountryId()).collect(Collectors.toList());
		playerContinents.removeAll(continents);
		for (Continent continent : continents) {
			List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId()).collect(Collectors.toList());

			boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);

			if (hasPlayerAllCountries){
				playerContinents.add(continent);
			}
		}
	}
	
	/**
	 * Gets the conquerd continents.
	 *
	 * @return the conquerd continents
	 */
	public ArrayList<Continent> getConquerdContinents(){
		return playerContinents;
	}
	
	/**
	 * Sets the initial armiesafter exchange.
	 *
	 * @param armies the new initial armiesafter exchange
	 */
	public void setInitialArmiesafterExchange(int armies) {
		initialArmiesafterExchange = armies;
	}

	/**
	 * This returns the player color.
	 * @param playerID the id of the player
	 * @return EnumColor,color of the player
	 */
	public static Colors getPlayerColor(int playerID) {
		switch (playerID) {
		case 0:
			return Colors.BLACK;
		case 1:
			return Colors.BLUE;
		case 2:
			return Colors.GREEN;
		case 3:
			return Colors.RED;
		case 4:
			return Colors.ORANGE ;
		case 5:
			return Colors.MAGENTA;
		default:
			return Colors.BLACK;
		}
	}
}
