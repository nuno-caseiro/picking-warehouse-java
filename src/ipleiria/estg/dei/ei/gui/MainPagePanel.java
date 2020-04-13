package ipleiria.estg.dei.ei.gui;

import javax.swing.*;
import java.awt.*;

public class MainPagePanel extends JPanel {

    private JLabel title;

    public MainPagePanel() {
        setLayout(new BorderLayout());

        title= new JLabel("PICKING");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 50));
        this.add(title,BorderLayout.CENTER);
    }
}
