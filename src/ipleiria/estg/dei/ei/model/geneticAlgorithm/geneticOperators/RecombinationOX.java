package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

public class RecombinationOX extends Recombination {

    public RecombinationOX(double probability) {
        super(probability);
    }

    @Override
    public void recombine(Individual ind1, Individual ind2) {

        final int size = ind1.getNumGenes();

        int start = 0;
        int end = 0;
        while (start == end) {

            final int number1 = GeneticAlgorithm.random.nextInt(size - 1);
            final int number2 = GeneticAlgorithm.random.nextInt(size);

            start = Math.min(number1, number2);
            end = Math.max(number1, number2);
        }

        final List<Integer> child1 = new Vector<Integer>();
        final List<Integer> child2 = new Vector<Integer>();

        for (int i = start; i <= end; i++) {
            child1.add(ind1.getGene(i));
        }

        for (int i = start; i <= end; i++) {
            child2.add(ind2.getGene(i));
        }

        int currentBoxIndex = 0;
        int currentBoxInInd1 = 0;
        int currentBoxInInd2 = 0;

        for (int i = 0; i < size; i++) {

            currentBoxIndex = (end + i) % size;

            currentBoxInInd1 = ind1.getGene(currentBoxIndex);
            currentBoxInInd2 = ind2.getGene(currentBoxIndex);

            if (!child1.contains(currentBoxInInd2)) {
                child1.add(currentBoxInInd2);
            }

            if (!child2.contains(currentBoxInInd1)) {
                child2.add(currentBoxInInd1);
            }
        }

        Collections.rotate(child1, start);
        Collections.rotate(child2, start);


        for (int i = 0; i < size; i++) {
            ind1.setGene(i, child2.get(i));
        }


        for (int i = 0; i < size; i++) {
            ind2.setGene(i, child1.get(i));
        }

//        System.out.println(ind1.toString());
//        System.out.println(ind2.toString());

    }

    @Override
    public String toString(){
        return "OX";
    }
}
