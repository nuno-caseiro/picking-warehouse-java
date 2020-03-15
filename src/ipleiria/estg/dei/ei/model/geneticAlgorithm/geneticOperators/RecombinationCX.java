package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import java.util.*;

public class RecombinationCX extends Recombination {

    public RecombinationCX(double probability) { super(probability); }

    private ArrayList<Individual> offsprings = new ArrayList<Individual>();


    @Override
    public void recombine(Individual ind1, Individual ind2) {
        final int length = ind1.getGenome().length;

        System.out.println("START");
        System.out.println(ind1.toString());
        System.out.println(ind2.toString());

        // array representations of the parents
        final List<Integer> parent1Rep = new ArrayList<>();
        final List<Integer> parent2Rep = new ArrayList<>();

        final List<Integer> child1Rep = new ArrayList<>();
        final List<Integer> child2Rep = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            parent1Rep.add(ind1.getGene(i));
            parent2Rep.add(ind2.getGene(i));
            child1Rep.add(ind2.getGene(i));
            child2Rep.add(ind1.getGene(i));

        }


        System.out.println(parent1Rep.toString());
        System.out.println(parent2Rep.toString());

        // and of the children: do a crossover copy to simplify the later processing


        // the set of all visited indices so far
        final Set<Integer> visitedIndices = new HashSet<Integer>(length);
        // the indices of the current cycle
        final List<Integer> indices = new ArrayList<Integer>(length);

        // determine the starting index
        int idx =  0;//GeneticAlgorithm.random.nextInt(length);
        int cycle = 1;

        while (visitedIndices.size() < length) {
            indices.add(idx);

            int item = parent2Rep.get(idx);
            idx = parent1Rep.indexOf(item);

            while (idx != indices.get(0)) {
                // add that index to the cycle indices
                indices.add(idx);
                // get the item in the second parent at that index
                item = parent2Rep.get(idx);
                // get the index of that item in the first parent
                idx = parent1Rep.indexOf(item);
            }

            // for even cycles: swap the child elements on the indices found in this cycle
            if (cycle++ % 2 != 0) {
                for (int i : indices) {
                    Integer tmp = child1Rep.get(i);
                    child1Rep.set(i, child2Rep.get(i));
                    child2Rep.set(i, tmp);
                }
            }

            visitedIndices.addAll(indices);
            // find next starting index: last one + 1 until we find an unvisited index
            idx = (indices.get(0) + 1) % length;
            while (visitedIndices.contains(idx) && visitedIndices.size() < length) {
                idx++;
                if (idx >= length) {
                    idx = 0;
                }
            }
            indices.clear();
        }

        for (int i = 0; i < length; i++) {
            ind1.setGene(i,child1Rep.get(i));
            ind2.setGene(i,child2Rep.get(i));
        }

        System.out.println(child1Rep.toString());
        System.out.println(child2Rep.toString());
        System.out.println("STOP");
    }

    @Override
    public String toString() {
        return "CX";
    }
}
