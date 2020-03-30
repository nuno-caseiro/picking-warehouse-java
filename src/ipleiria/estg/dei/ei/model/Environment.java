package ipleiria.estg.dei.ei.model;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.search.*;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import ipleiria.estg.dei.ei.utils.Properties;

public class Environment {

    private static Environment INSTANCE = new Environment();
    private Individual bestInRun;
    private HashMap<String, Pair> pairsMap;
    private ArrayList<EnvironmentListener> listeners;
    private HashMap<Integer, List<Node>> originalGraph;
    private int graphSize;
    private HashMap<Integer, List<Node>> picksGraph;
    private HashMap<Integer, Node> nodes;
    private List<Node> decisionNodes;
    private List<Integer> picks;
    private List<Integer> agents;
    private int offloadArea;
    private List<Pair> pairs;
//    private HashMap<String, Edge> edges;
    private List<Edge> edges;
    private int maxLine;
    private int maxColumn;

    private Environment() {
        this.listeners = new ArrayList<>();
        this.originalGraph = new HashMap<>();
        this.graphSize = 0;
        this.picksGraph = new HashMap<>();
        this.agents = new ArrayList<>();
        this.picks = new ArrayList<>();
        this.pairs = new ArrayList<>();
        this.nodes = new HashMap<>();
//        this.edges = new HashMap<>();
        this.edges = new LinkedList<>();
        this.decisionNodes = new LinkedList<>();
        this.maxLine = 0;
        this.maxColumn = 0;
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public void readInitialStateFromFile(File file) throws IOException {
        createGraphFromFile(file);
    }

    private void createGraphFromFile(File file) throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = "";
        while ((row = csvReader.readLine()) != null && !row.contains("Sucessors")) {
            String[] data = row.split(";");
            if (data[0].contains("Nodes")) {
                continue;
            }

            Node node = new Node(Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[0]), data[1]);
            this.nodes.put(Integer.parseInt(data[0]), node);

            if (node.getType().equals("D") ) {
                decisionNodes.add(node);
            }

            if (node.getLine() > this.maxLine) {
                this.maxLine = node.getLine();
            }

            if (node.getColumn() > this.maxColumn) {
                this.maxColumn = node.getColumn();
            }
        }

        while ((row = csvReader.readLine()) != null && !row.contains("Edges")) {
            String[] data = row.split(";");
            ArrayList<Node> successors = new ArrayList<>();
            this.originalGraph.put(Integer.parseInt(data[0]), successors);
            this.graphSize++;

            for (int i = 1; i < data.length; i += 2) {
                Node node = nodes.get(Integer.parseInt(data[i]));
                successors.add(new Node(Integer.parseInt(data[i + 1]), node.getLine(), node.getColumn(), node.getNodeNumber()));
            }
        }

        while ((row = csvReader.readLine()) != null && !row.contains("Agents")) {
            String[] data = row.split(";");
            Node node1 = nodes.get(Integer.parseInt(data[0]));
            Node node2 = nodes.get(Integer.parseInt(data[1]));
//            edges.put(node1.getNodeNumber() + "-" + node2.getNodeNumber(), new Edge(node1, node2, Integer.parseInt(data[2]), Integer.parseInt(data[3])));
            edges.add(new Edge(node1, node2, Integer.parseInt(data[2]), Integer.parseInt(data[3])));
        }

        while ((row = csvReader.readLine()) != null & !row.contains("OffLoad")) {
            String[] data = row.split(";");
            agents.add(Integer.parseInt(data[1]));
        }

        row = csvReader.readLine();
        String[] data = row.split(";");

        offloadArea = Integer.parseInt(data[1]);
        csvReader.close();

