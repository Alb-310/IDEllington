package com.orion.pingv2.UI.Manager.Theme;

import com.orion.pingv2.UI.Component.Editor.FileEditorUI;
import com.orion.pingv2.UI.Component.Editor.TerminalUI;
import com.orion.pingv2.UI.Component.EditorUI;
import com.orion.pingv2.UI.Component.MenuBar.Edit.EditMenu;
import com.orion.pingv2.UI.Component.MenuBar.File.FileMenu;
import com.orion.pingv2.UI.Component.MenuBar.File.FileNewMenu;
import com.orion.pingv2.UI.Component.MenuBar.Tools.ToolsIntegrationMenu;
import com.orion.pingv2.UI.Component.MenuBar.Tools.ToolsMenu;
import com.orion.pingv2.UI.Component.MenuBarUI;
import com.orion.pingv2.UI.Component.SideToolBar.FileExplorerUI;
import com.orion.pingv2.UI.Component.SideToolBar.ThemeManagerUI;
import com.orion.pingv2.UI.Component.SideToolBar.ToolPanelUI;
import com.orion.pingv2.UI.Component.SideToolBarUI;
import com.orion.pingv2.UI.IDEllingtonUI;
import com.orion.pingv2.UI.Model.DataStruct.ThemeModel;
import com.orion.pingv2.UI.Model.JComponent.JMenuUI;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Model.JComponent.JTreeUI;
import com.orion.pingv2.UI.Model.ModernUI.ModernToggleButton;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.Vector;

public class ThemeManager {

    private static IDEllingtonUI idEllingtonUI;
    private static ThemeModel currentTheme;
    private static final Vector<Theme> themeList = new Vector<>();
    public static ThemeModel getCurrentTheme() {
        return currentTheme;
    }

    public static Vector<String> getThemeList() {
        Vector<String> themeByNameList = new Vector<>();

        for (Theme theme : themeList) {
            themeByNameList.add(theme.getName());
        }

        return themeByNameList;
    }

    public static void addTheme(Theme theme) {
        themeList.add(theme);
    }

    private static void addBuiltinTheme() {
        addTheme(
                new Theme(
                        "Ellington Gold",
                        new Color[]{ new Color(29, 29, 30), new Color(49, 50, 52)},
                        new Color[]{ new Color(255, 255, 255), new Color(121, 121, 121) },
                        new Color(218, 175, 94),
                        new Color(184, 203, 218),
                        "/images/explorer_gold.png",
                        "/images/paintbrush_gold.png"
                )
        );

        addTheme(
                new Theme(
                        "Ellington Silver",
                        new Color[]{ new Color(29, 29, 30), new Color(49, 50, 52)},
                        new Color[]{ new Color(255, 255, 255), new Color(175, 185, 190) },
                        new Color(184, 203, 218),
                        new Color(218, 175, 94),
                        "/images/explorer_silver.png",
                        "/images/paintbrush_silver.png"
                )
        );
    }

