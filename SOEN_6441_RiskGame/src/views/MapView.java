package views;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import javax.swing.*; 

import helper.PrintConsoleAndUserInput;
/**
 * This class is used to create a map. Also, this creates the window to show the map by using JFrame.
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class MapView {	
	
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	JPanel panelWindow = new JPanel();
	JFrame frameWindow = new JFrame(getClass().getSimpleName());
	String textFieldName = "Enter file name you want to save?";
	JTextField mapName = new JTextField(textFieldName);
	public JButton saveButton = new JButton("SAVE MAP");
	JTextArea textParameters = new JTextArea("[Map]\n\n\n[Continents]\n\n\n[Territories]");	
	
	/**
	 * @author Gargi sharma
	 * @version 1.0.0
	 * This method displays menu for map generator.
	 * @param 
	 */
	public int displayMapMenu() {
		print.consoleOut("=================================");
		print.consoleOut("\t Map Generator menu\t");
		print.consoleOut("1. Import Map From File");
		print.consoleOut("2. Design a New Map");
		print.consoleOut("3. Edit The Map");
	/*	System.out.println("\n 2. Design a New Map");
		System.out.println("\n 3. Edit The Map");
		System.out.println("\n 4. Save The Map");
		System.out.println("\n 5. Display The Map");
		System.out.println("\n 6. Back to The Main Menu");
		System.out.println("\n\n Please Enter Your Choice(1-6): ");*/
		print.consoleOut("=================================");	
		return print.userIntInput();
	}
	
	/**
	 * This method is used to return content of file(like continents, territories etc)
	 * 
	 * @return String mapContent
	 */
	public String returnMapContent() {
		return textParameters.getText();
	}
	
	/**
	 * This method is used to return name of the map
	 * 
	 * @return String mapName
	 */
	public String returnMapName() {
		return mapName.getText();
	}
	
	/**
	 * This method is used to close the jFrame window
	 */
	public void closeFrameWindow() {
		// TODO Auto-generated method stub
		frameWindow.dispose();
	}

	/**
	 * This method is used to open a Jframe window to create a map.
	 * @author Gargi Sharma
	 */
	public void createJframe() {
		// TODO Auto-generated method stub		
		panelWindow.setLayout(new FlowLayout());			
		panelWindow.setPreferredSize(new Dimension(100, 100));
		panelWindow.setBackground(Color.lightGray);
		panelWindow.add(saveButton);
		panelWindow.add(mapName);
		panelWindow.add(Box.createHorizontalGlue());				
		
		// mapname text parameters
		mapName.setPreferredSize(new Dimension(200, 30));
		mapName.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (mapName.getText().equals(textFieldName)) {
					mapName.setText("");
					mapName.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (mapName.getText().isEmpty()) {
					mapName.setForeground(Color.GRAY);
					mapName.setText(textFieldName);
				}
			}
		});	
		
		frameWindow.setTitle("Map Generator");
		frameWindow.setPreferredSize(new Dimension(1200, 800));
		frameWindow.add(textParameters);
		textParameters.setFont(new Font("Courier", Font.BOLD,24));
		frameWindow.add(panelWindow, BorderLayout.SOUTH);	
		frameWindow.pack();
		frameWindow.setVisible(true);
		frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}
