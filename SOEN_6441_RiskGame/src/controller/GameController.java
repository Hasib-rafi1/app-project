package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


import model.Country;
import model.Game;
import model.Player;
import strategies.Human;
import views.BoardView;
import views.CardView;
import views.WorldDominationView;
import model.MapModel;
import helper.GamePhase;
import helper.PrintConsoleAndUserInput;


// TODO: Auto-generated Javadoc
/**
 * Game Controller initializes the game by calling the game model.
 * It controls the view by actively listing to the view elements and performing the respective actions. 
 * Class that achieves the action listener for the user input 
 * 
 * @author Jaiganesh
 * @version 1.0.0
 */
public class GameController {

	/** The game. */
	Game game;

	/** The board view. */
	BoardView boardView;

	/** The world domination view. */
	WorldDominationView worldDominationViewObserver;

	/** The player. */
	Player player;

	/** The map model. */
	MapModel mapModel = new MapModel();

	/** The print. */
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();

	/** The userinput. */
	Scanner userinput = new Scanner(System.in);

	/** The att country. */
	Country attCountry;

	/** The def country. */
	Country defCountry;
	int playerStrategyName =0;
	/**
	 * This function is going to initializing the map by taking user input.
	 */
	public void initializeMap(String mapPath) {
		//	public void initializeMap() {
		//		print.listofMapsinDirectory();
		//		print.consoleOut("\nEnter Map Name to load Map file:\n");
		//		mapModel = new MapModel(); //------Refresh------
		//		String mapPath = mapModel.getMapNameByUserInput();
		File tempFile = new File(mapPath);
		boolean exists = tempFile.exists();
		if (exists) {			
			mapModel.readMapFile(mapPath);
			mapModel.printMapValidOrNot();
			if (!mapModel.checkMapIsValid()){
				//print.consoleErr("****Error!! Invalid map name. Please try again with the valid name****");
			}
		}else {
			print.consoleErr("****File not found!!!. Please enter the correct name of map.****");
		}
	}

	public MapModel getInitializedMapModel() {
		mapModel = new MapModel(); //------Refresh------
		//        ArrayList<String> mapNamesListForTournament= new ArrayList<>();
		String mapPath = mapModel.getMapNameByUserInput();
		//        if(!mapNamesListForTournament.contains(mapPath)){
		//            mapNamesListForTournament.add(mapPath);
		//        }else {
		//            print.consoleOut("Please Different Names For the Maps You want to play On");
		//        }
		System.out.println(mapPath);
		initializeMap(mapPath);
		return mapModel;
	}

