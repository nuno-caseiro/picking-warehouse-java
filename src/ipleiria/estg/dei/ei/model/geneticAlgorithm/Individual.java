package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.search.Pair;
import ipleiria.estg.dei.ei.model.search.State;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Individual implements Comparable<Individual>{

    private int[] genome;
    private double fitness;

    public Individual(int numPicks, int numAgents) {
        int genomeSize = numPicks + (numAgents -1);
        this.genome = new int[genomeSize];
        List<Integer> genes = new LinkedList<>(); // 0 -> divisions between paths for different agents | 1 ... n index of the picks (eg. Environment.getInstance().getPicks().get(n -1))

        for (int i = 0; i < numAgents - 1; i++) {
            genes.add(-1 - i);
        }

        for (int i = 0; i < numPicks; i++) {
            genes.add(i + 1);
        }

        for (int i = 0; i < genomeSize; i++) {
            int randomIndex = GeneticAlgorithm.random.nextInt(genes.size());
            genome[i] = genes.get(randomIndex);
            genes.remove(randomIndex);
        }
    }

    public Individual (Individual original) {
        this.genome = new int[original.genome.length];
        System.arraycopy(original.genome, 0, this.genome, 0, this.genome.length);
        this.fitness = original.fitness;
    }

    public Individual(int[] genome) {
        this.genome = genome;
        computeFitness();
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

    public int getIndexOf(int value){
        for (int i = 0; i < genome.length; i++) {
            if (genome[i] == value)
                return i;
        }
        return -1;
    }

    public int getNumGenes() {
        return genome.length;
    }

    public int[] getGenome() {
        return genome;
    }

    public void setGene(int index, int newValue) {
        genome[index] = newValue;
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(o.fitness, this.fitness);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("fitness: ");
        sb.append(fitness);
        sb.append("\npath: ");
        for (int value : genome) {
            sb.append(value).append(" ");
        }
        return sb.toString();
    }

    public void computeFitness() {
        List<State> picks = Environment.getInstance().getPicks();
        HashMap<String, Pair> pairsValueMap = Environment.getInstance().getPairsMap();
        List<State> agents = Environment.getInstance().getOriginalAgents();
        State offload = Environment.getInstance().getOffloadArea();
        double agentFitness = 0;
        this.fitness = 0;
        int agent = 0;
        State previousState = agents.get(agent);
        State currentState;

        for (int i = 0; i < genome.length; i++) {
            if (genome[i] < 0) {
                currentState = offload;
                agentFitness += getPairValue(pairsValueMap, previousState, currentState);

                if (agentFitness > fitness) {
                    fitness = agentFitness;
                }
                agentFitness = 0;
                previousState = agents.get(++agent);
                continue;
            }
            currentState = picks.get(genome[i] - 1);
            agentFitness += getPairValue(pairsValueMap, previousState, currentState);

            previousState = currentState;
        }

        currentState = offload;
        agentFitness += getPairValue(pairsValueMap, previousState, currentState);

        if (agentFitness > fitness) {
            fitness = agentFitness;
        }
    }

    private double getPairValue(HashMap<String, Pair> pairsValueMap, State state1, State state2) {
        return pairsValueMap.get(getKey(state1, state2)).getValue();
    }

    private String getKey(State state1, State state2) {
        return state1.getLine() + "-" + state1.getColumn() + "/" + state2.getLine() + "-" + state2.getColumn();
    }
}
