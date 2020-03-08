package ipleiria.estg.dei.ei.model.search;

import java.util.List;

public class Pair {

    private State state1;
    private State state2;
    private double value;
    private List<Action> path;

    public Pair(State state1, State state2) {
        this.state1 = state1;
        this.state2 = state2;
    }

    public State getState1() {
        return state1;
    }

    public State getState2() {
        return state2;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<Action> getPath() {
        return path;
    }

    public void setPath(List<Action> path) {
        this.path = path;
    }
}
