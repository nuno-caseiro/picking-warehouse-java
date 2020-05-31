package ipleiria.estg.dei.ei.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.AgentPath;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.search.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Environment {

    private static Environment INSTANCE = new Environment();
    private Individual bestInRun;
    private HashMap<String, List<Node>> pairsMap;
    private ArrayList<EnvironmentListener> listeners;
    private HashMap<Integer, List<Node>> warehouseGraph;
    private int graphSize;
    private int edgesSize;
    private HashMap<Integer, List<Node>> picksGraph;
    private HashMap<Integer, Node> nodes;
    private List<Node> decisionNodes;
    private List<Node> picks;
    private List<Node> agents;
    private int offloadArea;
    private HashMap<String, Edge> edgesMap;
    private List<Edge> edges;
    private int timeWeight;
    private int collisionsWeight;
    private Boolean pause;
    private int executionSteps= 0;
    private Thread auxThread;
    private File defaultWarehouseLayout;
    private HashMap<String, Integer> positionsPicks;
    private HashMap<String, Integer> positionsAgents;

    private Environment() {
        this.listeners = new ArrayList<>();
        this.pairsMap = new HashMap<>();
        this.defaultWarehouseLayout=null;
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public void readInitialStateFromFile(File file) {
        this.nodes = new HashMap<>();
        this.graphSize = 0;
        this.edgesSize = 0;
        this.warehouseGraph = new HashMap<>();
        this.edgesMap = new HashMap<>();
        this.edges = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.decisionNodes = new ArrayList<>();

        try {
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();

            // IMPORT NODES
            JsonArray jsonNodes = jsonObject.getAsJsonArray("nodes");

            JsonObject jsonNode;
            Node decisionNode;
            for (JsonElement elementNode : jsonNodes) {
                jsonNode = elementNode.getAsJsonObject();
                decisionNode = new Node(jsonNode.get("nodeNumber").getAsInt(), jsonNode.get("line").getAsInt(), jsonNode.get("column").getAsInt(), jsonNode.get("type").getAsString());
                this.nodes.put(jsonNode.get("nodeNumber").getAsInt(), decisionNode);
                this.decisionNodes.add(decisionNode);
                this.graphSize++;
            }

            // IMPORT SUCCESSORS
            JsonArray jsonSuccessors;
            JsonObject jsonSuccessor;
            Node node;
            for (JsonElement elementNode : jsonNodes) {
                jsonNode = elementNode.getAsJsonObject();
                List<Node> successors = new ArrayList<>();
                this.warehouseGraph.put(jsonNode.get("nodeNumber").getAsInt(), successors);

                jsonSuccessors = jsonNode.getAsJsonArray("successors");
                for (JsonElement elementSuccessor : jsonSuccessors) {
                    jsonSuccessor = elementSuccessor.getAsJsonObject();
                    node = new Node(this.nodes.get(jsonSuccessor.get("nodeNumber").getAsInt()));
                    node.setCostFromAdjacentNode(jsonSuccessor.get("distance").getAsDouble());

                    successors.add(node);
                }
            }

            // IMPORT EDGES
            JsonArray jsonEdges = jsonObject.getAsJsonArray("edges");

            JsonObject jsonEdge;
            Edge edge;
            Node node1;
            Node node2;
            for (JsonElement elementEdge : jsonEdges) {
                jsonEdge = elementEdge.getAsJsonObject();
                node1 = this.nodes.get(jsonEdge.get("node1Number").getAsInt());
                node2 = this.nodes.get(jsonEdge.get("node2Number").getAsInt());
                node1.addEdge(jsonEdge.get("edgeNumber").getAsInt());
                node2.addEdge(jsonEdge.get("edgeNumber").getAsInt());
                edge = new Edge(jsonEdge.get("edgeNumber").getAsInt(), node1, node2, jsonEdge.get("distance").getAsDouble(), jsonEdge.get("direction").getAsInt());

                this.edges.add(edge);
                this.edgesMap.put(jsonEdge.get("node1Number") +  "-" + jsonEdge.get("node2Number"), edge);
                this.edgesSize++;
            }
            Collections.sort(this.edges);

            // IMPORT AGENTS
            JsonArray jsonAgents = jsonObject.getAsJsonArray("agents");

            JsonObject jsonAgent;
            Node newNode;
            for (JsonElement elementAgent : jsonAgents) {
                jsonAgent = elementAgent.getAsJsonObject();

                newNode = addNodeToGraph(this.warehouseGraph, jsonAgent.get("edgeNumber").getAsInt(), jsonAgent.get("line").getAsInt(), jsonAgent.get("column").getAsInt());

                if (newNode != null) {
                    this.agents.add(newNode);
                }
            }

            // IMPORT OFFLOAD
            this.offloadArea = jsonObject.get("offloadArea").getAsInt();

        } catch (Exception e) {
            e.printStackTrace();
        }

        fireCreateEnvironment();
    }

    public void loadAtualLayout() throws IOException {
        String defaultLayoutPath = Files.walk(Paths.get("./src/ipleiria/estg/dei/ei/dataSets/warehouseLayout/actual"))
                .map(Path::toString)
                .filter(n -> n.endsWith(".json")).collect(Collectors.joining());
        defaultWarehouseLayout = new File(defaultLayoutPath);
        readInitialStateFromFile(defaultWarehouseLayout);
    }

    public void loadPicksFromFile(File file) {
        this.picks = new ArrayList<>();
        this.picksGraph = new HashMap<>();

        readInitialStateFromFile(defaultWarehouseLayout);

        // COPY WAREHOUSE GRAPH TO PICKS GRAPH
        List<Node> successors;
        for (int i = 1; i <= this.graphSize; i++) {
            successors = new LinkedList<>();
            this.picksGraph.put(i, successors);

            for (Node node : this.warehouseGraph.get(i)) {
                successors.add(new Node(node));
            }
        }

        try {
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(file)).getAsJsonObject();

            // IMPORT PICKS
            JsonArray jsonPicks = jsonObject.getAsJsonArray("picks");

            JsonObject jsonPick;
            Node newNode;
            for (JsonElement elementNode : jsonPicks) {
                jsonPick = elementNode.getAsJsonObject();

                newNode = addNodeToGraph(this.picksGraph, jsonPick.get("edgeNumber").getAsInt(), jsonPick.get("line").getAsInt(), jsonPick.get("column").getAsInt());

                if (newNode != null) {
                    newNode.setLocation(jsonPick.get("location").getAsInt());

                    if (jsonPick.has("weight") && jsonPick.has("capacity")) {
                        newNode.setWeight(jsonPick.get("weight").getAsInt());
                        newNode.setCapacity(jsonPick.get("capacity").getAsInt());
                    }

                    this.picks.add(newNode);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.pairsMap = new HashMap<>();

        fireCreateSimulation();
    }

    // RETURNS NODE ADDED TO THE GRAPH OR NULL IF UNSUCCESSFUL // TODO COULD THROW EXCEPTION INSTEAD OF RETURNING 0
    private Node addNodeToGraph(HashMap<Integer, List<Node>> graph, int edgeNumber, int line, int column) {
        Node edgeNode1 = this.edges.get(edgeNumber - 1).getNode1();
        Node edgeNode2 = this.edges.get(edgeNumber - 1).getNode2();

        if (edgeNode1.getLine() == line && edgeNode1.getColumn() == column) {
            return edgeNode1;
        }

        Node previousNode;
        Node nextNode = edgeNode1;
        int previousNodeNumber = -1;
        do {
            previousNode = nextNode;
            nextNode = null;
            for (Node n : graph.get(previousNode.getNodeNumber())) {
                if (n.belongsToEdge(edgeNumber) && n.getNodeNumber() != previousNodeNumber) {
                    nextNode = n;
                    break;
                }
            }

            if (nextNode == null) {
                return null;
            }

            if ((line - previousNode.getLine()) * (line - nextNode.getLine()) < 0 || (column - previousNode.getColumn()) * (column - nextNode.getColumn()) < 0) {
                // ADD NEW NODE TO NODES
                this.graphSize++;
                Node newNode = new Node(this.graphSize, line, column, "O");
                this.nodes.put(newNode.getNodeNumber(), newNode);

                // ADD NEW NODE'S SUCCESSORS TO GRAPH
                List<Node> successors = new ArrayList<>();
                graph.put(newNode.getNodeNumber(), successors);
                Node n1 = new Node(previousNode);
                n1.setCostFromAdjacentNode(Math.abs(previousNode.getLine() - line) + Math.abs(previousNode.getColumn() - column));
                Node n2 = new Node(nextNode);
                n2.setCostFromAdjacentNode(Math.abs(nextNode.getLine() - line) + Math.abs(nextNode.getColumn() - column));
                successors.add(n1);
                successors.add(n2);

                // ALTER PREVIOUS AND NEXT NODE SUCCESSORS
                Node finalNextNode = nextNode;
                graph.get(previousNode.getNodeNumber()).removeIf(n -> n.getNodeNumber() == finalNextNode.getNodeNumber());
                Node finalPreviousNode = previousNode;
                graph.get(nextNode.getNodeNumber()).removeIf(n -> n.getNodeNumber() == finalPreviousNode.getNodeNumber());

                n1 = new Node(newNode);
                n1.setCostFromAdjacentNode(Math.abs(previousNode.getLine() - line) + Math.abs(previousNode.getColumn() - column));
                n1.addEdge(edgeNumber);
                n2 = new Node(newNode);
                n2.setCostFromAdjacentNode(Math.abs(nextNode.getLine() - line) + Math.abs(nextNode.getColumn() - column));
                n2.addEdge(edgeNumber);

                graph.get(previousNode.getNodeNumber()).add(n1);
                graph.get(nextNode.getNodeNumber()).add(n2);

                // CREATE NEW SUB EDGES
                this.edgesMap.put(previousNode.getNodeNumber() + "-" + newNode.getNodeNumber(), new Edge(++this.edgesSize, this.nodes.get(previousNode.getNodeNumber()), newNode, Math.abs(previousNode.getLine() - line) + Math.abs(previousNode.getColumn() - column), this.edges.get(edgeNumber - 1).getDirection()));
                this.edgesMap.put(nextNode.getNodeNumber() + "-" + newNode.getNodeNumber(), new Edge(++this.edgesSize, this.nodes.get(nextNode.getNodeNumber()), newNode, Math.abs(nextNode.getLine() - line) + Math.abs(nextNode.getColumn() - column), this.edges.get(edgeNumber - 1).getDirection()));

                return newNode;
            }

            if (line == nextNode.getLine() && column == nextNode.getColumn()) {
                return nextNode;
            }

            previousNodeNumber = previousNode.getNodeNumber();

        } while (nextNode.getNodeNumber() != edgeNode2.getNodeNumber());

        return null;
    }

    // IMPORT LAYOUT
    public void generateRandomLayout() {
        this.positionsPicks = new HashMap<>();
        this.positionsAgents = new HashMap<>();
        this.nodes = new HashMap<>();
        this.graphSize = 0;
        this.edgesSize = 0;
        this.warehouseGraph = new HashMap<>();
        this.edgesMap = new HashMap<>();
        this.edges = new ArrayList<>();
        this.agents = new ArrayList<>();
        this.decisionNodes = new ArrayList<>();

        try {
            JsonObject jsonObject = JsonParser.parseReader(new FileReader(this.defaultWarehouseLayout)).getAsJsonObject();

            // IMPORT NODES
            JsonArray jsonNodes = jsonObject.getAsJsonArray("nodes");

            JsonObject jsonNode;
            Node decisionNode;
            for (JsonElement elementNode : jsonNodes) {
                jsonNode = elementNode.getAsJsonObject();
                decisionNode = new Node(jsonNode.get("nodeNumber").getAsInt(), jsonNode.get("line").getAsInt(), jsonNode.get("column").getAsInt(), jsonNode.get("type").getAsString());
                this.nodes.put(jsonNode.get("nodeNumber").getAsInt(), decisionNode);
                this.decisionNodes.add(decisionNode);
                this.graphSize++;
            }

            // IMPORT SUCCESSORS
            JsonArray jsonSuccessors;
            JsonObject jsonSuccessor;
            Node node;
            for (JsonElement elementNode : jsonNodes) {
                jsonNode = elementNode.getAsJsonObject();
                List<Node> successors = new ArrayList<>();
                this.warehouseGraph.put(jsonNode.get("nodeNumber").getAsInt(), successors);

                jsonSuccessors = jsonNode.getAsJsonArray("successors");
                for (JsonElement elementSuccessor : jsonSuccessors) {
                    jsonSuccessor = elementSuccessor.getAsJsonObject();
                    node = new Node(this.nodes.get(jsonSuccessor.get("nodeNumber").getAsInt()));
                    node.setCostFromAdjacentNode(jsonSuccessor.get("distance").getAsDouble());

                    successors.add(node);
                }
            }

            // IMPORT EDGES
            JsonArray jsonEdges = jsonObject.getAsJsonArray("edges");

            JsonObject jsonEdge;
            Edge edge;
            Node node1;
            Node node2;
            for (JsonElement elementEdge : jsonEdges) {
                jsonEdge = elementEdge.getAsJsonObject();
                node1 = this.nodes.get(jsonEdge.get("node1Number").getAsInt());
                node2 = this.nodes.get(jsonEdge.get("node2Number").getAsInt());
                node1.addEdge(jsonEdge.get("edgeNumber").getAsInt());
                node2.addEdge(jsonEdge.get("edgeNumber").getAsInt());
                edge = new Edge(jsonEdge.get("edgeNumber").getAsInt(), node1, node2, jsonEdge.get("distance").getAsDouble(), jsonEdge.get("direction").getAsInt());

                this.edges.add(edge);
                this.edgesMap.put(jsonEdge.get("node1Number") +  "-" + jsonEdge.get("node2Number"), edge);
                this.edgesSize++;
            }
            Collections.sort(this.edges);

            // IMPORT OFFLOAD
            this.offloadArea = jsonObject.get("offloadArea").getAsInt();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // GET POSSIBLE POSITIONS
        Node n1;
        Node n2;
        int line;
        int column;
        for (Edge e : this.edges) {
            n1 = e.getNode1();
            n2 = e.getNode2();
            line = n1.getLine();
            column = n1.getColumn();

            if (n1.getColumn() == n2.getColumn()) {
                for (int i = Math.min(n1.getLine(), n2.getLine()) + 1; i < Math.max(n1.getLine(), n2.getLine()); i++) {
                    this.positionsPicks.put(i + "-" + column, e.getEdgeNumber());
                    this.positionsAgents.put(i + "-" + column, e.getEdgeNumber());
                }
            } else {
                for (int i = Math.min(n1.getColumn(), n2.getColumn()) + 1; i < Math.max(n1.getColumn(), n2.getColumn()); i++) {
                    this.positionsAgents.put(line + "-" + i, e.getEdgeNumber());
                }
            }

            if (!positionsAgents.containsKey(line + "-" + column)) {
                this.positionsAgents.put(line + "-" + column, e.getEdgeNumber());
            }

            if (!positionsAgents.containsKey(n2.getLine() + "-" + n2.getColumn())) {
                this.positionsAgents.put(n2.getLine() + "-" + n2.getColumn(), e.getEdgeNumber());
            }
        }
    }

    // GENERATE RANDOM PICKS AND AGENTS
    public void generateRandomPicks(int seed, int numPicks, int numAgents, int numRuns) {
        HashSet<String> cells = new HashSet<>();
        Random randomPicks = new Random(seed);
        Random randomAgents = new Random(numRuns + seed);
        this.picks = new ArrayList<>();
        this.picksGraph = new HashMap<>();

        // COPY WAREHOUSE GRAPH TO PICKS GRAPH
        List<Node> successors;
        for (int i = 1; i <= this.graphSize; i++) {
            successors = new LinkedList<>();
            this.picksGraph.put(i, successors);

            for (Node node : this.warehouseGraph.get(i)) {
                successors.add(new Node(node));
            }
        }

//        List<Node> picksList = new ArrayList<>();
//        List<Node> agentList = new ArrayList<>();

//        List<List<Node>> result = new ArrayList<>();
//        result.add(picksList);
//        result.add(agentList);

        int picks = 0;
        int line;
        int column;
        int offset;
        int weight;
        int capacity;
        Node newNode;
        while (picks < numPicks) {
            line = randomPicks.nextInt(33);
            column = randomPicks.nextInt(21);
            offset = randomPicks.nextInt(2) == 0 ? -1 : 1;
            weight = randomPicks.nextInt(25) + 1;
            capacity = randomPicks.nextInt(18) + 3;

            if (cells.contains(line + "-" + column + "-" + offset)) {
                continue;
            }

            if (!this.positionsPicks.containsKey(line + "-" + column)) {
                continue;
            }

            cells.add(line + "-" + column + "-" + offset);
//            picksList.add(new Node(line, column, offset, weight, capacity));

            picks++;

            newNode = addNodeToGraph(this.picksGraph, this.positionsPicks.get(line + "-" + column), line, column);

            if (newNode != null) {
                newNode.setLocation(offset);
                newNode.setWeight(weight);
                newNode.setCapacity(weight * capacity);

                this.picks.add(newNode);
            }
        }

        int agents = 0;
        while (agents < numAgents) {
            line = randomAgents.nextInt(33);
            column = randomAgents.nextInt(21);

            if (!this.positionsAgents.containsKey(line + "-" + column)) {
                continue;
            }

//            agentList.add(new Node(0, line, column,"O"));

            agents++;

            newNode = addNodeToGraph(this.picksGraph, this.positionsAgents.get(line + "-" + column), line, column);

            if (newNode != null) {
                this.agents.add(newNode);
            }
        }

        this.pairsMap = new HashMap<>();
//        return result;
    }

    public void executeSolution() throws InterruptedException {
        this.pause=false;
        auxThread = new Thread();
        List<List<Location>> individualPaths = computeIndividualLocations(this.bestInRun.getIndividualPaths());


        for (int i = 0; i < individualPaths.size(); i++) {
            List<Location> l = individualPaths.get(i);
            System.out.println("Agent " + i + ": " + l.size());
        }

        fireCreateSimulationPicks();

        auxThread.start();

        synchronized (auxThread){
            Node offloadNode = this.nodes.get(this.offloadArea);
            List<Location> iterationAgentsLocations;
            for (this.executionSteps = 0; this.executionSteps < this.bestInRun.getFitness(); this.executionSteps++) {
                iterationAgentsLocations = new LinkedList<>();
                for (List<Location> l : individualPaths) {
                    if (this.executionSteps < l.size()) {
                        iterationAgentsLocations.add(l.get(this.executionSteps));
                    } else {
                        iterationAgentsLocations.add(new Location(offloadNode.getLine(), offloadNode.getColumn(), 0));
                    }
                }
                fireUpdateEnvironment(iterationAgentsLocations, this.executionSteps);
                if(this.pause){
                    auxThread.wait();
                }else{
                    Thread.sleep(300);
                }
            }
        }

    }

    public void resume(Thread a){
        if(this.pause!=null){
            this.pause=!this.pause;
            this.executionSteps = this.executionSteps - 1;
            synchronized (a){
                a.notify();
            }
        }
    }

    public void increment(Thread a){
        if(this.pause!=null) {
            if (!this.pause) {
                this.pause = true;
                this.executionSteps = this.executionSteps - 1;
                return;
            }
            synchronized (a) {
                a.notify();
            }
        }
    }

    public void decrement(Thread a){
        if(this.pause!=null) {
            if (!this.pause) {
                this.pause = true;
                this.executionSteps = this.executionSteps - 1;
                return;
            }
            synchronized (a) {
                this.executionSteps = this.executionSteps - 2;
                if (this.executionSteps < 0)
                    this.executionSteps = 0;
                a.notify();
            }
        }
    }

    private List<List<Location>> computeIndividualLocations(List<AgentPath> individualPaths) {
        List<List<Location>> solutionLocations = new ArrayList<>();
        List<Location> agentLocations = new ArrayList<>();

        List<Node> path;
        Node n1;
        Node n2;
        int action;
        int line;
        int column;
        for (AgentPath agentPath : individualPaths) {
            path = agentPath.getPath();
            for (int i = 0; i < path.size() - 1; i++) {
                n1 = path.get(i);
                n2 = path.get(i + 1);
                line = n1.getLine();
                column = n1.getColumn();


                if (column < n2.getColumn() || line < n2.getLine()) {
                    action = 1;
                } else {
                    action = -1;
                }

                if (line == n2.getLine()) {

                    column += action;
                    while (column != n2.getColumn()) {
                        agentLocations.add(new Location(line, column, 0));
                        column += action;
                    }
                    agentLocations.add(new Location(line, column, n2.getLocation()));
                } else {

                    line += action;
                    while (line != n2.getLine()) {
                        agentLocations.add(new Location(line, column, 0));
                        line += action;
                    }
                    agentLocations.add(new Location(line, column, n2.getLocation()));
                }
            }
            solutionLocations.add(agentLocations);
            agentLocations = new ArrayList<>();
        }
        return solutionLocations;
    }

    public int getTimeWeight() {
        return timeWeight;
    }

    public void setTimeWeight(int timeWeight) {
        this.timeWeight = timeWeight;
    }

    public int getCollisionsWeight() {
        return collisionsWeight;
    }

    public void setCollisionsWeight(int collisionsWeight) {
        this.collisionsWeight = collisionsWeight;
    }

    public List<Node> getPicks() {
        return picks;
    }

    public List<Node> getPickNodes() {
        return this.picks;
    }

    public List<Node> getAgentNodes() {
        return this.agents;
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

    public File getDefaultWarehouseLayout() {
        return defaultWarehouseLayout;
    }

    public void setDefaultWarehouseLayout(File defaultWarehouseLayout) {
        this.defaultWarehouseLayout = defaultWarehouseLayout;
    }

    public int getMaxLine() {
        int maxLine = 0;

        for (int i = 1; i <= this.graphSize; i++) {
            if (this.nodes.get(i).getLine() > maxLine) {
                maxLine = this.nodes.get(i).getLine();
            }
        }

        return maxLine;
    }

    public int getMaxColumn() {
        int maxColumn = 0;

        for (int i = 1; i <= this.graphSize; i++) {
            if (this.nodes.get(i).getColumn() > maxColumn) {
                maxColumn = this.nodes.get(i).getColumn();
            }
        }

        return maxColumn;
    }

    public List<Node> getDecisionNodes() {
        return decisionNodes;
    }

    public void setBestInRun(Individual bestInRun) {
        this.bestInRun = bestInRun;
    }

    public HashMap<String, List<Node>> getPairsMap() {
        return pairsMap;
    }

    public boolean pairsMapContains(Node firstNode, Node secondNode) {
        return this.pairsMap.containsKey(firstNode.getNodeNumber() + "-" + secondNode.getNodeNumber());
    }

    public void addToPairsMap(List<Node> path) {

        this.pairsMap.put(path.get(0).getNodeNumber() + "-" + path.get(path.size() - 1).getNodeNumber(), path);
        path.remove(0);
    }

    public int getEdgeDirection(int node1, int node2) {
        if (this.edgesMap.containsKey(node1 + "-" + node2)) {
            return this.edgesMap.get(node1 + "-" + node2).getDirection();
        }
        return this.edgesMap.get(node2 + "-" + node1).getDirection();
    }

    public Thread getAuxThread() {
        return auxThread;
    }

    private List<Node> createInversePath(List<Node> path) {
        List<Node> invPath = new ArrayList<>();
        path.forEach((node) -> invPath.add(new Node(node)));

        double pathSize = path.get(path.size() - 1).getG();
        for (int i = path.size() - 1; i >= 0; i--) {
            invPath.get(i).setG(pathSize - path.get(i).getG());
        }

        Collections.reverse(invPath);

        return invPath;
    }

    public List<Node> getPath(Node firstNode, Node secondNode) {
        return this.pairsMap.get(firstNode.getNodeNumber() + "-" + secondNode.getNodeNumber());
    }

    public List<Node> getAgents() {
        return this.agents;
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

    public void fireUpdateEnvironment(List<Location> agents, int iteration) {
        for (EnvironmentListener listener : listeners) {
            listener.updateEnvironment(agents, iteration);
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

    public void fireCreateSimulationPicks() {
        for (EnvironmentListener listener : listeners) {
            listener.createSimulationPicks();
        }
    }
}
