package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.EnvironmentListener;
import ipleiria.estg.dei.ei.model.search.Location;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelSimulation extends JLayeredPane implements EnvironmentListener {

    public static int PANEL_HEIGHT;
    public static int PANEL_WIDTH;
    public static int NODE_SIZE = 18;
    public static int NODE_PADDING = 4;
    private WarehouseLayout warehouseLayout;
    private Simulate simulate;

    public PanelSimulation(int width, int height) {
        PANEL_WIDTH = width;
        PANEL_HEIGHT = height;

        this.setLayout(new OverlayLayout(this));
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
    }

    @Override
    public void createEnvironment() {
        int maxLine = Environment.getInstance().getMaxLine();
        int maxColumn = Environment.getInstance().getMaxColumn();

        int cellSize = Math.min(PANEL_HEIGHT / (maxLine + 1), PANEL_WIDTH / (maxColumn + 2));

        NODE_PADDING = (int) (cellSize * 0.3);
        NODE_SIZE = cellSize - NODE_PADDING;

        if(warehouseLayout!=null){
            this.remove(warehouseLayout);
            this.validate();
        }
        if(this.simulate!=null){
            this.remove(this.simulate);
            this.validate();
        }

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
        if(this.simulate!=null){
            this.remove(this.simulate);
            this.validate();
        }

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
    public void updateEnvironment(List<Location> agents, int iteration) {
        this.simulate.updateAgentLocations(agents,iteration);
        this.simulate.validate();
        this.simulate.repaint();
    }
}
