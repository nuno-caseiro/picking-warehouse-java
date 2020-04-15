package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.AStar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Individual implements Comparable<Individual> {

    private int[] genome;
    private double fitness;
    private List<AgentPath> individualPaths;
    private Environment environment;
    private AStar aStar;

    public Individual(int numPicks, int numAgents) {
        int genomeSize = numPicks + (numAgents - 1);
        this.genome = new int[genomeSize];
        this.environment = Environment.getInstance();

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
    }

//    public Individual(int[] genome) {
//        this.genome = genome;
//        computeFitness();
//    }

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
        this.aStar = new AStar();
        this.individualPaths = new ArrayList<>();

        List<Integer> picks = this.environment.getPicks();
        List<Integer> agents = this.environment.getAgents();
        int offloadArea = this.environment.getOffloadArea();

        AgentPath agentPath;
        int i = 0;
        for (int agent : agents) {
            agentPath = new AgentPath();

            if (i >= this.genome.length || this.genome[i] < 0) {
                computePath(agentPath, agent, offloadArea);
                this.individualPaths.add(agentPath);
                i++;
                continue;
            }

            computePath(agentPath, agent, picks.get(this.genome[i] - 1));

            while (i < (this.genome.length - 1) && this.genome[i + 1] > 0) {
                computePath(agentPath, picks.get(this.genome[i] - 1), picks.get(this.genome[i + 1] - 1));
                i++;
            }

            computePath(agentPath, picks.get(this.genome[i] - 1), offloadArea);
            this.individualPaths.add(agentPath);
            i = i + 2;
        }

        this.fitness = 0;
        for (AgentPath path : this.individualPaths) {
            if (this.fitness < path.getValue()) {
                this.fitness = path.getValue();
            }
        }
    }

    private void computePath(AgentPath agentPath, int firstNde, int secondNode) {
        agentPath.addPath(this.aStar.search(this.environment.getNode(firstNde), this.environment.getNode(secondNode)));
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
        sb.append("\nPath: ");
        for (int value : genome) {
            sb.append(value).append(" ");
        }
        return sb.toString();
    }
}
