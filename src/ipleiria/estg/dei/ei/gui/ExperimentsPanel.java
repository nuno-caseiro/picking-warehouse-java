package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ExperimentsPanel extends JPanel implements GAListener {


    private final ExperimentParametersPanel experimentParameters;
    private final JPanel eastPanel;
    private ExperimentsEditParametersPanel experimentsEditParametersPanel;
    private final HashMap<String,List<String>> availableParameters;
    private JButton run;
    private JProgressBar experimentsProgressBar;
    private int atualValueProgressBar;
    private JTextArea individual = new JTextArea(10,50);


    public ExperimentsPanel() {

    setLayout(new BorderLayout());

    JPanel parameters = new JPanel(new BorderLayout());

    experimentParameters =new ExperimentParametersPanel(this);
    eastPanel =new JPanel(new BorderLayout());
    atualValueProgressBar=0;
    availableParameters= new HashMap<>();
    List<String> values = new LinkedList<>();
    values.add("Tournament");
    values.add("Rank");
    availableParameters.put("Selection",values);
    values = new LinkedList<>();
    values.add("PMX");
    values.add("OX");
    values.add("OX1");
    values.add("CX");
    values.add("DX");
    availableParameters.put("Recombination",values);
    values = new LinkedList<>();
    values.add("Insert");
    values.add("Inversion");
    values.add("Scramble");
    availableParameters.put("Mutation",values);
    values = new LinkedList<>();
    values.add("StatisticBestAverage");
    values.add("StatisticBestAverageWithoutCollisions");
    availableParameters.put("Statistics",values);

    List<List<String>> listOfPickFiles = new ArrayList<>();


    experimentsProgressBar = new JProgressBar();
    experimentsProgressBar.setStringPainted(true);
    experimentsProgressBar.setValue(atualValueProgressBar);

    parameters.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

    run= new JButton("Run");

    parameters.add(experimentParameters,BorderLayout.NORTH);
    parameters.add(run,BorderLayout.SOUTH);

    this.individual.setEditable(false);
    this.eastPanel.add(individual,BorderLayout.SOUTH);
    this.add(experimentsProgressBar,BorderLayout.SOUTH);
    this.add(parameters,BorderLayout.WEST);
    this.add(eastPanel,BorderLayout.CENTER);

    }

    public void showEditParameters(int panelId, List<String> values){
        for (Component component : eastPanel.getComponents()) {
            if(component==experimentsEditParametersPanel){
                eastPanel.remove(component);
            }
        }
        experimentsEditParametersPanel= new ExperimentsEditParametersPanel(panelId,values,availableParameters,experimentParameters);
        eastPanel.add(experimentsEditParametersPanel,BorderLayout.NORTH);
        this.validate();
        this.repaint();
    }

    public void hideEditParameters(){
        eastPanel.remove(experimentsEditParametersPanel);

        this.validate();
        this.repaint();
    }

    /*public void showEditParameters(int panelId, List values, Point point) {
        experimentsEditParametersPanel= new ExperimentsEditParametersPanel(panelId,values,availableParameters,experimentParameters);
        experimentsEditParametersPanel.setLocation(point.x+250,point.y);
        experimentsEditParametersPanel.setPreferredSize(new Dimension(300,200));
        eastPanel.setComponentPopupMenu(experimentsEditParametersPanel);
        experimentsEditParametersPanel.setInvoker(eastPanel);
        experimentsEditParametersPanel.setVisible(true);
    }

    public void hideEditParameters(){
        experimentsEditParametersPanel.setInvoker(null);
        experimentsEditParametersPanel.setVisible(false);
    }*/

    public HashMap<String, List<String>> getAvailableParameters() {
        return availableParameters;
    }

    public ExperimentParametersPanel getExperimentParameters() {
        return experimentParameters;
    }

    public JButton getRun() {
        return run;
    }

    public JProgressBar getExperimentsProgressBar() {
        return experimentsProgressBar;
    }

    public int getAtualValueProgressBar() {
        return atualValueProgressBar;
    }

    public void setAtualValueProgressBar(int atualValueProgressBar) {
        this.atualValueProgressBar = atualValueProgressBar;
    }

    @Override
    public void generationEnded(GeneticAlgorithm e) {

    }

    @Override
    public void runEnded(GeneticAlgorithm e) {
        atualValueProgressBar++;
        experimentsProgressBar.setValue(atualValueProgressBar);
        individual.setText(e.getBestInRun().toString());
    }


    @Override
    public void experimentEnded() {

    }
}
