package com.orion.pingv2.UI.Component.SideToolBar;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JComboBoxUI;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Model.ModernUI.ModernButton;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class ThemeManagerUI extends JPanelUI implements UIBuilder {

    private Dimension dimension;
    private JComboBox themeComboBox;
    private JComboBox typeComboBox;
    private JLabel themeSelectLabel;
    private ModernButton applyThemeButton;

    public ThemeManagerUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);
        this.dimension = dimension;

        this.setupUI();
    }

    public Dimension getDimension() {
        return dimension;
    }

    public JComboBox getThemeComboBox() {
        return themeComboBox;
    }

    public JComboBox getTypeComboBox() {
        return typeComboBox;
    }

    public JLabel getThemeSelectLabel() {
        return themeSelectLabel;
    }

    public ModernButton getApplyThemeButton() {
        return applyThemeButton;
    }

    @Override
    public void setupUI() {
        this.mainPanel.setLayout(new GridLayout(30, 1, 0, 0));
        this.mainPanel.setMinimumSize(new Dimension(0, 0));

        this.themeComboBox = new JComboBox<>(ThemeManager.getThemeList());

        String[] typeList = { "Dark" };
        this.typeComboBox = new JComboBox<>(typeList);

        this.themeSelectLabel = new JLabel("Select a theme:");
        Border border = themeSelectLabel.getBorder();
        Border margin = new EmptyBorder(10,8,10,10);
        this.themeSelectLabel.setBorder(new CompoundBorder(border, margin));

        this.themeSelectLabel.setForeground(ThemeManager.getCurrentTheme().accentColor);

        this.applyThemeButton = new ModernButton("Apply selected theme");
    }

    @Override
    public JPanel build() {
        this.mainPanel.add(themeSelectLabel);
        this.mainPanel.add(themeComboBox);
        this.mainPanel.add(typeComboBox);
        this.mainPanel.add(applyThemeButton);

        return this.mainPanel;
    }
}
