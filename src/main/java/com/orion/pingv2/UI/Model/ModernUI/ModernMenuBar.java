package com.orion.pingv2.UI.Model.ModernUI;

import javax.swing.*;
import java.awt.*;

public class ModernMenuBar extends JMenuBar {
    private Color bgColor;
    private Color fgColor;

    public ModernMenuBar() {
        super();
    }

    public void setBackgroundColor(Color color) {
        this.bgColor = color;
        this.setBackground(this.bgColor);
    }

    public void setForegroundColor(Color color) {
        this.fgColor = color;
        this.setForeground(this.fgColor);
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(this.bgColor);
        g2d.fillRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
    }*/
}


