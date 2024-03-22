package ru.nsu.icg.filtershop.model.utils;

import lombok.experimental.UtilityClass;

import java.awt.image.BufferedImage;

@UtilityClass
public class ColorUtils {
    public int getRed(int rgb) {
        return (rgb >> 16) & 0xFF;
    }

    public int getGreen(int rgb) {
        return (rgb >> 8) & 0xFF;
    }

    public int getBlue(int rgb) {
        return rgb & 0xFF;
    }

    public int getRGB(int r, int g, int b) {
        return 0xff000000 | (r << 16) | (g << 8) | b;
    }

    public int getMiddleRGB(int c) {
        return (int) ((getRed(c) + getGreen(c) + getBlue(c)) / 3.);
    }
}
