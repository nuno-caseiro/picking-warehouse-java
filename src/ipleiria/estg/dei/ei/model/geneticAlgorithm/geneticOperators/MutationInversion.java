package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

public class MutationInversion extends Mutation {

    public MutationInversion(double probability) {
        super(probability);
    }

    @Override
    public void mutate(Individual ind) {

        int l = ind.getNumGenes();

        int r1 = GeneticAlgorithm.random.nextInt(l);
        int r2 = GeneticAlgorithm.random.nextInt(l);

        while (r1 >= r2) {
            r1 = GeneticAlgorithm.random.nextInt(l);
            r2 = GeneticAlgorithm.random.nextInt(l);
        }

        int mid = r1 + ((r2 + 1) - r1) / 2;
        int endCount = r2;
        for (int i = r1; i < mid; i++) {
            int tmp = ind.getGene(i);
            ind.setGene(i, ind.getGene(endCount));
            ind.setGene(endCount, tmp);
            endCount--;
        }
    }

    @Override
    public String toString() {
        return "Inversion";
    }
}
