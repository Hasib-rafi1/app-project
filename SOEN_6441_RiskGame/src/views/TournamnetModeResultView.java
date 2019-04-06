package views;

import helper.JTableRowNameDominationView;
import model.Game;
import strategies.PlayerStrategy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class TournamnetModeResultView {
    public static JPanel panelTournamnetModeResultView;
    public static JFrame frameTournamnetModeResultView;
    public static JTable table;
    public static JLabel label;
    public static JScrollPane scroll;
    public static JList rowHeader;
    private static Game gameGlobal;

//    public static void tournamentModeParams() {
//        frameTournamnetModeResultView = new JFrame("Congratulations");
//        panelTournamnetModeResultView = new JPanel();
//        frameTournamnetModeResultView.setSize(800, 300);
//        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        label = new JLabel("Congratulation! Player wins the game");
//        Font font = new Font("Courier", Font.BOLD, 30);
//        label.setFont(font);
//        label.setBounds(100, 100, 220, 40);
//        panelTournamnetModeResultView.add(label);
//        frameTournamnetModeResultView.add(panelTournamnetModeResultView);
//        frameTournamnetModeResultView.setVisible(true);
//
//    }

//    public static void callTournamnetResult(int M, int G, int D, HashMap<String, ArrayList<String>> tournamentResult,
//                                            ArrayList<PlayerStrategy> strategies) {
    public static void callTournamnetResult(int M, int G, int D, String[][] rowData, String[] playerNamesInTableColumns) {
        frameTournamnetModeResultView = new JFrame("The Tournament Result");
        panelTournamnetModeResultView = new JPanel(new BorderLayout());
        panelTournamnetModeResultView.setLayout(new FlowLayout());
        panelTournamnetModeResultView.setPreferredSize(new Dimension(1000, 200));


//        String[] getMapsUserUsed = tournamentResult.keySet().toArray(new String[tournamentResult.keySet().size()]);
        String headers[] = {"Percentage Country", "Continents Owned", "Armies Owned"};
        // Putting the data in a table
        ListModel lm = new AbstractListModel() {
            public int getSize() { return headers.length; }
            public Object getElementAt(int index) {
                return headers[index];
            }
        };
//        tournamentModeParams();
        table = new JTable(rowData, playerNamesInTableColumns );
        table.setEnabled(false);
        table.getTableHeader().setBackground(Color.pink);
        rowHeader = new JList(lm);
        rowHeader.setFixedCellWidth(150);
        rowHeader.setFixedCellHeight(table.getRowHeight() + table.getRowMargin());
        rowHeader.setCellRenderer(new JTableRowNameDominationView(table));

        //*****
        frameTournamnetModeResultView = new JFrame("Congratulations");
        panelTournamnetModeResultView = new JPanel();
        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        label = new JLabel("<html>M: [Map1, Map2]<br><html>" +"P:[Aggressive, Cheater]<br>"
                +"G:"+G+"<br>"+"D:"+D);

        label.setBounds(10, 10, 220, 40);
        panelTournamnetModeResultView.add(label);
        frameTournamnetModeResultView.add(panelTournamnetModeResultView);
        frameTournamnetModeResultView.setVisible(true);
        scroll = new JScrollPane( table );
        scroll.setRowHeaderView(label);
//        scroll.setRowHeaderView(rowHeader);

        frameTournamnetModeResultView.getContentPane().add(scroll, BorderLayout.CENTER);
        frameTournamnetModeResultView.setSize(1000, 200);
//        frameWindowForWorldDominationView.setLocationRelativeTo(null);
//        frameWindowForWorldDominationView.setVisible(true);
//        frameWindowForWorldDominationView.add(panelWindowForWorldDominationView);
//        frameWindowForWorldDominationView.pack();
        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
