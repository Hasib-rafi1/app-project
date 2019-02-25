package views;

import java.awt.Component;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Risk game view designed in java swing
 * 
 * @author naren
 *
 */

public class BoardView {
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

	private static JComboBox<String> var_destinationCountry;
	private static JComboBox<String> var_armiesToMove;
	private static JButton var_fortificationMove = new JButton("Move Army");

	/**
	 * Method for fortification implementation
	 */
public void fortification() {
	
}
	/**
	 * Method for reinforcement implementation
	 */
public void reinforcements() {
	
}
	/**
	 * Method for initialisation 
	 */
public void initialisation() {
	
	
}
}
