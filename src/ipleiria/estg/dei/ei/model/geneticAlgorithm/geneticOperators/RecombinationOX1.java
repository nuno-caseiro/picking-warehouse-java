package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;


public class RecombinationOX1 extends Recombination {



    public RecombinationOX1(double probability) {
        super(probability);
    }

    private int[] child1, child2;
    int numGenes;

    @Override
    public void recombine(Individual ind1, Individual ind2) {
        numGenes = ind1.getNumGenes();

        int cut1 = GeneticAlgorithm.random.nextInt(numGenes-1);
        int cut2 = GeneticAlgorithm.random.nextInt(numGenes);

        int start = Math.min(cut1, cut2);
        int end = Math.max(cut1, cut2);

        child1 = new int[numGenes];
        child2 = new int[numGenes];
        insertElementsInsideCutArea(child1, ind2, start, end);
        insertElementsInsideCutArea(child2, ind1, start, end);

        insertElementsOutsideCutArea(child1, ind1, end);
        insertElementsOutsideCutArea(child2, ind2, end);


        for (int i = 0; i < numGenes; i++) {
            ind1.setGene(i, child1[i]);
            ind2.setGene(i, child2[i]);
        }
    }

    private boolean contains(int[] child, int value){
        for (int i = 0; i < numGenes; i++) {
            if (child[i] == value){
                return true;
            }
        }
        return false;
    }

    private void insertElementsInsideCutArea(int[] child, Individual parent, int start, int end){
        for (int i = start; i <= end; i++) {
            child[i] = parent.getGene(i);
        }
    }

    private void insertElementsOutsideCutArea(int[] child, Individual parent, int end){

        int pos = (end+1) % numGenes;
        for (int i = 0; i < numGenes; i++) {
            if (child[i] != 0){
                continue;
            }
            while (contains(child, parent.getGene(pos))){
                pos = (pos+1) % numGenes;
            }

            child[i] = parent.getGene(pos);
        }
    }

    @Override
    public String toString(){
        return "OX1";
    }
}
