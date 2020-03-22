package ipleiria.estg.dei.ei.model.search;

public class Heuristic {

    public double compute(GraphNode state, int gLine, int gColumn) {
        return Math.abs(gLine - state.getLine()) + Math.abs(gColumn - state.getCol());
    }
}
