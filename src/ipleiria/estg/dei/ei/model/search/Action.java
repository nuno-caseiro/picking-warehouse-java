package ipleiria.estg.dei.ei.model.search;

public class Action {
    private int cost;
    private int verticalMovement; // +1 move down | -1 move up | 0 dont move up or down
    private int horizontalMovement; // +1 move right | -1 move left | 0 dont right or left

    public Action(int cost, int verticalMovement, int horizontalMovement) {
        this.cost = cost;
        this.verticalMovement = verticalMovement;
        this.horizontalMovement = horizontalMovement;
    }

    public int getCost() {
        return cost;
    }

    public int getVerticalMovement() {
        return verticalMovement;
    }

    public int getHorizontalMovement() {
        return horizontalMovement;
    }
}
