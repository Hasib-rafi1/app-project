package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;
import java.util.Random;
import java.util.Collections;
import helper.InitialPlayerArmy;

import helper.GamePhase;
import helper.PrintConsoleAndUserInput;
import helper.InitialPlayerArmy;

public class Game extends Observable {

	private MapModel mapModel;
	private GamePhase gamePhase;
	private int currentPlayerId;
	
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
		}
		
		int players_count = playerList.size();
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
        		players_id =0;
        	}
        	
        	Country assign_country = mapModel.getCountryList().get(randomNumbers.get(i));
        	assignPlayerCountry(playerList.get(players_id),assign_country);
        	assignUnassigned(playerList.get(players_id),assign_country);
        	players_id++;
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
				setNextPlayerTurn();
			}
		}
		else if (gamePhase == gamePhase.Reinforcement)
		{
			addingReinforcementCountryArmy(countryName);
		}
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
		if(player.getNumberOfInitialArmies() == 0)
		{
			print.consoleOut("Player "+player.getPlayerName()+"Doesn't have any Armies.");
			return false;
		}
		Country country = playerCountry.get(player).stream()
				.filter(c -> c.getCountryName().equalsIgnoreCase(countryName)).findAny().orElse(null);
		if (country == null) 
		{
			print.consoleOut("Country name -  " + countryName + " does not exist!");
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
	
	public void setNextPlayerTurn()
	{
		currentPlayerId++;
		if(currentPlayerId==playerList.size())
		{
			currentPlayerId = 0;
			print.consoleOut("Current Player ID:"+currentPlayerId);
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
	
	public ArrayList<String> getNeighbouringCountries(String sourceCountryName) 
	{
		ArrayList<String> neighborCountriesName = null;
		System.out.println("sourceCountryName :" + sourceCountryName);

			Player currentPlayer = this.getCurrentPlayer();
			ArrayList<String> countriesAssignedToPlayer = new ArrayList<String>();

			for (Country country : playerCountry.get(currentPlayer)) 
			{
				String countryName = country.getCountryName();
				countriesAssignedToPlayer.add(countryName);
				if (country.getCountryName().equals(sourceCountryName)) 
				{
					neighborCountriesName = country.getNeighboursString();
					break;
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
				//noOfArmies = country.getnoOfArmies();
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
	