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
        float angle = (float) Math.toRadians(rotationAngle);
        Point center = new Point(image.getWidth() / 2, image.getHeight() / 2);
        float cos = (float) Math.cos(-angle);
        float sin = (float) Math.sin(-angle);

        Dimension newSize = findSizeAfterRotation(image.getWidth(), image.getHeight(), cos, sin);
        Point newCenter = new Point(newSize.width / 2, newSize.height / 2);

        BufferedImage newImage = new BufferedImage(newSize.width, newSize.height, BufferedImage.TYPE_INT_ARGB);
        int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
        for (int y = 0; y < newImage.getHeight(); ++y) {
            for (int x = 0; x < newImage.getWidth(); ++x) {
                int x1 = (int) ((x - newCenter.x) * cos - (y - newCenter.y) * sin) + center.x;
                int y1 = (int) ((x - newCenter.x) * sin + (y - newCenter.y) * cos) + center.y;
                if (x1 >= 0 && x1 < image.getWidth() && y1 >= 0 && y1 < image.getHeight()) {
                    newImage.setRGB(x, y, pixels[y1 * image.getWidth() + x1]);
                }
                else {
                    newImage.setRGB(x, y, 0xFFFFFFFF);
                }
            }
        }

        return newImage;
    }

    private static Dimension findSizeAfterRotation(int oldWidth, int oldHeight, float cos, float sin) {
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        List<Point> corners = new ArrayList<>();
        corners.add(new Point(0, 0));
        corners.add(new Point(0, oldHeight - 1));
        corners.add(new Point(oldWidth - 1, oldHeight - 1));
        corners.add(new Point(oldWidth - 1, 0));

        for (Point corner : corners) {
            minX = Math.min(minX, (int) (corner.x * cos - corner.y * sin));
            maxX = Math.max(maxX, (int) (corner.x * cos - corner.y * sin));
            minY = Math.min(minY, (int) (corner.x * sin + corner.y * cos));
            maxY = Math.max(maxY, (int) (corner.x * sin + corner.y * cos));
        }
        int newWidth = maxX - minX;
        int newHeight = maxY - minY;
        return new Dimension(newWidth, newHeight);
    }
}
