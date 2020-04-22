package ipleiria.estg.dei.ei.utils;

import ipleiria.estg.dei.ei.gui.MainFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class IntegerTextField_KeyAdapter implements KeyListener {

    final private MainFrame adaptee;

    public IntegerTextField_KeyAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        char c = keyEvent.getKeyChar();
        if(!Character.isDigit(c)  || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE || Character.isWhitespace(c)){
            keyEvent.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}