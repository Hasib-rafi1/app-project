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
import helper.InitialPlayerArmy;

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


	public Game(MapModel map) 
	{
		super();
		this.mapModel = map;
		this.setGamePhase(GamePhase.Startup);
	}

	public void startGame() 
	{
		//Assigning the Initial armies.
		for(int i=0; i<playerList.size(); i++)
		{
			playerList.get(i).setNumberOfInitialArmies(InitialPlayerArmy.getInitialArmyCount(playerList.size()));
			System.out.println("Player ID:"+playerList.get(i).getPlayerId()+" Player Name:"+playerList.get(i).getPlayerName()+" Player's Army:"+playerList.get(i).getNumberOfInitialArmies()+"Player's Color"+playerList.get(i).getColor());
		}

		int players_count = playerList.size();
		System.out.println("Player Count:"+players_count);
		int countries_count = mapModel.getCountryList().size();
		int players_id = 0;

		ArrayList<Integer> randomNumbers = new ArrayList<>();
		for(int i=0; i<countries_count; i++)
		{
			randomNumbers.add(i);
		}
		Collections.shuffle(randomNumbers, new Random());

		for(int i=0; i<countries_count ; i++)
		{
			if (players_id == players_count)
			{
				players_id = 0;
			}

			Country assign_country = mapModel.getCountryList().get(randomNumbers.get(i));
			assignPlayerCountry(playerList.get(players_id),assign_country);
			System.out.println(players_id+"+"+assign_country);
			assignUnassigned(playerList.get(players_id),assign_country);
			players_id++;
		}

		for (Map.Entry<Player, ArrayList<Country>> entry : playerCountry.entrySet())
		{
			Player key = entry.getKey();
			ArrayList<Country> value = entry.getValue();
			System.out.println("\n"+key.getPlayerName()+" countries: \n");
			for(Country aString : value)
			{
				System.out.println(aString.getCountryName());
			}
		}
		notifyObserverslocal(this);
	}

	public void addPlayer(Player player) 
	{
		this.playerList.add(player);
	}

	public void assignPlayerCountry(Player player, Country country)
	{
		if(playerCountry.containsKey(player))
		{
			playerCountry.get(player).add(country);
		}
		else
		{
			ArrayList<Country> assign_country = new ArrayList<>();
			assign_country.add(country);
			playerCountry.put(player, assign_country);
		}
		country.setCountryColor(player.getColor());
		country.setPlayerId(player.getPlayerId());
	}

	public void addingCountryArmy(String countryName)
	{
		if(gamePhase == gamePhase.Attack || gamePhase == gamePhase.Fortification)
		{
			print.consoleOut("Armies can't be added during Attack or Fortification Phase.");
			return;
		}
		if(gamePhase == gamePhase.Startup) 
		{
			Boolean added = addingStartupCountryArmy(countryName);
			if(added)
			{
				setupNextPlayerTurn();
			}
		}
		else if (gamePhase == gamePhase.Reinforcement)
		{
			addingReinforcementCountryArmy(countryName);
		}
		updateGame();
		notifyObserverslocal(this);
	}

	public boolean addingStartupCountryArmy(String countryName)
	{
		if(this.gamePhase != gamePhase.Startup)
		{
			print.consoleOut("Not a Valid Phase");
			return false;
		}

		Player player = this.getCurrentPlayer();

		if(player == null)
		{
			print.consoleOut("Player ID"+currentPlayerId+"does not exist.");
			return false;
		}
		if(player.getNumberOfInitialArmies() == 0)
		{
			print.consoleOut("Player "+player.getPlayerName()+"Doesn't have any Armies.");
			this.setupNextPlayerTurn();
			return false;
		}
		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny().orElse(null);
		if (country == null) 
		{
			print.consoleOut("Country name -  " + countryName + " does not exist!");
			return false;
		}
		assignUnassigned(player,country);
		return true;
	}

	public boolean addingReinforcementCountryArmy(String countryName)
	{
		if(this.gamePhase != gamePhase.Reinforcement)
		{
			print.consoleOut("Not a Valid Phase");
			return false;
		}

		Player player = this.getCurrentPlayer();

		if(player == null)
		{
			print.consoleOut("Player ID"+currentPlayerId+"does not exist.");
			return false;
		}
		if(player.getNumberOfReinforcedArmies() == 0)
		{
			print.consoleOut("Player "+player.getPlayerName()+": Doesn't have any Armies.");
			return false;
		}
		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny().orElse(null);
		if (country == null) 
		{
			print.consoleOut("Country Name: " + countryName + " does not exist!");
			return false;
		}
		assignReinforcement(player,country);
		return true;
	}

	public void assignUnassigned(Player player, Country country)
	{
		player.decreasenumberOfInitialArmies();
		country.increaseArmyCount();
	}

	public void assignReinforcement(Player player, Country country)
	{
		player.decreaseReinforcementArmy();
		country.increaseArmyCount();
	}

	public void setupNextPlayerTurn()
	{
		print.consoleOut("Current Player ID:"+currentPlayerId);
		currentPlayerId++;
		if(currentPlayerId==playerList.size())
		{
			currentPlayerId = 0;
		}
	}

	public int calculationForNumberOfArmiesInReinforcement(Player player) {
		return (int) Math.floor(playerCountry.get(player).stream().count() / 3);
	}
	public void reinforcementPhaseSetup() 
	{
		Player player = getCurrentPlayer();

		int countries_count = calculationForNumberOfArmiesInReinforcement(player);

		if (playerCountry.containsKey(player)) 
		{
			ArrayList<Country> assignedCountries = playerCountry.get(player);

			List<Integer> assignedCountryIds = assignedCountries.stream().map(c -> c.getCountryId()).collect(Collectors.toList());

			ArrayList<Continent> continents = mapModel.getContinentList();

			for (Continent continent : continents) 
			{
				List<Integer> continentCountryIds = continent.getCountryList().stream().map(c -> c.getCountryId()).collect(Collectors.toList());

				boolean hasPlayerAllCountries = assignedCountryIds.containsAll(continentCountryIds);

				if (hasPlayerAllCountries)
				{
					countries_count += continent.getControlValue();
				}
			}
		}

		countries_count = countries_count < MINIMUM_REINFORCEMENT_PlAYERS ? MINIMUM_REINFORCEMENT_PlAYERS : countries_count;
		System.out.println("Countries Count:" + countries_count);
		player.setNumberOfReinforcedArmies(countries_count);
	}

	public void getCountryArmies(String countryName) 
	{
		int armies_number = 0;
		Player player = this.getCurrentPlayer();
		for(Country country: playerCountry.get(player))
		{
			if(country.getCountryName().equals(countryName))
			{
				armies_number = country.getnoOfArmies();
			}
		}
	}

	public boolean fortificationPhase(String source, String destination, int armies)
	{
		Player player = getCurrentPlayer();

		Country sourceCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(source)).findAny().orElse(null);

		Country destinationCountry = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(destination)).findAny().orElse(null);


		if (sourceCountry == null || destinationCountry == null) 
		{
			print.consoleOut("Source or destination country is invalid!");
			return false;
		}

		if (armies == 0) 
		{
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

	private void updateGame() 
	{
		if (this.getGamePhase() == gamePhase.Startup) 
		{
			long pendingPlayersCount = playerList.stream().filter(p -> p.getNumberOfInitialArmies() > 0).count();
			System.out.println(pendingPlayersCount);

			if (pendingPlayersCount == 0) 
			{
				this.setGamePhase(gamePhase.Reinforcement);
				currentPlayerId = 0;
				reinforcementPhaseSetup();
			}
		} 
		else if (this.getGamePhase() == gamePhase.Reinforcement) 
		{	
			if (getCurrentPlayer().getNumberOfReinforcedArmies() == 0) 
			{
				this.setGamePhase(gamePhase.Fortification);
			}

		} 
		else if (this.getGamePhase() == gamePhase.Fortification) 
		{
			this.setGamePhase(gamePhase.Reinforcement);
		}
	}

	public ArrayList<Player> getAllPlayers() 
	{
		return playerList;
	}

	public Player getCurrentPlayer() 
	{
		Player currentPlayer = playerList.get(currentPlayerId);
		return currentPlayer;
	}

	public ArrayList<Country> getCurrentPlayerCountries() 
	{
		Player currentPlayer = playerList.get(currentPlayerId);
		return playerCountry.get(currentPlayer);
	}

	public ArrayList<Country> getPlayersCountry(Player currentPlayer) 
	{
		return playerCountry.get(currentPlayer);
	}

	public ArrayList<String> getNeighbouringCountries(String source) 
	{
		System.out.println("source Country Name :" + source);

		Player currentPlayer = this.getCurrentPlayer();
		initialSourceCountry = source;

		ArrayList<String> countriesAssignedToPlayer = new ArrayList<String>();
		ArrayList<String> neighborCountriesName = null;
		ArrayList<Country> countryList = playerCountry.get(currentPlayer);
			for (Country country : countryList) 
			{
				String countryName = country.getCountryName();
				countriesAssignedToPlayer.add(countryName);
				if (country.getCountryName().equals(source)) 
				{
					neighborCountriesName = country.getNeighboursString();
				}
			}
			
			Iterator<String> it = neighborCountriesName.iterator();
			while (it.hasNext()) 
			{
				String country = it.next();
				if (!countriesAssignedToPlayer.contains(country))
				{
					it.remove();
				}
			}

			if(neighborCountriesName!=null) {
				neighborCountriesName.removeAll(connectedOwnCountries);
				connectedOwnCountries.addAll(neighborCountriesName);
			}

			Iterator<String> rec = neighborCountriesName.iterator();
			while (rec.hasNext()) 
			{
				String country = rec.next();
				getConnectedCountries(country, countryList);
			}
			System.out.println("1. Neighbouring Countries:"+neighborCountriesName.toString());
			System.out.println("1. Player's Countries:"+countriesAssignedToPlayer.toString());
//			neighborCountriesName.clear();
//			neighborCountriesName.addAll(connectedOwnCountries);
//			connectedOwnCountries.clear();
		return connectedOwnCountries;
	}
	
	
	public void getConnectedCountries(String source, ArrayList<Country> countryList) 
	{
		System.out.println("source Country Name :" + source);


		ArrayList<String> countriesAssignedToPlayer = new ArrayList<String>();
		ArrayList<String> neighborCountriesName = null;
		
			for (Country country : countryList) 
			{
				String countryName = country.getCountryName();
				countriesAssignedToPlayer.add(countryName);
				if (country.getCountryName().equals(source)) 
				{
					neighborCountriesName = country.getNeighboursString();
				}
			}
			
			Iterator<String> it = neighborCountriesName.iterator();
			while (it.hasNext()) 
			{
				String country = it.next();
				if (!countriesAssignedToPlayer.contains(country)||country.equals(initialSourceCountry))
				{
					it.remove();
				}
			}
			if(neighborCountriesName!=null) {
				neighborCountriesName.removeAll(connectedOwnCountries);
				connectedOwnCountries.addAll(neighborCountriesName);
			}

			Iterator<String> rec = neighborCountriesName.iterator();
			while (rec.hasNext()) 
			{
				String country = rec.next();
				getConnectedCountries(country, countryList);
			}
			System.out.println("1. Neighbouring Countries:"+neighborCountriesName.toString());
			System.out.println("1. Player's Countries:"+countriesAssignedToPlayer.toString());

	}
	
	public ArrayList<String> findNeighbour(String country_name)
	{
		
		Player currentPlayer = this.getCurrentPlayer();
		ArrayList<String> countriesAssignedToPlayer = new ArrayList<String>();
		ArrayList<String> neighborCountriesName = new ArrayList<String>();
		
		for (Country country : playerCountry.get(currentPlayer)) 
		{
			String countryName = country.getCountryName();
			countriesAssignedToPlayer.add(countryName);
			if (country.getCountryName().equals(country_name)) 
			{
				neighborCountriesName = country.getNeighboursString();
			}
		}
		Iterator<String> it = neighborCountriesName.iterator();
		while (it.hasNext()) 
		{
			String country = it.next();
			if (!countriesAssignedToPlayer.contains(country))
			{
				it.remove();
			}
		}
		return neighborCountriesName;
}

	public int getArmiesAssignedToCountry(String sourceCountryName) 
	{
		Player currentPlayer = this.getCurrentPlayer();
		int noOfArmies = 0;

		for (Country country : playerCountry.get(currentPlayer)) 
		{
			if (country.getCountryName().equals(sourceCountryName)) 
			{
				noOfArmies = country.getnoOfArmies();
			}
		}
		return noOfArmies;
	}

	public int getCurrentPlayerId() 
	{
		return currentPlayerId;
	}

	public MapModel getMap() 
	{
		return mapModel;
	}
	public void setMap(MapModel map) 
	{
		this.mapModel = map;
	}
	public GamePhase getGamePhase() 
	{
		return gamePhase;
	}
	public void setGamePhase(GamePhase gamePhase) 
	{
		this.gamePhase = gamePhase;
	}

	private void notifyObserverslocal(Game game)
	{
		setChanged();
		notifyObservers(this);
	}
}
