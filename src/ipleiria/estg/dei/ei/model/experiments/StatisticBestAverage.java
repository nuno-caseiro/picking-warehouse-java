package ipleiria.estg.dei.ei.model.experiments;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.utils.FileOperations;
import ipleiria.estg.dei.ei.utils.Maths;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatisticBestAverage implements GAListener {

    private final double[] values;
    private final double[] valuesWoCollisions;
    private int run;
    private final double[] allRunsCollisions;
    private double numberTimesOffload;
    private double waitTime;
    private GeneticAlgorithm geneticAlgorithm;
    private final int numRuns;
    private int numberOfIndividualsWithCollisions;
    private List<Double> waitTimes;
    private List<Double> maxWaitTimes;

    public StatisticBestAverage(int numRuns, String experimentHeader) {
        run=0;
        values = new double[numRuns];
        valuesWoCollisions = new double[numRuns];
        allRunsCollisions = new double[numRuns];
        numberTimesOffload = 0;
        waitTime = 0;
        this.numberOfIndividualsWithCollisions = 0;
        this.numRuns=numRuns;
        this.waitTimes = new ArrayList<>();
        this.maxWaitTimes = new ArrayList<>();
        File file = new File("statistic_average_fitness_1.xls");
        if(!file.exists()){
            FileOperations.appendToTextFile("statistic_average_fitness_1.xls", experimentHeader + "AverageFitness:" + "\t" + "AverageFitnessStdDev:" + "\t" + "AverageTime:" + "\t" + "AverageTimeStdDev:" + "\t" + "CollisionsAverage" + "\t" + "CollisionsAverageStdDev" +"\t"+ "NumberAgents"+"\t"+ "NumberPicks" +"\t"+"NumberTimesOffload" + "\t" + "TimeWaitAvg" + "\t" + "MeanTimeWait" + "\t" + "MedianTimeWait" + "\t" + "%runsWithCollisions" + "\t" + "MeanMaxTimeWait" + "\t" + "MedianMaxTimeWait" + "\t" + "MaxTimeWaitExperiment" + "\r\n");
        }
    }


    @Override
    public void generationEnded(GeneticAlgorithm e) {

    }

    @Override
    public void runEnded(GeneticAlgorithm geneticAlgorithm) {
        this.geneticAlgorithm= geneticAlgorithm;
        values[run]=geneticAlgorithm.getBestInRun().getFitness();
        valuesWoCollisions[run]=geneticAlgorithm.getBestInRun().getTime();
        numberTimesOffload+= geneticAlgorithm.getBestInRun().getNumberTimesOffload();
        waitTime += geneticAlgorithm.getBestInRun().getWaitTime();

        allRunsCollisions[run++]=geneticAlgorithm.getBestInRun().getNumberOfCollisions();

        if (geneticAlgorithm.getBestInRun().getNumberOfCollisions() > 0) {
            this.numberOfIndividualsWithCollisions++;
            this.waitTimes.add(geneticAlgorithm.getBestInRun().getWaitTime());
            this.maxWaitTimes.add(geneticAlgorithm.getBestInRun().getMaxWaitTime());
        }

        System.out.println("#Collisions: " + geneticAlgorithm.getBestInRun().getNumberOfCollisions() + " WaitTime: " + geneticAlgorithm.getBestInRun().getWaitTime() + " MaxWaitTime: " + geneticAlgorithm.getBestInRun().getMaxWaitTime());
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

        double numberTimesOffl = numberTimesOffload/(numRuns * nrAgents);
        double timeWaitAvg = waitTime/(numRuns * nrAgents);

        double meanTimeWaitForIndividualsWithCollisions = waitTime / numberOfIndividualsWithCollisions;
        Collections.sort(this.waitTimes);
        double medianTimeWaitForIndividualsWithCollisions = Maths.computeMedian(this.waitTimes);
        double percentageOfRunsWithCollisions = (this.numberOfIndividualsWithCollisions / (double) this.numRuns) * 100;

        double meanMaxTimeWaitForIndividualsWithCollisions = Maths.average(this.maxWaitTimes);
        Collections.sort(this.maxWaitTimes);
        double medianMaxTimeWaitForIndividualsWithCollisions = Maths.computeMedian(this.maxWaitTimes);
        double maxWaitTimeForExperiment = this.maxWaitTimes.size() > 0 ? this.maxWaitTimes.get(this.maxWaitTimes.size() - 1) : 0;

        FileOperations.appendToTextFile("statistic_average_fitness_1.xls", buildExperimentValues() + average +"\t" + stdDeviation + "\t" + averageWoCollisions + "\t"+ stdDeviationWoCollisions  +"\t" + collisionsAverage + "\t" + collisionStdDeviation +"\t"+ nrAgents + "\t" + nrPicks + "\t" + numberTimesOffl + "\t"+ timeWaitAvg + "\t"+ meanTimeWaitForIndividualsWithCollisions + "\t" + medianTimeWaitForIndividualsWithCollisions + "\t" + percentageOfRunsWithCollisions + "\t" + meanMaxTimeWaitForIndividualsWithCollisions + "\t" + medianMaxTimeWaitForIndividualsWithCollisions + "\t" + maxWaitTimeForExperiment + "\r\n");
        Arrays.fill(values,0);
        Arrays.fill(valuesWoCollisions,0);
        Arrays.fill(allRunsCollisions,0);
        this.run=0;
        this.numberTimesOffload=0;
        this.waitTime=0;
        this.numberOfIndividualsWithCollisions = 0;
        this.waitTimes = new ArrayList<>();
        this.maxWaitTimes = new ArrayList<>();

        System.out.println("##################################################");
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
        return sb.toString();
    }


}
