package ipleiria.estg.dei.ei.gui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class MainFrame extends JFrame  {

    private static final long serialVersionUID = 1L;

    //TODO
    /*private CatchProblemForGA problemGA;
    private CatchState state;
    private CatchAgentSearch agentSearch;
    private Solution solution;
    private GeneticAlgorithm<CatchIndividual, CatchProblemForGA> ga;
    private CatchIndividual bestInRun;
    private CatchExperimentsFactory experimentsFactory;*/

    private PanelTextArea problemPanel;
    private PanelTextArea bestIndividualPanel;

    private JButton buttonDataSet = new JButton("Data set");
    private JButton buttonRunGA = new JButton("GA");
    private JButton buttonRunSearch = new JButton("Search1");
    private JButton buttonStop = new JButton("Stop");
    private JButton buttonRunSearch2 = new JButton("Search2");
    private JButton buttonExperiments = new JButton("Experiments");
    private JButton buttonRunExperiments = new JButton("Run experiments");
    private PanelParameters panelParameters = new PanelParameters(this);
    private JTextField textFieldExperimentsStatus = new JTextField("", 10);
    private XYSeries seriesBestIndividual;
    private XYSeries seriesAverage;
    private SwingWorker<Void, Void> worker; // An abstract class to perform lengthy GUI-interaction tasks in a background thread.

    private PanelSimulation simulationPanel;
    
    public MainFrame() throws HeadlessException {
        try{
           jbInit();
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
    }

    public void jbInit() throws Exception{
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("PICKING");

        //North Left Panel
        JPanel panelNorthLeft = new JPanel(new BorderLayout());     //BorderLayout: container with 5 regions north, south, east, west, and center
        panelNorthLeft.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1,1,1,1)));  //defines border of panelNorthLeft

        panelNorthLeft.add(getPanelParameters(), BorderLayout.WEST);
        JPanel panelButtons = new JPanel();

        panelButtons.add(buttonDataSet);
        buttonDataSet.setVisible(true);
        buttonDataSet.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        panelButtons.add(buttonRunSearch);
        buttonRunSearch.setEnabled(false);
        buttonRunSearch.addActionListener(new ButtonRunSearch_actionAdapter(this));

        panelButtons.add(buttonRunGA);
        buttonRunGA.setEnabled(false);
        buttonRunGA.addActionListener(new ButtonRunGA_actionAdapter(this));

        panelButtons.add(buttonStop);
        buttonStop.setEnabled(false);
        buttonStop.addActionListener(new ButtonStop_actionAdapter(this));

        panelButtons.add(buttonRunSearch2);
        buttonRunSearch2.setEnabled(false);
        buttonRunSearch2.addActionListener(new ButtonRunSearch2_actionAdapter(this));

        panelNorthLeft.add(panelButtons,BorderLayout.SOUTH);

        //North Right Panel - Chart creation

        seriesBestIndividual = new XYSeries("Best");
        seriesAverage = new XYSeries("Average");

        XYSeriesCollection dataSet = new XYSeriesCollection(); // Represents a collection of XYSeries objects that can be used as a dataset.
        dataSet.addSeries(seriesBestIndividual);
        dataSet.addSeries(seriesAverage);
        JFreeChart chart = ChartFactory.createXYLineChart("Evolution", //Title
                "generation", //x-axis label
                "fitness", // y-axis label
                dataSet, //Dataset
                PlotOrientation.VERTICAL, //Plot orientation
                true, //Show legend
                true, //Use tooltips
                false); //Configure chart to generate urls

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1,1,1,1)
        ));
        //default size
        chartPanel.setPreferredSize(new java.awt.Dimension(400,200));

        //North Panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(panelNorthLeft, BorderLayout.WEST);
        northPanel.add(chartPanel, BorderLayout.CENTER);

        //Center panel
        problemPanel = new PanelTextArea("Problem data: ",15,40);
        bestIndividualPanel= new PanelTextArea("Best solution: ", 15,40);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(problemPanel,BorderLayout.WEST);
        centerPanel.add(bestIndividualPanel, BorderLayout.CENTER);

        //South Panel
        JPanel southPanel = new JPanel();
        southPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(""),
                BorderFactory.createEmptyBorder(1,1,1,1)));

        southPanel.add(buttonExperiments);
        buttonExperiments.setEnabled(true);
        buttonExperiments.addActionListener(new ButtonExperiments_actionAdapter(this));
        southPanel.add(buttonRunExperiments);
        buttonRunExperiments.setEnabled(false);
        buttonRunExperiments.addActionListener(new ButtonRunExperiments_actionAdapter(this));
        southPanel.add(new JLabel("Status: "));
        southPanel.add(textFieldExperimentsStatus);
        textFieldExperimentsStatus.setEditable(false);

        //Big left panel
        JPanel bigLeftPanel = new JPanel(new BorderLayout());
        bigLeftPanel.add(northPanel,BorderLayout.NORTH);
        bigLeftPanel.add(centerPanel, BorderLayout.CENTER);
        bigLeftPanel.add(southPanel,BorderLayout.SOUTH);
        this.getContentPane().add(bigLeftPanel);

        simulationPanel = new PanelSimulation(this);
        simulationPanel.setJButtonSimulateEnabled(false);

        //Global structure
        JPanel globalPanel = new JPanel(new BorderLayout());
        globalPanel.add(bigLeftPanel,BorderLayout.WEST);
        globalPanel.add(simulationPanel,BorderLayout.EAST);
        this.getContentPane().add(globalPanel);

        this.setVisible(true);
        pack();
    }

    public PanelParameters getPanelParameters() {
        return panelParameters;
    }

    public JButton getButtonDataSet() {
        return buttonDataSet;
    }

    public JButton getButtonRunSearch() {
        return buttonRunSearch;
    }

    public PanelTextArea getProblemPanel() {
        return problemPanel;
    }

    public PanelTextArea getBestIndividualPanel() {
        return bestIndividualPanel;
    }

    public XYSeries getSeriesAverage() {
        return seriesAverage;
    }

    public XYSeries getSeriesBestIndividual() {
        return seriesBestIndividual;
    }

    public JButton getButtonRunGA() {
        return buttonRunGA;
    }

    public void cleanBoards() {
        problemPanel.textArea.setText("");
        bestIndividualPanel.textArea.setText("");
        seriesBestIndividual.clear();
        seriesAverage.clear();
    }



    public void jButtonRunSearch_actionPerformed(ActionEvent actionEvent) {
//TODO
    /*    try{
            if(agentSearch.getEnvironment() == null){
                JOptionPane.showMessageDialog(this, "You must first choose a problem", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bestIndividualPanel.textArea.setText("");
            seriesBestIndividual.clear();
            seriesAverage.clear();


            manageButtons(false,false,false,false,false,true,false,false);

            worker = new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try{
                        LinkedList<Pair> pairs = agentSearch.getPairs();
                        for(Pair p : pairs){
                            CatchState state = ((CatchState) agentSearch.getEnvironment()).clone();
                            state.setGoal(p.getCell2().getLine(), p.getCell2().getColumn());
                            state.setCellCatch(p.getCell1().getLine(), p.getCell1().getColumn());
                            CatchProblemSearch problem = new CatchProblemSearch(state,p.getCell2());
                            Solution s = agentSearch.solveProblem(problem);
                            p.setValue((int) s.getCost());
                        }

                        problemGA = new CatchProblemForGA(agentSearch.getInitialBox(),pairs,agentSearch.getCellCatch(), agentSearch.getDoor());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void done() {
                    problemPanel.textArea.setText(agentSearch.toString());
                    manageButtons(false,true,false,false,false,true,false,false);
                }
            };
            worker.execute();
        }catch (NumberFormatException e1){
            JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
*/
    }

    public void manageButtons(
            boolean dataSet,
            boolean runGA,
            boolean runSearch,
            boolean stopRun,
            boolean runSearch2,
            boolean experiments,
            boolean runExperiments,
            boolean runEnvironment) {
        buttonDataSet.setEnabled(dataSet);
        buttonRunGA.setEnabled(runGA);
        buttonRunSearch.setEnabled(runSearch);
        buttonStop.setEnabled(stopRun);
        buttonRunSearch2.setEnabled(runSearch2);
        buttonExperiments.setEnabled(experiments);
        buttonRunExperiments.setEnabled(runExperiments);
        if(simulationPanel != null)
            simulationPanel.setJButtonSimulateEnabled(runEnvironment);

    }

    public void jButtonRunGA_actionPerformed(ActionEvent actionEvent) {
       /* try {
            if (problemGA == null) {
                JOptionPane.showMessageDialog(this, "You must first choose a problem", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            bestIndividualPanel.textArea.setText("");
            seriesBestIndividual.clear();
            seriesAverage.clear();
            manageButtons(false, false, false, true, true, true, false, false);
            Random random = new Random(Integer.parseInt(getPanelParameters().textFieldSeed.getText()));
            ga = new GeneticAlgorithm<>(
                    Integer.parseInt(getPanelParameters().textFieldN.getText()),
                    Integer.parseInt(getPanelParameters().textFieldGenerations.getText()),
                    getPanelParameters().getSelectionMethod(),
                    getPanelParameters().getRecombinationMethod(),
                    getPanelParameters().getMutationMethod(),
                    random);

            ga.addGAListener(this);


            worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    try {

                        bestInRun = ga.run(problemGA);

                    } catch (Exception e) {
                        e.printStackTrace(System.err);
                    }
                    return null;
                }

                @Override
                public void done() {
                    agentSearch = new CatchAgentSearch(state.clone());
                    manageButtons(false, true, false, false, true, true, false, false);
                }
            };

            worker.execute();

        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
        }*/

    }

    public void jButtonRunSearch2_actionPerformed(ActionEvent actionEvent) {
        /*try {
            if (bestInRun == null) {
                JOptionPane.showMessageDialog(this, "You must first obtain a solution", "Error!", JOptionPane.ERROR_MESSAGE);
                return;
            }

            manageButtons(false, false, false, false, false, true, false, false);

            worker = new SwingWorker<Void, Void>() {
                @Override
                public Void doInBackground() {
                    try {
                        CatchState auxState = state.clone();
                        Solution auxSolution = null;
                        solution = null;
                        LinkedList<Cell> visitedBoxes = new LinkedList<>();
                        int genome[] = bestInRun.getGenome();
                        for (int i = 0; i <= genome.length; i++) {
                            Cell cell;
                            if (i != genome.length){
                                cell = (Cell) agentSearch.getInitialBox().get(genome[i] - 1);
                                if (visitedBoxes.contains(cell)){
                                    continue;
                                }
                            }
                            else{
                                cell = agentSearch.getDoor();
                            }
                            CatchProblemSearch problem = new CatchProblemSearch(auxState, cell);
                            auxSolution = agentSearch.solveProblem(problem);

                            if (auxSolution == null) {
                                solution = null;
                                break;
                            }
                            if ((i != genome.length)){
                                auxState = (CatchState) agentSearch.executeSolution(agentSearch.getInitialBox(), visitedBoxes);
                            }
                            if (i == 0){
                                solution = auxSolution;
                            }
                            else{
                                solution.updateActions(auxSolution.getActions());
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    agentSearch.setSolution(solution);

                    return null;
                }

                @Override
                public void done() {
                    bestIndividualPanel.textArea.append(agentSearch.getSearchReport());
                    problemPanel.textArea.setText("");
                    manageButtons(true, false, false, false, false, true, false, true);

                }
            };

            worker.execute();

        } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(this, "Wrong parameters!", "Error!", JOptionPane.ERROR_MESSAGE);
        }*/
    }

    //TODO GAListener
    /*public void generationEnded(GAEvent e) {
        GeneticAlgorithm<CatchIndividual, CatchProblemForGA> source = e.getSource();
        bestIndividualPanel.textArea.setText(source.getBestInRun().toString());
        seriesBestIndividual.add(source.getGeneration(), source.getBestInRun().getFitness());
        seriesAverage.add(source.getGeneration(), source.getAverageFitness());
        if (worker.isCancelled()) {
            e.setStopped(true);
        }
    }*/

    public void jButtonStop_actionPerformed(ActionEvent actionEvent) {
        worker.cancel(true);
    }

    public void buttonExperiments_actionPerformed(ActionEvent actionEvent) {
        //TODO
      /*  JFileChooser fc = new JFileChooser(new File("."));
        int returnVal = fc.showOpenDialog(this);

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                experimentsFactory = new CatchExperimentsFactory(fc.getSelectedFile());

                manageButtons(true, problemGA != null, false, false, false, true, true, false);

                if (experimentsFactory.getFile()==null) {
                    experimentsFactory=null;
                    throw  new java.util.NoSuchElementException();
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace(System.err);
            manageButtons(true, problemGA != null, false, false, false, true, false, false);
        } catch (java.util.NoSuchElementException e2) {
            JOptionPane.showMessageDialog(this, "File format not valid", "Error!", JOptionPane.ERROR_MESSAGE);
            manageButtons(true, problemGA != null, false, false, false, true, false, false);
        }*/
    }

    public void buttonRunExperiments_actionPerformed(ActionEvent actionEvent) {
        //TODO
       /* manageButtons(false, false, false, false, false, false, false, false);
        textFieldExperimentsStatus.setText("Running");

        worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                try {
                    int[][] matrix = CatchAgentSearch.readInitialStateFromFile(new File(experimentsFactory.getFile()));
                    CatchAgentSearch agentSearch = new CatchAgentSearch(new CatchState(matrix));

                    LinkedList<Pair> pairs = agentSearch.getPairs();
                    for (Pair p : pairs) {
                        CatchState state = ((CatchState) agentSearch.getEnvironment()).clone();
                        state.setGoal(p.getCell2().getLine(), p.getCell2().getColumn());
                        state.setCellCatch(p.getCell1().getLine(), p.getCell1().getColumn());
                        CatchProblemSearch problem = new CatchProblemSearch(state, p.getCell2());
                        Solution s = agentSearch.solveProblem(problem);
                        p.setValue((int) s.getCost());
                    }

                    while (experimentsFactory.hasMoreExperiments()) {
                        try {

                            Experiment experiment = experimentsFactory.nextExperiment(agentSearch.getInitialBox(), pairs, agentSearch.getCellCatch(), agentSearch.getDoor());
                            experiment.run();

                        } catch (IOException e1) {
                            e1.printStackTrace(System.err);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                }
                return null;
            }

            @Override
            public void done() {
                manageButtons(true, problemGA != null, false, false, false, true, false, false);
                textFieldExperimentsStatus.setText("Finished");
            }
        };
        worker.execute();*/
    }

    //TODO experimentEnd and getters & setters

}

