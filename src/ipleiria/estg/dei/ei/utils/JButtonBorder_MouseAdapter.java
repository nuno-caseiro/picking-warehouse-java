package ipleiria.estg.dei.ei.utils;

import ipleiria.estg.dei.ei.gui.MainFrame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JButtonBorder_MouseAdapter implements MouseListener {

     private JButton button;

    public JButtonBorder_MouseAdapter(JButton button) {
        this.button = button;
    }



    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        if(button.isEnabled()){
            button.setBorderPainted(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        button.setBorderPainted(false);
    }
}
