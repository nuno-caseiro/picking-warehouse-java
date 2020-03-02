package ipleiria.estg.dei.ei.model;

import ipleiria.estg.dei.ei.model.search.Action;

import java.util.LinkedList;
import java.util.List;

public class Environment {
    private static Environment INSTANCE = new Environment();
    private int[][] matrix;
    private List<Action> actions;

    private Environment() {
        actions = new LinkedList<>();
        actions.add(new Action(1, -1, 0)); // move up
        actions.add(new Action(1, 1, 0)); // move down
        actions.add(new Action(1, 0, -1)); // move left
        actions.add(new Action(1, 0, 1)); // move right
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
