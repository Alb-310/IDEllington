package com.orion.pingv2.UI.Model.ModernUI;


import javax.swing.*;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;

import java.awt.*;
import java.awt.event.*;  



public class ModernClosePane extends JPanel{

    public JTabbedPane tab;
    private JLabel Title;
    public ModernClosePane(GridBagLayout gbl, JTabbedPane tab, String name)
    {
        super(gbl);
        this.tab = tab;
        this.setOpaque(false);
        this.setBackground(ThemeManager.getCurrentTheme().baseColor);
        this.setForeground(ThemeManager.getCurrentTheme().accentColor);
        Title = new JLabel(name);
        
        Title.setBackground(ThemeManager.getCurrentTheme().accentColor);
        Title.setForeground(ThemeManager.getCurrentTheme().baseColor);

        JButton btnClose = new JButton(" X "){
            {
                setSize(10, 10);
                setMaximumSize(getSize());
            }
        };
        
        btnClose.setBackground(ThemeManager.getCurrentTheme().baseColor);
        btnClose.setForeground(Color.RED);
        btnClose.setOpaque(false);
        btnClose.setBorderPainted(false);
        btnClose.setFocusable(false);
        btnClose.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        this.add(Title, gbc);
        gbc.gridx++;
        gbc.gridx++;
        gbc.weightx = 0;
        btnClose.addActionListener(new MyCloseActionHandler(name));
        this.add(btnClose, gbc);

    }
    public class MyCloseActionHandler implements ActionListener 
    {

        public String name;

        public MyCloseActionHandler(String name) {
            this.name = name;
        }

        public void actionPerformed(ActionEvent evt) {

            int index = tab.indexOfTab(name);
            if (index >= 0) {

                tab.removeTabAt(index);
            
            }
            index = tab.getSelectedIndex();
            for(int i = 0; i < tab.getTabCount(); i++)
            {
              if (i == index)
                  ((ModernClosePane)(tab.getTabComponentAt(i))).getTitle().setForeground(ThemeManager.getCurrentTheme().baseColor);
              else
                  ((ModernClosePane)(tab.getTabComponentAt(i))).getTitle().setForeground(ThemeManager.getCurrentTheme().accentColor);
            }

        }
    }
    public JLabel getTitle()
    {
        return Title;
    }   
}
