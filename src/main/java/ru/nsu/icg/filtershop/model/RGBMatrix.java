package ru.nsu.icg.filtershop.model;

import lombok.Getter;
import ru.nsu.icg.filtershop.model.utils.ImageUtils;

import java.awt.image.BufferedImage;

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
        resized = ImageUtils.cloneImage(image);
        edited = ImageUtils.cloneImage(image);
    }

    public void reset() {
        setImage(original);
    }

}
