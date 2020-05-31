package ipleiria.estg.dei.ei.model.search;


import ipleiria.estg.dei.ei.utils.Properties;
import ipleiria.estg.dei.ei.utils.PropertiesNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Note: this class has a natural ordering that is inconsistent with equals.
public class Node implements Comparable<Node> {

    private Node parent;
    private double f; // g+h
    private double g; // cost of the path from the start node
    private double costFromAdjacentNode; // distance from the adjacent node to this one
    private int line;
    private int column;
    private int nodeNumber;
    private String type;
    private double time;
    private int location; // -1 if pick is on the left of column, 1 if pick is on the right of column or 2 if there are picks both on the left and right
    private int weight;
    private int capacity;
    private List<Integer> edges;

    public Node(int nodeNumber, int line, int column, String type) {
        this.nodeNumber = nodeNumber;
        this.line = line;
        this.column = column;
        this.type = type;
        this.edges = new ArrayList<>();
    }

    public Node(int line, int column, int nodeNumber) {
        this.line = line;
        this.column = column;
        this.nodeNumber = nodeNumber;

    }

    public Node(int line, int column, int location, int weight, int capacity) {
        this.line = line;
        this.column = column;
        this.location = location;
        this.weight = weight;
        this.capacity = capacity;
    }

    public Node(double costFromAdjacentNode, int line, int column, int nodeNumber) {
        this.costFromAdjacentNode = costFromAdjacentNode;
        this.line = line;
        this.column = column;
        this.nodeNumber = nodeNumber;
    }

    public Node(Node node) {
        this.costFromAdjacentNode = node.costFromAdjacentNode;
        this.nodeNumber = node.nodeNumber;
        this.line = node.line;
        this.column = node.column;
        this.g = node.g;
        this.time = 0;
        this.edges = node.edges;
        this.location = node.location;
        this.weight = node.weight;
        this.capacity = node.capacity;
    }

    public Node(Node parent, double f, double g, int line, int column, int nodeNumber) {
        this.parent = parent;
        this.f = f;
        this.g = g;
        this.line = line;
        this.column = column;
        this.nodeNumber = nodeNumber;
    }

    public Node(int line, int column, int nodeNumber, double time, int location) {
        this.line = line;
        this.column = column;
        this.nodeNumber = nodeNumber;
        this.time = time;
        this.location = location;
    }

    public void addEdge(int edge) {
        if (!this.edges.contains(edge)) {
            this.edges.add(edge);
        }
    }

    public boolean belongsToEdge(int edge) {
        return this.edges.contains(edge);
    }

    public void setEdges(List<Integer> edges) {
        this.edges = edges;
    }

    public Node getParent() {
        return parent;
    }

    public double getF() {
        return f;
    }

    public double getG() {
        return g;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getCostFromAdjacentNode() {
        return costFromAdjacentNode;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public int getNodeNumber() {
        return nodeNumber;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public String getType() {
        return type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = this.g + time;
    }

    public void setCostFromAdjacentNode(double costFromAdjacentNode) {
        this.costFromAdjacentNode = costFromAdjacentNode;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void addCost(double cost) {
        this.costFromAdjacentNode += cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return nodeNumber == node.nodeNumber;
    }

    @Override
    public int compareTo(Node o) {
        return (this.f < o.f) ? -1 : (f == o.f) ? 0 : 1;
    }

    @Override
    public String toString() {
        return nodeNumber + "/" + weight + "-" + capacity + "/" + location;
    }
}
