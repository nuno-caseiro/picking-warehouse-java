package ipleiria.estg.dei.ei.model.experiments;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.utils.FileOperations;
import ipleiria.estg.dei.ei.utils.Maths;
import java.io.File;
import java.util.Arrays;

public class StatisticBestAverage implements GAListener {

    private final double[] values;
    private final double[] valuesWoCollisions;
    private int run;
    private final double[] allRunsCollisions;
    private GeneticAlgorithm geneticAlgorithm;
    private double numberTimesOffload;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        run=0;
        values = new double[numRuns];
        valuesWoCollisions = new double[numRuns];
        allRunsCollisions = new double[numRuns];
        numberTimesOffload = 0;
        File file = new File("statistic_average_fitness_1.xls");
        if(!file.exists()){
            FileOperations.appendToTextFile("statistic_average_fitness_1.xls", experimentHeader + "AverageFitness:" + "\t" + "AverageFitnessStdDev:" + "\t" + "AverageTime:" + "\t" + "AverageTimeStdDev:" + "\t" + "CollisionsAverage" + "\t" + "CollisionsAverageStdDev" +"\t"+ "NumberAgents"+"\t"+ "NumberPicks\t"+"#Offload"+"\r\n");
        }
    }


    @Override
    public void generationEnded(GeneticAlgorithm e) {

    }

    @Override
    public void runEnded(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm= geneticAlgorithm;
        values[run]=geneticAlgorithm.getBestInRun().getFitness();
        valuesWoCollisions[run]=geneticAlgorithm.getBestInRun().getFitnessWoCollisions();
        allRunsCollisions[run++]=geneticAlgorithm.getBestInRun().getNumberOfCollisions();
        this.numberTimesOffload += geneticAlgorithm.getBestInRun().getNumberTimesOffload();
    }


    @Override
    public void experimentEnded() {
        double average = Maths.average(values);
        double stdDeviation= Maths.standardDeviation(values,average);
        double collisionsAverage = Maths.average(allRunsCollisions);
        double collisionStdDeviation = Maths.standardDeviation(allRunsCollisions,collisionsAverage);

        double averageWoCollisions = Maths.average(valuesWoCollisions);
        double stdDeviationWoCollisions = Maths.standardDeviation(valuesWoCollisions,averageWoCollisions);

        int nrAgents = Environment.getInstance().getNumberOfAgents();
        int nrPicks = Environment.getInstance().getNumberOfPicks();

        double numTimesOffload = numberTimesOffload/(this.values.length * nrAgents);

        FileOperations.appendToTextFile("statistic_average_fitness_1.xls", buildExperimentValues() + average +"\t" + stdDeviation + "\t" + averageWoCollisions + "\t"+ stdDeviationWoCollisions  +"\t" + collisionsAverage + "\t" + collisionStdDeviation +"\t"+ nrAgents + "\t"+ nrPicks + "\t"+ numTimesOffload  +"\r\n");
        Arrays.fill(values,0);
        Arrays.fill(valuesWoCollisions,0);
        Arrays.fill(allRunsCollisions,0);
        this.run=0;
        this.numberTimesOffload = 0;
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
