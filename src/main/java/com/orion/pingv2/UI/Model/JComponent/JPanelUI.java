package com.orion.pingv2.UI.Model.JComponent;


import com.orion.pingv2.UI.Model.Interface.UIBuilder;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class JPanelUI {

    protected JPanel mainPanel = new JPanel();
    protected Color bgColor;
    protected Color fgColor;
    protected Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

    public JPanelUI(Color bgColor, Color fgColor) {
        this.bgColor = bgColor;
        this.fgColor = fgColor;
        this.setupUI();
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    private void setupUI() {
        this.mainPanel.setBackground(this.bgColor);
        this.mainPanel.setForeground(this.fgColor);
        this.mainPanel.setLayout(new BorderLayout());


        Border border = new LineBorder(this.fgColor, 1, true);
        this.mainPanel.setBorder(border);
    }

    public JPanel build() {
        return this.mainPanel;
    }

}
