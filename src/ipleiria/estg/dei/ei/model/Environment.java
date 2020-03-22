package ipleiria.estg.dei.ei.model;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.search.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import ipleiria.estg.dei.ei.utils.Properties;

public class Environment {

    private static Environment INSTANCE = new Environment();
    private int[][] matrix;
    private int[][] originalMatrix;
    private List<Action> actions;
    private List<State> picks;
    private List<State> shelfPicks;
    private List<Pair> pairs;
    private List<State> agents;
    private List<State> originalAgents;
    private State offloadArea;
    private Individual bestInRun;
    private HashMap<String, Pair> pairsMap;
    private HashMap<String, PairGraph> pairsGraphMap;
    private ArrayList<EnvironmentListener> listeners;
    private List<GraphNode> agentsGraph;
    private List<GraphNode> pickGraph;
    private GraphNode offLoadGraph ;
    private List<PairGraph> pairGraph;
    private HashMap<Integer,GraphNode> vertices;


    int numberNode = 1;

    private Environment() {
        this.actions = new LinkedList<>();
        this.actions.add(new Action(1, -1, 0)); // move up
        this.actions.add(new Action(1, 1, 0)); // move down
        this.actions.add(new Action(1, 0, -1)); // move left
        this.actions.add(new Action(1, 0, 1)); // move right
        this.agents = new LinkedList<>();
        this.originalAgents = new LinkedList<>();
        this.listeners = new ArrayList<>();
        this.picks= new LinkedList<>();
        this.shelfPicks= new LinkedList<>();
        this.agentsGraph= new LinkedList<>();
        this.pickGraph= new LinkedList<>();
        this.pairGraph= new LinkedList<>();
        this.vertices = new HashMap<>();

    }

    public static Environment getInstance() {
        return INSTANCE;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public void readInitialStateFromFile(File file) throws IOException {
        java.util.Scanner scanner = new java.util.Scanner(file);
        picks.clear();
        agents.clear();

        int lines = scanner.nextInt();
        scanner.nextLine();
        int columns = scanner.nextInt();
        scanner.nextLine();

        int[][] matrix = new int[lines][columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
                /*if (matrix[i][j] == Properties.agent) {
                    agents.add(new State(i,j));
                }*/
                if (matrix[i][j] == Properties.offloadArea) {
                    offloadArea = new State(i,j);
                }
            }
            scanner.nextLine();
        }
        this.matrix = matrix;

        int nrPicks = scanner.nextInt();
        scanner.nextLine();
        int nrAgents = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nrPicks; i++) {
            int line = scanner.nextInt();
            int col = scanner.nextInt();
            picks.add(new State(line,col - 1));
            shelfPicks.add(new State(line,col));
            scanner.nextLine();
        }

        for (int i = 0; i < nrAgents; i++) {
            int line = scanner.nextInt();
            int col = scanner.nextInt();
            State agent = new State(line,col);
            agents.add(agent);
            originalAgents.add(new State(line,col));
        }

        setAgentsCellsColour(agents);
        setPicks();

