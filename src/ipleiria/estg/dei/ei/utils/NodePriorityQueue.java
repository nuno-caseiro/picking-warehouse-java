package ipleiria.estg.dei.ei.utils;

import ipleiria.estg.dei.ei.model.search.Node;

import java.util.HashSet;
import java.util.PriorityQueue;

public class NodePriorityQueue extends PriorityQueue<Node> {
    private HashSet<Integer> contains;

    public NodePriorityQueue() {
        this.contains = new HashSet<>();
    }

    @Override
    public boolean add(Node node) {
        contains.add(node.getNodeNumber());
        return super.add(node);
    }

    @Override
    public void clear() {
        contains.clear();
        super.clear();
    }

    @Override
    public Node poll() {
        Node node = super.poll();
        if (node != null) {
            contains.remove(node.getNodeNumber());
        }

        return node;
    }

    public boolean containsNode(Node node) {
        return contains.contains(node.getNodeNumber());
    }
}
