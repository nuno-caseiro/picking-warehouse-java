package ipleiria.estg.dei.ei.controller;

import ipleiria.estg.dei.ei.model.experiments.Experiment;
import ipleiria.estg.dei.ei.gui.MainFrame;
import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.AgentPath;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.Individual;
import ipleiria.estg.dei.ei.model.search.Node;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class Controller {

    private MainFrame view;
    private Environment environment;
    private SwingWorker<Void, Void> worker;

    public Controller(MainFrame view) {
        this.view = view;
        this.environment = Environment.getInstance();
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

        view.getToolBarHorizontal().getStepForward().addActionListener(e-> increment());
        view.getToolBarHorizontal().getStepBackward().addActionListener(e-> decrement());
        view.getToolBarHorizontal().getResume().addActionListener(e-> resume());

        loadDefaultLayout();
    }

    private void loadDefaultLayout(){
        this.environment.addEnvironmentListener(this.view.getSimulationPanel());
        try{
            Environment.getInstance().loadAtualLayout();
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        view.manageButtons(true,true,false,false,false,false);

    }

    private void resume(){
        Environment.getInstance().resume(Environment.getInstance().getAuxThread());
    }

    private void decrement() {
        Environment.getInstance().decrement(Environment.getInstance().getAuxThread());

    }

    private void increment() {
        Environment.getInstance().increment(Environment.getInstance().getAuxThread());
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

        if(!view.isSimulationPanelShow()){
            showSimulatePanel();
        }

        worker = new SwingWorker<>() {
            @Override
            public Void doInBackground() {
                try {
                    view.manageButtons(false,false,false,false,false,true);
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
        if(worker != null && !worker.isCancelled() && !worker.isDone()) {
            worker.cancel(true);
        }
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
            protected Void doInBackground() throws InterruptedException {

                try {
                    long start = System.currentTimeMillis();
                    Individual bestInRun = ga.run();
                    long end = System.currentTimeMillis();

                    Environment.getInstance().setBestInRun(bestInRun);
                    view.getGaHistoryPanel().appendParametersOfRun(bestInRun,view.getGaPanel().getPanelParameters());
                    System.out.println(bestInRun);

                    System.out.println("##################################");
                    System.out.println("Time: " + ((double)(end - start) / 1000) + " seconds");
                    System.out.println("##################################");

                    for (AgentPath agentPath : bestInRun.getIndividualPaths()) {
                        System.out.println("-------------------------------");
                        for (Node node : agentPath.getPath()) {
                            if (node.getWeight() != 0 || node.getNodeNumber() == 36) {
                                System.out.print(node + ", ");
                            }
                        }
                        System.out.println();
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            protected void done() {
                if(!isCancelled()){
                view.manageButtons(true,true,false,true,false,true);
                }

            }
        };
        worker.execute();
    }

    private void loadWarehouseLayout() {
        JFileChooser fc = new JFileChooser(new File("src/ipleiria/estg/dei/ei/dataSets/warehouseLayout"));
        int returnVal = fc.showOpenDialog(this.view);
        this.environment.addEnvironmentListener(this.view.getSimulationPanel());

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dataSet = fc.getSelectedFile();
                File newFile= null;
                Path newDataset = null;
                try{
                    newDataset = Files.move(Paths.get(dataSet.getPath()), Paths.get("./src/ipleiria/estg/dei/ei/dataSets/warehouseLayout/actual/"+dataSet.getName()));
                    Files.move(Paths.get(Environment.getInstance().getDefaultWarehouseLayout().getPath()),Paths.get("./src/ipleiria/estg/dei/ei/dataSets/warehouseLayout/other/"+Environment.getInstance().getDefaultWarehouseLayout().getName()));
                }catch (Exception e){
                    e.printStackTrace(System.err);
                }
                newFile= new File(newDataset.toUri());
                Environment.getInstance().setDefaultWarehouseLayout(newFile);
                Environment.getInstance().readInitialStateFromFile(newFile);
                view.manageButtons(true,true,false,false,false,false);
            }

        } catch (java.util.NoSuchElementException e) {
            JOptionPane.showMessageDialog(view, "Invalid file format", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void loadPicks(){
        JFileChooser fc = new JFileChooser(new File("./src/ipleiria/estg/dei/ei/dataSets/picks"));
        int returnVal = fc.showOpenDialog(view);
        try {

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File dataSetPick = fc.getSelectedFile();
                Environment.getInstance().loadPicksFromFile(dataSetPick);

                view.manageButtons(true,true,true,true,false,false);
            }

        } catch (java.util.NoSuchElementException e) {
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


