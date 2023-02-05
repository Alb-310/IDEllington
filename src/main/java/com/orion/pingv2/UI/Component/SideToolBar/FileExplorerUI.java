package com.orion.pingv2.UI.Component.SideToolBar;

import com.orion.pingv2.Feature.Languages.Languages;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Model.JComponent.JTreeUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FileExplorerUI extends JPanelUI implements UIBuilder {

    private static Languages languages;
    private JTreeUI jTreeUI;
    private Dimension dimension;

    public FileExplorerUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);

        this.dimension = dimension;

        this.setupUI();
    }

    public JTreeUI getjTreeUI() {
        return jTreeUI;
    }

    public JTree getjTree() {
        return jTreeUI.getjTree();
    }

    public static Languages getLanguages() {
        return languages;
    }

    public static void setLanguages(Languages languages) {
        FileExplorerUI.languages = languages;
    }

    @Override
    public void setupUI() {
        this.mainPanel.setSize(dimension);
        this.mainPanel.setPreferredSize(dimension);
        this.mainPanel.setMinimumSize(new Dimension(0, 0));

        this.jTreeUI = new JTreeUI(ThemeManager.getCurrentTheme().baseColor, ThemeManager.getCurrentTheme().accentColor);
    }

    @Override
    public JPanel build() {
        JScrollPane jScrollPane = new JScrollPane();
        Border border = new LineBorder(ThemeManager.getCurrentTheme().accentColor, 1, true);
        jScrollPane.setBorder(border);
        jScrollPane.setViewportView(this.jTreeUI.build());

        this.mainPanel.add(jScrollPane);

        return this.mainPanel;
    }
}
