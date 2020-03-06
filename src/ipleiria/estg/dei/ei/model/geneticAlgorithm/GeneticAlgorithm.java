package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators.Mutation;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators.Recombination;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.SelectionMethod;

import java.util.Random;

public class GeneticAlgorithm {

//    public static Random random;
    private final int popSize;
    private final int maxGenerations;
    private Population population;
    private final SelectionMethod selection;
    private final Recombination recombination;
    private final Mutation mutation;
    private int t;
    private boolean stopped;
    private Individual bestInRun;

    public GeneticAlgorithm(int popSize, int maxGenerations, SelectionMethod selection, Recombination recombination, Mutation mutation) {
        this.popSize = popSize;
        this.maxGenerations = maxGenerations;
        this.selection = selection;
        this.recombination = recombination;
        this.mutation = mutation;
//        random = rand;
    }

    public Individual run() {
        t = 0;
    }
}
