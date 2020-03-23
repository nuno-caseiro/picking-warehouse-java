package ipleiria.estg.dei.ei.model.search;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.utils.NodePriorityQueue;
import ipleiria.estg.dei.ei.utils.Properties;

import java.util.*;

public class AStar {

    private NodePriorityQueue frontier;
    private HashSet<Integer> explored;
    private Heuristic heuristic;

    public AStar() {
        this.frontier = new NodePriorityQueue();
        this.explored = new HashSet<>();
        this.heuristic = new Heuristic();
    }

    public List<GraphNode> search(Node initialNode, Node goalNode) {
        frontier.clear();
        explored.clear();
        frontier.add(initialNode);

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();
            if (node.getNodeNumber() == goalNode.getNodeNumber()){
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

        solution.add(node);
        while (node.hasParent()) {
            solution.addFirst(node);
            node = node.getParent();
        }

        return solution;
    }

    public double computePathCost(List<GraphNode> path) {
        double cost = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            cost += heuristic.compute(path.get(i),path.get(i+1).getLine(),path.get(i+1).getCol());
        }

        return cost;
    }

    private void addSuccessorsToFrontier(List<Node> successors, Node parent, Node goalNode) {
        for (Node node : successors) {
            double g = parent.getG() + node.getCostFromAdjacentNode();

            if (!frontier.containsState(node) && !explored.contains(node.getNodeNumber())) {
                frontier.add(new Node(parent, g + heuristic.compute(node, goalNode.getLine(), goalNode.getColumn()), g, node.getLine(), node.getColumn(), node.getNodeNumber()));
            }
        }
    }
}
