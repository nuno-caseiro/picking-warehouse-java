package ipleiria.estg.dei.ei.gui;

import ipleiria.estg.dei.ei.utils.JButtonBorder_MouseAdapter;

import javax.swing.*;
import java.awt.*;

public class ToolBarHorizontal extends JToolBar {

    private JButton loadPicks;
    private JButton gaRun;
    private JButton stopGaRun;
    private JButton simulateRun;

    private JButton stepForward;
    private JButton stepBackward;
    private JButton resume;

    public ToolBarHorizontal() {
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());

        setBackground(Color.white);
        setFloatable(false);
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.GRAY));

        this.loadPicks= new JButton("",new ImageIcon(getClass().getResource("assets/loadPicksIcon.png")));
        this.loadPicks.setBorderPainted(false);
        this.loadPicks.setEnabled(false);
        this.loadPicks.setBackground(Color.WHITE);
        this.gaRun= new JButton("",new ImageIcon(getClass().getResource("assets/gaRunIcon.png")));
        this.gaRun.setBorderPainted(false);
        this.gaRun.setEnabled(false);
        this.gaRun.setBackground(Color.WHITE);
        this.stopGaRun= new JButton("",new ImageIcon(getClass().getResource("assets/stopGARunIcon.png")));
        this.stopGaRun.setBorderPainted(false);
        this.stopGaRun.setEnabled(false);
        this.stopGaRun.setBackground(Color.WHITE);
        this.simulateRun= new JButton("",new ImageIcon(getClass().getResource("assets/simulationRunIcon.png")));
        this.simulateRun.setBorderPainted(false);
        this.simulateRun.setEnabled(false);
        this.simulateRun.setBackground(Color.WHITE);

        this.stepForward= new JButton("",new ImageIcon(getClass().getResource("assets/nextIcon.png")));
        this.stepForward.setBorderPainted(false);
        this.stepForward.setEnabled(false);
        this.stepForward.setBackground(Color.WHITE);

        this.stepBackward= new JButton("",new ImageIcon(getClass().getResource("assets/backIcon.png")));
        this.stepBackward.setBorderPainted(false);
        this.stepBackward.setEnabled(false);
        this.stepBackward.setBackground(Color.WHITE);

        this.resume= new JButton("",new ImageIcon(getClass().getResource("assets/pausePlayIcon.png")));
        this.resume.setBorderPainted(false);
        this.resume.setEnabled(false);
        this.resume.setBackground(Color.WHITE);

        c.gridx=1;
        c.gridy=0;
        c.anchor = GridBagConstraints.NORTHWEST;
        //c.insets = new Insets(0, 0, 0, -5);
        this.add(this.loadPicks,c);
        JSeparator jSeparator= new JSeparator(JSeparator.VERTICAL);
        c.gridx=2;
        c.gridy=0;
        c.fill = GridBagConstraints.VERTICAL;
        c.insets = new Insets(0, 0, 0, 0);
        jSeparator.setForeground(Color.GRAY);
        jSeparator.setBackground(Color.LIGHT_GRAY.brighter());
        this.add(jSeparator,c);
        c.gridx=3;
        c.gridy=0;
        this.add(this.gaRun,c);
        c.gridx=4;
        c.gridy=0;
        this.add(this.stopGaRun,c);
        c.gridx=5;
        c.gridy=0;
        this.add(this.simulateRun,c);
        JSeparator jSeparator2= new JSeparator(JSeparator.VERTICAL);
        c.gridx=6;
        c.gridy=0;
        c.fill = GridBagConstraints.VERTICAL;
        jSeparator2.setForeground(Color.GRAY);
        jSeparator2.setBackground(Color.LIGHT_GRAY.brighter());
        this.add(jSeparator2,c);
        c.gridx=7;
        c.gridy=0;
        this.add(this.resume,c);
        c.gridx=8;
        c.gridy=0;
        this.add(this.stepForward);
        c.gridx=9;
        c.gridy=0;
        c.weightx=1.0;
        c.weighty=1.0;
        this.add(this.stepBackward,c);

        loadPicks.addMouseListener(new JButtonBorder_MouseAdapter(loadPicks));
        gaRun.addMouseListener(new JButtonBorder_MouseAdapter(gaRun));
        stopGaRun.addMouseListener(new JButtonBorder_MouseAdapter(stopGaRun));
        simulateRun.addMouseListener(new JButtonBorder_MouseAdapter(simulateRun));
        stepForward.addMouseListener(new JButtonBorder_MouseAdapter(stepForward));
        stepBackward.addMouseListener(new JButtonBorder_MouseAdapter(stepBackward));
        resume.addMouseListener(new JButtonBorder_MouseAdapter(resume));
        this.setBackground(Color.WHITE);
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

    public JButton getStepForward() {
        return stepForward;
    }

    public JButton getStepBackward() {
        return stepBackward;
    }

    public JButton getResume() {
        return resume;
    }

    public void manageButtons(boolean layout, boolean picks, boolean runSearch, boolean runGA, boolean stopRunGA, boolean runEnvironment) {
        this.loadPicks.setEnabled(picks);
        this.gaRun.setEnabled(runGA);
        this.simulateRun.setEnabled(runEnvironment);
        this.stopGaRun.setEnabled(stopRunGA);
        this.stepForward.setEnabled(runEnvironment);
        this.stepBackward.setEnabled(runEnvironment);
        this.resume.setEnabled(runEnvironment);
    }
}
