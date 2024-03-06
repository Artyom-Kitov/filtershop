package ru.nsu.icg.filtershop.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

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
        original = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }

    public void setOriginal(BufferedImage image) {
        original = image;
        resized = image;
        edited = image;
    }

}
