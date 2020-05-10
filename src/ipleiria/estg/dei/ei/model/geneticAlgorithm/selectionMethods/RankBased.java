package ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods;



import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Population;
import ipleiria.estg.dei.ei.utils.FitnessComparator;

import java.util.Comparator;

public class RankBased extends SelectionMethod  {

    double[] accumulated;
    double ps = 2;

    public RankBased(int popSize) {
        super(popSize);
        accumulated = new double[popSize];
    }

    public RankBased(int popSize, double ps ){
        super(popSize);
        accumulated = new double[popSize];
        this.ps= ps;
    }

    @Override
    public Population run(Population original) {
        original.getIndividuals().sort(new FitnessComparator());

        Population result = new Population(original.size());
        double N=popSize;
        double[] prob = new double[original.size()];
        for (int i = 1; i <= original.size(); i++) {
            prob[i-1] = (1/N) *(2-ps+2*(ps-1)*((i-1)/(N-1)));
        }

        accumulated[0] = prob[0];
        for (int i = 1; i < popSize; i++) {
            accumulated[i] = accumulated[i - 1] + prob[i];
        }

        double rankSum = accumulated[popSize - 1];
        for (int i = 0; i < popSize; i++) {
            accumulated[i] /= rankSum;
        }

        for (int i = 0; i < popSize; i++) {
            result.addIndividual(roulette(original));
        }
        
        return result;
    }

    private Individual roulette(Population population) {
        double probability = GeneticAlgorithm.random.nextDouble();

        for (int i = 0; i < popSize; i++) {
            if (probability <= accumulated[i]) {
                return new Individual(population.getIndividual(i));
            }
        }

        //For the case where all individuals have fitness 0
        return new Individual(population.getIndividual(GeneticAlgorithm.random.nextInt(popSize)));
    }
    
    @Override
    public String toString(){
        return "Rank("+ps+")";
    }    
}


