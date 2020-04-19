package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators.*;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.RankBased;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.SelectionMethod;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.Tournament;
import ipleiria.estg.dei.ei.utils.IntegerTextField_KeyAdapter;
import ipleiria.estg.dei.ei.utils.JComboBoxSelectionMethods_ActionAdapter;
import ipleiria.estg.dei.ei.utils.RankTextField_KeyAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelParameters extends PanelAtributesValue {

    public static final int TEXT_FIELD_LENGHT = 7;
    public static final String SEED= "1";
    public static final String POPULATION_SIZE= "100";
    public static final String GENERATIONS= "100";
    public static final String TOURNMENT_SIZE= "4";
    public static final String SELECTIVE_PRESSURE= "2";
    public static final String PROB_RECOMBINATION= "0.7";
    public static final String PROB_MUTATION= "0.2";
    public static final String TIME_WEIGHT= "1";
    public static final String COLLISIONS_WEIGHT= "1";
    private MainFrame mainFrame;

    JTextField textFieldSeed = new JTextField(SEED,TEXT_FIELD_LENGHT);
    JTextField textFieldN = new JTextField(POPULATION_SIZE,TEXT_FIELD_LENGHT);
    JTextField textFieldGenerations = new JTextField(GENERATIONS,TEXT_FIELD_LENGHT);
    JTextField textFieldTimeWeight = new JTextField(TIME_WEIGHT,TEXT_FIELD_LENGHT);
    JTextField textFieldCollisionsWeight = new JTextField(COLLISIONS_WEIGHT,TEXT_FIELD_LENGHT);

    String[] selectionMethods= {"Tournament", "Rank"};
    JComboBox comboBoxSelectionMethods = new JComboBox(selectionMethods);
    JTextField textFieldTournamentSize = new JTextField(TOURNMENT_SIZE,TEXT_FIELD_LENGHT);
    JTextField textFieldSelectivePressure = new JTextField(SELECTIVE_PRESSURE,TEXT_FIELD_LENGHT);

    String[] recombinationMethods = {"PMX", "OX" , "OX1", "DX" , "CX"};
    JComboBox comboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField textFieldProbRecombination = new JTextField(PROB_RECOMBINATION,TEXT_FIELD_LENGHT);

    String[] mutationMethods={"Insert", "Inversion", "Scramble"};
    JComboBox comboBoxMutationMethods = new JComboBox(mutationMethods);
    JTextField textFieldProbMutation = new JTextField(PROB_MUTATION,TEXT_FIELD_LENGHT);

    public PanelParameters(MainFrame mf){

        this.mainFrame=mf;
        title = "Genetic algorithm parameters";

        labels.add(new JLabel("Seed: "));
        valueComponents.add(textFieldSeed);
        textFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));
        //textFieldSeed.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("Population size: "));
        valueComponents.add(textFieldN);
        textFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));
        //textFieldN.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("# of generations: "));
        valueComponents.add(textFieldGenerations);
        textFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));
        //textFieldGenerations.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("Selection method: "));
        valueComponents.add(comboBoxSelectionMethods);
        comboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));
        //((JLabel)comboBoxSelectionMethods.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);

        labels.add(new JLabel("Tournament size: "));
        valueComponents.add(textFieldTournamentSize);
        textFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));
        //textFieldTournamentSize.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("Selective pressure: "));
        valueComponents.add(textFieldSelectivePressure);
        textFieldSelectivePressure.addKeyListener(new RankTextField_KeyAdapter(null));
        //textFieldSelectivePressure.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("Recombination method: "));
        valueComponents.add(comboBoxRecombinationMethods);
        //((JLabel)comboBoxRecombinationMethods.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);

        labels.add(new JLabel("Recombination prob.: "));
        valueComponents.add(textFieldProbRecombination);
        //textFieldProbRecombination.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("Mutation method: "));
        valueComponents.add(comboBoxMutationMethods);
        //((JLabel)comboBoxMutationMethods.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);


        labels.add(new JLabel("Mutation prob.: "));
        valueComponents.add(textFieldProbMutation);
        //textFieldProbMutation.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("Time Weight: "));
        valueComponents.add(textFieldTimeWeight);
        textFieldSelectivePressure.addKeyListener(new RankTextField_KeyAdapter(null));
        //textFieldTimeWeight.setHorizontalAlignment(JTextField.RIGHT);

        labels.add(new JLabel("Collisions Weight: "));
        valueComponents.add(textFieldCollisionsWeight);
        textFieldSelectivePressure.addKeyListener(new RankTextField_KeyAdapter(null));
        //textFieldCollisionsWeight.setHorizontalAlignment(JTextField.RIGHT);

        textFieldTournamentSize.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 0);
        textFieldSelectivePressure.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 1);

        configure();
    }

    public JTextField getTextFieldTimeWeight() {
        return textFieldTimeWeight;
    }

    public JTextField getTextFieldCollisionsWeight() {
        return textFieldCollisionsWeight;
    }

    public JTextField getTextFieldSeed() {
        return textFieldSeed;
    }

    public JTextField getTextFieldN() {
        return textFieldN;
    }

    public JTextField getTextFieldGenerations() {
        return textFieldGenerations;
    }

    public SelectionMethod getSelectionMethod() {

        switch (comboBoxSelectionMethods.getSelectedIndex()) {
            case 0:
                return new Tournament(Integer.parseInt(textFieldN.getText()), Integer.parseInt(textFieldTournamentSize.getText()));
            case 1:
                return new RankBased(Integer.parseInt(textFieldN.getText()),Double.parseDouble(textFieldSelectivePressure.getText()));
        }
        return null;
    }


    public Recombination getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(textFieldProbRecombination.getText());

        switch (comboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationPartialMapped(recombinationProb);
            case 1:
                return new RecombinationOX(recombinationProb);
            case 2:
                return new RecombinationOX1(recombinationProb);
            case 3:
                return new RecombinationDX(recombinationProb);
            case 4:
                return new RecombinationCX(recombinationProb);
        }

        return null;
    }

    public Mutation getMutationMethod() {
        double mutationProbability = Double.parseDouble(textFieldProbMutation.getText());
        switch (comboBoxMutationMethods.getSelectedIndex()) {
            case 0:
                return new MutationInsert(mutationProbability);
            case 1:
                return new MutationInversion(mutationProbability);
            case 2:
                return new MutationScramble(mutationProbability);
        }
        return null;
    }

    public void actionPerformedSelectionMethods(ActionEvent actionEvent) {
        textFieldTournamentSize.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 0);
        textFieldSelectivePressure.setEnabled(comboBoxSelectionMethods.getSelectedIndex() == 1);
    }

    public String[] getSelectionMethods() {
        return selectionMethods;
    }

    public JComboBox getComboBoxSelectionMethods() {
        return comboBoxSelectionMethods;
    }

    public JTextField getTextFieldTournamentSize() {
        return textFieldTournamentSize;
    }

    public String[] getRecombinationMethods() {
        return recombinationMethods;
    }

    public JComboBox getComboBoxRecombinationMethods() {
        return comboBoxRecombinationMethods;
    }

    public JTextField getTextFieldProbRecombination() {
        return textFieldProbRecombination;
    }

    public String[] getMutationMethods() {
        return mutationMethods;
    }

    public JComboBox getComboBoxMutationMethods() {
        return comboBoxMutationMethods;
    }

    public JTextField getTextFieldProbMutation() {
        return textFieldProbMutation;
    }

    public JTextField getTextFieldSelectivePressure() {
        return textFieldSelectivePressure;
    }
}

