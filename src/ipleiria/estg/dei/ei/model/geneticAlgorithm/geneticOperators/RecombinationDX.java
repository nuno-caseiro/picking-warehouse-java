package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import java.util.*;

public class RecombinationDX extends Recombination {

    public RecombinationDX(double probability) { super(probability); }

    int cut1P1;
    int cut2P1;

    int cut1P2;
    int cut2P2;

    private List<Integer> child1,child2;
    private HashMap<Integer,List<Integer>> segments1;
    private HashMap<Integer,List<Integer>> segments2;



    @Override
    public void recombine(Individual ind1, Individual ind2) {


/*        int[] genome1= new int[]{9,4,1,10,11,5,2,7,8,3,12,6};
        int[] genome2= new int[]{10,11,3,7,9,12,1,2,4,8,5,6};
        ind1.setGenome(genome1);
        ind2.setGenome(genome2);*/

        int size = ind1.getGenome().length;


        cut1P1=0; // GeneticAlgorithm.random.nextInt(ind1.getNumGenes());
        cut1P2=0;



       segments1= new HashMap<>();
       segments2= new HashMap<>();
        child1 = new Vector<Integer>();
        child2 = new Vector<Integer>();


        create_Segments(ind1, segments1);
        create_Segments(ind2, segments2);



        createChild(ind1,ind2);

        /*System.out.println("Start");
        System.out.println(ind1.toString());
        System.out.println(ind2.toString());
        System.out.println(segment1.toString());
        System.out.println(segment2.toString());

        checkDuplicates(child1);
        insertRemainingNumbers(child1,segment1,segment2.size()-1, size);
        replaceIndividual(ind1,child1);

        checkDuplicates(child2);
        insertRemainingNumbers(child2,segment2,segment2.size()-1, size);
        replaceIndividual(ind2,child2);


        System.out.println("----Child----");
        System.out.println(child1.toString());*/

    }

    private void createChild( Individual individual1, Individual individual2) {


        List<Integer> child = new LinkedList<>();

        for (int i = 0; i < individual1.getNumGenes(); i++) {
            child.add(0);
        }
        Random random = new Random();
        int randomAgent = random.nextInt(segments1.size()) ;
        while(segments1.get(randomAgent)==null ||segments2.get(randomAgent) == null ){
            randomAgent = random.nextInt(segments1.size()) ;
        }

        List<Integer> agentGenes1 = segments1.get(randomAgent);
        int indexFirstGeneOfCut1 = individual1.getIndexOf(agentGenes1.get(0));
        int indexLastGeneOfCut1 = individual1.getIndexOf(agentGenes1.get(agentGenes1.size()-1));


        List<Integer> agentGenes2 = segments2.get(randomAgent);
        System.out.println(randomAgent);

        int indexFirstGeneOfCut2 = individual2.getIndexOf(agentGenes2.get(0));
        int indexLastGeneOfCut2 = individual2.getIndexOf(agentGenes2.get(agentGenes2.size()-1));

        int j = 0;

        for(int i= indexFirstGeneOfCut2; i< indexLastGeneOfCut2+1; i++){
            replaceGene(child,i,agentGenes2.get(j));
           // child.add(i,agentGenes2.get(j));
            j++;
        }

        int k= indexLastGeneOfCut1+1;

        for(int i = indexLastGeneOfCut2+1; i< individual1.getNumGenes() ; i++){
            replaceGene(child,i,individual1.getGene(k));
            k++;
        }

          /*  if(i==individual1.getNumGenes() && child.get(0)==0){
               int o = i-1;
               int x = 0;
                while(o!=indexFirstGeneOfCut1){
                    if(o==individual1.getNumGenes()){
                        o=0;
                    }
                    replaceGene(child,x,individual1.getGene(o));
                    o++;
                    x++;

                }

            }else{
                if(i!=individual1.getNumGenes()){*/

                //}
          //  }


    }

    private void replaceIndividual(Individual ind, List<Integer> child) {
        for (int i = 0; i < ind.getGenome().length; i++) {
            ind.setGene(i,child.get(i));
        }
    }

    private void create_Segments(Individual ind, HashMap<Integer,List<Integer>> segments) {
        List<Integer> segment = new LinkedList<>();


            int i = 0;
            for (int j = 0; j < ind.getNumGenes(); j++) {
                int gene = ind.getGene(j);
                segment.add(gene);
                if(gene<0){
                  List<Integer> segmentCopy = new LinkedList<>();
                  segmentCopy.addAll(segment);
                  segments.put(i,segmentCopy);
                  i++;
                  segment.clear();
                }else{
                    if(j==ind.getNumGenes()-1){
                        List<Integer> segmentCopy = new LinkedList<>();
                        segmentCopy.addAll(segment);
                        segments.put(i,segmentCopy);
                        segment.clear();

                    }
                }
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

    private void insertRemainingNumbers(List<Integer> child, List<Integer> seg1, int minRandom, int size){
        while (child.size()!=size){
            child.add(0);
        }

        for (int i = 0; i < seg1.size(); i++) {
            if(!child.contains(seg1.get(i))){
                int random = GeneticAlgorithm.random.nextInt(size - minRandom) + minRandom;
                child.add(random,seg1.get(i));
            }
        }

        child.removeIf(integer -> integer == 0);

    }

    private void replaceGene(List<Integer> list, int indice, int number){
        list.remove(indice);
        list.add(indice,number);

    }

    @Override
    public String toString() {
        return "DX";
    }
}
