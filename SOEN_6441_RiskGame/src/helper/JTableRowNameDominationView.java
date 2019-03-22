package helper;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

// TODO: Auto-generated Javadoc
/**
 * The Class JTableRowNameDominationView.
 *
 * @version 1.0 11/09/98
 */

public class JTableRowNameDominationView extends JLabel implements ListCellRenderer {

    /**
     * Instantiates a new j table row name domination view.
     *
     * @param table the table
     */
    public JTableRowNameDominationView(JTable table) {
        JTableHeader header = table.getTableHeader();
        setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
    }

    /* (non-Javadoc)
     * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
     */
    public Component getListCellRendererComponent( JList list,
                                                   Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText((value == null) ? "" : value.toString());
        return this;
    }
}


