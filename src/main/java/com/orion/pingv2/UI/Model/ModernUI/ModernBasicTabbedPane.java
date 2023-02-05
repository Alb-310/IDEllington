package com.orion.pingv2.UI.Model.ModernUI;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class ModernBasicTabbedPane extends BasicTabbedPaneUI {
    private int leadingOffset = 0;
    private int minHeight = 30;
    private int trailingOffset = 0;

    public ModernBasicTabbedPane() {
        super();
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex,
                                     int fontHeight) {
        return Math.max(super.calculateTabHeight(tabPlacement, tabIndex, fontHeight), this.minHeight);
    }

    @Override
    protected Insets getTabAreaInsets(int tabPlacement) {
        // ignores tab placement for now
        return new Insets(0, this.leadingOffset, 0, this.trailingOffset);
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) { }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
        if (!isSelected) {
            g.setColor(ThemeManager.getCurrentTheme().accentColor);
            g.drawString(title, textRect.x, textRect.y + (textRect.height / 2) + (int)(metrics.getHeight() * 0.35));
        }
    }

    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        super.paintTabBackground(g, tabPlacement, tabIndex, x, y, w, h, isSelected);

        if(isSelected) {
            g.setColor(ThemeManager.getCurrentTheme().accentColor);
        }
        else
            g.setColor(ThemeManager.getCurrentTheme().baseVariantColor);

        g.fillRect(x, y, w, h);
    }

    @Override
    protected Insets getTabInsets(int tabPlacement, int tabIndex) {
        return new Insets(8, 20, 8, 20);
    }

    public void setLeadingOffset(int offset) {
        this.leadingOffset = offset;
    }

    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    public void setTrailingOffset(int offset) {
        this.trailingOffset = offset;
    }
}
