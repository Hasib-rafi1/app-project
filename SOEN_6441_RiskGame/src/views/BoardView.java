package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

import model.MapModel;
import model.Player;
import model.Game;
import helper.Colors;
import helper.PrintConsoleAndUserInput;
import model.Country;
import model.CountryViewModel;
import helper.GamePhase;
// TODO: Auto-generated Javadoc

/**
 * This class implements the Risk game view designed in gui .
 *
 * @author naren
 * @version 2.0.0
 */
public class BoardView implements Observer {

	/** The frame game window. */
	// Board Initialization
	private static JFrame frameGameWindow ;
	
	/** The panel game action. */
	private static JPanel panel_gameAction;

	/** The lab map. */
	// Map variables
	private static JLabel lab_map;
	
	/** The pane map scroll pane. */
	private static JScrollPane pane_mapScrollPane = null;
	
	/** The map hash map. */
	private static HashMap<String, Component> map_hashMap = new HashMap<>();

	/** The lab game phase. */
	// Phase variables
	private static JLabel lab_gamePhase;
	
	/** The lab nameof phase. */
	private static JLabel lab_nameofPhase;

	// Phase View Actions Components
	private static JComponent component_gamePhaseActions;
	private static JScrollPane pane_gameScrollPhaseView;
	private static JLabel lab_currentAction;

	/** The lab initialisation. */
	// Initialization variables
	private static JLabel lab_initialisation;
	
	/** The lab players turn. */
	private static JLabel lab_playersTurn;
	
	/** The lab armies left. */
	private static JLabel lab_armiesLeft;

	/** The lab reinforcement. */
	// Reinforcement variables
	private static JLabel lab_reinforcement;
	
	/** The lab unassigned reinforcement. */
	private static JLabel lab_unassignedReinforcement;

	/** The lab attack. */
	// Attack Label
	private static JLabel lab_attack;
	
	/** The combo attacker country. */
	private static JComboBox<String> combo_attackerCountry;
	
	/** The combo defender country. */
	private static JComboBox<String> combo_defenderCountry;
	
	/** The combo attacker no of dice. */
	private static JComboBox<String> combo_attackerNoOfDice;
	
	/** The combo defender no of dice. */
	private static JComboBox<String> combo_defenderNoOfDice;
	
	/** The combo attack move armies. */
	private static JComboBox<String> combo_attackMoveArmies;
	
	/** The button move armies. */
	private static JButton button_moveArmies = new JButton("Move");
	
	/** The button attack. */
	private static JButton button_attack = new JButton("Attack");
	
	/** The button all out. */
	private static JButton button_allOut = new JButton("All Out");
	
	/** The button end attack. */
	private static JButton button_endAttack = new JButton("End Attack");
	
	private static JButton saveGameButton = new JButton("Save Game");
	


	/** The lab fortification. */
	// Fortification variables
	private static JLabel lab_fortification;
	
	/** The combo country source. */
	private static JComboBox<String> combo_countrySource;

	/** The combo country destination. */
	private static JComboBox<String> combo_countryDestination;
	
	/** The combo army to move. */
	private static JComboBox<String> combo_armyToMove;
	
	/** The button move fortification. */
	private static JButton button_moveFortification = new JButton("Move Army");
	
	/** The button skip. */
	private static JButton button_skip = new JButton("Skip");

	/** The button player world domination view. */
	// Player World Domination Button
	private static JButton button_playerWorldDominationView;

	/** The screen size. */
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/** The screen height. */
	int screen_height = screenSize.height;
	
	/** The screen width. */
	int screen_width = screenSize.width;

	/** The active player name. */
	//Flags for determining the next actions
	String activePlayerName = null;
	
	/** The active player id. */
	int activePlayerId;
	
	/** The active player color. */
	Colors activePlayerColor = null;
	
	/** The reinforcement unassigned armies count. */
	String activePlayerUnassignedArmiesCount, reinforcementUnassignedArmiesCount;  
	
