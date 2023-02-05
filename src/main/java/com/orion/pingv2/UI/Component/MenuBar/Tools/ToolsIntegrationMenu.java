package com.orion.pingv2.UI.Component.MenuBar.Tools;

import com.orion.pingv2.UI.Component.MenuBar.Tools.ToolsIntegration.YoutubeIntegrationUI;
import com.orion.pingv2.UI.Model.Interface.UIBuilder;
import com.orion.pingv2.UI.Model.JComponent.JMenuUI;

import javax.swing.*;

public class ToolsIntegrationMenu extends JMenuUI implements UIBuilder {

    private YoutubeIntegrationUI youtubeIntegrationUI;
    private JMenuItem youtubeItem;

    public ToolsIntegrationMenu(String text) {
        super(text);
        this.setupUI();
    }

    public JMenuItem getYoutubeItem() {
        return youtubeItem;
    }

    @Override
    public void setupUI() {
        this.youtubeIntegrationUI = new YoutubeIntegrationUI(
                "IDEllington — YouTube Integration",
                960,
                560
        );

        this.youtubeItem = new JMenuItem("YouTube");
        this.youtubeItem.addActionListener(event -> {
            this.youtubeIntegrationUI.setVisible(true);
        });
    }

    @Override
    public JMenu build() {
        this.menu.add(youtubeItem);

        return this.menu;
    }

}
