package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Population {

    private List<Individual> individuals;

    public Population(int size) {
        this.individuals = new ArrayList<>(size);
    }


}
