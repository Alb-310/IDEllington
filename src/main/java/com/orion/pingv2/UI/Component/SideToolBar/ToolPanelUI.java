package com.orion.pingv2.UI.Component.SideToolBar;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Model.ModernUI.ModernToggleButton;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

public class ToolPanelUI extends JPanelUI implements UIBuilder {

    private Dimension dimension;
    private JToolBar jToolBar;
    private ModernToggleButton fileExplorerButton;
    private ModernToggleButton themeManagerButton;

    public ToolPanelUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);

        this.dimension = dimension;

        this.setupUI();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public JToolBar getjToolBar() {
        return jToolBar;
    }

    public ModernToggleButton getFileExplorerButton() {
        return fileExplorerButton;
    }

    public ModernToggleButton getThemeManagerButton() {
        return themeManagerButton;
    }

    @Override
    public void setupUI() {
        this.mainPanel.setSize(dimension);
        this.mainPanel.setPreferredSize(dimension);
        this.mainPanel.setMinimumSize(new Dimension((int)(this.dimension.width * 0.5), this.dimension.height));

        this.jToolBar = new JToolBar();
        this.jToolBar.setBackground(ThemeManager.getCurrentTheme().baseColor);
        this.jToolBar.setFloatable(false);
        this.jToolBar.setOrientation(SwingConstants.VERTICAL);

        try {
            InputStream explorerImageStream = ThemeManager.class.getResourceAsStream(ThemeManager.getCurrentTheme().explorerImage);
            this.fileExplorerButton = new ModernToggleButton(explorerImageStream, 0.05);
            fileExplorerButton.setBorderPainted(false);
            fileExplorerButton.setFocusable(false);
            fileExplorerButton.setEnabled(false);

            InputStream themeImageStream = ThemeManager.class.getResourceAsStream(ThemeManager.getCurrentTheme().themeManagerImage);
            this.themeManagerButton = new ModernToggleButton(themeImageStream, 0.05);
            themeManagerButton.setBorderPainted(false);
            themeManagerButton.setFocusable(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JPanel build() {
        this.jToolBar.add(fileExplorerButton);
        this.jToolBar.add(themeManagerButton);
        this.mainPanel.add(jToolBar, BorderLayout.CENTER);

        return this.mainPanel;
    }

}
