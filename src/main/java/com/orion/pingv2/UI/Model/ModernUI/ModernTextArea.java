package com.orion.pingv2.UI.Model.ModernUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaCompletionProvider;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rtextarea.Gutter;
import org.fife.ui.rtextarea.RTextScrollPane;

import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

public class ModernTextArea {

    private RSyntaxTextArea textArea;
    private RTextScrollPane scrollPane;


    public ModernTextArea(Color bgColor, Color fgColor, File file) {
        super();
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setHighlightCurrentLine(true);
        textArea.setHighlightSecondaryLanguages(false);

        textArea.setBackground(ThemeManager.getCurrentTheme().baseColor);
        textArea.setForeground(ThemeManager.getCurrentTheme().writingColor);
        textArea.setCaretColor(ThemeManager.getCurrentTheme().accentColor);
        textArea.setCurrentLineHighlightColor(ThemeManager.getCurrentTheme().baseVariantColor);

        textArea.setMargin(new Insets(20, 10, 20, 20));

        scrollPane = new RTextScrollPane(textArea);
        scrollPane.setLineNumbersEnabled(true);
        scrollPane.setFoldIndicatorEnabled(false);

        scrollPane.getGutter().setBackground(ThemeManager.getCurrentTheme().baseVariantColor);
        scrollPane.getGutter().setLineNumberColor(ThemeManager.getCurrentTheme().accentColor);

        if (file != null)
            configOpenedFileMenu(file);
    }

    public RSyntaxTextArea getTextArea()
    {
        return textArea;
    }

    public RTextScrollPane getScrollPane() {
        return scrollPane;
    }

    private String getExtension(File file)
    {
        String fileName = file.toString();
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }

        return extension;
    }

    private void configOpenedFileMenu(File file) {
        //JPanel cp = new JPanel(new BorderLayout());
        String ext = getExtension(file);
        textArea.setSyntaxEditingStyle(getStyleForExtension(ext));
        textArea.setCodeFoldingEnabled(false);

        LanguageSupportFactory lsf = LanguageSupportFactory.get();
        LanguageSupport support = lsf.getSupportFor(getStyleForExtension(ext));

        changeColorScheme(textArea);
    }

    private String getStyleForExtension(String extension) {
        return switch (extension) {
            case "java" -> SyntaxConstants.SYNTAX_STYLE_JAVA;
            case "c", "h" -> SyntaxConstants.SYNTAX_STYLE_C;
            case "cc", "cpp", "cxx", "hh", "hxx" -> SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS;
            case "cs" -> SyntaxConstants.SYNTAX_STYLE_CSHARP;
            case "css" -> SyntaxConstants.SYNTAX_STYLE_CSS;
            case "html" -> SyntaxConstants.SYNTAX_STYLE_HTML;
            case "js" -> SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT;
            case "ts" -> SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT;
            case "json" -> SyntaxConstants.SYNTAX_STYLE_JSON;
            case "xml" -> SyntaxConstants.SYNTAX_STYLE_XML;
            case "yaml", "yml" -> SyntaxConstants.SYNTAX_STYLE_YAML;
            case "kt", "kts", "ktm" -> SyntaxConstants.SYNTAX_STYLE_KOTLIN;
            case "md" -> SyntaxConstants.SYNTAX_STYLE_MARKDOWN;
            case "php" -> SyntaxConstants.SYNTAX_STYLE_PHP;
            case "py" -> SyntaxConstants.SYNTAX_STYLE_PYTHON;
            default -> SyntaxConstants.SYNTAX_STYLE_NONE;
        };
    }

    private void changeColorScheme(RSyntaxTextArea textArea) {

        SyntaxScheme scheme = textArea.getSyntaxScheme();

        scheme.getStyle(Token.VARIABLE).foreground = new Color(135, 230, 254);

        scheme.getStyle(Token.FUNCTION).foreground = new Color(255, 241, 54);

        scheme.getStyle(Token.DATA_TYPE).foreground = new Color(36, 242, 230);
        scheme.getStyle(Token.ANNOTATION).foreground = new Color(35, 242, 230);

        scheme.getStyle(Token.RESERVED_WORD_2).foreground = new Color(131, 76, 222);

        scheme.getStyle(Token.RESERVED_WORD).foreground = new Color(78, 146, 207);
        scheme.getStyle(Token.LITERAL_BOOLEAN).foreground = new Color(78, 146, 207);

        // Numbers
        scheme.getStyle(Token.LITERAL_NUMBER_DECIMAL_INT).foreground = new Color(70, 226, 143);
        scheme.getStyle(Token.LITERAL_NUMBER_FLOAT).foreground = new Color(70, 226, 143);
        scheme.getStyle(Token.LITERAL_NUMBER_HEXADECIMAL).foreground = new Color(70, 226, 143);

        scheme.getStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).foreground = new Color(251, 149, 4);

        // Comments
        scheme.getStyle(Token.COMMENT_EOL).foreground = new Color(46, 173, 25);
        scheme.getStyle(Token.COMMENT_DOCUMENTATION).foreground = new Color(46, 173, 25);

        scheme.getStyle(Token.OPERATOR).foreground = Color.GRAY;
        scheme.getStyle(Token.SEPARATOR).foreground = Color.GRAY;

        //scheme.getStyle(Token.IDENTIFIER).foreground = Color.PINK;

        scheme.getStyle(Token.COMMENT_EOL).font = new Font("SF Pro", Font.PLAIN, 13);

        textArea.revalidate();
    }
}
