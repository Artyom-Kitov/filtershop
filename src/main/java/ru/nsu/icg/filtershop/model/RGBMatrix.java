package ru.nsu.icg.filtershop.model;

import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/*
Author: Artyom Kitov
Date: 06.03.2024
 */
public class RGBMatrix {

    private BufferedImage original;
    private BufferedImage resized;

    @Getter
    private BufferedImage edited;

    public RGBMatrix(int width, int height) {
        setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
    }

    public void setImage(BufferedImage image) {
        original = image;
        resized = cloneImage(image);
        edited = cloneImage(image);
    }

    public void makeBlackWhite() {
        final double[] factors = {0.299, 0.587, 0.114};
        for (int y = 0; y < resized.getHeight(); y++) {
            for (int x = 0; x < resized.getWidth(); x++) {
                int color = resized.getRGB(x, y);
                int r = (color & 0xff0000) >> 16;
                int g = (color & 0x00ff00) >> 8;
                int b = color & 0x0000ff;
                int gray = (int) (r * factors[0] + g * factors[1] + b * factors[2]);
                edited.setRGB(x, y, (gray << 16) + (gray << 8) + gray);
            }
        }
    }

    public void invert() {
        for (int y = 0; y < resized.getHeight(); y++) {
            for (int x = 0; x < resized.getWidth(); x++) {
                int color = resized.getRGB(x, y);
                int r = (color & 0xff0000) >> 16;
                int g = (color & 0x00ff00) >> 8;
                int b = color & 0x0000ff;
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;
                edited.setRGB(x, y, (r << 16) + (g << 8) + b);
            }
        }
    }

    private BufferedImage cloneImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

}
