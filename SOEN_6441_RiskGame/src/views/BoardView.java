package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;



import model.MapModel;

import model.Game;
import helper.Colors;
import helper.PrintConsoleAndUserInput;
import model.Country;
import model.CountryViewModel;
import model.Game;
import helper.GamePhase;
import model.Player;
/**
 * Risk game view designed in java swing
 * 
 * @author naren
 *
 */
public class BoardView implements Observer {
	private static JFrame frame_gameWindow = null;
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

	// Fortification variables
	private static JLabel lab_fortification;
	private static JComboBox<String> combo_countrySource;

	private static JComboBox<String> combo_countryDestination;
	private static JComboBox<String> combo_armyToMove;
	private static JButton button_moveFortification = new JButton("Move Army");

	String activePlayerName = null;
	int activePlayerId;
	Colors activePlayerColor = null;
	String activePlayerUnassignedArmiesCount = "5", reinforcementUnassignedArmiesCount;  
	PrintConsoleAndUserInput obj_print=new PrintConsoleAndUserInput();
	

	String mapPath =obj_print.getMapDir()+ "World.bmp" ;
	ArrayList<CountryViewModel> countryList = new ArrayList<CountryViewModel>();
	 GamePhase phase;
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Game game = ((Game)arg0);
<<<<<<< HEAD
		//MapModel map=game.getMap();
=======
		game.getMap().getContinentList();
>>>>>>> branch 'master' of https://naren_csp@bitbucket.org/gargisharma5292/soen_6441_riskgame.git
		mapPath = game.getMap().getMapDir()+ "World.bmp";
	     phase = game.getGamePhase(); 
	    // mapPath = map.getMapPath() + map.getMapName() + ".bmp";
	     MapModel map = game.getMap();
	     activePlayerName = game.getCurrentPlayer().getPlayerName();
	     activePlayerId = game.getCurrentPlayerId();
	     activePlayerColor = game.getCurrentPlayer().getColor();
	     activePlayerUnassignedArmiesCount = Integer.toString(game.getCurrentPlayer().getNumberOfInitialArmies()); 
	     reinforcementUnassignedArmiesCount = Integer.toString(game.getCurrentPlayer().getNumberOfReinforcedArmies());
	     countryList.clear();
	     for(Country country: map.getCountryList())
	     {  CountryViewModel viewCountry = new CountryViewModel();
	        viewCountry.setCountryId(country.getCountryId());
	        viewCountry.setColorOfCountry(country.getCountryColor());
	        viewCountry.setCountryName(country.getCountryName());
	        viewCountry.setNumberOfArmies(country.getnoOfArmies());
	        viewCountry.setxCoordinate(country.getxCoordinate());
	        viewCountry.setyCoordinate(country.getyCoordinate());
	        viewCountry.setNeighbours(country.getNeighboursString());
	        viewCountry.setPlayerID(country.getPlayerId());
	        JLabel label = (JLabel)map_hashMap.get(String.valueOf(country.getCountryId()));
	        if(label != null)
	        { label.setText(String.valueOf(viewCountry.getNumberOfArmies()));
	        }
	        countryList.add(viewCountry);
	     }
	     if(lab_playersTurn != null)
	     {
	    	 lab_playersTurn.setText(activePlayerName);
	    
	    	 
	    	 lab_playersTurn.setForeground(PrintConsoleAndUserInput.getColor(activePlayerColor));
			lab_armiesLeft.setText(activePlayerUnassignedArmiesCount);
			
			lab_unassignedReinforcement.setText(reinforcementUnassignedArmiesCount);
			
			if (game.getGamePhase() == GamePhase.Startup) {
					lab_gamePhase.setText("Initialization");
				} else if (game.getGamePhase() == GamePhase.Reinforcement) {
					lab_gamePhase.setText("Reinforcement");
				} else if (game.getGamePhase() == GamePhase.Attack) {
					lab_gamePhase.setText("Attack - not implemented");
					
				} else if (game.getGamePhase() == GamePhase.Fortification) {
					lab_gamePhase.setText("Fortification");
					comboSourceCountry();
				}
	     }

	}

	/**
	 * Method that loads up the GUI window
	 */
	public void gameWindowLoad() {
		actionPlan();
		gamePhase();
		view_initialisation();
		reinforcements();
		fortification();
		
		frame_gameWindow.setSize(1450, 600);
		frame_gameWindow.setVisible(true);
		frame_gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	


	/**
	 * This method is initializing the jframe and importing the map file and country related data
	 */
	public void actionPlan() {
		frame_gameWindow= new JFrame("Risk Game");
		panel_gameAction=new JPanel(null);
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
			newLabel.setFont(new Font("Courier", Font.ITALIC, 20));
			newLabel.setForeground(PrintConsoleAndUserInput.getColor(viewCountry.getColorOfCountry()));
			map_hashMap.put(String.valueOf(viewCountry.getCountryId()), newLabel);
			lab_map.add(newLabel);
		}

		pane_mapScrollPane = new JScrollPane(lab_map);
		pane_mapScrollPane.setBounds(0,10,920,520);
		panel_gameAction.add(pane_mapScrollPane);
		frame_gameWindow.add(panel_gameAction);
	}
	
	/**
	 * Method that updates the phase of the game
	 */
	public void gamePhase() {
		lab_gamePhase = new JLabel();
		lab_gamePhase.setBorder(
				BorderFactory.createTitledBorder(null, "Phase Information", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		lab_gamePhase.setBounds(pane_mapScrollPane.getX()+930, pane_mapScrollPane.getY(), 490, 100);

		lab_nameofPhase = new JLabel("Initialization");
		Font font = new Font("Courier", Font.BOLD, 24);
		lab_nameofPhase.setFont(font);
		lab_nameofPhase.setBounds(15, 15, 220, 70);
		
		lab_gamePhase.add(lab_nameofPhase);
		panel_gameAction.add(lab_gamePhase);
	}
	
	
	/**
	 * Method for initialisation of game view
	 */
	public void view_initialisation() {
		lab_initialisation = new JLabel();

		lab_initialisation.setBorder(
				BorderFactory.createTitledBorder(null, "Initialization Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		lab_initialisation.setBounds(lab_gamePhase.getX(), lab_gamePhase.getY()+ lab_gamePhase.getHeight()+10, 490, 100);

		// Recreate every components in Label
		lab_playersTurn = new JLabel(activePlayerName);
		Font font = new Font("Courier", Font.BOLD, 24);
		lab_playersTurn.setFont(font);
		//lab_playersTurn.setForeground(Colors.(activePlayerColor));
		lab_playersTurn.setBorder(new TitledBorder("Active Player"));
		lab_playersTurn.setBounds(15, 25, 220, 70);

		lab_armiesLeft = new JLabel("" + activePlayerUnassignedArmiesCount);
		lab_armiesLeft.setBorder(new TitledBorder("Armies Left"));
		lab_armiesLeft.setBounds(lab_playersTurn.getX() + 240,
				lab_playersTurn.getY() - 70 + lab_playersTurn.getHeight(), lab_playersTurn.getWidth(),
				lab_playersTurn.getHeight());

		lab_initialisation.add(lab_playersTurn);
		lab_initialisation.add(lab_playersTurn);
		lab_initialisation.add(lab_armiesLeft);

		panel_gameAction.add(lab_initialisation);
	}
	/**
	 * Method for reinforcement implementation
	 */
	public void reinforcements() {
		lab_reinforcement = new JLabel();
		lab_reinforcement.setBorder(
				BorderFactory.createTitledBorder(null, "Reinforcement Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		lab_reinforcement.setBounds(lab_initialisation.getX(),
				lab_initialisation.getY() + 10 + lab_initialisation.getHeight(), lab_initialisation.getWidth(),
				80);

		lab_unassignedReinforcement = new JLabel(reinforcementUnassignedArmiesCount);
		lab_unassignedReinforcement.setBorder(new TitledBorder("Reinforced Army Unit"));
		lab_unassignedReinforcement.setBounds(15,25, 460,50);

		lab_reinforcement.add(lab_unassignedReinforcement);
		panel_gameAction.add(lab_reinforcement);
	}
	/**
	 * Method for fortification implementation
	 */
	public void fortification() {
		lab_fortification = new JLabel();
		lab_fortification.setBorder(
				BorderFactory.createTitledBorder(null, "Fortification Phase", TitledBorder.DEFAULT_JUSTIFICATION,
						TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
		lab_fortification.setBounds(lab_reinforcement.getX(),
				lab_reinforcement.getY() + 10 + lab_reinforcement.getHeight(), lab_reinforcement.getWidth(),
				140);
		
		combo_countrySource = new JComboBox();
		combo_countrySource.setBorder(new TitledBorder("Source Country"));
		combo_countrySource.setBounds(15, 25, 220, 50);

		String destinationCountries[] = { " " };
		combo_countryDestination = new JComboBox<>(destinationCountries);
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
		combo_armyToMove.setBorder(new TitledBorder("Total number of army to move"));
		//button_moveFortification.setFocusable(true);
		button_moveFortification.setBounds(combo_countryDestination.getX(), combo_armyToMove.getY(),
				combo_countryDestination.getWidth(), combo_countryDestination.getHeight());

		// Add all components in Label
		lab_fortification.add(combo_countrySource);
		lab_fortification.add(combo_countryDestination);
		lab_fortification.add(combo_armyToMove);
		lab_fortification.add(button_moveFortification);
		// Adding Label to Panel
		panel_gameAction.add(lab_fortification);
	}
	
	/**
	 * This method is going to use for the mouse event for the map labels
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
	 * This method is going to add a listener in the combobox of the source country
	 * @param listener ActionListener
	 */
	public void addActionListenToSourceCountryList(ActionListener listener) {
		combo_countrySource.addActionListener(listener);
	}
	
	
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
	public void comboSourceCountry(){
		combo_countrySource.removeAllItems();
		for (int i = 0; i < countryList.size(); i++) {
			CountryViewModel temp_cname = countryList.get(i);
			if (activePlayerId == temp_cname.getPlayerID()) {
				combo_countrySource.addItem(temp_cname.getCountryName());
			}
		}
		
	}
	
	/**
	 * Method is populating value in the destination phase combobox 
	 * @param destinationCountries ArrayList
	 */	
	public void populateDestinationCountryComboBox(ArrayList<String> destinationCountries)
	{   combo_countryDestination.removeAllItems();
		for(String countryName : destinationCountries)
		   combo_countryDestination.addItem(countryName);
			
	}
	
	/**
	 * Method to add the possible number of the army the player can move
	 * @param NoOfArmies int
	 * 
	 */
	public void populateNoOfArmyToMoveJcomboBox(int NoOfArmies)
	{   combo_armyToMove.removeAllItems();
		for(Integer i=0;i<NoOfArmies;i++)
			combo_armyToMove.addItem(i.toString());		
	}
	
	
	/**
	 * Method for performing action listener on move army button
	 * @param listener ActionListener
	 */
	public void moveArmyButtonListener(ActionListener listener) {
		button_moveFortification.addActionListener(listener);
	}
	
	/**
	 * get the selected item from destination combo
	 * @return selectedCountry
	 */
	public static String getDestinationCountry() {
		
		Object selectedItem = combo_countryDestination.getSelectedItem();
		if(selectedItem != null)
		{
			String selectedCountry = (String) selectedItem;
			return selectedCountry;
		}
		else {
			return "";
		}
	}
	
	
	/**
	 * Static method to get number of army the player wants to move
	 * @return NoOfArmies
	 */
	public static Integer getNoOfArmyToMoveJcomboBox() {
		Object selectedItem = combo_armyToMove.getSelectedItem();
		if(selectedItem != null)
		{
			Integer NoOfArmies = (Integer.parseInt((String) selectedItem));
			return NoOfArmies;
		}
		return 0;
	}
}
