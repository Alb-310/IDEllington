package com.orion.pingv2.UI.Model.SwingFX;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * SwingFXWebView
 */
public class SwingFXWebView extends JPanel {

    private String urlPage;
    private Stage stage;
    private WebView browser;
    private JFXPanel jfxPanel;
    private WebEngine webEngine;

    public SwingFXWebView( String urlPage){
        this.urlPage = urlPage;
        initComponents();
    }

    private void initComponents(){

        jfxPanel = new JFXPanel();
        createScene();

        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
    }

    /**
     * createScene
     *
     * Note: Key is that Scene needs to be created and run on "FX user thread"
     *       NOT on the AWT-EventQueue Thread
     *
     */
    private void createScene() {
        PlatformImpl.startup(() -> {

            stage = new Stage();

            stage.setResizable(true);

            Group root = new Group();
            Scene scene = new Scene(root,960,540);
            stage.setScene(scene);

            // Set up the embedded browser:
            browser = new WebView();
            browser.setPrefSize(960, 540);
            webEngine = browser.getEngine();
            webEngine.load(urlPage);

            ObservableList<Node> children = root.getChildren();
            children.add(browser);

            jfxPanel.setScene(scene);
        });
    }
}
