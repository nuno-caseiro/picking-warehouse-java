package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Population;

public abstract class Mutation extends  GeneticOperator{

    public Mutation(double probability){
        super(probability);
    }

    public void run(Population population) {
        int populationSize = population.size();
        for (int i = 0; i < populationSize; i++) {
            if (GeneticAlgorithm.random.nextDouble() < getProbability()) {
                mutate(population.getIndividual(i));
            }
        }
    }

    public abstract void mutate(Individual individual);
}
