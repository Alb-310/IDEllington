/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.orion.pingv2;

import com.orion.pingv2.Feature.SoundEffects;
import com.orion.pingv2.UI.IDEllingtonUI;
import com.orion.pingv2.UI.Manager.ActionListenerManager;
import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author tom.termaat
 */
public class PINGv2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws InterruptedException {
        //System.setProperty("apple.awt.application.appearance", "system");
        Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ThemeManager.beforeInit();

        IDEllingtonUI idEllingtonUI = new IDEllingtonUI(
                "IDEllington — Orion Corp.",
                screenDim.width,
                screenDim.height
        );

        ThemeManager.afterInit(idEllingtonUI);
        new ActionListenerManager(idEllingtonUI).bindActions();
        idEllingtonUI.setVisible(true);
        SoundEffects.onStartSound();
    }
}
