package com.orion.pingv2.UI.Model.JComponent;

import com.orion.pingv2.UI.Model.ModernUI.ModernComboBoxRenderer;
import com.orion.pingv2.UI.Model.ModernUI.ModernComboxEditor;

import javax.swing.*;
import java.awt.*;

public class JComboBoxUI {

    private JComboBox jComboBox;
    private Dimension dimension;
    private Color bgColor;
    private Color fgColor;

    public JComboBoxUI(Dimension dimension, Color bgColor, Color fgColor) {
        this.dimension = dimension;
        this.bgColor = bgColor;
        this.fgColor = fgColor;

        this.setupUI();
    }

    public JComboBox getjComboBox() {
        return jComboBox;
    }

    public void setupUI() {
        String[] strList = { "Hello", "World"};
        jComboBox = new JComboBox<>();
    }

    public JComboBox build() {

        return jComboBox;
    }

}
