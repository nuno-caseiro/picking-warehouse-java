package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;


public class PanelGeneticAlgorithmHistory extends JPanel {

    private PanelTextArea gaHistoryTextArea;

    public PanelGeneticAlgorithmHistory() {
        setLayout(new BorderLayout());
        gaHistoryTextArea = new PanelTextArea("Genetic algorithm history: ",15,40);
        this.add(gaHistoryTextArea, BorderLayout.CENTER);

    }


    public void appendParametersOfRun(Individual bestInRun, PanelParameters panelParameters) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        gaHistoryTextArea.textArea.append(timestamp+"\n");
        gaHistoryTextArea.textArea.append(bestInRun.toString()+"\n");
        gaHistoryTextArea.textArea.append("Seed: "+ panelParameters.getTextFieldSeed().getText()+"\n");
        gaHistoryTextArea.textArea.append("Population size: "+ panelParameters.getTextFieldN().getText()+"\n");
        gaHistoryTextArea.textArea.append("Number of generations: "+ panelParameters.getTextFieldGenerations().getText()+"\n");
        gaHistoryTextArea.textArea.append("Selection method: "+ panelParameters.getComboBoxSelectionMethods().getSelectedItem().toString()+"\n");
        if(panelParameters.getTextFieldTournamentSize().isEnabled()){
        gaHistoryTextArea.textArea.append("Tournament size: "+ panelParameters.getTextFieldTournamentSize().getText()+"\n");
        }
        if(panelParameters.getTextFieldSelectivePressure().isEnabled()){
        gaHistoryTextArea.textArea.append("Selective pressure: "+ panelParameters.getTextFieldSelectivePressure().getText()+"\n");
        }
        gaHistoryTextArea.textArea.append("Recombination method: "+ panelParameters.getComboBoxRecombinationMethods().getSelectedItem().toString()+"\n");
        gaHistoryTextArea.textArea.append("Recombination probability: "+ panelParameters.getTextFieldProbRecombination().getText()+"\n");
        gaHistoryTextArea.textArea.append("Mutation method: "+ panelParameters.getComboBoxMutationMethods().getSelectedItem().toString()+"\n");
        gaHistoryTextArea.textArea.append("Mutation probability: "+ panelParameters.getTextFieldProbMutation().getText()+"\n");
        gaHistoryTextArea.textArea.append("\n");

    }
}
