package ipleiria.estg.dei.ei.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelTextArea extends JPanel {
    public JTextArea textArea;

    public PanelTextArea(String title, int rows, int columns) {
        textArea = new JTextArea(rows,columns);
        setLayout(new BorderLayout());
        JLabel jLabel = new JLabel(title);
        jLabel.setBorder(new EmptyBorder(0,10,10,0));//top,left,bottom,right
        add(jLabel,BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        add(scrollPane,BorderLayout.CENTER);
    }
}
