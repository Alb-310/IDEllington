package com.orion.pingv2.UI.Model.ModernUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class ModernButton extends JButton {

    private ImageIcon imageIcon;

    public ModernButton(String text) {
        super(text);
    }
    public ModernButton(String imagePath, double multiplierSize) {
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

    public ModernButton(InputStream imageStream, double multiplierSize) {
        try {
            imageIcon = new ImageIcon(ImageIO.read(imageStream));
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
