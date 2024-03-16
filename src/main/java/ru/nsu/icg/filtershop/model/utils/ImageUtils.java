package ru.nsu.icg.filtershop.model.utils;

import lombok.experimental.UtilityClass;
import ru.nsu.icg.filtershop.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.net.URL;
import java.util.Objects;

/**
 * Author: Artyom Kitov
 * <br/>
 * Date: 12.03.2024
 */
@UtilityClass
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
            // add logging
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
}
