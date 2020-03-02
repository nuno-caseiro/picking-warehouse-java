package ipleiria.estg.dei.ei.model;

import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.model.search.State;

import java.util.HashSet;
import java.util.PriorityQueue;

public class NodePriorityQueue extends PriorityQueue<Node> {
    private HashSet<String> contains;

    public NodePriorityQueue() {
        this.contains = new HashSet<>();
    }

    @Override
    public boolean add(Node node) {
        contains.add(node.getState().toString());
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
            contains.remove(node.toString());
        }

        return node;
    }

    public boolean containsState(State state) {
        return contains.contains(state.toString());
    }
}
