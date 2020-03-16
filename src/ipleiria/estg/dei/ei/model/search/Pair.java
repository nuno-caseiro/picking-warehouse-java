package ipleiria.estg.dei.ei.model.search;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Pair {

    private State state1;
    private State state2;
    private double value;
    private List<Action> actions;

    public Pair(State state1, State state2) {
        this.state1 = state1;
        this.state2 = state2;
    }

    // create the inverse pair
    public Pair(Pair pair) {
        this.state1 = pair.state2;
        this.state2 = pair.state1;
        this.value = pair.value;
        this.actions = reverseActions(new LinkedList<>(pair.actions));
    }

    public State getState1() {
        return state1;
    }

    public State getState2() {
        return state2;
    }

    public void setState1(State state1) {
        this.state1 = state1;
    }

    public void setState2(State state2) {
        this.state2 = state2;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    private List<Action> reverseActions(List<Action> actions) {
        Collections.reverse(actions);

        for (Action action : actions) {
            action.flipVerticalMovement();
            action.flipHorizontalMovement();
        }

        return actions;
    }

    @Override
    public String toString() {
        return "(" + state1.getLine() + "," + state1.getColumn() + ") -> (" + state2.getLine() + "," + state2.getColumn() + ") = " + value + "\n";
    }
}
