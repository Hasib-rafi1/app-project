package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;
import java.util.stream.Collectors;



import java.util.Collections;
import helper.InitialPlayerArmy;
import helper.GamePhase;
import helper.PrintConsoleAndUserInput;

// TODO: Auto-generated Javadoc
/**
 * Game model contains the class to create a model for the game. 
 * It is bounded with the Game Controller and the Board View.
 * @author Jaiganesh
 * @author Hasibul Huq
 * @author Gargi sharma
 * @version 1.0.0
 */

public class Game extends Observable {

	/** The map model. */
	private MapModel mapModel;
	
	/** The game phase. */
	private GamePhase gamePhase;
	
	/** The current player id. */
	private int currentPlayerId;
	
	/** The MINIMU M REINFORCEMEN T pl AYERS. */
	private int MINIMUM_REINFORCEMENT_PlAYERS = 3;

	/** The connected own countries. */
	private ArrayList<String> connectedOwnCountries = new ArrayList<String>();
	
	/** The initial source country. */
	private String initialSourceCountry;

	/** The print. */
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();

	/** The player list. */
	private ArrayList<Player> playerList = new ArrayList<Player>();
	
	/** The player country. */
	private HashMap<Player, ArrayList<Country>> playerCountry = new HashMap<>();

	/**
	 * Instantiates a new game.
	 *
	 * @param map the map
	 */
	//Initializes the map and the game phase of the game.
	public Game(MapModel map) {
		super();
		this.mapModel = map;
		this.setGamePhase(GamePhase.Startup);
	}


	//Functions called by the initializeGame() from the GameController. 
	/**
	 * This method adds the player to the game's player list.
	 * @param player get the player
	 */
	public void addPlayer(Player player) {
		this.playerList.add(player);
	}

	/**
	 * This method initializes the Game.
	 * It assigns the initial armies to the player.
	 * It randomly assigns the countries to the players. 
	 */
	public void startGame() {
		//Assigning the Initial armies.
		for(int i=0; i<playerList.size(); i++){
			playerList.get(i).setNumberOfInitialArmies(InitialPlayerArmy.getInitialArmyCount(playerList.size()));
			System.out.println("Player ID: "+playerList.get(i).getPlayerId()+" Player Name: "+playerList.get(i).getPlayerName()+" Player's Army: "+playerList.get(i).getNumberOfInitialArmies()+" Player's Color"+playerList.get(i).getColor());
		}

		int players_count = playerList.size();
		System.out.println("Player Count:"+players_count);
		int countries_count = mapModel.getCountryList().size();
		int players_id = 0;

		ArrayList<Integer> randomNumbers = new ArrayList<>();
		for(int i=0; i<countries_count; i++){
			randomNumbers.add(i);
		}
		Collections.shuffle(randomNumbers, new Random());

		for(int i=0; i<countries_count ; i++){
			if (players_id == players_count){
				players_id = 0;
			}

			Country assign_country = mapModel.getCountryList().get(randomNumbers.get(i));
			assignPlayerCountry(playerList.get(players_id),assign_country);
			assignUnassigned(playerList.get(players_id),assign_country);
			playerList.get(players_id).assignCountryToPlayer(assign_country);
			players_id++;
		}

		for (Map.Entry<Player, ArrayList<Country>> entry : playerCountry.entrySet()){
			Player key = entry.getKey();
			ArrayList<Country> value = entry.getValue();
			System.out.println("\n"+key.getPlayerName()+" countries: \n");
			for(Country aString : value){
				System.out.println(aString.getCountryName());
			}
		}
		notifyObserverslocal(this);
	}

	/**
	 * This method assigns the player to the corresponding country. 
	 * @param player player
	 * @param country country 
	 */
	public void assignPlayerCountry(Player player, Country country){
		if(playerCountry.containsKey(player)){
			playerCountry.get(player).add(country);
		}
		else{
			ArrayList<Country> assign_country = new ArrayList<>();
			assign_country.add(country);
			playerCountry.put(player, assign_country);
		}
		country.setCountryColor(player.getColor());
		country.setPlayerId(player.getPlayerId());
	}

