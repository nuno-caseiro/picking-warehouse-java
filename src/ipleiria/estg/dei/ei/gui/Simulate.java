package ipleiria.estg.dei.ei.gui;


import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.Location;
import ipleiria.estg.dei.ei.model.search.Node;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Simulate extends JLayeredPane {

    private List<Location> agents;
    private List<Node> originalPicks;
    private List<Location> picks;
    private int nodeSize;
    private int nodePadding;
    private int cellSize;
    private int count;
    private HashMap<Integer,List<Location>> statesOfIterations;

    public Simulate(int nodeSize, int nodePadding) {
        this.nodeSize = nodeSize;
        this.nodePadding = nodePadding;
        this.cellSize = nodeSize + nodePadding;
        this.originalPicks = Environment.getInstance().getPickNodes();
        this.picks = new ArrayList<>();
        this.statesOfIterations= new HashMap<>();
        initializePicks();
    }

    public void initializePicks() {
        this.picks.clear();
        this.statesOfIterations.clear();
        this.agents = new LinkedList<>();

        for (Node pick : this.originalPicks) {
            this.picks.add(new Location(pick.getLine(), pick.getColumn(), pick.getLocation()));
        }

        for (Node agent : Environment.getInstance().getAgentNodes()) {
            this.agents.add(new Location(agent.getLine(), agent.getColumn(), agent.getLocation()));
        }
    }

    public void updateAgentLocations(List<Location> agents, int iteration) {
        if(!statesOfIterations.containsKey(iteration)){
            List<Location> picksAux= List.copyOf(this.picks);
            this.statesOfIterations.put(iteration, picksAux);
        }

        if(statesOfIterations.containsKey(iteration)){
            this.picks.clear();
            this.picks.addAll(statesOfIterations.get(iteration));
        }

        for (Location location : agents) {
            if (location.getColumnOffset() == 2) {
                this.picks.removeIf(n -> n.getLine() == location.getLine() && n.getColumn() == location.getColumn());
            } else {
                this.picks.removeIf(n -> n.getLine() == location.getLine() && n.getColumn() == location.getColumn() && n.getColumnOffset() == location.getColumnOffset());
            }
        }

        this.agents = agents;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics f = g.getFontMetrics();

        Location location;
        for (int i = 0; i < this.agents.size(); i++) {
            location = this.agents.get(i);

            if (location.getColumnOffset() != 0) {
                g2d.setColor(Color.yellow);
            } else {
                g2d.setColor(Color.red);
            }

            g2d.fillOval(location.getColumn() * this.cellSize, location.getLine() * this.cellSize, this.nodeSize, this.nodeSize);

            g2d.setColor(Color.black);
            g2d.drawOval(location.getColumn() * this.cellSize, location.getLine() * this.cellSize, this.nodeSize, this.nodeSize);

            String str = String.valueOf(i + 1);
            g2d.drawString(str, (location.getColumn() * this.cellSize) + ((this.nodeSize / 2) - (f.stringWidth(str) / 2)), (location.getLine() * this.cellSize) + ((this.nodeSize / 2) + (f.getHeight() / 2)) - 2);

        }

        for (Location l : this.picks) {
            g2d.setColor(Color.green);
            g2d.fillRect(((l.getColumn() + (l.getColumnOffset())) * this.cellSize) - (this.nodePadding / 2), (l.getLine() * this.cellSize) - (this.nodePadding / 2), this.cellSize, this.cellSize);

            g2d.setColor(Color.black);
            g2d.drawRect(((l.getColumn() + (l.getColumnOffset())) * this.cellSize) - (this.nodePadding / 2), (l.getLine() * this.cellSize) - (this.nodePadding / 2), this.cellSize, this.cellSize);
        }
    }
}
