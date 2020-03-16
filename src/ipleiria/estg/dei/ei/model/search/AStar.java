package ipleiria.estg.dei.ei.model.search;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.utils.NodePriorityQueue;
import ipleiria.estg.dei.ei.utils.Properties;

import java.util.*;

public class AStar {

    private NodePriorityQueue frontier;
    private HashSet<String> explored;
    private Heuristic heuristic;

    public AStar() {
        this.frontier = new NodePriorityQueue();
        this.explored = new HashSet<>();
        this.heuristic = new Heuristic();
    }

    public List<Action> search(Node initialNode, Node goalNode) {
        frontier.clear();
        explored.clear();
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            if (node.getState().equals(goalNode.getState())){
                return computeSolution(node);
            }

            explored.add(node.getState().toString());
            List<State> successors = computeSuccessors(node.getState());
            addSuccessorsToFrontier(successors, node, goalNode.getState());
            //TODO
        }
        return null;
    }

    private List<Action> computeSolution(Node node) {
        LinkedList<Action> solution = new LinkedList<>();

        while (node.hasParent()) {
            solution.addFirst(node.getState().getAction());
            node = node.getParent();
        }

        return solution;
    }

    public double computePathCost(List<Action> path) {
        double cost = 0;
        for (Action operator : path) {
            cost += operator.getCost();
        }
        return cost;
    }

    private List<State> computeSuccessors(State state) {
        List<Action> actions = Environment.getInstance().getActions();
        List<State> successors = new LinkedList<>();

        for (Action action : actions) {
            int nextLine = state.getLine() + action.getVerticalMovement();
            int nextColumn = state.getColumn() + action.getHorizontalMovement();

            if (canMove(nextLine, nextColumn)) {
                successors.add(new State(nextLine, nextColumn, action));
            }
        }

        return successors;
    }

    private boolean canMove(int nextLine, int nextColumn) {
        int[][] matrix = Environment.getInstance().getMatrix();
        int vLen = matrix.length;
        int hLen = matrix[0].length;

        if (nextLine < 0 || nextLine > (vLen - 1)) {
            return false;
        }

        if (nextColumn < 0 || nextColumn > (hLen - 1)) {
            return false;
        }

        return matrix[nextLine][nextColumn] != Properties.obstacle && matrix[nextLine][nextColumn] != Properties.pick;
    }

    private void addSuccessorsToFrontier(List<State> successors, Node parent, State goalState) {
        for (State state : successors) {
            double g = parent.getG() + state.getAction().getCost();

            if (!frontier.containsState(state) && !explored.contains(state.toString())) {
                frontier.add(new Node(state, parent, g + heuristic.compute(state, goalState.getLine(), goalState.getColumn()), g));
            }
        }
    }
}
