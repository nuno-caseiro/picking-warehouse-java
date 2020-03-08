package ipleiria.estg.dei.ei.controller;

import ipleiria.estg.dei.ei.gui.MainFrame;
import ipleiria.estg.dei.ei.model.Environment;
import ipleiria.estg.dei.ei.model.geneticAlgorithm.GeneticAlgorithm;
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

public class Controller {

    private MainFrame view;

    public Controller(MainFrame view) {
        this.view = view;
    }

    public void initController() {
        view.getButtonDataSet().addActionListener(e -> loadDataSet());
        view.getButtonRunSearch().addActionListener(e -> search());
    }

    private void search() {
        List<State> picks = new LinkedList<>();
        picks.add(new State(3,0));
        picks.add(new State(5,11));
        picks.add(new State(9,12));

        Environment.getInstance().setPicks(picks);

        AStar aStar = new AStar();
        for (Pair pair : Environment.getInstance().getPairs()) {
            List<Action> actions = aStar.search(new Node(pair.getState1()), new Node(pair.getState2()));
            pair.setPath(actions);
            pair.setValue(aStar.computePathCost(actions));
        }
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
