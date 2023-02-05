/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.orion.pingv2.UI;

import com.orion.pingv2.UI.Component.EditorUI;
import com.orion.pingv2.UI.Component.MenuBarUI;
import com.orion.pingv2.UI.Component.SideToolBarUI;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.DataStruct.ThemeModel;
import com.orion.pingv2.UI.Model.ModernUI.ModernSplitPane;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author tom.termaat
 */
public class IDEllingtonUI extends JFrame {

    private String appName;
    private Dimension dimension = new Dimension();
    private MenuBarUI menuBarUI;
    private SideToolBarUI sideToolBarUI;
    private JPanel sideToolBarPanel;
    private EditorUI editorUI;

    private ModernSplitPane modernSplitPane;

    public IDEllingtonUI(String appName, int width, int height) {
        this.appName = appName;

        this.dimension.width = width;
        this.dimension.height = height;

        ThemeModel currentTheme = ThemeManager.getCurrentTheme();

        this.setupLookAndFeel();
        this.initComponents();
    }

    public EditorUI getEditorUI() {
        return editorUI;
    }

    public MenuBarUI getMenuBarUI() {
        return menuBarUI;
    }

    public SideToolBarUI getSideToolBarUI() {
        return sideToolBarUI;
    }

    public ModernSplitPane getModernSplitPane() {
        return modernSplitPane;
    }

    private void initComponents() {
        this.menuBarUI = new MenuBarUI(
                this.dimension,
                new Color( 190, 190, 210),
                ThemeManager.getCurrentTheme().accentColor
        );

        this.sideToolBarUI = new SideToolBarUI(
                new Dimension((int)(dimension.width * 0.2), dimension.height),
                ThemeManager.getCurrentTheme().baseColor,
                ThemeManager.getCurrentTheme().accentColor
        );
        sideToolBarPanel = this.sideToolBarUI.build();

        this.editorUI = new EditorUI(
                new Dimension((int)(dimension.width * 0.6), dimension.height),
                ThemeManager.getCurrentTheme().baseColor,
                ThemeManager.getCurrentTheme().accentColor
        );

        Border border = new LineBorder(ThemeManager.getCurrentTheme().accentColor, 1, true);

        modernSplitPane = new ModernSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        modernSplitPane.setBorder(border);
        modernSplitPane.setLeftComponent(sideToolBarPanel);
        modernSplitPane.setRightComponent(this.editorUI.build());
        modernSplitPane.setDividerLocation(this.getSideToolBarUI().getToolPanelUI().getDimension().width);

        this.add(this.modernSplitPane, BorderLayout.CENTER);

        this.setJMenuBar(this.menuBarUI.build());
    }

    private void setupLookAndFeel() {
        this.setTitle(this.appName);
        this.setSize(this.dimension);
        this.setPreferredSize(this.dimension);
        this.setLayout(new BorderLayout());

        InputStream appLogoStream = ThemeManager.class.getResourceAsStream("/images/idellington_logo.png");
        try {
            ImageIcon imageIcon = new ImageIcon(ImageIO.read(appLogoStream));
            this.setIconImage(imageIcon.getImage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Set the Window in the center of the screen
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenDim.width / 2 - this.getSize().width / 2, screenDim.height / 2 - this.getSize().height / 2);

        // Set Menu outside of the app for macOS
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        // Use darkmode for macOS (black window bar)
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
    }

    public JPanel getSideToolBarPanel() {
        return sideToolBarPanel;
    }
}
