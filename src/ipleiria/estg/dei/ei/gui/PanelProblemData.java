package ipleiria.estg.dei.ei.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PanelProblemData extends JPanel {
    private JTable problemPanel;
    private DefaultTableModel model;
    public PanelProblemData() {
        setLayout(new BorderLayout());
        JLabel jLabel = new JLabel("Problem data:");
        jLabel.setBorder(new EmptyBorder(10,10,10,0));//top,left,bottom,right
        add(jLabel,BorderLayout.NORTH);

        model = new DefaultTableModel();
        problemPanel= new JTable(model);

        model.addColumn("1");
        model.addColumn("2");
        model.addColumn("3");
        model.addColumn("4");

        JScrollPane scrollPane = new JScrollPane(problemPanel);
        problemPanel.setFillsViewportHeight(true);
        problemPanel.setTableHeader(null);
        this.add(scrollPane, BorderLayout.CENTER);

    }

    public void addRow(String one, String two,String three,String four){
        model.addRow(new Object[]{one, two, three,four});
    }

    public DefaultTableModel getModel() {
        return model;
    }
}
