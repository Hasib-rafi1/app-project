package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.JTableHeader;

import model.MapModel;

import model.Game;
import helper.Colors;
import helper.PrintConsoleAndUserInput;
import model.Country;
import model.CountryViewModel;
import helper.GamePhase;
/**
 * This class implements the Risk game view designed in gui 
 * @author naren
 * @version 1.0.0
 */
public class BoardView implements Observer {

	// Board Initialization
	private static JFrame frameGameWindow ;
	private static JPanel panel_gameAction;

	// Map variables
	private static JLabel lab_map;
	private static JScrollPane pane_mapScrollPane = null;
	private static HashMap<String, Component> map_hashMap = new HashMap<>();

	// Phase variables
	private static JLabel lab_gamePhase;
	private static JLabel lab_nameofPhase;

	// Initialization variables
	private static JLabel lab_initialisation;
	private static JLabel lab_playersTurn;
	private static JLabel lab_armiesLeft;

	// Reinforcement variables
	private static JLabel lab_reinforcement;
	private static JLabel lab_unassignedReinforcement;

	// Attack Label
	private static JLabel lab_attack;
	private static JComboBox<String> combo_attackerCountry;
	private static JComboBox<String> combo_defenderCountry;
	private static JComboBox<String> combo_attackerNoOfDice;
	private static JComboBox<String> combo_defenderNoOfDice;
	private static JComboBox<String> combo_attackMoveArmies;
	private static JButton button_moveArmies = new JButton("Move");
	private static JButton button_attack = new JButton("Attack");
	private static JButton button_allOut = new JButton("All Out");
	private static JButton button_endAttack = new JButton("End Attack");

	// Fortification variables
	private static JLabel lab_fortification;
	private static JComboBox<String> combo_countrySource;

	private static JComboBox<String> combo_countryDestination;
	private static JComboBox<String> combo_armyToMove;
	private static JButton button_moveFortification = new JButton("Move Army");
	private static JButton button_skip = new JButton("Skip");

	// Player World Domination Button
	private static JButton button_playerWorldDominationView;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screen_height = screenSize.height;
	int screen_width = screenSize.width;

	//Flags for determining the next actions
	String activePlayerName = null;
	int activePlayerId;
	Colors activePlayerColor = null;
	String activePlayerUnassignedArmiesCount, reinforcementUnassignedArmiesCount;  
	PrintConsoleAndUserInput obj_print=new PrintConsoleAndUserInput();


	String mapPath;
	ArrayList<CountryViewModel> countryList = new ArrayList<CountryViewModel>();
	GamePhase phase;

