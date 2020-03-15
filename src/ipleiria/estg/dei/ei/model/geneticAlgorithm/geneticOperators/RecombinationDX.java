package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import java.util.List;
import java.util.Vector;

public class RecombinationDX extends Recombination {

    public RecombinationDX(double probability) { super(probability); }

    int cut1P1;
    int cut2P1;

    int cut1P2;
    int cut2P2;

    private List<Integer> child1,child2,segment1,segment2;



    @Override
    public void recombine(Individual ind1, Individual ind2) {


/*        int[] genome1= new int[]{9,4,1,10,11,5,2,7,8,3,12,6};
        int[] genome2= new int[]{10,11,3,7,9,12,1,2,4,8,5,6};
        ind1.setGenome(genome1);
        ind2.setGenome(genome2);*/




        cut1P1=0; // GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        cut2P1= GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        if(cut1P1>cut2P1){
            int aux= cut1P1;
            cut1P1=cut2P1;
            cut2P1=aux;
        }

        cut1P2=cut1P1;
        cut2P2=GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        /*while(cut2P2<=cut1P2){
            cut2P2=GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        }*/

        segment1 = new Vector<Integer>();
        segment2 = new Vector<Integer>();
        child1 = new Vector<Integer>();

        create_Segments(cut1P1,cut2P1,ind1,segment1);
        create_Segments(cut1P2,cut2P2,ind2,segment2);

        child1.addAll(segment2);
        for(int i= cut2P1+1; i<ind2.getNumGenes();i++){
            child1.add(ind1.getGene(i));
        }

       /* System.out.println("Start");
        System.out.println(ind1.toString());
        System.out.println(ind2.toString());
        System.out.println(segment1.toString());
        System.out.println(segment2.toString());*/

        checkDuplicates(child1);
        insertRemainingNumbers(child1,ind1,segment2.size());
        replaceIndividual(ind1,child1);


//        System.out.println("----Child----");
//      System.out.println(child1.toString());

    }

    private void replaceIndividual(Individual ind, List<Integer> child) {
        for (int i = 0; i < ind.getNumGenes(); i++) {
            ind.setGene(i,child.get(i));
        }
    }

    private void create_Segments(int cutPoint1, int cutPoint2, Individual ind1, List<Integer> segment) {
        for(int i = cutPoint1; i<=cutPoint2;i++){
            segment.add(ind1.getGene(i));
        }
    }

    private void checkDuplicates(List<Integer> child){
        for (int i=0; i<child.size(); i++){
            int x= child.get(i);
            for(int j= i+1; j<child.size();j++){
                int y = child.get(j);
                if(x==y){
                    child.set(j,0);
                    break;
                }
            }
        }

        child.removeIf(integer -> integer == 0);
    }

    private void insertRemainingNumbers(List<Integer> child, Individual ind, int minRandom){
        while (child.size()!=ind.getNumGenes()){
            child.add(0);
        }

        for (int i=1; i<=ind.getNumGenes();i++){
            if(!child.contains(i)){
                int random= GeneticAlgorithm.random.nextInt(ind.getNumGenes());
                while (random<minRandom){
                    random= GeneticAlgorithm.random.nextInt(ind.getNumGenes());
                }
                child.add(random,i);
            }
        }

        child.removeIf(integer -> integer == 0);

    }

    @Override
    public String toString() {
        return "DX";
    }
}
