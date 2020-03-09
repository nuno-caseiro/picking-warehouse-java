package ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators;


public abstract class GeneticOperator {

    protected double probability;

    public GeneticOperator(double probability){
        this.probability = probability;
    }
    
    public double getProbability(){
        return probability;
    }
}