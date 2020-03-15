package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Individual> individuals;
    private Individual bestInPopulation;

    public Population(int size) {
        this.individuals = new ArrayList<>(size);
    }

    public Population(int size, int numPicks, int numAgents) {
        this.individuals = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            individuals.add(new Individual(numPicks, numAgents));
        }
    }


    public Individual evaluate() {
        bestInPopulation = individuals.get(0);

        for (Individual individual : individuals) {
            individual.computeFitness();
            if (individual.compareTo(bestInPopulation) > 0) {
                bestInPopulation = individual;
            }
        }

        return bestInPopulation;
    }

    public int size() {
        return individuals.size();
    }

    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    public Individual getIndividual(int index) {
        return individuals.get(index);
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }
}