class ButtonRunSearch_actionAdapter implements ActionListener{

    final private MainFrame adaptee;

    public ButtonRunSearch_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButtonRunSearch_actionPerformed(actionEvent);
    }
}

class ButtonRunGA_actionAdapter implements ActionListener{

    final private MainFrame adaptee;

    public ButtonRunGA_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButtonRunGA_actionPerformed(actionEvent);
    }
}

class ButtonRunSearch2_actionAdapter implements ActionListener{

    final private MainFrame adaptee;

    public ButtonRunSearch2_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButtonRunSearch2_actionPerformed(actionEvent);
    }
}

class ButtonStop_actionAdapter implements ActionListener{

    final private MainFrame adaptee;

    public ButtonStop_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.jButtonStop_actionPerformed(actionEvent);
    }
}

class ButtonExperiments_actionAdapter implements ActionListener{

    final private MainFrame adaptee;

    public ButtonExperiments_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.buttonExperiments_actionPerformed(actionEvent);
    }
}

class ButtonRunExperiments_actionAdapter implements ActionListener
{
    final private MainFrame adaptee;

    public ButtonRunExperiments_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.buttonRunExperiments_actionPerformed(actionEvent);
    }
}

class PanelTextArea extends JPanel{
    public JTextArea textArea;

    public PanelTextArea(String title, int rows, int columns) {
        textArea = new JTextArea(rows,columns);
        setLayout(new BorderLayout());
        add(new JLabel(title),BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(textArea); // provides a scrollable view of a component
        textArea.setEditable(false);
        add(scrollPane);
    }
}
