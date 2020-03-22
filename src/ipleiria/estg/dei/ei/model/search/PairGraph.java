package ipleiria.estg.dei.ei.model.search;

import java.util.List;

public class Edge {

    private GraphNode node1;
    private GraphNode node2;
    private double value;
    private List<GraphNode> path;


    public Edge(GraphNode node1, GraphNode node2, double value) {
        this.node1 = node1;
        this.node2 = node2;
        this.value = value;
    }

    public Edge(GraphNode node1, GraphNode node2) {
        this.node1 = node1;
        this.node2 = node2;
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
}
