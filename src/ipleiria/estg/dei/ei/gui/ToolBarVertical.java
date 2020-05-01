package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.JButtonBorder_MouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ToolBarVertical extends JToolBar {
    private JButton ga;
    private JButton simulate;
    private JButton gaHistory;
    private JButton experiments;

   public ToolBarVertical() {
       setOrientation(JToolBar.VERTICAL);
       setBackground(Color.white);
       setFloatable(false);
       setBorder(BorderFactory.createMatteBorder(0,0,0,1,Color.GRAY));

       this.ga = new JButton("",new ImageIcon(getClass().getResource("assets/gaIcon.png")));
       this.ga.setBorderPainted(false);
       this.ga.setBackground(Color.WHITE);
       this.add(this.ga);

       this.simulate= new JButton("",new ImageIcon(getClass().getResource("assets/simulationIcon.png")));
       this.simulate.setBorderPainted(false);
       this.simulate.setBackground(Color.WHITE);
       this.add(this.simulate);

       this.gaHistory= new JButton("",new ImageIcon(getClass().getResource("assets/gaHistoryIcon.png")));
       this.gaHistory.setBorderPainted(false);
       this.gaHistory.setBackground(Color.WHITE);
       this.add(this.gaHistory);

       this.experiments= new JButton("",new ImageIcon(getClass().getResource("assets/experimentsIcon.png")));
       this.experiments.setBorderPainted(false);
       this.experiments.setBackground(Color.WHITE);
       this.add(this.experiments);

       ga.addMouseListener(new JButtonBorder_MouseAdapter(ga));
       simulate.addMouseListener(new JButtonBorder_MouseAdapter(simulate));
       gaHistory.addMouseListener(new JButtonBorder_MouseAdapter(gaHistory));
       experiments.addMouseListener(new JButtonBorder_MouseAdapter(experiments));

   }

    public JButton getGa() {
        return ga;
    }

    public JButton getSimulate() {
        return simulate;
    }

    public JButton getGaHistory() {
        return gaHistory;
    }

    public JButton getExperiments() {
        return experiments;
    }
}
