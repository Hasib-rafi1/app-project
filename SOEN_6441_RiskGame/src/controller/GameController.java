package controller;

import java.io.File;
import java.util.ArrayList;

import model.Game;
import model.MapModel;
import model.Player;
import helper.PrintConsoleAndUserInput;

public class GameController {

	Game game;	
	MapModel map = new MapModel();
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	

	
	public void initializeMap() 
	{
		int i = 1;
		print.consoleOut("List of Maps:");
		listofMapsinDirectory();
		print.consoleOut("\nEnter Map number to load Map file:\n");
		String mapPath = map.getMapNameByUserInput();
		map.readMapFile(mapPath);
	}
	
	public void initializeGame()
	{
		game = new Game(map);

		print.consoleOut("\nEnter the number of Players:");
		
		int playerCount = print.userIntInput();

		for (int i = 0; i < playerCount; i++) 
		{
			print.consoleOut("\nEnter the name of Player " + (i+1));
			String playerName = print.userStrInput();
			Player player = new Player(i, playerName);
			game.addPlayer(player);
		}
		
	}
	
	public ArrayList<String> listofMapsinDirectory(){
		ArrayList<String> mapFileList = new ArrayList<String>();
		File folder = new File(print.getMapDir());
		File[] listOfFiles = folder.listFiles();
		int i = 0, j = 1;
		for(File file : listOfFiles){		    
			if(file.isFile()){
				//System.out.println(file.getName());
				if (file.getName().toLowerCase().contains(".map")){
					mapFileList.add(listOfFiles[i].getName());
				}
			}
			i++;
		}
		print.consoleOut("\n"+ "The List of Maps is Given Below:-"+ "\n");
		for (String s : mapFileList) {
			print.consoleOut(j + "."+s);
			j++;
		}
		return mapFileList;
	}
}