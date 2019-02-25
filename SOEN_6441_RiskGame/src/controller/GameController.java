package controller;

import java.io.File;
import java.util.ArrayList;

import model.Game;
import model.MapModel;
import model.Player;
import helper.PrintConsoleAndUserInput;

public class GameController {

	Game game;
	MapModel map;
	//GameView gameView;
	
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();

	
	public void initializeMap() 
	{
		int i = 1;
		print.consoleOut("List of Maps:");
		ArrayList<String> maps = getListOfMaps();
		
		for (String file : maps) 
		{
			print.consoleOut( i + ")" + file);
			i++;
		}
		
		print.consoleOut("\nEnter Map number to load Map file:\n");
		int mapNumber = print.userIntInput();
		String selectedMapName = maps.get(mapNumber - 1);
		//map.setMapName(selectedMapName);
		//map.readMap();

		/*if(!map.isMapValid())
		{
			print.consoleOut("\nInvalid Map. Select Again!");
		    initializeMap();
		}*/
	}
	
	public void initializeGame()
	{
		game = new Game(map);
		//gameView=new GameView();
		//game.addObserver(gameView);
		
		print.consoleOut("\nEnter the number of Players:");
		
		int playerCount = print.userIntInput();

		for (int i = 0; i < playerCount; i++) 
		{
			print.consoleOut("\nEnter the name of Player " + (i+1));
			String playerName = print.userStrInput();
			Player player = new Player(i, playerName);
			game.addPlayer(player);
		}
		
		//game.startUpPhase();
		//gameView.gameInitializer();
		//activateListenersOnView();
	}
	
	private ArrayList<String> getListOfMaps() 
	{
		ArrayList<String> fileNames = new ArrayList<String>();
		File folder = new File("src/mapFiles");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) 
			{
				if(listOfFiles[i].getName().toLowerCase().contains(".map"))
					fileNames.add(listOfFiles[i].getName());
			} 
			else if (listOfFiles[i].isDirectory()) 
			{
			}
		}
		return fileNames;
	}
	
}
