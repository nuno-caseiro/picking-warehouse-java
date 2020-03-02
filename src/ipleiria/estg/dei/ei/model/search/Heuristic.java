package ipleiria.estg.dei.ei.model.search;

public class Heuristic {

    public double compute(State state, int gLine, int gColumn) {
        return Math.abs(gLine - state.getLine()) + Math.abs(gColumn - state.getColumn());
    }
}
