package com.orion.pingv2.UI.Manager;

import com.orion.pingv2.Feature.FileManager;
import com.orion.pingv2.Feature.Languages.Compilation;
import com.orion.pingv2.Feature.SoundEffects;
import com.orion.pingv2.UI.Component.Editor.FileEditorUI;
import com.orion.pingv2.UI.Component.MenuBar.Edit.EditMenu;
import com.orion.pingv2.UI.Component.MenuBar.File.FileMenu;
import com.orion.pingv2.UI.Component.MenuBar.Tools.ToolsIntegrationMenu;
import com.orion.pingv2.UI.Component.MenuBar.Tools.ToolsMenu;
import com.orion.pingv2.UI.Component.MenuBarUI;
import com.orion.pingv2.UI.Component.SideToolBar.FileExplorerUI;
import com.orion.pingv2.UI.Component.SideToolBar.ThemeManagerUI;
import com.orion.pingv2.UI.Component.SideToolBar.ToolPanelUI;
import com.orion.pingv2.UI.Component.SideToolBarUI;
import com.orion.pingv2.UI.IDEllingtonUI;
import com.orion.pingv2.UI.Manager.Theme.Theme;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.ModernUI.ModernButton;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.util.Objects;

public class ActionListenerManager {

    private IDEllingtonUI idEllingtonUI;
    private JTree jTree;
    private JTabbedPane jTabbedPane;

    public ActionListenerManager(IDEllingtonUI idEllingtonUI) {
        this.idEllingtonUI = idEllingtonUI;

        this.jTabbedPane = this.idEllingtonUI.getEditorUI().getFileEditorUI().getjTabbedPane();
        this.jTree = this.idEllingtonUI.getSideToolBarUI().getFileExplorerUI().getjTree();
    }

    public void bindMenuBarActions() {
        MenuBarUI menuBarUI = idEllingtonUI.getMenuBarUI();

        FileMenu fileMenu = menuBarUI.getFileMenu();
        JMenuItem openMenuItem = fileMenu.getOpenMenuItem();
        JMenuItem saveMenuItem = fileMenu.getSaveMenuItem();
        JMenuItem saveAsMenuItem = fileMenu.getSaveAsMenuItem();
        JMenuItem newFileItem = fileMenu.getFileNewMenu().getFileMenuItem();

        EditMenu editMenu = menuBarUI.getEditMenu();
        JMenuItem undoMenuItem = editMenu.getUndoMenuItem();
        JMenuItem redoMenuItem = editMenu.getRedoMenuItem();

        ToolsMenu toolsMenu = menuBarUI.getToolsMenu();
        ToolsIntegrationMenu toolsIntegrationMenu = toolsMenu.getToolsIntegrationMenu();
        JMenuItem youtubeMenuItem = toolsIntegrationMenu.getYoutubeItem();

        SideToolBarUI sideToolBarUI = idEllingtonUI.getSideToolBarUI();
        ToolPanelUI toolPanelUI = sideToolBarUI.getToolPanelUI();
        JToggleButton fileExplorerButton = toolPanelUI.getFileExplorerButton();

        newFileItem.addActionListener(event -> {
            FileManager.newFile(this.idEllingtonUI, this.jTree, this.jTabbedPane);
        });

        openMenuItem.addActionListener(event -> {

            FileManager.open(this.idEllingtonUI, this.jTree, this.jTabbedPane);

            FileEditorUI fileEditorUI = idEllingtonUI.getEditorUI().getFileEditorUI();
            JButton runButton = fileEditorUI.getRunButton();

            sideToolBarUI.setToolPanel(sideToolBarUI.getFileExplorerPanel());

            if (Compilation.getIsRunnable()) {
                runButton.addActionListener(event1 -> {
                    // Commented because it would overlap with compilation sound
                    //SoundEffects.runSound();
                    String output = Compilation.run(FileExplorerUI.getLanguages());
                    idEllingtonUI.getEditorUI().getTerminalUI().appendOutputText(output);

                    if (Compilation.getLastReturnValue() != 0)
                        SoundEffects.errorSound();
                    else
                        SoundEffects.successSound();
                });

                runButton.setEnabled(true);
            }
        });

        saveAsMenuItem.addActionListener(event -> {
            FileManager.saveAs(this.idEllingtonUI, this.jTree, this.jTabbedPane);
        });

        saveMenuItem.addActionListener(event -> {
            FileManager.save(this.idEllingtonUI, this.jTree, this.jTabbedPane);
        });

        undoMenuItem.addActionListener(event -> {
            RTextScrollPane rTextScrollPane = (RTextScrollPane) idEllingtonUI.getEditorUI()
                    .getFileEditorUI()
                    .getjTabbedPane()
                    .getSelectedComponent();

            JViewport jViewport = (JViewport) rTextScrollPane.getComponent(0);
            RSyntaxTextArea rSyntaxTextArea = (RSyntaxTextArea) jViewport.getComponent(0);

            if (rSyntaxTextArea.canUndo())
                rSyntaxTextArea.undoLastAction();
        });

        redoMenuItem.addActionListener(event -> {
            RTextScrollPane rTextScrollPane = (RTextScrollPane) idEllingtonUI.getEditorUI()
                    .getFileEditorUI()
                    .getjTabbedPane()
                    .getSelectedComponent();

            JViewport jViewport = (JViewport) rTextScrollPane.getComponent(0);
            RSyntaxTextArea rSyntaxTextArea = (RSyntaxTextArea) jViewport.getComponent(0);

            if (rSyntaxTextArea.canRedo()) {
                rSyntaxTextArea.redoLastAction();
            }
        });
    }

