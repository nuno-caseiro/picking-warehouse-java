package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.JButtonBorder_MouseAdapter;

import javax.swing.*;
import java.awt.*;

public class ToolBarHorizontal extends JToolBar {

    private JButton loadPicks;
    private JButton gaRun;
    private JButton stopGaRun;
    private JButton simulateRun;


    public ToolBarHorizontal() {
        GridBagConstraints c = new GridBagConstraints();

        setBackground(Color.white);
        setFloatable(false);
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.GRAY));

        this.loadPicks= new JButton("",new ImageIcon(getClass().getResource("assets/loadPicksIcon.png")));
        this.loadPicks.setBorderPainted(false);
        this.loadPicks.setEnabled(false);
        this.gaRun= new JButton("",new ImageIcon(getClass().getResource("assets/gaRunIcon.png")));
        this.gaRun.setBorderPainted(false);
        this.gaRun.setEnabled(false);
        this.stopGaRun= new JButton("",new ImageIcon(getClass().getResource("assets/stopGARunIcon.png")));
        this.stopGaRun.setBorderPainted(false);
        this.stopGaRun.setEnabled(false);
        this.simulateRun= new JButton("",new ImageIcon(getClass().getResource("assets/simulationRunIcon.png")));
        this.simulateRun.setBorderPainted(false);
        this.simulateRun.setEnabled(false);

        JPanel horizontalPanel= new JPanel(new GridBagLayout());
        c.gridx=1;
        c.gridy=0;
        c.insets = new Insets(0, 0, 0, -4);
        c.anchor = GridBagConstraints.NORTHWEST;
        horizontalPanel.add(this.loadPicks,c);
        JSeparator jSeparator= new JSeparator(JSeparator.VERTICAL);
        c.gridx=2;
        c.gridy=0;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 5);
        horizontalPanel.add(jSeparator,c);
        c.gridx=3;
        c.gridy=0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 5);
        horizontalPanel.add(this.gaRun,c);
        c.gridx=4;
        c.gridy=0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 5);
        horizontalPanel.add(this.stopGaRun,c);
        c.gridx=5;
        c.gridy=0;
        c.weightx=1.0;
        c.weighty=1.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 0);
        horizontalPanel.add(this.simulateRun,c);


        loadPicks.addMouseListener(new JButtonBorder_MouseAdapter(loadPicks));
        gaRun.addMouseListener(new JButtonBorder_MouseAdapter(gaRun));
        stopGaRun.addMouseListener(new JButtonBorder_MouseAdapter(stopGaRun));
        simulateRun.addMouseListener(new JButtonBorder_MouseAdapter(simulateRun));
        horizontalPanel.setBackground(Color.WHITE);
        this.add(horizontalPanel);

    }

    public JButton getGaRun() {
        return gaRun;
    }

    public JButton getSimulateRun() {
        return simulateRun;
    }

    public JButton getLoadPicks() {
        return loadPicks;
    }

    public JButton getStopGaRun() {
        return stopGaRun;
    }

    public void manageButtons(boolean layout, boolean picks, boolean runSearch, boolean runGA, boolean stopRunGA, boolean runEnvironment) {
        this.loadPicks.setEnabled(picks);
        this.gaRun.setEnabled(runGA);
        this.simulateRun.setEnabled(runEnvironment);
        this.stopGaRun.setEnabled(stopRunGA);
    }
}
