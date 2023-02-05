package com.orion.pingv2.UI.Component.Editor;

import com.orion.pingv2.Feature.FileManager;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.ModernUI.ModernBasicTabbedPane;
import com.orion.pingv2.UI.Model.ModernUI.ModernButton;
import com.orion.pingv2.UI.Model.ModernUI.ModernTextArea;

import com.orion.pingv2.UI.Model.ModernUI.ModernClosePane;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;

public class FileEditorUI extends JPanelUI implements UIBuilder {
    private Dimension dimension;
    private JTabbedPane jTabbedPane;
    private JToolBar leadingButtons;
    private JToolBar trailingButtons;
    private ModernBasicTabbedPane modernBasicTabbedPane;
    private JButton runButton;
    private ComponentOrientation orient = ComponentOrientation.RIGHT_TO_LEFT;

    public enum ButtonSide {
        LEADING,
        TRAILING;
    }
    public FileEditorUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);

        this.dimension = new Dimension(dimension.width, (int)(dimension.getHeight() * 0.6));

        this.setupUI();
    }

    public JButton getRunButton() {
        return runButton;
    }

    public JTabbedPane getjTabbedPane() {
        return this.jTabbedPane;
    }

    @Override
    public void setupUI() {
        this.mainPanel.setSize(dimension);
        this.mainPanel.setPreferredSize(dimension);

        SpringLayout sl = new SpringLayout();
        this.mainPanel.setLayout(sl);

        this.mainPanel.setBackground(ThemeManager.getCurrentTheme().baseVariantColor);

        this.leadingButtons = new JToolBar();
        this.leadingButtons.setBorderPainted(false);
        this.leadingButtons.setFloatable(false);
        this.leadingButtons.setOpaque(false);
        this.leadingButtons.setComponentOrientation(this.orient);
        this.leadingButtons.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));

        sl.putConstraint(SpringLayout.NORTH, this.leadingButtons, 0, SpringLayout.NORTH, this.mainPanel);
        String side = ((orient == ComponentOrientation.RIGHT_TO_LEFT) ? SpringLayout.EAST : SpringLayout.WEST);
        sl.putConstraint(side, this.leadingButtons, 0, side, this.mainPanel);


        this.trailingButtons = new JToolBar();
        this.trailingButtons.setBorderPainted(false);
        this.trailingButtons.setFloatable(false);
        this.trailingButtons.setOpaque(false);
        this.trailingButtons.setComponentOrientation(orient);
        this.trailingButtons.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));

        sl.putConstraint(SpringLayout.NORTH, this.trailingButtons, 5, SpringLayout.NORTH, this.mainPanel);
        side = ((orient == ComponentOrientation.RIGHT_TO_LEFT) ? SpringLayout.WEST : SpringLayout.EAST);
        sl.putConstraint(side, this.trailingButtons, 0, side, this.mainPanel);

        this.jTabbedPane = new JTabbedPane();
        jTabbedPane.addChangeListener(changeListener);
        this.jTabbedPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.jTabbedPane.setBackground(ThemeManager.getCurrentTheme().accentColor);
        this.jTabbedPane.setForeground(ThemeManager.getCurrentTheme().baseColor);

        Border border = new LineBorder(ThemeManager.getCurrentTheme().accentColor, 1, true);
        this.jTabbedPane.setBorder(border);

        sl.putConstraint(SpringLayout.NORTH, this.jTabbedPane, 0, SpringLayout.NORTH, this.mainPanel);
        sl.putConstraint(SpringLayout.SOUTH, this.jTabbedPane, 0, SpringLayout.SOUTH, this.mainPanel);
        sl.putConstraint(SpringLayout.WEST, this.jTabbedPane, 0, SpringLayout.WEST, this.mainPanel);
        sl.putConstraint(SpringLayout.EAST, this.jTabbedPane, 0, SpringLayout.EAST, this.mainPanel);

        this.modernBasicTabbedPane = new ModernBasicTabbedPane();
        this.jTabbedPane.setUI(this.modernBasicTabbedPane);

        this.mainPanel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (jTabbedPane.getTabCount() > 0) {
                    jTabbedPane.getSelectedComponent().requestFocusInWindow();
                }
            }
        });

        try {
            InputStream runImageStream = ThemeManager.class.getResourceAsStream("/images/run.png");
            runButton = new ModernButton(runImageStream, 0.04);
            runButton.setBackground(ThemeManager.getCurrentTheme().baseVariantColor);
            runButton.setEnabled(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addButton(JButton button, ButtonSide side) {
        button.setBorderPainted(false);
        button.setFocusable(false);
        button.setMargin(new Insets(4, 1, 1, 40));
        ((side == ButtonSide.LEADING) ? this.leadingButtons : this.trailingButtons).add(button);
        this.mainPanel.validate();
    }

    @Override
    public JPanel build() {
        this.mainPanel.add(leadingButtons);
        ModernTextArea welcome = new ModernTextArea(ThemeManager.getCurrentTheme().baseColor, ThemeManager.getCurrentTheme().accentColor, null);

        welcome.getTextArea().setText(
                "Hi, Welcome to IDEllington!\n" +
                "\n" +
                "Here are the shortcuts you can use:\n" +
                "\n" +
                "- Ctrl + O: Open a file\n" +
                "- Ctrl + S: Save a file\n" +
                "- Ctrl + Shift + S: Save a file as\n" +
                "- Ctrl + Z: Undo\n" +
                "- Ctrl + Y: Redo\n" +
                "\n" +
                "You can also access to YouTube, to do so, click on \"Tools\" > \"Integration\" > \"Youtube\"\n" +
                "\n" +
                "Enjoy coding!\n" +
                "\n" +
                "--\n" +
                "Orion Corp."
        );

        this.jTabbedPane.add("Welcome", welcome.getScrollPane());
        int index = jTabbedPane.indexOfTab("Welcome");
        JPanel tab = new ModernClosePane(new GridBagLayout(), jTabbedPane, "Welcome");
        jTabbedPane.setTabComponentAt(index, tab);

        this.addButton(runButton, ButtonSide.LEADING);


        this.mainPanel.add(this.jTabbedPane);

        return this.mainPanel;
    }

    ChangeListener changeListener = new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
          int index = jTabbedPane.getSelectedIndex();
          if (jTabbedPane.getTabCount() <= 1)
            return;
          for(int i = 0; i < jTabbedPane.getTabCount(); i++)
          {
            if (i == index)
                ((ModernClosePane)(jTabbedPane.getTabComponentAt(i))).getTitle().setForeground(ThemeManager.getCurrentTheme().baseColor);
            else
                ((ModernClosePane)(jTabbedPane.getTabComponentAt(i))).getTitle().setForeground(ThemeManager.getCurrentTheme().accentColor);
          }
        }
      };
}
