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

    public List<GraphNode> search(GraphNode initialNode, GraphNode goalNode) {
        frontier.clear();
        explored.clear();
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            GraphNode node = frontier.poll();
            if (node.getLine()==goalNode.getLine() && node.getCol()==goalNode.getCol()){
                return computeSolution(node);
            }

            explored.add(node.toString());
            List<GraphNode> successors = node.getNeighbors();
            addSuccessorsToFrontier(successors, node, goalNode);
            //TODO
        }
        return null;
    }

    private List<GraphNode> computeSolution(GraphNode node) {
        LinkedList<GraphNode> solution = new LinkedList<>();

        solution.add(node);
        while (node.hasParent()) {
            solution.addFirst(node.getParent());
            node = node.getParent();
        }

        return solution;
    }

    public double computePathCost(List<GraphNode> path) {
        double cost = 0;
        for (int i = 0; i < path.size()-1; i++) {
            cost += heuristic.compute(path.get(i),path.get(i+1).getLine(),path.get(i+1).getCol());
        }

        return cost;
    }

    private List<GraphNode> computeSuccessors(GraphNode state) {
        //List<Action> actions = Environment.getInstance().getActions();
        List<GraphNode> successors = new LinkedList<>();

        for (GraphNode graphNode : state.getNeighbors()) {
                successors.add(new GraphNode(graphNode.getNumberNode(),state,0,0,graphNode.getLine(),graphNode.getCol(),graphNode.getNeighbors()));
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

    private void addSuccessorsToFrontier(List<GraphNode> successors, GraphNode parent, GraphNode goalState) {
        for (GraphNode state : successors) {
            double g = parent.getG() + heuristic.compute(state,parent.getLine(),parent.getCol());

            if (!frontier.containsState(state) && !explored.contains(state.toString())) {
                frontier.add(new GraphNode(state.getNumberNode(), parent, g + heuristic.compute(state, goalState.getLine(), goalState.getCol()), g,state.getLine(),state.getCol(),state.getNeighbors()));
            }
        }
    }
}
