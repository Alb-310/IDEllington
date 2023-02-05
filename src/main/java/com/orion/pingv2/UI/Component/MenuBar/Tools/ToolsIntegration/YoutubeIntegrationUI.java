package com.orion.pingv2.UI.Component.MenuBar.Tools.ToolsIntegration;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.DataStruct.ThemeModel;
import com.orion.pingv2.UI.Model.SwingFX.SwingFXWebView;

import javax.swing.*;
import java.awt.*;

public class YoutubeIntegrationUI extends JFrame {

    private String windowName;
    private Dimension dimension = new Dimension();
    private Color baseColor;
    private Color accentColor;

    private SwingFXWebView swingFXWebView;

    public YoutubeIntegrationUI(String windowName, int width, int height) {
        this.windowName = windowName;

        this.dimension.width = width;
        this.dimension.height = height;

        ThemeModel currentTheme = ThemeManager.getCurrentTheme();

        this.baseColor = currentTheme.baseColor;
        this.accentColor = currentTheme.accentColor;

        this.setupLookAndFeel();
        this.initComponents();
    }

    public void setupLookAndFeel() {
        this.setTitle(this.windowName);
        this.setSize(this.dimension);
        this.setPreferredSize(this.dimension);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        // Set the Window in the center of the screen
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenDim.width / 2 - this.getSize().width / 2, screenDim.height / 2 - this.getSize().height / 2);
    }

    public void initComponents() {
        this.swingFXWebView = new SwingFXWebView("https://www.youtube.com");
        this.add(swingFXWebView, BorderLayout.CENTER);
    }

}
