package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


import model.Game;
import helper.Card;

/**
 *
 * Card View lets the player to chose the cards during the
 * Reinforcement phase of the game to obtain new armies.
 * @author Jaiganesh
 */

public class FinishView {
	private static JFrame frame_congratulation = null;
	private static JPanel panel_congratulation;
	private static JLabel lab_congratulation;
	
	public void Exchange(String playerName) {
		frame_congratulation = new JFrame("Congratulation");
		panel_congratulation = new JPanel();
		frame_congratulation.setSize(800, 300);

		frame_congratulation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lab_congratulation = new JLabel("Congratulation! Player "+ playerName + " wins the game");
		Font font = new Font("Courier", Font.BOLD, 30);
		lab_congratulation.setFont(font);
		lab_congratulation.setBounds(100, 100, 220, 40);
		panel_congratulation.add(lab_congratulation);
		frame_congratulation.add(panel_congratulation);
		frame_congratulation.setVisible(true);
		
	}
}
