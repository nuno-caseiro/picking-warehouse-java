package ipleiria.estg.dei.ei.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PanelAtributesValue extends JPanel {

    protected String title;
    protected List<JLabel> labels = new ArrayList<>();
    protected List<JComponent> valueComponents = new ArrayList<>();

    public PanelAtributesValue() {
    }

    protected void configure(){

        GridBagLayout gridBag = new GridBagLayout();
        setLayout(gridBag);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor= GridBagConstraints.WEST;
        Iterator<JLabel> itLabels = labels.iterator();
        Iterator<JComponent> itTextFields = valueComponents.iterator();

        while(itLabels.hasNext()){
            c.gridwidth = GridBagConstraints.RELATIVE;
            c.fill = GridBagConstraints.NONE;
            c.weightx = 0.0;
            add(itLabels.next(),c);

            c.gridwidth= GridBagConstraints.REMAINDER;
            c.fill= GridBagConstraints.HORIZONTAL;
            c.weightx = 1.0;
            add(itTextFields.next(),c);
        }

    }
}
