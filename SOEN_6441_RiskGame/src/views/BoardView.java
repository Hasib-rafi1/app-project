package views;

import java.awt.Component;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

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

public class BoardView implements Observer {
	private static JFrame gameJframe = null;
	private static JPanel gameActionl;

	// Map Label
	private static JLabel mapJlabel;
	private static JScrollPane mapScrollPane = null;
	private static HashMap<String, Component> mapLabels = new HashMap<>();

	// Phase label
	private static JLabel gamePhaseJLabel;
	private static JLabel gamePhaseNameJLabel;

	// Initialization Label
	private static JLabel initializationJlabel;
	private static JLabel playersTurnJlabel;
	private static JLabel armyLeftJlabel;

	// Reinforcement Label
	private static JLabel reinforcementsJlabel;
	private static JLabel reinforcementUnassignedUnit;

	// Fortification Label
	private static JLabel fortificationJlabel;
	private static JComboBox<String> sourceCountry;

	private static JComboBox<String> destinationCountry;
	private static JComboBox<String> noOfArmyToMoveJcomboBox;
	private static JButton fortificationMoveButton = new JButton("Move Army");
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	

}
