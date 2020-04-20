package ipleiria.estg.dei.ei.controller;

import ipleiria.estg.dei.ei.model.experiments.Experiment;
import ipleiria.estg.dei.ei.gui.MainFrame;
import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class Controller {

    private MainFrame view;
    private SwingWorker<Void, Void> worker;

    public Controller(MainFrame view) {
        this.view = view;
    }

    public void initController() {
        view.getMenuBarHorizontal().getMenuItemImportLayout().addActionListener(e -> loadWarehouseLayout());
        view.getMenuBarHorizontal().getMenuItemImportPicks().addActionListener(e -> loadPicks());
        view.getMenuBarHorizontal().getExit().addActionListener(e->closeApplication());

        view.getMenuBarHorizontal().getMenuItemRunGA().addActionListener(e -> runGA());
        view.getMenuBarHorizontal().getMenuItemStopGA().addActionListener(e -> stop());
        view.getMenuBarHorizontal().getMenuItemRunSimulate().addActionListener(e -> simulate());

        view.getToolBarHorizontal().getLoadPicks().addActionListener(e-> loadPicks());
        view.getToolBarHorizontal().getGaRun().addActionListener(e-> runGA());
        view.getToolBarHorizontal().getStopGaRun().addActionListener(e-> stop());
        view.getToolBarHorizontal().getSimulateRun().addActionListener(e-> simulate());

        view.getMenuBarHorizontal().getMenuItemGAPanel().addActionListener(e->showGaPanel());
        view.getMenuBarHorizontal().getMenuItemSimulationPanel().addActionListener(e->showSimulatePanel());

        view.getToolBarVertical().getGa().addActionListener(e->showGaPanel());
        view.getToolBarVertical().getSimulate().addActionListener(e->showSimulatePanel());
        view.getToolBarVertical().getGaHistory().addActionListener(e->showGaHistory());
        view.getToolBarVertical().getExperiments().addActionListener(e->showExperimentPanel());

        view.getMenuBarHorizontal().getMenuItemWelcome().addActionListener(e->showMainPage());

        view.getExperimentsPanel().getRun().addActionListener(e-> runExperiments());
    }

    private void showExperimentPanel() {
        view.showPanel(1);
    }

    private void showGaHistory() {
        view.showPanel(4);
    }

    private void showSimulatePanel() {
    view.showPanel(3);
    }

    private void showGaPanel() {
        view.showPanel(2);
    }

    private void showMainPage() {
        view.showPanel(0);
    }

    private void simulate() {
        worker = new SwingWorker<>() {
            @Override
            public Void doInBackground() {
                try {
                    view.manageButtons(false,false,false,false,false,false);
                    Environment.getInstance().executeSolution();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                view.manageButtons(true,false,false,true,false,true);

            }
        };
        worker.execute();
    }

    private void stop() {
        worker.cancel(true);
    }

    private void runGA() {

        view.setBestIndividualPanelText("");
        view.getGaPanel().getSeriesBestIndividual().clear();
        view.getGaPanel().getSeriesAverage().clear();

        if(!view.isGaPanelShow()){
            showGaPanel();
        }

        view.manageButtons(true,false,true,true,true,false);
        Random random = new Random(Integer.parseInt(view.getGaPanel().getPanelParameters().getTextFieldSeed().getText()));

        Environment.getInstance().setTimeWeight(Integer.parseInt(view.getGaPanel().getPanelParameters().getTextFieldTimeWeight().getText()));
        Environment.getInstance().setCollisionsWeight(Integer.parseInt(view.getGaPanel().getPanelParameters().getTextFieldCollisionsWeight().getText()));

        GeneticAlgorithm ga = new GeneticAlgorithm(
                Integer.parseInt(view.getGaPanel().getPanelParameters().getTextFieldN().getText()),
                Integer.parseInt(view.getGaPanel().getPanelParameters().getTextFieldGenerations().getText()),
                view.getGaPanel().getPanelParameters().getSelectionMethod(),
                view.getGaPanel().getPanelParameters().getRecombinationMethod(),
                view.getGaPanel().getPanelParameters().getMutationMethod(),
                Environment.getInstance().getNumberOfAgents(),
                Environment.getInstance().getNumberOfPicks(), random);

        ga.addGAListener(view);

        worker = new SwingWorker<>() {
            @Override
            public Void doInBackground() {
                try {
                    Individual bestInRun = ga.run();
                    Environment.getInstance().setBestInRun(bestInRun);
                    view.getGaHistoryPanel().appendParametersOfRun(bestInRun,view.getGaPanel().getPanelParameters());
                    System.out.println(Environment.getInstance().getPicks());
                    System.out.println(bestInRun);

                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                view.manageButtons(true,false,false,true,false,true);

            }
        };
        worker.execute();
    }

    private void loadWarehouseLayout() {
        JFileChooser fc = new JFileChooser(new File("./src/ipleiria/estg/dei/ei/dataSets"));
        int returnVal = fc.showOpenDialog(view);
        Environment.getInstance().addEnvironmentListener(view.getSimulationPanel());

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dataSet = fc.getSelectedFile();
                Environment.getInstance().readInitialStateFromFile(dataSet);
                view.manageButtons(true,true,false,false,false,false);
            }

        } catch (IOException e1) {
            e1.printStackTrace(System.err);
        } catch (java.util.NoSuchElementException e2) {
            JOptionPane.showMessageDialog(view, "Invalid file format", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPicks(){
        JFileChooser fc = new JFileChooser(new File("./src/ipleiria/estg/dei/ei/dataSets"));
        int returnVal = fc.showOpenDialog(view);
        try {

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dataSetPick = fc.getSelectedFile();
                Environment.getInstance().loadPicksFromFile(dataSetPick);
                view.manageButtons(true,true,true,true,false,false);
            }

        }catch (IOException e1) {
            e1.printStackTrace(System.err);
        } catch (java.util.NoSuchElementException e2) {
            JOptionPane.showMessageDialog(view, "Invalid file format", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void runExperiments() {

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try{

                Experiment experiment = new Experiment();
                experiment.readParametersValues(view.getExperimentsPanel().getExperimentParameters());
                view.getExperimentsPanel().getExperimentsProgressBar().setMaximum(experiment.getCountAllRuns());
                experiment.setExperimentsPanel(view.getExperimentsPanel());
                view.getExperimentsPanel().setAtualValueProgressBar(0);
                while (experiment.hasMoreExperiments()){
                    experiment.run();
                    experiment.indicesManaging(experiment.getParameters().keySet().size() - 1);
                }
                view.manageButtons(false,false,false,false,false,false);
                }catch (Exception e){
                    e.printStackTrace(System.err);
                }

            return null;
            }

            @Override
            protected void done() {
                super.done();
                view.manageButtons(true,true,false,true,false,true);
            }
        };

        worker.execute();


    }

    private void closeApplication(){
        System.exit(0);
    }
}