        fireCreateEnvironment();

//        for (int i = 1; i < this.graphSize + 1; i++) {
//            System.out.println(i + "->" + this.originalGraph.get(i));
//        }
//        System.out.println("----------------------------------------------------");
    }

    public void loadPicksFromFile(File file) throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = "";
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            this.picks.add(Integer.parseInt(data[1]));
        }
        csvReader.close();

        createPicksGraph();

        setPairs();
    }

    private void createPicksGraph() {

        for (int i = 1; i <= this.graphSize; i++) {
            List<Node> successors = new LinkedList<>();
            this.picksGraph.put(i, successors);

            for (Node node : this.originalGraph.get(i)) {
                successors.add(new Node(node));
            }
        }


        for (int i = 1; i <= this.graphSize ; i++) {
            if (this.nodes.get(i).getType().equals("D")) {
                continue;
            }

            if (this.picks.contains(i)) {
                continue;
            }

            if (i == this.offloadArea) {
                continue;
            }

            List<Node> successors = this.picksGraph.get(i);
            for (Node node : successors) {
                List<Node> nodeSuccessors = this.picksGraph.get(node.getNodeNumber());
                double cost = node.getCostFromAdjacentNode();
                int finalI = i;
                nodeSuccessors.removeIf(n -> n.getNodeNumber() == finalI);

                for (Node n : successors) {
                    if (n.getNodeNumber() != node.getNodeNumber()) {
                        Node nodeToAdd = new Node(n);
                        nodeToAdd.addCost(cost);
                        nodeSuccessors.add(nodeToAdd);
                    }
                }
            }

            this.picksGraph.remove(i);
        }

        fireCreateSimulation();
    }

    public void setPairs() {

        for (int agent : this.agents) {
            pairs.add(new Pair(agent, offloadArea));
        }

        for (int pick : this.picks) {
            for (int agent : this.agents) {
                pairs.add(new Pair(agent, pick));
            }
            pairs.add(new Pair(pick, offloadArea));
        }

        for (int i = 0; i < picks.size() - 1; i++) {
            for (int j = i + 1; j < picks.size(); j++) {
                pairs.add(new Pair(picks.get(i), picks.get(j)));
            }
        }
    }

    public void executeSolution() {



        fireUpdateEnvironment();
    }

    public List<Integer> getPicks() {
        return picks;
    }

    public List<Location> getPickNodes() {
        List<Location> picks = new LinkedList<>();
        for (int i : this.picks) {
            Node node = this.nodes.get(i);
            picks.add(new Location(node.getLine(), node.getColumn()));
        }

        return picks;
    }

    public List<Location> getAgentNodes() {
        List<Location> agents = new LinkedList<>();
        for (int i : this.agents) {
            Node node = this.nodes.get(i);
            agents.add(new Location(node.getLine(), node.getColumn()));
        }

        return agents;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public Node getNode(int i) {
        return nodes.get(i);
    }

    public List<Node> getAdjacentNodes(int nodeNumber) {
        return picksGraph.get(nodeNumber);
    }

    public int getNumberOfAgents() {
        return agents.size();
    }

    public int getNumberOfPicks() {
        return picks.size();
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public int getMaxColumn() {
        return maxColumn;
    }

    public List<Node> getDecisionNodes() {
        return decisionNodes;
    }

    public void setBestInRun(Individual bestInRun) {
        this.bestInRun = bestInRun;
    }

    public void setPairsMap() {
        pairsMap = new HashMap<>();

        StringBuilder sb;
        StringBuilder sb1;
        for (Pair pair : pairs) {
            sb = new StringBuilder();
            sb.append(pair.getNode1());
            sb.append("-");
            sb.append(pair.getNode2());
            String key1 = sb.toString();

            sb1 = new StringBuilder();
            sb1.append(pair.getNode2());
            sb1.append("-");
            sb1.append(pair.getNode1());
            String key2 = sb1.toString();

            pairsMap.put(key1, pair);
            pairsMap.put(key2, new Pair(pair));
        }
    }

    public HashMap<String, Pair> getPairsMap() {
        return pairsMap;
    }

    public List<Integer> getAgents() {
        return agents;
    }

    public int getOffloadArea() {
        return offloadArea;
    }

    public Node getOffloadAreaNode() {
        return this.nodes.get(this.offloadArea);
    }

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

//    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
//        listeners.remove(l);
//    }
//
    public void fireUpdateEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.updateEnvironment();
        }
    }

    public void fireCreateEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.createEnvironment();
        }
    }

    public void fireCreateSimulation() {
        for (EnvironmentListener listener : listeners) {
            listener.createSimulation();
        }
    }
}
