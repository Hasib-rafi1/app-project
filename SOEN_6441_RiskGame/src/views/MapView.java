package views;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.*; 
/**
 * This class is used to create a map. Also, this creates the window to show the map by using JFrame.
 * @author Gargi Sharma
 * @version 1.0.0
 */

public class MapView {
/*	String saveNameText = "Enter Map Name here";
	private static JTextArea textBox = new JTextArea("[Continents]\n\n\n[Territories]");
	private JTextField mapName = new JTextField(saveNameText);
	public static JButton saveMapButton = new JButton("SAVE MAP");
	private static JPanel pane = new JPanel();*/
	/**
	 * Create JFrame
	 */
	public static void createJFrame( ){		
		
		
		JFrame mapWindow= new JFrame();
		mapWindow.setTitle("Generate Map");
		mapWindow.setVisible(true);
		mapWindow.setSize(1500,800);
		
		Container windowContent= mapWindow.getContentPane();
		windowContent.setBackground(Color.blue);		
		mapWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	

		

		
	}

	
	

}
