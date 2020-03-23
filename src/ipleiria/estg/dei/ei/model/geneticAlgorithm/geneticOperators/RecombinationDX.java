package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import java.util.*;

public class RecombinationDX extends Recombination {

    public RecombinationDX(double probability) { super(probability); }

    int indexFirstGeneOfCut1;
    int indexLastGeneOfCut1;
    int indexFirstGeneOfCut2;
    int indexLastGeneOfCut2;

    private List<Integer> child1,child2;
    private HashMap<Integer,List<Integer>> segments1;
    private HashMap<Integer,List<Integer>> segments2;
    List<Integer> agentGenes1;
    List<Integer> agentGenes2;



    @Override
    public void recombine(Individual ind1, Individual ind2) {


/*        int[] genome1= new int[]{9,4,1,10,11,5,2,7,8,3,12,6};
        int[] genome2= new int[]{10,11,3,7,9,12,1,2,4,8,5,6};
        ind1.setGenome(genome1);
        ind2.setGenome(genome2);*/

        int size = ind1.getGenome().length;
        System.out.println("Start");
        System.out.println(ind1.toString());
        System.out.println(ind2.toString());

       segments1= new HashMap<>();
       segments2= new HashMap<>();
        child1 = new Vector<Integer>();
        child2 = new Vector<Integer>();


        create_Segments(ind1, segments1);
        create_Segments(ind2, segments2);



        createChild(ind1,ind2, 1);
        checkDuplicates(child1);
        insertRemainingNumbers(child1,ind1);
        replaceIndividual(ind1,child1);

        createChild(ind2,ind1, 2);
        checkDuplicates(child2);
        insertRemainingNumbers(child2,ind2);
        replaceIndividual(ind2,child2);


        //System.out.println(segment1.toString());
        //System.out.println(segment2.toString());






        System.out.println("----Child----");
        System.out.println(child1.toString());
        System.out.println(child2.toString());

    }

    private void createChild( Individual individual1, Individual individual2, Integer nr) {


        List<Integer> child = new LinkedList<>();

        for (int i = 0; i < individual1.getNumGenes(); i++) {
            child.add(0);
        }
        Random random = new Random();
        int randomAgent = random.nextInt(segments1.size()) ;
        while(segments1.get(randomAgent)==null ||segments2.get(randomAgent) == null ){
            randomAgent = random.nextInt(segments1.size()) ;
        }

        agentGenes1 = segments1.get(randomAgent);
         indexFirstGeneOfCut1 = individual1.getIndexOf(agentGenes1.get(0));
         indexLastGeneOfCut1 = individual1.getIndexOf(agentGenes1.get(agentGenes1.size()-1));


         agentGenes2 = segments2.get(randomAgent);

         indexFirstGeneOfCut2 = individual2.getIndexOf(agentGenes2.get(0));
         indexLastGeneOfCut2 = individual2.getIndexOf(agentGenes2.get(agentGenes2.size()-1));

        int j = 0;


        for(int i= indexFirstGeneOfCut1; i< indexLastGeneOfCut1+1; i++){
            replaceGene(child,i,agentGenes2.get(j));
           // child.add(i,agentGenes2.get(j));
            j++;
            if(j==agentGenes2.size()){
                break;
            }
        }

        int k= indexLastGeneOfCut1+1;

        for(int i = indexLastGeneOfCut1+1; i<= individual1.getNumGenes() ; i++){
            if(k==individual1.getNumGenes()){
                k=0;
            }
            if(i == individual1.getNumGenes() ){
                i=0;
            }
            while (child.get(i)!=0 && i!=individual1.getNumGenes()-1){
                i++;
            }
            replaceGene(child,i,individual1.getGene(k));
            k++;
            if(!notComplete(child) ){
                break;
            }
        }

       if(nr==1){
           child1=child;
       }else{
           child2=child;
       }

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

    public boolean notComplete(List<Integer> child){
        for (Integer integer : child) {
            if(integer==0){
                return true;
            }
        }
        return false;
    }


    private void checkDuplicates(List<Integer> child){
        for (int i=0; i<child.size(); i++){
            int x= child.get(i);
            for(int j= i+1; j<child.size();j++){
                int y = child.get(j);
                if(x==y){
                    if(j==indexFirstGeneOfCut1 || j==indexLastGeneOfCut1){
                        child.set(i,0);
                        break;
                    }
                    child.set(j,0);
                    break;
                }
            }
        }

        //child.removeIf(integer -> integer == 0);
    }

    private void insertRemainingNumbers(List<Integer> child, Individual individual){
        List<Integer> remainingNumbers = new LinkedList<>();

        for (int i = 0; i < individual.getGenome().length; i++) {
           if(!child.contains(individual.getGene(i))){
               remainingNumbers.add(individual.getGene(i));
           }
        }

        Random random = new Random();

        for (int i = 0; i < child.size(); i++) {
            if(child.get(i)==0){
                replaceGene(child,i,remainingNumbers.get(0));
                remainingNumbers.remove(0);
            }
        }


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
