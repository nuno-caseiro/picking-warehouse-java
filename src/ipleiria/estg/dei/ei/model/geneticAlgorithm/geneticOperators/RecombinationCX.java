package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import java.util.*;

public class RecombinationCX extends Recombination {

    public RecombinationCX(double probability) { super(probability); }

    private ArrayList<Individual> offsprings = new ArrayList<Individual>();


    @Override
    public void recombine(Individual ind1, Individual ind2) {
        final List<Integer> cycleIndices = new Vector<Integer>();

        int ind1index = GeneticAlgorithm.random.nextInt(ind1.getNumGenes() - 1);
        cycleIndices.add(ind1index);

        int ind2Box = ind2.getGene(ind1index);

        ind1index = ind1.getIndexOf(ind2Box);

        while (ind1index != cycleIndices.get(0)) {
            cycleIndices.add(ind1index);
            ind2Box = ind2.getGene(ind1index);
            ind1index = ind1.getIndexOf(ind2Box);
        }

        for (Integer cycleIndex : cycleIndices) {
            int aux = ind1.getGene(cycleIndex);
            ind1.setGene(cycleIndex,ind2.getGene(cycleIndex));
            ind2.setGene(cycleIndex,aux);
        }
    }

    @Override
    public String toString() {
        return "CX";
    }
}
