package views;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



import model.CountryViewModel;

/**
 * Risk game view designed in java swing
 * 
 * @author naren
 *
 */
public class BoardView implements Observer {
	private static JFrame var_gameWindow = null;
	private static JPanel var_gameAction;

	// Map variables
	private static JLabel var_map;
	private static JScrollPane var_mapScrollPane = null;
	private static HashMap<String, Component> var_hashMap = new HashMap<>();

	// Phase variables
	private static JLabel var_gamePhase;
	private static JLabel var_nameofPhase;

	// Initialization variables
	private static JLabel var_initialisation;
	private static JLabel var_playersTurn;
	private static JLabel var_armiesLeft;

	// Reinforcement variables
	private static JLabel var_reinforcement;
	private static JLabel var_unassignedReinforcement;

	// Fortification variables
	private static JLabel var_fortification;
	private static JComboBox<String> var_sourceCountry;

	private static JComboBox<String> destinationCountry;
	private static JComboBox<String> noOfArmyToMoveJcomboBox;
	private static JButton fortificationMoveButton = new JButton("Move Army");

	/*String activePlayerName = null;
    int activePlayerId;
    EnumColor activePlayerColor = null;
	String activePlayerUnassignedArmiesCount, reinforcementUnassignedArmiesCount;   
    String mapPath;
    ArrayList<CountryViewModel> countryList = new ArrayList<CountryViewModel>();
    PhaseEnum phase;*/
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	

    



/**
 * Method that loads up the GUI window
 */
public void gameWindowLoad() {
	
	//view_initialisation();
	//reinforcements();
	//fortification();
	//gamePhase();
	actionPlan();
	var_gameWindow.setSize(1250, 700);
	var_gameWindow.setVisible(true);
	var_gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
}
public void actionPlan() {
	var_gameWindow= new JFrame("Risk Game");
	var_gameAction=new JPanel(null);
	
}
/**
 * Method for initialisation of game view
 */
public void view_initialisation() {

}
/**
 * Method for reinforcement implementation
 */
public void reinforcements() {

}
/**
 * Method for fortification implementation
 */
public void fortification() {

}
/**
 * Method that updates the phase of the game
 */
public void gamePhase() {
	
}
}
