package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

public class MutationInsert extends Mutation {

    public MutationInsert(double probability) {
        super(probability);
    }

    @Override
    public void mutate(Individual ind) {
        int cut1 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());//cut1=1
        int cut2;
        do {
            cut2 = GeneticAlgorithm.random.nextInt(ind.getNumGenes());//cut2=4
        }while (cut1==cut2);
        if (cut1 > cut2) {
            int aux = cut1;
            cut1 = cut2;
            cut2 = aux;
        }
        for(int i = cut2-1; i > cut1 ; i--) {//int i = 3;3>1;i--
            int aux = ind.getGene(i + 1);//aux=4
            ind.setGene(i + 1, ind.getGene(i));
            ind.setGene(i, aux);
        }
    }


    @Override
    public String toString() {
        return "Insert";
    }
}
