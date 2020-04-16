package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.search.Node;

import java.util.LinkedList;
import java.util.List;

public class AgentPath {

    private List<Node> path;
    private double value;

    public AgentPath() {
        this.path = new LinkedList<>();
        this.value = 0;
    }

    public List<Node> getPath() {
        return path;
    }

    public double getValue() {
        return value;
    }

    public void addPath(List<Node> path) {
        Node n;
        for (Node node : path) {
            n = new Node(node);
            n.setTime(this.value);
            this.path.add(n);
        }

        this.value += path.get(path.size() - 1).getG();
    }

    public void addAgentInitialPosition(Node node) {
        Node n = new Node(node);
        n.setTime(0);
        this.path.add(n);
    }
}
