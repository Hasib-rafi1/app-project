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

/**
 * Game model contains the class to create a model for the game. 
 * It is bounded with the Game Controller and the Board View.
 * @author Jaiganesh
 * @author Hasibul Huq
 * @version 1.0.0
 */
public class Game extends Observable {

	private MapModel mapModel;
	private GamePhase gamePhase;
	private int currentPlayerId;
	private int MINIMUM_REINFORCEMENT_PlAYERS = 3;

	private ArrayList<String> connectedOwnCountries = new ArrayList<String>();
	private String initialSourceCountry;

	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();

	private ArrayList<Player> playerList = new ArrayList<Player>();
	private HashMap<Player, ArrayList<Country>> playerCountry = new HashMap<>();

	//Initializes the map and the game phase of the game.
	public Game(MapModel map) {
		super();
		this.mapModel = map;
		this.setGamePhase(GamePhase.Startup);
	}


	//Functions called by the initializeGame() from the GameController. 
	/**
	 * This method adds the player to the game's player list.
	 * @param player
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
	 * This method assigns the player to the corresponding coutry. 
	 * @param player
	 * @param country
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
	 * @param countryName
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
	 * @param countryName
	 * @return
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
	 * @param countryName
	 * @return
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
	 * @param player
	 * @return
	 */
	public int calculationForNumberOfArmiesInReinforcement(Player player) {
		return (int) Math.floor(playerCountry.get(player).stream().count() / 3);
	}

	/**
	 * This method updates the game phase of the game during the end of every Startup, Reinforcement and Fortification phase.
	 */
	private void updateGame() {
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
				this.setGamePhase(gamePhase.Fortification);
			}

		} 
		else if (this.getGamePhase() == gamePhase.Fortification) {
			this.setGamePhase(gamePhase.Reinforcement);
		}
	}


	//Functions called by addSourceCountriesListener() from the GameController.
	/**
	 * This method returns the neighboring connected countries of a specific country.
	 * @param source
	 * @return
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
	 * @param source
	 * @return
	 */
	public void getConnectedCountries(String source, ArrayList<Country> countryList) 
	{
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
	 * @param sourceCountryName
	 * @return
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
	 * @param source
	 * @param destination
	 * @param armies
	 * @return
	 */
	public boolean fortificationPhase(String source, String destination, int armies){
		Player player = getCurrentPlayer();

		Country sourceCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(source)).findAny().orElse(null);

		Country destinationCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(destination)).findAny().orElse(null);


		if (sourceCountry == null || destinationCountry == null) {
			print.consoleOut("Source or destination country is invalid!");
			return false;
		}

		if (armies == 0) {
			print.consoleOut("No armies to move");
			return true;
		}
		sourceCountry.decreaseArmyCount(armies);
		destinationCountry.increaseArmyCount(armies);
		this.setupNextPlayerTurn();
		setGamePhase(gamePhase.Reinforcement);
		reinforcementPhaseSetup();
		notifyObserverslocal(this);
		return true;
	}


	//Functions called by other functions within the Game model.

	//Getter and Setter functions of Map. 
	public MapModel getMap() {
		return mapModel;
	}
	public void setMap(MapModel map) {
		this.mapModel = map;
	}

	//Getter and Setter functions of Game Phase.
	public GamePhase getGamePhase() {
		return gamePhase;
	}
	public void setGamePhase(GamePhase gamePhase) {
		this.gamePhase = gamePhase;
	}

	//Getter function for all the Player.
	public ArrayList<Player> getAllPlayers() {
		return playerList;
	}

	//Getter function for Current Player Id.
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	//Getter function for Current Player.
	public Player getCurrentPlayer() {
		Player currentPlayer = playerList.get(currentPlayerId);
		return currentPlayer;
	}

	//Getter function for getting all the Current Player Countries using current player's ID.
	public ArrayList<Country> getCurrentPlayerCountries() {
		Player currentPlayer = playerList.get(currentPlayerId);
		return playerCountry.get(currentPlayer);
	}

	//Getter function for getting all the Current Player Countries using current player's object.
	public ArrayList<Country> getPlayersCountry(Player currentPlayer) {
		return playerCountry.get(currentPlayer);
	}
	//Function the sets the next player's turn.
	public void setupNextPlayerTurn(){
		print.consoleOut("Current Player ID:"+currentPlayerId);
		currentPlayerId++;
		if(currentPlayerId==playerList.size()){
			currentPlayerId = 0;
		}
	}

	//Function the returns the armies of the country of the current player.
	public void getCountryArmies(String countryName) 
	{
		int armies_number = 0;
		Player player = this.getCurrentPlayer();
		for(Country country: playerCountry.get(player)){
			if(country.getCountryName().equals(countryName)){
				armies_number = country.getnoOfArmies();
			}
		}
	}

	//Function that moves an army from the player's initial army to the country's army.
	public void assignUnassigned(Player player, Country country){
		player.decreasenumberOfInitialArmies();
		country.increaseArmyCount();
	}

	//Function that moves an army from the player's reinforcement army to the country's army.
	public void assignReinforcement(Player player, Country country){
		player.decreaseReinforcementArmy();
		country.increaseArmyCount();
	}

	//Function that notifies all the observers connected to the observable.
	private void notifyObserverslocal(Game game){
		setChanged();
		notifyObservers(this);
	}
}
