package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import helper.PrintConsoleAndUserInput;

public class WorldDominationView {

	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();
	JPanel panelWindowForWorldDominationView = new JPanel(new BorderLayout());
	JFrame frameWindowForWorldDominationView = new JFrame("Player World Domination View");
	

	public void createJframeForWorldDominationView() {
		// TODO Auto-generated method stub		
		panelWindowForWorldDominationView.setLayout(new FlowLayout());
		panelWindowForWorldDominationView.setPreferredSize(new Dimension(600, 300));
		panelWindowForWorldDominationView.setBackground(Color.lightGray);
		

		
	
		
		frameWindowForWorldDominationView.setSize(600, 200);
		frameWindowForWorldDominationView.setLocationRelativeTo(null);
		frameWindowForWorldDominationView.setVisible(true);
		frameWindowForWorldDominationView.add(panelWindowForWorldDominationView);
		frameWindowForWorldDominationView.pack();
		frameWindowForWorldDominationView.setVisible(true);
		frameWindowForWorldDominationView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
