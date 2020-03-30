package ipleiria.estg.dei.ei.model.search;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Pair {

    private int node1;
    private int node2;
    private double value;
    private List<Node> path;

    public Pair(int node1, int node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    // create the inverse pair
    public Pair(Pair pair) {
        this.node1 = pair.node2;
        this.node2 = pair.node1;
        this.value = pair.value;

        this.path = new LinkedList<>();
        for (Node node : pair.path) {
            this.path.add(new Node(node));
        }
        Collections.reverse(this.path);
    }

    public int getNode1() {
        return node1;
    }

    public int getNode2() {
        return node2;
    }

    public double getValue() {
        return value;
    }

    public List<Node> getPath() {
        return path;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setPath(List<Node> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return node1 + " -> " + node2 + " = " + value + "\n";
    }
}
