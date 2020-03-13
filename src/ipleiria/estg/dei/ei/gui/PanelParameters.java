package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.geneticOperators.*;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.RankBased;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.SelectionMethod;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.selectionMethods.Tournament;

import javax.swing.*;
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
    public static final String PROB_RECOMBINATION= "0.7";
    public static final String PROB_MUTATION= "0.2";
    private MainFrame mainFrame;

    JTextField textFieldSeed = new JTextField(SEED,TEXT_FIELD_LENGHT);
    JTextField textFieldN = new JTextField(POPULATION_SIZE,TEXT_FIELD_LENGHT);
    JTextField textFieldGenerations = new JTextField(GENERATIONS,TEXT_FIELD_LENGHT);

    String[] selectionMethods= {"Tournament", "Rank"};
    JComboBox comboBoxSelectionMethods = new JComboBox(selectionMethods);
    JTextField textFieldTournamentSize = new JTextField(TOURNMENT_SIZE,TEXT_FIELD_LENGHT);

    String[] recombinationMethods = {"CX", "DX", "OX", "PMX"};
    JComboBox comboBoxRecombinationMethods = new JComboBox(recombinationMethods);
    JTextField textFieldProbRecombination = new JTextField(PROB_RECOMBINATION,TEXT_FIELD_LENGHT);

    String[] mutationMethods={"Insert", "Inversion", "Scramble"};
    JComboBox comboBoxMutationMethods = new JComboBox(mutationMethods);
    JTextField textFieldProbMutation = new JTextField(PROB_MUTATION,TEXT_FIELD_LENGHT);

    String[] methodsSearch={"A*"};
    JComboBox comboBoxSearch = new JComboBox(methodsSearch);

    public PanelParameters(MainFrame mf){

        this.mainFrame=mf;
        title = "Genetic algorithm parameters";

        labels.add(new JLabel("Seed: "));
        valueComponents.add(textFieldSeed);
        textFieldSeed.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Population size: "));
        valueComponents.add(textFieldN);
        textFieldN.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("# of generations: "));
        valueComponents.add(textFieldGenerations);
        textFieldGenerations.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Selection method: "));
        valueComponents.add(comboBoxSelectionMethods);
        comboBoxSelectionMethods.addActionListener(new JComboBoxSelectionMethods_ActionAdapter(this));

        labels.add(new JLabel("Tournament size: "));
        valueComponents.add(textFieldTournamentSize);
        textFieldTournamentSize.addKeyListener(new IntegerTextField_KeyAdapter(null));

        labels.add(new JLabel("Recombination method: "));
        valueComponents.add(comboBoxRecombinationMethods);

        labels.add(new JLabel("Recombination prob.: "));
        valueComponents.add(textFieldProbRecombination);

        labels.add(new JLabel("Mutation method: "));
        valueComponents.add(comboBoxMutationMethods);

        labels.add(new JLabel("Mutation prob.: "));
        valueComponents.add(textFieldProbMutation);

        labels.add(new JLabel("Search Methods: "));
        valueComponents.add(comboBoxSearch);
        comboBoxSearch.addActionListener(new JComboBoxSearch_ActionAdapter(this));

        mainFrame.manageButtons(true,false,false,false,false,false,false,false);

        configure();
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
                return new RankBased(Integer.parseInt(textFieldN.getText()));
        }
        return null;
    }


    public Recombination getRecombinationMethod() {

        double recombinationProb = Double.parseDouble(textFieldProbRecombination.getText());

        switch (comboBoxRecombinationMethods.getSelectedIndex()) {
            case 0:
                return new RecombinationCX(recombinationProb);
            case 1:
                return new RecombinationDX(recombinationProb);
            case 2:
                return new RecombinationOX(recombinationProb);
            case 3:
                return new RecombinationPartialMapped(recombinationProb);
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
    }

    public void actionPerformedSearch(ActionEvent actionEvent) {
        mainFrame.cleanBoards();
    }
}

class IntegerTextField_KeyAdapter implements KeyListener{

    final private MainFrame adaptee;

    IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        char c = keyEvent.getKeyChar();
        if(!Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE){
            keyEvent.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}

class JComboBoxSelectionMethods_ActionAdapter implements ActionListener{

    final private PanelParameters adaptee;

    public JComboBoxSelectionMethods_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.actionPerformedSelectionMethods(actionEvent);
    }
}

class JComboBoxSearch_ActionAdapter implements ActionListener{

    final private PanelParameters adaptee;

    public JComboBoxSearch_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.actionPerformedSearch(actionEvent);
    }
}
