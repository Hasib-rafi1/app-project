package views;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
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

public  class CardView {
	private static JFrame frame_cardExchange = null;
	private static JPanel panel_cardExchange;
	private static JLabel lab_cardExchange;
	private static JLabel lab_forPlayerTurn;
	//DefaultListModel dlm = new DefaultListModel();
	public static JList<String> list_cardsOwnedByThePlayer;
	private static JLabel lab_totalNewArmies;
	private static JButton button_cardExchange = new JButton("Exchange Cards");
	private static JButton button_exit = new JButton("Skip Exchange");
	//Instantiate game object
	 Game game;
	public CardView(Game gameTemp){
		game = gameTemp;
	}
	public  void Exchange() {
		frame_cardExchange = new JFrame("Card Exchange View");
		panel_cardExchange = new JPanel(null);
		frame_cardExchange.setSize(800, 600);

		frame_cardExchange.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		lab_cardExchange = new JLabel();
		lab_cardExchange
		.setBorder(BorderFactory.createTitledBorder(null, "Exchange Card", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, new Font("SansSerif", Font.PLAIN, 12), Color.BLACK));
		lab_cardExchange.setBounds(100, 100, 600, 400);
		lab_forPlayerTurn = new JLabel(game.getCurrentPlayer().getPlayerName());
		Font font = new Font("Courier", Font.BOLD, 24);
		lab_forPlayerTurn.setFont(font);
		lab_forPlayerTurn.setForeground(Color.RED);
		lab_forPlayerTurn.setBorder(new TitledBorder("Active Player"));
		lab_forPlayerTurn.setBounds(30, 45, 250, 70);
		ArrayList<Card> typeOfCards = game.getCurrentPlayer().getCards();
		String cards[] = new String[typeOfCards.size()];
		for (int i = 0; i < typeOfCards.size(); i++) {
			cards[i] = typeOfCards.get(i).toString();
		}
		list_cardsOwnedByThePlayer = new JList<>(cards);
		JScrollPane s = new JScrollPane(list_cardsOwnedByThePlayer);
	   // s.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		list_cardsOwnedByThePlayer.setBorder(new TitledBorder("Cards Owned"));
		list_cardsOwnedByThePlayer.setBounds(310, 45, 250, 70);

		lab_totalNewArmies = new JLabel("" + game.getCurrentPlayer().getCards());
		
		lab_totalNewArmies.setBorder(new TitledBorder("New  Armies Assigned"));
		lab_totalNewArmies.setBounds(180, 150, 250, 70);
		button_cardExchange.setBounds(120, 255, 160, 40);
		button_exit.setBounds(310, 255, 160, 40);
		
		lab_cardExchange.add(lab_totalNewArmies);
		lab_cardExchange.add(list_cardsOwnedByThePlayer);
		//frame_cardExchange.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		lab_cardExchange.add(lab_forPlayerTurn);
		lab_cardExchange.add(button_cardExchange);
		lab_cardExchange.add(button_exit);
	//	panel_cardExchange.add(new JScrollPane (lab_cardExchange));
		panel_cardExchange.add(lab_cardExchange);
		frame_cardExchange.add(panel_cardExchange);
		frame_cardExchange.setVisible(true);
		
	}

	public static void exchange_actionListener(ActionListener listener) {
		button_cardExchange.addActionListener(listener);
	}
	public static void exit_actionListener(ActionListener listener) {
		button_exit.addActionListener(listener);
	}
	
	
	
}
