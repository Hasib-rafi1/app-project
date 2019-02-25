package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import model.Game;
import model.Player;
import model.MapModel;
import helper.PrintConsoleAndUserInput;

public class GameController {

	Game game;	
	MapModel mapModel = new MapModel();
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	Scanner userinput = new Scanner(System.in);
	
	public void initializeMap() 
	{
		int i = 1;
		print.consoleOut("List of Maps:");
		listofMapsinDirectory();
		print.consoleOut("\nEnter Map Name to load Map file:\n");
		String mapPath = mapModel.getMapNameByUserInput();
		mapModel.readMapFile(mapPath);
	}
	
	public void initializeGame()
	{
		game = new Game(mapModel);

		print.consoleOut("\nEnter the number of Players:");
		int playerCount = PrintConsoleAndUserInput.userIntInput();

		for (int i = 0; i < playerCount ; i++) 
		{
			print.consoleOut("\nEnter the name of Player " + (i));
			String name = userinput.nextLine();
			Player player = new Player(i,name);
			game.addPlayer(player);
			System.out.println(game.getAllPlayers());
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