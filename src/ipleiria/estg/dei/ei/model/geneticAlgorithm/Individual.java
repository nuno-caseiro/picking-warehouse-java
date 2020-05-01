package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.AStar;
import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.model.search.TimePair;
import ipleiria.estg.dei.ei.utils.NodePathList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Individual implements Comparable<Individual> {

    private int[] genome;
    private double fitness;
    private int numberOfCollisions;
    private List<AgentPath> individualPaths;
    private Environment environment;
    private AStar aStar;

    public Individual(int numPicks, int numAgents) {
        int genomeSize = numPicks + (numAgents - 1);
        this.genome = new int[genomeSize];
        this.environment = Environment.getInstance();
        this.aStar = new AStar();

        List<Integer> genes = new LinkedList<>(); // 0 -> divisions between paths for different agents | 1 ... n index of the picks (eg. Environment.getInstance().getPicks().get(n -1))
        for (int i = 0; i < numAgents - 1; i++) {
            genes.add(-1 - i);
        }

        for (int i = 0; i < numPicks; i++) {
            genes.add(i + 1);
        }

        int randomIndex;
        for (int i = 0; i < genomeSize; i++) {
            randomIndex = GeneticAlgorithm.random.nextInt(genes.size());
            this.genome[i] = genes.get(randomIndex);
            genes.remove(randomIndex);
        }
    }

    public Individual (Individual original) {
        this.genome = new int[original.genome.length];
        System.arraycopy(original.genome, 0, this.genome, 0, this.genome.length);
        this.fitness = original.fitness;
        this.environment = Environment.getInstance();
        this.aStar = new AStar();
        this.individualPaths = original.individualPaths;
        this.numberOfCollisions = original.numberOfCollisions;
    }

    public int[] getGenome() {
        return genome;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getGene(int index) {
        return genome[index];
    }

    public void setGene(int index, int newValue) {
        genome[index] = newValue;
    }

    public List<AgentPath> getIndividualPaths() {
        return individualPaths;
    }

    public int getIndexOf(int value){
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public int getNumGenes() {
        return genome.length;
    }

    public void computeFitness() {
        this.individualPaths = new ArrayList<>();

        List<Node> picks = this.environment.getPicks();
        List<Node> agents = this.environment.getAgents();
        int offloadArea = this.environment.getOffloadArea();

        AgentPath agentPath;
        int i = 0;
        for (Node agent : agents) {
            agentPath = new AgentPath();
            agentPath.addAgentInitialPosition(agent);

            if (i >= this.genome.length || this.genome[i] < 0) {
                computePath(agentPath, agent, this.environment.getNode(offloadArea));
                this.individualPaths.add(agentPath);
                i++;
                continue;
            }

            computePath(agentPath, agent, picks.get(this.genome[i] - 1));

            while (i < (this.genome.length - 1) && this.genome[i + 1] > 0) {
                computePath(agentPath, picks.get(this.genome[i] - 1), picks.get(this.genome[i + 1] - 1));
                i++;
            }

            computePath(agentPath, picks.get(this.genome[i] - 1), this.environment.getNode(offloadArea));
            this.individualPaths.add(agentPath);
            i = i + 2;
        }

        this.fitness = 0;
        for (AgentPath path : this.individualPaths) {
            if (this.fitness < path.getValue()) {
                this.fitness = path.getValue();
            }
        }

        detectAndPenalizeCollisions();
    }

    private void computePath(AgentPath agentPath, Node firstNde, Node secondNode) {
        agentPath.addPath(this.aStar.search(firstNde, secondNode), secondNode.getLocation());
    }

    private void detectAndPenalizeCollisions() {
        this.numberOfCollisions = 0;

        // BUILD PAIRS
        for (AgentPath agentPath : this.individualPaths) {
            agentPath.populateNodePairsMap();
        }

        // TYPE 1 COLLISIONS
        for (int i = 0; i < this.individualPaths.size() - 1; i++) {
            for (int j = i + 1; j < this.individualPaths.size(); j++) {
                for (Node node : this.individualPaths.get(i).getPath()) {
                    if (this.individualPaths.get(j).getPath().containsNodeAtTime(node.getNodeNumber(), node.getTime())) {
                        this.numberOfCollisions++;
                    }
                }
            }
        }

        // TYPE 2 COLLISIONS
        List<TimePair> pairs;
        for (int i = 0; i < this.individualPaths.size() - 1; i++) {
            NodePathList path = this.individualPaths.get(i).getPath();
            for (int j = i + 1; j < this.individualPaths.size(); j++) {
                NodePathList path1 = this.individualPaths.get(j).getPath();
                for (int k = 0; k < path.size() - 1; k++) {
                    if (isEdgeOneWay(path.get(k).getNodeNumber(), path.get(k + 1).getNodeNumber())) {
                        pairs = path1.getPair(path.get(k + 1), path.get(k));
                        if (pairs != null) {
                            for (TimePair timePair : pairs) {
                                if (rangesOverlap(path.get(k).getTime(), path.get(k + 1).getTime(), timePair.getNode1Time(), timePair.getNode2Time())) {
                                    this.numberOfCollisions++;
                                }
                            }
                        }
                    }
                }
            }
        }

        // COMPUTE FITNESS
        this.fitness = (this.fitness * this.environment.getTimeWeight()) + (this.numberOfCollisions * this.environment.getCollisionsWeight());
    }

    private boolean isEdgeOneWay(int node1, int node2) {
        return this.environment.getEdgeDirection(node1, node2) == 1;
    }

    private boolean rangesOverlap(double x1, double x2, double y1, double y2) {
        return x1 < y2 && y1 < x2;
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(o.fitness, this.fitness);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Fitness: ");
        sb.append(fitness);
        sb.append(" - Collisions: ");
        sb.append(this.numberOfCollisions);
        sb.append("\nPath: ");
        for (int value : genome) {
            sb.append(value).append(" ");
        }
        return sb.toString();
    }
}
