package com.orion.pingv2.UI.Model.Interface;

import javax.swing.*;

public interface UIBuilder {

    /**
     * @implNote Set up the linked JComponent and instantiate its components
     */
    void setupUI();

    /**
     *
     * @return The linked JComponent
     */
    JComponent build();

}