	/** The obj print. */
	PrintConsoleAndUserInput obj_print=new PrintConsoleAndUserInput();


	/** The map path. */
	String mapPath;
	
	/** The country list. */
	ArrayList<CountryViewModel> countryList = new ArrayList<CountryViewModel>();
	
	/** The phase. */
	GamePhase phase;
	
	/** The map it. */
	MapModel mapIt;



	//----------------------------- View Update Function ---------------------------
	/**
	 * method to perform all the actions.
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Game game = ((Game)arg0);
		mapIt = game.getMap();
		mapPath = game.getMap().getMapDir()+game.getMap().getMapName()+ ".bmp";
		phase = game.getGamePhase(); 
		File tempFile = new File(mapPath);
		boolean exists = tempFile.exists();
		if (!exists) {
			mapPath = game.getMap().getMapDir()+"no.bmp";
		}

		MapModel map = game.getMap();
		activePlayerName = game.getCurrentPlayer().getPlayerName();
		activePlayerId = game.getCurrentPlayerId();
		activePlayerColor = game.getCurrentPlayer().getColor();
		activePlayerUnassignedArmiesCount = Integer.toString(game.getCurrentPlayer().getNumberOfInitialArmies()); 
		reinforcementUnassignedArmiesCount = Integer.toString(game.getCurrentPlayer().getNumberOfReinforcedArmies());
		countryList.clear();

		for(Country country: map.getCountryList()){  
			CountryViewModel viewCountry = new CountryViewModel();
			viewCountry.setCountryId(country.getCountryId());
			viewCountry.setColorOfCountry(country.getCountryColor());
			viewCountry.setCountryName(country.getCountryName());
			viewCountry.setNumberOfArmies(country.getnoOfArmies());
			viewCountry.setxCoordinate(country.getxCoordinate());
			viewCountry.setyCoordinate(country.getyCoordinate());
			viewCountry.setNeighbours(country.getNeighboursString());
			viewCountry.setPlayerID(country.getPlayerId());
			JLabel label = (JLabel)map_hashMap.get(String.valueOf(country.getCountryId()));
			if(label != null){
				label.setText(String.valueOf(viewCountry.getNumberOfArmies()));
				label.setForeground(PrintConsoleAndUserInput.getColor(viewCountry.getColorOfCountry()));
			}
			countryList.add(viewCountry);
		}
		if(lab_playersTurn != null){
			lab_playersTurn.setText(activePlayerName);
			lab_playersTurn.setForeground(PrintConsoleAndUserInput.getColor(activePlayerColor));
			lab_armiesLeft.setText(activePlayerUnassignedArmiesCount);
			lab_unassignedReinforcement.setText(reinforcementUnassignedArmiesCount);

			if (game.getGamePhase() == GamePhase.Startup) {
				lab_nameofPhase.setText("Initialization");
				lab_fortification.setVisible(false); lab_initialisation.setVisible(true);
				lab_reinforcement.setVisible(false);
			} else if (game.getGamePhase() == GamePhase.Reinforcement) {
				lab_nameofPhase.setText("Reinforcement");
				lab_nameofPhase.setForeground(Color.red);
				
				lab_fortification.setVisible(false); lab_reinforcement.setVisible(true);
				lab_attack.setVisible(false);
				 
			} else if (game.getGamePhase() == GamePhase.Attack) {
				lab_nameofPhase.setText("Attack Phase");
				lab_nameofPhase.setForeground(Color.BLUE);	
				lab_attack.setVisible(true); lab_fortification.setVisible(false); 
				combo_attackerCountry();
			} else if (game.getGamePhase() == GamePhase.Fortification) {
				lab_nameofPhase.setText("Fortification");
				lab_nameofPhase.setForeground(Color.MAGENTA);	
				lab_attack.setVisible(false); lab_fortification.setVisible(true); 
				combo_sourceCountry();
			}
			component_gamePhaseActions.removeAll();
			int strartY = 5;
			ArrayList<String> gamePhaseDetailForPrint = game.getGamePhaseDetails();
			for (String gamePhaseDetailString : gamePhaseDetailForPrint) {
				JLabel textLabel = new JLabel();
				System.out.println(gamePhaseDetailString);
				textLabel.setText(gamePhaseDetailString);
				Font font = new Font("Courier", Font.ITALIC, 16);
				textLabel.setFont(font);
				textLabel.setBounds(15, strartY, 220, 20);
				strartY = strartY + 20;

				component_gamePhaseActions.add(textLabel);
			}
			component_gamePhaseActions.setPreferredSize(new Dimension(300, strartY));
			component_gamePhaseActions.revalidate();
			component_gamePhaseActions.repaint();
		}

	}

	//-------------------------- Board View Initializer ---------------------------------
	/**
	 * Method that loads up the GUI window.
	 */
	public void gameWindowLoad() {
		
		frameGameWindow=  new JFrame("Risk Game");
		frameGameWindow.toFront();
		panel_gameAction=new JPanel(null);

		mapGenerator();
		createPlayerWorldDominationView();
		createSaveGameButton();
		gamePhase();
		loadingPhaseActionLabel();
		view_initialisation();
		reinforcements();
		fortification();
		viewAttackPhase();
	
		

		frameGameWindow.setSize(pane_mapScrollPane.getWidth()+550, 1200);
		frameGameWindow.setVisible(true);
		panel_gameAction.setBackground(Color.white);
		frameGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	

	//------------------------ Map View initializing -----------------------------
	/**
	 * This method is initializing the jframe and importing the map file and country related data.
	 */
	public void mapGenerator() {

		File imageFile = null;

		imageFile = new File(mapPath);
		Image image;
		ImageIcon icon = null;
		try {
			image = ImageIO.read(imageFile);
			icon = new ImageIcon(image);
		} catch (IOException e) {

			e.printStackTrace();
		}

		lab_map = new JLabel(icon);

		for (int i = 0; i < countryList.size(); i++) {
			CountryViewModel viewCountry = countryList.get(i);
			int xCoordinate = viewCountry.getxCoordinate();
			int yCoordinate = viewCountry.getyCoordinate();
			JLabel newLabel = new JLabel("" + viewCountry.getNumberOfArmies());
			newLabel.setName("mapLabel" + viewCountry.getCountryId());
			newLabel.setToolTipText(viewCountry.getCountryName());
			newLabel.setBounds(xCoordinate, yCoordinate, 25, 25);
			newLabel.setFont(new Font("Courier", Font.BOLD, 20));
			newLabel.setForeground(PrintConsoleAndUserInput.getColor(viewCountry.getColorOfCountry()));
			map_hashMap.put(String.valueOf(viewCountry.getCountryId()), newLabel);
			lab_map.add(newLabel);
		}

		pane_mapScrollPane = new JScrollPane(lab_map);
		pane_mapScrollPane.setBounds(0,10,icon.getIconWidth()+20,icon.getIconHeight()+20);
		panel_gameAction.add(pane_mapScrollPane);
		frameGameWindow.add(panel_gameAction);
	}

	//-------------------------------  Game Phase details ---------------------------
	/**
	 * Method that updates the phase of the game.
	 */
	public void gamePhase() {
		lab_gamePhase = new JLabel();
		TitledBorder tb = BorderFactory.createTitledBorder(null, "Current Phase", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Serif", Font.PLAIN, 12), Color.blue);
		lab_gamePhase.setBorder(
				tb);
		String nm="#6600cc";
	//	tb.setBorder(new LineBorder(Color.decode(nm)));
		lab_gamePhase.setBounds(pane_mapScrollPane.getWidth()+10, pane_mapScrollPane.getY()+55, 490, 60);

		lab_nameofPhase = new JLabel("Initialization");
		Font font = new Font("Courier", Font.BOLD, 20);
		lab_nameofPhase.setFont(font);
		lab_nameofPhase.setBounds(15, 15, 220, 40);

		lab_gamePhase.add(lab_nameofPhase);
		panel_gameAction.add(lab_gamePhase);
	}

	/**
	 * Method to display the actions performed during each phase
	 */
	public void loadingPhaseActionLabel() {
		component_gamePhaseActions = new JPanel(){
		    @Override
		    public Dimension getPreferredSize() {
		        return new Dimension(300, 5000);
		    };
		};
		component_gamePhaseActions.setLayout(new FlowLayout(FlowLayout.LEFT));
		pane_gameScrollPhaseView = new JScrollPane(component_gamePhaseActions);
		pane_gameScrollPhaseView.setBounds(lab_gamePhase.getX(),lab_gamePhase.getY() + lab_gamePhase.getHeight()+20,
				lab_gamePhase.getWidth(), 150);
		TitledBorder tb = BorderFactory.createTitledBorder(null, "Phase Details", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Serif", Font.PLAIN, 12), Color.blue);
		pane_gameScrollPhaseView.setBorder(tb);
		panel_gameAction.add(pane_gameScrollPhaseView);
	}

	//--------------------------- initial Phase Start -----------------------------
	/**
	 * Method for initialization of game view.
	 */
	public void view_initialisation() {
		lab_initialisation = new JLabel();
		
		
		TitledBorder tb = BorderFactory.createTitledBorder(null, "Initialization Phase", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Serif", Font.PLAIN, 12), Color.blue);
		lab_initialisation.setBorder(tb);
		String nm="#6600cc";
	//	tb.setBorder(new LineBorder(Color.decode(nm)));
		lab_initialisation.setBounds(lab_gamePhase.getX(), pane_gameScrollPhaseView.getY()+ pane_gameScrollPhaseView.getHeight()+20, 490, 80);

		
		lab_playersTurn = new JLabel(activePlayerName);
		Font font = new Font("Courier", Font.BOLD, 24);
		lab_playersTurn.setFont(font);
		lab_playersTurn.setBorder(new TitledBorder("Active Player Name"));
//		lab_playersTurn.setBorder(new LineBorder(Color.decode(nm)));
		lab_playersTurn.setBounds(15, 25, 220, 50);
		lab_armiesLeft = new JLabel("" + activePlayerUnassignedArmiesCount);
		lab_armiesLeft.setBorder(new TitledBorder("Armies Left"));
		lab_armiesLeft.setBounds(lab_playersTurn.getX() + 240,
				lab_playersTurn.getY() - 50 + lab_playersTurn.getHeight(), lab_playersTurn.getWidth(),
				lab_playersTurn.getHeight());

		lab_initialisation.add(lab_playersTurn);
		lab_initialisation.add(lab_playersTurn);
		lab_initialisation.add(lab_armiesLeft);
		panel_gameAction.add(lab_initialisation);
	}

	//------------------------------- Reinforcement Phase ------------------------------
	/**
	 * Method for reinforcement implementation.
	 */
	public void reinforcements() {

		lab_reinforcement = new JLabel();
		TitledBorder tb = BorderFactory.createTitledBorder(null, "Reinforcement Phase", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Serif", Font.PLAIN, 12), Color.blue);
		lab_reinforcement.setBorder(tb);
		String nm="#6600cc";
		//tb.setBorder(new LineBorder(Color.decode(nm)));
		
		lab_reinforcement.setBounds(lab_initialisation.getX(),
				lab_initialisation.getY() +20 + lab_initialisation.getHeight(), lab_initialisation.getWidth(),
				80);
		lab_unassignedReinforcement = new JLabel(reinforcementUnassignedArmiesCount);
		lab_unassignedReinforcement.setBorder(new TitledBorder("Reinforced Unit"));
		lab_unassignedReinforcement.setBounds(15,25, 460,50);
		lab_reinforcement.setVisible(false);
		lab_reinforcement.add(lab_unassignedReinforcement);
		panel_gameAction.add(lab_reinforcement);
	}

	//------------------------------------ Attack phase ---------------------------------
	/**
	 * Method used to perform Attack phase of game.
	 */
	
	  public void viewAttackPhase() { 
		  lab_attack = new JLabel(); 
	  TitledBorder tb = BorderFactory.createTitledBorder(null, "Attack Phase", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Serif", Font.PLAIN, 12), Color.blue);
		lab_attack.setBorder(tb);
		String nm="#6600cc";
	//	tb.setBorder(new LineBorder(Color.decode(nm)));
	  lab_attack.setBounds(lab_reinforcement.getX(), lab_reinforcement.getY() + 25
	  + lab_reinforcement.getHeight(), lab_reinforcement.getWidth(), 250);
	  
	  combo_attackerCountry = new JComboBox();
	  combo_attackerCountry.setBorder(new
	  TitledBorder("Attack From"));
	  combo_attackerCountry.setBounds(15, 15, 220,50);
	  
	  combo_defenderCountry = new JComboBox();
	  combo_defenderCountry.setBorder(new TitledBorder("Attack To"));
	  combo_defenderCountry.setBounds(combo_attackerCountry.getX() + 20 +
	  combo_attackerCountry.getWidth() + 3, combo_attackerCountry.getY(),
	  combo_attackerCountry.getWidth(), combo_attackerCountry.getHeight());
	  
	  combo_attackerNoOfDice = new JComboBox<>();
	  combo_attackerNoOfDice.setBorder(new TitledBorder("Attacker's No Of Dice"));
	  combo_attackerNoOfDice.setBounds(combo_attackerCountry.getX(),
	  combo_attackerCountry.getY() + 7 + combo_attackerCountry.getHeight(),
	  combo_attackerCountry.getWidth(), combo_attackerCountry.getHeight());
	  
	  combo_defenderNoOfDice = new JComboBox<>();
	  combo_defenderNoOfDice.setBorder(new TitledBorder("Defender's No Of Dice"));
	  combo_defenderNoOfDice.setBounds(combo_attackerNoOfDice.getX() + 20 +
	  combo_attackerNoOfDice.getWidth() + 3, combo_attackerNoOfDice.getY(),
	  combo_attackerNoOfDice.getWidth(), combo_attackerNoOfDice.getHeight());
	  
	  button_attack.setBounds(combo_attackerNoOfDice.getX(),
	  combo_attackerNoOfDice.getY() + 7 + combo_attackerNoOfDice.getHeight(), 138,
	  30);
	  
	  button_allOut.setBounds(button_attack.getX() + button_attack.getWidth() + 21,
	  button_attack.getY(), 138, 30);
	  
	  button_endAttack.setBounds(button_allOut.getX() + button_allOut.getWidth() +
	  21, button_allOut.getY(), 138, 30);
	  
	  combo_attackMoveArmies = new JComboBox<>();
	  combo_attackMoveArmies.setBorder(new TitledBorder("Move armies"));
	  combo_attackMoveArmies.setBounds(button_attack.getX(), button_attack.getY() + button_attack.getHeight() + 7, combo_attackerNoOfDice.getWidth()+80,combo_attackerNoOfDice.getHeight());
	  combo_attackMoveArmies.setVisible(false);
	  button_moveArmies.setBounds(button_endAttack.getX(),combo_attackMoveArmies.getY() + 10, 138, 30);
	  button_moveArmies.setVisible(false);
	  lab_attack.add(combo_attackerCountry); lab_attack.add(combo_defenderCountry);
	  lab_attack.add(combo_attackerNoOfDice);
	  lab_attack.add(combo_defenderNoOfDice);
	  lab_attack.add(combo_attackMoveArmies); lab_attack.add(button_moveArmies);
	  lab_attack.add(button_attack); lab_attack.add(button_allOut);
	  lab_attack.add(button_endAttack); lab_attack.setVisible(false);
	  panel_gameAction.add(lab_attack);
	  
	  }
	 
	 
	//------------------------ Fortification View Initial ------------------------------
	/**
	 * Method for fortification implementation.
	 */
	public void fortification() {
		lab_fortification= new JLabel();
		TitledBorder tb = BorderFactory.createTitledBorder(null, "Fortification Phase", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("Serif", Font.PLAIN, 12), Color.blue);
		lab_fortification.setBorder(tb);
		lab_fortification.setBounds(lab_reinforcement.getX(),
				lab_reinforcement.getY() + 25 + lab_reinforcement.getHeight(), lab_reinforcement.getWidth(),
				140);

		combo_countrySource = new JComboBox();
		combo_countrySource.setBorder(new TitledBorder("Source Country"));
		combo_countrySource.setBounds(15, 25, 220, 50);
		combo_countryDestination = new JComboBox<>();
		combo_countryDestination.setBorder(new TitledBorder("Destination Country"));
		combo_countryDestination.setBounds(combo_countrySource.getX() + 20 + combo_countrySource.getWidth() + 3, combo_countrySource.getY(),
				combo_countrySource.getWidth(), combo_countrySource.getHeight());

		ArrayList<Integer> NoOfArmies = new ArrayList<Integer>();
		for (int i = 1; i <= Integer.parseInt(activePlayerUnassignedArmiesCount); i++) {
			NoOfArmies.add(i);
		}

		combo_armyToMove = new JComboBox(NoOfArmies.toArray());
		combo_armyToMove.setBounds(combo_countrySource.getX(), combo_countrySource.getHeight() + combo_countrySource.getY() + 7,
				combo_countrySource.getWidth(), combo_countrySource.getHeight());
		combo_armyToMove.setBorder(new TitledBorder("Total number of armies to move"));
		button_moveFortification.setBounds(combo_countryDestination.getX(),
				combo_countryDestination.getHeight() + combo_countryDestination.getY() + 17, 100, 30);
		button_skip.setBounds(button_moveFortification.getX() + button_moveFortification.getWidth() + 10,
				button_moveFortification.getY(), button_moveFortification.getWidth(),
				button_moveFortification.getHeight());
		lab_fortification.add(combo_countrySource);
		lab_fortification.add(combo_countryDestination);
		lab_fortification.add(combo_armyToMove);
		lab_fortification.add(button_moveFortification);
		lab_fortification.add(button_skip);
		lab_fortification.setVisible(false);
		panel_gameAction.add(lab_fortification);
	}

	// ----------------------------- Player Domination Button Initialized ------------------
	/**
	 * Method to display world domination view for each player.
	 */
	public void createPlayerWorldDominationView() {
		button_playerWorldDominationView = new JButton("Player World Domination View");
		button_playerWorldDominationView.setBounds(pane_mapScrollPane.getWidth()+15, pane_mapScrollPane.getY(), 250, 40);
		
		panel_gameAction.add(button_playerWorldDominationView);
	
	}

	/**
	 * World domination view listener.
	 * @param listener Action Listener
	 */
	//--------------------------------- Listener Initialization -------------------------
	public void worldDominationViewListener(ActionListener listener) {
		button_playerWorldDominationView.addActionListener(listener);
	}	
	
	
	// ----------------------------- Save and load Game ------------------	
	/**
	 * This method is used to create the save game button in the panel window.
	 */
	public void createSaveGameButton() {	
		saveGameButton.setBounds(button_playerWorldDominationView.getX() + button_playerWorldDominationView.getWidth() + 15,
				pane_mapScrollPane.getY(),200, 40);
		panel_gameAction.add(saveGameButton);	
	}
	
	/**
	 * Save game Listener.
	 * @param listener Action Listener
	 */
	public void saveGameButtonListener(ActionListener listener) {
		saveGameButton.addActionListener(listener);
	}
	
	
	
	/**
	 * method to use for the mouse event for the map labels.
	 * @param listener MouseListener
	 */
	public void addMapLabelsListener(MouseListener listener) {
		int n = lab_map.getComponentCount();
		for (int i = 0; i < n; i++) {
			JLabel jLabel = (JLabel) lab_map.getComponent(i);
			jLabel.addMouseListener(listener);
		}
	}

	/**
	 * method to add a listener in the combobox of the source country.
	 * @param listener ActionListener
	 */
	public void addActionListenToSourceCountryList(ActionListener listener) {
		combo_countrySource.addActionListener(listener);
	}

	/**
	 * method to add a listener in the combobox of the attacker country.
	 * @param listener ActionListener
	 */
	public void addActionListenToAttackerCountryList(ActionListener listener) {
		combo_attackerCountry.addActionListener(listener);
	}

	/**
	 * method to add a listener in the combobox of the defender country.
	 * @param listener ActionListener
	 */
	public void addActionListenToDefenderCountryList(ActionListener listener) {
		combo_defenderCountry.addActionListener(listener);
	}

	/**
	 * Method for performing action listener on move army button.
	 * @param listener ActionListener
	 */
	public void moveArmyButtonListener(ActionListener listener) {
		button_moveFortification.addActionListener(listener);
	}
	
	/**
	 * Method for performing action listener on attack Button.
	 * @param listener  ActionListener
	 */
	public void addActionListenToAttackButton(ActionListener listener) {
		button_attack.addActionListener(listener);
	}
	
	/**
	 * Method for performing action listener on End attack Button.
	 * @param listener ActionListener
	 */
	public void addActionListenToEndAttackButton(ActionListener listener) {
		button_endAttack.addActionListener(listener);
	}
	
	/**
	 * Method for performing action listener on End attack Button.
	 * @param listener ActionListener
	 */
	public void addActionListenToAllOutButton(ActionListener listener) {
		button_allOut.addActionListener(listener);
	}
	
	/**
	 * Method for performing action listener on move armies after concuring Button.
	 * @param listener ActionListener
	 */
	public void addActionListenToMoveButton(ActionListener listener) {
		button_moveArmies.addActionListener(listener);
	}
	
	/**
	 * Skip the fortification round
	 * @param listener ActionListener
	 */
	public void skipFortificationActionListener(ActionListener listener) {
		button_skip.addActionListener(listener);
		
	}
	//--------------------- General Functions for data gathering ---------------- 

	/**
	 * Static method to get selected source country.
	 * @return selectedCountry
	 */
	public static String getSourceCountry() {

		return (String)combo_countrySource.getSelectedItem();

	}

	/**
	 * method to add countries to the source country combo box.
	 */
	public void combo_sourceCountry(){
		combo_countrySource.removeAllItems();
		for (int i = 0; i < countryList.size(); i++) {
			CountryViewModel temp_cname = countryList.get(i);
			if (activePlayerId == temp_cname.getPlayerID()) {
				combo_countrySource.addItem(temp_cname.getCountryName());
			}
		}

	}

	/**
	 * Static method to get selected attacker country.
	 * @return selectedCountry
	 */
	public static String getAttackerCountry() {
		return (String)combo_attackerCountry.getSelectedItem();

	}

	/**
	 * method to add countries to the attacker country combo box.
	 */

	public void combo_attackerCountry(){
		combo_attackerCountry.removeAllItems();
		for (int i = 0; i < countryList.size(); i++) {
			CountryViewModel temp_cname = countryList.get(i);
			if (activePlayerId == temp_cname.getPlayerID()&& temp_cname.getNumberOfArmies()>1) {
				combo_attackerCountry.addItem(temp_cname.getCountryName());
			}
		}

	}

	/**
	 * Static method to get selected attacker country.
	 * @return selectedCountry
	 */
	public  String getDefenderCountry() {
		return (String)combo_defenderCountry.getSelectedItem();

	}


	/**
	 * Method is populating value in the destination phase combobox.
	 * @param defenderCountries ArrayList
	 */	
	public void combo_fillDefendersCountry(ArrayList<String> defenderCountries){   
		combo_defenderCountry.removeAllItems();
		for(String countryName : defenderCountries) {
			combo_defenderCountry.addItem(countryName);
		}

	}

	/**
	 * Method is populating value in the destination phase combobox.
	 * @param destinationCountries ArrayList
	 */	
	public void combo_fillDestinationCountry(ArrayList<String> destinationCountries){   
		combo_countryDestination.removeAllItems();
		for(String countryName : destinationCountries) {
			combo_countryDestination.addItem(countryName);
		}

	}

	/**
	 * static method to get the selected item from destination combo.
	 * @return selectedCountry
	 */
	public static String getDestinationCountry() {

		Object selectedItem = combo_countryDestination.getSelectedItem();
		if(selectedItem != null){
			String selectedCountry = (String) selectedItem;
			return selectedCountry;
		}
		else {
			return "";
		}
	}

	/**
	 * Method to add the possible number of the army the player can move.
	 * @param NoOfArmies int
	 */
	public void combo_fillArmyToMove(int NoOfArmies){   
		combo_armyToMove.removeAllItems();
		for(Integer i=0;i<NoOfArmies;i++)
			combo_armyToMove.addItem(i.toString());		
	}


	/**
	 * Static method to get number of army the player wants to move.
	 * @return NoOfArmies
	 */
	public static Integer combo_getArmyToMove() {
		Object selectedItem = combo_armyToMove.getSelectedItem();
		if(selectedItem != null){
			Integer NoOfArmies = (Integer.parseInt((String) selectedItem));
			return NoOfArmies;
		}
		return 0;
	}

	/**
	 * Method used to populate value in the attacker dice.
	 * @param allowableDices the new attacker dice combo box
	 */
	public void setAttackerDiceComboBox(int allowableDices) {
		combo_attackerNoOfDice.removeAllItems();
		for (int i = 1; i <= allowableDices; i++) {
			combo_attackerNoOfDice.addItem(Integer.toString(i));
		}
	}

	/**
	 * Static method to get selected attacker dice no.
	 * @return selectedCountry
	 */
	public static String getAttackerDiceNo() {
		return (String) combo_attackerNoOfDice.getSelectedItem();

	}
	
	/**
	 * Method used to populate value in the defender dice.
	 * @param allowableDices the new defender dice combo box
	 */
	public void setDefenderDiceComboBox(int allowableDices) {
		combo_defenderNoOfDice.removeAllItems();
		for (int i = 1; i <= allowableDices; i++) {
			combo_defenderNoOfDice.addItem(Integer.toString(i));
		}
	}
	
	/**
	 * Static method to get selected defender dice no.
	 * @return selectedCountry
	 */
	public static String getDefenderDiceNo() {
		return (String)combo_defenderNoOfDice.getSelectedItem();

	}
	
	/**
	 * A method to set visible to move after attack.
	 */
	public void setVisibalityOfMoveAfterConcure() {
		combo_attackMoveArmies.setVisible(true);
		button_moveArmies.setVisible(true);
	}
	
	/**
	 * A method to set visible to move after move.
	 */
	public void setVisibalityOfMoveAfterMove() {
		combo_attackMoveArmies.setVisible(false);
		button_moveArmies.setVisible(false);
	}
	
	/**
	 * Method used to populate value in the move.
	 * @param movePossible possible move
	 */
	public void setMoveComboBox(int movePossible) {
		combo_attackMoveArmies.removeAllItems();
		for (int i = 1; i < movePossible; i++) {
			combo_attackMoveArmies.addItem(Integer.toString(i));
		}
	}
	
	/**
	 * Static method to get selected move possible.
	 * @return selectedCountry
	 */
	public  String getMoveComboBox() {
		return (String)combo_attackMoveArmies.getSelectedItem();

	}
	

	/**
	 * get the frame to control the card conditions.
	 * @return frameGameWindow jframe for the card window
	 */
	public JFrame getFrameGameWindow() {
		return frameGameWindow;
	}
}
