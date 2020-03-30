package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.EnvironmentListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class PanelSimulation extends JPanel implements EnvironmentListener {

    public static final int PANEL_HEIGHT = 675;
    public static final int PANEL_WIDTH = 400;
    public static final int NODE_SIZE = 30;
    public static final int NODE_PADDING = 15;
    private JPanel environmentPanel;
    private WarehouseLayout warehouseLayout;
    private Simulate simulate;
    private JButton buttonSimulate;

    public PanelSimulation() {
        setLayout(new BorderLayout());

        this.environmentPanel = new JPanel();
        environmentPanel.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        add(environmentPanel,BorderLayout.NORTH);

        this.buttonSimulate = new JButton("Simulate");
        add(buttonSimulate,BorderLayout.SOUTH);

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createEmptyBorder(2,1,1,1)));
    }

    public JButton getButtonSimulate() {
        return buttonSimulate;
    }

    public void setJButtonSimulateEnabled(boolean enabled){
        buttonSimulate.setEnabled(enabled);
    }

    @Override
    public void createEnvironment() {
        this.warehouseLayout = new WarehouseLayout(NODE_SIZE, NODE_PADDING);
        this.warehouseLayout.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        this.warehouseLayout.setVisible(true);

        this.environmentPanel.add(this.warehouseLayout, 1, 0);
        this.warehouseLayout.repaint();
    }

    @Override
    public void createSimulation() {
        this.simulate = new Simulate(NODE_SIZE, NODE_PADDING);
        this.simulate.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        this.simulate.setVisible(true);

        this.environmentPanel.add(this.simulate, 0, 0);
        this.simulate.repaint();
    }

    @Override
    public void updateEnvironment() {

    }
}
