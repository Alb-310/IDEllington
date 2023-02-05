package com.orion.pingv2.UI.Component.MenuBar.File;

import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JMenuUI;

import javax.swing.*;
import java.awt.*;

public class FileMenu extends JMenuUI implements UIBuilder {

    private FileNewMenu fileNewMenu;
    private JMenuItem openMenuItem;
    private JMenuItem saveMenuItem;
    private JMenuItem saveAsMenuItem;

    public FileMenu(String text) {
        super(text);
        this.setupUI();
    }

    public FileNewMenu getFileNewMenu() {
        return fileNewMenu;
    }

    public JMenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public JMenuItem getSaveMenuItem() {
        return saveMenuItem;
    }

    public JMenuItem getSaveAsMenuItem() {
        return saveAsMenuItem;
    }

    @Override
    public void setupUI() {
        this.fileNewMenu = new FileNewMenu("New...");
        this.openMenuItem = new JMenuItem("Open");
        this.saveMenuItem = new JMenuItem("Save");
        this.saveAsMenuItem = new JMenuItem("Save as...");

        openMenuItem.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(
                        java.awt.event.KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()
                )
        );
        saveMenuItem.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(
                        java.awt.event.KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()
                )
        );
        saveAsMenuItem.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(
                        java.awt.event.KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() |
                                                               java.awt.event.InputEvent.SHIFT_DOWN_MASK
                )
        );
    }

    @Override
    public JMenu build() {
        this.menu.add(fileNewMenu.build());

        this.menu.add(openMenuItem);
        this.menu.add(saveMenuItem);
        this.menu.add(saveAsMenuItem);

        return this.menu;
    }

}
