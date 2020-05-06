package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.utils.NodePathList;

import java.util.List;

public class AgentPath {

    private NodePathList path;
    private double value;

    public AgentPath() {
        this.path = new NodePathList();
        this.value = 0;
    }

    public NodePathList getPath() {
        return path;
    }

    public double getValue() {
        return value;
    }

    public void addPath(List<Node> path, int pickLocation, int weight, int capacity) {
        Node n;
        for (Node node : path) {
            n = new Node(node);
            n.setTime(this.value);
            this.path.add(n);
        }

        if (path.size() < 1) { // path.size() == 0 WHEN 2 CONSECUTIVE PICKS ARE ON THE SAME NODE EX: 40 -> 40
            this.path.get(this.path.size() - 1).setLocation(2);
        } else {
            this.path.get(this.path.size() - 1).setLocation(pickLocation);
            this.path.get(this.path.size() - 1).setWeight(weight);
            this.path.get(this.path.size() - 1).setCapacity(capacity);
            this.value += path.get(path.size() - 1).getG();
        }
    }

    public void addAgentInitialPosition(Node node) {
        Node n = new Node(node);
        n.setTime(0);
        this.path.add(n);
    }

    public void populateNodePairsMap() {
        this.path.populateNodePairsMap();
    }
}
