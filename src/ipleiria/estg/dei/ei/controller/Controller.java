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
                int[][] matrix = readInitialStateFromFile(dataSet);
                Environment.getInstance().setMatrix(matrix);
            }
        }catch (IOException e1){
            e1.printStackTrace(System.err);
        }catch (java.util.NoSuchElementException e2){
            JOptionPane.showMessageDialog(view, "Invalid file format", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int[][] readInitialStateFromFile(File file) throws IOException {
        java.util.Scanner scanner = new java.util.Scanner(file);

        int lines = scanner.nextInt();
        scanner.nextLine();
        System.out.println(lines);
        int columns = scanner.nextInt();
        scanner.nextLine();
        System.out.println(columns);

        int[][] matrix = new int[lines][columns];
        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }

        return matrix;
    }
}
