package ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Population;
import ipleiria.estg.dei.ei.utils.FitnessComparator;

import java.util.List;
import java.util.Random;
import java.util.Vector;

public class RankBased extends SelectionMethod {
    public RankBased(int popSize) {
        super(popSize);
    }

    @Override
    public Population run(Population original) {
        int[] fitnesses = new int[original.size()]; 	//array of all fitnesses
        double[] rankProb = new double[original.size()];		//rank probabilities
        int[][] sortedinitPopulation = new int[original.size()][original.getIndividual(0).getNumGenes()]; //2D array to store individuals sorted by fitness
        int rankSum = 0; 					//sum of all ranks
        int counter = 0; 					//keep track of location in array
        Random random = new Random();		//for randomness

        //get fitnesses for all individuals
        for (int i = 0; i < original.size(); i++) {
            fitnesses[i] = (int) original.getIndividual(i).getFitness(); //store fitnesses in array
        }

        List<Integer> aux = new Vector<>();
        for (int i = 0; i < fitnesses.length; i++) {
            aux.add(fitnesses[i]);
        }

        aux.sort(new FitnessComparator());

        for (int i = 0; i < fitnesses.length; i++) {
            fitnesses[i]=aux.get(i);
        }


        //sort initial population by fitness
        for (int m = 0; m < original.size(); m++) {
            for (int n = 0; n < original.size(); n++) {
                if (fitnesses[m] == original.getIndividual(n).getFitness()) {
                    sortedinitPopulation[m] = original.getIndividual(n).getGenome(); //store in new array
                }
            }
        }

        //find total sum of ranks
        for (int j = 1; j <= original.size(); j++) {
            rankSum += j;
        }

        double test = 0;
        //get probability for each rank
        for (int k = 0; k < original.size(); k++) {
            rankProb[k] = (double) k/rankSum; //store into array
        }
        Population population = new Population(original.size());

        while(counter != original.size()) { 				//while breeding pool is not full
            //int choice = Math.abs(random.nextInt(original.size())); 	//generate a random int smaller than size
            //double randomValue = 0 + (1 - 0) * random.nextDouble();
            double r = 0 + (rankSum - 0) * random.nextDouble();
            if(rankProb[counter] < r) { 				//if our rank probability is smaller than a random double
                population.addIndividual(new Individual(sortedinitPopulation[counter]));
                counter++; 	//increment location in breeding pool
            }
        }



        return population;


    }


}

