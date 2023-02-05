package com.orion.pingv2.UI.Model.ModernUI;

import com.orion.pingv2.UI.Manager.Theme.ThemeManager;
import com.orion.pingv2.Utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ModernImageViewer extends JLabel {

    private BufferedImage image;

    public void setImage(String imagePath, double multiplierSize) {

        try {
            image = ImageIO.read(new File(imagePath));

            final Dimension dimension = new Dimension(
                    (int)(image.getWidth() * multiplierSize),
                    (int)(image.getHeight() * multiplierSize)
            );

            image = Utils.resizeImage(
                    image,
                    dimension.width,
                    dimension.height
            );

            this.setIcon(new ImageIcon(image));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
