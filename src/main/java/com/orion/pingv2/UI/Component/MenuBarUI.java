package com.orion.pingv2.UI.Component;

import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JMenuBarUI;
import com.orion.pingv2.UI.Component.MenuBar.Edit.EditMenu;
import com.orion.pingv2.UI.Component.MenuBar.File.FileMenu;
import com.orion.pingv2.UI.Component.MenuBar.Tools.ToolsMenu;

import javax.swing.*;
import java.awt.*;

public class MenuBarUI extends JMenuBarUI implements UIBuilder {

    private Dimension dimension;
    private FileMenu fileMenu;
    private EditMenu editMenu;
    private ToolsMenu toolsMenu;
    public MenuBarUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);

        this.dimension = dimension;

        this.setupUI();
    }

    public FileMenu getFileMenu() {
        return fileMenu;
    }

    public EditMenu getEditMenu() {
        return editMenu;
    }

    public ToolsMenu getToolsMenu() {
        return toolsMenu;
    }

    @Override
    public void setupUI() {
        this.fileMenu = new FileMenu("File");
        this.editMenu = new EditMenu("Edit");
        this.toolsMenu = new ToolsMenu("Tools");
    }

    public JMenuBar build() {
        this.menuBar.add(fileMenu.build());
        this.menuBar.add(editMenu.build());
        this.menuBar.add(toolsMenu.build());

        return this.menuBar;
    }
}