    public void bindSideToolBarActions() {
        SideToolBarUI sideToolBarUI = this.idEllingtonUI.getSideToolBarUI();
        ToolPanelUI toolPanelUI = sideToolBarUI.getToolPanelUI();

        JToggleButton fileExplorerButton = toolPanelUI.getFileExplorerButton();
        JToggleButton themeManagerButton = toolPanelUI.getThemeManagerButton();

        fileExplorerButton.addActionListener(event -> {
            if (fileExplorerButton.isSelected()) {
                themeManagerButton.setSelected(false);
                sideToolBarUI.setToolPanel(sideToolBarUI.getFileExplorerPanel());
                idEllingtonUI.getModernSplitPane().setDividerLocation((toolPanelUI.getDimension().width * 6));
            }
            else
                idEllingtonUI.getModernSplitPane().setDividerLocation((int)(toolPanelUI.getDimension().width + (toolPanelUI.getDimension().width * 0.17)));
        });

        themeManagerButton.addActionListener(event -> {
            if (themeManagerButton.isSelected()) {
                fileExplorerButton.setSelected(false);
                sideToolBarUI.setToolPanel(sideToolBarUI.getThemeManagerPanel());
                idEllingtonUI.getModernSplitPane().setDividerLocation((toolPanelUI.getDimension().width * 6));
            }
            else
                idEllingtonUI.getModernSplitPane().setDividerLocation((int)(toolPanelUI.getDimension().width + (toolPanelUI.getDimension().width * 0.10)));
        });

    }

    public void bindThemeManagerUI() {
        ThemeManagerUI themeManagerUI = idEllingtonUI.getSideToolBarUI().getThemeManagerUI();
        JComboBox themeComboBox = themeManagerUI.getThemeComboBox();
        JComboBox typeComboBox = themeManagerUI.getTypeComboBox();
        JButton applyTheme = themeManagerUI.getApplyThemeButton();

        applyTheme.addActionListener(event -> {
            String themeName = (String) themeComboBox.getSelectedItem();

            if ((Objects.requireNonNull(typeComboBox.getSelectedItem())).equals("Dark")) {
                ThemeManager.setCurrentTheme(themeName, Theme.Type.DARK);
            }
            else
                ThemeManager.setCurrentTheme(themeName, Theme.Type.BRIGHT);
        });
    }

    public void bindActions() {
        if (this.idEllingtonUI != null) {
            this.bindMenuBarActions();
            this.bindSideToolBarActions();
            this.bindThemeManagerUI();
        }
    }

}
