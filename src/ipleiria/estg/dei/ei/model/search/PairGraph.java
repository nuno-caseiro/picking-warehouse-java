package ipleiria.estg.dei.ei.model.search;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PairGraph {

    private GraphNode node1;
    private GraphNode node2;
    private double value;
    private List<GraphNode> path;


    public PairGraph(GraphNode node1, GraphNode node2, double value) {
        this.node1 = node1;
        this.node2 = node2;
        this.value = value;
    }

    public PairGraph(GraphNode node1, GraphNode node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public PairGraph(PairGraph pair) {
        this.node1 = pair.node1;
        this.node2 = pair.node2;
        this.value = pair.value;
        this.path = reversePath(new LinkedList<>(pair.path));
    }

    public GraphNode getNode1() {
        return node1;
    }

    public void setNode1(GraphNode node1) {
        this.node1 = node1;
    }

    public GraphNode getNode2() {
        return node2;
    }

    public void setNode2(GraphNode node2) {
        this.node2 = node2;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<GraphNode> getPath() {
        return path;
    }

    public void setPath(List<GraphNode> path) {
        this.path = path;
    }


    private List<GraphNode> reversePath(List<GraphNode> actions) {
        Collections.reverse(actions);


        return actions;
    }

    @Override
    public String toString() {
        return "(" + node1.getLine() + "," + node1.getCol() + ") -> (" + node2.getLine() + "," + node2.getCol() + ") = " + value + "\n";
    }

}
