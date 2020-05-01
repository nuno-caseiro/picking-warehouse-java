package ipleiria.estg.dei.ei.model.geneticAlgorithm;

import ipleiria.estg.dei.ei.model.experiments.ExperimentListener;

public interface GAListener extends ExperimentListener {
   
    void generationEnded(GeneticAlgorithm e);
    
    void runEnded(GeneticAlgorithm e);

}
