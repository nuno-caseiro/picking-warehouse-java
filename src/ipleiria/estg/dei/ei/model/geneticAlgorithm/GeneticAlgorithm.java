package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators.Mutation;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators.Recombination;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.SelectionMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class GeneticAlgorithm {

    public static Random random;
    private final int popSize;
    private final int maxGenerations;
    private Population population;
    private final SelectionMethod selection;
    private final Recombination recombination;
    private final Mutation mutation;
    private int numAgents;
    private int numPicks;
    private int t;
    private boolean stopped;
    private Individual bestInRun;

    public GeneticAlgorithm(int popSize, int maxGenerations, SelectionMethod selection, Recombination recombination, Mutation mutation, int numAgents, int numPicks, Random rand) {
        this.popSize = popSize;
        this.maxGenerations = maxGenerations;
        this.selection = selection;
        this.recombination = recombination;
        this.mutation = mutation;
        this.numAgents = numAgents;
        this.numPicks = numPicks;
        random = rand;
    }

    public Individual  run() {
        t = 0;
        population = new Population(popSize, numPicks, numAgents);
        bestInRun = population.evaluate();
        fireGenerationEnded(this);

        while (!stopCondition(t)) {
            Population populationAux = selection.run(population);
            recombination.run(populationAux);
            mutation.run(populationAux);
            population = populationAux;
            Individual bestInGen = population.evaluate();
            computeBestInRun(bestInGen);
            t++;
            fireGenerationEnded(this);
        }
        fireRunEnded(this);
        return bestInRun;
    }

    private void computeBestInRun(Individual bestInGen) {
        if (bestInGen.compareTo(bestInRun) > 0) {
            bestInRun = new Individual(bestInGen);
        }
    }

    private boolean stopCondition(int t) {
        return stopped || t == maxGenerations;
    }

    public void stop() {
        stopped = true;
    }

    public Individual getBestInRun() {
        return bestInRun;
    }

    public int getGenerationNr() {
        return t;
    }

    public double getAverageFitness() {
        return population.getAverageFitness();
    }

    public double getAverageFitnessWithoutCollisions() {
        return population.getAverageFitnessWithoutCollisions();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Population size:" + popSize + "\r\n");
        sb.append("Max generations:" + maxGenerations + "\r\n");
        sb.append("Selection:" + selection + "\r\n");
        sb.append("Recombination:" + recombination + "\r\n");
        sb.append("Recombination prob.: " + recombination.getProbability() + "\r\n");
        sb.append("Mutation:" + mutation + "\r\n");
        sb.append("Mutation prob.: " + mutation.getProbability()+ "\r\n");
        sb.append("Time weight:" + Environment.getInstance().getTimeWeight()+ "\r\n");
        sb.append("Collisions weight:" + Environment.getInstance().getCollisionsWeight()+ "\r\n");
        return sb.toString();
    }

    public int getPopSize() {
        return popSize;
    }

    public int getMaxGenerations() {
        return maxGenerations;
    }

    public Population getPopulation() {
        return population;
    }

    public SelectionMethod getSelection() {
        return selection;
    }

    public Recombination getRecombination() {
        return recombination;
    }

    public Mutation getMutation() {
        return mutation;
    }

    //Listeners
    private final transient List<GAListener> listeners = new ArrayList<>(3);

    public synchronized void addGAListener(GAListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public synchronized void removeAGListener(GAListener listener) {
        if (listeners != null && listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void fireGenerationEnded(GeneticAlgorithm e) {
        for (GAListener listener : listeners) {
            listener.generationEnded(e);
        }
        if (stopped) {
            stop();
        }
    }

    public void fireRunEnded(GeneticAlgorithm e) {
        for (GAListener listener : listeners) {
            listener.runEnded(e);
        }
    }
}
