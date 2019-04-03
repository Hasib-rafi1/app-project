package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;



import helper.Colors;
import helper.GamePhase;
import helper.Card;
import strategies.PlayerStrategy;


// TODO: Auto-generated Javadoc
/**
 * this is a player class which contains the players attributes and basic setter getter functions 
 * to get and set the value out of it. Some business logic is also added in this class
 * 
 * @author Md Hasibul Huq
 * @version 1.0.0
 */
public class Player implements Serializable{
	
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

	/** The Player's Strategy. */
	private PlayerStrategy playerStrategy;
	/** The connected own countries. */
	private ArrayList<Country> connectedOwnCountries = new ArrayList<Country>();
	/** The initial source country. */
	private Country initialSourceCountry;
	//Fortification-Strategy
	private ArrayList<Country> assignedCountryList = new ArrayList<Country>();
	private Card riskCards;

	private Country fortifySourceCountry;
	private Country fortifyDestinationCountry;
	private int fortifyArmies;
	private ArrayList<Player> playerList;

	public Card getRiskCards() {
		return riskCards;
	}

	public void setRiskCards(Card riskCards) {
		this.riskCards = riskCards;
	}


	public void setFortifySourceCountry(Country country){
		this.fortifySourceCountry = country;
	}

	public Country getFortifySourceCountry(){
		return fortifySourceCountry;
	}

	public void setFortifyDestinationCountry(Country country){
		this.fortifyDestinationCountry = country;
	}

	public Country getFortifyDestinationCountry(){
		return fortifyDestinationCountry;
	}

	public void setFortifyArmies(int armies){
		this.fortifyArmies = armies;
	}

	public int getFortifyArmies(){
		return fortifyArmies;
	}

    public boolean fortificationPhase(){
        return this.playerStrategy.fortify(this);
    }

    //Reinforcement-Strategy

    private Country reinforceCountry;
    private ArrayList<Continent> reinforceContinent;

	public ArrayList<Continent> getReinforceContinent() {
		return reinforceContinent;
	}

	public void setReinforceContinent(ArrayList<Continent> reinforceContinent) {
		this.reinforceContinent = reinforceContinent;
	}

	public void setReinforceCountry(Country country){
	    this.reinforceCountry = country;
    }

    public Country getReinforceCountry(){
        return reinforceCountry;
    }

    public boolean reinforcementPhase(){
        return this.playerStrategy.reinforce(this);
    }

    //Attack-Strategy

    private Player attackDefenderPlayer;
	private Country attackAttackerCountry;
    private Country attackDefenderCountry;
    private int attackAttackerDiceCount;
    private int attackDefenderDiceCount;
    private HashMap<Player, ArrayList<Country>> attackPlayerCountry;
    private ArrayList<String> attackGamePhaseDetails;

    public Player getAttackDefenderPlayer() {
        return attackDefenderPlayer;
    }

    public void setAttackDefenderPlayer(Player attackDefenderPlayer) {
        this.attackDefenderPlayer = attackDefenderPlayer;
    }

    public Country getAttackAttackerCountry() {
        return attackAttackerCountry;
    }

    public void setAttackAttackerCountry(Country attackAttackerCountry) {
        this.attackAttackerCountry = attackAttackerCountry;
    }

    public Country getAttackDefenderCountry() {
        return attackDefenderCountry;
    }

    public void setAttackDefenderCountry(Country attackDefenderCountry) {
        this.attackDefenderCountry = attackDefenderCountry;
    }

    public int getAttackAttackerDiceCount() {
        return attackAttackerDiceCount;
    }

    public void setAttackAttackerDiceCount(int attackAttackerDiceCount) {
        this.attackAttackerDiceCount = attackAttackerDiceCount;
    }

    public int getAttackDefenderDiceCount() {
        return attackDefenderDiceCount;
    }

    public void setAttackDefenderDiceCount(int attackDefenderDiceCount) {
        this.attackDefenderDiceCount = attackDefenderDiceCount;
    }

    public HashMap<Player, ArrayList<Country>> getattackPlayerCountry() {
        return attackPlayerCountry;
    }

    public void setAttackPlayerCountry(HashMap<Player, ArrayList<Country>> attackPlayerCountry) {
        this.attackPlayerCountry = attackPlayerCountry;
    }

    public ArrayList<String> getAttackGamePhaseDetails() {
        return attackGamePhaseDetails;
    }

    public void setAttackGamePhaseDetails(ArrayList<String> attackGamePhaseDetails) {
        this.attackGamePhaseDetails = attackGamePhaseDetails;
    }

    public ArrayList<Integer> getDiceResults() {
        return diceResults;
    }

