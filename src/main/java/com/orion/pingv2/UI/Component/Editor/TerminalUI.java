package com.orion.pingv2.UI.Component.Editor;

import com.orion.pingv2.Feature.Terminal;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JPanelUI;
import com.orion.pingv2.UI.Model.ModernUI.ModernTextArea;
import com.orion.pingv2.Utils.Utils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.regex.Matcher;

public class TerminalUI extends JPanelUI implements UIBuilder {

    private Dimension dimension;
    private Terminal terminal;
    private RSyntaxTextArea terminalTextArea;
    private JScrollPane jScrollPane;

    public TerminalUI(Dimension dimension, Color bgColor, Color fgColor) {
        super(bgColor, fgColor);

        this.dimension = dimension;
        this.terminal = new Terminal();

        this.setupUI();
    }

    public RSyntaxTextArea getTerminalTextArea() {
        return terminalTextArea;
    }

    public JScrollPane getjScrollPane() {
        return jScrollPane;
    }

    public Terminal getTerminal() {
        return this.terminal;
    }

    @Override
    public void setupUI() {
        ModernTextArea terminalBuilder = new ModernTextArea(ThemeManager.getCurrentTheme().baseColor, Color.WHITE, null);
        this.terminalTextArea = terminalBuilder.getTextArea();
        this.terminalTextArea.setHighlightCurrentLine(false);
        this.mainPanel.setMinimumSize(new Dimension(0, 0));

        this.terminalTextArea.setColumns(20);
        this.terminalTextArea.setRows(5);
        this.terminalTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                terminalKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                terminalKeyReleased(evt);
            }
        });

        this.terminalTextArea.append(this.terminal.getPrompt());
    }

    @Override
    public JPanel build() {
        jScrollPane = new JScrollPane();
        Border border = new LineBorder(ThemeManager.getCurrentTheme().accentColor, 1, true);
        jScrollPane.setBorder(border);
        jScrollPane.setViewportView(this.terminalTextArea);

        this.mainPanel.add(jScrollPane, BorderLayout.CENTER);

        return this.mainPanel;
    }

    private void terminalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_terminalTextAreaKeyPressed
        String lastLine = Utils.getLastLine(terminalTextArea.getText());

        int terminalPromptLength = terminal.getPrompt().length();
        int textLength = terminalTextArea.getText().length();
        int absoluteCaretPosition = textLength - terminalTextArea.getCaretPosition();
        int currentCaretPosition = lastLine.length() - absoluteCaretPosition;

        if (currentCaretPosition <= terminalPromptLength) {
            terminalTextArea.append(" ");
            terminalTextArea.setCaretPosition(terminalTextArea.getText().length());
        }
    }

    private void terminalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_terminalTextAreaKeyReleased
        if (evt.getKeyCode() == 10) {
            String lastLine = Utils.getLastLine(terminalTextArea.getText());

            Matcher matcher = Utils.execRegex("\\[.*\\]\\$[ ]*(?<commandLine>.*)", lastLine);

            if (matcher.find()) {
                String commandLine = matcher.group(1);
                matcher = Utils.execRegex("(?<command>.*)[ ](?<dir>.*)", commandLine);

                if (commandLine.equals("clear")) {
                    this.clearTerminal();
                    return;
                }
                else
                    terminalTextArea.append(terminal.command(commandLine));
            }

            terminalTextArea.append(terminal.getPrompt() + " ");
            terminalTextArea.setCaretPosition(terminalTextArea.getText().length());
        }
    }

    public void clearTerminal() {
        this.terminalTextArea.setText(terminal.getPrompt() + " ");
        terminalTextArea.setCaretPosition(terminalTextArea.getText().length());
    }

    public void appendOutputText(String outputText) {
        terminalTextArea.append("\n");
        terminalTextArea.append(outputText);

        terminalTextArea.append(terminal.getPrompt() + " ");
        terminalTextArea.setCaretPosition(terminalTextArea.getText().length());
    }

}
