package ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.Population;

public abstract class SelectionMethod {

    protected int popSize;

    public SelectionMethod(int popSize){
        this.popSize = popSize;
    }

    public abstract Population run(Population original);
}