    public void setDiceResults(ArrayList<Integer> diceResults) {
        this.diceResults = diceResults;
    }

    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(ArrayList<Card> playerCards) {
        this.playerCards = playerCards;
    }

    public boolean attackPhase(){
        return this.playerStrategy.attack(this);
    }

	/**
	 * This is a constructor of Player Class which sets playerId, name, and
	 * color.
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
	 * @return integer value of playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * This method is setting the value of playerId of a player object.
	 * @param playerId ID of player
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	/**
	 * This function is providing the assigned army number of a player.
	 * @return integer value of assigned armies
	 */
	public int getNumberOfInitialArmies() {
		return numberOfInitialArmies;
	}

	/**
	 * This is function is going to set the assigned army numbers in the specific object.
	 * @param numberOfInitialArmies number of initial armies
	 */
	public void setNumberOfInitialArmies(int numberOfInitialArmies) {
		this.numberOfInitialArmies = numberOfInitialArmies;
	}

	/**
	 * This function is going to return the reinforced army number.
	 * @return integer value number of reinforced army
	 */
	public int getNumberOfReinforcedArmies() {
		return numberOfReinforcedArmies;
	}

	/**
	 * This function is setting the reinforced army.
	 * @param noOfReinforcedArmies number of enforced armies
	 */
	public void setNumberOfReinforcedArmies(int noOfReinforcedArmies) {
		this.numberOfReinforcedArmies = noOfReinforcedArmies;
	}

	/**
	 * This function is going to return name of the player.
	 * @return string value.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * This is the setter function for the player's strategy.
	 * @param playerStrategy
	 */
	public void setPlayerStrategy(PlayerStrategy playerStrategy) {
		this.playerStrategy = playerStrategy;
	}

	/**
	 * This is the getter function for the player's strategy.
	 * @return playerStrategy
	 */
	public PlayerStrategy getPlayerStrategy() {
		return playerStrategy;
	}

	/**
	 * This function sets the name of the player.
	 * @param playerName name of player
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * This function returns the color.
	 * @return ENUM value of color
	 */
	public Colors getColor() {
		return color;
	}

	/**
	 * Setting the color.
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
	 * @return assignedListOfCountries
	 */
	public   ArrayList<Country> getAssignedListOfCountries() {
		return assignedListOfCountries;
	}
	
	/**
	 * Assigns the current country to player.
	 * @param country the country
	 */
	public void assignCountryToPlayer(Country country) {
		assignedListOfCountries.add(country);
	}
	
	/**
	 * Unassigns the current country to player.
	 * @param country the country
	 */
	public void unAssignCountryToPlayer(Country country) {
		assignedListOfCountries.remove(country);
	}


