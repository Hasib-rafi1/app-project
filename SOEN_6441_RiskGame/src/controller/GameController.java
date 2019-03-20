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
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import model.Country;
import model.Game;
import model.Player;
import views.BoardView;
import views.WorldDominationView;
//import views.WorldDominationView;
import model.MapModel;
import helper.Colors;
import helper.GamePhase;
import helper.PrintConsoleAndUserInput;
// TODO: Auto-generated Javadoc
/**
 * Game Controller initializes the game by calling the game model.
 * It controls the view by actively listing to the view elements and performing the respective actions. 
 * Class that achieves the action listener for the user input 
 * @author Jaiganesh
 * @version 1.0.0
 */
public class GameController {

	/** The game. */
	Game game;

	/** The board view. */
	BoardView boardView;

	Player player;

	/** The map model. */
	MapModel mapModel = new MapModel();

	/** The print. */
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();

	/** The userinput. */
	//	WorldDominationView worldDominationView = new WorldDominationView();
	Scanner userinput = new Scanner(System.in);
	Country attCountry;
	Country defCountry;

	/**
	 * This function is going to initializing the map by taking user input.
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
			print.consoleErr("****File not found!!!. Please enter the correct name of map.****");
		}
	}


	/**
	 * This method is setting up the board and game model
	 * It is intializing the observer for the gui also
	 * It is taking the the input from the user for creating number of players.
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
		addAllOutButtonListener();
		addEndAttackButtonListener();
		addAttackMoveArmyButtonListener();
		addSkipButtonListener();
	}

	/**
	 * This method is going to assign armies to the specific countries in initial phase and in reinforcement phase.
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
	 * This method is going to populate destination combo box and the number of army combobox.
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
	 * to add listeners on the attacker Country List.
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
	 * to add listeners on the defender Country List.
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
	 * to add listener on the Attack Button.
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
						boolean state =game.attackPhase(attackerCountry, defenderCountry, attackerDiceCount, defenderDiceCount);
						if(state) {
							attCountry = mapModel.getCountryFromName(attackerCountry);
							defCountry = mapModel.getCountryFromName(defenderCountry);
							boardView.setVisibalityOfMoveAfterConcure();
							boardView.setMoveComboBox(attCountry.getnoOfArmies());
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Selecting attacking and defending countries");
				}
			}
		});
	}

	/**
	 * to add listener on the END Attack Button.
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
	 * to add listener on the END Attack Button.
	 */
	public void addAllOutButtonListener() {
		boardView.addActionListenToAllOutButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getGamePhase() == GamePhase.Attack) {
					String attackerCountry = boardView.getAttackerCountry();
					String defenderCountry = boardView.getDefenderCountry();
					if (attackerCountry != null && defenderCountry != null) {
						boolean state = game.attackAllOutPhase(attackerCountry, defenderCountry);
						if(state) {
							attCountry = mapModel.getCountryFromName(attackerCountry);
							defCountry = mapModel.getCountryFromName(defenderCountry);
							boardView.setVisibalityOfMoveAfterConcure();
							boardView.setMoveComboBox(attCountry.getnoOfArmies());
						}
					} else {
						JOptionPane.showMessageDialog(null, "Selecting attacking and defending countries");
					}
				}
			}
		});
	}

	/**
	 * to add listener on the move army Button
	 */
	public void addAttackMoveArmyButtonListener() {
		boardView.addActionListenToMoveButton(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game.getGamePhase() == GamePhase.Attack) {

					Integer attackerMoveArmies = Integer.parseInt(boardView.getMoveComboBox());
					game.moveArmies(attCountry,defCountry,attackerMoveArmies);
					attCountry = null;
					defCountry = null;
					boardView.setVisibalityOfMoveAfterMove();

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

	/**
	 * Add action listener for the world domination view.
	 *
	 */
	public void addActionListenerForWorldDominationView() {
		boardView.worldDominationViewListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DecimalFormat countryPercentFormat = new DecimalFormat(".####");
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
					//playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;

					if(player.getPlayerColor(y)==Colors.BLACK)
						playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;
					else if(player.getPlayerColor(y)==Colors.BLUE)
						playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;
					else if(player.getPlayerColor(y)==Colors.GREEN)
						playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;
					else if(player.getPlayerColor(y)==Colors.RED)
						playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;
					else if(player.getPlayerColor(y)==Colors.ORANGE)
						playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;
					else if(player.getPlayerColor(y)==Colors.MAGENTA)
						playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;
					else
						playerNamesInTableColumns[y] = "Player name : "+nameOfPlayer;
					y++;
				}
				int size = newPlayerNameList.size();

				// Get the Percentage of the map controlled by every player
				Float[] mapPercentage = new Float[size];
				HashMap<Integer,Float> findPercentageOfMap =  game.getPercentageOfMapControlledByEveryPlayer();
				int z=0;
				for (Map.Entry<Integer, Float> entry : findPercentageOfMap.entrySet()) {
					//   System.out.println(entry.getKey()+" : "+entry.getValue());
					float value = entry.getValue();
					mapPercentage[z] = value;
					z++;
				}

				//Get the continents controlled by every player
				String[] continentsAcquired = new String[size];
				HashMap<Integer,Integer> findContinentsAcquired =  game.getContinentsControlledByEachPlayer();
				int l=0;
				for (Map.Entry<Integer, Integer> entry : findContinentsAcquired.entrySet()) {
					Integer value = entry.getValue();
					continentsAcquired[l] = value.toString();
					l++;
				}

				int[] numberOfArmies = new int[size];
				HashMap<Integer, Integer> armiesMap = game.getNumberOfArmiesForEachPlayer();
				int i=0;
				for (Map.Entry<Integer, Integer> entry : armiesMap.entrySet()) {
					int value = entry.getValue();
					numberOfArmies[i] = value;
					i++;
				}

				// To print data in a table
				String[][] dataInTableRows = new String[3][size];
				for (int percentColumn = 0; percentColumn < dataInTableRows[0].length; percentColumn++) {
					String formattedPercent = countryPercentFormat.format(mapPercentage[percentColumn]);
					dataInTableRows[0][percentColumn] = formattedPercent + " %";
				}
				for (int continentColumn = 0; continentColumn < dataInTableRows[0].length; continentColumn++) {
					dataInTableRows[1][continentColumn] = continentsAcquired[continentColumn];
				}
				for (int armyColumn = 0; armyColumn < dataInTableRows[0].length ; armyColumn++) {
					dataInTableRows[2][armyColumn] = Integer.toString(numberOfArmies[armyColumn]);
				}

				WorldDominationView.createJframeForWorldDominationView(dataInTableRows,playerNamesInTableColumns);


			}
		});
	}

	/**
	 * Adds the skip button listener.
	 */
	public void addSkipButtonListener() {
		boardView.skipFortificationActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("A");
				if(game.getGamePhase()==GamePhase.Fortification) {
					game.skipFortification();

				}
			}

		});
	}
}