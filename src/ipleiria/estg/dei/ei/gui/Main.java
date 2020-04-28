package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.controller.Controller;

import javax.swing.*;

public class Main {

    public Main() {
        MainFrame frame = new MainFrame();
        Controller controller = new Controller(frame);
        controller.initController();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new Main();
        });
    }

}
