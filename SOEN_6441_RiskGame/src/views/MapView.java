package views;

import java.awt.*;
import javax.swing.*; 
/**
 * This class is used to create a map. Also, this creates the window to show the map by using JFrame.
 * @author Gargi Sharma
 * @version 1.0.0
 */

public class MapView {
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
