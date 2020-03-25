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
    //    private int[][] matrix;
//    private int[][] originalMatrix;
//    private List<Action> actions;
//    private List<State> picks;
//    private List<State> shelfPicks;
//    private List<Pair> pairs;
//    private List<State> agents;
//    private List<State> originalAgents;
//    private State offloadArea;
    private Individual bestInRun;
    private HashMap<String, Pair> pairsMap;
    //    private HashMap<String, PairGraph> pairsGraphMap;
    private ArrayList<EnvironmentListener> listeners;
    //    private List<GraphNode> agentsGraph;
//    private List<GraphNode> pickGraph;
//    private GraphNode offLoadGraph;
//    private List<PairGraph> pairGraph;
//    private HashMap<Integer,GraphNode> vertices;
    private HashMap<Integer, List<Node>> adjacencyList;
    private HashMap<Integer, Node> nodes;
    private int graphSize;
    private HashMap<String, Integer> nodeLookup;
    private List<Integer> agents;
    private int offloadArea;
    private List<Integer> picks;
    private List<Pair> pairs;
    private int[][] matrix;
    private HashMap<String, Edge> edges;


//    int numberNode = 1;

    private Environment() {
//        this.actions = new LinkedList<>();
//        this.actions.add(new Action(1, -1, 0)); // move up
//        this.actions.add(new Action(1, 1, 0)); // move down
//        this.actions.add(new Action(1, 0, -1)); // move left
//        this.actions.add(new Action(1, 0, 1)); // move right
//        this.agents = new LinkedList<>();
//        this.originalAgents = new LinkedList<>();
        this.listeners = new ArrayList<>();
//        this.picks= new LinkedList<>();
//        this.shelfPicks= new LinkedList<>();
//        this.agentsGraph= new LinkedList<>();
//        this.pickGraph= new LinkedList<>();
//        this.pairGraph= new LinkedList<>();
//        this.vertices = new HashMap<>();
        this.adjacencyList = new HashMap<>();
        this.agents = new ArrayList<>();
        this.picks = new ArrayList<>();
        this.pairs = new ArrayList<>();
        this.nodeLookup = new HashMap<>();
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public void readInitialStateFromFile(File file) throws IOException {

        // createMatrixFromFile(file);
        // createGraph();
        createGraphFromFile(file);


//        for (int i = 0; i < graphSize; i++) {
//            System.out.println(nodes.get(i));
//        }
//
//        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
//        for (int i = 0; i < graphSize; i++) {
//            System.out.println(adjacencyList.get(i));
//        }

//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[0].length; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }

        //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

//        int[][] copyMatrix = new int[lines][columns];
//        for (int i = 0; i < lines; i++) {
//            System.arraycopy(this.matrix[i], 0, copyMatrix[i], 0, columns);
//        }
//        this.originalMatrix = copyMatrix;
    }

    private void createGraphFromFile(File file) throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = "";
        while ((row = csvReader.readLine()) != null && !row.contains("Sucessors")) {
            String[] data = row.split(";");
            if (data[0].contains("Nodes")) {
                continue;
            }
            this.nodeLookup.put(Integer.parseInt(data[2]) + "-" + Integer.parseInt(data[3]), Integer.parseInt(data[0]));
            this.nodes.put(Integer.parseInt(data[0]), new Node(Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[0]), data[1]));

        }

        while ((row = csvReader.readLine()) != null && !row.contains("Edges")) {
            String[] data = row.split(";");
            ArrayList<Node> successors = new ArrayList<>();
            this.adjacencyList.put(Integer.parseInt(data[0]), successors);

            for (int i = 1; i < data.length; i += 2) {
                Node node = nodes.get(Integer.parseInt(data[i]));
                successors.add(new Node(Integer.parseInt(data[i + 1]), node.getLine(), node.getColumn(), node.getNodeNumber()));
            }
        }

        while ((row = csvReader.readLine()) != null && !row.contains("Agents")) {
            String[] data = row.split(";");
            Node node1 = nodes.get(Integer.parseInt(data[0]));
            Node node2 = nodes.get(Integer.parseInt(data[1]));
            edges.put(node1.getNodeNumber() + "-" + node2.getNodeNumber(), new Edge(node1, node2, Integer.parseInt(data[2]), Integer.parseInt(data[3])));
        }

        while ((row = csvReader.readLine()) != null & !row.contains("OffLoad")) {
            String[] data = row.split(";");
            agents.add(Integer.parseInt(data[1]));
        }

        row = csvReader.readLine();
        String[] data = row.split(";");

        offloadArea = Integer.parseInt(data[1]);
        csvReader.close();

    }

    public void loadPicksFromFile(File file) throws IOException {

        BufferedReader csvReader = new BufferedReader(new FileReader(file));
        String row = "";
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            this.picks.add(Integer.parseInt(data[1]));
        }
        csvReader.close();

        setPairs();

    }

    private void createMatrixFromFile(File file) throws FileNotFoundException {
        java.util.Scanner scanner = new java.util.Scanner(file);


        int lines = scanner.nextInt();
        scanner.nextLine();
        int columns = scanner.nextInt();
        scanner.nextLine();

        int[][] matrix = new int[lines][columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }

        // --------------------------------------------------------------------------

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 1 && i - 1 >= 0 && matrix[i - 1][j] == 0) {
                    if (j - 1 >= 0) {
                        matrix[i - 1][j - 1] = Properties.node;
                    }
                    if (j + 1 < columns) {
                        matrix[i - 1][j + 1] = Properties.node;
                    }
                }
                if (matrix[i][j] == 1 && i + 1 < lines && matrix[i + 1][j] == 0) {
                    if (j - 1 >= 0) {
                        matrix[i + 1][j - 1] = Properties.node;
                    }
                    if (j + 1 < columns) {
                        matrix[i + 1][j + 1] = Properties.node;
                    }
                }
            }
        }

        // ---------------------------------------------------------------------------

        int nrPicks = scanner.nextInt();
        scanner.nextLine();
        int nrAgents = scanner.nextInt();
        scanner.nextLine();

        int line;
        int column;
        for (int i = 0; i < nrPicks; i++) {
            line = scanner.nextInt();
            column = scanner.nextInt();
            matrix[line][column] = Properties.pick;
//            picks.add(new State(line,col - 1));
//            shelfPicks.add(new State(line,col));
            scanner.nextLine();
        }

        for (int i = 0; i < nrAgents; i++) {
            line = scanner.nextInt();
            column = scanner.nextInt();
            matrix[line][column] = Properties.agent;
//            agents.add(agent);
//            originalAgents.add(new State(line,col));
            scanner.nextLine();
        }
        line = scanner.nextInt();
        column = scanner.nextInt();
        matrix[line][column] = Properties.offloadArea;

        this.matrix = matrix;
    }


    private void createGraph() {
        int lines = matrix.length;
        int columns = matrix[0].length;

        int nodeNumber = 0;
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] >= 2) {
                    this.nodeLookup.put(i + "-" + j, nodeNumber);
                    this.nodes.put(nodeNumber, new Node(i, j, nodeNumber));
                    nodeNumber++;
                }
            }
        }
        this.graphSize = nodeNumber;

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] >= 2) {
                    ArrayList<Node> successors = new ArrayList<>();
                    nodeNumber = getNodeNumber(i, j);
                    this.adjacencyList.put(nodeNumber, successors);

                    switch (matrix[i][j]) {
                        case Properties.agent:
                            this.agents.add(nodeNumber);
                            break;
                        case Properties.offloadArea:
                            this.offloadArea = nodeNumber;
                            break;
                        case Properties.pick:
                            this.picks.add(nodeNumber);
                    }

                    // UP
                    for (int k = i - 1; k >= 0; k--) {
                        if (matrix[k][j] == Properties.obstacle) {
                            break;
                        }

                        if (matrix[k][j] >= 2) {
                            successors.add(new Node(Math.abs(i - k), k, j, getNodeNumber(k, j)));
                            break;
                        }
                    }

                    // DOWN
                    for (int k = i + 1; k < lines; k++) {
                        if (matrix[k][j] == Properties.obstacle) {
                            break;
                        }

                        if (matrix[k][j] >= 2) {
                            successors.add(new Node(Math.abs(i - k), k, j, getNodeNumber(k, j)));
                            break;
                        }
                    }

                    //LEFT
                    for (int k = j - 1; k >= 0; k--) {
                        if (matrix[i][k] == Properties.obstacle) {
                            break;
                        }

                        if (matrix[i][k] >= 2) {
                            successors.add(new Node(Math.abs(j - k), i, k, getNodeNumber(i, k)));
                            break;
                        }
                    }

                    //RIGHT
                    for (int k = j + 1; k < columns; k++) {
                        if (matrix[i][k] == Properties.obstacle) {
                            break;
                        }

                        if (matrix[i][k] >= 2) {
                            successors.add(new Node(Math.abs(j - k), i, k, getNodeNumber(i, k)));
                            break;
                        }
                    }
                }
            }
        }
    }

    private int getNodeNumber(int l, int c) {
        return this.nodeLookup.get(l + "-" + c);
    }

    public void setPairs() {
//        for (State shelfPick : shelfPicks) {
//            matrix[shelfPick.getLine()][shelfPick.getColumn()] = 4;
//        }

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

    public List<Integer> getPicks() {
        return picks;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public Node getNode(int i) {
        return nodes.get(i);
    }

    public List<Node> getAdjacentNodes(int nodeNumber) {
        return adjacencyList.get(nodeNumber);
    }

    //    public List<Action> getActions() {
//        return actions;
//    }
//
//    public void setActions(List<Action> actions) {
//        this.actions = actions;
//    }
//
//    public List<State> getPicks() {
//        return picks;
//    }
//

    //
    public int getNumberOfAgents() {
        return agents.size();
    }

    public int getNumberOfPicks() {
        return picks.size();
    }
//
//    public List<Pair> getPairs() {
//        return pairs;
//    }
//
//    public Individual getBestInRun() {
//        return bestInRun;
//    }

    public void setBestInRun(Individual bestInRun) {
        this.bestInRun = bestInRun;
    }

//    public List<State> getAgents() {
//        return agents;
//    }

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

    //

    public List<Integer> getAgents() {
        return agents;
    }

    public int getOffloadArea() {
        return offloadArea;
    }

    //    public void executeSolution() throws InterruptedException {
//        for (int i = 0; i < this.matrix.length; i++) {
//            System.arraycopy(this.originalMatrix[i], 0, this.matrix[i], 0, this.matrix[0].length);
//        }
//
//        this.agents.clear();
//        for (State s : this.originalAgents) {
//            agents.add(new State(s.getLine(), s.getColumn()));
//        }
//
//        int[] genome = bestInRun.getGenome();
//        List<List<State>> agentsPicks = separateGenomeByAgents(genome);
//        List<List<Action>> agentsActions = new ArrayList<>();
//        List<Action> agentActions;
//        State state1;
//        State state2;
//
//        for (List<State> agentPicks : agentsPicks) {
//            agentActions = new ArrayList<>();
//            for (int i = 0; i < agentPicks.size() - 1; i++) {
//                state1 = agentPicks.get(i);
//                state2 = agentPicks.get(i + 1);
//
//                agentActions.addAll(pairsMap.get(getKey(state1, state2)).getActions());
//            }
//            agentsActions.add(agentActions);
//        }
//
//        int numIterations = 0;
//        for (List<Action> l : agentsActions) {
//            if (l.size() > numIterations) {
//                numIterations = l.size();
//            }
//        }
//
//        fireUpdatedEnvironment();
//        int agent;
//        for (int i = 0; i < numIterations; i++) {
//            agent = 0;
//            for (List<Action> l : agentsActions) {
//                if (i < l.size()) {
//                    executeAction(l.get(i), agent);
//                }
//                agent++;
//            }
//            Thread.sleep(500);
//            fireUpdatedEnvironment();
//        }
//
//
//    }
//
//    private void executeAction(Action action, int agent) {
//        State a = agents.get(agent);
//        int line = a.getLine();
//        int column = a.getColumn();
//
////        matrix[line][column] = 0;
//
//        if (matrix[line][column + 1] == Properties.pick) {
//            matrix[line][column + 1] = Properties.obstacle;
//        }
//
//        int newLine = line + action.getVerticalMovement();
//        int newColumn = column + action.getHorizontalMovement();
//
//        matrix[newLine][newColumn] = Properties.agent;
//        a.setLine(newLine);
//        a.setColumn(newColumn);
//
//        if (!stateHasAgent(line, column)) {
//            matrix[line][column] = 0;
//        }
//    }
//
//    private boolean stateHasAgent(int line, int column) {
//        for (State agent : this.agents) {
//            if (agent.getLine() == line && agent.getColumn() == column) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    private String getKey(State state1, State state2) {
//        return state1.getLine() + "-" + state1.getColumn() + "/" + state2.getLine() + "-" + state2.getColumn();
//    }
//
//    private List<List<State>> separateGenomeByAgents(int[] genome) {
//        List<List<State>> agents = new ArrayList<>();
//        List<State> agentPicks = new ArrayList<>();
//        int agent = 0;
//
//        agentPicks.add(this.agents.get(agent));
//        for (int value : genome) {
//            if (value < 0) {
//                agentPicks.add(offloadArea);
//                agents.add(agentPicks);
//                agentPicks = new LinkedList<>();
//                agentPicks.add(this.agents.get(++agent));
//                continue;
//            }
//            agentPicks.add(picks.get(value - 1));
//        }
//        agentPicks.add(offloadArea);
//        agents.add(agentPicks);
//
//        return agents;
//    }

    public Color getCellColor(int line, int column) {

        switch (matrix[line][column]) {
            case Properties.obstacle:
                return Properties.COLOROBSTACLE;
            case Properties.agent:
                return Properties.COLORAGENT;
            case Properties.offloadArea:
                return Properties.COLOROFFLOADAREA;
            case Properties.pick:
                return Properties.COLORPICK;
            default:
                return Properties.COLOREMPTY;
        }
    }

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }
//
//    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
//        listeners.remove(l);
//    }
//
//    public void fireUpdatedEnvironment() {
//        for (EnvironmentListener listener : listeners) {
//            listener.environmentUpdated();
//        }
//    }
//
//    public List<GraphNode> getPickGraph() {
//        return pickGraph;
//    }
//
//    public List<PairGraph> getPairGraph() {
//        return pairGraph;
//    }
//
//    public HashMap<Integer, GraphNode> getVertices() {
//        return vertices;
//    }
//
//    public List<GraphNode> getAgentsGraph() {
//        return agentsGraph;
//    }
//
//    public HashMap<String, PairGraph> getPairsGraphMap() {
//        return pairsGraphMap;
//    }
}
