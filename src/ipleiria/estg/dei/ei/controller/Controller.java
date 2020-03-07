package ipleiria.estg.dei.ei.controller;

import ipleiria.estg.dei.ei.gui.MainFrame;
import ipleiria.estg.dei.ei.model.Environment;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class Controller {

    private MainFrame view;

    public Controller(MainFrame view) {
        this.view = view;
    }

    public void initController() {
        view.getButtonDataSet().addActionListener(e -> loadDataSet());
    }

    private void loadDataSet() {
        JFileChooser fc = new JFileChooser(new File("./src/ipleiria/estg/dei/ei/dataSets"));
        int returnVal = fc.showOpenDialog(view);

        try{
            if(returnVal == JFileChooser.APPROVE_OPTION){
                File dataSet = fc.getSelectedFile();
                Environment.getInstance().readInitialStateFromFile(dataSet);
            }
        }catch (IOException e1){
            e1.printStackTrace(System.err);
        }catch (java.util.NoSuchElementException e2){
            JOptionPane.showMessageDialog(view, "Invalid file format", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

}
