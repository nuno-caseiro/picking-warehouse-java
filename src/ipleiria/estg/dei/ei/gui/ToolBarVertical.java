package ipleiria.estg.dei.ei.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ToolBarVertical extends JToolBar {
//    private JButton problemData;
    private JButton ga;
    private JButton simulate;
    private JButton gaHistory;

   public ToolBarVertical() throws IOException {
       setOrientation(JToolBar.VERTICAL);
       setBackground(Color.white);
       setFloatable(false);
       setBorder(BorderFactory.createMatteBorder(0,1,1,1,Color.GRAY));

//       this.problemData= new JButton("",new ImageIcon(getClass().getResource("assets/searchIcon.png")));
//       add(this.problemData);

       this.ga = new JButton("",new ImageIcon(getClass().getResource("assets/gaIcon.png")));
       add(this.ga);

       this.simulate= new JButton("",new ImageIcon(getClass().getResource("assets/simulationIcon.png")));
       add(this.simulate);

       this.gaHistory= new JButton("",new ImageIcon(getClass().getResource("assets/gaHistoryIcon.png")));
       add(this.gaHistory);
   }

//    public JButton getProblemData() {
//        return problemData;
//    }

    public JButton getGa() {
        return ga;
    }

    public JButton getSimulate() {
        return simulate;
    }

    public JButton getGaHistory() {
        return gaHistory;
    }
}
