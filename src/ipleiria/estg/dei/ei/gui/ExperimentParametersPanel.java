package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.IntegerTextField_KeyAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ExperimentParametersPanel extends PanelAtributesValue {
    private ExperimentsPanel mainPanel;

    private JTextArea numRunsTextField;
    private JLabel numRunsLabel;

    private JLabel populationSizesLabel;
    private List<String> populationSizes;
    private JTextArea populationSizesTextField;

    private JLabel maxGenerationsLabel;
    private List<String> maxGenerations;
    private JTextArea maxGenerationsTextField;

    private JLabel selectionMethodsLabel;
    private List<String> selectionMethods;
    private JTextArea selectionMethodsTextField;

    private JLabel tournamentSizeLabel;
    private List<String> tournamentSizeValues;
    private JTextArea tournamentSizeTextField;

    private JLabel selectivePressureLabel;
    private List<String> selectivePressureValues;
    private JTextArea selectivePressureTextField;

    private JLabel recombinationMethodsLabel;
    private List<String> recombinationMethods;
    private JTextArea recombinationMethodsTextField;

    private JLabel recombinationProbabilitiesLabel;
    private List<String> recombinationProbabilities;
    private JTextArea recombinationProbabilitiesTextField;

    private JLabel mutationMethodsLabel;
    private List<String> mutationMethods;
    private JTextArea mutationMethodsTextField;

    private JLabel mutationProbabilitiesLabel;
    private List<String> mutationProbabilities;
    private JTextArea mutationProbabilitiesTextField;

    private JLabel timeWeightsLabel;
    private List<String> timeWeightValues;
    private JTextArea timeWeightsTextField;

    private JLabel collisionsWeightsLabel;
    private List<String> collisionsWeightsValues;
    private JTextArea collisionsWeightsTextField;

    private JLabel statisticsSelection;
    private List<String> statisticsValues;
    private JTextArea statisticsTextField;

    private JLabel numberAgents;
    private List<String> numberAgentsValues;
    private JTextArea numberAgentsTextField;

    private JLabel numberPicks;
    private List<String> numberPicksValues;
    private JTextArea numberPicksTextField;

    public ExperimentParametersPanel(ExperimentsPanel mainPanel) {

        this.mainPanel=mainPanel;
        setLayout(new GridBagLayout());

        initVars();

        this.labels.add(numRunsLabel);
        numRunsTextField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 0)));
        JScrollPane numRunsScrollPane = new JScrollPane(numRunsTextField);

        this.valueComponents.add(numRunsScrollPane);

        numRunsTextField.addKeyListener(new IntegerTextField_KeyAdapter(null));

        addComponents(2,populationSizesLabel,populationSizesTextField,populationSizes,"100", null);
        addComponents(3,maxGenerationsLabel,maxGenerationsTextField,maxGenerations,"100", null);
        addComponents(4,selectionMethodsLabel,selectionMethodsTextField,selectionMethods,"Tournament", null);
        addComponents(5,tournamentSizeLabel,tournamentSizeTextField, tournamentSizeValues,"4", null);
        addComponents(6,selectivePressureLabel,selectivePressureTextField, selectivePressureValues,"1.0", null);
        addComponents(7,recombinationMethodsLabel,recombinationMethodsTextField, recombinationMethods,"PMX", null);
        addComponents(8,recombinationProbabilitiesLabel,recombinationProbabilitiesTextField, recombinationProbabilities,"0.7", null);
        addComponents(9,mutationMethodsLabel,mutationMethodsTextField, mutationMethods,"Insert", null);
        addComponents(10,mutationProbabilitiesLabel,mutationProbabilitiesTextField, mutationProbabilities,"0.3", null);
        addComponents(11,timeWeightsLabel,timeWeightsTextField, timeWeightValues,"1", null);
        addComponents(12,collisionsWeightsLabel, collisionsWeightsTextField, collisionsWeightsValues,"1", null);
        addComponents(13,statisticsSelection, statisticsTextField, statisticsValues,"StatisticBestAverage", null);
        addComponents(14, numberAgents, numberAgentsTextField, numberAgentsValues,"3", null);
        addComponents(15, numberPicks, numberPicksTextField, numberPicksValues,"45", null);

        configure();
    }

    private void addComponents(int parameterId, JLabel label, JTextArea textField, List<String> values, String defaultValue, JScrollPane scroll ){
        this.labels.add(label);
        values.add(defaultValue);
        textField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 0)));
        textField.setEditable(false);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showEditParameters(parameterId, values);
            }
        });
        scroll=new JScrollPane(textField);
        this.valueComponents.add(scroll);
    }

    private void initVars() {
        numRunsTextField = new JTextArea("30",1,25);
        numRunsLabel = new JLabel("Number of runs:");

        populationSizesLabel = new JLabel("Population sizes:");
        populationSizes = new LinkedList<>();
        populationSizesTextField = new JTextArea("100",1,25);

        maxGenerationsLabel = new JLabel("Max generations:");
        maxGenerations = new LinkedList<>();
        maxGenerationsTextField = new JTextArea("100",1,25);

        selectionMethodsLabel= new JLabel("Selection methods:");
        selectionMethods= new LinkedList<>();
        selectionMethodsTextField = new JTextArea("Tournament",1,25);

        tournamentSizeLabel= new JLabel("Tournament sizes:");
        tournamentSizeValues = new LinkedList<>();
        tournamentSizeTextField = new JTextArea("4",1,25);

        selectivePressureLabel= new JLabel("Selective pressure values:");
        selectivePressureValues = new LinkedList<>();
        selectivePressureTextField = new JTextArea("1",1,25);

        recombinationMethodsLabel= new JLabel("Recombination methods:");
        recombinationMethods = new LinkedList<>();
        recombinationMethodsTextField = new JTextArea("PMX",1,25);

        recombinationProbabilitiesLabel= new JLabel("Recombination probabilities:");
        recombinationProbabilities = new LinkedList<>();
        recombinationProbabilitiesTextField = new JTextArea("0.7",1,25);

        mutationMethodsLabel= new JLabel("Mutation methods:");
        mutationMethods = new LinkedList<>();
        mutationMethodsTextField = new JTextArea("Insert",1,25);

        mutationProbabilitiesLabel= new JLabel("Mutation probabilities:");
        mutationProbabilities = new LinkedList<>();
        mutationProbabilitiesTextField = new JTextArea("0.3",1,25);

        timeWeightsLabel= new JLabel("Time weights:");
        timeWeightValues = new LinkedList<>();
        timeWeightsTextField = new JTextArea("1",1,25);

        collisionsWeightsLabel= new JLabel("Collision weights:");
        collisionsWeightsValues = new LinkedList<>();
        collisionsWeightsTextField = new JTextArea("1",1,25);

        statisticsSelection= new JLabel("Statistics: ");
        statisticsValues= new LinkedList<>();
        statisticsTextField = new JTextArea("StatisticBestAverage",1, 25);

        numberAgents = new JLabel("Number of Agents ");
        numberAgentsValues = new LinkedList<>();
        numberAgentsTextField = new JTextArea("3",1, 25);

        numberPicks = new JLabel("Number of picks: ");
        numberPicksValues = new LinkedList<>();
        numberPicksTextField = new JTextArea("45",1, 25);


        this.setBorder(new EmptyBorder(0,5,0,5));//top,left,bottom,right

    }

    public void showEditParameters(int panelId, List<String> values) {
            mainPanel.showEditParameters(panelId, values);
    }

    public void updateTextFieldParameters(int panelId){

            switch (panelId){
                case 1:
                    break;
                case 2:
                    populationSizesTextField.setText(listsToString(populationSizes));
                    break;
                case 3:
                    maxGenerationsTextField.setText(listsToString(maxGenerations));
                    break;
                case 4:
                    selectionMethodsTextField.setText(listsToString(selectionMethods));
                    break;
                case 5:
                    tournamentSizeTextField.setText(listsToString(tournamentSizeValues));
                    break;
                case 6:
                    selectivePressureTextField.setText(listsToString(selectivePressureValues));
                    break;
                case 7:
                    recombinationMethodsTextField.setText(listsToString(recombinationMethods));
                    break;
                case 8:
                    recombinationProbabilitiesTextField.setText(listsToString(recombinationProbabilities));
                    break;
                case 9:
                    mutationMethodsTextField.setText(listsToString(mutationMethods));
                    break;
                case 10:
                    mutationProbabilitiesTextField.setText(listsToString(mutationProbabilities));
                    break;
                case 11:
                    timeWeightsTextField.setText(listsToString(timeWeightValues));
                    break;
                case 12:
                    collisionsWeightsTextField.setText(listsToString(collisionsWeightsValues));
                    break;
                case 13:
                    statisticsTextField.setText(listsToString(statisticsValues));
                    break;
                case 14:
                    numberAgentsTextField.setText(listsToString(numberAgentsValues));
                    break;
                case 15:
                    numberPicksTextField.setText(listsToString(numberPicksValues));
                    break;
            }
    }

    public void hideParameters(){
        mainPanel.hideEditParameters();
    }

    private String listsToString(List<String> list){
        StringBuilder sb = new StringBuilder();
        int i=0;
        for (String o : list) {
            sb.append(o);
            if(list.size()>1 && i!=list.size()-1){
                sb.append(", ");
            }
            i++;
        }
        return sb.toString();
    }

    public JTextArea getNumRunsTextField() {
        return numRunsTextField;
    }

    public List<String> getPopulationSizes() {
        return populationSizes;
    }

    public List<String> getMaxGenerations() {
        return maxGenerations;
    }

    public List<String> getSelectionMethods() {
        return selectionMethods;
    }

    public List<String> getTournamentSizeValues() {
        return tournamentSizeValues;
    }

    public List<String> getSelectivePressureValues() {
        return selectivePressureValues;
    }

    public List<String> getRecombinationMethods() {
        return recombinationMethods;
    }

    public List<String> getRecombinationProbabilities() {
        return recombinationProbabilities;
    }

    public List<String> getMutationMethods() {
        return mutationMethods;
    }

    public List<String> getMutationProbabilities() {
        return mutationProbabilities;
    }

    public List<String> getTimeWeightValues() {
        return timeWeightValues;
    }

    public List<String> getCollisionsWeightsValues() {
        return collisionsWeightsValues;
    }

    public List<String> getStatisticsValues() {
        return statisticsValues;
    }

    public List<String> getNumberAgentsValues() {
        return numberAgentsValues;
    }

    public List<String> getNumberPicksValues() {
        return numberPicksValues;
    }
}
