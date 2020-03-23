package ipleiria.estg.dei.ei.model.search;

public class Heuristic {

    public double compute(Node node, int gLine, int gColumn) {
        return Math.abs(gLine - node.getLine()) + Math.abs(gColumn - node.getColumn());
    }
}
