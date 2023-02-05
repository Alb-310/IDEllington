package com.orion.pingv2.Feature;

import com.orion.pingv2.Feature.Languages.Compilation;
import com.orion.pingv2.Feature.Languages.Languages;
import com.orion.pingv2.Feature.Languages.LanguagesRecognition;
import com.orion.pingv2.UI.Component.SideToolBar.FileExplorerUI;
import com.orion.pingv2.UI.IDEllingtonUI;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.DataStruct.FileTreeModel;
import com.orion.pingv2.UI.Model.JComponent.JTreeUI;
import com.orion.pingv2.UI.Model.ModernUI.ModernClosePane;
import com.orion.pingv2.UI.Model.ModernUI.ModernTextArea;
import com.orion.pingv2.Utils.Utils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class FileManager {

    public static File currentFileOpened;

    public static void open(File file, JTabbedPane jTabbedPane) {
        int index = jTabbedPane.indexOfTab(file.getName());
        if (index < 0)
        {
            ModernTextArea textAreaBuilder = new ModernTextArea(ThemeManager.getCurrentTheme().baseColor, ThemeManager.getCurrentTheme().writingColor, file);
            textAreaBuilder.getTextArea().setText(Utils.getFileContent(file.getPath()));
            textAreaBuilder.getTextArea().setCaretPosition(0);

            jTabbedPane.add(file.getName(), textAreaBuilder.getScrollPane());
            index = jTabbedPane.indexOfTab(file.getName());

            JPanel tab = new ModernClosePane(new GridBagLayout(), jTabbedPane, file.getName());

            jTabbedPane.setTabComponentAt(index, tab);
            jTabbedPane.setSelectedComponent(textAreaBuilder.getScrollPane());
        }
        else
            jTabbedPane.setSelectedIndex(index);

        currentFileOpened = file;
    }

    public static void open(IDEllingtonUI idEllingtonUI, JTree jTree, JTabbedPane jTabbedPane) {
        JToggleButton fileExplorerButton = idEllingtonUI.getSideToolBarUI().getToolPanelUI().getFileExplorerButton();

        JFileChooser j = new JFileChooser();

        j.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        j.setDialogType(JFileChooser.OPEN_DIALOG);

        if (j.showOpenDialog(idEllingtonUI) == 0) {
            File file = new File(j.getSelectedFile().getAbsolutePath());

            if (file.isDirectory()) {
                // Init Jtree
                JTreeUI.jTabbedPane = jTabbedPane;
                JTreeUI.rootPath = file.toPath();
                JTreeUI.idEllingtonUI = idEllingtonUI;

                FileTreeModel fileTreeModel = new FileTreeModel(new FileTreeModel.MyFile(file));

                Optional<Languages> optionalLanguages = LanguagesRecognition.recognise(fileTreeModel);

                if (optionalLanguages.isPresent()) {
                    Languages languages = optionalLanguages.get();
                    FileExplorerUI.setLanguages(languages);
                    Compilation.compilePrep(languages);
                }
                else
                    System.out.println("Language not recognized");

                jTree.setModel(fileTreeModel);

                fileExplorerButton.setEnabled(true);
                fileExplorerButton.setSelected(true);

                idEllingtonUI.getModernSplitPane()
                             .setDividerLocation(
                                     idEllingtonUI.getSideToolBarUI()
                                                  .getToolPanelUI()
                                                  .getDimension().width * 6
                             );

                idEllingtonUI.getEditorUI().getTerminalUI().getTerminal().setCurrentDirectory(file.getAbsolutePath());
                idEllingtonUI.getEditorUI().getTerminalUI().clearTerminal();
            }
            else {
                FileTreeModel fileTreeModel = new FileTreeModel(new FileTreeModel.MyFile(file));
                Optional<Languages> optionalLanguages = LanguagesRecognition.recognise(fileTreeModel);

                if (optionalLanguages.isPresent()) {
                    Languages languages = optionalLanguages.get();
                    FileExplorerUI.setLanguages(languages);
                    Compilation.compilePrep(languages);
                }
                else
                    System.out.println("Language not recognized");

                open(file, jTabbedPane);
            }
        }
    }

    public static void saveAs(IDEllingtonUI idEllingtonUI, JTree jTree, JTabbedPane jTabbedPane) {
        if (currentFileOpened != null) {
            JFileChooser j = new JFileChooser();

            // Open the save dialog
            j.setFileSelectionMode(JFileChooser.FILES_ONLY);
            j.setDialogType(JFileChooser.SAVE_DIALOG);

            if (j.showSaveDialog(idEllingtonUI) == 0) {
                File file = new File(j.getSelectedFile().getAbsolutePath());

                if (file.exists()) {
                    int choice = JOptionPane.showConfirmDialog(idEllingtonUI, "Le fichier existe déjà, voulez-vous l'écraser ?");

                    if (choice == 1) {
                        j.setVisible(false);
                        FileManager.saveAs(idEllingtonUI, jTree, jTabbedPane);
                    }
                }

                try {
                    JScrollPane sp = (JScrollPane) jTabbedPane.getSelectedComponent();
                    RSyntaxTextArea textArea = (RSyntaxTextArea) sp.getViewport().getComponent(0);
                    Files.writeString(
                            Path.of(file.getPath()),
                            textArea.getText(),
                            StandardCharsets.UTF_8
                    );
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void save(IDEllingtonUI idEllingtonUI, JTree jTree, JTabbedPane jTabbedPane) {
        if (currentFileOpened != null) {
            try {
                JScrollPane sp = (JScrollPane) jTabbedPane.getSelectedComponent();
                RSyntaxTextArea textArea = (RSyntaxTextArea) sp.getViewport().getComponent(0);
                Files.writeString(
                        Path.of(currentFileOpened.getPath()),
                        textArea.getText(),
                        StandardCharsets.UTF_8
                );
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void newFile(IDEllingtonUI idEllingtonUI, JTree jTree, JTabbedPane jTabbedPane) {
        if (true) {
            JFileChooser j = new JFileChooser();

            j.setFileSelectionMode(JFileChooser.FILES_ONLY);
            j.setDialogType(JFileChooser.SAVE_DIALOG);

            if (j.showSaveDialog(idEllingtonUI) == 0) {
                File file = new File(j.getSelectedFile().getAbsolutePath());

                currentFileOpened = file;

                if (file.exists()) {
                    int choice = JOptionPane.showConfirmDialog(idEllingtonUI, "Le fichier existe déjà, voulez-vous l'écraser ?");

                    if (choice == 1) {
                        j.setVisible(false);
                        FileManager.saveAs(idEllingtonUI, jTree, jTabbedPane);
                    }
                }

                try {
                    Files.writeString(
                            Path.of(file.getPath()),
                            "",
                            StandardCharsets.UTF_8
                    );
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                FileTreeModel fileTreeModel = new FileTreeModel(new FileTreeModel.MyFile(file));
                Optional<Languages> optionalLanguages = LanguagesRecognition.recognise(fileTreeModel);

                if (optionalLanguages.isPresent()) {
                    Languages languages = optionalLanguages.get();
                    FileExplorerUI.setLanguages(optionalLanguages.get());
                    Compilation.compilePrep(optionalLanguages.get());
                }
                else
                    System.out.println("Language not recognized");

                ModernTextArea textAreaBuilder = new ModernTextArea(ThemeManager.getCurrentTheme().baseColor, ThemeManager.getCurrentTheme().writingColor, file);
                RSyntaxTextArea textArea = textAreaBuilder.getTextArea();
                textArea.setText(Utils.getFileContent(file.getPath()));
                JScrollPane scroll =  new JScrollPane(textArea);
                jTabbedPane.add(file.getName(), scroll);
                int index = jTabbedPane.indexOfTab(file.getName());
                JPanel tab = new ModernClosePane(new GridBagLayout(), jTabbedPane, file.getName());
                jTabbedPane.setTabComponentAt(index, tab);
                jTabbedPane.setSelectedComponent(scroll);
            }

        }
    }

}
