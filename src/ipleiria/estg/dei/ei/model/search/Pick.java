package ipleiria.estg.dei.ei.model.search;

import java.util.List;

public class Pick {

    private State pick;
    private List<State> cellsToPick;

    public Pick(State pick, List<State> cellsToPick) {
        this.pick = pick;
        this.cellsToPick = cellsToPick;
    }

    public State getPick() {
        return pick;
    }

    public void setPick(State pick) {
        this.pick = pick;
    }

    public List<State> getCellsToPick() {
        return cellsToPick;
    }

    public void setCellsToPick(List<State> cellsToPick) {
        this.cellsToPick = cellsToPick;
    }
}
