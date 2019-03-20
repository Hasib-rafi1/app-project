package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Shape;

import javax.swing.*;

import helper.Colors;
import helper.PrintConsoleAndUserInput;
import helper.JTableRowNameDominationView;




// TODO: Auto-generated Javadoc
/**
 *This class implements the Players world domination view.
 *
 * @author Gargi Sharma
 * @version 1.0.0
 */
public class WorldDominationView {



	/** The print. */
	PrintConsoleAndUserInput print = new PrintConsoleAndUserInput();

	/** The panel window for world domination view. */
	JPanel panelWindowForWorldDominationView = new JPanel(new BorderLayout());

	/** The frame window for world domination view. */
	JFrame frameWindowForWorldDominationView = new JFrame("Player World Domination View");

	/**
	 * Creates the jframe for world domination view.
	 *
	 * @param rowData the row data
	 * @param playerNamesInTableColumns the player names in table columns
	 */
	public static void createJframeForWorldDominationView(String[][] rowData, String[] playerNamesInTableColumns) {	

	
		// TODO Auto-generated method stub		
		JPanel panelWindowForWorldDominationView = new JPanel(new BorderLayout());
		JFrame frameWindowForWorldDominationView = new JFrame("Players World Domination View");
		panelWindowForWorldDominationView.setLayout(new FlowLayout());
		panelWindowForWorldDominationView.setPreferredSize(new Dimension(1000, 200));


		// Putting the data in a table
		ListModel lm = new AbstractListModel() {
			String headers[] = {"Percentage Country", "Continents Owned", "Armies Owned"};
			public int getSize() { return headers.length; }
			public Object getElementAt(int index) {
				return headers[index];
			}
		};

		JTable table = new JTable(rowData, playerNamesInTableColumns);
		table.setEnabled(false);
		table.getTableHeader().setBackground(Color.orange);
//		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JList rowHeader = new JList(lm);
		rowHeader.setFixedCellWidth(150);

		rowHeader.setFixedCellHeight(table.getRowHeight()
				+ table.getRowMargin());
//                             + table.getIntercellSpacing().height);
		rowHeader.setCellRenderer(new JTableRowNameDominationView(table));

		JScrollPane scroll = new JScrollPane( table );
		scroll.setRowHeaderView(rowHeader);
		frameWindowForWorldDominationView.getContentPane().add(scroll, BorderLayout.CENTER);

//		frameWindowForWorldDominationView.getContentPane( ).add(new JScrollPane(table));
		frameWindowForWorldDominationView.setSize(1000, 200);
		frameWindowForWorldDominationView.setLocationRelativeTo(null);
		frameWindowForWorldDominationView.setVisible(true);
		frameWindowForWorldDominationView.add(panelWindowForWorldDominationView);
		frameWindowForWorldDominationView.pack();


		frameWindowForWorldDominationView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}


	




}