	/**
	 * This method is setting up the board and game model
	 * It is intializing the observer for the gui also
	 * It is taking the the input from the user for creating number of players.
	 */
	public void initializeGame(){

		print.consoleOut("Enter the Game mode you want to play.");
		print.consoleOut("1 -> Single Game Mode. \n2 -> Tournament Mode.");
		int gameMode = print.userIntInput();

		if(gameMode == 1){

			int j=1;

			print.listofMapsinDirectory();
			print.consoleOut("\nEnter Map Name to load Map file:\n");
			mapModel = new MapModel(); //------Refresh------
			String mapPath = mapModel.getMapNameByUserInput();
			initializeMap(mapPath);

			game = new Game(mapModel);
			boardView=new BoardView();
			worldDominationViewObserver = new WorldDominationView();
			game.addObserver(worldDominationViewObserver);
			game.addObserver(boardView);
			//game.addObserver(cardView);
			print.consoleOut("\nEnter the number of Players between 3-5:");
			int playerCount = Integer.parseInt(userinput.nextLine());

			if(playerCount < 3 || playerCount > 5) {
				print.consoleErr("**** Error!!! Please enter the number of Players between 3-5. ****");
			} else {
				for (int i = 0; i < playerCount ; i++) {
					print.consoleOut("\nEnter the name of Player " + j);
					String name = userinput.nextLine();
					Player player = new Player(i,name);
                    print.consoleOut("\nEnter The Strategy of playing for Player: " +name);
                    print.consoleOut("\n1. Human \n2. Aggressive \n3. Benevolent \n4. Cheater \n5. Random");
                    int strategy = Integer.parseInt(userinput.nextLine());

                    if(strategy == 1){
                        player.setPlayerStrategy(new Human());
                    }

//					playerStrategyName = print.userIntInput();
//					playerStrategyActions();

					game.addPlayer(player);
					j++;
				}
				game.startGame();
				game.initializeRiskCards();
				boardView.gameWindowLoad();
				callListenerOnView();
			}

		} else if (gameMode == 2){

			int M = 0, P = 0, G = 0, D = 0;
			ArrayList<MapModel> mapNamesForTournament = new ArrayList<>();


			print.consoleOut("******* Welcome to Tournament Mode. *******");
			while (true) {
				print.consoleOut("Enter The Number of Maps You want to play on (1-5): ");
				int numberOfMaps = print.userIntInput();
				if (numberOfMaps >= 1 && numberOfMaps <= 5) {
					M = numberOfMaps;
					break;
				}else{print.consoleErr("Please Enter the number of Maps between 3-5");}
			}
			print.consoleOut("Enter '" + M + "' Different Map Names from following list: ");
			print.listofMapsinDirectory();
			for (int i = 0; i < M; i++) {
				mapModel = new MapModel(); //------Refresh------
				mapNamesForTournament.add(getInitializedMapModel());
			}
			System.out.println(mapNamesForTournament.size());


			while (true) {
				print.consoleOut("Enter The Number of player strategies you want to play with(2-4): ");
				int numberOfStrategies = print.userIntInput();
				if (numberOfStrategies >= 2 && numberOfStrategies <= 4) {
					P = numberOfStrategies;
					break;
				}else{print.consoleErr("Please Enter the number of Strategies between 2-4. ");}
			}
			print.consoleOut("Enter '" + P + "' Different Strategies from following list:");
			print.consoleOut("2. Aggressive \n3. Benevolent \n4. Cheater \n5. Random");
			for (int i = 0; i < P; i++) {
				while (true) {
					playerStrategyName = print.userIntInput();
					if(!(playerStrategyName < 2 || playerStrategyName > 5)){
						playerStrategyActions();
						break;
					}else{
						print.consoleErr("For Tournament Select the Strategies between 2-5");
					}
				}

			}


			print.consoleOut("Enter Number of Games you want to play on Each Map (1-5): ");
			print.consoleOut("Enter Maximum Number of Turns for Each Game (10 - 50): ");
		}else {
			print.consoleErr("Please Enter a Valid Game Mode.");
		}
	}

