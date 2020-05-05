package ipleiria.estg.dei.ei.model.experiments;

import ipleiria.estg.dei.ei.gui.Main;
import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.utils.FileOperations;
import ipleiria.estg.dei.ei.utils.Maths;
import java.io.File;
import java.util.Arrays;

public class StatisticBestAverage implements GAListener {

    private final double[] values;
    private final double[] valuesWoFitness;
    private int run;
    private final double[] allRunsCollisions;
    private GeneticAlgorithm geneticAlgorithm;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        run=0;
        values = new double[numRuns];
        valuesWoFitness = new double[numRuns];
        allRunsCollisions = new double[numRuns];
        File file = new File("statistic_average_fitness.xls");
        if(!file.exists()){
            FileOperations.appendToTextFile("statistic_average_fitness.xls", experimentHeader + "\t" + "Average:" + "\t" + "StdDev:" + "\t" + "AverageWoCollisions:" + "\t" + "StdDevWoCollisions:" + "\t" + "Collisions average" + "\t" + "Collisions stdDev" +"\t"+ "Number agents"+"\t"+ "Number picks" +"\r\n");
        }
    }


    @Override
    public void generationEnded(GeneticAlgorithm e) {

    }

    @Override
    public void runEnded(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm= geneticAlgorithm;
        values[run]=geneticAlgorithm.getBestInRun().getFitness();
        valuesWoFitness[run]=geneticAlgorithm.getBestInRun().getFitnesswofitness();
        allRunsCollisions[run++]=geneticAlgorithm.getBestInRun().getNumberOfCollisions();
    }


    @Override
    public void experimentEnded() {
        double average = Maths.average(values);
        double stdDeviation= Maths.standardDeviation(values,average);
        double collisionsAverage = Maths.average(allRunsCollisions);
        double collisionStdDeviation = Maths.standardDeviation(allRunsCollisions,collisionsAverage);

        double averageWoCollisions = Maths.average(valuesWoFitness);
        double stdDeviationWoCollisions = Maths.standardDeviation(valuesWoFitness,averageWoCollisions);

        int nrAgents = Environment.getInstance().getNumberOfAgents();
        int nrPicks = Environment.getInstance().getNumberOfPicks();

        FileOperations.appendToTextFile("statistic_average_fitness.xls", buildExperimentValues() + "\t" + average +"\t" + stdDeviation + "\t" + averageWoCollisions + "\t"+ stdDeviationWoCollisions  +"\t" + collisionsAverage + "\t" + collisionStdDeviation +"\t"+ nrAgents + "\t"+ nrPicks + "\r\n");
        Arrays.fill(values,0);
        Arrays.fill(valuesWoFitness,0);
        Arrays.fill(allRunsCollisions,0);
        this.run=0;
    }

    private String buildExperimentValues() {
        StringBuilder sb = new StringBuilder();
        sb.append(geneticAlgorithm.getPopSize() + "\t");
        sb.append(geneticAlgorithm.getMaxGenerations() + "\t");
        sb.append(geneticAlgorithm.getSelection() + "\t");
        sb.append(geneticAlgorithm.getRecombination() + "\t");
        sb.append(geneticAlgorithm.getRecombination().getProbability() + "\t");
        sb.append(geneticAlgorithm.getMutation() + "\t");
        sb.append(geneticAlgorithm.getMutation().getProbability() + "\t");
        sb.append(Environment.getInstance().getTimeWeight() + "\t");
        sb.append(Environment.getInstance().getCollisionsWeight() + "\t");
        return sb.toString();
    }


}
