package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.EnvironmentListener;
import ipleiria.estg.dei.ei.model.search.Location;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelSimulation extends JLayeredPane implements EnvironmentListener {

    public static final int PANEL_HEIGHT = 900;
    public static final int PANEL_WIDTH = 400;
    public static final int NODE_SIZE = 18;
    public static final int NODE_PADDING = 4;
    private WarehouseLayout warehouseLayout;
    private Simulate simulate;

    public PanelSimulation() {
        this.setLayout(new OverlayLayout(this));
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
    }

    @Override
    public void createEnvironment() {
        this.warehouseLayout = new WarehouseLayout(NODE_SIZE, NODE_PADDING);
        this.warehouseLayout.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        this.setLayer(this.warehouseLayout,-1);
        this.add(this.warehouseLayout,BorderLayout.CENTER);
        this.warehouseLayout.validate();
        this.warehouseLayout.repaint();
        this.validate();
    }

    @Override
    public void createSimulation() {
        this.simulate = new Simulate(NODE_SIZE, NODE_PADDING);
        this.simulate.setSize(PANEL_WIDTH,PANEL_HEIGHT);
        this.setLayer(this.simulate,1);
        this.add(this.simulate,BorderLayout.CENTER);
        this.simulate.validate();
        this.simulate.repaint();
        this.validate();
    }

    @Override
    public void createSimulationPicks() {
        this.simulate.initializePicks();
        this.simulate.validate();
        this.simulate.repaint();
    }

    @Override
    public void updateEnvironment(List<Location> agents) {
        this.simulate.updateAgentLocations(agents);
        this.simulate.validate();
        this.simulate.repaint();
    }
}
