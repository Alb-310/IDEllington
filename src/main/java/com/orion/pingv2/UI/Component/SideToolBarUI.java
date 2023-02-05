package com.orion.pingv2.UI.Component;

import com.orion.pingv2.UI.Component.SideToolBar.ThemeManagerUI;
import com.orion.pingv2.UI.Component.SideToolBar.ToolPanelUI;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Component.SideToolBar.FileExplorerUI;

import javax.swing.*;
import java.awt.*;

public class SideToolBarUI extends JPanelUI implements UIBuilder {
    private Dimension dimension;
    private ToolPanelUI toolPanelUI;
    private FileExplorerUI fileExplorerUI;
    private ThemeManagerUI themeManagerUI;
    private JPanel fileExplorerPanel;
    private JPanel themeManagerPanel;

    public SideToolBarUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);

        this.dimension = dimension;

        this.setupUI();
    }

    public FileExplorerUI getFileExplorerUI() {
        return fileExplorerUI;
    }

    public ToolPanelUI getToolPanelUI() {
        return toolPanelUI;
    }

    public JPanel getFileExplorerPanel() {
        return fileExplorerPanel;
    }

    public JPanel getThemeManagerPanel() {
        return themeManagerPanel;
    }

    public ThemeManagerUI getThemeManagerUI() {
        return themeManagerUI;
    }

    @Override
    public void setupUI() {
        this.mainPanel.setPreferredSize(this.dimension);
        this.mainPanel.setSize(this.dimension);
        this.mainPanel.setMinimumSize(new Dimension(0, 0));

        this.toolPanelUI = new ToolPanelUI(
                new Dimension((int)(this.dimension.width * 0.15),
                        this.dimension.height),
                        ThemeManager.getCurrentTheme().accentColor,
                        ThemeManager.getCurrentTheme().baseColor);

        this.fileExplorerUI = new FileExplorerUI(
                new Dimension((int)(this.dimension.width * 0.8), this.dimension.height),
                ThemeManager.getCurrentTheme().baseColor,
                ThemeManager.getCurrentTheme().accentColor
        );
        this.fileExplorerPanel = this.fileExplorerUI.build();

        this.themeManagerUI = new ThemeManagerUI(
                new Dimension((int)(this.dimension.width * 0.8), this.dimension.height),
                ThemeManager.getCurrentTheme().baseColor,
                ThemeManager.getCurrentTheme().accentColor
        );
        this.themeManagerPanel = themeManagerUI.build();
    }

    @Override
    public JPanel build() {
        this.mainPanel.add(toolPanelUI.build(), BorderLayout.WEST);
        this.mainPanel.add(fileExplorerUI.build(), BorderLayout.CENTER);

        return this.mainPanel;
    }

    public void setToolPanel(JPanel toolPanel) {
        this.mainPanel.remove(1);
        this.mainPanel.add(toolPanel, BorderLayout.CENTER);
    }
}
