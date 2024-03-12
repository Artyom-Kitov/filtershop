package ru.nsu.icg.filtershop.model.utils;

import lombok.experimental.UtilityClass;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

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
            throw new IllegalArgumentException("different images sized");
        }
        for (int y = 0; y < from.getHeight(); y++) {
            for (int x = 0; x < from.getWidth(); x++) {
                to.setRGB(x, y, from.getRGB(x, y));
            }
        }
    }

}
