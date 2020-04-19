package ipleiria.estg.dei.ei.gui;


import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.Location;
import ipleiria.estg.dei.ei.model.search.Node;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Simulate extends JLayeredPane {

    private List<Node> agents;
    private List<Node> originalPicks;
    private List<Location> picks;
    private int nodeSize;
    private int nodePadding;
    private int cellSize;

    public Simulate(int nodeSize, int nodePadding) {
        this.nodeSize = nodeSize;
        this.nodePadding = nodePadding;
        this.cellSize = nodeSize + nodePadding;
        this.originalPicks = Environment.getInstance().getPickNodes();
        this.picks = new ArrayList<>();
        initializePicks();
    }

    public void initializePicks() {
        this.picks.clear();

        for (Node pick : this.originalPicks) {
            this.picks.add(new Location(pick.getLine(), pick.getColumn(), pick.getLocation()));
        }

        this.agents = Environment.getInstance().getAgentNodes();
    }

    public void updateAgentLocations(List<Location> agents) {
//        for (Node pick : this.originalPicks) {
//            for (Node agent : this.agents) {
//                if (pick.equals(agent)) {
//                    this.picks.remove(pick);
//                }
//            }
//        }
//
//        this.agents = agents;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics f = g.getFontMetrics();

        Node location;
        for (int i = 0; i < this.agents.size(); i++) {
            location = this.agents.get(i);

            g2d.setColor(Color.red);
            g2d.fillOval(location.getColumn() * this.cellSize, location.getLine() * this.cellSize, this.nodeSize, this.nodeSize);

            g2d.setColor(Color.black);
            g2d.drawOval(location.getColumn() * this.cellSize, location.getLine() * this.cellSize, this.nodeSize, this.nodeSize);

            String str = "A" + (i + 1);
            g2d.drawString(str, (location.getColumn() * this.cellSize) + ((this.nodeSize / 2) - (f.stringWidth(str) / 2)), (location.getLine() * this.cellSize) + ((this.nodeSize / 2) + (f.getHeight() / 2)));

        }

        for (Location l : this.picks) {
            g2d.setColor(Color.green);
            g2d.fillRect(((l.getColumn() + (l.getColumnOffset())) * this.cellSize) - (this.nodePadding / 2), (l.getLine() * this.cellSize) - (this.nodePadding / 2), this.cellSize, this.cellSize);

            g2d.setColor(Color.black);
            g2d.drawRect(((l.getColumn() + (l.getColumnOffset())) * this.cellSize) - (this.nodePadding / 2), (l.getLine() * this.cellSize) - (this.nodePadding / 2), this.cellSize, this.cellSize);
        }
    }
}
