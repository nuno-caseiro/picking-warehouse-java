package ipleiria.estg.dei.ei.model.search;

public class Edge implements Comparable<Edge> {

    private int edgeNumber;
    private Node node1;
    private Node node2;
    private double distanceOfNodes;
    private int direction;

    public Edge(int edgeNumber, Node node1, Node node2, double distanceOfNodes, int direction) {
        this.edgeNumber = edgeNumber;
        this.node1 = node1;
        this.node2 = node2;
        this.distanceOfNodes = distanceOfNodes;
        this.direction = direction;
    }

    public int getEdgeNumber() {
        return edgeNumber;
    }

    public void setEdgeNumber(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public double getDistanceOfNodes() {
        return distanceOfNodes;
    }

    public void setDistanceOfNodes(double distanceOfNodes) {
        this.distanceOfNodes = distanceOfNodes;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public int compareTo(Edge edge) {
        return Integer.compare(this.edgeNumber, edge.edgeNumber);
    }
}
