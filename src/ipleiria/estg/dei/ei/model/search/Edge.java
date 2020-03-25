package ipleiria.estg.dei.ei.model.search;

public class Edge {

    private Node node1;
    private Node node2;
    private int distanceOfNodes;
    private int direction;

    public Edge(Node node1, Node node2, int distanceOfNodes, int direction) {
        this.node1 = node1;
        this.node2 = node2;
        this.distanceOfNodes = distanceOfNodes;
        this.direction = direction;
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

    public int getDistanceOfNodes() {
        return distanceOfNodes;
    }

    public void setDistanceOfNodes(int distanceOfNodes) {
        this.distanceOfNodes = distanceOfNodes;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}