	public void playerStrategyActions(){
		switch (playerStrategyName) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			default:
				print.consoleErr("\n\t Error! Select the Strategies from the list (1 to 5):");
				break;
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
		addActionListenerForloadAndSaveGame();
		addAttackButtonListener();
		addAllOutButtonListener();
		addEndAttackButtonListener();
		addAttackMoveArmyButtonListener();
		addSkipButtonListener();
		skipExchangeListener();
		exchangeButtonListener();
		setBoardView();

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
			@Override			
			public void actionPerformed(ActionEvent  e) {
				String countryName = boardView.getSourceCountry();
				System.out.println("Game controller class"+countryName);
				if(countryName!=null) {
					ArrayList<String> neighborCountries = game.getNeighbouringCountries(countryName);
					int armyCount = game.getArmiesAssignedToCountry(countryName);
					boardView.comboboxFillDestinationCountry(neighborCountries);
					boardView.comboboxFillArmyToMove(armyCount);
				}
			}		
		});
	}

	/**
	 * This method is used to add listeners on the attacker Country List.
	 */
	public void addAttackerCountryListener() {
		boardView.addActionListenToAttackerCountryList(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String countryName = boardView.getAttackerCountry();
				if (countryName != null) {
					ArrayList<String> neighborCountries = game.getOthersNeighbouringCountriesOnly(countryName);
					boardView.comboboxFillDefendersCountry(neighborCountries);
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
	 * This method is used to add listener on the Attack Button.
	 */
	public void addAttackButtonListener() {
		boardView.addActionListenToAttackButton(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String attackerCountry = boardView.getAttackerCountry();
				String defenderCountry = boardView.getDefenderCountry();
				attCountry = mapModel.getCountryFromName(attackerCountry);
				defCountry = mapModel.getCountryFromName(defenderCountry);
				boardView.setVisibalityOfMoveAfterMove();
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
	 * This method is used to add listener on the END Attack Button.
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
	 * This method is used to add listener on the END Attack Button.
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
	 * This method is used to add listener on the move army Button.
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
					game.fortificationPhase(boardView.getSourceCountry(),boardView.getDestinationCountry(),boardView.comboboxGetArmyToMove());
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
				game.dominationViewOn =true;
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
				HashMap<Integer,String> findContinentsAcquired =  game.getContinentsControlledByEachPlayer();
				int l=0;
				for (Map.Entry<Integer, String> entry : findContinentsAcquired.entrySet()) {
					String value = entry.getValue();
					continentsAcquired[l] = value;
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

				worldDominationViewObserver.createJframeForWorldDominationView(dataInTableRows,playerNamesInTableColumns);
			}
		});
	}

	/**
	 * Add action listener for the Load and save Game
	 */
	private void addActionListenerForloadAndSaveGame() {
		// TODO Auto-generated method stub
		boardView.saveGameButtonListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				game.saveMyGame();
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

	/**
	 * This function is used to exchange button listener.
	 */
	public void exchangeButtonListener() {
		CardView.exchangeActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CardView.listCardsOwnedByThePlayer.getSelectedValuesList() != null &&  CardView.listCardsOwnedByThePlayer.getSelectedValuesList().size() > 0) {
					// This list holds the cards selected by the user
					ArrayList<String> selectedCards = (ArrayList<String>) CardView.listCardsOwnedByThePlayer.getSelectedValuesList();
					boolean success = game.exchangeRiskCards(selectedCards);
					if(success) {
						CardView.closeTheWindow();
						boardView.getFrameGameWindow().setEnabled(true);
						game.updateReinforcementValue();						
					}else {
						// Nothing implemented
					}
				}
			}
		});
	}

	/**
	 * This function is going to close/skip if number of card is less than 5.
	 */
	public void skipExchangeListener() {
		CardView.exitActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int tempForNumberOfCardsPlayerHolds = (game.getCurrentPlayer().getCards()).size();
				if(tempForNumberOfCardsPlayerHolds >= 5) {
					JOptionPane.showMessageDialog(null, "Cannot skip Exchange. Perform the Exchange operation!");
				}else {
					boardView.getFrameGameWindow().setEnabled(true);
					CardView.closeTheWindow();
				}
			}
		});
	}

	/**
	 * This function is going to set the BoardView in the game model.
	 */
	public void setBoardView() {
		game.setBoardView(boardView);
	}

	public void loadSavedGame() {
		// print the saved game files in the directory
		print.listofSavedGamesinDirectory();
		
		print.consoleOut("Enter Game file name which you want to load:");		
		String selectFileToLoadGame = userinput.nextLine();

		game = Game.loadGame(selectFileToLoadGame);
		
		MapModel mapModel = game.getMap();
		//game = new Game(mapModel);
		boardView=new BoardView();		
		worldDominationViewObserver = new WorldDominationView();
		
		game.addObserver(worldDominationViewObserver);
		game.addObserver(boardView);
		
		callListenerOnView();
		game.notifyObserverslocal(game);
		/*int i = 1;
		for (String GameTitle : savedGameList) {
			IOHelper.print(i + ")" + GameTitle);
			i++;
		}
		IOHelper.print("\nEnter Game that you want to load:");
		int gameNumber = IOHelper.getNextInteger();
		String GameTitle = savedGameList.get(gameNumber - 1);
		game = Game.loadGame(GameTitle);

		Map map = game.getMap();
		cardExchangeView = new CardExchangeView();
		gameView = new GameView(); // boardview
		game.addObserver(gameView);
		game.addObserver(cardExchangeView);
		game.notifyObserversLocal();
		gameView.mapPath = map.getMapPath() + map.getMapName() + ".bmp";
		gameView.gameInitializer();
		activateListenersOnView();
		game.notifyObserversLocal();

		IOHelper.print("Game Successfully Loaded");*/
		
	}

}