package ipleiria.estg.dei.ei.gui;

import javax.swing.*;

public class MenuBarVertical {
        private JMenuBar menuBar;
        private JMenu menu;
        private JMenuItem menuItemImportLayout;
        private JMenuItem menuItemImportPicks;

    public MenuBarVertical() {
        this.menuBar = new JMenuBar();

        this.menu = new JMenu("File");
        this.menuBar.add(menu);

        this.menuItemImportLayout = new JMenuItem("Import Layout");
        this.menuItemImportPicks = new JMenuItem("Import Picks");

        this.menu.add(menuItemImportLayout);
        this.menu.add(menuItemImportPicks);

    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JMenuItem getMenuItemImportLayout() {
        return menuItemImportLayout;
    }

    public JMenuItem getMenuItemImportPicks() {
        return menuItemImportPicks;
    }
}
