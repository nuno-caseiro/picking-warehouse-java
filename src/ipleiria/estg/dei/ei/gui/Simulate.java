package ipleiria.estg.dei.ei.gui;


import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.Location;
import ipleiria.estg.dei.ei.model.search.Node;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Simulate extends JLayeredPane {

    private List<Location> agents;
    private List<Location> picks;
    private int nodeSize;
    private int nodePadding;
    private int cellSize;

    public Simulate(int nodeSize, int nodePadding) {
        this.nodeSize = nodeSize;
        this.nodePadding = nodePadding;
        this.cellSize = nodeSize + nodePadding;
        this.agents = Environment.getInstance().getAgentNodes();
        this.picks = Environment.getInstance().getPickNodes();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics f = g.getFontMetrics();

        g2d.setColor(Color.red);
        for (Location l : this.agents) {
            g2d.fillOval(l.getColumn() * this.cellSize, l.getLine() * this.cellSize, this.nodeSize, this.nodeSize);
        }

        g2d.setColor(Color.green);
        for (Location l : this.picks) {
            g2d.fillRect(((l.getColumn() + 1) * this.cellSize) - (this.nodePadding / 2), (l.getLine() * this.cellSize) - (this.nodePadding / 2), this.cellSize, this.cellSize);
        }
    }
}
