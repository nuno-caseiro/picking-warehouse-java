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
import java.net.URL;
import java.nio.file.Path;
import java.util.*;


public class Experiment implements ExperimentListener  {

    private int seed;
    private int runs;
    private HashMap<String, Parameter> parameters;
    private GeneticAlgorithm geneticAlgorithm;
    private int countAllRuns;
    protected List<String> statisticsNames;
    protected List<ExperimentListener> statistics;
    private ExperimentsPanel experimentsPanel;



    public Experiment() {
        this.parameters= new LinkedHashMap<>();
        this.countAllRuns=1;
        this.statistics= new LinkedList<>();
        this.statisticsNames= new LinkedList<>();
        this.seed=1;
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
        addParameter("Collisions weight",experimentParametersPanel.getCollisionsWeightsValues());
        addParameter("NumAgents",experimentParametersPanel.getNumberAgentsValues());
        addParameter("NumPicks",experimentParametersPanel.getNumberPicksValues());

        runs = Integer.parseInt(getParameterValue("Runs"));

        countAllRuns=countAllRuns*runs;

        String[] statistics = experimentParametersPanel.getStatisticsValues().toString().trim().replaceAll("[\\[\\]\\(\\)]", "").split(",");
        for (String statistic : statistics) {
            if (statistic.equals("StatisticBestAverage")) {
                StatisticBestAverage statisticBestAverage = new StatisticBestAverage(runs, buildExperimentHeader());
                addExperimentListener(statisticBestAverage);
                this.statistics.add(statisticBestAverage);
            }

        }
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

        parameters.get(key).activeValueIndex++;
        if (i != 0 && parameters.get(key).activeValueIndex >= parameters.get(key).getNumberOfValues()) {
            parameters.get(key).activeValueIndex = 0;
            indicesManaging(--i);
        }
    }

    private GeneticAlgorithm buildRun(){
        //        String atualLayout = getParameterValue("WarehouseLayout");
//        HashMap<String,Object> warehousesLayout = (HashMap<String, Object>) experimentsPanel.getAvailableParameters().get("WarehouseLayout");
//        String file= (String) warehousesLayout.get(atualLayout);
//
//        File layout= new File(file);
//        Environment.getInstance().setDefaultWarehouseLayout(layout);
        Environment.getInstance().generateRandomLayout();

//        String atualPickFile = getParameterValue("Picks");
//        HashMap<String,Object> pickFile = (HashMap<String, Object>) experimentsPanel.getAvailableParameters().get("Picks");
//        String filePick= (String) pickFile.get(atualPickFile);

//        File pick= new File(filePick);

        runs = Integer.parseInt(getParameterValue("Runs"));
        int numAgents = Integer.parseInt(getParameterValue("NumAgents"));
        int numPicks = Integer.parseInt(getParameterValue("NumPicks"));

        Environment.getInstance().generateRandomPicks(this.seed, numPicks, numAgents, this.runs);

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

        for (ExperimentListener statistic : statistics) {
            ga.addGAListener((GAListener) statistic);
        }

        return ga;
    }

    public boolean hasMoreExperiments(){
        return parameters.get("Runs").activeValueIndex < parameters.get("Runs").getNumberOfValues();
    }

    public void run(){

        for (int i = 0; i < runs; i++) {
            geneticAlgorithm = buildRun();
            geneticAlgorithm.addGAListener(experimentsPanel);
            geneticAlgorithm.run();
            seed++;
        }
        this.seed=1;
        fireExperimentEnded();
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


    public int getCountAllRuns() {
        return countAllRuns;
    }

    final private List<ExperimentListener> listeners = new ArrayList<>(10);

    public synchronized void addExperimentListener(ExperimentListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void fireExperimentEnded() {
        for (ExperimentListener listener : listeners) {
            listener.experimentEnded();
        }
    }

    @Override
    public void experimentEnded() {

    }

    public void setExperimentsPanel(ExperimentsPanel experimentsPanel) {
        this.experimentsPanel=experimentsPanel;
    }
}
