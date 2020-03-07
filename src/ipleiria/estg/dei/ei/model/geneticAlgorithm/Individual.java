package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.Environment;

import java.util.LinkedList;
import java.util.List;

public class Individual implements Comparable<Individual>{

    private int[] genome;
    private double fitness;
    private List<Integer> genes; // 0 -> divisions between paths for different agents | 1 ... n index of the picks (eg. Environment.getInstance().getPicks().get(n -1))

    public Individual(int numPicks, int numAgents) {
        int genomeSize = numPicks + (numAgents -1);
        this.genome = new int[genomeSize];
        this.genes = new LinkedList<>();

        for (int i = 0; i < numAgents - 1; i++) {
            genes.add(0);
        }

        for (int i = 0; i < numPicks; i++) {
            genes.add(i +1);
        }

        for (int i = 0; i < genomeSize; i++) {
            int randomIndex = GeneticAlgorithm.random.nextInt(genes.size());
            genome[i] = genes.get(randomIndex);
            genes.remove(randomIndex);
        }
    }

    public Individual (Individual original) {
        this.genome = new int[original.genome.length];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
        this.fitness = original.fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(o.fitness, this.fitness);
    }
}
