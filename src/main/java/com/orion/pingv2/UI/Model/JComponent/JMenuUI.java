package com.orion.pingv2.UI.Model.JComponent;

import javax.swing.*;

public class JMenuUI {

    protected JMenu menu = new JMenu();

    public JMenuUI(String text) {
        menu.setText(text);

        this.setupUI();
    }

    public JMenu getMenu() {
        return this.menu;
    }

    private void setupUI() {
    }

    public JMenu build() {
        return this.menu;
    }

}
