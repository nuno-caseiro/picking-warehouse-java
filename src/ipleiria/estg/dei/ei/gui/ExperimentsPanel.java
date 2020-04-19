package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.Environment;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class ExperimentsPanel extends JPanel {


    private final ExperimentParametersPanel experimentParameters;
    private final JPanel eastPanel;
    private ExperimentsEditParametersPanel experimentsEditParametersPanel;
    private final HashMap<String,List<String>> availableParameters;
    private JButton run;


    public ExperimentsPanel() {

    setLayout(new BorderLayout());

    JPanel parameters = new JPanel(new BorderLayout());

    experimentParameters =new ExperimentParametersPanel(this);
    eastPanel =new JPanel(new BorderLayout());

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


    eastPanel.setBorder(BorderFactory.createMatteBorder(0,1,1,0,Color.GRAY));
    run= new JButton("Run");

    parameters.add(experimentParameters,BorderLayout.NORTH);
    parameters.add(run,BorderLayout.SOUTH);

    this.add(parameters,BorderLayout.WEST);
    this.add(eastPanel,BorderLayout.EAST);

    }

    public void showEditParameters(int panelId, List values, Point point) {
        experimentsEditParametersPanel= new ExperimentsEditParametersPanel(panelId,values,availableParameters,experimentParameters);
        experimentsEditParametersPanel.setLocation(point.x+250,point.y);
        eastPanel.setComponentPopupMenu(experimentsEditParametersPanel);
        experimentsEditParametersPanel.setInvoker(eastPanel);
        experimentsEditParametersPanel.setVisible(true);
    }

    public void hideEditParameters(){
        experimentsEditParametersPanel.setInvoker(null);
        experimentsEditParametersPanel.setVisible(false);
    }

    public HashMap<String, List<String>> getAvailableParameters() {
        return availableParameters;
    }

    public ExperimentParametersPanel getExperimentParameters() {
        return experimentParameters;
    }

    public JButton getRun() {
        return run;
    }
}
