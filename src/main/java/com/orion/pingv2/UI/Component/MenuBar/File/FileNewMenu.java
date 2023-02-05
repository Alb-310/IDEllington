package com.orion.pingv2.UI.Component.MenuBar.File;

import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JMenuUI;

import javax.swing.*;

public class FileNewMenu extends JMenuUI implements UIBuilder {

    private JMenuItem fileMenuItem;
    private JMenuItem projectMenuItem;

    public FileNewMenu(String text) {
        super(text);
        this.setupUI();
    }

    public JMenuItem getFileMenuItem() {
        return fileMenuItem;
    }

    public JMenuItem getProjectMenuItem() {
        return projectMenuItem;
    }

    @Override
    public void setupUI() {
        this.fileMenuItem = new JMenuItem("File");
        this.projectMenuItem = new JMenuItem("Project");
    }

    @Override
    public JMenu build() {
        this.menu.add(fileMenuItem);
        this.menu.add(projectMenuItem);

        return this.menu;
    }

}
