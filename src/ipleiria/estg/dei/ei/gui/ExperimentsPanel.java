package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.model.geneticAlgorithm.GAListener;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class ExperimentsPanel extends JPanel implements GAListener {


    private final ExperimentParametersPanel experimentParameters;
    private final JPanel eastPanel;
    private ExperimentsEditParametersPanel experimentsEditParametersPanel;
    private final HashMap<String,Object> availableParameters;
    private JButton run;
    private JProgressBar experimentsProgressBar;
    private int atualValueProgressBar;
    private JTextArea individual = new JTextArea(10,50);
    HashMap<String, String> mapPickFiles = new HashMap<String, String>();
    HashMap<String, String> mapWarhouseLayoutFiles = new HashMap<String, String>();

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


//        try {
//            List<Path> files = Files.walk(Paths.get("src/ipleiria/estg/dei/ei/dataSets/picks")).filter(s -> s.toString().endsWith(".json")).map(Path::getFileName).sorted().collect(Collectors.toList());
//            List<String> filesToString = Files.walk(Paths.get("src/ipleiria/estg/dei/ei/dataSets/picks")).filter(s -> s.toString().endsWith(".json")).map(Path::toString).sorted().collect(Collectors.toList());
//            for (int i = 0; i < files.size(); i++) {
//
//                mapPickFiles.put(files.get(i).toString(),filesToString.get(i));
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            List<Path> files = Files.walk(Paths.get("src/ipleiria/estg/dei/ei/dataSets/wareHouseLayout")).filter(s -> s.toString().endsWith(".json")).map(Path::getFileName).sorted().collect(Collectors.toList());
//            List<String> filesToString = Files.walk(Paths.get("src/ipleiria/estg/dei/ei/dataSets/wareHouseLayout")).filter(s -> s.toString().endsWith(".json")).map(Path::toString).sorted().collect(Collectors.toList());
//
//            for (int i = 0; i < files.size(); i++) {
//
//                mapWarhouseLayoutFiles.put(files.get(i).toString(),filesToString.get(i));
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        availableParameters.put("WarehouseLayout", mapWarhouseLayoutFiles);
//        availableParameters.put("Picks", mapPickFiles);

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

    public HashMap<String, Object> getAvailableParameters() {
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
