package com.orion.pingv2.UI.Component.MenuBar.Edit;

import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JMenuUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class EditMenu extends JMenuUI implements UIBuilder {

    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;

    public EditMenu(String text) {
        super(text);
        this.setupUI();
    }

    public JMenuItem getUndoMenuItem() {
        return undoMenuItem;
    }

    public JMenuItem getRedoMenuItem() {
        return redoMenuItem;
    }

    @Override
    public void setupUI() {
        this.undoMenuItem = new JMenuItem("Undo");
        this.redoMenuItem = new JMenuItem("Redo");

        this.undoMenuItem.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(
                        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()
                )
        );

        this.redoMenuItem.setAccelerator(
                javax.swing.KeyStroke.getKeyStroke(
                        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx() |
                                                java.awt.event.InputEvent.SHIFT_DOWN_MASK
                )
        );
    }

    @Override
    public JMenu build() {
        this.menu.add(undoMenuItem);
        this.menu.add(redoMenuItem);

        return this.menu;
    }

}
