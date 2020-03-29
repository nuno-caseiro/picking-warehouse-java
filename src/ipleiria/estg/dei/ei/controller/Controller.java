package ipleiria.estg.dei.ei.controller;

import ipleiria.estg.dei.ei.gui.MainFrame;
import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.search.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
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
        view.getButtonStop().addActionListener(e -> stop());
//        view.getButtonSimulate().addActionListener(e -> simulate());
    }

    //
//    private void simulate() {
//        worker = new SwingWorker<>() {
//            @Override
//            public Void doInBackground() {
//                try {
//                    view.manageButtons(false, false, false, false, false, true, false, false);
//                    Environment.getInstance().executeSolution();
//                } catch (Exception e) {
//                    e.printStackTrace(System.err);
//                }
//                return null;
//            }
//
//            @Override
//            public void done() {
//                view.manageButtons(true, true, false, false, false, true, false, true);
//            }
//        };
//        worker.execute();
//    }

    private void stop() {
        worker.cancel(true);
    }

    private void runGA() {

        view.setBestIndividualPanelText("");
        view.getSeriesBestIndividual().clear();
        view.getSeriesAverage().clear();

        view.manageButtons(true, true, false, true, true, true, false, false);
        Random random = new Random(Integer.parseInt(view.getPanelParameters().getTextFieldSeed().getText()));

        GeneticAlgorithm ga = new GeneticAlgorithm(
                Integer.parseInt(view.getPanelParameters().getTextFieldN().getText()),
                Integer.parseInt(view.getPanelParameters().getTextFieldGenerations().getText()),
                view.getPanelParameters().getSelectionMethod(),
                view.getPanelParameters().getRecombinationMethod(),
                view.getPanelParameters().getMutationMethod(),
                Environment.getInstance().getNumberOfAgents(),
                Environment.getInstance().getNumberOfPicks(), random);

        ga.addGAListener(view);

        worker = new SwingWorker<>() {
            @Override
            public Void doInBackground() {
                try {
                    Individual bestInRun = ga.run();
                    Environment.getInstance().setBestInRun(bestInRun);
                    System.out.println(Environment.getInstance().getPicks());
                    System.out.println(bestInRun);

                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                view.manageButtons(true, true, false, false, true, true, false, true);
            }
        };
        worker.execute();
    }

    private void search() {

        String textFieldSelectivePressure = view.getPanelParameters().getTextFieldSelectivePressure().getText();
        double selectivePressure = Double.parseDouble(textFieldSelectivePressure);

        if(selectivePressure<1 ||selectivePressure>2){
            JOptionPane.showMessageDialog(view, "Selective pressure should be between 1 and 2.");
        }else{
            worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        AStar aStar = new AStar();
                        for (Pair pair : Environment.getInstance().getPairs()) {
                            List<Node> pathNodes = aStar.search(Environment.getInstance().getNode(pair.getNode1()), Environment.getInstance().getNode(pair.getNode2()));
                            pair.setValue(pathNodes.get(pathNodes.size() - 1).getG());
                            pair.setPath(pathNodes);
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
                    view.setProblemPanelText(str.toString());
                    view.manageButtons(true, true, false, false, false, true, false, false);
                }
            };
            worker.execute();
        }

    }

    private void loadDataSet() {
        JFileChooser fc = new JFileChooser(new File("./src/ipleiria/estg/dei/ei/dataSets"));
        int returnVal = fc.showOpenDialog(view);
        Environment.getInstance().addEnvironmentListener(view.getSimulationPanel());

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dataSet = fc.getSelectedFile();
                Environment.getInstance().readInitialStateFromFile(dataSet);

                returnVal = fc.showOpenDialog(view);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File dataSetPick = fc.getSelectedFile();
                    Environment.getInstance().loadPicksFromFile(dataSetPick);
                }

                //   view.getSimulationPanel().createEnvironment();
                view.manageButtons(true, false, true, false, false, true, false, false);
            }
        } catch (IOException e1) {
            e1.printStackTrace(System.err);
        } catch (java.util.NoSuchElementException e2) {
            JOptionPane.showMessageDialog(view, "Invalid file format", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}


