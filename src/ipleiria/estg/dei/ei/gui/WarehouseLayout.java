package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.Edge;
import ipleiria.estg.dei.ei.model.search.Node;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WarehouseLayout extends JLayeredPane {

    private List<Node> decisionNodes;
    private List<Edge> edges;
    private Node offload;
    private int nodeSize;
    private int nodePadding;
    private int cellSize;
    private int maxLine;
    private int maxColumn;

    public WarehouseLayout(int nodeSize, int nodePadding) {
        this.decisionNodes = Environment.getInstance().getDecisionNodes();
        this.edges = Environment.getInstance().getEdges();
        this.offload = Environment.getInstance().getOffloadAreaNode();
        this.maxLine = Environment.getInstance().getMaxLine();
        this.maxColumn = Environment.getInstance().getMaxColumn();

        this.nodeSize = nodeSize;
        this.nodePadding = nodePadding;
        this.cellSize = nodeSize + nodePadding;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        FontMetrics f = g.getFontMetrics();
        g2d.setColor(Color.black);

        for (Node n : this.decisionNodes) {
            g2d.drawOval(n.getColumn() * this.cellSize, n.getLine() * this.cellSize, this.nodeSize, this.nodeSize);

            String number = String.valueOf(n.getNodeNumber());
            g2d.drawString(number, (n.getColumn() * this.cellSize) + ((this.nodeSize / 2) - (f.stringWidth(number) / 2)), (n.getLine() * this.cellSize) + ((this.nodeSize / 2) + (f.getHeight() / 2)));
        }

        g2d.fillOval(offload.getColumn() * this.cellSize, offload.getLine() * this.cellSize, this.nodeSize, this.nodeSize);

        Node n1;
        Node n2;
        Node aux;
        for (Edge edge : this.edges) {
            n1 = edge.getNode1();
            n2 = edge.getNode2();

            if (n1.getColumn() > n2.getColumn() || n1.getLine() > n2.getLine()) {
                aux = n2;
                n2 = n1;
                n1 = aux;
            }

            if (n1.getLine() == n2.getLine()) {
                g2d.drawLine((n1.getColumn() * this.cellSize) + this.nodeSize, (n1.getLine() * this.cellSize) + (this.nodeSize / 2), (n2.getColumn() * this.cellSize), (n2.getLine() * this.cellSize) + (this.nodeSize / 2));

            } else {
                g2d.drawLine((n1.getColumn() * this.cellSize) + (this.nodeSize / 2), (n1.getLine() * this.cellSize) + this.nodeSize, (n2.getColumn() * this.cellSize) + (this.nodeSize / 2), (n2.getLine() * this.cellSize));

                drawShelf(n1, edge, g2d);
            }
        }
    }

    private void drawShelf(Node n, Edge edge, Graphics2D g2d) {

//        if ((n.getColumn() * this.cellSize) < (this.maxColumn * this.cellSize) && (n.getLine() * this.cellSize) < (this.maxLine * this.cellSize)) {
//            for (int i = 1; i < edge.getDistanceOfNodes(); i++) {
//                g2d.setColor(Color.gray);
//                g2d.fillRect((n.getColumn() * this.cellSize) + (this.nodeSize + (this.nodePadding / 2)) , (n.getLine() * this.cellSize) + ((this.cellSize * i) - (this.nodePadding / 2)), this.cellSize, this.cellSize);
//
//                g2d.setColor(Color.black);
//                g2d.drawRect((n.getColumn() * this.cellSize) + (this.nodeSize + (this.nodePadding / 2)) , (n.getLine() * this.cellSize) + ((this.cellSize * i) - (this.nodePadding / 2)), this.cellSize, this.cellSize);
//            }
//        }

        for (int i = 1; i < edge.getDistanceOfNodes(); i++) {
            g2d.setColor(Color.gray);
            g2d.fillRect((n.getColumn() * this.cellSize) + (this.nodeSize + (this.nodePadding / 2)), (n.getLine() * this.cellSize) + ((this.cellSize * i) - (this.nodePadding / 2)), this.cellSize, this.cellSize);

            g2d.setColor(Color.black);
            g2d.drawRect((n.getColumn() * this.cellSize) + (this.nodeSize + (this.nodePadding / 2)), (n.getLine() * this.cellSize) + ((this.cellSize * i) - (this.nodePadding / 2)), this.cellSize, this.cellSize);

            g2d.setColor(Color.gray);
            g2d.fillRect((n.getColumn() * this.cellSize) - (this.nodePadding/2) - (this.nodeSize + (this.nodePadding )), (n.getLine() * this.cellSize) + ((this.cellSize * i) - (this.nodePadding / 2)), this.cellSize, this.cellSize);

            g2d.setColor(Color.black);
            g2d.drawRect((n.getColumn() * this.cellSize) - (this.nodePadding/2) - (this.nodeSize + (this.nodePadding )), (n.getLine() * this.cellSize) + ((this.cellSize * i) - (this.nodePadding / 2)), this.cellSize, this.cellSize);
        }
    }
}
