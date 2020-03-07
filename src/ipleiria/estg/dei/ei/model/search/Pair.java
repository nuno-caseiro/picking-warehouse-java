package ipleiria.estg.dei.ei.model.search;

public class Pair {

    private State state1;
    private State state2;
    private int value;

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
