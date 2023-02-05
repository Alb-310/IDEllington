package com.orion.pingv2.UI.Component.MenuBar.Tools;

import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JMenuUI;

import javax.swing.*;

public class ToolsMenu extends JMenuUI implements UIBuilder {

    private ToolsIntegrationMenu toolsIntegrationMenu;

    public ToolsMenu(String text) {
        super(text);
        this.setupUI();
    }

    public ToolsIntegrationMenu getToolsIntegrationMenu() {
        return toolsIntegrationMenu;
    }

    @Override
    public void setupUI() {
        this.toolsIntegrationMenu = new ToolsIntegrationMenu("Integration");
    }

    @Override
    public JMenu build() {
        this.menu.add(toolsIntegrationMenu.build());

        return this.menu;
    }

}