    public static void applyTheme() {
        try {
            MenuBarUI menuBarUI = idEllingtonUI.getMenuBarUI();

            SideToolBarUI sideToolBarUI = idEllingtonUI.getSideToolBarUI();
            EditorUI editorUI = idEllingtonUI.getEditorUI();

            FileMenu fileMenu = menuBarUI.getFileMenu();
            FileNewMenu fileNewMenu = fileMenu.getFileNewMenu();

            EditMenu editMenu = menuBarUI.getEditMenu();
            ToolsMenu toolsMenu = menuBarUI.getToolsMenu();
            ToolsIntegrationMenu toolsIntegrationMenu = toolsMenu.getToolsIntegrationMenu();

            FileExplorerUI fileExplorerUI = sideToolBarUI.getFileExplorerUI();
            JTreeUI jTreeUI = fileExplorerUI.getjTreeUI();

            ThemeManagerUI themeManagerUI = sideToolBarUI.getThemeManagerUI();

            ToolPanelUI toolPanelUI = sideToolBarUI.getToolPanelUI();

            FileEditorUI fileEditorUI = editorUI.getFileEditorUI();
            TerminalUI terminalUI = editorUI.getTerminalUI();

            Border border = new LineBorder(currentTheme.accentColor, 1, true);

            JPanelUI[] jPanels = {
                    sideToolBarUI,
                    editorUI,
                    fileExplorerUI,
                    toolPanelUI,
                    fileEditorUI,
                    terminalUI,
                    themeManagerUI,
            };

            JTreeUI[] jTreeUIS = {
                    jTreeUI,
            };

            for (JPanelUI jPanelUI : jPanels) {
                JPanel jPanel = jPanelUI.getMainPanel();
                jPanel.setBackground(currentTheme.baseColor);
                jPanel.setForeground(currentTheme.accentColor);
                jPanel.setBorder(border);
            }

            for (JTreeUI jTreeUI_ : jTreeUIS) {
                JTree jTree = jTreeUI_.getjTree();
                jTree.setBackground(currentTheme.baseColor);
                jTree.setForeground(currentTheme.accentColor);
            }

            fileExplorerUI.getMainPanel().setBackground(currentTheme.baseColor);
            fileExplorerUI.getMainPanel().setForeground(currentTheme.accentColor);
            themeManagerUI.getThemeSelectLabel().setForeground(currentTheme.accentColor);
            toolPanelUI.getMainPanel().setBackground(currentTheme.baseColor);
            toolPanelUI.getMainPanel().setForeground(currentTheme.accentColor);

            fileEditorUI.getMainPanel().setBackground(currentTheme.baseVariantColor);
            fileEditorUI.getjTabbedPane().setBackground(currentTheme.accentColor);
            fileEditorUI.getjTabbedPane().setForeground(currentTheme.baseColor);
            fileEditorUI.getjTabbedPane().setBorder(border);

            InputStream explorerImageStream = ThemeManager.class.getResourceAsStream(ThemeManager.getCurrentTheme().explorerImage);
            InputStream themeImageStream = ThemeManager.class.getResourceAsStream(ThemeManager.getCurrentTheme().themeManagerImage);

            toolPanelUI.getFileExplorerButton().setImageIcon(explorerImageStream, 0.05);
            toolPanelUI.getThemeManagerButton().setImageIcon(themeImageStream, 0.05);

            for (int i = 0; i < fileEditorUI.getjTabbedPane().getTabCount(); i++) {
                RTextScrollPane rTextScrollPane = (RTextScrollPane) fileEditorUI.getjTabbedPane().getComponentAt(i);
                JViewport jViewport = (JViewport) rTextScrollPane.getComponent(0);
                RSyntaxTextArea rSyntaxTextArea = (RSyntaxTextArea) jViewport.getComponent(0);

                rSyntaxTextArea.setBackground(currentTheme.baseColor);
                rSyntaxTextArea.setForeground(currentTheme.writingColor);
                rSyntaxTextArea.setCaretColor(currentTheme.accentColor);
                rSyntaxTextArea.setCurrentLineHighlightColor(currentTheme.baseVariantColor);

                rTextScrollPane.getGutter().setBackground(currentTheme.baseVariantColor);
                rTextScrollPane.getGutter().setLineNumberColor(currentTheme.accentColor);
            }

            toolPanelUI.getjToolBar().setBackground(currentTheme.baseColor);

            terminalUI.getjScrollPane().setBorder(border);
            terminalUI.getTerminalTextArea().setBackground(currentTheme.baseColor);
            terminalUI.getTerminalTextArea().setForeground(currentTheme.accentColor);
            terminalUI.getTerminalTextArea().setBorder(border);

            idEllingtonUI.validate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setCurrentTheme(Theme theme, Theme.Type type) {
        currentTheme = type == Theme.Type.BRIGHT ? theme.getBrightTheme() : theme.getDarkTheme();
        applyTheme();
    }

    public static void setCurrentTheme(String name, Theme.Type type) {
        for (Theme theme : themeList) {
            if (theme.getName().equals(name)) {
                setCurrentTheme(theme, type);
                return;
            }

        }

        System.out.println("Theme: " + name + " not found...");
    }

    public static void beforeInit() {
        addBuiltinTheme();
        currentTheme = themeList.get(0).getDarkTheme();
    }

    public static void afterInit(IDEllingtonUI idEllington) {
        idEllingtonUI = idEllington;
    }

}
