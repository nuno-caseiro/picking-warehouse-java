package ipleiria.estg.dei.ei.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MenuBarVertical extends JToolBar {
    private JButton problemData;
    private JButton ga;
    private JButton simulate;

   public MenuBarVertical() throws IOException {
       setOrientation(JToolBar.VERTICAL);
       setBackground(Color.white);
       setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(""), BorderFactory.createEmptyBorder(1,1,1,1)));

       this.problemData= new JButton("",new ImageIcon(getClass().getResource("assets/searchIcon.png")));
       add(this.problemData);

       this.ga = new JButton("",new ImageIcon(getClass().getResource("assets/gaIcon.png")));
       add(this.ga);

       this.simulate= new JButton("",new ImageIcon(getClass().getResource("assets/simulationIcon.png")));
       add(this.simulate);
   }

    public JButton getProblemData() {
        return problemData;
    }

    public JButton getGa() {
        return ga;
    }

    public JButton getSimulate() {
        return simulate;
    }
}
