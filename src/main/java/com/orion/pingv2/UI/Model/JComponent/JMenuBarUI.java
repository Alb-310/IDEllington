package com.orion.pingv2.UI.Model.JComponent;

import com.orion.pingv2.UI.Model.ModernUI.ModernButton;
import com.orion.pingv2.UI.Model.ModernUI.ModernMenuBar;

import javax.swing.*;
import java.awt.*;

public class JMenuBarUI {

    protected ModernMenuBar menuBar = new ModernMenuBar();
    protected Color bgColor;
    protected Color fgColor;
    protected Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    public JMenuBarUI(Color bgColor, Color fgColor) {
        this.bgColor = bgColor;
        this.fgColor = fgColor;

        this.setupUI();
    }

    public JMenuBar getMenuBar() {
        return this.menuBar;
    }

    private void setupUI() {
        this.menuBar.setBackground(this.bgColor);
        this.menuBar.setForeground(this.fgColor);
    }

    public JMenuBar build() {
        return this.menuBar;
    }

}
