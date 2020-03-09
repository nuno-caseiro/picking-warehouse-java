package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Population;

public abstract class Recombination extends GeneticOperator {

    public Recombination(double probability) {
        super(probability);
    }

    public void run(Population population) {
        int populationSize = population.size();
        for (int i = 0; i < populationSize; i += 2) {
            if (GeneticAlgorithm.random.nextDouble() < getProbability()) {
                recombine(population.getIndividual(i), population.getIndividual(i + 1));
            }
        }
    }

    public abstract void recombine(Individual ind1, Individual ind2);
}
