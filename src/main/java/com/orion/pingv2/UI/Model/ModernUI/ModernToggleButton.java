package com.orion.pingv2.UI.Model.ModernUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class ModernToggleButton extends JToggleButton {

    private ImageIcon imageIcon;

    public ModernToggleButton(String text) {
        super(text);
    }
    public ModernToggleButton(String imagePath, double multiplierSize) {
        super();
        this.setImageIcon(imagePath, multiplierSize);
    }

    public ModernToggleButton(InputStream bufferedImage, double multiplierSize) {
        super();
        this.setImageIcon(bufferedImage, multiplierSize);
    }

    public void setImageIcon(String imagePath, double multiplierSize) {
        try {
            imageIcon = new ImageIcon(imagePath);
            ImageIcon iconResized = scaleImage(
                    imageIcon,
                    (int)(imageIcon.getIconHeight() * multiplierSize),
                    (int)(imageIcon.getIconWidth() * multiplierSize)
            );

            this.setIcon(iconResized);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImageIcon(InputStream bufferedImage, double multiplierSize) {
        try {
            imageIcon = new ImageIcon(ImageIO.read(bufferedImage));
            ImageIcon iconResized = scaleImage(
                    imageIcon,
                    (int)(imageIcon.getIconHeight() * multiplierSize),
                    (int)(imageIcon.getIconWidth() * multiplierSize)
            );

            this.setIcon(iconResized);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ImageIcon scaleImage(ImageIcon icon, int w, int h)
    {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if(icon.getIconWidth() > w)
        {
            nw = w;
            nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if(nh > h)
        {
            nh = h;
            nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
    }

}
