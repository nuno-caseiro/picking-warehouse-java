package ipleiria.estg.dei.ei.utils;

import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.model.search.TimePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NodePathList extends ArrayList<Node> {

    private HashMap<Double, Integer> contains;
    private HashMap<String, List<TimePair>> nodePairs;

    public NodePathList() {
        this.contains = new HashMap<>();
    }

    @Override
    public boolean add(Node node) {
        this.contains.put(node.getTime(), node.getNodeNumber());
        return super.add(node);
    }

    public boolean containsNodeAtTime(int nodeNumber, double time) {
        if (this.contains.get(time) == null) {
            return false;
        }

        return nodeNumber == this.contains.get(time);
    }

    public List<TimePair> getPair(Node n1, Node n2) {
        return this.nodePairs.get(n1.getNodeNumber() + "-" + n2.getNodeNumber());
    }

    public void populateNodePairsMap() {
        this.nodePairs = new HashMap<>();

        Node n1;
        Node n2;
        List<TimePair> times;
        for (int i = 0; i < this.size() - 1; i++) {
            n1 = this.get(i);
            n2 = this.get(i + 1);

            times = this.nodePairs.get(n1.getNodeNumber() + "-" + n2.getNodeNumber());
            if (times != null) {
                times.add(new TimePair(n1.getTime(), n2.getTime()));
            } else {
                times = new ArrayList<>();
                times.add(new TimePair(n1.getTime(), n2.getTime()));

                this.nodePairs.put(n1.getNodeNumber() + "-" + n2.getNodeNumber(), times);
            }
        }
    }
}
