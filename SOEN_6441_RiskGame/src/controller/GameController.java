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

public class GameController {

	Game game;	
	BoardView boardView;
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
		boardView=new BoardView();
		game.addObserver(boardView);

		print.consoleOut("\nEnter the number of Players:");
		int playerCount = PrintConsoleAndUserInput.userIntInput();

		for (int i = 0; i < playerCount ; i++) 
		{
			print.consoleOut("\nEnter the name of Player " + i);
			String name = userinput.nextLine();
			Player player = new Player(i,name);
			game.addPlayer(player);
		}	
		game.startGame();
		boardView.gameWindowLoad();	
		callListenerOnView();
	}
<<<<<<< HEAD
	
		
	
=======

>>>>>>> branch 'master' of https://jaiganesh_vr@bitbucket.org/gargisharma5292/soen_6441_riskgame.git
	public ArrayList<String> listofMapsinDirectory()
	{
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

	private void callListenerOnView(){
		numberOfArmiesClickListener();
		addSourceCountriesListener();
		addMoveArmyButtonListener();
	}

	/**
	 * this method is going to assign armies to the specific countries in initial phase and in reinforcement phase
	 */
	public void numberOfArmiesClickListener(){
		boardView.addMapLabelsListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				JLabel jLabel=	(JLabel) e.getSource();
				String string=jLabel.getToolTipText();
				if (game.getGamePhase()==GamePhase.Startup || game.getGamePhase() == GamePhase.Reinforcement){
					//jai do whatever you want to do.
				}
			}
		});
	}

	/**
	 * this method is going to populate destination combo box and the number of army combobox
	 */
	public void addSourceCountriesListener(){
		boardView.addActionListenToSourceCountryList(new ActionListener() {

			public void actionPerformed(ActionEvent  e) {
				String countryName = boardView.getSourceCountry();
				if(countryName!=null) {
					// Jai come up with the neighboring country arraylist from the specific country name
					// Jai find the army number that is available in this country
					//call the bellow function and pass the veriable
					//boardView.populateDestinationCountryComboBox(neighborCountries);
					//boardView.populateNoOfArmyToMoveJcomboBox(armyCount);

				}
			}
		});
	}

	/**
	 * to update view
	 */
	public void addMoveArmyButtonListener(){
		boardView.moveArmyButtonListener(new ActionListener() {

			public void actionPerformed(ActionEvent  e) {
				if (game.getGamePhase()==GamePhase.Fortification) {
					//jai do whatever you wan to do 
					//boardView.getSourceCountry(), this is the source country
					//boardView.getDestinationCountry(), this is the destination country
					//boardView.getNoOfArmyToMoveJcomboBox() number of the army
				}
			}
		});
	}

}