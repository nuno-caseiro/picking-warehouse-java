package ipleiria.estg.dei.ei.utils;

import ipleiria.estg.dei.ei.model.search.GraphNode;
import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.model.search.State;

import java.util.HashSet;
import java.util.PriorityQueue;

public class NodePriorityQueue extends PriorityQueue<GraphNode> {
    private HashSet<String> contains;

    public NodePriorityQueue() {
        this.contains = new HashSet<>();
    }

    @Override
    public boolean add(GraphNode node) {
        contains.add(node.toString());
        return super.add(node);
    }

    @Override
    public void clear() {
        contains.clear();
        super.clear();
    }

    @Override
    public GraphNode poll() {
        GraphNode node = super.poll();
        if (node != null) {
            contains.remove(node.toString());
        }

        return node;
    }

    public boolean containsState(GraphNode state) {
        return contains.contains(state.toString());
    }
}
