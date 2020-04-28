package ipleiria.estg.dei.ei.model.experiments;

import ipleiria.estg.dei.ei.gui.ExperimentParametersPanel;
import ipleiria.estg.dei.ei.gui.ExperimentsPanel;
import ipleiria.estg.dei.ei.gui.PanelParameters;
import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators.*;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.RankBased;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.SelectionMethod;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.Tournament;
import ipleiria.estg.dei.ei.utils.FileOperations;
import ipleiria.estg.dei.ei.utils.Maths;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Experiment implements GAListener {

    private int seed;
    private int runs;
    private HashMap<String, Parameter> parameters;
    private GeneticAlgorithm geneticAlgorithm;
    private double[] allRunsFitness;
    private double[] allRunsCollisions;
    private int countAllRuns;
    private ExperimentsPanel experimentsPanel;

    public Experiment() throws FileNotFoundException {
        this.parameters= new LinkedHashMap<>();
        this.countAllRuns=1;
    }

    public void readParameterFile() throws FileNotFoundException {
        parameters.clear();
        File file = new File("/Users/nunocaseiro/Projeto/experimentsDataSets/config_DataSet1.txt");
        Scanner scanner= new Scanner(file);

        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] split = s.split(":|,");
            String[] values = new String[split.length];
            for (int i = 1; i < split.length; i++) {
                values[i-1]= split[i].trim();
            }
            Parameter parameter = new Parameter(split[0],values);
            parameters.put(split[0],parameter);
        }

        runs = Integer.parseInt(getParameterValue("Runs"));
        allRunsFitness= new double[runs];
    }

    public void readParametersValues(ExperimentParametersPanel experimentParametersPanel){
        parameters.clear();
        String[] values = new String[1];
        values[0]= experimentParametersPanel.getNumRunsTextField().getText();
        Parameter parameter = new Parameter("Runs",values);
        parameters.put("Runs",parameter);

        addParameter("Population size",experimentParametersPanel.getPopulationSizes());
        addParameter("Max generations",experimentParametersPanel.getMaxGenerations());
        addParameter("Selection",experimentParametersPanel.getSelectionMethods());
        addParameter("Tournament size",experimentParametersPanel.getTournamentSizeValues());
        addParameter("Selective pressure",experimentParametersPanel.getSelectivePressureValues());
        addParameter("Recombination",experimentParametersPanel.getRecombinationMethods());
        addParameter("Recombination probability",experimentParametersPanel.getRecombinationProbabilities());
        addParameter("Mutation",experimentParametersPanel.getMutationMethods());
        addParameter("Mutation probability",experimentParametersPanel.getMutationProbabilities());
        addParameter("Time weight",experimentParametersPanel.getTimeWeightValues());
        addParameter("Collisions weight",experimentParametersPanel.getTimeWeightValues());

        runs = Integer.parseInt(getParameterValue("Runs"));
        allRunsFitness= new double[runs];
        allRunsCollisions= new double[runs];

        countAllRuns=countAllRuns*runs;
    }


    public void addParameter(String keyName,List<String> listToAdd){
        String[] values= new String[listToAdd.size()];
        for (int i = 0; i < listToAdd.size(); i++) {
            values[i]=listToAdd.get(i);
        }
        Parameter parameter= new Parameter(keyName,values);
        parameters.put(keyName,parameter);
        countAllRuns*=listToAdd.size();
    }

    public void indicesManaging(int i){
        String key=null;
        String[] keys= parameters.keySet().toArray(new String[0]);
        key=keys[i];

       /* Iterator iterator= parameters.keySet().iterator();
        iterator.next();

        while(index!=i){
            index++;
            key = iterator.next().toString();
        }*/

        parameters.get(key).activeValueIndex++;
        if (i != 0 && parameters.get(key).activeValueIndex >= parameters.get(key).getNumberOfValues()) {
            parameters.get(key).activeValueIndex = 0;
            indicesManaging(--i);
        }

    }

    private GeneticAlgorithm buildRun(){
        runs = Integer.parseInt(getParameterValue("Runs"));
        int populationSize = Integer.parseInt(getParameterValue("Population size"));
        int maxGenerations = Integer.parseInt(getParameterValue("Max generations"));
        SelectionMethod selection = null;
        Recombination recombination = null;
        Mutation mutation= null;
        int timeWeight = 1;
        int collisionsWeight = 1;

        if (getParameterValue("Selection").equals("Tournament")) {
            int tournamentSize = Integer.parseInt(getParameterValue("Tournament size"));
            selection = new Tournament(populationSize, tournamentSize);
        }else{
            double selectivePressure= Double.parseDouble(getParameterValue("Selective pressure"));
            selection = new RankBased(populationSize,selectivePressure);
        }

        //RECOMBINATION
        double recombinationProbability = Double.parseDouble(getParameterValue("Recombination probability"));
        switch (getParameterValue("Recombination")) {
            case "PMX":
                recombination = new RecombinationPartialMapped(recombinationProbability);
                break;
            case "OX1":
                recombination = new RecombinationOX1(recombinationProbability);
                break;
            case "OX":
                recombination = new RecombinationOX(recombinationProbability);
                break;
            case "DX":
                recombination = new RecombinationDX(recombinationProbability);
                break;
            case "CX":
                recombination = new RecombinationCX(recombinationProbability);
                break;
        }

        //MUTATION
        double mutationProbability = Double.parseDouble(getParameterValue("Mutation probability"));
        switch (getParameterValue("Mutation")) {
            case "Insert":
                mutation = new MutationInsert(mutationProbability);
                break;
            case "Scramble":
                mutation = new MutationScramble(mutationProbability);
                break;
            case "Inversion":
                mutation = new MutationInversion(mutationProbability);
                break;
        }

        timeWeight= Integer.parseInt(getParameterValue("Time weight"));
        collisionsWeight= Integer.parseInt(getParameterValue("Collisions weight"));

        Random random = new Random(seed);

        GeneticAlgorithm ga = new GeneticAlgorithm(populationSize,maxGenerations,selection,recombination,mutation, Environment.getInstance().getNumberOfAgents(),Environment.getInstance().getNumberOfPicks(),random);
        Environment.getInstance().setTimeWeight(timeWeight);
        Environment.getInstance().setCollisionsWeight(collisionsWeight);

        return ga;
    }

    public boolean hasMoreExperiments(){
        return parameters.get("Runs").activeValueIndex < parameters.get("Runs").getNumberOfValues();
    }

    public void run(){

        Arrays.fill(allRunsFitness, 0.0);
        Arrays.fill(allRunsCollisions, 0.0);

        for (int i = 0; i < runs; i++) {
            geneticAlgorithm = buildRun();
            geneticAlgorithm.addGAListener(this);
            geneticAlgorithm.addGAListener(experimentsPanel);
            geneticAlgorithm.run();
            runEnded(i);
            System.out.println("Done "+geneticAlgorithm.getAverageFitness());
            seed++;
        }
        experimentEnded();
        //Fire experiment ended
        System.out.println("---------TERMINOU-------");
    }

    private void runEnded(int i) {
        allRunsFitness[i]=geneticAlgorithm.getAverageFitness();
        allRunsCollisions[i]=geneticAlgorithm.getBestInRun().getNumberOfCollisions();
    }

    private void experimentEnded(){
        File file = new File("statistic_average_fitness.xls");
        if(!file.exists()){
            FileOperations.appendToTextFile("statistic_average_fitness.xls", buildExperimentHeader() + "\t" + "Average:" + "\t" + "StdDev:" + "\t" + "Collisions average" + "\t" + "Collisions stdDev" + "\r\n");
        }

        double average = Maths.average(allRunsFitness);
        double stdDeviation= Maths.standardDeviation(allRunsFitness,average);
        double collisionsAverage = Maths.average(allRunsCollisions);
        double collisionStdDeviation = Maths.average(allRunsCollisions);

        FileOperations.appendToTextFile("statistic_average_fitness.xls", buildExperimentValues() + "\t" + average + "\t" + stdDeviation + "\t" + collisionsAverage + "\t" + collisionStdDeviation + "\r\n");
    }

    protected String getParameterValue(String parameterName){
        if(parameters.get(parameterName)!=null){
            return parameters.get(parameterName).getActiveValue();
        }
        return null;
    }

    public HashMap<String, Parameter> getParameters() {
        return parameters;
    }

    private String buildExperimentHeader() {
        StringBuilder sb = new StringBuilder();
        sb.append("Population size:" + "\t");
        sb.append("Max generations:" + "\t");
        sb.append("Selection:" + "\t");
        sb.append("Recombination:" + "\t");
        sb.append("Recombination prob.:" + "\t");
        sb.append("Mutation:" + "\t");
        sb.append("Mutation prob.:" + "\t");
        sb.append("Time weight:" + "\t");
        sb.append("Collisions weight:" + "\t");
        return sb.toString();
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

    public int getCountAllRuns() {
        return countAllRuns;
    }

    @Override
    public void generationEnded(GeneticAlgorithm e) {

    }

    @Override
    public void runEnded(GeneticAlgorithm e) {

    }

    public void setExperimentsPanel(ExperimentsPanel experimentsPanel) {
        this.experimentsPanel=experimentsPanel;

    }
}
