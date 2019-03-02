package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JLabel;

import model.Game;
import model.Player;
import views.BoardView;
import model.MapModel;
import helper.GamePhase;
import helper.PrintConsoleAndUserInput;

/**
 * Game Controller initializes the game by calling the game model.
 * It controls the view by actively listing to the view elements and performing the respective actions. 
 * Class that achieves the action listener for the user input 
 * @author Jaiganesh
 * @version 1.0.0
 */

public class GameController {

	Game game;	
	BoardView boardView;
	MapModel mapModel = new MapModel();
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	Scanner userinput = new Scanner(System.in);

	/**
	 * This function is going to initializing the map by taking user input
	 */
	public void initializeMap() {
		int i = 1;
		print.consoleOut("List of Maps:");
		listofMapsinDirectory();
		print.consoleOut("\nEnter Map Name to load Map file:\n");
		String mapPath = mapModel.getMapNameByUserInput();
		mapModel.readMapFile(mapPath);
	}

	/**
	 * This method is setting up the board and game model
	 * It is intializing the observer for the gui also
	 * It is taking the the input from the user for creating number of players
	 */
	public void initializeGame(){
		int j=1;
		game = new Game(mapModel);
		boardView=new BoardView();
		game.addObserver(boardView);

		print.consoleOut("\nEnter the number of Players 3-5:");
		int playerCount = Integer.parseInt(userinput.nextLine());

		for (int i = 0; i < playerCount ; i++) {
			print.consoleOut("\nEnter the name of Player " + j);
			String name = userinput.nextLine();
			Player player = new Player(i,name);
			game.addPlayer(player);
			j++;
		}	
		game.startGame();
		boardView.gameWindowLoad();	
		callListenerOnView();
	}

	/**
	 * prints the lists of maps in the directory
	 * @return ArrayList of String
	 */
	public ArrayList<String> listofMapsinDirectory(){
		ArrayList<String> mapFileList = new ArrayList<String>();
		File folder = new File(print.getMapDir());
		File[] listOfFiles = folder.listFiles();
		int i = 0, j = 1;
		for(File file : listOfFiles)
		{		    
			if(file.isFile())
			{
				if (file.getName().toLowerCase().contains(".map"))
				{
					mapFileList.add(listOfFiles[i].getName());
				}
			}
			i++;
		}
		print.consoleOut("\n"+ "The List of Maps is Given Below:-"+ "\n");
		for (String s : mapFileList) 
		{
			print.consoleOut(j + "."+s);
			j++;
		}
		return mapFileList;
	}

	/**
	 * The functions is calling the listener functions 
	 * 
	 */
	private void callListenerOnView(){
		numberOfArmiesClickListener();
		addSourceCountriesListener();
		addMoveArmyButtonListener();
	}

	/**
	 *This method is going to assign armies to the specific countries in initial phase and in reinforcement phase
	 */
	public void numberOfArmiesClickListener(){
		boardView.addMapLabelsListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				JLabel jLabel=	(JLabel) e.getSource();
				String country=jLabel.getToolTipText();
				if (game.getGamePhase()==GamePhase.Startup || game.getGamePhase() == GamePhase.Reinforcement){
					game.addingCountryArmy(country);
				}
			}
		});
	}

	/**
	 * This method is going to populate destination combo box and the number of army combobox
	 */
	public void addSourceCountriesListener(){
		boardView.addActionListenToSourceCountryList(new ActionListener() {

			public void actionPerformed(ActionEvent  e) {
				String countryName = boardView.getSourceCountry();
				System.out.println("Game controller class"+countryName);
				if(countryName!=null) {
					ArrayList<String> neighborCountries = game.getNeighbouringCountries(countryName);
					int armyCount = game.getArmiesAssignedToCountry(countryName);
					boardView.combo_fillDestinationCountry(neighborCountries);
					boardView.combo_fillArmyToMove(armyCount);

				}
			}
		});
	}

	/**
	 * To update view
	 */
	public void addMoveArmyButtonListener(){
		boardView.moveArmyButtonListener(new ActionListener() {

			public void actionPerformed(ActionEvent  e) {
				if (game.getGamePhase()==GamePhase.Fortification) {
					game.fortificationPhase(boardView.getSourceCountry(),boardView.getDestinationCountry(),boardView.combo_getArmyToMove());
				}
			}
		});
	}

}