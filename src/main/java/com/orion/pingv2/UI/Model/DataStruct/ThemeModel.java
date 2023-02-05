package com.orion.pingv2.UI.Model.DataStruct;

import java.awt.*;

public class ThemeModel {

    private String name;
    public Color baseColor;
    public Color baseVariantColor;
    public Color accentColor;
    public Color writingColor;
    public String explorerImage;
    public String themeManagerImage;

    public ThemeModel(String name, Color baseColor, Color baseVariantColor, Color accentColor,
                      Color writingColor, String explorerImage, String themeManagerImage) {
        this.name = name;
        this.baseColor = baseColor;
        this.baseVariantColor = baseVariantColor;
        this.accentColor = accentColor;
        this.writingColor = writingColor;
        this.explorerImage = explorerImage;
        this.themeManagerImage = themeManagerImage;
    }

}
