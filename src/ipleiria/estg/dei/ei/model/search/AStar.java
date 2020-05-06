package ipleiria.estg.dei.ei.model.search;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.utils.NodePriorityQueue;

import java.util.*;

public class AStar {

    private NodePriorityQueue frontier;
    private HashSet<Integer> explored;
    private Heuristic heuristic;
    private Environment environment;

    public AStar() {
        this.frontier = new NodePriorityQueue();
        this.explored = new HashSet<>();
        this.heuristic = new Heuristic();
        this.environment = Environment.getInstance();
    }

    public List<Node> search(Node initialNode, Node goalNode) {
        if (this.environment.pairsMapContains(initialNode, goalNode)) {
            return this.environment.getPath(initialNode, goalNode);
        }

        frontier.clear();
        explored.clear();
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            if (node.getNodeNumber() == goalNode.getNodeNumber()) {
                return computeSolution(node);
            }

            explored.add(node.getNodeNumber());
            List<Node> successors = Environment.getInstance().getAdjacentNodes(node.getNodeNumber());
            addSuccessorsToFrontier(successors, node, goalNode);
        }
        return null;
    }

    private List<Node> computeSolution(Node node) {
        List<Node> solution = new ArrayList<>();

        while (node != null) {
            solution.add(0, node);
            node = node.getParent();
        }

        if (solution.size() == 1) { // WHEN INITIAL NODE AND GOAL NODE ARE THE SAME
            return new ArrayList<>();
        }

        this.environment.addToPairsMap(solution);
        return solution;
    }

    private void addSuccessorsToFrontier(List<Node> successors, Node parent, Node goalNode) {
        for (Node node : successors) {
            double g = parent.getG() + node.getCostFromAdjacentNode();

            if (!frontier.containsNode(node) && !explored.contains(node.getNodeNumber())) {
                frontier.add(new Node(parent, g + heuristic.compute(node, goalNode.getLine(), goalNode.getColumn()), g, node.getLine(), node.getColumn(), node.getNodeNumber()));
            }
        }
    }
}
