package com.orion.pingv2.UI.Component;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Component.Editor.FileEditorUI;
import com.orion.pingv2.UI.Component.Editor.TerminalUI;
import com.orion.pingv2.UI.Model.ModernUI.ModernSplitPane;

import javax.swing.*;
import java.awt.*;

public class EditorUI extends JPanelUI implements UIBuilder {

    private Dimension dimension;
    private ModernSplitPane modernSplitPane;
    private FileEditorUI fileEditorUI;
    private TerminalUI terminalUI;

    public EditorUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);

        this.dimension = dimension;

        this.setupUI();
    }

    public FileEditorUI getFileEditorUI() {
        return fileEditorUI;
    }

    public TerminalUI getTerminalUI() {
        return terminalUI;
    }

    @Override
    public void setupUI() {
        this.mainPanel.setPreferredSize(dimension);
        this.mainPanel.setSize(dimension);

        this.modernSplitPane = new ModernSplitPane(JSplitPane.VERTICAL_SPLIT);

        this.fileEditorUI = new FileEditorUI(
                new Dimension(this.dimension.width, (int)(this.dimension.height * 1.15)),
                ThemeManager.getCurrentTheme().baseColor,
                ThemeManager.getCurrentTheme().accentColor
        );

        this.terminalUI = new TerminalUI(
                new Dimension(this.dimension.width, (int)(this.dimension.height * 0.8)),
                ThemeManager.getCurrentTheme().baseColor,
                ThemeManager.getCurrentTheme().accentColor
        );

        this.modernSplitPane.setLeftComponent(this.fileEditorUI.build());
        this.modernSplitPane.setRightComponent(this.terminalUI.build());
    }

    @Override
    public JPanel build() {

        this.mainPanel.add(modernSplitPane, BorderLayout.CENTER);

        return this.mainPanel;
    }

}
