package ipleiria.estg.dei.ei.controller;

import ipleiria.estg.dei.ei.gui.MainFrame;
import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.search.AStar;
import ipleiria.estg.dei.ei.model.search.Action;
import ipleiria.estg.dei.ei.model.search.Node;
import ipleiria.estg.dei.ei.model.search.Pair;
import ipleiria.estg.dei.ei.model.search.State;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Controller {

    private MainFrame view;
    private SwingWorker<Void, Void> worker;

    public Controller(MainFrame view) {
        this.view = view;
    }

    public void initController() {
        view.getButtonDataSet().addActionListener(e -> loadDataSet());
        view.getButtonRunSearch().addActionListener(e -> search());
        view.getButtonRunGA().addActionListener(e -> runGA());
    }

    private void runGA() {

        // bestIndividualPanel.textArea.setText(""); //TODO
        view.getSeriesBestIndividual().clear();
        view.getSeriesAverage().clear();

        view.manageButtons(false, true, false, true, true, true, false, false);
        Random random = new Random(Integer.parseInt(view.getPanelParameters().getTextFieldSeed().getText()));

        GeneticAlgorithm ga = new GeneticAlgorithm(
                Integer.parseInt(view.getPanelParameters().getTextFieldN().getText()),
                Integer.parseInt(view.getPanelParameters().getTextFieldGenerations().getText()),
                view.getPanelParameters().getSelectionMethod(),
                view.getPanelParameters().getRecombinationMethod(),
                view.getPanelParameters().getMutationMethod(),
                Environment.getInstance().getNumberOfAgents(),
                Environment.getInstance().getNumberOfPicks(), random);

        worker = new SwingWorker<>() {
            @Override
            public Void doInBackground() {
                try {
                    Individual bestInRun = ga.run();
                    Environment.getInstance().setBestInRun(bestInRun);
                    System.out.println(bestInRun);

                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                view.manageButtons(false, true, false, false, true, true, false, false);
            }
        };
        worker.execute();
    }

    private void search() { //TODO agents and offload area loaded statically(in matrix)
        List<State> picks = new LinkedList<>();
        picks.add(new State(3,0));
        picks.add(new State(5,11));
        picks.add(new State(9,12));

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    Environment.getInstance().setPicks(picks);

                    AStar aStar = new AStar();
                    for (Pair pair : Environment.getInstance().getPairs()) {
                        List<Action> actions = aStar.search(new Node(pair.getState1()), new Node(pair.getState2()));
                        pair.setValue(aStar.computePathCost(actions));
                        pair.setActions(actions);
                    }

                    Environment.getInstance().setPairsMap();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void done() {
                StringBuilder str = new StringBuilder();
                str.append("Pairs:\n");
                for (Pair p : Environment.getInstance().getPairs()) {
                    str.append(p);
                }
                System.out.println(str.toString());
                view.manageButtons(false, true, false, false, false, true, false, false);
            }
        };
        worker.execute();
    }

    private void loadDataSet() {
        JFileChooser fc = new JFileChooser(new File("./src/ipleiria/estg/dei/ei/dataSets"));
        int returnVal = fc.showOpenDialog(view);

        try{
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File dataSet = fc.getSelectedFile();
                Environment.getInstance().readInitialStateFromFile(dataSet);
                view.manageButtons(true,false,true,false,false,true,false,false);
            }
        }catch (IOException e1){
            e1.printStackTrace(System.err);
        }catch (java.util.NoSuchElementException e2){
            JOptionPane.showMessageDialog(view, "Invalid file format", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

}
