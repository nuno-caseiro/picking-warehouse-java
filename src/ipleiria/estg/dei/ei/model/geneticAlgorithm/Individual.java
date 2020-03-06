package ipleiria.estg.dei.ei.model.geneticAlgorithm;

public class Individual {

    private int[] genome;
    private double fitness;

    public Individual(int size) {
        this.genome = new int[size];

        //TODO generate individual
    }

    public Individual(Individual individual) {
        this.genome = new int[individual.genome.length];
        System.arraycopy(individual.genome, 0, this.genome, 0, this.genome.length);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