        int[][] copyMatrix = new int[lines][columns];
        for (int i = 0; i < lines; i++) {
            System.arraycopy(this.matrix[i], 0, copyMatrix[i], 0, columns);
        }
        this.originalMatrix = copyMatrix;


    }

    public void createGraph(){

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if(matrix[i][j] == 5 || matrix[i][j] == 3 || matrix[i][j]== 4 || matrix[i][j]== 2){

                    GraphNode graphNode = new GraphNode(numberNode,null,i,j);
                    if(matrix[i][j]== 2){
                        agentsGraph.add(graphNode);
                    }
                    if(matrix[i][j]== 4){
                        pickGraph.add(graphNode);
                    }
                    if(matrix[i][j]== 3){
                        offLoadGraph = graphNode;
                    }
                    int number = numberNode;
                    numberNode++;
                    vertices.put(number,graphNode);
                }
            }
        }

        for (int i = 1; i < vertices.size(); i++) {
            findAdjacentNodes(vertices.get(i));
        }

    }

    public void findAdjacentNodes(GraphNode graphNode){


        int line = graphNode.getLine();
        int col = graphNode.getCol();

        //left
        while(--col > -1 ){
            if(matrix[line][col]==1){
                break;
            }
            if(matrix[line][col]==5 || matrix[line][col] == 3 || matrix[line][col]== 4 || matrix[line][col]== 2){
                GraphNode graphNode1 = findNode(line,col);
                graphNode.addNeighbor(graphNode1);
                break;
            }
        }

        line = graphNode.getLine();
        col = graphNode.getCol();

        //right
        while(++col < matrix.length){
            if(matrix[line][col]==1){
                break;
            }
            if(matrix[line][col]==5 || matrix[line][col] == 3 || matrix[line][col]== 4 || matrix[line][col]== 2){
                GraphNode graphNode1 = findNode(line,col);
                graphNode.addNeighbor(graphNode1);
                break;
            }
        }

        line = graphNode.getLine();
        col = graphNode.getCol();
        //up
        while(--line> -1){
            if(matrix[line][col]==1){
                break;
            }
            if(matrix[line][col]==5|| matrix[line][col] == 3 || matrix[line][col]== 4 || matrix[line][col]== 2){
                GraphNode graphNode1 = findNode(line,col);
                graphNode.addNeighbor(graphNode1);
                break;
            }
        }

        line = graphNode.getLine();
        col = graphNode.getCol();

        //down
        while(++line<matrix.length){
            if(matrix[line][col]==1){
                break;
            }

            if(matrix[line][col]==5 || matrix[line][col] == 3 || matrix[line][col]== 4 || matrix[line][col]== 2){
                GraphNode graphNode1 = findNode(line,col);
                graphNode.addNeighbor(graphNode1);
                break;
            }
        }

    }

    public GraphNode findNode(int line, int col){
        for (int i = 1; i <= vertices.size(); i++) {
            GraphNode graphNode = vertices.get(i);
            if(graphNode.getLine()==line && graphNode.getCol() == col){
                return graphNode;
            }
        }
        return null;
    }

    public void setAgentsCellsColour(List<State> agents){
        for (State agent : agents) {
            matrix[agent.getLine()][agent.getColumn()] = Properties.agent;
        }
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<State> getPicks() {
        return picks;
    }

    public void setPicks() {
        for (State shelfPick : shelfPicks) {
            matrix[shelfPick.getLine()][shelfPick.getColumn()] = 4;
        }

        this.pairs = new LinkedList<>();

        for (State agent : this.agents) {
            pairs.add(new Pair(agent, offloadArea));
        }

        for (State pick : picks) {
            for (State agent : this.agents) {
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

    public void setPicksGraph(){
        for (GraphNode agent : this.agentsGraph) {
            pairGraph.add(new PairGraph(agent, offLoadGraph));
        }


        for (GraphNode pick : pickGraph) {
            for (GraphNode agent : this.agentsGraph) {
                pairGraph.add(new PairGraph(agent, pick));
            }
            pairGraph.add(new PairGraph(pick, offLoadGraph));
        }

        for (int i = 0; i < pickGraph.size() - 1; i++) {
            for (int j = i + 1; j < pickGraph.size(); j++) {
                pairGraph.add(new PairGraph(pickGraph.get(i), pickGraph.get(j)));
            }
        }
    }

    public int getNumberOfAgents() {
        return agents.size();
    }

    public int getNumberOfPicks() {
        return picks.size();
    }

    public List<Pair> getPairs() {
        return pairs;
    }

    public Individual getBestInRun() {
        return bestInRun;
    }

    public void setBestInRun(Individual bestInRun) {
        this.bestInRun = bestInRun;
    }

    public List<State> getAgents() {
        return agents;
    }

    public State getOffloadArea() {
        return offloadArea;
    }

    public HashMap<String, Pair> getPairsMap() {
        return pairsMap;
    }

    public void setPairsMap() {
        pairsMap = new HashMap<>();

        StringBuilder sb;
        StringBuilder sb1;
        for (Pair pair : pairs) {
            sb = new StringBuilder();
            sb.append(pair.getState1().getLine());
            sb.append("-");
            sb.append(pair.getState1().getColumn());
            sb.append("/");
            sb.append(pair.getState2().getLine());
            sb.append("-");
            sb.append(pair.getState2().getColumn());
            String key1 = sb.toString();

            sb1 = new StringBuilder();
            sb1.append(pair.getState2().getLine());
            sb1.append("-");
            sb1.append(pair.getState2().getColumn());
            sb1.append("/");
            sb1.append(pair.getState1().getLine());
            sb1.append("-");
            sb1.append(pair.getState1().getColumn());
            String key2 = sb1.toString();

            pairsMap.put(key1, pair);
            pairsMap.put(key2, new Pair(pair));
        }
    }

    public void setPairsGraphMap(){

        pairsGraphMap = new HashMap<>();

        StringBuilder sb;
        StringBuilder sb1;
        for (PairGraph pair : pairGraph) {
            sb = new StringBuilder();
            sb.append(pair.getNode1().getLine());
            sb.append("-");
            sb.append(pair.getNode1().getCol());
            sb.append("/");
            sb.append(pair.getNode2().getLine());
            sb.append("-");
            sb.append(pair.getNode2().getCol());
            String key1 = sb.toString();

            sb1 = new StringBuilder();
            sb1.append(pair.getNode2().getLine());
            sb1.append("-");
            sb1.append(pair.getNode2().getCol());
            sb1.append("/");
            sb1.append(pair.getNode1().getLine());
            sb1.append("-");
            sb1.append(pair.getNode1().getCol());
            String key2 = sb1.toString();

            pairsGraphMap.put(key1, pair);
            pairsGraphMap.put(key2, new PairGraph(pair));
        }

    }

    public List<State> getOriginalAgents() {
        return originalAgents;
    }

    public void executeSolution() throws InterruptedException {
        for (int i = 0; i < this.matrix.length; i++) {
            System.arraycopy(this.originalMatrix[i], 0, this.matrix[i], 0, this.matrix[0].length);
        }

        this.agents.clear();
        for (State s : this.originalAgents) {
            agents.add(new State(s.getLine(), s.getColumn()));
        }

        int[] genome = bestInRun.getGenome();
        List<List<State>> agentsPicks = separateGenomeByAgents(genome);
        List<List<Action>> agentsActions = new ArrayList<>();
        List<Action> agentActions;
        State state1;
        State state2;

        for (List<State> agentPicks : agentsPicks) {
            agentActions = new ArrayList<>();
            for (int i = 0; i < agentPicks.size() - 1; i++) {
                state1 = agentPicks.get(i);
                state2 = agentPicks.get(i + 1);

                agentActions.addAll(pairsMap.get(getKey(state1, state2)).getActions());
            }
            agentsActions.add(agentActions);
        }

        int numIterations = 0;
        for (List<Action> l : agentsActions) {
            if (l.size() > numIterations) {
                numIterations = l.size();
            }
        }

        fireUpdatedEnvironment();
        int agent;
        for (int i = 0; i < numIterations; i++) {
            agent = 0;
            for (List<Action> l : agentsActions) {
                if (i < l.size()) {
                    executeAction(l.get(i), agent);
                }
                agent++;
            }
            Thread.sleep(500);
            fireUpdatedEnvironment();
        }


    }

    private void executeAction(Action action, int agent) {
        State a = agents.get(agent);
        int line = a.getLine();
        int column = a.getColumn();

//        matrix[line][column] = 0;

        if (matrix[line][column + 1] == Properties.pick) {
            matrix[line][column + 1] = Properties.obstacle;
        }

        int newLine = line + action.getVerticalMovement();
        int newColumn = column + action.getHorizontalMovement();

        matrix[newLine][newColumn] = Properties.agent;
        a.setLine(newLine);
        a.setColumn(newColumn);

        if (!stateHasAgent(line, column)) {
            matrix[line][column] = 0;
        }
    }

    private boolean stateHasAgent(int line, int column) {
        for (State agent : this.agents) {
            if (agent.getLine() == line && agent.getColumn() == column) {
                return true;
            }
        }
        return false;
    }

    private String getKey(State state1, State state2) {
        return state1.getLine() + "-" + state1.getColumn() + "/" + state2.getLine() + "-" + state2.getColumn();
    }

    private List<List<State>> separateGenomeByAgents(int[] genome) {
        List<List<State>> agents = new ArrayList<>();
        List<State> agentPicks = new ArrayList<>();
        int agent = 0;

        agentPicks.add(this.agents.get(agent));
        for (int value : genome) {
            if (value < 0) {
                agentPicks.add(offloadArea);
                agents.add(agentPicks);
                agentPicks = new LinkedList<>();
                agentPicks.add(this.agents.get(++agent));
                continue;
            }
            agentPicks.add(picks.get(value - 1));
        }
        agentPicks.add(offloadArea);
        agents.add(agentPicks);

        return agents;
    }

    public Color getCellColor(int line, int column) {

        switch (matrix[line][column]) {
            case Properties.obstacle:
                return Properties.COLOROBSTACLE;
            case Properties.agent:
                return Properties.COLORAGENT;
            case Properties.offloadArea:
                return Properties.COLOROFFLOADAREA;
                case  Properties.pick:
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

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

    public List<GraphNode> getPickGraph() {
        return pickGraph;
    }

    public List<PairGraph> getPairGraph() {
        return pairGraph;
    }

    public HashMap<Integer, GraphNode> getVertices() {
        return vertices;
    }

    public List<GraphNode> getAgentsGraph() {
        return agentsGraph;
    }

    public HashMap<String, PairGraph> getPairsGraphMap() {
        return pairsGraphMap;
    }
}
