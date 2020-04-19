package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.IntegerTextField_KeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class ExperimentParametersPanel extends PanelAtributesValue {
    private ExperimentsPanel mainPanel;

    private JTextField numRunsTextField;
    private JLabel numRunsLabel;

    private JLabel populationSizesLabel;
    private List<String> populationSizes;
    private JTextField populationSizesTextField;

    private JLabel maxGenerationsLabel;
    private List<String> maxGenerations;
    private JTextField maxGenerationsTextField;

    private JLabel selectionMethodsLabel;
    private List<String> selectionMethods;
    private JTextField selectionMethodsTextField;

    private JLabel tournamentSizeLabel;
    private List<String> tournamentSizeValues;
    private JTextField tournamentSizeTextField;

    private JLabel selectivePressureLabel;
    private List<String> selectivePressureValues;
    private JTextField selectivePressureTextField;

    private JLabel recombinationMethodsLabel;
    private List<String> recombinationMethods;
    private JTextField recombinationMethodsTextField;

    private JLabel recombinationProbabilitiesLabel;
    private List<String> recombinationProbabilities;
    private JTextField recombinationProbabilitiesTextField;

    private JLabel mutationMethodsLabel;
    private List<String> mutationMethods;
    private JTextField mutationMethodsTextField;

    private JLabel mutationProbabilitiesLabel;
    private List<String> mutationProbabilities;
    private JTextField mutationProbabilitiesTextField;

    private JLabel timeWeightsLabel;
    private List<String> timeWeightValues;
    private JTextField timeWeightsTextField;

    private JLabel collisionsWeightsLabel;
    private List<String> collisionsWeightsValues;
    private JTextField collisionsWeightsTextField;

    int isEditShowing= 0;

    public ExperimentParametersPanel(ExperimentsPanel mainPanel) {

        this.mainPanel=mainPanel;
        setLayout(new GridBagLayout());

        initVars();

        this.labels.add(numRunsLabel);
        this.valueComponents.add(numRunsTextField);
        numRunsTextField.addKeyListener(new IntegerTextField_KeyAdapter(null));

        addComponents(2,populationSizesLabel,populationSizesTextField,populationSizes,"100");
        addComponents(3,maxGenerationsLabel,maxGenerationsTextField,maxGenerations,"50");
        addComponents(4,selectionMethodsLabel,selectionMethodsTextField,selectionMethods,"Tournament");
        addComponents(5,tournamentSizeLabel,tournamentSizeTextField, tournamentSizeValues,"4");
        addComponents(6,selectivePressureLabel,selectivePressureTextField, selectivePressureValues,"1.0");
        addComponents(7,recombinationMethodsLabel,recombinationMethodsTextField, recombinationMethods,"PMX");
        addComponents(8,recombinationProbabilitiesLabel,recombinationProbabilitiesTextField, recombinationProbabilities,"0.7");
        addComponents(9,mutationMethodsLabel,mutationMethodsTextField, mutationMethods,"Insert");
        addComponents(10,mutationProbabilitiesLabel,mutationProbabilitiesTextField, mutationProbabilities,"0.3");
        addComponents(11,timeWeightsLabel,timeWeightsTextField, timeWeightValues,"1");
        addComponents(12,collisionsWeightsLabel, collisionsWeightsTextField, collisionsWeightsValues,"1");

        configure();
    }

    private void addComponents(int parameterId, JLabel label, JTextField textField, List<String> values, String defaultValue ){
        this.labels.add(label);
        this.valueComponents.add(textField);
        values.add(defaultValue);
        textField.setEditable(false);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                showEditParameters(parameterId, values, textField.getLocationOnScreen());
            }
        });

    }

    private void initVars() {
        numRunsTextField = new JTextField("30",7);
        numRunsLabel = new JLabel("Number of runs:");

        populationSizesLabel = new JLabel("Population sizes:");
        populationSizes = new LinkedList<>();
        populationSizesTextField = new JTextField("100",7);

        maxGenerationsLabel = new JLabel("Max generations:");
        maxGenerations = new LinkedList<>();
        maxGenerationsTextField = new JTextField("100",7);

        selectionMethodsLabel= new JLabel("Selection methods:");
        selectionMethods= new LinkedList<>();
        selectionMethodsTextField = new JTextField("Tournament",20);

        tournamentSizeLabel= new JLabel("Tournament sizes:");
        tournamentSizeValues = new LinkedList<>();
        tournamentSizeTextField = new JTextField("4",7);

        selectivePressureLabel= new JLabel("Selective pressure values:");
        selectivePressureValues = new LinkedList<>();
        selectivePressureTextField = new JTextField("1",7);

        recombinationMethodsLabel= new JLabel("Recombination methods:");
        recombinationMethods = new LinkedList<>();
        recombinationMethodsTextField = new JTextField("PMX",7);

        recombinationProbabilitiesLabel= new JLabel("Recombination probabilities:");
        recombinationProbabilities = new LinkedList<>();
        recombinationProbabilitiesTextField = new JTextField("0.7",7);

        mutationMethodsLabel= new JLabel("Mutation methods:");
        mutationMethods = new LinkedList<>();
        mutationMethodsTextField = new JTextField("Insert",7);

        mutationProbabilitiesLabel= new JLabel("Mutation probabilities:");
        mutationProbabilities = new LinkedList<>();
        mutationProbabilitiesTextField = new JTextField("0.3",7);

        timeWeightsLabel= new JLabel("Time weights:");
        timeWeightValues = new LinkedList<>();
        timeWeightsTextField = new JTextField("1",7);

        collisionsWeightsLabel= new JLabel("Collision weights:");
        collisionsWeightsValues = new LinkedList<>();
        collisionsWeightsTextField = new JTextField("1",7);
    }

    public void showEditParameters(int panelId, List values, Point location) {
        if(isEditShowing==0){
            isEditShowing=1;
            mainPanel.showEditParameters(panelId, values, location);
        }
    }

    public void reverseEditing(){
            isEditShowing=0;
    }

    public void hideEditParameters(int panelId){
        if(isEditShowing==1){

            mainPanel.hideEditParameters();

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
            }
            isEditShowing=0;
        }
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

    public JTextField getNumRunsTextField() {
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
}
