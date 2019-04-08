package views;

import helper.JTableRowNameDominationView;
import model.Game;
import model.Player;
import strategies.PlayerStrategy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TournamentModeResultView {
    public static JPanel panelTournamnetModeResultView;
    public static JFrame frameTournamnetModeResultView;
    public static JTable table;
    public static JTable table1, table2;
    public static JLabel label;
    public static JScrollPane scroll;
    public static JList rowHeader;
    private static Game gameGlobal;

    public static void callTournamentResult(int M, int G, int D, HashMap<String, ArrayList<String>> tournamentResult,
                                            ArrayList<PlayerStrategy> strategies) {
//    public static void callTournamentResult(int M, int G, int D, String[][] rowData, String[] playerNamesInTableColumns) {
        frameTournamnetModeResultView = new JFrame("The Tournament Result");
        panelTournamnetModeResultView = new JPanel(new BorderLayout());
        panelTournamnetModeResultView.setLayout(new FlowLayout());
        panelTournamnetModeResultView.setPreferredSize(new Dimension(1000, 200));

        StringBuffer stratergiesNameString = new StringBuffer();
        for (int i = 0; i < strategies.size(); i++) {
            stratergiesNameString.append(strategies.get(i).getStrategyName() + ", ");
        }
        String[] getMapsUserUsed = tournamentResult.keySet().toArray(new String[tournamentResult.keySet().size()]);
        String[] columns = {"",""};
        String rowData[][] =new String[6][6] ;

        rowData[0][0] = "                     M: ";
        rowData[0][1] = Arrays.toString(getMapsUserUsed);
        rowData[1][0] = "                     P: ";
        rowData[1][1] = "[ "+stratergiesNameString.toString()+"]";
        rowData[2][0] = "                     G: ";
        rowData[2][1] = Integer.toString(G);
        rowData[3][0] = "                     D: ";
        rowData[3][1] = Integer.toString(D);
        rowData[4][0] = "";
        rowData[4][1] = "";
        rowData[5][0] = "";
        rowData[5][1] = "";

        ListModel lm = new AbstractListModel() {
            public int getSize() { return getMapsUserUsed.length; }
            public Object getElementAt(int index) {
                return getMapsUserUsed[index];
            }
        };

        table2 = new JTable(rowData, getMapsUserUsed);
        table2.setEnabled(false);
        table2.getTableHeader().setBackground(Color.orange);
        rowHeader = new JList(lm);
        rowHeader.setFixedCellWidth(150);
        rowHeader.setFixedCellHeight(table2.getRowHeight() + table2.getRowMargin());
        rowHeader.setCellRenderer(new JTableRowNameDominationView(table2));
        scroll = new JScrollPane( table2 );
        scroll.setRowHeaderView(rowHeader);
//
        table1 = new JTable(rowData,columns);
//        table2 = new JTable(rowData, getMapsUserUsed);
//
//        frameTournamnetModeResultView.setSize(700, 500);

        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JScrollPane scrollPane1 = new JScrollPane(table1);
        JScrollPane scrollPane2 = new JScrollPane(table2);
        Container c = frameTournamnetModeResultView.getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
        c.add(table1.getTableHeader());
        c.add(table1);
//      frameTournamnetModeResultView.add(scrollPane1, BorderLayout.CENTER);
//        frameTournamnetModeResultView.validate();
        frameTournamnetModeResultView.add(scrollPane2, BorderLayout.SOUTH);
        frameTournamnetModeResultView.setSize(700, 2000);
        frameTournamnetModeResultView.setVisible(true);
        frameTournamnetModeResultView.validate();

//        ListModel lm = new AbstractListModel() {
//            public int getSize() { return getMapsUserUsed.length; }
//            public Object getElementAt(int index) {
//                return getMapsUserUsed[index];
//            }
//        };
//
//        table2 = new JTable(rowData, getMapsUserUsed);
//        table2.setEnabled(false);
//        table2.getTableHeader().setBackground(Color.orange);
//        rowHeader = new JList(lm);
//        rowHeader.setFixedCellWidth(150);
//        rowHeader.setFixedCellHeight(table.getRowHeight() + table.getRowMargin());
//        rowHeader.setCellRenderer(new JTableRowNameDominationView(table));
//        scroll = new JScrollPane( table );
//        scroll.setRowHeaderView(rowHeader);




//        table2 = new JTable(rowData, getMapsUserUsed);
//        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Container c = frameTournamnetModeResultView.getContentPane();
//        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
//        c.add(table1.getTableHeader());
//        c.add(table1);
//        c.add(table2.getTableHeader());
//        c.add(table2);
//        frameTournamnetModeResultView.pack();
//        frameTournamnetModeResultView.setVisible(true);
//
//        ListModel lm1 = new AbstractListModel() {
//            public int getSize() { return 5; }
//            public Object getElementAt(int index) {
//                return data[index];
//            }
//        };
//        table1 =new JTable(data,a);
//        table1.setEnabled(false);
//        table1.getTableHeader().setBackground(Color.pink);
//        rowHeader = new JList(lm1);
//        rowHeader.setFixedCellWidth(150);
//        rowHeader.setFixedCellHeight(table1.getRowHeight() + table1.getRowMargin());
//        rowHeader.setCellRenderer(new JTableRowNameDominationView(table1));
//        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Container c = frameTournamnetModeResultView.getContentPane();
//        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
//        c.add(table1.getTableHeader());
//        c.add(table1);
//        frameTournamnetModeResultView.pack();
//        frameTournamnetModeResultView.setVisible(true);
//
////
//        String[] getMapsUserUsed = tournamentResult.keySet().toArray(new String[tournamentResult.keySet().size()]);
//        String dataFirst[] ={Integer.toString(M), Integer.toString(G), Integer.toString(D)} ;
//        String[] columns = new String[] {};
//
//        //actual data for the table in a 2d array
//
//        String headers[][] =new String[3][4] ;
//        // Putting the data in a table
//        ListModel lm = new AbstractListModel() {
//            public int getSize() { return getMapsUserUsed.length; }
//            public Object getElementAt(int index) {
//                return getMapsUserUsed[index];
//            }
////            public int getSize() { return headers.length; }
////            public Object getElementAt(int index) {
////                return headers[index];
////            }
//        };

//        table1 = new JTable(dataFirst, getMapsUserUsed);
//        table2 =new JTable(headers, getMapsUserUsed);
//        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        Container c = frame.getContentPane();
//        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
//        c.add(table1.getTableHeader());
//        c.add(table1);
//        c.add(table2.getTableHeader());
//        c.add(table2);
//        frame.pack();
//        frame.setVisible(true);


//        table = new JTable(toTableModel(tournamentResult));
//        table.setEnabled(false);
//        table.getTableHeader().setBackground(Color.pink);
//        rowHeader = new JList(lm);
//        rowHeader.setFixedCellWidth(150);
//        rowHeader.setFixedCellHeight(table.getRowHeight() + table.getRowMargin());
//        rowHeader.setCellRenderer(new JTableRowNameDominationView(table));
//
//        //*****
//        frameTournamnetModeResultView = new JFrame("Congratulations");
//        panelTournamnetModeResultView = new JPanel();
//        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        label = new JLabel("<html>M: [Map1, Map2]<br><html>" +"P:[Aggressive, Cheater]<br>"
//                +"G:"+G+"<br>"+"D:"+D);
//        label.setBounds(10, 10, 220, 40);
//        panelTournamnetModeResultView.add(label);
//        frameTournamnetModeResultView.add(panelTournamnetModeResultView);
//        frameTournamnetModeResultView.setVisible(true);
////        scroll = new JScrollPane( label );
//        scroll = new JScrollPane( table );
//
////        scroll.setViewportView(label);
//        scroll.setRowHeaderView(rowHeader);
//
//        frameTournamnetModeResultView.getContentPane().add(scroll, BorderLayout.CENTER);
//        frameTournamnetModeResultView.setSize(1000, 200);
//        frameTournamnetModeResultView.setLocationRelativeTo(null);
//        frameWindowForWorldDominationView.setVisible(true);
//        frameWindowForWorldDominationView.add(panelWindowForWorldDominationView);
//        frameWindowForWorldDominationView.pack();
//        frameTournamnetModeResultView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

        public static TableModel toTableModel(Map<?,?> map) {
            DefaultTableModel model = new DefaultTableModel(
                new Object[] { "Key", "Value" }, 0
            );
            for (Map.Entry<?,?> entry : map.entrySet()) {
                model.addRow(new Object[] { entry.getKey(), entry.getValue() });
            }
            return model;
        }
}
