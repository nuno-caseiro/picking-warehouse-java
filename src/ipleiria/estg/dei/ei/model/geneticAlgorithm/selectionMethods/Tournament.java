package ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Population;

public class Tournament extends SelectionMethod {

    private int tournamentSize;

    public Tournament(int popSize) {
        this(popSize, 2);
    }

    public Tournament(int popSize, int size) {
        super(popSize);
        this.tournamentSize = size;
    }

    @Override
    public Population run(Population original) {
        Population result = new Population(original.size());

        for (int i = 0; i < popSize; i++) {
            result.addIndividual(tournament(original));
        }

        return result;
    }

    private Individual tournament(Population population) {
        Individual best = population.getIndividual(GeneticAlgorithm.random.nextInt(popSize));

        for (int i = 1; i < tournamentSize; i++) {
            Individual aux = population.getIndividual(GeneticAlgorithm.random.nextInt(popSize));
            if (aux.compareTo(best) > 0) { //if aux is BETTER than best
                best = aux;
            }
        }

        return new Individual(best);
    }

    @Override
    public String toString(){
        return "Tournament(" + tournamentSize + ")";
    }
}
