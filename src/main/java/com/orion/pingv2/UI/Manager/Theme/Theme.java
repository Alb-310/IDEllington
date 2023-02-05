package com.orion.pingv2.UI.Manager.Theme;

import com.orion.pingv2.UI.Model.DataStruct.ThemeModel;

import java.awt.*;

public class Theme {

    public enum Type {
        DARK,
        BRIGHT
    }

    private String name;
    private ThemeModel darkTheme;
    private ThemeModel brightTheme;
    public String explorerImage;
    public String themeManagerImage;

    public Theme(String name, Color[] darkColor, Color[] brightColor, Color accentColor,
                 Color writingColor, String explorerImage, String themeManagerImage) {
        this.name = name;
        this.darkTheme = new ThemeModel("Dark", darkColor[0], darkColor[1], accentColor,
                                         writingColor, explorerImage, themeManagerImage);
        this.brightTheme = new ThemeModel("Bright", brightColor[0], brightColor[1], accentColor,
                                           writingColor, explorerImage, themeManagerImage);
    }

    public String getName() {
        return this.name;
    }

    public String getFullName(Type type) {
        return type == Type.DARK ? name + " Dark" : name + " Bright";
    }

    public ThemeModel getDarkTheme() {
        return darkTheme;
    }

    public ThemeModel getBrightTheme() {
        return brightTheme;
    }

}
