package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import model.Game;
import model.Player;
import views.BoardView;
import views.WorldDominationView;
//import views.WorldDominationView;
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
//	WorldDominationView worldDominationView = new WorldDominationView();
	Scanner userinput = new Scanner(System.in);

	/**
	 * This function is going to initializing the map by taking user input
	 */
	public void initializeMap() {
		print.listofMapsinDirectory();
		print.consoleOut("\nEnter Map Name to load Map file:\n");
		String mapPath = mapModel.getMapNameByUserInput();		
		File tempFile = new File(mapPath);
		boolean exists = tempFile.exists();
		if (exists) {			
			mapModel.readMapFile(mapPath);
			mapModel.printMapValidOrNot();
			if (!mapModel.checkMapIsValid()){
				//print.consoleErr("****Error!! Invalid map name. Please try again with the valid name****");
			}else {
				initializeGame();
			}
		}else {
			print.consoleErr("****File not found!!!. Please enter the coreect name of map.****");
		}
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

		print.consoleOut("\nEnter the number of Players between 3-5:");
		int playerCount = Integer.parseInt(userinput.nextLine());

		if(playerCount < 3 || playerCount > 5) {
			print.consoleErr("**** Error!!! Please enter the number of Players between 3-5. ****");
		} else {
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
	}

	/**
	 * This function is used to call the listener functions. 
	 * 
	 */
	private void callListenerOnView(){
		numberOfArmiesClickListener();
		addSourceCountriesListener();
		addMoveArmyButtonListener();
		addAttackerCountryListener();
		addDefenderCountryListener();
		addActionListenerForWorldDominationView();
		addAttackButtonListener();
		addEndAttackButtonListener();
		addAllOutButtonListener();
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
	 * to add listeners on the attacker Country List
	 */
	public void addAttackerCountryListener() {
		boardView.addActionListenToAttackerCountryList(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String countryName = boardView.getAttackerCountry();

				if (countryName != null) {
					ArrayList<String> neighborCountries = game.getOthersNeighbouringCountriesOnly(countryName);
					boardView.combo_fillDefendersCountry(neighborCountries);
					int diceCount = game.getMaximumDices(countryName, "Attacker");
					boardView.setAttackerDiceComboBox(diceCount);
				}
			}
		});
	}

	/**
	 * to add listeners on the defender Country List
	 */
	public void addDefenderCountryListener() {
		boardView.addActionListenToDefenderCountryList(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String countryName = boardView.getDefenderCountry();

				if (countryName != null) {
					int diceCount = game.getMaximumDices(countryName, "Defender");
					boardView.setDefenderDiceComboBox(diceCount);
				}
			}
		});
	}

	/**
	 * to add listener on the Attack Button
	 */
	public void addAttackButtonListener() {
		boardView.addActionListenToAttackButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String attackerCountry = boardView.getAttackerCountry();
				String defenderCountry = boardView.getDefenderCountry();
				if (attackerCountry != null && defenderCountry != null) {
					if (game.getGamePhase() == GamePhase.Attack) {
						Integer attackerDiceCount = Integer.parseInt(boardView.getAttackerDiceNo());
						Integer defenderDiceCount = Integer.parseInt(boardView.getDefenderDiceNo());
						game.attackPhase(attackerCountry, defenderCountry, attackerDiceCount, defenderDiceCount);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecting attacking and defending countries");
				}
			}
		});
	}

	/**
	 * to add listener on the END Attack Button
	 */
	public void addEndAttackButtonListener() {
		boardView.addActionListenToEndAttackButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getGamePhase() == GamePhase.Attack) {
					game.updateGame();
				}
			}
		});
	}
	
	/**
	 * to add listener on the END Attack Button
	 */
	public void addAllOutButtonListener() {
		boardView.addActionListenToAllOutButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getGamePhase() == GamePhase.Attack) {
					String attackerCountry = boardView.getAttackerCountry();
					String defenderCountry = boardView.getDefenderCountry();
					if (attackerCountry != null && defenderCountry != null) {
						game.attackAllOutPhase(attackerCountry, defenderCountry);
						System.out.println(attackerCountry);
						System.out.println(defenderCountry);
					} else {
						JOptionPane.showMessageDialog(null, "Selecting attacking and defending countries");
					}
				}
			}
		});
	}
	
	/**
	 * This method is to update the board view.
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

	public void addActionListenerForWorldDominationView() {		
		boardView.worldDominationViewListener(new ActionListener() {			
			public void actionPerformed(ActionEvent e) {
				ArrayList<Player> playerList = game.getAllPlayers();

				// Get players from the above arraylist and add in the other arraylist.
				int x = 0;
				ArrayList<String> newPlayerNameList = new ArrayList<>();
				for (Player playerData : playerList) {
					String name = playerData.getPlayerName();
					newPlayerNameList.add(name);
					x++;
				}

				// print Player name in tabular columns(Ist row heading)				
				String[] playerNamesInTableColumns = new String[newPlayerNameList.size()];
				int y=0;				
				for ( String nameOfPlayer : newPlayerNameList ) {				
					playerNamesInTableColumns[y] = "Player name "+nameOfPlayer;
					y++;
				}

				// Get the percentage of the map controlled by every player(not working till now)
				Float[] mapPercentage = new Float[newPlayerNameList.size()];
				HashMap<Integer,Float> findPercentageOfMap =  game.getPercentageOfMapControlledByEveryPlayer();
				int z=0;
				for (Map.Entry<Integer, Float> entry : findPercentageOfMap.entrySet()) {
					//   System.out.println(entry.getKey()+" : "+entry.getValue());
					mapPercentage[z] = entry.getValue();
					z++;
				}

				String[][] rowData = new String[3][newPlayerNameList.size()];
				for (int i = 0; i < rowData[0].length; i++) {
					rowData[0][i] = mapPercentage[i] + " %";
				}


				WorldDominationView.createJframeForWorldDominationView(rowData,playerNamesInTableColumns);


				/*	JPanel panelWindowForWorldDominationView = new JPanel(new BorderLayout());
				JFrame frameWindowForWorldDominationView = new JFrame("Players World Domination View");
				panelWindowForWorldDominationView.setLayout(new FlowLayout());
				panelWindowForWorldDominationView.setPreferredSize(new Dimension(580, 300));


				// Putting the data in a table
				JTable table = new JTable(rowData, playerNamesInTableColumns);
				frameWindowForWorldDominationView.getContentPane( ).add(new JScrollPane(table));
				frameWindowForWorldDominationView.setSize(600, 300);
				frameWindowForWorldDominationView.setLocationRelativeTo(null);
				frameWindowForWorldDominationView.setVisible(true);
				frameWindowForWorldDominationView.add(panelWindowForWorldDominationView);
				frameWindowForWorldDominationView.pack();
				frameWindowForWorldDominationView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
			}
		});
	}

}