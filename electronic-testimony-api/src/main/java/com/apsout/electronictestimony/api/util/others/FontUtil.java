package com.apsout.electronictestimony.api.util.others;

import com.apsout.electronictestimony.api.controller.ResourceController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * From https://stackoverflow.com/a/14380414
 */
public class FontUtil {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    //TODO Review
    public static int pickOptimalSize(Graphics2D g, String content, int width, int height) {
        Rectangle2D rectangle2D = null;
        int initialFontSize = 30; //initial value
        do {
            initialFontSize--;
            Font font = new Font("Arial", Font.PLAIN, initialFontSize);
            rectangle2D = getStringBoundsRectangle2D(g, content, font);
        } while (rectangle2D.getWidth() >= width || rectangle2D.getHeight() >= height);
        return initialFontSize;
    }

    private static Rectangle2D getStringBoundsRectangle2D(Graphics g, String title, Font font) {
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(title, g);
        return rect;
    }

    public static int pickOptimalSize(String content, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g1 = image.createGraphics();
        return pickOptimalSize(g1, content, width, height);
    }
}
