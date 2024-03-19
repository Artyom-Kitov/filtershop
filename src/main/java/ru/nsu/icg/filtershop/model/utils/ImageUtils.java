package ru.nsu.icg.filtershop.model.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import ru.nsu.icg.filtershop.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Artyom Kitov
 * <br/>
 * Date: 12.03.2024
 */
@UtilityClass
@Log4j2
public class ImageUtils {

    public BufferedImage cloneImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    public void writeTo(BufferedImage from, BufferedImage to) {
        if (from.getWidth() > to.getWidth() || from.getHeight() > to.getHeight()) {
            throw new IllegalArgumentException("destination image is too small");
        }
        for (int y = 0; y < from.getHeight(); y++) {
            for (int x = 0; x < from.getWidth(); x++) {
                to.setRGB(x, y, from.getRGB(x, y));
            }
        }
    }

    public static ImageIcon getImageFromResources(String path) {
        URL imageURL = Main.class.getResource(path);
        if (imageURL != null) {
            return new ImageIcon(imageURL);
        }
        else {
            log.error("Couldn't get an image from resource path: " + path);
            return null;
        }
    }

    public static ImageIcon getScaledImageFromResources(String path, int width, int height) {
        ImageIcon imageFromResources = getImageFromResources(path);
        if (imageFromResources == null) {
            return null;
        }
        Image image = imageFromResources.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public static BufferedImage getRotatedImage(BufferedImage image, int rotationAngle) {
        double angle = Math.toRadians(rotationAngle);
        Point center = new Point(image.getWidth() / 2, image.getHeight() / 2);

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        List<Point> corners = new ArrayList<>();
        corners.add(new Point(0, 0));
        corners.add(new Point(0, image.getHeight() - 1));
        corners.add(new Point(image.getWidth() - 1, image.getHeight() - 1));
        corners.add(new Point(image.getWidth() - 1, 0));

        for (Point corner : corners) {
            minX = Math.min(minX, rotateX(corner.x, corner.y, angle, center));
            maxX = Math.max(maxX, rotateX(corner.x, corner.y, angle, center));
            minY = Math.min(minY, rotateY(corner.x, corner.y, angle, center));
            maxY = Math.max(maxY, rotateY(corner.x, corner.y, angle, center));
        }
        int newWidth = maxX - minX;
        int newHeight = maxY - minY;

        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int x1 = rotateX(x, y, angle, center) - minX;
                int y1 = rotateY(x, y, angle, center) - minY;
                if (x1 >= 0 && x1 <= newImage.getWidth() - 1 && y1 >= 0 && y1 <= newImage.getHeight() - 1) {
                    newImage.setRGB(x1, y1, image.getRGB(x, y));
                }
            }
        }

        return newImage;
    }

    private static int rotateX(int x, int y, double angle, Point center) {
        return (int) (Math.cos(angle) * x - Math.sin(angle) * y
               + (1 - Math.cos(angle)) * center.x - Math.sin(angle) * center.y);
    }

    private static int rotateY(int x, int y, double angle, Point center) {
        return (int) (Math.sin(angle) * x + Math.cos(angle) * y
                + Math.sin(angle) * center.x + (1 - Math.cos(angle)) * center.y);
    }
}
