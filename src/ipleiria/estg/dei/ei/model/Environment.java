package ipleiria.estg.dei.ei.model;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.search.Action;
import ipleiria.estg.dei.ei.model.search.Pair;
import ipleiria.estg.dei.ei.model.search.Pick;
import ipleiria.estg.dei.ei.model.search.State;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import ipleiria.estg.dei.ei.utils.Properties;

public class Environment {

    private static Environment INSTANCE = new Environment();
    private int[][] matrix;
    private List<Action> actions;
    private List<State> picks;
    private List<Pair> pairs;
    private List<State> agents;
    private State offloadArea;
    private Individual bestInRun;
    private HashMap<String, Pair> pairsMap;

    private Environment() {
        this.actions = new LinkedList<>();
        this.actions.add(new Action(1, -1, 0)); // move up
        this.actions.add(new Action(1, 1, 0)); // move down
        this.actions.add(new Action(1, 0, -1)); // move left
        this.actions.add(new Action(1, 0, 1)); // move right
        this.agents = new LinkedList<>();
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

        int lines = scanner.nextInt();
        scanner.nextLine();
        int columns = scanner.nextInt();
        scanner.nextLine();

        int[][] matrix = new int[lines][columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
                if (matrix[i][j] == Properties.agent) {
                    agents.add(new State(i,j));
                }
                if (matrix[i][j] == Properties.offloadArea) {
                    offloadArea = new State(i,j);
                }
            }
            scanner.nextLine();
        }

        this.matrix = matrix;
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

    public void setPicks(List<State> picks) {
        this.picks = picks;
        this.pairs = new LinkedList<>();

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

            Pair reverseActionsPair = pair.clone();
            reverseActionsPair.reverseActions();

            pairsMap.put(key1, pair.clone());
            pairsMap.put(key2, reverseActionsPair);
        }
    }
}
