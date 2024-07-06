package com.planvision;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.Icon;

public class BufferedIcon implements Icon {

    private BufferedImage img;

    public BufferedIcon(BufferedImage img) {
        this.img = img;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.drawImage(img, x, y, null);
    }

    @Override
    public int getIconWidth() {
        return img.getWidth();
    }

    @Override
    public int getIconHeight() {
        return img.getHeight();
    }
}
