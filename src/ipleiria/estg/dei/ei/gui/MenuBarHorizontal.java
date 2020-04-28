package ipleiria.estg.dei.ei.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuBarHorizontal extends JMenuBar {

        private JMenu menuFile;
        private JMenuItem menuItemImportLayout;
        private JMenuItem menuItemImportPicks;
        private JMenuItem exit;

        private JMenu menuEdit;

        private JMenu menuRun;
        private JMenuItem menuItemRunGA;
        private JMenuItem menuItemRunSimulate;
        private JMenuItem menuItemStopGA;

        private JMenu menuView;
        private JMenuItem menuItemSearchPanel;
        private JMenuItem menuItemGAPanel;
        private JMenuItem menuItemSimulationPanel;
        private JMenuItem menuItemExperimentsPanel;

        private JMenu menuHelp;
        private JMenuItem menuItemWelcome;
        private JMenuItem menuItemDocumentation;
        private JMenuItem menuItemReleaseNotes;
        private JMenuItem menuItemLicense;
        private JMenuItem menuItemAbout;


    public MenuBarHorizontal() {

        this.menuFile = new JMenu("File");
        this.menuItemImportLayout = new JMenuItem("Switch Layout", KeyEvent.VK_O);
        this.menuItemImportPicks = new JMenuItem("Import Picks", KeyEvent.VK_O);
        this.menuItemImportPicks.setEnabled(false);
        this.exit = new JMenuItem("Exit");
        this.menuFile.add(menuItemImportLayout);
        this.menuFile.add(menuItemImportPicks);
        this.menuFile.add(exit);
        this.add(menuFile);

        this.menuEdit = new JMenu("Edit");
        this.add(menuEdit);

        this.menuRun = new JMenu("Run");
        this.menuItemRunGA= new JMenuItem("Run Genetic Algorithm");
        this.menuItemRunGA.setEnabled(false);
        this.menuItemRunSimulate = new JMenuItem("Run Simulate");
        this.menuItemRunSimulate.setEnabled(false);
        this.menuItemStopGA= new JMenuItem("Stop Genetic Algorithm");
        this.menuItemStopGA.setEnabled(false);
        this.menuRun.add(menuItemRunGA);
        this.menuRun.add(menuItemRunSimulate);
        this.menuRun.add(menuItemStopGA);
        this.add(menuRun);

        this.menuView = new JMenu("View");
        this.menuItemSearchPanel= new JMenuItem("Search Panel");
        this.menuItemGAPanel= new JMenuItem("Genetic Algorithm Panel");
        this.menuItemSimulationPanel= new JMenuItem("Simulation Panel");
        this.menuItemExperimentsPanel= new JMenuItem("Experiments Panel");
        this.menuView.add(menuItemSearchPanel);
        this.menuView.add(menuItemGAPanel);
        this.menuView.add(menuItemSimulationPanel);
        this.menuView.add(menuItemExperimentsPanel);
        this.add(menuView);

        this.menuHelp = new JMenu("Help");
        this.menuItemWelcome= new JMenuItem("Welcome");
        this.menuItemDocumentation= new JMenuItem("Documentation");
        this.menuItemReleaseNotes= new JMenuItem("Release Notes");
        this.menuItemLicense= new JMenuItem("License");
        this.menuItemAbout= new JMenuItem("About");
        this.menuHelp.add(menuItemWelcome);
        this.menuHelp.add(menuItemDocumentation);
        this.menuHelp.add(menuItemReleaseNotes);
        this.menuHelp.add(menuItemAbout);
        this.add(menuHelp);

        this.setBackground(Color.WHITE);

    }

    public void manageButtons(boolean layout, boolean picks,boolean runSearch, boolean runGA, boolean stopRunGA, boolean runEnvironment) {
        this.menuItemImportLayout.setEnabled(layout);
        this.menuItemImportPicks.setEnabled(picks);
        this.menuItemRunGA.setEnabled(runGA);
        this.menuItemStopGA.setEnabled(stopRunGA);
        this.menuItemRunSimulate.setEnabled(runEnvironment);
    }


    public JMenuItem getMenuItemImportLayout() {
        return menuItemImportLayout;
    }

    public JMenuItem getMenuItemImportPicks() {
        return menuItemImportPicks;
    }

    public JMenuItem getExit() {
        return exit;
    }

    public JMenuItem getMenuItemRunGA() {
        return menuItemRunGA;
    }

    public JMenuItem getMenuItemRunSimulate() {
        return menuItemRunSimulate;
    }

    public JMenuItem getMenuItemStopGA() {
        return menuItemStopGA;
    }

    public JMenuItem getMenuItemSearchPanel() {
        return menuItemSearchPanel;
    }

    public JMenuItem getMenuItemGAPanel() {
        return menuItemGAPanel;
    }

    public JMenuItem getMenuItemSimulationPanel() {
        return menuItemSimulationPanel;
    }

    public JMenuItem getMenuItemExperimentsPanel() {
        return menuItemExperimentsPanel;
    }

    public JMenuItem getMenuItemWelcome() {
        return menuItemWelcome;
    }

    public JMenuItem getMenuItemDocumentation() {
        return menuItemDocumentation;
    }

    public JMenuItem getMenuItemReleaseNotes() {
        return menuItemReleaseNotes;
    }

    public JMenuItem getMenuItemLicense() {
        return menuItemLicense;
    }

    public JMenuItem getMenuItemAbout() {
        return menuItemAbout;
    }
}
