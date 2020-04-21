package ipleiria.estg.dei.ei.model.search;

import java.util.Objects;

public class Location {

    private int line;
    private int column;
    private int columnOffset;

    public Location(int line, int column, int columnOffset) {
        this.line = line;
        this.column = column;
        this.columnOffset = columnOffset;
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

    public int getColumnOffset() {
        return columnOffset;
    }

    public void setColumnOffset(int columnOffset) {
        this.columnOffset = columnOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return line == location.line &&
                column == location.column;
    }

    @Override
    public String toString() {
        return line + "," + column;
    }
}