	//Functions called by numberOfArmiesClickListener() from the GameController.
	/**
	 * This method adds armies to the country based on the startup or reinforcement game phase.
	 * @param countryName name of country
	 */
	public void addingCountryArmy(String countryName){
		if(gamePhase == gamePhase.Attack || gamePhase == gamePhase.Fortification){
			print.consoleOut("Armies can't be added during Attack or Fortification Phase.");
			return;
		}
		if(gamePhase == gamePhase.Startup) {
			Boolean added = addingStartupCountryArmy(countryName);
			if(added)
			{
				setupNextPlayerTurn();
			}
		}
		else if (gamePhase == gamePhase.Reinforcement){
			addingReinforcementCountryArmy(countryName);
		}
		updateGame();
		notifyObserverslocal(this);
	}

	/**
	 * This method add armies to the country during the startup phase and returns true when successful.
	 * @param countryName name of country
	 * @return true if valid phase otherwise false
	 */
	public boolean addingStartupCountryArmy(String countryName){
		if(this.gamePhase != gamePhase.Startup){
			print.consoleOut("Not a Valid Phase");
			return false;
		}

		Player player = this.getCurrentPlayer();

		if(player == null){
			print.consoleOut("Player ID"+currentPlayerId+"does not exist.");
			return false;
		}
		if(player.getNumberOfInitialArmies() == 0){
			print.consoleOut("Player "+player.getPlayerName()+"Doesn't have any Armies.");
			this.setupNextPlayerTurn();
			return false;
		}
		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny().orElse(null);
		if (country == null) {
			print.consoleOut("Country name -  " + countryName + " does not exist!");
			return false;
		}
		assignUnassigned(player,country);
		return true;
	}

	/**
	 * This method adds armies to the country during the reinforcement phase and returns when successful. 
	 * @param countryName name of country
	 * @return true
	 */
	public boolean addingReinforcementCountryArmy(String countryName){
		if(this.gamePhase != gamePhase.Reinforcement){
			print.consoleOut("Not a Valid Phase");
			return false;
		}

		Player player = this.getCurrentPlayer();

		if(player == null){
			print.consoleOut("Player ID"+currentPlayerId+"does not exist.");
			return false;
		}
		if(player.getNumberOfReinforcedArmies() == 0){
			print.consoleOut("Player "+player.getPlayerName()+": Doesn't have any Armies.");
			return false;
		}
		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny().orElse(null);
		if (country == null) {
			print.consoleOut("Country Name: " + countryName + " does not exist!");
			return false;
		}
		assignReinforcement(player,country);
		return true;
	}

