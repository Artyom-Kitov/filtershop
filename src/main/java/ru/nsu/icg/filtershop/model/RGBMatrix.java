package ru.nsu.icg.filtershop.model;

import lombok.Getter;
import ru.nsu.icg.filtershop.model.tools.Tool;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

/*
Author: Artyom Kitov
Date: 06.03.2024
 */
@Getter
public class RGBMatrix {

    private BufferedImage original;

    private BufferedImage resized; // should we add a new field for a rotated image?

    private BufferedImage edited;

    public RGBMatrix(int width, int height) {
        setImage(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
    }

    public void setImage(BufferedImage image) {
        original = image;
        resized = cloneImage(image);
        edited = cloneImage(image);
    }

    private BufferedImage cloneImage(BufferedImage image) {
        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }
}
