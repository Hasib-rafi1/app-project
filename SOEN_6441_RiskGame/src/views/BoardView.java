package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
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
import javax.swing.border.TitledBorder;

import helper.Colors;

import model.CountryViewModel;
import model.Game;

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
	String activePlayerUnassignedArmiesCount, reinforcementUnassignedArmiesCount;  
    String mapPath;
    ArrayList<CountryViewModel> countryList = new ArrayList<CountryViewModel>();
   // PhaseEnum phase;
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		Game game = ((Game)arg0);
	     
	  //   mapPath = game.getMap().+ "World.bmp";
		
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
	frame_gameWindow.setSize(1250, 700);
	frame_gameWindow.setVisible(true);
	frame_gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
}
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
	lab_fortification = new JLabel();
	lab_fortification.setBorder(
			BorderFactory.createTitledBorder(null, "Fortification Phase", TitledBorder.DEFAULT_JUSTIFICATION,
					TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLUE));
	lab_fortification.setBounds(lab_reinforcement.getX(),
			lab_reinforcement.getY() + 10 + lab_reinforcement.getHeight(), lab_reinforcement.getWidth(),
			140);
}
/**
 * Method that updates the phase of the game
 */
public void gamePhase() {
	
}
}
