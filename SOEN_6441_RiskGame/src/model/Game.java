package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Observable;

import helper.GamePhase;

public class Game extends Observable {

	private MapModel map;
	private GamePhase gamePhase;
	private int currentPlayerId;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private HashMap<Player, ArrayList<Country>> playerCountry = new HashMap<>();


	public Game(MapModel map) 
	{
		super();
		this.map = map;
		this.setGamePhase(GamePhase.Startup);
	}
	
	public void addPlayer(Player player) 
	{
		this.playerList.add(player);
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
	public void setNextPlayerTurn() 
	{
		currentPlayerId++;
		if (currentPlayerId == playerList.size())
			currentPlayerId = 0;
		System.out.println("current player ID:" + currentPlayerId);
	}
	
	public MapModel getMap() 
	{
		return map;
	}
	public void setMap(MapModel map) 
	{
		this.map = map;
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
	