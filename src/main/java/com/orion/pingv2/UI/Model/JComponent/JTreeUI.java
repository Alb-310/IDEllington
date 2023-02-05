package com.orion.pingv2.UI.Model.JComponent;

import com.orion.pingv2.Feature.FileManager;
import com.orion.pingv2.UI.IDEllingtonUI;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.ModernUI.ModernClosePane;
import com.orion.pingv2.UI.Model.ModernUI.ModernTextArea;
import com.orion.pingv2.Utils.Utils;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class JTreeUI implements UIBuilder {

    private JTree jTree = new JTree();
    private Color bgColor;
    private Color fgColor;

    // Dirty code but don't worry it works
    public static Path rootPath = Path.of("");
    public static JTabbedPane jTabbedPane;
    public static IDEllingtonUI idEllingtonUI;

    public JTreeUI(Color bgColor, Color fgColor) {
        super();

        this.bgColor = bgColor;
        this.fgColor = fgColor;

        this.jTree.setModel(null);

        MouseListener ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = jTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 2) {
                        myDoubleClick(selRow, selPath);
                    }
                }
            }
        };
        jTree.addMouseListener(ml);
        
        this.setupUI();
    }

    public JTree getJtree() {
        return this.jTree;
    }

    private void myDoubleClick(int selRow, TreePath selPath) {
        String p = rootPath.toString();
        Object[] oldSp = selPath.getPath();
        Object[] sp = Arrays.copyOfRange(oldSp, 1, oldSp.length);

        for (Object s : sp) {
            if (System.getProperty("os.name").matches("(?i)windows.*"))
                p = p.concat("\\");
            else
                p = p.concat("/");
            p = p.concat(s.toString());
        }

        if (new File(p).isFile())
            openFile(new File(p));
    }

    private void openFile(File f)
    {
        FileManager.open(f, jTabbedPane);
    }

    public JTree getjTree() {
        return jTree;
    }

    @Override
    public void setupUI() {
        this.jTree.setBackground(this.bgColor);
        this.jTree.setForeground(this.fgColor);

        Border border = this.jTree.getBorder();
        Border margin = new EmptyBorder(10,10,10,10);
        this.jTree.setBorder(new CompoundBorder(border, margin));

        this.jTree.setCellRenderer(new MyCellRenderer());
    }

    @Override
    public JTree build() {
        return this.jTree;
    }

    public Path getRootPath() {
        return rootPath;
    }

    public void setRootPath(Path rootPath) {
        this.rootPath = rootPath;
    }

    public class MyCellRenderer extends DefaultTreeCellRenderer {

        @Override
        public Color getBackgroundNonSelectionColor() {
            return ThemeManager.getCurrentTheme().baseColor;
        }

        @Override
        public Color getBackgroundSelectionColor() {
            return ThemeManager.getCurrentTheme().baseColor;
        }

        @Override
        public Color getBackground() {
            return ThemeManager.getCurrentTheme().baseColor;
        }

        @Override
        public Color getBorderSelectionColor() {
            return ThemeManager.getCurrentTheme().accentColor;
        }

        @Override
        public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
            final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
            //final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
            this.setText(value.toString());

            this.setForeground(ThemeManager.getCurrentTheme().accentColor);

            return ret;
        }
    }

}
