package ipleiria.estg.dei.ei.utils;

import ipleiria.estg.dei.ei.gui.PanelParameters;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JComboBoxSelectionMethods_ActionAdapter implements ActionListener {

    final private PanelParameters adaptee;

    public JComboBoxSelectionMethods_ActionAdapter(PanelParameters adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        adaptee.actionPerformedSelectionMethods(actionEvent);
    }
}