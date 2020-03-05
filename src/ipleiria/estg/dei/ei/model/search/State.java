package ipleiria.estg.dei.ei.model.search;


import java.util.Objects;

public class State {
    private int line;
    private int column;
    private Action action; // action that generated this state

    public State(int line, int column) {
        this(line, column, null);
    }

    public State(int line, int column, Action action) {
        this.line = line;
        this.column = column;
        this.action = action;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return line + "-" + column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return line == state.line &&
                column == state.column;
    }
}