	/**
	 * This method initializes the reinforcement phase for each player by adding corresponding number of armies. 
	 */
	public void reinforcementPhaseSetup() {
		Player player = getCurrentPlayer();

		int countries_count = calculationForNumberOfArmiesInReinforcement(player);

		if (playerCountry.containsKey(player)) {
			ArrayList<Country> assignedCountries = playerCountry.get(player);

			List<Integer> assignedCountryIds = assignedCountries.stream().map(c -> c.getCountryId()).collect(Collectors.toList());

			ArrayList<Continent> continents = mapModel.getContinentList();

			for (Continent continent : continents) {
				List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId()).collect(Collectors.toList());

				boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);

				if (hasPlayerAllCountries){
					countries_count += continent.getControlValue();
				}
			}
		}

		countries_count = countries_count < MINIMUM_REINFORCEMENT_PlAYERS ? MINIMUM_REINFORCEMENT_PlAYERS : countries_count;
		System.out.println("Countries Count:" + countries_count);
		player.setNumberOfReinforcedArmies(countries_count);
	}

	/**
	 * This method calculates the corresponding reinforcement armies from a particular player from the number of countries owned by the layer.
	 * @param player Player
	 * @return total number of armies in reinforcement
	 */
	public int calculationForNumberOfArmiesInReinforcement(Player player) {
		return (int) Math.floor(playerCountry.get(player).stream().count() / 3);
	}

	/**
	 * This method updates the game phase of the game during the end of every Startup, Reinforcement and Fortification phase.
	 */
	public void updateGame() {
		if (this.getGamePhase() == gamePhase.Startup) {
			long pendingPlayersCount = playerList.stream().filter(p -> p.getNumberOfInitialArmies() > 0).count();
			System.out.println(pendingPlayersCount);

			if (pendingPlayersCount == 0) {
				this.setGamePhase(gamePhase.Reinforcement);
				currentPlayerId = 0;
				reinforcementPhaseSetup();
			}
		} 
		else if (this.getGamePhase() == gamePhase.Reinforcement) {	
			if (getCurrentPlayer().getNumberOfReinforcedArmies() == 0) {
				this.setGamePhase(gamePhase.Attack);
			}

		} 
		else if (this.getGamePhase() ==  gamePhase.Attack) {
			this.setGamePhase(gamePhase.Fortification);
			notifyObserverslocal(this);
		}
		else if (this.getGamePhase() == gamePhase.Fortification) {
			this.setGamePhase(gamePhase.Reinforcement);
			notifyObserverslocal(this);
		}
	}

	//Functions called by addSourceCountriesListener() from the GameController.
	/**
	 * This method returns the neighboring connected countries of a specific country.
	 * @param source source countries
	 * @return finalCOuntries countries
	 */
	public ArrayList<String> getNeighbouringCountries(String source) {
		System.out.println("source Country Name :" + source);
		System.out.print(connectedOwnCountries.toString());
		Player currentPlayer = this.getCurrentPlayer();
		initialSourceCountry = source;

		ArrayList<String> countriesAssignedToPlayer = new ArrayList<String>();
		ArrayList<String> finalCOuntries = new ArrayList<String>();

		ArrayList<Country> countryList = playerCountry.get(currentPlayer);
		ArrayList<String> neighborCountriesName = new ArrayList<>();

		for (Country country : countryList) {
			String countryName = country.getCountryName();
			countriesAssignedToPlayer.add(countryName);
			if (country.getCountryName().equals(source)) {
				for( Country country1 :  country.getNeighboursOfCountry()){
					neighborCountriesName.add(country1.getCountryName());
				}
			}
		}

		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) {
			String country = it.next();
			if (!countriesAssignedToPlayer.contains(country)){
				it.remove();
			}
		}

		if(neighborCountriesName!=null) {
			neighborCountriesName.removeAll(connectedOwnCountries);
			connectedOwnCountries.addAll(neighborCountriesName);
		}

		Iterator<String> rec = neighborCountriesName.iterator();
		while (rec.hasNext()) {
			String country = rec.next();
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
	 *
	 * @param source source countries
	 * @param countryList list of countries
	 * @return the connected countries
	 */
	public void getConnectedCountries(String source, ArrayList<Country> countryList) {
		System.out.println("source Country Name :" + source);

		ArrayList<String> countriesAssignedToPlayer = new ArrayList<String>();
		ArrayList<String> neighborCountriesName = new ArrayList<String>();

		for (Country country : countryList) {
			String countryName = country.getCountryName();
			countriesAssignedToPlayer.add(countryName);
			if (country.getCountryName().equals(source)) {
				for( Country country1 :  country.getNeighboursOfCountry()){
					neighborCountriesName.add(country1.getCountryName());
				}
			}
		}

		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) {
			String country = it.next();
			if (!countriesAssignedToPlayer.contains(country)||country.equals(initialSourceCountry)){
				it.remove();
			}
		}

		if(neighborCountriesName!=null) {
			neighborCountriesName.removeAll(connectedOwnCountries);
			connectedOwnCountries.addAll(neighborCountriesName);
		}

		Iterator<String> rec = neighborCountriesName.iterator();
		while (rec.hasNext()) {
			String country = rec.next();
			getConnectedCountries(country, countryList);
		}

		System.out.println("1. Neighbouring Countries:"+neighborCountriesName.toString());
		System.out.println("1. Player's Countries:"+countriesAssignedToPlayer.toString());

	}

	/**
	 * This method returns the number of armies assigned to a specific country.
	 * @param sourceCountryName source country names
	 * @return noOfArmies number of armies
	 */
	public int getArmiesAssignedToCountry(String sourceCountryName) {
		Player currentPlayer = this.getCurrentPlayer();
		int noOfArmies = 0;

		for (Country country : playerCountry.get(currentPlayer)) {
			if (country.getCountryName().equals(sourceCountryName)) {
				noOfArmies = country.getnoOfArmies();
			}
		}
		return noOfArmies;
	}

	//Functions called by addMoveArmyButtonListener() from GameController.
	/**
	 * This method checks whether the source and destination countries belongs to the player and moves the armies from source to destination.
	 * @param source  source as string
	 * @param destination destination countries as string
	 * @param armies count of armies as int
	 * @return true
	 */
	public boolean fortificationPhase(String source, String destination, int armies){
		Player player = getCurrentPlayer();

		Country sourceCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(source)).findAny().orElse(null);

		Country destinationCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(destination)).findAny().orElse(null);
		// player class function
		boolean sucesss = player.fortificationPhase(sourceCountry, destinationCountry, armies);
		
	
		this.setupNextPlayerTurn();
		setGamePhase(gamePhase.Reinforcement);
		reinforcementPhaseSetup();
		notifyObserverslocal(this);
		return true;
	}
	
	/**
	 * This function skip the fortification phase
	 */
	public void skipFortification() {
		this.setupNextPlayerTurn();
		setGamePhase(gamePhase.Reinforcement);
		reinforcementPhaseSetup();
		notifyObserverslocal(this);
	}

	//Functions called by other functions within the Game model.

	//Getter and Setter functions of Map. 

	/**
	 * This function is used to get map.
	 *
	 * @return mapModel
	 */
	public MapModel getMap() {
		return mapModel;
	}


	/**
	 *  This is used to set map.
	 *
	 * @param map map
	 */
	public void setMap(MapModel map) {
		this.mapModel = map;
	}



	/**
	 *This is used to get game phase.
	 * @return gamePhase getGamePhase
	 */
	public GamePhase getGamePhase() {
		return gamePhase;
	}


	/**
	 * This is used to set game phase.
	 *
	 * @param gamePhase Game phase
	 */
	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}


	/**
	 * Getter function for all the Player.
	 * @return playerList list of players
	 */
	public ArrayList<Player> getAllPlayers() {
		return playerList;
	}


	/**
	 * Getter function for Current Player Id.
	 * @return currentPlayerId, current id of player
	 */
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}


	/**
	 * Getter function for Current Player.
	 * @return currentPlayer current player
	 */
	public Player getCurrentPlayer() {
		Player currentPlayer = playerList.get(currentPlayerId);
		return currentPlayer;
	}


	/**
	 * Getter function for getting all the Current Player Countries using current player's ID.
	 * @return playerCountry arraylist
	 */
	public ArrayList<Country> getCurrentPlayerCountries() {
		Player currentPlayer = playerList.get(currentPlayerId);
		return playerCountry.get(currentPlayer);
	}


	/**
	 * Getter function for getting all the Current Player Countries using current player's object.
	 * @param currentPlayer current player 
	 * @return playerCountry player country
	 */
	public ArrayList<Country> getPlayersCountry(Player currentPlayer) {
		return playerCountry.get(currentPlayer);
	}



	/**
	 * Function the sets the next player's turn.
	 */
	public void setupNextPlayerTurn(){
		print.consoleOut("Current Player ID:"+currentPlayerId);
		currentPlayerId++;
		if(currentPlayerId==playerList.size()){
			currentPlayerId = 0;
		}
		if(playerCountry.get(getCurrentPlayer()).size()==0) {
			setupNextPlayerTurn();
		}
	}


	/**
	 * Function the returns the armies of the country of the current player.
	 *
	 * @param countryName anme of the country
	 * @return the country armies
	 */
	public void getCountryArmies(String countryName) {
		int armies_number = 0;
		Player player = this.getCurrentPlayer();
		for(Country country: playerCountry.get(player)){
			if(country.getCountryName().equals(countryName)){
				armies_number = country.getnoOfArmies();
			}
		}
	}


	/**
	 * Function that moves an army from the player's initial army to the country's army.
	 * @param player player 
	 * @param country country
	 */
	public void assignUnassigned(Player player, Country country){
		player.decreasenumberOfInitialArmies();
		country.increaseArmyCount();
	}


	/**
	 * Function that moves an army from the player's reinforcement army to the country's army.
	 * @param player player 
	 * @param country country
	 */
	public void assignReinforcement(Player player, Country country){
		player.decreaseReinforcementArmy();
		country.increaseArmyCount();
	}


	/**
	 * This is the method that notifies all the observers connected to the observable.
	 * @param game game view
	 */
	private void notifyObserverslocal(Game game){
		setChanged();
		notifyObservers(this);
	}

	/**
	 * Returns number of dices for attacking / defending country.
	 *
	 * @param countryName the country name
	 * @param playerStatus the player status
	 * @return Integer
	 */
	public int getMaximumDices(String countryName, String playerStatus) {
		int allowableAttackingArmies = 0;
		if (this.gamePhase ==GamePhase.Attack) {
			// Will also add validation if the attacker is assigned to player or not

			Country c = mapModel.getCountryFromName(countryName);

			if (c != null) {
				allowableAttackingArmies = getCurrentPlayer().getNumberDices(c, playerStatus);
			}
		}
		return allowableAttackingArmies;
	}

	/**
	 * Returns allowable dices for attacking country.
	 *
	 * @param countryName the country name
	 * @return Integer
	 */
	public ArrayList<String> getOthersNeighbouringCountriesOnly(String countryName) {
		ArrayList<String> allowableAttackingArmies = new ArrayList<String>();
		if (this.gamePhase ==GamePhase.Attack) {
			// Will also add validation if the attacker is assigned to player or not

			Country c = mapModel.getCountryFromName(countryName);
			Player currentPlayer = this.getCurrentPlayer();
			ArrayList<Country> countryList = playerCountry.get(currentPlayer);

			if (c != null) {
				allowableAttackingArmies = c.getNeighboursString();
				for (Country country : countryList) {
					String countryName1 = country.getCountryName();
					allowableAttackingArmies.remove(countryName1);
				}

			}
		}
		return allowableAttackingArmies;
	}

	/**
	 * Method for  attack phase where attack will handled.
	 *
	 * @param attackerCountry the attacker country
	 * @param defenderCountry the defender country
	 * @param attackerDiceCount the attacker dice count
	 * @param defendergDiceCount the defenderg dice count
	 * @return true, if attack done
	 */
	public Boolean attackPhase(String attackerCountry, String defenderCountry, int attackerDiceCount, int defendergDiceCount) {

		Country attCountry = mapModel.getCountryFromName(attackerCountry);
		Country defCountry = mapModel.getCountryFromName(defenderCountry);

		if (attCountry == null || defCountry == null) {
			return false;
		}

		if (defCountry.getnoOfArmies() < defendergDiceCount) {
			return false;
		}
		Player defenderPlayer = playerList.stream().filter(p -> p.getPlayerId()==defCountry.getPlayerId())
				.findAny().orElse(null);

		if (defenderPlayer == null) {
			return false;
		}

		getCurrentPlayer().attackPhaseActions(defenderPlayer, attCountry, defCountry, attackerDiceCount, defendergDiceCount,playerCountry);

		//playerCountry;
		if (isMapConcured()) {
			System.out.println("Congratulation!"+this.getCurrentPlayer().getPlayerName() + ": You Win.");
		} else if (!checkAttackPossible()) {
			updateGame();
		}

		notifyObserverslocal(this);
		if(defCountry.getPlayerId()==attCountry.getPlayerId()&& attCountry.getnoOfArmies()>1) {
			return true;
		}

		return false;
	}
	
	/**
	 * Checks if is map concured.
	 *
	 * @return true, if is map concured
	 */
	public boolean isMapConcured() {
		if(mapModel.getCountryList().size() == playerCountry.get(this.getCurrentPlayer()).size()) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * method to get countries from the attackers country where number of armies are getter than 1.
	 *
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getAttackPossibleCountries() {
		ArrayList<String> attackerCountry = new ArrayList<String>();
		ArrayList<Country> countryList = this.getCurrentPlayerCountries();
		for (int i = 0; i < countryList.size(); i++) {
			Country temp_cname = countryList.get(i);
			if (temp_cname.getnoOfArmies()>1) {
				attackerCountry.add(temp_cname.getCountryName());
			}
		}
		return attackerCountry;

	}

	/**
	 * This method is to check current user can attack or not.
	 * @return boolean
	 */
	public boolean checkAttackPossible() {
		ArrayList<String> attackerPossibleCountries = getAttackPossibleCountries();
		if (attackerPossibleCountries.size() == 0) {
			return false;
		}else {
			for (String countryName : attackerPossibleCountries) {
				ArrayList<String> neighborCountries = getOthersNeighbouringCountriesOnly(countryName);
				if (neighborCountries.size() > 0) {
					return true;
				}
			}
			return false;
		}
	}




	/**
	 * Method for performing All out attack phase.
	 *
	 * @param attackerCountry the attacker country
	 * @param defenderCountry the defender country
	 * @return true, if attack phase out
	 */
	public Boolean attackAllOutPhase(String attackerCountry, String defenderCountry) {

		Country attCountry = mapModel.getCountryFromName(attackerCountry);
		Country defCountry = mapModel.getCountryFromName(defenderCountry);


		if (attCountry == null || defCountry == null) {
			return false;
		}

		while ((attCountry.getPlayerId()!=defCountry.getPlayerId()) && attCountry.getnoOfArmies() > 1) {
			int attackerDiceCount = this.getMaximumDices(attackerCountry, "Attacker");
			int defenderDiceCount = this.getMaximumDices(defenderCountry, "Defender");

			attackPhase(attackerCountry, defenderCountry, attackerDiceCount, defenderDiceCount);
		}
		notifyObserverslocal(this);
		if(defCountry.getPlayerId()==attCountry.getPlayerId()&& attCountry.getnoOfArmies()>1) {
			return true;
		}
		return false;
	}
	/**
	 * move Armies after attack
	 * @param attackersCountry
	 * @param atteckersNewCountry
	 * @param attackerMoveArmies
	 */
	public void moveArmies(Country attackersCountry, Country atteckersNewCountry, int attackerMoveArmies) {
		attackersCountry.decreaseArmyCount(attackerMoveArmies);
		atteckersNewCountry.increaseArmyCount(attackerMoveArmies);
		notifyObserverslocal(this);
		
	}
	


	/**
	 * Gets the percentage of map controlled by every player.
	 *
	 * @return the percentage of map controlled by every player
	 */
	public HashMap<Integer, Float> getPercentageOfMapControlledByEveryPlayer() {
		float totalNumberOfCountries = 0;
		ArrayList<Continent> allContinents = mapModel.getContinentList();
		for (Continent continent : allContinents) {
			ArrayList<Country> country = continent.getCountryList();
			totalNumberOfCountries = totalNumberOfCountries + country.size();
			//System.out.println(totalNumberOfCountries+"====="+country.size());
		}

		// store the percentage in a hashmap with the player id.
		HashMap<Integer, Float> mapPercentageStoredInMap = new HashMap<Integer, Float>();
		for (Player player : playerList) {

			// get size of player country list
			int playerCountries = playerCountry.get(player).size();

			// find percentage
			float percentage = (playerCountries / totalNumberOfCountries) * 100;			

			// get player id and percentage and then put in map
			int playerId = player.getPlayerId();			
			mapPercentageStoredInMap.put(playerId, percentage);
		}
		return mapPercentageStoredInMap;

	}
	public HashMap<Integer, Integer> getNumberOfContinentsControlledForEachPlayer() {
		HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		ArrayList<Continent> allContinents = this.mapModel.getContinentList();
		for (Player player : this.playerList) {
			boolean goToOuterLoop = false;
			int numberOfContinentsAquired = 0;
			for (Continent continent : allContinents) {
				for (Country country : continent.getCountryList()) {
					if (player.getAssignedListOfCountries().contains(country)) {
					} else {
						goToOuterLoop = true;
						break;
					}
				}
				if (goToOuterLoop) {
					goToOuterLoop = false;
					continue;
				}
				numberOfContinentsAquired++;
			}
			returnMap.put(player.getPlayerId(), numberOfContinentsAquired);
		}
		return returnMap;
	}
	
	public HashMap<Integer, Integer> getNumberOfArmiesForEachPlayer() {
		HashMap<Integer, Integer> returnMap = new HashMap<Integer, Integer>();
		for (Player player : this.playerList) {
			for (Country country : player.getAssignedListOfCountries()) {
				int totalArmies = country.getnoOfArmies();
				if(returnMap.containsKey(player.getPlayerId())) 
				{
					totalArmies += returnMap.get(player.getPlayerId());
				}
				returnMap.put(player.getPlayerId(), totalArmies);
			}
		}
		return returnMap;
	}


}
