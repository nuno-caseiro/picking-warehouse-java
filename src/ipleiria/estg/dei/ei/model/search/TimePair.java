package ipleiria.estg.dei.ei.model.search;

public class TimePair {

    private double node1Time;
    private double node2Time;

    public TimePair(double node1Time, double node2Time) {
        this.node1Time = node1Time;
        this.node2Time = node2Time;
    }

    public double getNode1Time() {
        return node1Time;
    }

    public double getNode2Time() {
        return node2Time;
    }
}