	//----------------------------- View Update Function ---------------------------
	/**
	 * method to perform all the actions 
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Game game = ((Game)arg0);

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
		}
	}

	//-------------------------- Board View Initializer ---------------------------------
	/**
	 * Method that loads up the GUI window
	 */
	public void gameWindowLoad() {
		frameGameWindow=  new JFrame("Risk Game");
		panel_gameAction=new JPanel(null);

		mapGenerator();
		createPlayerWorldDominationView();
		gamePhase();
		view_initialisation();
		reinforcements();
		fortification();
		viewAttackPhase();

		frameGameWindow.setSize(pane_mapScrollPane.getWidth()+550, 800);
		frameGameWindow.setVisible(true);
		panel_gameAction.setBackground(Color.white);
		frameGameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	//------------------------ Map View initializing -----------------------------
	/**
	 * This method is initializing the jframe and importing the map file and country related data
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
	 * Method that updates the phase of the game
	 */
	public void gamePhase() {
		lab_gamePhase = new JLabel();
		lab_gamePhase.setBorder(
				BorderFactory.createTitledBorder(null, "Current Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("Serif", Font.PLAIN, 12), Color.BLUE));

		lab_gamePhase.setBounds(pane_mapScrollPane.getWidth()+10, pane_mapScrollPane.getY()+55, 490, 60);

		lab_nameofPhase = new JLabel("Initialization");
		Font font = new Font("Courier", Font.BOLD, 20);
		lab_nameofPhase.setFont(font);
		lab_nameofPhase.setBounds(15, 15, 220, 40);

		lab_gamePhase.add(lab_nameofPhase);
		panel_gameAction.add(lab_gamePhase);
	}

	//--------------------------- initial Phase Start -----------------------------
	/**
	 * Method for initialization of game view
	 */
	public void view_initialisation() {
		lab_initialisation = new JLabel();
		lab_initialisation.setBorder(
				BorderFactory.createTitledBorder(null, "Initialization Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		lab_initialisation.setBounds(lab_gamePhase.getX(), lab_gamePhase.getY()+ lab_gamePhase.getHeight()+20, 490, 80);


		lab_playersTurn = new JLabel(activePlayerName);
		Font font = new Font("Courier", Font.BOLD, 24);
		lab_playersTurn.setFont(font);
		lab_playersTurn.setBorder(new TitledBorder("Active Player Name"));
		lab_playersTurn.setBounds(15, 25, 220, 40);

		lab_armiesLeft = new JLabel("" + activePlayerUnassignedArmiesCount);
		lab_armiesLeft.setBorder(new TitledBorder("Armies Left"));
		lab_armiesLeft.setBounds(lab_playersTurn.getX() + 240,
				lab_playersTurn.getY() - 40 + lab_playersTurn.getHeight(), lab_playersTurn.getWidth(),
				lab_playersTurn.getHeight());

		lab_initialisation.add(lab_playersTurn);
		lab_initialisation.add(lab_playersTurn);
		lab_initialisation.add(lab_armiesLeft);
		panel_gameAction.add(lab_initialisation);
	}

	//------------------------------- Reinforcement Phase ------------------------------
	/**
	 * Method for reinforcement implementation
	 */
	public void reinforcements() {

		lab_reinforcement = new JLabel();
		lab_reinforcement.setBorder(
				BorderFactory.createTitledBorder(null, "Reinforcement Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
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
	 * Method used to perform Attack phase of game
	 */

	
	
	  public void viewAttackPhase() { lab_attack = new JLabel(); lab_attack
	  .setBorder(BorderFactory.createTitledBorder(null, "Attack Phase",
	  TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new
	  Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
	  lab_attack.setBounds(lab_reinforcement.getX(), lab_reinforcement.getY() + 25
	  + lab_reinforcement.getHeight(), lab_reinforcement.getWidth(), 250);
	  
	  combo_attackerCountry = new JComboBox(); combo_attackerCountry.setBorder(new
	  TitledBorder("Attack From")); combo_attackerCountry.setBounds(15, 15, 220,
	  50);
	  
	  combo_defenderCountry = new JComboBox(); combo_defenderCountry.setBorder(new
	  TitledBorder("Attack To"));
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
	  combo_attackerNoOfDice.getY() + 7 + combo_attackerNoOfDice.getHeight(), 100,
	  30);
	  
	  button_allOut.setBounds(button_attack.getX() + button_attack.getWidth() + 21,
	  button_attack.getY(), 100, 30);
	  
	  button_endAttack.setBounds(button_allOut.getX() + button_allOut.getWidth() +
	  21, button_allOut.getY(), 100, 30);
	  
	  combo_attackMoveArmies = new JComboBox<>();
	  combo_attackMoveArmies.setBorder(new TitledBorder("Move armies"));
	  combo_attackMoveArmies.setBounds(button_attack.getX(), button_attack.getY() +
	  button_attack.getHeight() + 7, combo_attackerNoOfDice.getWidth(),
	  combo_attackerNoOfDice.getHeight());
	  
	  button_moveArmies.setBounds(button_endAttack.getX(),
	  combo_attackMoveArmies.getY() + 10, 100, 30);
	  
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
	 * Method for fortification implementation
	 */
	public void fortification() {
		lab_fortification= new JLabel();
		lab_fortification.setBorder(
				BorderFactory.createTitledBorder(null, "Fortification Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
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

		//button_moveFortification.setBounds(combo_countryDestination.getX(), combo_armyToMove.getY(),
			//	combo_countryDestination.getWidth(), combo_countryDestination.getHeight());
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

	// ----------------------------- Player Domination Button Initialzed ------------------
	/**
	 * Method to display world domination view for each player
	 */
	public void createPlayerWorldDominationView() {
		button_playerWorldDominationView = new JButton("Player World Domination View");
		//button_playerWorldDominationView.setBackground(Color.blue);
		button_playerWorldDominationView.setBounds(pane_mapScrollPane.getWidth()+10, pane_mapScrollPane.getY(), 490, 40);
		panel_gameAction.add(button_playerWorldDominationView,BorderLayout.NORTH);
	
	}

	//--------------------------------- Listener Initialization -------------------------
	public void worldDominationViewListener(ActionListener listener) {
		button_playerWorldDominationView.addActionListener(listener);
	}	
	
	/**
	 * method to use for the mouse event for the map labels
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
	 * method to add a listener in the combobox of the source country
	 * @param listener ActionListener
	 */
	public void addActionListenToSourceCountryList(ActionListener listener) {
		combo_countrySource.addActionListener(listener);
	}

	/**
	 * method to add a listener in the combobox of the attacker country
	 * @param listener ActionListener
	 */
	public void addActionListenToAttackerCountryList(ActionListener listener) {
		combo_attackerCountry.addActionListener(listener);
	}

	/**
	 * method to add a listener in the combobox of the defender country
	 * @param listener ActionListener
	 */
	public void addActionListenToDefenderCountryList(ActionListener listener) {
		combo_defenderCountry.addActionListener(listener);
	}

	/**
	 * Method for performing action listener on move army button
	 * @param listener ActionListener
	 */
	public void moveArmyButtonListener(ActionListener listener) {
		button_moveFortification.addActionListener(listener);
	}
	
	/**
	 * Method for performing action listener on attack Button
	 * 
	 * @param listener
	 *            ActionListener
	 */
	public void addActionListenToAttackButton(ActionListener listener) {
		button_attack.addActionListener(listener);
	}

	//--------------------- General Functions for data gathering ---------------- 

	/**
	 * Static method to get selected source country
	 * @return selectedCountry
	 */
	public static String getSourceCountry() {

		return (String)combo_countrySource.getSelectedItem();

	}

	/**
	 * method to add countries to the source country combo box
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
	 * Static method to get selected attacker country
	 * @return selectedCountry
	 */
	public static String getAttackerCountry() {
		return (String)combo_attackerCountry.getSelectedItem();

	}

	/**
	 * method to add countries to the attacker country combo box
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
	 * Static method to get selected attacker country
	 * @return selectedCountry
	 */
	public static String getDefenderCountry() {
		return (String)combo_defenderCountry.getSelectedItem();

	}


	/**
	 * Method is populating value in the destination phase combobox 
	 * @param defenderCountries ArrayList
	 */	
	public void combo_fillDefendersCountry(ArrayList<String> defenderCountries){   
		combo_defenderCountry.removeAllItems();
		for(String countryName : defenderCountries) {
			combo_defenderCountry.addItem(countryName);
		}

	}

	/**
	 * Method is populating value in the destination phase combobox 
	 * @param destinationCountries ArrayList
	 */	
	public void combo_fillDestinationCountry(ArrayList<String> destinationCountries){   
		combo_countryDestination.removeAllItems();
		for(String countryName : destinationCountries) {
			combo_countryDestination.addItem(countryName);
		}

	}

	/**
	 * static method to get the selected item from destination combo
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
	 * Method to add the possible number of the army the player can move
	 * @param NoOfArmies int
	 * 
	 */
	public void combo_fillArmyToMove(int NoOfArmies){   
		combo_armyToMove.removeAllItems();
		for(Integer i=0;i<NoOfArmies;i++)
			combo_armyToMove.addItem(i.toString());		
	}


	/**
	 * Static method to get number of army the player wants to move
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
	 * Method used to populate value in the attacker dice
	 * 
	 * @param allowableDices
	 */
	public void setAttackerDiceComboBox(int allowableDices) {
		combo_attackerNoOfDice.removeAllItems();
		for (int i = 1; i <= allowableDices; i++) {
			combo_attackerNoOfDice.addItem(Integer.toString(i));
		}
	}

	/**
	 * Static method to get selected attacker dice no
	 * @return selectedCountry
	 */
	public static String getAttackerDiceNo() {
		return (String) combo_attackerNoOfDice.getSelectedItem();

	}
	
	/**
	 * Method used to populate value in the defender dice
	 * 
	 * @param allowableDices
	 */
	public void setDefenderDiceComboBox(int allowableDices) {
		combo_defenderNoOfDice.removeAllItems();
		for (int i = 1; i <= allowableDices; i++) {
			combo_defenderNoOfDice.addItem(Integer.toString(i));
		}
	}
	
	/**
	 * Static method to get selected defender dice no
	 * @return selectedCountry
	 */
	public static String getDefenderDiceNo() {
		return (String)combo_defenderNoOfDice.getSelectedItem();

	}
}
