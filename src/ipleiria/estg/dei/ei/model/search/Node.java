package ipleiria.estg.dei.ei.model.search;


public class Node implements Comparable<Node> {

    private State state; // actual line and column
    private Node parent; // node that gave origin to the actual node
    private double f; // g+h
    // private double g; // cost of the path from the start node
    // private double h; // cost of the cheapest path from the actual node to the goal

    public Node(State state, Node parent) {
        this.state = state;
        this.parent = parent;
    }

    public Node(State state, Node parent, double f) {
        this.state = state;
        this.parent = parent;
        this.f = f;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public double getF() {
        return f;
    }

    public void setF(double f) {
        this.f = f;
    }

    @Override
    public int compareTo(Node o) {
        return (this.f < o.f) ? -1 : (f == o.f) ? 0 : 1;
    }
}
