package ru.nsu.icg.filtershop.model.tools;

import ru.nsu.icg.filtershop.model.RGBMatrix;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface Tool {

    void applyTo(BufferedImage original, BufferedImage edited);

    default void applyTo(RGBMatrix matrix) {
        applyTo(matrix.getResized(), matrix.getEdited());
    }

}
