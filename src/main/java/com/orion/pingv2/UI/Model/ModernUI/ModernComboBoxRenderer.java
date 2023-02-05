package com.orion.pingv2.UI.Model.ModernUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ModernComboBoxRenderer extends JLabel implements ListCellRenderer {

    public ModernComboBoxRenderer() {
        setOpaque(true);
        setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 14));
        setBackground(Color.BLUE);
        setForeground(Color.YELLOW);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {
        setText(value.toString());
        return this;
    }

}
