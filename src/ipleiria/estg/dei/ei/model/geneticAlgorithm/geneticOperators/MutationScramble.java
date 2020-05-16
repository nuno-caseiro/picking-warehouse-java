package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import java.util.LinkedList;

public class MutationScramble extends Mutation {

    public MutationScramble(double probability) {
        super(probability);
    }

    @Override
    public void mutate(Individual ind) {
        int min = (Environment.getInstance().getNumberOfAgents()-1)*-1;
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());
        }while (cut1==cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }

        LinkedList<Integer> list = new LinkedList<>();
        for (int j = 0; j <= cut2-cut1; j++) {
            list.add(ind.getGene(cut1+j));
        }

        int num1=-1;
        int num2=-1;

        for(int i = 0; i < list.size(); i++){
            do{
                num1= GeneticAlgorithm.random.nextInt((ind.getNumGenes()-min)+1)+min;
            }while(!list.contains(num1) || num1==0);

            do{
                num2= GeneticAlgorithm.random.nextInt((ind.getNumGenes()-min)+1)+min;
            }while(!list.contains(num2) || num2==0);

            int auxNum2 = ind.getIndexOf(num2);
            ind.setGene(ind.getIndexOf(num1), num2);
            ind.setGene(auxNum2, num1);
        }
    }

    @Override
    public String toString() {
        return "Scramble";
    }
}
