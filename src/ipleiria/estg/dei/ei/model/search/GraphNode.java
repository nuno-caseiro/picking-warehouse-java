package ipleiria.estg.dei.ei.model.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphNode implements Comparable<GraphNode> {
    private int numberNode;
    private GraphNode parent; // node that gave origin to the actual node
    private int line;
    private int col;
    private double f; // g+h
    private double g; // cost of the path from the start node
    // private double h; // cost of the cheapest path from the actual node to the goal
    private List<GraphNode> neighbors;
    private double distanceToParent;


    public GraphNode(int numberNode ,GraphNode parent, double f, double g, int line, int col, List<GraphNode> neighbors) {
        this.parent = parent;
        this.f = f;
        this.g = g;
        this.numberNode= numberNode;
        this.neighbors= neighbors;
        this.line = line;
        this.col = col;
    }

    public GraphNode(int numberNode, GraphNode parent, int line, int col) {
       this.numberNode= numberNode;
       this.line= line;
       this.col = col;
       this.parent = parent;
       this.neighbors = new LinkedList<GraphNode>();
    }

    public GraphNode(GraphNode graphNode){
        this.numberNode=graphNode.numberNode;
        this.parent=graphNode.parent;
        this.line=graphNode.line;
        this.col=graphNode.col;
        this.f=graphNode.f;
        this.g=graphNode.g;
        this.neighbors=graphNode.neighbors;
    }





    public int getCol() {
        return col;
    }

    public int getLine() {
        return line;
    }

    public int getNumberNode() {
        return numberNode;
    }

    public void setNumberNode(int numberNode) {
        this.numberNode = numberNode;
    }

    public GraphNode getParent() {
        return parent;
    }

    public void setParent(GraphNode parent) {
        this.parent = parent;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public int compareTo(GraphNode o) {
        return (this.f < o.f) ? -1 : (f == o.f) ? 0 : 1;
    }

    public void addNeighbor(GraphNode neighbor)
    {
        this.neighbors.add(new GraphNode(neighbor));
    }

    public List<GraphNode> getNeighbors() {
        return neighbors;
    }

    public void setCol(int col) {
        this.col = col;
    }


}
