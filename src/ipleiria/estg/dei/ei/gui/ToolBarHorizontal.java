package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.JButtonBorder_MouseAdapter;

import javax.swing.*;
import java.awt.*;

public class ToolBarHorizontal extends JToolBar {

    private JButton loadLayout;
    private JButton loadPicks;
//    private JButton aStarRun;
    private JButton gaRun;
    private JButton stopGaRun;
    private JButton simulateRun;


    public ToolBarHorizontal() {
        GridBagConstraints c = new GridBagConstraints();

        setBackground(Color.white);
        setFloatable(false);
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.GRAY));

        this.loadLayout= new JButton("",new ImageIcon(getClass().getResource("assets/loadWarehouseLayoutIcon.png")));
        this.loadLayout.setBorderPainted(false);
        this.loadPicks= new JButton("",new ImageIcon(getClass().getResource("assets/loadPicksIcon.png")));
        this.loadPicks.setBorderPainted(false);
        this.loadPicks.setEnabled(false);
//        this.aStarRun= new JButton("",new ImageIcon(getClass().getResource("assets/aStarRunIcon.png")));
//        this.aStarRun.setBorderPainted(false);
//        this.aStarRun.setEnabled(false);
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
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        horizontalPanel.add(this.loadLayout,c);
        c.gridx=2;
        c.gridy=0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.NORTHWEST;
        horizontalPanel.add(this.loadPicks,c);
        JSeparator jSeparator= new JSeparator(JSeparator.VERTICAL);
        c.gridx=3;
        c.gridy=0;
        c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 0);
        horizontalPanel.add(jSeparator,c);
//        c.gridx=4;
//        c.gridy=0;
//        c.insets = new Insets(0, 0, 0, 0);
//        c.anchor = GridBagConstraints.NORTHWEST;
//        horizontalPanel.add(this.aStarRun,c);
        c.gridx=5;
        c.gridy=0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 0);
        horizontalPanel.add(this.gaRun,c);
        c.gridx=6;
        c.gridy=0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 0);
        horizontalPanel.add(this.stopGaRun,c);
        c.gridx=7;
        c.gridy=0;
        c.weightx=1.0;
        c.weighty=1.0;
        c.anchor = GridBagConstraints.NORTHWEST;
        c.insets = new Insets(0, 0, 0, 0);
        horizontalPanel.add(this.simulateRun,c);


        loadLayout.addMouseListener(new JButtonBorder_MouseAdapter(loadLayout));
        loadPicks.addMouseListener(new JButtonBorder_MouseAdapter(loadPicks));
//        aStarRun.addMouseListener(new JButtonBorder_MouseAdapter(aStarRun));
        gaRun.addMouseListener(new JButtonBorder_MouseAdapter(gaRun));
        stopGaRun.addMouseListener(new JButtonBorder_MouseAdapter(stopGaRun));
        simulateRun.addMouseListener(new JButtonBorder_MouseAdapter(simulateRun));

        this.add(horizontalPanel);

    }

//    public JButton getaStarRun() {
//        return aStarRun;
//    }

    public JButton getGaRun() {
        return gaRun;
    }

    public JButton getSimulateRun() {
        return simulateRun;
    }

    public JButton getLoadLayout() {
        return loadLayout;
    }

    public JButton getLoadPicks() {
        return loadPicks;
    }

    public JButton getStopGaRun() {
        return stopGaRun;
    }

    public void manageButtons(boolean layout, boolean picks, boolean runSearch, boolean runGA, boolean stopRunGA, boolean runEnvironment) {
        this.loadLayout.setEnabled(layout);
        this.loadPicks.setEnabled(picks);
//        this.aStarRun.setEnabled(runSearch);
        this.gaRun.setEnabled(runGA);
        this.simulateRun.setEnabled(runEnvironment);
        this.stopGaRun.setEnabled(stopRunGA);
    }
}