	/**
	 * This method will process attack for the selected player and for the defender player.
	 * @param defenderPlayer the defender player
	 * @param attackerCountry the attacker country
	 * @param defenderCountry the defender country
	 * @param attackerDiceCount the attacker dice count
	 * @param defenderDiceCount the defender dice count
	 * @param playerCountry the player country
	 * @param gamePhaseDetails Details of Game Phase
	 */
	public void attackPhaseActions(Player defenderPlayer, Country attackerCountry, Country defenderCountry, int attackerDiceCount, int defenderDiceCount,HashMap<Player, ArrayList<Country>> playerCountry,ArrayList<String> gamePhaseDetails) {
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
			gamePhaseDetails.add("Attacker dice - " + attackerDice + "  to Defender dice - " + defencerDice);
			if (attackerDice > defencerDice) {
				System.out.println("Attacker wins for dice " + (i + 1));
				gamePhaseDetails.add("Attacker wins for dice " + (i + 1));
				defenderCountry.decreaseArmyCount(1);

			} else {
				System.out.println("Defender wins for dice " + (i + 1));
				gamePhaseDetails.add("Defender wins for dice " + (i + 1));
				attackerCountry.decreaseArmyCount(1);
			}

			if (attackerCountry.getnoOfArmies() == 1) {
				System.out.println("Attacker not able to Attack ");
				gamePhaseDetails.add("Attacker not able to Attack ");
				break;
			} 
			if (defenderCountry.getnoOfArmies() == 0) {
				System.out.println("Defender lost all armies in " + (i + 1) + " dice roll");
				gamePhaseDetails.add("Defender lost all armies in " + (i + 1) + " dice roll");
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
			gamePhaseDetails.add(defenderCountry.getCountryName()+" is Conquered");
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
	 * @param diceCount the dice count
	 */
	public void diceRoller(int diceCount) {
		diceResults.clear();
		for (int i = 0; i < diceCount; i++) {
			diceResults.add(this.getRandomDiceNumber());
		}
	}

	/**
	 * This will generate the random integers between  1 to 6.
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
		int countriesCount = (int) Math.floor(playerCountry.get(this).stream().count() / 3);
		if (playerCountry.containsKey(this)) {
			ArrayList<Country> assignedCountries = playerCountry.get(this);

			List<Integer> assignedCountryIds = assignedCountries.stream().map(c -> c.getCountryId()).collect(Collectors.toList());

			for (Continent continent : continents) {
				List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId()).collect(Collectors.toList());

				boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);

				if (hasPlayerAllCountries){
					countriesCount += continent.getControlValue();
				}
			}
		}
		countriesCount = countriesCount+ initialArmiesafterExchange;
		return countriesCount;
	}
	

	/**
	 * This method checks whether the source and destination countries belongs to the player and moves the armies from source to destination.
	 * @return true if armies count increases or decreases
	 */


    public boolean checkFortificationCondition(Country sourceCountry, Country destinationCountry, int armies) {
        if (sourceCountry == null || destinationCountry == null) {
            System.out.println("Source or destination country is invalid!");
            return false;
        }
        if (armies == 0) {
            System.out.println("No armies to move");
            return false;
        }
        return true;
    }
	
	/**
	 * This method checks if the fortification condition is satisfied for further operations
	 * @param sourceCountry The source Country
	 * @param destinationCountry The destination Country
	 * @param armies Armies count
	 * @return boolean result based on condition passes
	 */


	/**
	 * Gets the checks if is conqured.
	 * @return the checks if is conqured
	 */
	public boolean getIsConqured() {
		return isConquered;
	}
	
	/**
	 * Sets the checks if is conqured.
	 * @param isConqueredTemp the new checks if is conqured
	 */
	public void setIsConqured(boolean isConqueredTemp) {
		isConquered = isConqueredTemp;
	}
	
	/**
	 * Gets the cards.
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
	 * @param card the card
	 */
	public void addCard(Card card) {
		playerCards.add(card);
	}

	/**
	 * set Conquer continents for the player.
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
	 * @return the conquerd continents
	 */
	public ArrayList<Continent> getConquerdContinents(){
		return playerContinents;
	}
	
	/**
	 * Sets the initial armies after exchange.
	 * @param armies the new initial armiesafter exchange
	 */
	public void setInitialArmiesafterExchange(int armies) {
		initialArmiesafterExchange = armies;
	}

	/**
	 * This method will perform operation required after conquering a country
	 * 
	 * @param defenderPlayer,
	 *            Player object
	 */
	public void conquerCountry(Player defenderPlayer) {
		getAttackDefenderCountry().setPlayerId(playerId);
		getAttackDefenderCountry().setCountryColor(getAttackAttackerCountry().getCountryColor());
        defenderPlayer.unAssignCountryToPlayer(getAttackDefenderCountry());
        assignedListOfCountries.add(getAttackDefenderCountry());
        attackPlayerCountry.get(this).add(getAttackDefenderCountry());
        attackPlayerCountry.get(defenderPlayer).remove(getAttackDefenderCountry());
        isConquered =true;
        attackGamePhaseDetails.add(getAttackDefenderCountry().getCountryName()+" is Conquered");
        // attacker has to put minimum one army defending country (By Game rules)
        getAttackAttackerCountry().decreaseArmyCount(1);
        getAttackDefenderCountry().increaseArmyCount(1);

        if (defenderPlayer.getAssignedListOfCountries().size() == 0) {
            ArrayList<Card> defendersCards = defenderPlayer.getCards();
            defenderPlayer.removeCards();
            for(Card card: defendersCards) {
                playerCards.add(card);
            }
        }
	}
	
	
	/**
	 * This method will perform operation required after conquering a country
	 * 
	 * @param defenderPlayer,
	 *            Player object
	 */
	public void conquerCountryAutomate(Player defenderPlayer,Country defenderCountry, Country attackerCountry) {
		defenderCountry.setPlayerId(playerId);
		defenderCountry.setCountryColor(attackerCountry.getCountryColor());
        defenderPlayer.unAssignCountryToPlayer(defenderCountry);
        assignedListOfCountries.add(defenderCountry);
        attackPlayerCountry.get(this).add(defenderCountry);
        attackPlayerCountry.get(defenderPlayer).remove(defenderCountry);
        isConquered =true;
        attackGamePhaseDetails.add(defenderCountry.getCountryName()+" is Conquered");
        // attacker has to put minimum one army defending country (By Game rules)
        attackerCountry.decreaseArmyCount(1);
        defenderCountry.increaseArmyCount(1);

        if (defenderPlayer.getAssignedListOfCountries().size() == 0) {
            ArrayList<Card> defendersCards = defenderPlayer.getCards();
            defenderPlayer.removeCards();
            for(Card card: defendersCards) {
                playerCards.add(card);
            }
        }
	}
	
	/**
	 * It is going to return the values of attack possible  countries who have more than one armies
	 * @return ArrayList of Countries
	 */
	public ArrayList<Country> getpossibleAttackerCountries() {
		ArrayList<Country> countries = new ArrayList<Country>();
		for (Country country : getAssignedListOfCountries()) {
			if (country.getnoOfArmies() > 1) {
				countries.add(country);
			}
		}
		return countries;
	}
	
	/**
	 * Returns allowable dices for attacking country.
	 * @param countryName the country name
	 * @return Integer
	 */
	public ArrayList<Country> getOthersNeighbouringCountriesOnlyObject(Country selectedCountry) {
		ArrayList<Country> allowableAttackingArmies = new ArrayList<Country>();
			
			ArrayList<Country> countryList = getAssignedListOfCountries();

			if (selectedCountry != null) {
				allowableAttackingArmies = selectedCountry.getNeighboursOfCountry();
				for (Country country : countryList) {
					allowableAttackingArmies.remove(country);
				}

			}
		
		return allowableAttackingArmies;
	}

	/**
	 * add player list 
	 */
	public void addPlayerList(ArrayList<Player> playerListTemp) {
		playerList= playerListTemp;
	}
	
	public Player getPlayer(int playerId) {
		
		for(int i=0; i<playerList.size(); i++){
			if(playerList.get(i).playerId == playerId) {
				return playerList.get(i);
			}
		}
		return this;
	}
	
	
	//Functions called by addSourceCountriesListener() from the GameController.
		/**
		 * This method returns the neighboring connected countries of a specific country.
		 * @param source source countries
		 * @return finalCountries countries
		 */
		public ArrayList<Country> getNeighbouringCountries(Country source) {

			System.out.print(connectedOwnCountries.toString());
			
			initialSourceCountry = source;

			ArrayList<Country> countriesAssignedToPlayer = new ArrayList<Country>();
			ArrayList<Country> finalCOuntries = new ArrayList<Country>();

			ArrayList<Country> countryList = this.getAssignedListOfCountries();
			ArrayList<Country> neighborCountriesName = new ArrayList<Country>();

			for (Country country : countryList) {
				countriesAssignedToPlayer.add(country);
				if (country==source) {
					for( Country country1 :  country.getNeighboursOfCountry()){
						neighborCountriesName.add(country1);
					}
				}
			}

			Iterator<Country> it = neighborCountriesName.iterator();
			while (it.hasNext()) {
				Country country = it.next();
				if (!countriesAssignedToPlayer.contains(country)){
					it.remove();
				}
			}

			if(neighborCountriesName!=null) {
				neighborCountriesName.removeAll(connectedOwnCountries);
				connectedOwnCountries.addAll(neighborCountriesName);
			}

			Iterator<Country> rec = neighborCountriesName.iterator();
			while (rec.hasNext()) {
				Country country = rec.next();
				getConnectedCountries(country, countryList);
			}

			System.out.println("1. Neighbouring Countries:"+neighborCountriesName.toString());
			System.out.println("1. Player's Countries:"+countriesAssignedToPlayer.toString());
			finalCOuntries.addAll(connectedOwnCountries);
			connectedOwnCountries.clear();
			return finalCOuntries;
		}


		/**
		 * This method recursively explores all the nodes connected to a country and returns the neighboring countries.
		 * @param source source countries
		 * @param countryList list of countries
		 *
		 */
		public void getConnectedCountries(Country source, ArrayList<Country> countryList) {
			System.out.println("source Country Name :" + source);

			ArrayList<Country> countriesAssignedToPlayer = new ArrayList<Country>();
			ArrayList<Country> neighborCountriesName = new ArrayList<Country>();

			for (Country country : countryList) {
				Country countryName = country;
				countriesAssignedToPlayer.add(countryName);
				if (country==source) {
					for( Country country1 :  country.getNeighboursOfCountry()){
						neighborCountriesName.add(country1);
					}
				}
			}

			Iterator<Country> it = neighborCountriesName.iterator();
			while (it.hasNext()) {
				Country country = it.next();
				if (!countriesAssignedToPlayer.contains(country)||country==initialSourceCountry){
					it.remove();
				}
			}

			if(neighborCountriesName!=null) {
				neighborCountriesName.removeAll(connectedOwnCountries);
				connectedOwnCountries.addAll(neighborCountriesName);
			}

			Iterator<Country> rec = neighborCountriesName.iterator();
			while (rec.hasNext()) {
				Country country = rec.next();
				getConnectedCountries(country, countryList);
			}

			System.out.println("1. Neighbouring Countries:"+neighborCountriesName.toString());
			System.out.println("1. Player's Countries:"+countriesAssignedToPlayer.toString());

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
